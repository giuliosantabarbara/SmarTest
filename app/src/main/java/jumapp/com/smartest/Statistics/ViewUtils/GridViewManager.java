package jumapp.com.smartest.Statistics.ViewUtils;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 09/04/17.
 */

public class GridViewManager {


    public static ArrayList<GridType> getGridType (ArrayList<?> arrayList){

        ArrayList<GridType> g = new ArrayList<GridType>();
        if(arrayList.get(0) instanceof Exercise){

            for (int i =0; i<arrayList.size();i++){
                Exercise e = (Exercise)arrayList.get(i);
                GridType t = new GridType(e.getCategoryName(),e.getPercentage());
                g.add(t);
            }
        }
        else if (arrayList.get(0) instanceof SimulationCategory){
            for (int i =0; i<arrayList.size();i++){
                SimulationCategory sc = (SimulationCategory) arrayList.get(i);
                GridType t = new GridType(sc.getCategoryName(),sc.getPercentage());
                g.add(t);
            }
        }
        return g;
    }
}
