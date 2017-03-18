package jumapp.com.smartest.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.util.ArrayList;

import jumapp.com.smartest.R;
import jumapp.com.smartest.adapters.ExerciseAdapterProva;


/**
 * Created by giuli on 09/03/2017.
 */

public class ExerciseFragmentProva extends Fragment implements View.OnClickListener{

    public ExerciseFragmentProva() {

    }

    Context context;

    private int mScrollState;
    private ExerciseAdapterProva mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_exercise_simulation_prova, container, false);
        context = view.getContext();


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // toolbar.setTitle(demo.titleResId);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mItems = new ArrayList<>();
        mItems.add(":)");
        for (int i = 1; i <= 9; i++) {
            mItems.add(String.valueOf(i));
        }

        mAdapter = new ExerciseAdapterProva();
        mAdapter.addAll(mItems);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mAdapter.getCenterPosition(0));
        //mViewPager.addOnPageChangeListener(context);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                view.findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(mViewPager);


        return view;
    }


    public void onClicked(View view) {
       /* // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_a:
                if (checked) {

                }
                // Pirates are the best
                else {
                    RadioButton a = (RadioButton) view;
                    a.setChecked(false);
                }


                break;
            case R.id.radio_b:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_c:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_d:
                if (checked)
                    // Ninjas rule
                    break;
        }*/
    }

    @Override
    public void onClick(View view) {
/*
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_a:
                if (checked) {

                }
                // Pirates are the best
                else {
                    RadioButton a = (RadioButton) view;
                    a.setChecked(false);
                }


                break;
            case R.id.radio_b:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_c:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_d:
                if (checked)
                    // Ninjas rule
                    break;
        }*/

    }
}