package jumapp.com.smartest;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
    private SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    protected ContestSingleton(Context context){
        Log.i("###", "ho chiamato il retrieve service");
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor=prefs.edit();
        editor.putInt("contest_singleton_id",0);
        editor.commit();
        Intent intent = new Intent(context, RetrieveContestService.class);
        context.startService(intent);
        Log.i("###", "ho chiamato il retrieve service");
    }

    public static synchronized ContestSingleton getInstance(Context context){
        if(null == mInstance){
            mInstance = new ContestSingleton(context);
        }
        return mInstance;
    }

    public boolean isReady(){
        if(contest==null) return false;
        else return true;
    }

    /*public ArrayList<Question> getRandomQuestions(ArrayList<Pair> pairs){
        ArrayList<Question> result= new ArrayList<Question>();
        for(Pair p: pairs){
            String Category=p.getCategory();
            int number= p.getNumber();
        }
    }*/


    public ArrayList<Question> getRandomQuestions(ArrayList<Pair> pairs){
        ArrayList<Question> result = new ArrayList<Question>();
        for(Pair p:pairs) {
            String category= p.getCategory();
            int number =p.getNumber();
            ArrayList<Question> questions=contest.getQuestions().getQuestionsByCategory(category);
            int[] array = new int[number];
            int i;
            int coef = (int) Math.ceil(Math.random() * (questions.size() / number));
            int index;

            for (i = 1; i < (array.length + 1); i++) {
                index = ((i) * coef)-1;
                result.add(questions.get(index));
                Log.i("###", "" + (index));
            }
        }
        return result;
    }


    public static class RetrieveContestService   extends IntentService {



        public RetrieveContestService() {
            super("RetrieveContestService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###","start contest retrieve");
            ContestDAO con= new ContestDAOImpl(this);
            SQLiteDatabase db = con.openReadableConnection();
            contest=con.getContestById(1, db);
            db.close();
            editor.putInt("contest_singleton_id",1);
            editor.commit();
            Log.i("###", "stop contest retrieve: "+contest.getQuestions().getSize());
        }
    }

}