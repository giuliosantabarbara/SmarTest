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

import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.ExerciseDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by giulio on 14/04/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExerciseDBTest {

    private static ExerciseDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new ExerciseDAOImpl(InstrumentationRegistry.getTargetContext());
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
    public void testInsertExerxise() throws Exception {

        Exercise exOne = new Exercise(1,"Matematica",5,10);
        Exercise exTwo = new Exercise(1,"Storia",18,20);
        mDataSource.insert(exOne,dbWritable);
        mDataSource.insert(exTwo,dbWritable);


        ArrayList<Exercise> exercises = mDataSource.getExerciseByContestId(1,dbReadable);

        assertThat(exercises.size(), is(2));
        assertTrue(exercises.get(0).getCategoryName().equals("Matematica"));
        assertTrue(exercises.get(0).getId_contest()==1);
        assertEquals(5, exercises.get(0).getNumAnswered());
        assertEquals(10, exercises.get(0).getTotQuestions());

        assertTrue(exercises.get(1).getCategoryName().equals("Storia"));
        assertTrue(exercises.get(1).getId_contest()==1);
        assertEquals(18, exercises.get(1).getNumAnswered());
        assertEquals(20, exercises.get(1).getTotQuestions());

    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Exercise> exercises = mDataSource.getExerciseByContestId(1,dbReadable);
        assertThat(exercises.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {

        Exercise exOne = new Exercise(1,"Matematica",5,10);
        mDataSource.insert(exOne,dbWritable);

        ArrayList<Exercise> exercises = mDataSource.getExerciseByContestId(1,dbReadable);
        assertThat(exercises.size(), is(1));

        mDataSource.deleteExerciseByContestId(1,dbWritable);
        exercises = mDataSource.getExerciseByContestId(1,dbReadable);
        assertThat(exercises.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        Exercise exOne = new Exercise(1,"Matematica",5,10);
        Exercise exTwo = new Exercise(1,"Storia",18,20);
        Exercise exThree = new Exercise(1,"Fisica",13,31);
        Exercise exFour = new Exercise(2,"Geografia",2,5);

        mDataSource.insert(exOne,dbWritable);
        mDataSource.insert(exTwo,dbWritable);
        mDataSource.insert(exThree,dbWritable);
        mDataSource.insert(exFour,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(4));

        mDataSource.deleteExerciseByContestId(1,dbWritable);

        ArrayList<Exercise>exercises = mDataSource.getExerciseByContestId(2,dbReadable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(1));
        assertThat(exercises.size(), is(1));
    }

    @Test
    public void testData() {
        mDataSource.deleteAll(dbWritable);
        Exercise exOne = new Exercise(1,"Matematica",5,10);
        Exercise exTwo = new Exercise(1,"Storia",18,20);
        Exercise exThree = new Exercise(1,"Fisica",13,31);
        mDataSource.insert(exOne,dbWritable);
        mDataSource.insert(exTwo,dbWritable);
        mDataSource.insert(exThree,dbWritable);

        ArrayList<Exercise>exercises = mDataSource.getExerciseByContestId(1,dbReadable);

        assertEquals("Matematica", exercises.get(0).getCategoryName());
        assertEquals("Storia", exercises.get(1).getCategoryName());
        assertEquals("Fisica",exercises.get(2).getCategoryName());

        assertEquals( 5, exercises.get(0).getNumAnswered());
        assertEquals( 18, exercises.get(1).getNumAnswered());
        assertEquals( 13, exercises.get(2).getNumAnswered());

        assertEquals( 10, exercises.get(0).getTotQuestions());
        assertEquals( 20, exercises.get(1).getTotQuestions());
        assertEquals( 31, exercises.get(2).getTotQuestions());
    }

    @Test
    public void testCategoryAndPercentage() {

        mDataSource.deleteAll(dbWritable);
        Exercise exOne = new Exercise(1,"Matematica",5,10);
        Exercise exTwo = new Exercise(1,"Storia",18,20);
        Exercise exThree = new Exercise(1,"Fisica",13,31);
        mDataSource.insert(exOne,dbWritable);
        mDataSource.insert(exTwo,dbWritable);
        mDataSource.insert(exThree,dbWritable);

        ArrayList<String>categories = mDataSource.getAllCategoriesByContestId(1,dbReadable);
        ArrayList<Exercise> exercises = mDataSource.getExerciseByContestId(1,dbReadable);

        //alphabetic order
        assertEquals("Matematica", categories.get(1));
        assertEquals("Storia", categories.get(2));
        assertEquals("Fisica",categories.get(0));

        assertEquals(50, exercises.get(0).getPercentage());
        assertEquals(90, exercises.get(1).getPercentage());
        assertEquals(41,exercises.get(2).getPercentage());

    }
}
