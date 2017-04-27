package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 04/04/17.
 */
public interface SimulationCategoryDAO {

    /**
     * Gets categories by simulation id.
     *
     * @param simulationId the simulation id
     * @param dbExercise   the db exercise
     * @return the categories by simulation id
     */
    public ArrayList<SimulationCategory> getCategoriesBySimulationId(long simulationId, SQLiteDatabase dbExercise);

    /**
     * Insert.
     *
     * @param e  the e
     * @param db the db
     */
    public void insert(SimulationCategory e,SQLiteDatabase db);

    /**
     * Open writable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openWritableConnection();

    /**
     * Open readable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openReadableConnection();

    /**
     * Number of rows int.
     *
     * @param db the db
     * @return the int
     */
    public int numberOfRows(SQLiteDatabase db);

    /**
     * Delete all.
     *
     * @param dbN the db n
     */
    public void deleteAll(SQLiteDatabase dbN);

    /**
     * Delete all categories array list.
     *
     * @param simulationId the simulation id
     * @param dbExercise   the db exercise
     * @return the array list
     */
    public ArrayList<SimulationCategory> deleteAllCategories(long simulationId, SQLiteDatabase dbExercise);
    //public SimulationCategory deleteCategory(long simulationId, String nameCategory, SQLiteDatabase dbExercise);

}
