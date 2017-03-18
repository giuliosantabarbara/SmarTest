package jumapp.com.smartest.Storage.DAOInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 18/03/2017.
 */
public interface QuestionDAO {

    public ArrayList<Question> getAllQuestionsByContestId(long contestId, SQLiteDatabase dbAlt,SQLiteDatabase dbQuest,SQLiteDatabase dbAtt);
    public Question getQuestionById(long questionId,SQLiteDatabase dbAtt);
    public void insert(Question q);
    public Question deleteQuestion(long questionId);
    public ArrayList<Question> getAllQuestions();
    public ArrayList<String> getAllCategoriesByContestId(long contest_id);

}