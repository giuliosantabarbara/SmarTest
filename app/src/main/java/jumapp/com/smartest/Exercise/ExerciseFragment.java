package jumapp.com.smartest.Exercise;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.CustomRecyclerViewAdapter;

/**
 * Created by giuli on 08/03/2017.
 */

public class ExerciseFragment extends Fragment implements View.OnClickListener{

   public ExerciseFragment(){

    }
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_exercise_simulation, container,false);
        context = view.getContext();


        ExerciseAdapter adapter = new ExerciseAdapter();
        adapter.addAllQuestion(QuestionsSingleton.getInstance().getQuestions());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pag_fragment_exercise);
        viewPager.setAdapter(adapter);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) view.findViewById(R.id.recycler_tab_layout_exercise);
        recyclerTabLayout.setUpWithAdapter(new CustomRecyclerViewAdapter(viewPager));
        return view;
    }



    @Override
    public void onClick(View view) {



    }
}
