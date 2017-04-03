package jumapp.com.smartest.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jumapp.com.smartest.R;
import jumapp.com.smartest.RemoteConnection.Connector;
import jumapp.com.smartest.RemoteConnection.FirebaseConnector;
import jumapp.com.smartest.RemoteConnection.IntentService.AlternativeDownloadService;
import jumapp.com.smartest.RemoteConnection.IntentService.MetaDataDownloadService;
import jumapp.com.smartest.Storage.DAOImpl.AlternativeDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.AttachmentDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.Contest;
import jumapp.com.smartest.fragments.BottomNavigationFragment;
import jumapp.com.smartest.fragments.CircleHamButtonFragment;


public class MainActivity extends AppCompatActivity implements CircleHamButtonFragment.OnSelectedButtonListener, NavigationView.OnNavigationItemSelectedListener {

    /*@Bind(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;*/

    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private ArrayList<Contest> myContests= new ArrayList<Contest>();
    private ChildEventListener childEventListener;
    private DatabaseReference mDatabase;
    private Context context;
    private ContestMetaDataReceiver contestMetaDataReceiver;
    private ContestDataReceiver contestDataReceiver;
    private SweetAlertDialog contestDialog;
    ImageView pencil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences("jumapp", Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       //DatabaseReference ref = database.getReference();
        //ref.removeValue();

        context=this;
        pencil=(ImageView) this.findViewById(R.id.start_icon_pencil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Fragment Handler
        Connector firebase= new FirebaseConnector(this,"contests");
        firebase.downloadContestMetaData();

        final ContestDAO conDAO= new ContestDAOImpl(this);
        int numberOfRow= conDAO.numberOfRows();
        if(numberOfRow>0){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CircleHamButtonFragment ch=new CircleHamButtonFragment();
            Bundle bundle=new Bundle();
            bundle.putString("from", "main_activity");
            ch.setArguments(bundle);
            fragmentTransaction.add(R.id.activity_main, ch,"first_first");
            fragmentTransaction.commit();
            pencil.setVisibility(View.INVISIBLE);

        }else{
            pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (conDAO.numberOfRows() == 0) {
                        final SweetAlertDialog pDialog = new SweetAlertDialog(context,
                                SweetAlertDialog.PROGRESS_TYPE)
                                .setTitleText("Connecting to the Server");
                        pDialog.show();
                        pDialog.setCancelable(false);
                        new MyCountDownTimer(3000, 500, pDialog, conDAO).Start();

                    }
                }
            });
        }


    }

    //Drawer listener
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSelectedButton(String s) {
        // get body fragment (native method is getFragmentManager)
        CircleHamButtonFragment fragment = (CircleHamButtonFragment) getFragmentManager().findFragmentById(R.id.circle_button);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(s.equalsIgnoreCase("uno")){

            fragmentTransaction.replace(R.id.activity_main, new BottomNavigationFragment());
            fragmentTransaction.addToBackStack("back");
            fragmentTransaction.commit();
        }
        if (s.equalsIgnoreCase("studia")) {
            fragmentTransaction.replace(R.id.activity_main, new BottomNavigationFragment());
            fragmentTransaction.addToBackStack("back");
            fragmentTransaction.commit();
        }


    }

    @Override
    public void onBackPressed() {
            Log.i("####","MAIN BACK PRESSED RAMO ELSE");
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }


    }


    public void cleanDB(){
        ContestDAOImpl cont= new ContestDAOImpl(this);
        cont.deleteAll();
        AlternativeDAOImpl alt= new AlternativeDAOImpl(this);
        alt.deleteAll();
        AttachmentDAOImpl att= new AttachmentDAOImpl(this);
        att.deleteAll();
        QuestionDAOImpl quest= new QuestionDAOImpl(this);
        quest.deleteAll();

    }


    public void cancellaTutto(View v){
        ContestDAOImpl cont= new ContestDAOImpl(this);
        cont.deleteAll();
        AlternativeDAOImpl alt= new AlternativeDAOImpl(this);
        alt.deleteAll();
        AttachmentDAOImpl att= new AttachmentDAOImpl(this);
        att.deleteAll();
        QuestionDAOImpl quest= new QuestionDAOImpl(this);
        quest.deleteAll();
    }

    public void cancellaTuttoTranneMetaData(View v){
        AlternativeDAOImpl alt= new AlternativeDAOImpl(this);
        alt.deleteAll();
        AttachmentDAOImpl att= new AttachmentDAOImpl(this);
        att.deleteAll();
        QuestionDAOImpl quest= new QuestionDAOImpl(this);
        quest.deleteAll();
    }

    public void downloadContestMetaData(View v){
        Connector fireConnector= new FirebaseConnector(this,"contests");
        fireConnector.downloadContestMetaData();
    }

    public void dowloadPrimoContest(View v){

        Connector fireConnector= new FirebaseConnector(this,"contests");

        contestDialog = new SweetAlertDialog(context,
                SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Connecting to the Server");
        contestDialog.show();


        contestDialog.setCancelable(false);

        fireConnector.downloadContest(1);
    }

    public void stampaContestNelLog(View v){

        ContestDAO conDAO= new ContestDAOImpl(this);
        QuestionDAOImpl questDAO= new QuestionDAOImpl(this);
        long st=System.currentTimeMillis();
        Log.i("###", "Partito");
        ArrayList<Contest> contests= conDAO.getAllContests();
        long end=System.currentTimeMillis();
        Log.i("###", "Tempo di fine: " + (end - st));
        Log.i("###", "Numero di questions: " + questDAO.numberOfRows());

        AlternativeDAOImpl impl = new AlternativeDAOImpl(this);
        ArrayList<Alternative> array = impl.getAllAlternatives();

        for(Alternative a: array){
           a.printLog("!!!!");
        }
        /*for(Contest c: contests){
            ArrayList<Question> questions=c.getQuestions();
            Log.i("###",""+questions.size());
            c.printLog("$$$");
        }*/

       /* AttachmentDAOImpl atDAO= new AttachmentDAOImpl(this);
        ArrayList<Attachment> attachments= atDAO.getAllAttachments();
        for(Attachment a: attachments ) a.printLog("!!!!!!!!");*/
    }




    class MyCountDownTimer {
        private long millisInFuture;
        private long countDownInterval;
        private SweetAlertDialog pDialog;
        private ContestDAO conDAO;

        public MyCountDownTimer(long pMillisInFuture, long pCountDownInterval, SweetAlertDialog pDialog, ContestDAO conDAO) {
            this.millisInFuture = pMillisInFuture;
            this.countDownInterval = pCountDownInterval;
            this.pDialog=pDialog;
            this.conDAO=conDAO;
        }
        public void Start()
        {
            final Handler handler = new Handler();
            Log.v("status", "starting");
            final Runnable counter = new Runnable(){

                public void run(){
                    pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                    long sec = millisInFuture/1000;
                    Log.v("status", Long.toString(sec) + " seconds remain");
                    millisInFuture -= countDownInterval;
                    if (conDAO.numberOfRows()>0){
                        pDialog.setTitleText("Download completato!")
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        handler.removeCallbacks(this);

                    }else{
                        handler.postDelayed(this, countDownInterval);
                    }


                }
            };

            handler.postDelayed(counter, countDownInterval);
        }
    }


    public class ContestMetaDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("####","entro nel recevier");

            //TODO: React to the Intent Broadcast received.
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CircleHamButtonFragment ch=new CircleHamButtonFragment();
            Bundle bundle=new Bundle();
            bundle.putString("from", "main_activity");
            ch.setArguments(bundle);
            fragmentTransaction.add(R.id.activity_main, ch,"first_first");
            //fragmentTransaction.addToBackStack("pencil_fragment");
            fragmentTransaction.commit();
            pencil.setVisibility(View.INVISIBLE);
        }
    }

    public class ContestDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO: React to the Intent Broadcast received.
            contestDialog.setTitleText("Download completato!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        contestMetaDataReceiver= new ContestMetaDataReceiver();
        contestDataReceiver= new ContestDataReceiver();
        registerReceiver(contestMetaDataReceiver, new IntentFilter(MetaDataDownloadService.INTENT_ACTION));
        registerReceiver(contestDataReceiver, new IntentFilter(AlternativeDownloadService.INTENT_ACTION_CONTEST));
    }

    @Override
    public void onPause() {
        unregisterReceiver(contestMetaDataReceiver);
        unregisterReceiver(contestDataReceiver);
        super.onPause();
    }





}
