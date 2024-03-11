package IMDB.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedTileSearchPage extends BasePage {

    public Map<String,String> outputRow;

    protected static final String SEARCH_RESULT = "//li[contains(@class,'summary')][%s]";

    List<Map<String,String>> tiles;
    boolean retrieveResultsUpdated;
    
    public AdvancedTileSearchPage(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
        outputRow = new HashMap<>();
    }

    public void applySortCriteria(String criteria){
        selectByVisibleText(getLocator(SELECT_WITH_ARIALABEL , "Sort by") , criteria , 30);
        retrieveResultsUpdated =  false;
    }

    public void retrieveTopNTileDetails(int count){
        if(!retrieveResultsUpdated){
            tiles = new ArrayList<>();
            for(int i = 1 ; i<=count ; i++){
                Map<String,String> tile = new HashMap<>();
                String searchResult = String.format(SEARCH_RESULT , i);
                tile.put("Name",geTextWhenVisible(getLocator(searchResult +  H3 ) , 30).replace(i +". " , ""));
                tile.put("RunningTime",geTextWhenVisible(getLocator(searchResult +  ELEMENT_CONTAINS_CLASS_WITH_INDEX , "title-metadata", "2") , 30));
                tile.put("IMDBRating",geTextWhenVisible(getLocator(searchResult +  ELEMENT_CONTAINS_ARIALABEL , "IMDb rating") , 30).split("\n")[0]);
                tiles.add(tile);
            }
            System.out.println(tiles);
            retrieveResultsUpdated = true;    
        }
    }

    public void retrieveListOfTopNTileName(int count) throws IOException {
        retrieveTopNTileDetails(count);
        String topNTilesName = "";
        for(int i = 0 ; i<count ; i++){
            if(i==count-1){
                topNTilesName +=tiles.get(i).get("Name");
            }else{
                topNTilesName +=tiles.get(i).get("Name")+",";
            }
        }
        outputRow.put("List Of Top 5 Tile Name",topNTilesName);
    }

    public void findAverageTimeDurationOfTopNTiles(int count){
        retrieveTopNTileDetails(count);
        String time;
        int totalSeconds = 0;
        int averageSecond;
        for(int i = 0 ; i<count ; i++){
            time = tiles.get(i).get("RunningTime");
            totalSeconds += Integer.valueOf(time.split(" ")[0].replaceAll("[A-Za-z]","")) * 3600;
            totalSeconds += Integer.valueOf(time.split(" ")[1].replaceAll("[A-Za-z]","")) * 60;
        }
        averageSecond = (totalSeconds/count);
        int hours  = (averageSecond / 3600);
        int minutes = (averageSecond % 3600) / 60;
        outputRow.put("Average Time Duration of Top 5 Tiles" , String.format("%02d:%02d",hours , minutes));
    }

    public void findAverageRatingOfTopNTiles(int count){
        retrieveTopNTileDetails(count);
        float averageRating;
        float totalRating = 0;
        for(int i = 0 ; i<count ; i++){
            totalRating += Float.parseFloat(tiles.get(i).get("IMDBRating"));
        }
        averageRating = (totalRating/count);
        outputRow.put("Average IMDB of Top 5 Tiles" , String.valueOf(averageRating));
    }
}
