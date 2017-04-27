package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;

/**
 * Created by giulio on 04/04/17.
 */
public interface SimulationDAO {

    /**
     * Gets simulations by contest id.
     *
     * @param contestId  the contest id
     * @param dbExercise the db exercise
     * @return the simulations by contest id
     */
    public ArrayList<Simulation> getSimulationsByContestId(long contestId, SQLiteDatabase dbExercise);

    /**
     * Gets simulation by simulation id.
     *
     * @param simulationId the simulation id
     * @param dbExercise   the db exercise
     * @return the simulation by simulation id
     */
    public Simulation getSimulationBySimulationId(long simulationId, SQLiteDatabase dbExercise);

    /**
     * Insert.
     *
     * @param e  the e
     * @param db the db
     */
    public void insert(Simulation e,SQLiteDatabase db);

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
     * Delete all.
     *
     * @param db the db
     */
    public void deleteAll(SQLiteDatabase db);

    /**
     * Number of rows int.
     *
     * @param db the db
     * @return the int
     */
    public int numberOfRows(SQLiteDatabase db);

    /**
     * Delete all simulation array list.
     *
     * @param contestId  the contest id
     * @param dbExercise the db exercise
     * @return the array list
     */
    public ArrayList<Simulation> deleteAllSimulation(long contestId, SQLiteDatabase dbExercise);

    /**
     * Delete simulation simulation.
     *
     * @param simulationId the simulation id
     * @param dbExercise   the db exercise
     * @return the simulation
     */
    public Simulation deleteSimulation(long simulationId, SQLiteDatabase dbExercise);

}
