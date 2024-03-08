@BOBTest
Feature: Checking Bank of Baroda application

@BOBReInvestmentTest
Scenario Outline: Checking BOB Fixed Deposit Calculator -  Reinvestment (Cumulative)
Given The User is in BOB Fixed Deposit Calculator screen
When The User enters the Amount of fixed deposit as "<Amount>"
And The User enters the Tenure date as "<Tenure>"
Then Validate Rate of Interest is "<Interest>"
And Validate Maturity Date And Maturity Value
And Store the FD results in output file

Examples:
  |Amount| Tenure                       | Interest|
  |100000|5 Years: 0 Months : 0 Days    | 6.5     |
  |50000 |2 Years: 11 Months: 0 Days    | 7.25    |

@BOBQuarterlyPayoutTest
Scenario Outline: Checking BOB Fixed Deposit Calculator -  Quarterly Payout
  Given The User is in BOB Fixed Deposit Calculator screen
  When The User selects the Fixed Deposit Type as "Quarterly Payout"
  And The User enters the Amount of fixed deposit as "<Amount>"
  And The User enters the Tenure date as "<Tenure>"
  Then Validate Rate of Interest is "<Interest>"
  And Validate Maturity Date And Maturity Value
  And Store the FD results in output file

  Examples:
    |Amount| Tenure                       | Interest|
    |100000|5 Years: 0 Months : 0 Days    | 6.5     |
