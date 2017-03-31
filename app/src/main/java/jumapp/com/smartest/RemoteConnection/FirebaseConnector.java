package jumapp.com.smartest.RemoteConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jumapp.com.smartest.RemoteConnection.IntentService.AlternativeDownloadService;
import jumapp.com.smartest.RemoteConnection.IntentService.AttachmentDownloadService;
import jumapp.com.smartest.RemoteConnection.IntentService.MetaDataDownloadService;
import jumapp.com.smartest.RemoteConnection.IntentService.QuestionDownloadService;
import jumapp.com.smartest.Storage.DAOImpl.AlternativeDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.Attachment;
import jumapp.com.smartest.Storage.DAOObject.Contest;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 18/03/2017.
 */
public class FirebaseConnector implements Connector {

    private String firebaseURL2ContestCollection;
    private static Context context;
    private DatabaseReference mDatabase;


    public FirebaseConnector(Context context, String firebaseURL2ContestCollection) {
        this.context = context;
        this.firebaseURL2ContestCollection = firebaseURL2ContestCollection;

    }


    @Override
    public boolean downloadContestMetaData() {
        MetaDataDownloadService meta= new MetaDataDownloadService(context,firebaseURL2ContestCollection);
        meta.start();
        return true;
    }


    @Override
    public boolean downloadContest(long id) {

        Log.i("###", "BOTTONE PREMUTO");
        ContestDAO conDao = new ContestDAOImpl(context);
        Contest con = conDao.getContestById(id);

        AttachmentDownloadService attch = new AttachmentDownloadService(context, con.getAttachmentsURL());
        attch.start();

        QuestionDownloadService quest= new QuestionDownloadService(context, con.getQuestionsURL());
        quest.start();

        AlternativeDownloadService alt= new AlternativeDownloadService(context,con.getAlternativesURL());
        alt.start();

        //downloadQuestions(con.getQuestionsURL());
        //downloadAlternatives(con.getAlternativesURL());
        //downloadAttachments(con.getAttachmentsURL());
        return true;
    }


/*
    private static boolean downloadQuestions(String url) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final QuestionDAO questDao = new QuestionDAOImpl(context);

        final ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("###","RICEVUTA RISPOSTA FIREBASE");
                long fire= 0;
                long dbT= 0;
                SQLiteDatabase db = questDao.openWritableConnection();
                db.beginTransaction();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    long st= System.currentTimeMillis();
                    Question q = messageSnapshot.getValue(Question.class);

                    //Log.i("$$$",q.getText());
                    questDao.insert(q);
                    long end=System.currentTimeMillis();
                    fire=fire+(end-st);
                    st= System.currentTimeMillis();
                    questDao.insert(q, db);
                    end=System.currentTimeMillis();
                    dbT=dbT+(end-st);

                }
                Log.i("###","DOWNLOAD COMPLETATO");
                Log.i("###","TEMPO LEGATO A FIREBASE: "+fire);
                Log.i("###","TEMPO LEGATO A DB: "+dbT);
                ref.removeEventListener(this);
                db.setTransactionSuccessful();
                db.endTransaction();
                db.close();
                Intent endIntent = new Intent();
                endIntent.setAction(INTENT_ACTION_CONTEST);
                context.sendBroadcast(endIntent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);
        return true;
    }

        private static boolean downloadAlternatives(String url) {
            final AlternativeDAO alternativeDao = new AlternativeDAOImpl(context);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref = database.getReference(url);
            final SQLiteDatabase db = alternativeDao.openWritableConnection();
            final ValueEventListener vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        Alternative a = messageSnapshot.getValue(Alternative.class);
                        alternativeDao.insert(a, db);
                    }
                    ref.removeEventListener(this);
                    db.close();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ref.addValueEventListener(vel);
            return true;
        }

        private static boolean downloadAttachments(String url) {
            final AttachmentDAO attachmentDAO = new AttachmentDAOImpl(context);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference ref = database.getReference(url);
            final SQLiteDatabase db = attachmentDAO.openWritableConnection();
            final ValueEventListener vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        Attachment a = messageSnapshot.getValue(Attachment.class);
                        attachmentDAO.insert(a, db);
                    }
                    ref.removeEventListener(this);
                    db.close();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ref.addValueEventListener(vel);
            return true;
        }
*/


}
