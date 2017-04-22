package jumapp.com.smartest.ContentsTest;

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

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by giulio on 19/04/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContestTest {


    private static ContestDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new ContestDAOImpl(InstrumentationRegistry.getTargetContext());
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

        Contest cOne = new Contest(1,"Esercito","Gdf","Allievi","questionUrl_1","attachUrl_1","alternativeUrl_1","announcementUrl_1",2016,"shortDescription_1");
        Contest cTwo = new Contest(2,"Esercito","Marina","Ufficiali","questionUrl_2","attachUrl_2","alternativeUrl_2","announcementUrl_2",2015,"shortDescription_2");
        Contest cThree = new Contest(3,"Esercito","Gdf","Marescialli","questionUrl_3","attachUrl_3","alternativeUrl_3","announcementUrl_3",2017,"shortDescription_3");
        Contest cFour = new Contest(4,"Esercito","Carabinieri","Marescialli","questionUrl_4","attachUrl_4","alternativeUrl_4","announcementUrl_4",2017,"shortDescription_4");
        mDataSource.insert(cOne,dbWritable);
        mDataSource.insert(cTwo,dbWritable);
        mDataSource.insert(cThree,dbWritable);
        mDataSource.insert(cFour,dbWritable);
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
    public void testInsertContests() throws Exception {

        ArrayList<Contest> contests = mDataSource.getAllContests(dbReadable);
        assertThat(contests.size(), is(4));
    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Contest> contests = mDataSource.getAllContests(dbReadable);
        assertThat(contests.size(), is(0));
    }


    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        Contest cFour = new Contest(4,"Esercito","Carabinieri","Marescialli","questionUrl_4","attachUrl_4","alternativeUrl_4","announcementUrl_4",2017,"shortDescription_4");
        mDataSource.insert(cFour,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(1));

        mDataSource.deleteAll(dbWritable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(0));

    }

    @Test
    public void testData() {

        ArrayList<Contest> contests = mDataSource.getAllContests(dbReadable);
        Contest contest = contests.get(0);
        assertTrue(contest.getId_contest()==1);
        assertEquals("Esercito", contest.getType());
        assertEquals("Gdf", contest.getScope());
        assertEquals("Allievi", contest.getPosition());
        assertEquals("questionUrl_1", contest.getQuestionsURL());
        assertEquals("alternativeUrl_1", contest.getAlternativesURL());
        assertEquals("attachUrl_1", contest.getAttachmentsURL());
        assertEquals("announcementUrl_1", contest.getAnnouncementURL());
        assertEquals("shortDescription_1", contest.getShortDescription());
        assertEquals(2016, contest.getYear());

        contest = contests.get(1);
        assertTrue(contest.getId_contest()==2);
        assertEquals("Esercito", contest.getType());
        assertEquals("Marina", contest.getScope());
        assertEquals("Ufficiali", contest.getPosition());
        assertEquals("questionUrl_2", contest.getQuestionsURL());
        assertEquals("alternativeUrl_2", contest.getAlternativesURL());
        assertEquals("attachUrl_2", contest.getAttachmentsURL());
        assertEquals("announcementUrl_2", contest.getAnnouncementURL());
        assertEquals("shortDescription_2", contest.getShortDescription());
        assertEquals(2015, contest.getYear());

        contest = contests.get(2);
        assertTrue(contest.getId_contest()==3);
        assertEquals("Esercito", contest.getType());
        assertEquals("Gdf", contest.getScope());
        assertEquals("Marescialli", contest.getPosition());
        assertEquals("questionUrl_3", contest.getQuestionsURL());
        assertEquals("alternativeUrl_3", contest.getAlternativesURL());
        assertEquals("attachUrl_3", contest.getAttachmentsURL());
        assertEquals("announcementUrl_3", contest.getAnnouncementURL());
        assertEquals("shortDescription_3", contest.getShortDescription());
        assertEquals(2017, contest.getYear());

        contest = contests.get(3);
        assertTrue(contest.getId_contest()==4);
        assertEquals("Esercito", contest.getType());
        assertEquals("Carabinieri", contest.getScope());
        assertEquals("Marescialli", contest.getPosition());
        assertEquals("questionUrl_4", contest.getQuestionsURL());
        assertEquals("alternativeUrl_4", contest.getAlternativesURL());
        assertEquals("attachUrl_4", contest.getAttachmentsURL());
        assertEquals("announcementUrl_4", contest.getAnnouncementURL());
        assertEquals("shortDescription_4", contest.getShortDescription());
        assertEquals(2017, contest.getYear());
    }

    @Test
    public void testGetPositions(){
        ArrayList<String> positions = mDataSource.getAllContestsPositionByScope("Gdf",dbReadable);
        assertThat(positions.size(), is(2));
        assertEquals("Allievi", positions.get(0));
        assertEquals("Marescialli", positions.get(1));
    }

    @Test
    public void testGetScopes(){
        ArrayList<String> scopes = mDataSource.getAllContestsScopes(dbReadable);
        assertThat(scopes.size(), is(4));
        assertEquals("Gdf", scopes.get(0));
        assertEquals("Marina", scopes.get(1));
        assertEquals("Gdf", scopes.get(2));
        assertEquals("Carabinieri", scopes.get(3));
    }

    @Test
    public void testGetYears(){
        ArrayList<String> years = mDataSource.getAllContestsYearsByScopeAndPosition("Gdf","Marescialli",dbReadable);
        assertThat(years.size(), is(1));
        assertEquals("2017", years.get(0));
    }

    @Test
    public void testGetContestByScopePositionYear(){
        Contest contest = mDataSource.getContestByScopePositionYear("Gdf","Marescialli",2017, dbReadable);
        assertNotNull(contest);

        assertTrue(contest.getId_contest()==3);
        assertEquals("Esercito", contest.getType());
        assertEquals("Gdf", contest.getScope());
        assertEquals("Marescialli", contest.getPosition());
        assertEquals("questionUrl_3", contest.getQuestionsURL());
        assertEquals("alternativeUrl_3", contest.getAlternativesURL());
        assertEquals("attachUrl_3", contest.getAttachmentsURL());
        assertEquals("announcementUrl_3", contest.getAnnouncementURL());
        assertEquals("shortDescription_3", contest.getShortDescription());
        assertEquals(2017, contest.getYear());
    }

    @Test
    public void testGetContestById(){
        Contest contest = mDataSource.getContestById(2,dbReadable);
        assertNotNull(contest);

        assertTrue(contest.getId_contest()==2);
        assertEquals("Esercito", contest.getType());
        assertEquals("Marina", contest.getScope());
        assertEquals("Ufficiali", contest.getPosition());
        assertEquals("questionUrl_2", contest.getQuestionsURL());
        assertEquals("alternativeUrl_2", contest.getAlternativesURL());
        assertEquals("attachUrl_2", contest.getAttachmentsURL());
        assertEquals("announcementUrl_2", contest.getAnnouncementURL());
        assertEquals("shortDescription_2", contest.getShortDescription());
        assertEquals(2015, contest.getYear());
    }
}
