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

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;

/**
 * Created by marco on 30/03/2017.
 */
public class MetaDataDownloadService {

    static Context context;
    static DataSnapshot dataSnapshotMetaData;
    String url;
    public static final String INTENT_ACTION = "www.jumapp.com.send.metadata.download";


    public MetaDataDownloadService(final Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public void start() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final ValueEventListener vel = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###", "FIREBASE HA RICEVUTO RISPOSTA METADATA");
                dataSnapshotMetaData = dataSnapshot;
                Log.i("###", "RICHIESTA AL SERVICE METADATA "+dataSnapshot.hasChildren());

                Intent intent = new Intent(((Activity) context), DownloadServiceMetaData.class);
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


    public static class DownloadServiceMetaData extends IntentService {

        public DownloadServiceMetaData() {
            super("DownloadServiceQuestion");
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            Log.i("$$$", "Entro nel handle intent");
            ContestDAO conDao = new ContestDAOImpl(context);
            final SQLiteDatabase db = conDao.openWritableConnection();
            db.beginTransaction();
            ArrayList<Contest> currentContests = conDao.getAllContests(db);
            ArrayList<Long> intCurrentContests = new ArrayList<Long>();
            for (Contest myC : currentContests) intCurrentContests.add(myC.getId_contest());


            for (DataSnapshot messageSnapshot : dataSnapshotMetaData.getChildren()) {

                Log.i("$$$", "for snapshot");

                Contest c = messageSnapshot.getValue(Contest.class);
                long confronto = c.getId_contest();
                if (!intCurrentContests.contains(confronto)) {
                    Log.i("$$$", "Insert contest " + db.isOpen());
                    conDao.insert(c, db);
                    Intent intentC = new Intent();
                    intentC.setAction(INTENT_ACTION);
                    context.sendBroadcast(intentC);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }

    }
}
