package jumapp.com.smartest.Exercise.ViewManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import jumapp.com.smartest.Exercise.Adapters.GridShortDescAdapt;
import jumapp.com.smartest.Exercise.Pair;
import jumapp.com.smartest.Exercise.Singleton.DictionaryCategoryNumberSingleton;
import jumapp.com.smartest.R;

/**
 * Created by marco on 07/04/2017.
 */
public class ExerciseTextViewManager {

    private View view;
    private TextView textView;
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private long contest_id;

    public ExerciseTextViewManager(View view,  TextView textView){
        this.view=view;
        this.textView=textView;
        this.context=view.getContext();
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        contest_id=prefs.getLong("contest_selected",0);
        editor = prefs.edit();

    }

    public void init(String t){
        final String text=t;
        textView.setText(text);
        final LinearLayout linearExer = (LinearLayout) view.findViewById(R.id.slider_simulation_exer_layout);
        final LinearLayout exer_linear_animation= (LinearLayout) view.findViewById(R.id.exer_linear_animation);
        final ScrollView scroll= (ScrollView) view.findViewById(R.id.scroll_view_categorie_exercise);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable dr = ContextCompat.getDrawable(context, R.drawable.shape_green);
                textView.setBackground(dr);
                exer_linear_animation.setVisibility(View.VISIBLE);

                editor.putString("exercise_text_selected", "" + textView.getText());
                editor.commit();

                GridView grid= (GridView) view.findViewById(R.id.grid_layout_short_descr);
                DictionaryCategoryNumberSingleton.getInstance().add(new Pair(""+textView.getText(),1));
                Log.i("###","TEXT MANAGER size: "+DictionaryCategoryNumberSingleton.getInstance().getPairs().size());
                GridShortDescAdapt adapter = new GridShortDescAdapt(context,DictionaryCategoryNumberSingleton.getInstance().getPairs(),view);
                grid.setAdapter(adapter);

                ViewGroup.LayoutParams params = scroll.getLayoutParams();
                if (scroll.getHeight() > (linearExer.getHeight() * 0.6)) {
                    int new_height = (int) (linearExer.getHeight() * 0.5);
                    params.height = new_height;
                    scroll.setLayoutParams(params);
                    Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.entrance);
                    exer_linear_animation.startAnimation(myAnim);
                }
            }
        });


    }

}
