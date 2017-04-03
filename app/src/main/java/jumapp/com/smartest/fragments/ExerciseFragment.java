package jumapp.com.smartest.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import jumapp.com.smartest.R;
import jumapp.com.smartest.adapters.ExcerciseAdapter;
import jumapp.com.smartest.adapters.DemoColorPagerAdapter;
import jumapp.com.smartest.utility.ColorItem;
import jumapp.com.smartest.utility.NumberList;


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
        List<ColorItem> items = NumberList.loadDemoColorItems();

        DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
        adapter.addAll(items);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) view.findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new ExcerciseAdapter(viewPager));
        return view;
    }



    @Override
    public void onClick(View view) {



    }
}
