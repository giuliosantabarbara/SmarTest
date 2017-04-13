package jumapp.com.smartest.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    int right,wrong,notAnswered,total;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_end_simulation, container, false);
        context = view.getContext();

        Bundle bundle = this.getArguments();
        this.right=bundle.getInt("right");
        this.wrong=bundle.getInt("wrong");
        this.notAnswered=bundle.getInt("notSetted");
        this.total=bundle.getInt("total");

        TextView textCorrect=(TextView)view.findViewById(R.id.textViewCorrect);
        TextView textWrong=(TextView)view.findViewById(R.id.textViewWrong);
        TextView textNotAnswered=(TextView)view.findViewById(R.id.textNotAnswered);
        TextView textOutcome=(TextView)view.findViewById(R.id.outcome);
        textCorrect.setText(""+right);
        textWrong.setText(""+wrong);
        textNotAnswered.setText(""+notAnswered);
        if(right>total/2) {
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
        numbers[0]=right/total;
        numbers[1]=wrong/total;
        numbers[2]=notAnswered/total;

        // Start apsv animation on start
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels()) {
                    model.setProgress(numbers[i]);
                    i++;
                }

                mArcProgressStackView.animateProgress();
            }
        }, 333);

        return view;
    }
}
