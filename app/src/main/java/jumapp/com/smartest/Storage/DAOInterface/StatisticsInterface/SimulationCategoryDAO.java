package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 04/04/17.
 */

public interface SimulationCategoryDAO {

    public ArrayList<SimulationCategory> getCategoriesBySimulationId(long simulationId, SQLiteDatabase dbExercise);
    public void insert(SimulationCategory e,SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();
    public ArrayList<SimulationCategory> deleteAllCategories(long simulationId, SQLiteDatabase dbExercise);
    //public SimulationCategory deleteCategory(long simulationId, String nameCategory, SQLiteDatabase dbExercise);

}
