Feature: Validate Embedded PDF

@PDFTest
Scenario: Checking PDF Header
Given The User opens the embedded PDF in SEBI application
Then The User should see the PDF header as "BEFORE THE ADJUDICATING OFFICER" "SECURITIES AND EXCHANGE BOARD OF INDIA"

@PDFTest
Scenario: Downloading PDF opened in the browser
Given The User opens the embedded PDF in SEBI application
When The User clicks on pdf download button
Then The PDF should be downloaded
And Extract The content from the downloaded PDF