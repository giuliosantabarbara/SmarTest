package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import jumapp.com.smartest.QuestionsHashMap;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 18/03/2017.
 */
public interface QuestionDAO {


    /**
     * Insert.
     *
     * @param q  the q
     * @param db the db
     */
    public void insert(Question q,SQLiteDatabase db);

    /**
     * Open writable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openWritableConnection();

    /**
     * Open readable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openReadableConnection();

    /**
     * Gets all categories by contest id.
     *
     * @param contest_id the contest id
     * @param db         the db
     * @return the all categories by contest id
     */
    public ArrayList<String> getAllCategoriesByContestId(long contest_id,  SQLiteDatabase db);

    /**
     * Gets percentage studied by category.
     *
     * @param contest_id the contest id
     * @param db         the db
     * @return the percentage studied by category
     */
    public ArrayList<Integer> getPercentageStudiedByCategory(long contest_id, SQLiteDatabase db);

    /**
     * Gets all question by category and contest id.
     *
     * @param contestId     the contest id
     * @param categoryParam the category param
     * @param dbQuest       the db quest
     * @return the all question by category and contest id
     */
    public ArrayList<Question> getAllQuestionByCategoryAndContestId(long contestId,String categoryParam,SQLiteDatabase dbQuest);

    /**
     * Sets question favorited.
     *
     * @param questionId the question id
     * @param studied    the studied
     * @param dbQuest    the db quest
     */
    public void setQuestionFavorited(long questionId,boolean studied,SQLiteDatabase dbQuest);

    /**
     * Number of rows by contest int.
     *
     * @param contestId the contest id
     * @param dbN       the db n
     * @return the int
     */
    public int numberOfRowsByContest(long contestId,SQLiteDatabase dbN);

    /**
     * Delete all.
     *
     * @param dbN the db n
     */
    public void deleteAll(SQLiteDatabase dbN);

    /**
     * Gets all questions by contest id hash.
     *
     * @param contestid the contestid
     * @param dbQuest   the db quest
     * @return the all questions by contest id hash
     */
    public QuestionsHashMap getAllQuestionsByContestIdHash(long contestid, SQLiteDatabase dbQuest);

    /**
     * Gets question by id.
     *
     * @param questionId the question id
     * @param dbAtt      the db att
     * @return the question by id
     */
    public Question getQuestionById(long questionId,SQLiteDatabase dbAtt);

    /**
     * Delete question question.
     *
     * @param questionId the question id
     * @param dbQuest    the db quest
     * @return the question
     */
    public Question deleteQuestion(long questionId,SQLiteDatabase dbQuest);

    /**
     * Gets all questions.
     *
     * @param db the db
     * @return the all questions
     */
    public ArrayList<Question> getAllQuestions(SQLiteDatabase db);

    /**
     * Gets all questions by contest id.
     *
     * @param contestid the contestid
     * @param dbQuest   the db quest
     * @return the all questions by contest id
     */
    public ArrayList<Question> getAllQuestionsByContestId(long contestid, SQLiteDatabase dbQuest);

    /**
     * Sets question studied.
     *
     * @param questionId the question id
     * @param studied    the studied
     * @param dbQuest    the db quest
     */
    public void setQuestionStudied(long questionId,boolean studied,SQLiteDatabase dbQuest);



}
