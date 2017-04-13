package jumapp.com.smartest.Exercise.ViewManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jumapp.com.smartest.ContestSingleton;
import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.Exercise.Pair;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.CustomRecyclerViewAdapter;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;

/**
 * Created by marco on 13/04/2017.
 */
public class AlertContestSingleton {
    private long countDownInterval;
    private SweetAlertDialog pDialog;
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ExerciseAdapter adapter;
    private  ArrayList<Pair> pairs;
    private  View view;
    private ViewPager viewPager;

    public AlertContestSingleton(Context context,View view, ViewPager viewPager,long pCountDownInterval, ExerciseAdapter adapter,  ArrayList<Pair> pairs) {
        this.countDownInterval = pCountDownInterval;
        this.context = context;
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        this.adapter=adapter;
        this.pairs=pairs;
        this.view=view;

        this.pDialog = new SweetAlertDialog(context,
                SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("L'esercitazione sta per iniziare!");
        pDialog.show();
        pDialog.setCancelable(false);
        this.viewPager=viewPager;


    }

    public void Start() {
        final Handler handler = new Handler();
        Log.v("status", "starting");
        final Runnable counter = new Runnable() {

            public void run() {
                pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));

                if (prefs.getInt("contest_singleton_id",0)==1) {
                    pDialog.setTitleText("Inizia l'esercitazione!")
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    handler.removeCallbacks(this);
                    adapter.addAllQuestion(ContestSingleton.getInstance(context).getRandomQuestions(pairs));
                    viewPager.setAdapter(adapter);
                    viewPager.setOnPageChangeListener(new FinishViewPagerHandler(viewPager, (Activity)context));

                    RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) view.findViewById(R.id.recycler_tab_layout_exercise);
                    recyclerTabLayout.setUpWithAdapter(new CustomRecyclerViewAdapter(viewPager));

                } else {
                    handler.postDelayed(this, countDownInterval);
                }


            }
        };

        handler.postDelayed(counter, countDownInterval);
    }
}
