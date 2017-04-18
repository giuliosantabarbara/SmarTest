package jumapp.com.smartest.StatisticsTest;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.SimulationCategoryDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.SimulationDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationCategoryDAO;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by giulio on 18/04/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SimulationDBTest {

    private static SimulationDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new SimulationDAOImpl(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();
    }

    @Before
    public void setUp(){
        dbWritable= mDataSource.openWritableConnection();
        dbReadable = mDataSource.openReadableConnection();

        mDataSource.deleteAll(dbWritable);
        SimulationCategoryDAO source = new SimulationCategoryDAOImpl(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase dbn = source.openWritableConnection();
        source.deleteAll(dbn);
        dbn.close();

        ArrayList<SimulationCategory> simulationCategories = new ArrayList<>();
        SimulationCategory scOne = new SimulationCategory("Storia",15,30);
        simulationCategories.add(scOne);
        SimulationCategory scTwo = new SimulationCategory("Matematica",15,15);
        simulationCategories.add(scTwo);
        SimulationCategory scThree = new SimulationCategory("Sintassi",30,50);
        simulationCategories.add(scThree);

        Simulation sOne = new Simulation(1,1,18,4,2017,simulationCategories);

        ArrayList<SimulationCategory> simulationCategoriesTwo = new ArrayList<>();
        SimulationCategory scOneBis = new SimulationCategory("Storia",18,30);
        simulationCategoriesTwo.add(scOneBis);
        SimulationCategory scTwoBis = new SimulationCategory("Matematica",10,15);
        simulationCategoriesTwo.add(scTwoBis);
        SimulationCategory scThreeBis = new SimulationCategory("Sintassi",43,50);
        simulationCategoriesTwo.add(scThreeBis);

        Simulation sTwo = new Simulation(2,1,10,4,2017, simulationCategoriesTwo);

        mDataSource.insert(sOne,dbWritable);
        mDataSource.insert(sTwo,dbWritable);
    }

    @After
    public void finish() {

        mDataSource.deleteAll(dbWritable);
        dbReadable.close();
        dbWritable.close();

    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void testInsertSimulation() throws Exception {


        ArrayList<Simulation> simulations = mDataSource.getSimulationsByContestId(1,dbReadable);
        assertThat(simulations.size(), is(2));
    }

    @Test
    public void testData() {

        ArrayList<Simulation> simulations = mDataSource.getSimulationsByContestId(1,dbReadable);
        assertThat(simulations.size(), is(2));

        assertTrue(simulations.get(0).getId_contest()==1);
        assertEquals(18, simulations.get(0).getDay());
        assertEquals(4, simulations.get(0).getMonth());
        assertEquals(2017, simulations.get(0).getYear());
        assertEquals(1, simulations.get(0).getId_simulation());
        assertNotNull(simulations.get(0).getSimulationCategories());

        assertTrue(simulations.get(1).getId_contest()==1);
        assertEquals(10, simulations.get(1).getDay());
        assertEquals(4, simulations.get(1).getMonth());
        assertEquals(2017, simulations.get(1).getYear());
        assertEquals(2, simulations.get(1).getId_simulation());
        assertNotNull(simulations.get(1).getSimulationCategories());

    }

    @Test
    public void testCategoryAndPercentage() {

        Simulation simulation = mDataSource.getSimulationBySimulationId(1,dbReadable);
        assertNotNull(simulation);

        ArrayList<SimulationCategory> retrieveFirstSc = simulation.getSimulationCategories();
        assertThat(retrieveFirstSc.size(), is(3));

        SimulationCategory first = retrieveFirstSc.get(0);
        assertEquals(1, first.getId_simulation());
        assertEquals("Storia", first.getCategoryName());
        assertEquals(15, first.getNumAnswered());
        assertEquals(30, first.getTotQuestions());
        assertEquals(50, first.getPercentage());

        first = retrieveFirstSc.get(1);
        assertEquals(1, first.getId_simulation());
        assertEquals("Matematica",first.getCategoryName());
        assertEquals(15, first.getNumAnswered());
        assertEquals(15, first.getTotQuestions());
        assertEquals(100, first.getPercentage());

        first = retrieveFirstSc.get(2);
        assertEquals(1, first.getId_simulation());
        assertEquals("Sintassi", first.getCategoryName());
        assertEquals(30, first.getNumAnswered());
        assertEquals(50, first.getTotQuestions());
        assertEquals(60, first.getPercentage());


        simulation = mDataSource.getSimulationBySimulationId(2,dbReadable);
        assertNotNull(simulation);

        ArrayList<SimulationCategory> retrieveSecondSc = simulation.getSimulationCategories();
        assertThat(retrieveFirstSc.size(), is(3));

        SimulationCategory second = retrieveSecondSc.get(0);
        assertEquals(2, second.getId_simulation());
        assertEquals("Storia",second.getCategoryName());
        assertEquals(18, second.getNumAnswered(), 18);
        assertEquals(30,second.getTotQuestions());
        assertEquals(60,second.getPercentage());

        second = retrieveSecondSc.get(1);
        assertEquals(2,second.getId_simulation());
        assertEquals("Matematica",second.getCategoryName());
        assertEquals(10,second.getNumAnswered());
        assertEquals(15,second.getTotQuestions());
        assertEquals(66,second.getPercentage());

        second = retrieveSecondSc.get(2);
        assertEquals(2,second.getId_simulation());
        assertEquals("Sintassi",second.getCategoryName());
        assertEquals(43,second.getNumAnswered());
        assertEquals(50,second.getTotQuestions());
        assertEquals(86,second.getPercentage());

    }


    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Simulation> simulations = mDataSource.getSimulationsByContestId(1,dbReadable);
        assertThat(simulations.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {


        ArrayList<Simulation> simulations = mDataSource.getSimulationsByContestId(1,dbReadable);
        assertThat(simulations.size(), is(2));

        mDataSource.deleteSimulation(2,dbWritable);
        simulations = mDataSource.getSimulationsByContestId(1,dbReadable);
        assertThat(simulations.size(), is(1));
    }

    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        ArrayList<SimulationCategory> simulationCategories = new ArrayList<>();
        SimulationCategory scOne = new SimulationCategory("Storia",15,30);
        simulationCategories.add(scOne);
        SimulationCategory scTwo = new SimulationCategory("Matematica",15,15);
        simulationCategories.add(scTwo);
        SimulationCategory scThree = new SimulationCategory("Sintassi",30,50);
        simulationCategories.add(scThree);

        Simulation simOne = new Simulation(1,22,3,2016,simulationCategories);
        Simulation simTwo = new Simulation(2,22,3,2016,simulationCategories);
        mDataSource.insert(simOne,dbWritable);
        mDataSource.insert(simTwo,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(2));

        mDataSource.deleteAllSimulation(1,dbWritable);

        ArrayList<Simulation>simulations = mDataSource.getSimulationsByContestId(2,dbReadable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(1));
        assertThat(simulations.size(), is(1));
    }
}