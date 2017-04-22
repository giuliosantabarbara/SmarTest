package jumapp.com.smartest.Exercise;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigamole.library.ArcProgressStackView;

import java.util.ArrayList;
import java.util.Random;

import jumapp.com.smartest.R;


/**
 * Created by giuli on 10/03/2017.
 */

public class SimulationEndFragment extends Fragment {
    public final static int MODEL_COUNT = 3;
    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];
    private ArcProgressStackView mArcProgressStackView;
    Context context;
    int rightF,wrongF,notAnsweredF,totalF;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_end_simulation, container, false);
        context = view.getContext();

        Bundle bundle = this.getArguments();
        this.rightF=bundle.getInt("right");
        this.wrongF=bundle.getInt("wrong");
        this.notAnsweredF=bundle.getInt("notSetted");
        this.totalF=bundle.getInt("total");

        TextView textCorrect=(TextView)view.findViewById(R.id.textCorrect_m);
        TextView textWrong=(TextView)view.findViewById(R.id.textWrong_m);
        TextView textNotAnswered=(TextView)view.findViewById(R.id.textNotAnswered_m);
        TextView textOutcome=(TextView)view.findViewById(R.id.outcome);
        textCorrect.setText("Corrette: "+rightF);
        textWrong.setText("Sbagliate: "+wrongF);
        textNotAnswered.setText("Non date: "+notAnsweredF);
        if(rightF>totalF/2) {
            textOutcome.setText("Superato!");
            textOutcome.setTextColor(Color.GREEN);
        }
        else{
            textOutcome.setText("Non Superato!");
            textOutcome.setTextColor(Color.RED);
        }

        final String[] bgColors = getResources().getStringArray(R.array.medical_express);
        final String[] startColors = getResources().getStringArray(R.array.questions);
        final String[] endColors = getResources().getStringArray(R.array.default_preview);
        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }
        mArcProgressStackView = (ArcProgressStackView) view.findViewById(R.id.apsv);
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Corrette", 0, Color.TRANSPARENT, mStartColors[0]));
        models.add(new ArcProgressStackView.Model("Sbagliate", 0, Color.TRANSPARENT, mStartColors[1]));
        models.add(new ArcProgressStackView.Model("Non risposte", 0, Color.TRANSPARENT, mStartColors[2]));


        mArcProgressStackView.setModels(models);

        final float [] numbers= new float[3];

        float right= Float.parseFloat(""+rightF);
        float total=Float.parseFloat(""+totalF);
        float wrong=Float.parseFloat(""+wrongF);
        float notAnswered=Float.parseFloat(""+notAnsweredF);

        numbers[0]=right/total;
        numbers[1]=wrong/total;
        numbers[2]=notAnswered/total;

        // Start apsv animation on start
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels()) {
                     model.setProgress(( Math.round(numbers[i] * 100)));
                    i++;
                }

                mArcProgressStackView.animateProgress();
            }
        }, 333);

        return view;
    }
}
