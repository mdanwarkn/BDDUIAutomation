Feature: Checking Make My Trip Application


 Background: User successfully lands on Make My Trip Home Page
    Given The User Opens Make My Trip Application
    And Close if any advertisement banner is popping up

 Scenario Outline: Checking Flight booking functionality
    Given The User has the flight booking details READ from the Excel "MakeMyTripTestData"  Sheet "FlightBookingDetails" and the Details are <Row No>
    When The User enters the Source , Destination and Travel Date
    And Clicks on Search flight
    Then User should be landed on Flight Search Results Screen And Should be shown with the Cheapest flight on the top


   Examples:
    | Row No|
    |      1|



