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

import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

/**
 * Created by marco on 30/03/2017.
 */
public class AttachmentDownloadService {

    static Context context;
    static DataSnapshot dataSnapshotAttachment;
    String url;


    public AttachmentDownloadService(final Context context,String url){
        this.context=context;
        this.url=url;
    }

    public void start(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref3 = database.getReference(url);
        final ValueEventListener vel3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###","FIREBASE HA RICEVUTO RISPOSTA ATT");
                dataSnapshotAttachment=dataSnapshot;
                Log.i("###", "RICHIESTA AL SERVICE ATT");
                Intent intent = new Intent(context, DownloadServiceAttachment.class);
                intent.putExtra("download_subject","attachments");
                ((Activity)context).startService(intent);
                ref3.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref3.addValueEventListener(vel3);
    }

    public static class DownloadServiceAttachment extends IntentService {

        public DownloadServiceAttachment() {
            super("DownloadServiceAttachment");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.i("###", "INIZIO SERVICE ATTACH");
            final AttachmentDAO questDao = new AttachmentDAOImpl(context);
            final SQLiteDatabase db = questDao.openWritableConnection();
            for (DataSnapshot messageSnapshot : dataSnapshotAttachment.getChildren()) {
                Attachment q = messageSnapshot.getValue(Attachment.class);
                questDao.insert(q, db);
            }
            Log.i("###", "SERVICE HA FINITO ELABORAZIONE ATT");

            db.close();
        }
    }
}
