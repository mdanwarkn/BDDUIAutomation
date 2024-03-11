package utilities;

import java.util.List;

public class Utility {


    public static boolean IsListSortedInAscending(List<Integer> list){
        int i;
        int j;
        for( i = 0 , j = 1; j < list.size() && list.get(i)<=list.get(j) ; i++ , j++);
        return j==list.size();
    }

    public static boolean IsFirstElementInTheListIsLowest(List<Integer> list){
        int i;
        int j;
        for( i = 0 , j = 1; j < list.size() && list.get(i)<=list.get(j) ; j++);
        return j==list.size();
    }
}
