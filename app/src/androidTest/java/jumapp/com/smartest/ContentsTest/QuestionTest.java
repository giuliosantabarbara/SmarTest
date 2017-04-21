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

import jumapp.com.smartest.QuestionsHashMap;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AlternativeDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by giulio on 21/04/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuestionTest {

    private static QuestionDAO mDataSource;
    private SQLiteDatabase dbWritable, dbReadable;
    private static AttachmentDAO attachmentDAO;
    private static AlternativeDAO alternativeDAO;

    @BeforeClass
    public static void setUpBeforeClass(){
        mDataSource = new QuestionDAOImpl(InstrumentationRegistry.getTargetContext());
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


        attachmentDAO = new AttachmentDAOImpl(InstrumentationRegistry.getTargetContext());
        Attachment attOne = new Attachment(1,"fireUrl_1","localUrl_1",3,"question");
        SQLiteDatabase conn = attachmentDAO.openWritableConnection();
        attachmentDAO.deleteAll(conn);
        attachmentDAO.insert(attOne,conn);
        //conn.close();


        alternativeDAO = new AlternativeDAOImpl(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase connAlt = alternativeDAO.openWritableConnection();
        alternativeDAO.deleteAll(conn);
        Alternative altOne = new Alternative(1,"Alt_1",false,1,null,false);
        Alternative altTwo = new Alternative(2,"Alt_2",false,1,null,false);
        Alternative altThree = new Alternative(3,"Alt_3",true,1,null,false);
        Alternative altFour = new Alternative(4,"Alt_4",false,1,null,false);
        Alternative altFive = new Alternative(5,"Alt_1",true,2,null,false);
        Alternative altSix = new Alternative(6,"Alt_2",false,2,null,false);

        alternativeDAO.insert(altOne,connAlt);
        alternativeDAO.insert(altTwo,connAlt);
        alternativeDAO.insert(altThree,connAlt);
        alternativeDAO.insert(altFour,connAlt);
        alternativeDAO.insert(altFive,connAlt);
        alternativeDAO.insert(altSix,connAlt);
        //connAlt.close();

        Question qOne = new Question(1,"Storia","Testo_1",false,false,1,false);
        Question qTwo = new Question(2,"Matematica","Testo_2",false,false,1,false);
        Question qThree = new Question(3,"Storia","Testo_3",false,false,1,true);
        Question qFour = new Question(4,"Storia","Testo_4",false,true,2,false);
        Question qFive = new Question(5,"Sintassi","Testo_5",true,false,2,false);
        Question qSix = new Question(6,"Matematica","Testo_6",true,true,1,false);

        mDataSource.insert(qOne,dbWritable);
        mDataSource.insert(qTwo,dbWritable);
        mDataSource.insert(qThree,dbWritable);
        mDataSource.insert(qFour,dbWritable);
        mDataSource.insert(qFive,dbWritable);
        mDataSource.insert(qSix,dbWritable);
    }

    @After
    public void finish() {
        mDataSource.deleteAll(dbWritable);


        SQLiteDatabase conn = attachmentDAO.openWritableConnection();
        attachmentDAO.deleteAll(conn);
        conn.close();

        SQLiteDatabase connAlt = alternativeDAO.openWritableConnection();
        alternativeDAO.deleteAll(connAlt);
        connAlt.close();

        dbReadable.close();
        dbWritable.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void testInsertQuestions() throws Exception {

        ArrayList<Question> questions = mDataSource.getAllQuestions(dbReadable);
        assertThat(questions.size(), is(6));
    }

    @Test
    public void testDeleteAll() {
        mDataSource.deleteAll(dbWritable);
        ArrayList<Question> questions = mDataSource.getAllQuestions(dbReadable);
        assertThat(questions.size(), is(0));
    }


    @Test
    public void testAddAndDelete() {
        mDataSource.deleteAll(dbWritable);

        Question qFour = new Question(4,"Storia","Testo_4",false,true,2,false);
        mDataSource.insert(qFour,dbWritable);

        int numRow = mDataSource.numberOfRowsByContest(2,dbReadable);
        assertThat(numRow, is(1));

        mDataSource.deleteAll(dbWritable);
        numRow = mDataSource.numberOfRowsByContest(2,dbReadable);
        assertThat(numRow, is(0));

    }

    @Test
    public void testData() {

        ArrayList<Question> questions = mDataSource.getAllQuestions(dbReadable);

        Question question = questions.get(0);
        assertTrue(question.getQuestion_id()==1);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_1", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());
        assertEquals("Alt_1", question.getAlternatives().get(0).getText());
        assertThat(question.getAlternatives().size(),is(4));

        question = questions.get(1);
        assertTrue(question.getQuestion_id()==2);
        assertEquals("Matematica", question.getCategory());
        assertEquals("Testo_2", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());
        assertEquals("Alt_2", question.getAlternatives().get(1).getText());
        assertThat(question.getAlternatives().size(),is(2));

        question = questions.get(2);
        assertTrue(question.getQuestion_id()==3);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_3", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(true, question.getHasAttach());
        assertEquals("fireUrl_1", question.getAttachments().get(0).getFireurl());
        assertThat(question.getAttachments().size(),is(1));

        question = questions.get(3);
        assertTrue(question.getQuestion_id()==4);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_4", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(true, question.getStudied());
        assertEquals(2, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        question = questions.get(4);
        assertTrue(question.getQuestion_id()==5);
        assertEquals("Sintassi", question.getCategory());
        assertEquals("Testo_5", question.getText());
        assertEquals(true, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(2, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        question = questions.get(5);
        assertTrue(question.getQuestion_id()==6);
        assertEquals("Matematica", question.getCategory());
        assertEquals("Testo_6", question.getText());
        assertEquals(true, question.getFavorited());
        assertEquals(true, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());

    }

    @Test
    public void testGetQuestionById(){
        Question question = mDataSource.getQuestionById(4,dbReadable);
        assertNotNull(question);

        assertTrue(question.getQuestion_id()==4);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_4", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(true, question.getStudied());
        assertEquals(2, question.getContest_id());
        assertEquals(false, question.getHasAttach());
    }

    @Test
    public void testGetCategoriesbyContestId(){
        ArrayList<String> categories = mDataSource.getAllCategoriesByContestId(1,dbReadable);
        assertThat(categories.size(), is(2));

        assertEquals("Matematica", categories.get(0));
        assertEquals("Storia", categories.get(1));

    }

    @Test
    public void testGetQuestionsByContestId(){
        ArrayList<Question> questions = mDataSource.getAllQuestionsByContestId(2,dbReadable);
        assertThat(questions.size(), is(2));

        Question question = questions.get(0);
        assertTrue(question.getQuestion_id()==4);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_4", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(true, question.getStudied());
        assertEquals(2, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        question = questions.get(1);
        assertTrue(question.getQuestion_id()==5);
        assertEquals("Sintassi", question.getCategory());
        assertEquals("Testo_5", question.getText());
        assertEquals(true, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(2, question.getContest_id());
        assertEquals(false, question.getHasAttach());
    }

    @Test
    public void testGetQuestionByCategoryAndContestId(){
        ArrayList<Question> questions = mDataSource.getAllQuestionByCategoryAndContestId(1,"Storia",dbReadable);
        assertThat(questions.size(), is(2));

        Question question = questions.get(0);
        assertTrue(question.getQuestion_id()==1);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_1", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        question = questions.get(1);
        assertTrue(question.getQuestion_id()==3);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_3", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(true, question.getHasAttach());
    }

    @Test
    public void testGetContestById(){
        ArrayList<Integer> percentages = mDataSource.getPercentageStudiedByCategory(2,dbReadable);
        assertNotNull(percentages);
        //assertTrue(percentages.get(0)==0);
        //assertTrue(percentages.get(1)==1);

    }

    @Test
    public void testGetQuestionsByContestIdHash(){
        QuestionsHashMap questionsHashMap = mDataSource.getAllQuestionsByContestIdHash(1,dbReadable);
        ArrayList<Question> questions = questionsHashMap.getQuestionsByCategory("Storia");
        assertNotNull(questions);
        Question question = questions.get(0);
        assertTrue(question.getQuestion_id()==1);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_1", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        question = questions.get(1);
        assertTrue(question.getQuestion_id()==3);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_3", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(true, question.getHasAttach());

    }

    @Test
    public void testDeleteQuestion(){
        Question question = mDataSource.deleteQuestion(2,dbWritable);
        assertTrue(question.getQuestion_id()==2);
        assertEquals("Matematica", question.getCategory());
        assertEquals("Testo_2", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());

        ArrayList<Question> questions = mDataSource.getAllQuestions(dbReadable);
        assertThat(questions.size(), is(5));

    }

    @Test
    public void testSetQuestionFavorited(){
        mDataSource.setQuestionFavorited(1,true,dbWritable);
        Question question = mDataSource.getQuestionById(1,dbReadable);
        assertNotNull(question);

        assertTrue(question.getQuestion_id()==1);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_1", question.getText());
        assertEquals(true, question.getFavorited());
        assertEquals(false, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());
    }

    @Test
    public void testSetQuestionStudied(){
        mDataSource.setQuestionStudied(1,true,dbWritable);
        Question question = mDataSource.getQuestionById(1,dbReadable);
        assertNotNull(question);

        assertTrue(question.getQuestion_id()==1);
        assertEquals("Storia", question.getCategory());
        assertEquals("Testo_1", question.getText());
        assertEquals(false, question.getFavorited());
        assertEquals(true, question.getStudied());
        assertEquals(1, question.getContest_id());
        assertEquals(false, question.getHasAttach());
    }
}
