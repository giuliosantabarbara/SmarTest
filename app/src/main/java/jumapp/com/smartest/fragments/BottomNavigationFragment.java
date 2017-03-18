package jumapp.com.smartest.fragments;

import android.app.AlertDialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import devlight.io.library.ntb.NavigationTabBar;
import jumapp.com.smartest.R;
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
                String[] nameproducts = new String[] { "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                int[] num = new int[] { 10, 20, 5, 33, 75, 12,35 };
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
                        RadioGroup rgp = (RadioGroup) view.findViewById(R.id.radio_group);
                        int buttons = 30;
                        for (int i = 1; i <= buttons ; i++) {
                            RadioButton rbn = new RadioButton(context);
                            rbn.setId(i + 1000);
                            rbn.setText("RadioButton" + i);
                            rgp.addView(rbn);
                        }
                        final ScrollView scrollView = (ScrollView)view.findViewById(R.id.scrollview_choices);
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                View radioGroup = getActivity().findViewById(R.id.radio_group);
                                scrollView.setFocusable(false);
                                //scrollView.scrollTo(0, radioGroup.getBottom());
                            }
                        });

                        Button button = (Button)getActivity().findViewById(R.id.button_esercitazione);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.activity_main, new ExerciseFragment());
                                fragmentTransaction.addToBackStack("back");
                                fragmentTransaction.commit();
                               /*Intent myIntent = new Intent(getActivity(), ExerciseActivity.class);
                                ((MainActivity)getActivity()).startActivity(myIntent);*/
                            }
                        });

                        Button buttonPicker = (Button)getActivity().findViewById(R.id.buttonPickerNumber);
                        buttonPicker.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(context ,"You Clicked : inside the boombaby",Toast.LENGTH_SHORT).show();

                               /* LinearLayout linearLayoutVertical = new LinearLayout(context);
                                linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);*/

                                LinearLayout LL = new LinearLayout(context);
                                LL.setOrientation(LinearLayout.HORIZONTAL);


                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
                                params.gravity = Gravity.CENTER;

                                LinearLayout.LayoutParams numPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                numPicerParams.weight = 1;

                                LinearLayout.LayoutParams qPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                qPicerParams.weight = 1;

                                /*TextView textView1 = new TextView(context);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.gravity = Gravity.CENTER;
                                layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
                                textView1.setLayoutParams(layoutParams);
                                textView1.setText("Da");
                                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                //textView1.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB

                                TextView textView2 = new TextView(context);
                                layoutParams.gravity = Gravity.RIGHT;
                                layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
                                textView2.setLayoutParams(layoutParams);
                                textView2.setText("A");
                                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);*/


                                MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(context)
                                    .minValue(1)
                                    .maxValue(10)
                                    .defaultValue(1)
                                    .backgroundColor(Color.WHITE)
                                    .separatorColor(Color.TRANSPARENT)
                                    .textColor(Color.BLACK)
                                    .textSize(20)
                                    .enableFocusability(false)
                                    .wrapSelectorWheel(true)
                                     .build();

                                MaterialNumberPicker numberPickerB = new MaterialNumberPicker.Builder(context)
                                        .minValue(1)
                                        .maxValue(10)
                                        .defaultValue(1)
                                        .backgroundColor(Color.WHITE)
                                        .separatorColor(Color.TRANSPARENT)
                                        .textColor(Color.BLACK)
                                        .textSize(20)
                                        .enableFocusability(false)
                                        .wrapSelectorWheel(true)
                                        .build();

                                LL.setLayoutParams(params);

                                /*linearLayoutVertical.addView(textView1);
                                linearLayoutVertical.addView(textView2);*/

                                LL.addView(numberPicker,numPicerParams);
                                LL.addView(numberPickerB,qPicerParams);
                                //linearLayoutVertical.addView(LL.getRootView());

                                new AlertDialog.Builder(context)
                                    .setTitle("Picker try")
                                    .setView(LL)
                                    .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                            //Snackbar.make(findViewById(R.id.your_container), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                                     }
                                 })
                                .show();

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

                                    String[] nameproducts = new String[]{"Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica", "Logica"};
                                    int[] num = new int[]{10, 20, 5, 33, 75, 12, 35};
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

                                        String[] nameproducts = new String[] { "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                                        int[] num = new int[] { 10, 20, 5, 33, 75, 12,35 };
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
                                                String[] nameproducts = new String[] { "Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica" , "Logica" };
                                                int[] num = new int[] { 10, 20, 5, 33, 75, 12,35 };
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
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Home")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Studio")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Simulazione")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Statistiche")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
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