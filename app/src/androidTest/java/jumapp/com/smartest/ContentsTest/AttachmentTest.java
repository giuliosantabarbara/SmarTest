package jumapp.com.smartest.ContentsTest;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

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
public class AttachmentTest {



    private static AttachmentDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;


    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new AttachmentDAOImpl(InstrumentationRegistry.getTargetContext());
        /*SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();*/
    }

    @AfterClass
    public static void tearDownAfterClass() {
        SQLiteDatabase db = mDataSource.openWritableConnection();
        mDataSource.deleteAll(db);
        db.close();
    }

    @Before
    public void setUp(){

        dbWritable = mDataSource.openWritableConnection();
        dbReadable = mDataSource.openReadableConnection();
        mDataSource.deleteAll(dbWritable);

        Attachment attOne = new Attachment(1,"fireUrl_1","localUrl_1",1,"Image");
        Attachment attTwo = new Attachment(2,"fireUrl_2","localUrl_2",1,"Image");
        Attachment attThree = new Attachment(3,"fireUrl_3","localUrl_3",2,"Image");
        Attachment attFour = new Attachment(4,"fireUrl_4","localUrl_4",1,"Text");
        Attachment attFive = new Attachment(5,"fireUrl_5","localUrl_5",3,"Text");

        mDataSource.insert(attOne,dbWritable);
        mDataSource.insert(attTwo,dbWritable);
        mDataSource.insert(attThree,dbWritable);
        mDataSource.insert(attFour,dbWritable);
        mDataSource.insert(attFive,dbWritable);
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
    public void testInsertContests() throws Exception {

        ArrayList<Attachment> attachments = mDataSource.getAllAttachments(dbReadable);
        assertThat(attachments.size(), is(5));
    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Attachment> attachments = mDataSource.getAllAttachments(dbReadable);
        assertThat(attachments.size(), is(0));
    }


    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        Attachment attFour = new Attachment(4,"fireUrl_4","localUrl_4",1,"Text");
        mDataSource.insert(attFour,dbWritable);

        int numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(1));

        mDataSource.deleteAll(dbWritable);
        numRow = mDataSource.numberOfRows(dbReadable);
        assertThat(numRow, is(0));

    }

    @Test
    public void testData() {

        ArrayList<Attachment> attachments = mDataSource.getAllAttachments(dbReadable);

        Attachment attachment = attachments.get(0);
        assertTrue(attachment.getAttachment_id()==1);
        assertEquals("fireUrl_1", attachment.getFireurl());
        assertEquals("localUrl_1", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

        attachment = attachments.get(1);
        assertTrue(attachment.getAttachment_id()==2);
        assertEquals("fireUrl_2", attachment.getFireurl());
        assertEquals("localUrl_2", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

        attachment = attachments.get(2);
        assertTrue(attachment.getAttachment_id()==3);
        assertEquals("fireUrl_3", attachment.getFireurl());
        assertEquals("localUrl_3", attachment.getLocalurl());
        assertEquals(2, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

        attachment = attachments.get(3);
        assertTrue(attachment.getAttachment_id()==4);
        assertEquals("fireUrl_4", attachment.getFireurl());
        assertEquals("localUrl_4", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Text", attachment.getType());

        attachment = attachments.get(4);
        assertTrue(attachment.getAttachment_id()==5);
        assertEquals("fireUrl_5", attachment.getFireurl());
        assertEquals("localUrl_5", attachment.getLocalurl());
        assertEquals(3, attachment.getLink_id());
        assertEquals("Text", attachment.getType());

    }

    @Test
    public void testGetAttachmentById(){
        Attachment attachment = mDataSource.getAttachmentById(3, dbReadable);
        assertNotNull(attachment);
        assertTrue(attachment.getAttachment_id()==3);
        assertEquals("fireUrl_3", attachment.getFireurl());
        assertEquals("localUrl_3", attachment.getLocalurl());
        assertEquals(2, attachment.getLink_id());
        assertEquals("Image", attachment.getType());
    }

    @Test
    public void testGetScopes(){
        ArrayList<Attachment> attachments = mDataSource.getAllAttachmentsByLinkId(1,"Image",dbReadable);
        assertThat(attachments.size(), is(2));

        Attachment attachment = attachments.get(0);
        assertTrue(attachment.getAttachment_id()==1);
        assertEquals("fireUrl_1", attachment.getFireurl());
        assertEquals("localUrl_1", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

        attachment = attachments.get(1);
        assertTrue(attachment.getAttachment_id()==2);
        assertEquals("fireUrl_2", attachment.getFireurl());
        assertEquals("localUrl_2", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

    }


    @Test
    public void testDeleteById(){
        Attachment attachment = mDataSource.deleteAttachment(2, dbWritable);

        assertTrue(attachment.getAttachment_id()==2);
        assertEquals("fireUrl_2", attachment.getFireurl());
        assertEquals("localUrl_2", attachment.getLocalurl());
        assertEquals(1, attachment.getLink_id());
        assertEquals("Image", attachment.getType());

        //introduced because of "re-open" problem
        SQLiteDatabase con = mDataSource.openReadableConnection();
        ArrayList<Attachment> attachments = mDataSource.getAllAttachments(con);
        con.close();
        assertThat(attachments.size(), is(4));

    }
}
