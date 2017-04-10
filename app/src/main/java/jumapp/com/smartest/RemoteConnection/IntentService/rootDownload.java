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

import java.util.Iterator;

import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 08/04/2017.
 */
public class rootDownload {
    static Context context;
    static DataSnapshot dataSnapshotRoot;
    String url;


    public rootDownload(Context context){
        this.context=context;
    }

    public void start(){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        final ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###", "FIREBASE HA RICEVUTO RISPOSTA JUMAPP");
                dataSnapshotRoot = dataSnapshot;

                Intent intent = new Intent(((Activity) context), DownloadServiceRoot.class);
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


    public static class DownloadServiceRoot extends IntentService {

        public DownloadServiceRoot() {
            super("DownloadServiceRoot");
        }


        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###", "INIZIO SERVICE ROOT");
            int k =0;
            /*while(dataSnapshotQuestion.getChildren().iterator().hasNext()){
                k++;
                dataSnapshotQuestion.getChildren().iterator().next();
            }*/

            for (DataSnapshot messageSnapshot : dataSnapshotRoot.getChildren()) {
                Iterator<DataSnapshot> iter=messageSnapshot.getChildren().iterator();
                while(iter.hasNext()){
                    DataSnapshot d=iter.next();
                    Iterator<DataSnapshot> iter2=d.getChildren().iterator();
                    while(iter2.hasNext()) {
                        k++;
                        DataSnapshot d2=iter2.next();
                        //Log.i("kkk",d2.toString());
                    }

                }

            }

            Log.i("###", "NUMBER OF CHILDREN: " + k);
        }
    }

}

