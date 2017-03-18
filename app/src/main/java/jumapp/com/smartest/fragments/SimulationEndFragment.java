package jumapp.com.smartest.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_end_simulation, container,false);
        context = view.getContext();

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

        // Start apsv animation on start
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Random random = new Random();
                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels())
                    model.setProgress(random.nextInt(101));
                mArcProgressStackView.animateProgress();
            }
        }, 333);

        return view;
    }
}
