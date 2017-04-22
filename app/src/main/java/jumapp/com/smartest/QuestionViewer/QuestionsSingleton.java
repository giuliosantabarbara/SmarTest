package jumapp.com.smartest.QuestionViewer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 02/04/2017.
 */

public class QuestionsSingleton extends Application {

    private static QuestionsSingleton mInstance;

    private ArrayList<Question> questions= new ArrayList<Question>();

    protected QuestionsSingleton(){}

    public static  QuestionsSingleton getInstance(){
        if(null == mInstance){
            mInstance = new QuestionsSingleton();
        }
        return mInstance;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Question getQuestionByIndex(int index){
        return questions.get(index);
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setQuestionsStudied(Context con,int index, boolean isStudied) {
        Question q=this.questions.get(index);
        q.setStudied(isStudied);
        QuestionDAO quest= new QuestionDAOImpl(con);
        SQLiteDatabase conn=quest.openWritableConnection();
        quest.setQuestionStudied(q.getQuestion_id(),isStudied,conn);
        conn.close();
    }

    public void setQuestionsFavorited(Context con,int index, boolean isStudied) {
        Question q=this.questions.get(index);
        q.setFavorited(isStudied);
        QuestionDAO quest= new QuestionDAOImpl(con);
        SQLiteDatabase conn=quest.openWritableConnection();
        quest.setQuestionFavorited(q.getQuestion_id(),isStudied,conn);
        conn.close();
    }
}