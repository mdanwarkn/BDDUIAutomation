Feature: Validate IMDB Advanced Tile Search

@IMDBTest
Scenario: Retrieving Tile from IMDB Application
Given The User is in IMDB Advanced Tile Search window
When The User applies sort by criteria as "Number of ratings"
Then Retrieve the list of top 5 movies
And Find out the average IMDB rating for top 5 movies
And Find out the average running time of top 5 movies
And Store the results in output file
