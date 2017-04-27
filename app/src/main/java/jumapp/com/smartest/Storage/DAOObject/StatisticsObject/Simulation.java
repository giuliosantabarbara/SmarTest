package jumapp.com.smartest.Storage.DAOObject.StatisticsObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by giulio on 04/04/17.
 */

public class Simulation {


    private long id_contest;
    private long id_simulation;
    private ArrayList<SimulationCategory> simulationCategories;
    private int day, month, year;

    /**
     * Instantiates a new Simulation.
     *
     * @param id_simulation        the id simulation
     * @param id_contest           the id contest
     * @param day                  the day
     * @param month                the month
     * @param year                 the year
     * @param simulationCategories the simulation categories
     */
    public Simulation(long id_simulation, long id_contest, int day, int month, int year, ArrayList<SimulationCategory> simulationCategories){

        this.id_simulation=id_simulation;
        this.id_contest = id_contest;
        this.day= day;
        this.month=month;
        this.year=year;
        this.simulationCategories=simulationCategories;

    }

    /**
     * Instantiates a new Simulation.
     *
     * @param id_contest           the id contest
     * @param day                  the day
     * @param month                the month
     * @param year                 the year
     * @param simulationCategories the simulation categories
     */
    public Simulation(long id_contest, int day, int month, int year, ArrayList<SimulationCategory> simulationCategories){

        this.id_contest = id_contest;
        this.day= day;
        this.month=month;
        this.year=year;
        this.simulationCategories=simulationCategories;

    }

    /**
     * Gets id contest.
     *
     * @return the id contest
     */
    public long getId_contest() {
        return id_contest;
    }

    /**
     * Gets id simulation.
     *
     * @return the id simulation
     */
    public long getId_simulation() {
        return id_simulation;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets simulation categories.
     *
     * @return the simulation categories
     */
    public ArrayList<SimulationCategory> getSimulationCategories() {
        return simulationCategories;
    }

}
