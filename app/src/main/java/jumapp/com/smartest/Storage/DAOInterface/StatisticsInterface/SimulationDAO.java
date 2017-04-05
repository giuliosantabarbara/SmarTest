package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;

/**
 * Created by giulio on 04/04/17.
 */

public interface SimulationDAO {

    public ArrayList<Simulation> getSimulationsByContestId(long contestId, SQLiteDatabase dbExercise);
    public Simulation getSimulationBySimulationId(long simulationId, SQLiteDatabase dbExercise);
    public void insert(Simulation e,SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();


    public ArrayList<Simulation> deleteAllSimulation(long contestId, SQLiteDatabase dbExercise);
    public Simulation deleteSimulation(long simulationId, SQLiteDatabase dbExercise);

}
