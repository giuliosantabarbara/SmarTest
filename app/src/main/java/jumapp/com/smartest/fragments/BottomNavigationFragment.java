package jumapp.com.smartest.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;


import jumapp.com.smartest.ContestSingleton;
import jumapp.com.smartest.Exercise.ExerciseFragment;
import jumapp.com.smartest.Exercise.ViewManager.ExerciseProgressBarManager;
import jumapp.com.smartest.Exercise.ViewManager.ExerciseTextViewManager;
import jumapp.com.smartest.QuestionViewer.DragSelecter.FragmentDragSelecter;
import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;

import jumapp.com.smartest.R;
import jumapp.com.smartest.Statistics.Adapters.CategoriesStatisticAdapter;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;
import jumapp.com.smartest.activities.MainActivity;

import jumapp.com.smartest.Statistics.ViewManager.StatisticsManager;


/**
 * Created by giuli on 09/02/2017.
 */

public class BottomNavigationFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    Context context;
    private View main_view;
    private int contest_id = 1;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private QuestionDAO quest;
    public static ArrayList<Question> questionsByCategory = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.activity_horizontal_ntb, container, false);
        context = main_view.getContext();

        ContestSingleton.getInstance(context);

        initUI(main_view);
        return main_view;
    }

    //Setter bottom navigation
    private void initUI(View view) {
        prefs = getActivity().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        quest = new QuestionDAOImpl(context);

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
                View view = null;
                switch (position) {
                    case 0:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_home, null, false);
                        ((TextView) view.findViewById(R.id.slider_content_text)).setMovementMethod(new ScrollingMovementMethod());
                        container.addView(view);

                        break;
                    case 1:
                        Log.i("###", "SONO NEL CASO 1");

                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_studio_esercitazione, null, false);
                        container.addView(view);

                        //get the names and studying percentage of each category to fill the statistic view
                        final SQLiteDatabase db = quest.openReadableConnection();
                        ArrayList<Integer> array_list = quest.getPercentageStudiedByCategory(1,db);
                        int[] result = new int[array_list.size()];
                        int m = 0;
                        for (Integer in : array_list) {
                            Log.i("###", "integer: " + in);
                            result[m] = in;
                            m++;
                        }

                        final ArrayList<String> names = quest.getAllCategoriesByContestId(contest_id,db);
                        int[] n = new int[names.size()];
                        String[] nam = new String[names.size()];
                        int k = 0;
                        for (String s : names) {
                            nam[k] = s;
                            n[k] = 3;
                            k++;

                        }




                        final ListView myList = (ListView) getActivity().findViewById(R.id.listViewCategorie);
                        CategoriesStatisticAdapter ad = new CategoriesStatisticAdapter(context,nam ,n);
                        myList.setAdapter(ad);



                        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                long start=  System.currentTimeMillis();;
                                questionsByCategory = quest.getAllQuestionByCategoryAndContestId(contest_id, names.get(position),db);
                                long end=  System.currentTimeMillis();
                                Log.i("LLL Total time",""+(end-start));

                                Log.i("####", "Finito" + questionsByCategory.get(0).getText());
                                QuestionsSingleton.getInstance().setQuestions(questionsByCategory);
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                FragmentDragSelecter fr = new FragmentDragSelecter();
                                fragmentTransaction.add(R.id.activity_main, fr);
                                fragmentTransaction.addToBackStack("back");
                                fragmentTransaction.commit();

                            }
                        });

                        db.close();
                        break;

                    case 2:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_simulazione_esercitazione, null, false);
                        container.addView(view);

                        SQLiteDatabase conn = quest.openReadableConnection();
                        ArrayList<String> categoriesName = quest.getAllCategoriesByContestId(contest_id,conn);
                        conn.close();
                        final LinearLayout linearExer = (LinearLayout) view.findViewById(R.id.slider_simulation_exer_layout);
                        final LinearLayout linearFrame= (LinearLayout) view.findViewById(R.id.category_exercise_frame_linear_layout);
                        final ScrollView scroll= (ScrollView) view.findViewById(R.id.scroll_view_categorie_exercise);
                        Button startExer= (Button) view.findViewById(R.id.button_short_exercise);

                        final DiscreteSeekBar progressBar= (DiscreteSeekBar) view.findViewById(R.id.exercise_progress_bar);
                        ExerciseProgressBarManager progressBarManager= new ExerciseProgressBarManager(view,progressBar);
                        progressBarManager.init();

                        scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                ViewGroup.LayoutParams params = scroll.getLayoutParams();
                                params.height =(int) (linearExer.getHeight() * 0.95) ;
                                scroll.setLayoutParams(params);
                                scroll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }
                        });

                        for(String catName: categoriesName){
                            final View viewQuest = LayoutInflater.from(container.getContext()).inflate(R.layout.exercise_category_list, container, false);
                            final TextView txt = (TextView) viewQuest.findViewById(R.id.exercise_category_textV);
                            ExerciseTextViewManager textManager= new ExerciseTextViewManager(view,txt);
                            textManager.init(catName);
                            linearFrame.addView(viewQuest);
                        }

                        startExer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                ExerciseFragment fr = new ExerciseFragment();
                                fragmentTransaction.add(R.id.activity_main, fr);
                                fragmentTransaction.addToBackStack("back");
                                fragmentTransaction.commit();
                            }
                        });
                        break;

                    case 3:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_content_statistics, null, false);
                        container.addView(view);

                        ViewPagerItemAdapter adapter = new ViewPagerItemAdapter(ViewPagerItems.with(context)
                                .add(R.string.es, R.layout.layout_page_exercise)
                                .add(R.string.ex, R.layout.layout_page_simulation)
                                .create());
                        final ViewPager viewPagerStatistics = (ViewPager) view.findViewById(R.id.viewpager);
                        viewPagerStatistics.setAdapter(adapter);

                        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
                        viewPagerTab.setViewPager(viewPagerStatistics);

                        StatisticsManager manager = new StatisticsManager(context,contest_id);
                        manager.init(viewPagerTab,getView(),viewPagerStatistics);

                        break;
                    case 4:
                        view = LayoutInflater.from(getActivity().getBaseContext()).inflate(R.layout.slider_picker_color, null, false);
                        container.addView(view);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ColorPickerFragment fr = new ColorPickerFragment();
                        fragmentTransaction.add(R.id.activity_main, fr);
                        fragmentTransaction.addToBackStack("back");
                        fragmentTransaction.commit();
                        /*Button b = (Button) getActivity().findViewById(R.id.button);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent myIntent = new Intent(getActivity(), StudyPlanIntro.class);
                                ((MainActivity) getActivity()).startActivity(myIntent);
                            }
                        });*/
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