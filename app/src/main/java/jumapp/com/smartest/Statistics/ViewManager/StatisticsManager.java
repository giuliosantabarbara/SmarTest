package jumapp.com.smartest.Statistics.ViewManager;

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

import java.util.ArrayList;

import jumapp.com.smartest.R;
import jumapp.com.smartest.Statistics.Singleton.ExerciseStatisticsSingleton;
import jumapp.com.smartest.Statistics.Singleton.SimulationStatisticsSingleton;
import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.ExerciseDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl.SimulationDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationDAO;
import jumapp.com.smartest.Statistics.Adapters.GridAdapter;
import jumapp.com.smartest.Statistics.ViewUtils.GridViewManager;
import jumapp.com.smartest.Statistics.Adapters.SimulationStatisticAdapter;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;

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

    /**
     * Instantiates a new Statistics manager.
     *
     * @param context   the context
     * @param contestId the contest id
     */
    public StatisticsManager(Context context, long contestId){
        this.context=context;
        this.contestId=contestId;
        this.simulationDao = new SimulationDAOImpl(context);
        this.exerciseDao = new ExerciseDAOImpl(context);
        this.simulationsSingleton = SimulationStatisticsSingleton.getInstance(this.simulationDao,contestId);
        this.exercisesSingleton = ExerciseStatisticsSingleton.getInstance(this.exerciseDao,contestId);
    }

    /**
     * Init.
     *
     * @param viewPagerTab        the view pager tab
     * @param view                the view
     * @param viewPagerStatistics the view pager statistics
     */
    public void init(SmartTabLayout viewPagerTab, final View view, final ViewPager viewPagerStatistics){

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean first = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && first) {
                    GridView gridView = (GridView) view.findViewById(R.id.gridView);
                    ArrayList<Exercise> ex = exercisesSingleton.getExercises();
                    if (ex.size()!=0){
                        GridAdapter gridAdapter = new GridAdapter(context, R.layout.grid_view_exercise_statistic, GridViewManager.getGridType(ex));
                        gridView.setAdapter(gridAdapter);
                    }

                    first = false;
                }
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
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
                        break;
                    case 1:
                        setSimulation(view, viewPagerStatistics);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSimulation(final View view, final ViewPager viewPagerStatistics){
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
                stat_linear_animation.setVisibility(View.VISIBLE);

                TextView simulationSpecs = (TextView)view.findViewById(R.id.textViewSimulation);
                simulationSpecs.setText("Dettagli Simulazione "+(position+1));
                GridView gridView = (GridView) view.findViewById(R.id.gridLayoutStatisticsRecap);

                Simulation sm = simulationsSingleton.getSimulationByIndex(position);
                if (sm!=null){
                    GridAdapter gridAdapter = new GridAdapter(context, R.layout.grid_view_exercise_statistic, GridViewManager.getGridType(sm.getSimulationCategories()));
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

            }
        });
    }
}
