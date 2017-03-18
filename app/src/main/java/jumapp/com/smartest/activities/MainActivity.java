package jumapp.com.smartest.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import jumapp.com.smartest.R;
import jumapp.com.smartest.fragments.BottomNavigationFragment;
import jumapp.com.smartest.fragments.CircleHamButtonFragment;
import jumapp.com.smartest.fragments.SimulationEndFragment;


public class MainActivity extends AppCompatActivity implements CircleHamButtonFragment.OnSelectedButtonListener, NavigationView.OnNavigationItemSelectedListener {

    /*@Bind(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        /*ButterKnife.bind(this);
        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();

        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(this, R.color.sp_primary_text_light), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(this, R.color.red), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                ContextCompat.getColor(this, R.color.blue_selected), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
        eventList.add(event3);



        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());*/

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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, new CircleHamButtonFragment());
        //fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit();
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


        /*if(s.equalsIgnoreCase("uno")){
            Log.i("valore fuori",s);
           CircleHamButtonFragment fragment = (CircleHamButtonFragment) getFragmentManager().findFragmentById(R.id.activity_main);

            // if fragment is not null and in layout, set text, else launch BodyActivity
            //
            if ((fragment!=null)) {
                Log.i("Valore dentro",s);

                fragment.onDestroy();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main, new HamButtonFragment());
                fragmentTransaction.commit();

            } else {
                /*Intent intent = new Intent(this,HamButtonFragment.class);
                intent.putExtra("value",s);
                startActivity(intent);*/


    }

    public void clickedButton(View v){
        //BottomNavigationFragment fragment = ( BottomNavigationFragment) getFragmentManager().findFragmentById(R.id.activity_main);
        //fragment.onDestroy();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, new SimulationEndFragment());
        fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit();

    }

    public void pickerButton(View v){

        Toast.makeText(MainActivity.this,"You Clicked : inside the boombaby",Toast.LENGTH_SHORT).show();
        /*MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(MainActivity.this)
                .minValue(1)
                .maxValue(10)
                .defaultValue(1)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();*/
       /* new AlertDialog.Builder(MainActivity.this)
                .setTitle("Picker try")
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Snackbar.make(findViewById(R.id.your_container), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                    }
                })
                .show();*/
    }
    public void popupMenu(View v){
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this,(ImageView) findViewById(R.id.imageMenu));
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu_exercise, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.two:

                        break;


                }
                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



}
