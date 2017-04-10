package jumapp.com.smartest.utility;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.ExerciseDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.SimulationDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationDAO;
import jumapp.com.smartest.adapters.AdapterGrid;
import jumapp.com.smartest.adapters.GridViewManager;
import jumapp.com.smartest.adapters.SimulationStatisticAdapter;

/**
 * Created by giulio on 08/04/17.
 */

public class StatisticsManager {

    private Context context;
    private long contestId;
    private final SimulationStatisticsSingleton simulationsSingleton;
    private final ExerciseStatisticsSingleton exercisesSingleton;
    private SimulationDAO simulationDao;
    private ExerciseDAO exerciseDao;

    public StatisticsManager(Context context, long contestId){
        this.context=context;
        this.contestId=contestId;
        this.simulationDao = new SimulationDAOImpl(context);
        this.exerciseDao = new ExerciseDAOImpl(context);
        this.simulationsSingleton = new SimulationStatisticsSingleton(this.simulationDao,contestId);
        this.exercisesSingleton = new ExerciseStatisticsSingleton(this.exerciseDao,contestId);


    }

    public void init(SmartTabLayout viewPagerTab, final View view, final ViewPager viewPagerStatistics){

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean first = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && first) {

                    GridView gridView = (GridView) view.findViewById(R.id.gridView);
                    AdapterGrid gridAdapter = new AdapterGrid(context, R.layout.grid_view_exercise_statistic, GridViewManager.getGridType(exercisesSingleton.getExercises()));
                    gridView.setAdapter(gridAdapter);

                                    /*final GridView gridView = (GridView)getView().findViewById(R.id.gridview);
                                    AdapterGrid a = new AdapterGrid(context);
                                    gridView.setAdapter(a);*/

                                    /*String[] nameproducts = new String[]{"Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica", "Logica"};
                                    int[] num = new int[]{10, 20, 5, 33, 75, 12, 35};
                                    final ListView myListStatistics = (ListView) getView().findViewById(R.id.listViewExerciseStatistics);
                                    ExercisesStatisticAdapter st = new ExercisesStatisticAdapter(context, nameproducts, num);
                                    myListStatistics.setAdapter(st);*/
                    first = false;

                }
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:

                        /*
                        final ArrayList<ExerciseStatisticItem> items = new ArrayList<>();
                        String[] nameproducts = new String[]{"Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica", "Logica"};
                        int[] num = new int[]{10, 20, 5, 33, 75, 12, 35};
                                        /*final ListView myListStatistics = (ListView) getView().findViewById(R.id.listViewExerciseStatistics);
                                        ExercisesStatisticAdapter st = new ExercisesStatisticAdapter(context, nameproducts, num);
                                        myListStatistics.setAdapter(st);*/
                        /*for (int i = 0; i < nameproducts.length; i++) {
                            ExerciseStatisticItem s = new ExerciseStatisticItem(nameproducts[i],num[i]);
                            items.add(s);
                        }

                        GridView gridView = (GridView) getView().findViewById(R.id.gridView);
                        AdapterGrid gridAdapter = new AdapterGrid(context, R.layout.grid_view_exercise_statistic, items);
                        gridView.setAdapter(gridAdapter);*/
                        break;
                    case 1:



                        final ListView myListStatisticsSim = (ListView) view.findViewById(R.id.listViewSimulationStatistics);
                        SimulationStatisticAdapter stSim = new SimulationStatisticAdapter(context, simulationsSingleton.getSimulations());
                        myListStatisticsSim.setAdapter(stSim);

                        final LinearLayout linearExer = (LinearLayout) view.findViewById(R.id.slider_content_statistics);
                        viewPagerStatistics.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                ViewGroup.LayoutParams params = viewPagerStatistics.getLayoutParams();
                                params.height =(int) (linearExer.getHeight() * 0.95) ;
                                viewPagerStatistics.setLayoutParams(params);
                                viewPagerStatistics.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }
                        });

                        myListStatisticsSim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View viewnew, int position, long id) {
                                final LinearLayout stat_linear_animation = (LinearLayout) view.findViewById(R.id.statistics_linear_animation);
                                //Drawable dr = ContextCompat.getDrawable(context, R.drawable.shape_green);
                                //textView.setBackground(dr);
                                //myListStatisticsSim.setVisibility(View.GONE);
                                stat_linear_animation.setVisibility(View.VISIBLE);

                                //editor.putString("exercise_text_selected", "" + textView.getText());
                                //editor.commit();

                                TextView simulationSpecs = (TextView)view.findViewById(R.id.textViewSimulation);
                                simulationSpecs.setText("Dettagli Simulazione "+(position+1));
                                GridView gridView = (GridView) view.findViewById(R.id.gridLayoutStatisticsRecap);

                                AdapterGrid gridAdapter = new AdapterGrid(context, R.layout.grid_view_exercise_statistic, GridViewManager.getGridType(simulationsSingleton.getSimulationByIndex(position).getSimulationCategories()));
                                gridView.setAdapter(gridAdapter);
                                ViewGroup.LayoutParams params = viewPagerStatistics.getLayoutParams();
                                if (myListStatisticsSim.getHeight() > (linearExer.getHeight() * 0.6)) {
                                    int new_height = (int) (linearExer.getHeight() * 0.43);
                                    params.height = new_height;
                                viewPagerStatistics.setLayoutParams(params);
                                    Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.entrance);
                                    stat_linear_animation.startAnimation(myAnim);
                                }
                            }
                        });

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
                        /*myListStatisticsSim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String[] nameproducts = new String[]{"Storia", "Matematica", "Attualità", "Geometria", "Geografia", "Grammatica", "Logica"};
                                int[] num = new int[]{10, 20, 5, 33, 75, 12, 35};


                                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                                AdapterGrid gridAdapter = new AdapterGrid(context, R.layout.grid_view_exercise_statistic, GridViewManager.getGridType(simulationsSingleton.getSimulationByIndex(position).getSimulationCategories()));
                                gridView.setAdapter(gridAdapter);

                            }
                        });*/
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
}
