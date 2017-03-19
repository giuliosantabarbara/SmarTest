package jumapp.com.smartest.RemoteConnection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
public class FirebaseConnector implements  Connector {

    private String firebaseURL2ContestCollection;
    private Context context;
    private DatabaseReference mDatabase;
    ContestDAO conDao;
    public static final String INTENT_ACTION = "www.jumapp.com.send.metadata.download";

    public FirebaseConnector(Context context,String firebaseURL2ContestCollection){
        this.context=context;
        this.firebaseURL2ContestCollection=firebaseURL2ContestCollection;
        conDao= new ContestDAOImpl(context);
    }


    @Override
    public boolean downloadContestMetaData() {
        Log.i("$$$", "ENTRO NEL CONTEST META DATA");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(firebaseURL2ContestCollection);
        final ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ArrayList<Contest> currentContests=conDao.getAllContests();
                    ArrayList<Long> intCurrentContests= new ArrayList<Long>();
                    for(Contest myC: currentContests ) intCurrentContests.add(myC.getId_contest());
                    Contest c = messageSnapshot.getValue(Contest.class);
                    long confronto=c.getId_contest();
                    if( !intCurrentContests.contains(confronto)){
                        Log.i("$$$", "Insert contest");
                        conDao.insert(c);
                        Intent intent = new Intent();
                        intent.setAction(INTENT_ACTION);
                        context.sendBroadcast(intent);
                        Toast.makeText(context, "Meta dati scaricati con successo", Toast.LENGTH_LONG).show();
                    }
                }

                ref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);



        return true;
    }

    @Override
    public boolean downloadContest(long id) {
        Contest con= conDao.getContestById(id);
        downloadQuestions(con.getQuestionsURL());
        downloadAlternatives(con.getAlternativesURL());
        downloadAttachments(con.getAttachmentsURL());

        return true;
    }

    private boolean downloadQuestions(String url){
        final QuestionDAO questDao= new QuestionDAOImpl(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Question q = messageSnapshot.getValue(Question.class);
                    //Log.i("$$$",q.getText());
                    questDao.insert(q);
                }
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);
        return true;
    }

    private boolean downloadAlternatives(String url){
        final AlternativeDAO alternativeDao= new AlternativeDAOImpl(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Alternative a = messageSnapshot.getValue(Alternative.class);
                    alternativeDao.insert(a);
                }
                ref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);
        return true;
    }

    private boolean downloadAttachments(String url){
        final AttachmentDAO attachmentDAO= new AttachmentDAOImpl(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(url);
        final ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Attachment a = messageSnapshot.getValue(Attachment.class);
                    attachmentDAO.insert(a);
                }
                Toast.makeText(context,"Download concorso N 1 completato", Toast.LENGTH_LONG).show();
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addValueEventListener(vel);
        return true;
    }
}
