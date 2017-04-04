package jumapp.com.smartest.QuestionViewer;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.util.ArrayList;
import java.util.List;

import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.Question;
import jumapp.com.smartest.adapters.DemoColorPagerAdapter;
import jumapp.com.smartest.adapters.ExcerciseAdapter;
import jumapp.com.smartest.utility.ColorItem;
import jumapp.com.smartest.utility.NumberList;

/**
 * Created by marco on 02/04/2017.
 */
public class StudyFragment extends Fragment implements View.OnClickListener{


    public StudyFragment(){
    }


    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_study, container, false);
        context = view.getContext();


        Bundle extras = getArguments();
        ArrayList<Question> arraylist  = extras.getParcelableArrayList("questions_parceable");
        StudyFragmentAdapter adapter = new StudyFragmentAdapter();
        adapter.addAll(arraylist);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_fragment_study);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(extras.getInt("question_selected"));

        return view;
    }



    @Override
    public void onClick(View view) {



    }
}
