package jumapp.com.smartest.RemoteConnection.IntentService;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 30/03/2017.
 */
public class QuestionDownloadService {

    static Context context;
    static DataSnapshot dataSnapshotQuestion;
    String url;


    public QuestionDownloadService(final Context context,String url){
        this.context=context;
        this.url=url;
    }


    public void start(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###", "FIREBASE HA RICEVUTO RISPOSTA QUEST");
                dataSnapshotQuestion = dataSnapshot;
                Log.i("###", "RICHIESTA AL SERVICE QUEST");
                Intent intent = new Intent(((Activity) context), DownloadServiceQuestion.class);
                intent.putExtra("download_subject", "questions");
                ((Activity) context).startService(intent);
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);

    }


    public static class DownloadServiceQuestion extends IntentService {

        public DownloadServiceQuestion() {
            super("DownloadServiceQuestion");
        }


        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###", "INIZIO SERVICE QUEST");
            final QuestionDAO questDao = new QuestionDAOImpl(context);
            final SQLiteDatabase db = questDao.openWritableConnection();
            for (DataSnapshot messageSnapshot : dataSnapshotQuestion.getChildren()) {
                Question q = messageSnapshot.getValue(Question.class);
                questDao.insert(q, db);
            }
            Log.i("###", "SERVICE HA FINITO ELABORAZIONE QUEST");

            db.close();

        }

    }

}
