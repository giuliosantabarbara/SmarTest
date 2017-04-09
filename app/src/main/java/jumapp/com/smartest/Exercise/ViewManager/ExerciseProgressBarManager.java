package jumapp.com.smartest.Exercise.ViewManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import jumapp.com.smartest.Exercise.Adapters.GridShortDescAdapt;
import jumapp.com.smartest.Exercise.Pair;
import jumapp.com.smartest.Exercise.Singleton.DictionaryCategoryNumberSingleton;
import jumapp.com.smartest.R;

/**
 * Created by marco on 07/04/2017.
 */
public class ExerciseProgressBarManager {

    DiscreteSeekBar progressBar;
    private View view;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public ExerciseProgressBarManager(View view,DiscreteSeekBar progressBar){
        this.progressBar=progressBar;
        this.view=view;
        prefs = view.getContext().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public  void init(){


        progressBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                Log.i("###", "Valore nel progress " + value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                Log.i("###", "Sono nel on start");


            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                String category=prefs.getString("exercise_text_selected", null);
                int number=seekBar.getProgress();
                DictionaryCategoryNumberSingleton.getInstance().add(new Pair(category, number));

                GridView grid= (GridView) view.findViewById(R.id.grid_layout_short_descr);
                GridShortDescAdapt adapter = new GridShortDescAdapt(view.getContext(),DictionaryCategoryNumberSingleton.getInstance().getPairs(),view);
                grid.setAdapter(adapter);
                //bisogna prendere tante domande randomiche quante quelle indicate fare una query?
            }
        });
    }
}
