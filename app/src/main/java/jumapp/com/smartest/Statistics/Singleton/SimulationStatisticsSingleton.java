package jumapp.com.smartest.Statistics.Singleton;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;

/**
 * Created by giulio on 08/04/17.
 */
public class SimulationStatisticsSingleton {


    private static SimulationStatisticsSingleton mInstance;
    private static SimulationDAO simulationDao;
    private long contestId;

    private static ArrayList<Simulation> simulations= new ArrayList<Simulation>();

    /**
     * Instantiates a new Simulation statistics singleton.
     *
     * @param simulationDao the simulation dao
     * @param contestId     the contest id
     */
    protected SimulationStatisticsSingleton(SimulationDAO simulationDao, long contestId){

        this.simulationDao = simulationDao;
        this.contestId = contestId;
        SQLiteDatabase conn = simulationDao.openReadableConnection();
        simulations = simulationDao.getSimulationsByContestId(contestId,conn);
        conn.close();

    }

    /**
     * Get instance simulation statistics singleton.
     *
     * @param simulationDao the simulation dao
     * @param contestId     the contest id
     * @return the simulation statistics singleton
     */
    public static SimulationStatisticsSingleton getInstance(SimulationDAO simulationDao, long contestId){
        if(null == mInstance){
            mInstance = new SimulationStatisticsSingleton(simulationDao, contestId);
        }
        return mInstance;
    }

    /**
     * Gets simulations.
     *
     * @return the simulations
     */
    public ArrayList<Simulation> getSimulations() {
        return simulations;
    }

    /**
     * Get simulation by index simulation.
     *
     * @param index the index
     * @return the simulation
     */
    public Simulation getSimulationByIndex(int index){
        return simulations.get(index);
    }

    /**
     * Sets simulations.
     *
     * @param simulations the simulations
     */
    public void setSimulations(ArrayList<Simulation> simulations) {

        this.simulations = simulations;

    }

    /**
     * Sets simulation.
     *
     * @param simulation the simulation
     */
    public static void setSimulation(Simulation simulation) {
        simulations.add(simulation);
        SQLiteDatabase conn = simulationDao.openWritableConnection();
        simulationDao.insert(simulation,conn);
        conn.close();
    }

    /*public int getPercentageByIndexAndSimulatioId(int index, long simulationId){
        int tot =0;
        ArrayList <SimulationCategory> sc = simulations.get(index).getSimulationCategories();
        for(SimulationCategory s : sc){

            tot+=s.getPercentage();
        }
        return tot/sc.size();
    }*/

}
