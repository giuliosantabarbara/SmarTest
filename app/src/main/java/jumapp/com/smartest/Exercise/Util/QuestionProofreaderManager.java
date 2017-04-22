package jumapp.com.smartest.Exercise.Util;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

import java.util.List;

import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.ColorItem;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Exercise.SimulationEndFragment;

/**
 * Created by marco on 22/04/2017.
 */
public class QuestionProofreaderManager {

    public static void correctQuestion(Activity activity){
        List<ColorItem> items = ExerciseAdapter.getItems();
        int right=0;
        int wrong=0;
        int notSetted=0;
        int total=0;

        for (ColorItem item : items) {
            if(item.getColorItem() == Color.RED) wrong+=1;
            else if(item.getColorItem()==Color.GREEN) right+=1;
            else notSetted+=1;
            total++;
        }

        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("right",right);
        bundle.putInt("wrong",wrong);
        bundle.putInt("notSetted", notSetted);
        bundle.putInt("total",total);
        SimulationEndFragment fr = new SimulationEndFragment();
        fr.setArguments(bundle);
        fragmentTransaction.add(R.id.activity_main, fr);
        fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit();
    }

}
