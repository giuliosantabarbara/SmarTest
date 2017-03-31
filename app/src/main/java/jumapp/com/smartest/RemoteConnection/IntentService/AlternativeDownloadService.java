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

import jumapp.com.smartest.Storage.DAOImpl.AlternativeDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOObject.Alternative;

/**
 * Created by marco on 30/03/2017.
 */
public class AlternativeDownloadService {

    static Context context;
    static DataSnapshot dataSnapshotAlternative;
    String url;
    public static final String INTENT_ACTION_CONTEST = "www.jumapp.com.send.contest.download";

    public AlternativeDownloadService(Context context,String url){
        this.context=context;
        this.url=url;
    }


    public void start() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref2 = database.getReference(url);
        final ValueEventListener vel2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###", "FIREBASE HA RICEVUTO RISPOSTA ALT");
                dataSnapshotAlternative = dataSnapshot;
                Log.i("###", "RICHIESTA AL SERVICE ALT");
                Intent intent = new Intent(((Activity) context), DownloadServiceAlternative.class);
                intent.putExtra("download_subject", "alternatives");
                ((Activity) context).startService(intent);
                ref2.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref2.addValueEventListener(vel2);

    }


    public static class DownloadServiceAlternative extends IntentService {

        public DownloadServiceAlternative() {
            super("DownloadServiceAlternative");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###", "INIZIO SERVICE ALTERN");
            final AlternativeDAO questDao = new AlternativeDAOImpl(context);
            final SQLiteDatabase db = questDao.openWritableConnection();
            for (DataSnapshot messageSnapshot : dataSnapshotAlternative.getChildren()) {
                Alternative q = messageSnapshot.getValue(Alternative.class);
                questDao.insert(q, db);
            }
            Log.i("###", "SERVICE HA FINITO ELABORAZIONE ALT");
            Intent endIntent = new Intent();
            endIntent.setAction(INTENT_ACTION_CONTEST);
            context.sendBroadcast(endIntent);
            db.close();
        }
    }

}
