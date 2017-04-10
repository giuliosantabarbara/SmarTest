package jumapp.com.smartest.QuestionViewer;

import android.app.Application;

import java.util.ArrayList;

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
}