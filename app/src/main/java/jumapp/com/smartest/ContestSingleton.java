package jumapp.com.smartest;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import jumapp.com.smartest.Exercise.Pair;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 10/04/2017.
 */
public class ContestSingleton extends Application {

    private static ContestSingleton mInstance;

    private static Contest contest;

    protected ContestSingleton(Context context){
       Log.i("###", "ho chiamato il retrieve service");
        Intent intent = new Intent(context, RetrieveContestService.class);
        context.startService(intent);
        Log.i("###", "ho chiamato il retrieve service");
    }

    public static  ContestSingleton getInstance(Context context){
        if(null == mInstance){
            mInstance = new ContestSingleton(context);
        }
        return mInstance;
    }

    /*public ArrayList<Question> getRandomQuestions(ArrayList<Pair> pairs){
        ArrayList<Question> result= new ArrayList<Question>();
        for(Pair p: pairs){
            String Category=p.getCategory();
            int number= p.getNumber();
        }
    }*/


   /* public ArrayList<Question> getRandomQuestions(int number){

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
    }*/


    public static class RetrieveContestService   extends IntentService {



        public RetrieveContestService() {
            super("RetrieveContestService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###","start contest retrieve");
            ContestDAO con= new ContestDAOImpl(this);
            contest=con.getContestById(1);
            contest.printLog("GGGMMM");



        }
    }

}