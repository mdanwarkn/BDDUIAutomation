Feature: Checking Make My Trip Application

 Background: User successfully lands on Make My Trip Home Page
    Given The User Opens Make My Trip Application
    And Close if any advertisement banner is popping up

 @MakeMyTripTest
 Scenario Outline: Checking Flight booking functionality
    Given The User has the flight booking details READ from the Excel "MakeMyTripTestData"  Sheet "FlightBookingDetails" and the Details are <Row No>
    When The User enters the Source , Destination and Travel Date
    And Clicks on Search flight
    Then User should be landed on Flight Search Results Screen
    And Should be shown with the Cheapest flight on the top
    Then User selects the Lowest Fare Flight
    And Select the Fare Option
    Then User should be landed on Complete Booking Screen
    And Check the flight booking details are populated correctly in Booking Screen
    Then Add Travelled Details
    And Add Customer contact details
    And Confirm the Billing details
    And Click "Continue" in confirm booking screen
    And Verify the passenger details in Review Modal and Click "CONFIRM"
    Then User should be landed on Seat Booking section
    And User Selects the seat for the passeneger included in the journey
    And Selects Skip to add-ons after seat selection
    Then The Button "Proceed to pay" should be visible and when the user clicks on proceeds to pay
    And "Accept" if airfare price increased during the process of flight booking
    Then User should be landed on Payment Screen

   Examples:
    | Row No|
    |      0|
    |      1|




