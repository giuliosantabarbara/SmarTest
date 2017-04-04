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

    public Simulation(long id_simulation, long id_contest, int day, int month, int year, ArrayList<SimulationCategory> simulationCategories){

        this.id_simulation=id_simulation;
        this.id_contest = id_contest;
        this.day= day;
        this.month=month;
        this.year=year;
        this.simulationCategories=simulationCategories;

    }

    public long getId_contest() {
        return id_contest;
    }

    public long getId_simulation() {
        return id_simulation;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<SimulationCategory> getSimulationCategories() {
        return simulationCategories;
    }
}
