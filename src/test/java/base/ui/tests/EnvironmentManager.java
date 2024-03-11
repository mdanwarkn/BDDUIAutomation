package base.ui.tests;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentManager {
    public static final Map<String,String> ENVIRONMENT_URL = new HashMap<>();

    static{
      setTestEnvironmentURL();
    }

    public static void setTestEnvironmentURL(){
        ENVIRONMENT_URL.put("MakeMyTrip","https://www.makemytrip.com/");
        ENVIRONMENT_URL.put("BankOfBaroda","https://www.bankofbaroda.in/calculators/fixed-deposit-calculator");
    }

    public static synchronized String  getTestEnvironmentURL(String reference){
        return ENVIRONMENT_URL.get(reference);
    }


}
