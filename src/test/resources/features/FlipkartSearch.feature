Feature: Validate flipkart search functionality

@FlipkartTest
Scenario: Checking searchBox in flipkart home page
Given The User opens the flipkart application
Then The User should see the search box in home screen

@FlipkartTest
Scenario: Checking Flipkart Search functionality
Given The User opens the flipkart application
When The User searches "Iphone" in the search box
Then The User should see the item "Apple iPhone 14 (Midnight, 128 GB)" in the search result
And The compareTo option should be present and enabled for that item
