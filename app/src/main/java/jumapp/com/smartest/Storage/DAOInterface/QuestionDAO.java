package jumapp.com.smartest.Storage.DAOInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 18/03/2017.
 */
public interface QuestionDAO {

    public ArrayList<Question> getAllQuestionsByContestId(long contestId, SQLiteDatabase dbAlt,SQLiteDatabase dbQuest,SQLiteDatabase dbAtt);
    public void insert(Question q,SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();
    public ArrayList<String> getAllCategoriesByContestId(long contest_id);
    public ArrayList<Integer> getPercentageStudiedByCategory(long contest_id);
    public ArrayList<Question> getAllQuestionByCategoryAndContestId(long contestId,String categoryParam);
    public void setQuestionStudied(long questionId,boolean studied,SQLiteDatabase dbQuest);
    public int numberOfRowsByContest(long contestId);

    public Question getQuestionById(long questionId,SQLiteDatabase dbAtt);



    public Question deleteQuestion(long questionId,SQLiteDatabase dbQuest);
    public ArrayList<Question> getAllQuestions();



}
