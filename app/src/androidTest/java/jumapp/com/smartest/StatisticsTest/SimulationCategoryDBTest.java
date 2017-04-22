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
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationCategoryDAO;
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
public class SimulationCategoryDBTest {

    private static SimulationCategoryDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new SimulationCategoryDAOImpl(InstrumentationRegistry.getTargetContext());
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
    }

    @After
    public void finish() {
        dbReadable.close();
        dbWritable.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void testInsertSimulationCategory() throws Exception {

        mDataSource.deleteAll(dbWritable);
        SimulationCategory scOne = new SimulationCategory("Matematica",40,60);
        SimulationCategory scTwo = new SimulationCategory("Fisica",50,100);
        mDataSource.insert(scOne,dbWritable);
        mDataSource.insert(scTwo,dbWritable);


        ArrayList<SimulationCategory> simulationCategories = mDataSource.getCategoriesBySimulationId(0,dbReadable);

        assertThat(simulationCategories.size(), is(2));
        assertTrue(simulationCategories.get(0).getCategoryName().equals("Matematica"));
        assertEquals(40, simulationCategories.get(0).getNumAnswered());
        assertEquals(60, simulationCategories.get(0).getTotQuestions());

        assertTrue(simulationCategories.get(1).getCategoryName().equals("Fisica"));
        assertEquals(50, simulationCategories.get(1).getNumAnswered());
        assertEquals(100, simulationCategories.get(1).getTotQuestions());

    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<SimulationCategory> simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);
        assertThat(simulationCategories.size(), is(0));
    }

    @Test
    public void testDeleteAllBySimulationId() {
        mDataSource.deleteAll(dbWritable);
        SimulationCategory exOne = new SimulationCategory(1,"Matematica",5,10);
        mDataSource.insert(exOne,dbWritable);

        ArrayList<SimulationCategory> simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);
        assertThat(simulationCategories.size(), is(1));

        mDataSource.deleteAllCategories(1,dbWritable);
        simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);
        assertThat(simulationCategories.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        SimulationCategory scOne = new SimulationCategory(1,"Matematica",5,10);
        SimulationCategory scTwo = new SimulationCategory(1,"Storia",18,20);
        SimulationCategory scThree = new SimulationCategory(1,"Fisica",13,31);
        SimulationCategory scFour = new SimulationCategory(1,"Geografia",2,5);

        mDataSource.insert(scOne,dbWritable);
        mDataSource.insert(scTwo,dbWritable);
        mDataSource.insert(scThree,dbWritable);
        mDataSource.insert(scFour,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(4));

        mDataSource.deleteAllCategories(1,dbWritable);

        ArrayList<SimulationCategory>simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(0));
        assertThat(simulationCategories.size(), is(0));
    }

    @Test
    public void testData() {
        mDataSource.deleteAll(dbWritable);
        SimulationCategory scOne = new SimulationCategory(1,"Matematica",5,10);
        SimulationCategory scTwo = new SimulationCategory(1,"Storia",18,20);
        SimulationCategory scThree = new SimulationCategory(1,"Fisica",13,31);
        mDataSource.insert(scOne,dbWritable);
        mDataSource.insert(scTwo,dbWritable);
        mDataSource.insert(scThree,dbWritable);

        ArrayList<SimulationCategory>simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);

        assertEquals("Matematica", simulationCategories.get(0).getCategoryName());
        assertEquals("Storia", simulationCategories.get(1).getCategoryName());
        assertEquals("Fisica",simulationCategories.get(2).getCategoryName());

        assertEquals( 5, simulationCategories.get(0).getNumAnswered());
        assertEquals( 18, simulationCategories.get(1).getNumAnswered());
        assertEquals( 13, simulationCategories.get(2).getNumAnswered());

        assertEquals( 10, simulationCategories.get(0).getTotQuestions());
        assertEquals( 20, simulationCategories.get(1).getTotQuestions());
        assertEquals( 31, simulationCategories.get(2).getTotQuestions());
    }

    @Test
    public void testCategoryAndPercentage() {

        mDataSource.deleteAll(dbWritable);
        SimulationCategory scOne = new SimulationCategory(1,"Matematica",5,10);
        SimulationCategory scTwo = new SimulationCategory(1,"Storia",18,20);
        SimulationCategory scThree = new SimulationCategory(1,"Fisica",13,31);
        mDataSource.insert(scOne,dbWritable);
        mDataSource.insert(scTwo,dbWritable);
        mDataSource.insert(scThree,dbWritable);

        ArrayList<SimulationCategory>simulationCategories = mDataSource.getCategoriesBySimulationId(1,dbReadable);

        assertEquals("Matematica", simulationCategories.get(0).getCategoryName());
        assertEquals("Storia", simulationCategories.get(1).getCategoryName());
        assertEquals("Fisica",simulationCategories.get(2).getCategoryName());

        assertEquals(50, simulationCategories.get(0).getPercentage());
        assertEquals(90, simulationCategories.get(1).getPercentage());
        assertEquals(41,simulationCategories.get(2).getPercentage());

    }
}
