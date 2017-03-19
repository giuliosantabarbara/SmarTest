package jumapp.com.smartest.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import java.util.ArrayList;
import java.util.Random;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import devlight.io.library.ntb.NavigationTabBar;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOInterface.QuestionDAO;
import jumapp.com.smartest.activities.MainActivity;
import jumapp.com.smartest.activities.StudyPlanIntro;
import jumapp.com.smartest.adapters.CategoriesStatisticAdapter;
import jumapp.com.smartest.adapters.ExercisesStatisticAdapter;
import jumapp.com.smartest.adapters.SimulationStatisticAdapter;


/**
 * Created by giuli on 09/02/2017.
 */

public class BottomNavigationFragment extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_horizontal_ntb, container,false);
        context = view.getContext();

        //mAgendaCalendarView = ButterKnife.findById(LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.calendar_view, null, false), R.id.agenda_calendar_view);
        //Log.i("Valore inizioAgenda: ",""+mAgendaCalendarView);
       // Log.i("Valore mAgenda fuori: ",""+mAgendaCalendarView);

        initUI(view);
        return view;
    }

    //Setter bottom navigation
    private void initUI(View view) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_horizontal_ntb);
        /*mAgendaCalendarView= (AgendaCalendarView) view.findViewById(R.id.agenda_calendar_view);
        Log.i("Valore inizioAgenda: ",""+mAgendaCalendarView);*/
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                View view=null;
                final QuestionDAO qst= new QuestionDAOImpl(context);
               final ArrayList<String> str = qst.getAllCategoriesByContestId(1);
                final String[] nameproducts = new String[str.size()]; //{ "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                final int[] num = new int[str.size()];
                Random r = new Random();
                for (int i=0; i<num.length;i++){
                    num[i] = r.nextInt(100);
                }
                for (int i=0; i<str.size();i++){
                    nameproducts[i] = str.get(i);
                    Log.i("AAAAAAAA: ",""+str.get(i));
                }
                switch(position){
                    case 0:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_home, null, false);
                        container.addView(view);

                        break;
                    case 1:

                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_studio_esercitazione, null, false);
                        container.addView(view);


                        /*final ArrayList<String> listp = new ArrayList<String>();
                        for (int i = 0; i < nameproducts.length; ++i) {
                            listp.add(nameproducts[i]);
                        }*/
                        final ListView myList = (ListView)getActivity().findViewById(R.id.listViewCategorie);

                        CategoriesStatisticAdapter ad = new CategoriesStatisticAdapter(context, nameproducts,num);
                        myList.setAdapter(ad);


                       /* final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_view_style,R.id.text1, listp);
                        myList.setAdapter(adapter);*/



                        /*final ArrayList<Float> listprogress = new ArrayList<Float>();
                        for (int i = 0; i < nameproducts.length; ++i) {
                            listprogress.add(20.5f);
                        }

                        final ListView myListProgress = (ListView)getActivity().findViewById(R.id.listViewCategorie);
                        final ArrayAdapter<Float> adapterProgress = new ArrayAdapter<Float>(getActivity(),R.layout.list_view_style,R.id.progress, listprogress);
                        myListProgress.setAdapter(adapter);*/

                        break;
                    case 2:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_simulazione_esercitazione, null, false);
                        container.addView(view);


                        final ViewPagerItemAdapter adapterDue = new ViewPagerItemAdapter(ViewPagerItems.with(context)
                                .add(R.string.es, R.layout.layout_holder_exercise)
                                .add(R.string.ex, R.layout.layout_holder_simulation)
                                .create());
                        final ViewPager viewPagerDue = (ViewPager) view.findViewById(R.id.viewpagerSimulazioneEsercitazione);
                        viewPagerDue.setAdapter(adapterDue);
                        SmartTabLayout viewPagerTabDue = (SmartTabLayout) view.findViewById(R.id.viewpagertabSimulazioneEsercitazione);
                        viewPagerTabDue.setViewPager(viewPagerDue);


                        viewPagerTabDue.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                            boolean first = true;
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                if(position==0 && first){
                                    View v = adapterDue.getPage(position);
                                    first=false;
                                    RadioGroup rgp = (RadioGroup) v.findViewById(R.id.radio_group);

                                    for (int i = 1; i < str.size(); i++) {
                                        RadioButton rbn = new RadioButton(context);
                                        rbn.setId(i + 1000);
                                        rbn.setText(str.get(i));
                                        rgp.addView(rbn);
                                    }
                                    final ScrollView scrollView = (ScrollView)v.findViewById(R.id.scrollview_choices);
                                    scrollView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            View radioGroup = getActivity().findViewById(R.id.radio_group);
                                            scrollView.setFocusable(false);
                                            //scrollView.scrollTo(0, radioGroup.getBottom());
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onPageSelected(int position) {
                                View v = adapterDue.getPage(position);

                               switch(position){
                                    case 0:



                                        break;
                                    case 1:
                                        String[] name = new String[] { "Simulazione 1", "Simulazione 2", "Simulazione 3", "Simulazione 4" };
                                        int[] nume = new int[] { 75, 25, 55, 33, 75, 12,35 };
                                        final ListView myListStatisticsSim = (ListView) getView().findViewById(R.id.listViewExameStatistics);
                                        SimulationStatisticAdapter stSim = new   SimulationStatisticAdapter(context, name,nume);
                                        myListStatisticsSim.setAdapter(stSim);

                                        myListStatisticsSim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                final ListView myListStatistics = (ListView) view.findViewById(R.id.listViewExameStatistics);
                                                SimulationStatisticAdapter stSim = new   SimulationStatisticAdapter(context, nameproducts,num);
                                                myListStatisticsSim.setAdapter(stSim);
                                            }
                                        });
                                        break;
                                }

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });





                        break;
                    case 3:

                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_statistics, null, false);
                        container.addView(view);



                        ViewPagerItemAdapter adapter = new ViewPagerItemAdapter(ViewPagerItems.with(context)
                                .add(R.string.es, R.layout.layout_page_exercise)
                                .add(R.string.ex, R.layout.layout_page_exame)
                                .create());
                        final ViewPager viewPagerStatistics = (ViewPager) view.findViewById(R.id.viewpager);
                        viewPagerStatistics.setAdapter(adapter);


                        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
                        viewPagerTab.setViewPager(viewPagerStatistics);

                        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            boolean first = true;
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                if(position==0 && first){

                                    //String[] nameproducts = new String[]{"Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica", "Logica"};
                                    //int[] num = new int[]{10, 20, 5, 33, 75, 12, 35};
                                    final ListView myListStatistics = (ListView) getView().findViewById(R.id.listViewExerciseStatistics);
                                    ExercisesStatisticAdapter st = new ExercisesStatisticAdapter(context, nameproducts, num);
                                    myListStatistics.setAdapter(st);
                                    first=false;

                                }
                            }

                            @Override
                            public void onPageSelected(int position) {

                                switch(position){
                                    case 0:

                                       //String[] nameproducts = new String[] { "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                                        //int[] num = new int[] { 10, 20, 5, 33, 75, 12,35 };
                                        final ListView myListStatistics = (ListView) getView().findViewById(R.id.listViewExerciseStatistics);
                                        ExercisesStatisticAdapter st = new ExercisesStatisticAdapter(context, nameproducts,num);
                                        myListStatistics.setAdapter(st);
                                        break;
                                    case 1:
                                        String[] name = new String[] { "Simulazione 1", "Simulazione 2", "Simulazione 3", "Simulazione 4" };
                                        int[] nume = new int[] { 75, 25, 55, 33, 75, 12,35 };
                                        final ListView myListStatisticsSim = (ListView) getView().findViewById(R.id.listViewExameStatistics);
                                        SimulationStatisticAdapter stSim = new   SimulationStatisticAdapter(context, name,nume);
                                        myListStatisticsSim.setAdapter(stSim);
                                        /*myListStatisticsSim.setOnKeyListener(new View.OnKeyListener() {
                                            @Override
                                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                String[] name = new String[] { "Simulazione 1", "Simulazione 2", "Simulazione 3", "Simulazione 4" };
                                                int[] nume = new int[] { 75, 25, 55, 33, 75, 12,35 };
                                                if(event.getAction() == KeyEvent.ACTION_DOWN)
                                                {
                                                     ListView listView = (ListView) v;

                                                    switch(keyCode)
                                                    {
                                                        case KeyEvent.KEYCODE_BACK:
                                                            SimulationStatisticAdapter stSim = new   SimulationStatisticAdapter(context, name,nume);
                                                            myListStatisticsSim.setAdapter(stSim);
                                                            break;
                                                    }
                                                }

                                                return false;
                                            }
                                        });*/
                                        myListStatisticsSim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                //String[] nameproducts = new String[] { "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                                                //int[] num = new int[] { 10, 20, 5, 33, 75, 12,35 };
                                                final ListView myListStatistics = (ListView) view.findViewById(R.id.listViewExameStatistics);
                                                SimulationStatisticAdapter stSim = new   SimulationStatisticAdapter(context, nameproducts,num);
                                                myListStatisticsSim.setAdapter(stSim);
                                            }
                                        });
                                        break;
                                }

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });

                        break;
                    case 4:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.bottone, null, false);
                        container.addView(view);
                        Button b = (Button)getActivity().findViewById(R.id.button);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent myIntent = new Intent(getActivity(), StudyPlanIntro.class);
                                ((MainActivity)getActivity()).startActivity(myIntent);
                            }
                        });
                        break;
                       /* view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.calendar_view, null, false);
                        container.addView(view);
                        Toast.makeText(context,"Sono in 3", Toast.LENGTH_SHORT).show();
                        break;*/
                    default:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_home, null, false);
                        break;
                }

              //  final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
             //   txtPage.setText(String.format("Page #%d", position));
          //      JustifiedTextView myMsg = (JustifiedTextView) view.findViewById(R.id.t1);
            //    myMsg.setText(getActivity().getResources().getString(R.string.descrizione_concorso));



                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview_here);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) view.findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.library_books),
                        Color.parseColor(colors[0]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Home")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.lamp),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Studio")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.test_exam),
                        Color.parseColor(colors[2]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Simulazione")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.statistics),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Statistiche")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.wall_schedule),
                        Color.parseColor(colors[4]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Piano di Studio")
                        .badgeTitle("777")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                navigationTabBar.getModels().get(position).hideBadge();

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPickerNumber:

                break;
        }
    }
}
