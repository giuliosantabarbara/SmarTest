package jumapp.com.smartest.Exercise.Singleton;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;
import jumapp.com.smartest.Storage.DAOObject.Contest;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 09/04/2017.
 */
public class AllQuestionsSingleton extends Application {

    private static QuestionsExerciseShortSingleton mInstance;

    private Contest contest;

    protected AllQuestionsSingleton(){}

    public static  QuestionsExerciseShortSingleton getInstance(){
        if(null == mInstance){
            mInstance = new QuestionsExerciseShortSingleton();
        }
        return mInstance;
    }

    public Contest getContest() {
        return contest;
    }


    public void setContest(Contest contest) {
        this.contest = contest;
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
            Log.i("###", "" + (index));
        }
        return questions;
    }

}
