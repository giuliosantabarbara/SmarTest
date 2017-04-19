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

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AlternativeDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;
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
public class AlternativeTest {

    private static AlternativeDAO mDataSource;
    private static AttachmentDAO attachmentDAO;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new AlternativeDAOImpl(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();

        attachmentDAO = new AttachmentDAOImpl(InstrumentationRegistry.getTargetContext());
        Attachment attOne = new Attachment(1,"fireUrl_1","localUrl_1",1,"alternative");
        Attachment attTwo = new Attachment(2,"fireUrl_2","localUrl_2",1,"alternative");
        Attachment attThree = new Attachment(3,"fireUrl_3","localUrl_3",2,"alternative");
        Attachment attFour = new Attachment(4,"fireUrl_4","localUrl_4",1,"alternative");
        Attachment attFive = new Attachment(5,"fireUrl_5","localUrl_5",3,"alternative");

        SQLiteDatabase conn = attachmentDAO.openWritableConnection();
        attachmentDAO.deleteAll(conn);
        attachmentDAO.insert(attOne,conn);
        attachmentDAO.insert(attTwo,conn);
        attachmentDAO.insert(attThree,conn);
        attachmentDAO.insert(attFour,conn);
        attachmentDAO.insert(attFive,conn);
        conn.close();

    }

    @AfterClass
    public static void tearDownAfterClass() {
        SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();

        SQLiteDatabase conn = attachmentDAO.openWritableConnection();
        attachmentDAO.deleteAll(conn);
        conn.close();
    }

    @Before
    public void setUp(){
        dbWritable= mDataSource.openWritableConnection();
        dbReadable = mDataSource.openReadableConnection();

        SQLiteDatabase conn = attachmentDAO.openReadableConnection();
        ArrayList<Attachment> attachments = attachmentDAO.getAllAttachments(conn);
        conn.close();

        Alternative altOne = new Alternative(1,"Alt_1",false,1,null,true);
        Alternative altTwo = new Alternative(2,"Alt_2",false,1,null,true);
        Alternative altThree = new Alternative(3,"Alt_3",true,1,null,true);
        Alternative altFour = new Alternative(4,"Alt_4",false,1,null,false);
        Alternative altFive = new Alternative(5,"Alt_1",true,2,null,false);
        Alternative altSix = new Alternative(6,"Alt_2",false,2,null,false);

        mDataSource.insert(altOne,dbWritable);
        mDataSource.insert(altTwo,dbWritable);
        mDataSource.insert(altThree,dbWritable);
        mDataSource.insert(altFour,dbWritable);
        mDataSource.insert(altFive,dbWritable);
        mDataSource.insert(altSix,dbWritable);


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


        ArrayList<Alternative> alternatives = mDataSource.getAllAlternatives(dbReadable);
        assertThat(alternatives.size(), is(6));
    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Alternative> alternatives = mDataSource.getAllAlternatives(dbReadable);
        assertThat(alternatives.size(), is(0));
    }


    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        Alternative altFive = new Alternative(5,"Alt_1",false,2,null,false);
        mDataSource.insert(altFive,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(1));

        mDataSource.deleteAll(dbWritable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(0));

    }

    @Test
    public void testData() {

        SQLiteDatabase conn = attachmentDAO.openReadableConnection();
        ArrayList<Attachment> attachments = attachmentDAO.getAllAttachments(conn);


        ArrayList<Alternative> alternatives = mDataSource.getAllAlternatives(dbReadable);

        Alternative alternative = alternatives.get(0);
        assertTrue(alternative.getAlternative_id()==1);
        assertEquals("Alt_1", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());
        ArrayList<Attachment> attachmentsOne = attachmentDAO.getAllAttachmentsByLinkId(1,"alternative",conn);
        assertThat(attachmentsOne.size(), is(3));

        alternative = alternatives.get(1);
        assertTrue(alternative.getAlternative_id()==2);
        assertEquals("Alt_2", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());
        ArrayList<Attachment> attachmentsTwo = attachmentDAO.getAllAttachmentsByLinkId(2,"alternative",conn);
        assertThat(attachmentsTwo.size(), is(1));

        alternative = alternatives.get(2);
        assertTrue(alternative.getAlternative_id()==3);
        assertEquals("Alt_3", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(true, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());
        ArrayList<Attachment> attachmentsThree = attachmentDAO.getAllAttachmentsByLinkId(3,"alternative",conn);
        assertThat(attachmentsThree.size(), is(1));

        conn.close();

        alternative = alternatives.get(3);
        assertTrue(alternative.getAlternative_id()==4);
        assertEquals("Alt_4", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());
        assertTrue(alternative.getAttachments().size()==0);


        alternative = alternatives.get(4);
        assertTrue(alternative.getAlternative_id()==5);
        assertEquals("Alt_1", alternative.getText());
        assertEquals(2, alternative.getQuestion_id());
        assertEquals(true, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());
        assertTrue(alternative.getAttachments().size()==0);

        alternative = alternatives.get(5);
        assertTrue(alternative.getAlternative_id()==6);
        assertEquals("Alt_2", alternative.getText());
        assertEquals(2, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());
        assertTrue(alternative.getAttachments().size()==0);
    }

    @Test
    public void testGetAlternativesByQuestionId(){
        SQLiteDatabase conn = attachmentDAO.openReadableConnection();
        ArrayList<Alternative> alternatives = mDataSource.getAllAlternativesByQuestionId(1,dbReadable,conn);
        assertThat(alternatives.size(), is(4));

        Alternative alternative = alternatives.get(0);
        assertTrue(alternative.getAlternative_id()==1);
        assertEquals("Alt_1", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());

        alternative = alternatives.get(1);
        assertTrue(alternative.getAlternative_id()==2);
        assertEquals("Alt_2", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());

        alternative = alternatives.get(2);
        assertTrue(alternative.getAlternative_id()==3);
        assertEquals("Alt_3", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(true, alternative.getRight());
        assertEquals(true, alternative.getHasAttach());
        alternative = alternatives.get(3);
        assertTrue(alternative.getAlternative_id()==4);
        assertEquals("Alt_4", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());

        conn.close();
    }

    @Test
    public void testGetAlternativeById(){
        Alternative alternative = mDataSource.getAlternativeById(4,dbReadable);
        assertTrue(alternative.getAlternative_id()==4);
        assertEquals("Alt_4", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());
        assertTrue(alternative.getAttachments().size()==0);
    }

    @Test
    public void testDeleteAlternativeByID(){
        Alternative alternative = mDataSource.deleteAlterantive(4,dbReadable);
        ArrayList<Alternative> alternatives = mDataSource.getAllAlternatives(dbReadable);
        assertThat(alternatives.size(), is(5));
        assertTrue(alternative.getAlternative_id()==4);
        assertEquals("Alt_4", alternative.getText());
        assertEquals(1, alternative.getQuestion_id());
        assertEquals(false, alternative.getRight());
        assertEquals(false, alternative.getHasAttach());
        assertTrue(alternative.getAttachments().size()==0);
    }

}
