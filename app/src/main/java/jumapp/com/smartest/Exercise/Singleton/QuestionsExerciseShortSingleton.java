package jumapp.com.smartest.Exercise.Singleton;

/**
 * Created by marco on 05/04/2017.
 */

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 02/04/2017.
 */

public class QuestionsExerciseShortSingleton extends Application {

    private static QuestionsExerciseShortSingleton mInstance;

    private ArrayList<Question> questions= new ArrayList<Question>();

    protected QuestionsExerciseShortSingleton(){}

    public static  QuestionsExerciseShortSingleton getInstance(){
        if(null == mInstance){
            mInstance = new QuestionsExerciseShortSingleton();
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

    public ArrayList<Question> getRandomQuestions(int number){

        ArrayList<Question> questSingleton= QuestionsSingleton.getInstance().getQuestions();
        int [] array= new int[number];
        int i;
        int coef=(int) Math.ceil(Math.random()*(questSingleton.size()/number));
        int index;
        ArrayList<Question> questions=new ArrayList<Question>();
        for(i=1; i<(array.length+1); i++){
            index=(i-1)*coef;
            questions.add(questSingleton.get(index));
            Log.i("###",""+(index));
        }
        return questions;
    }

    public void add(Question q){
        this.questions.add(q);
    }
}
