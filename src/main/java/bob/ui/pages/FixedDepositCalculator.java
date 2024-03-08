package bob.ui.pages;

import base.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FixedDepositCalculator extends BasePage {
    public Map<String,String> currentRow;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");
    private static final String DEPOSIT_TYPE_DROPDOWN_OPTION = "//*[@id='fdType']/li[normalize-space(text())='%s']";

    public FixedDepositCalculator(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
        currentRow = new HashMap<>();
        setDefaultDepositType();
    }

    public void setDefaultDepositType(){
        currentRow.put("FDDepositType","Reinvestment (Cumulative)");
    }

    public void selectFixedDepositType(String depositType){
        clickTheElementWhenVisibleUsingJS(getLocator(FOLLOWING_SIBLING + SPAN , "Type of Fixed Deposit:") , 30);
        clickTheElementWhenVisibleUsingJS(getLocator(DEPOSIT_TYPE_DROPDOWN_OPTION , depositType) , 30);
        currentRow.put("FDDepositType",depositType);
    }

    public void enterFixedDepositAmount(String amount){
        enterValueWhenVisible(getLocator(FOLLOWING_INPUTBOX ,"Amount of Fixed Deposit:") , String.valueOf(amount) ,30);
        currentRow.put("FDAmount",amount);
    }

    public void enterTenure(String tenure){
        String years = tenure.split(":")[0].replaceAll("[^\\d+]","");
        String months = tenure.split(":")[1].replaceAll("[^\\d+]","");
        String days = tenure.split(":")[2].replaceAll("[^\\d+]","");
        enterValueWhenVisible(getLocator(EM_WITH_INDEX ,"1") , years , 10);
        enterValueWhenVisible(getLocator(EM_WITH_INDEX ,"2") , months , 10);
        enterValueWhenVisible(getLocator(EM_WITH_INDEX ,"3") , days , 10);
        currentRow.put("FDTenure",tenure);
        LocalDate depositedDate = LocalDate.now();
        LocalDate expectedMaturityDate = depositedDate.plusYears(Integer.parseInt(years)).plusMonths(Integer.parseInt(months)).plusDays(Integer.parseInt(days));
        currentRow.put("DepositedDate",depositedDate.format(dateTimeFormatter));
        currentRow.put("FDTenureDays", String.valueOf(ChronoUnit.DAYS.between(depositedDate , expectedMaturityDate)));
    }

    public void validateRateOfInterest(String interest){
        currentRow.put("FDRateOfInterest",verifyTextWhenVisible(getLocator(FOLLOWING_SIBLING,"Rate of Interest (%)") , interest , 10));
    }

    public void validateMaturityDateAndValue(){
        String expectedMaturityDate = LocalDate.parse(currentRow.get("DepositedDate"),dateTimeFormatter)
                                                        .plusDays(Integer.parseInt(currentRow.get("FDTenureDays")))
                                                        .format(dateTimeFormatter);
        currentRow.put("FDMaturityDate",verifyTextWhenVisible(getLocator(FOLLOWING_SIBLING,"Maturity Date") , expectedMaturityDate , 30));

        String actualMaturityValue = geTextWhenVisible(getLocator(FOLLOWING_SIBLING,"Maturity Value") , 10);

        if(currentRow.get("FDDepositType").equals("Quarterly Payout")) {
            float interest = Float.parseFloat(currentRow.get("FDRateOfInterest"));
            int days = Integer.parseInt(currentRow.get("FDTenureDays"));
            int depositedAmount  = Integer.parseInt(currentRow.get("FDAmount"));
            int years = days / 365;
            int totalInterestAmount  = (int)((depositedAmount * years * interest) / 100);
            int interestPerQuarter = totalInterestAmount / (years * 4);

            NumberFormat inrFormatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
            inrFormatter.setMaximumFractionDigits(0);
            currentRow.put("AggregateInterestAmount",verifyTextWhenVisible(getLocator(FOLLOWING_SIBLING,"Aggregate Interest Amount") , inrFormatter.format(totalInterestAmount) , 30));
            currentRow.put("InterestPerQuarter",verifyTextWhenVisible(getLocator(FOLLOWING_SIBLING,"Interest Per Quarter ₹") , inrFormatter.format(interestPerQuarter).replace("₹","") , 30));
        }
        currentRow.put("FDMaturityValue",actualMaturityValue);
    }

}
