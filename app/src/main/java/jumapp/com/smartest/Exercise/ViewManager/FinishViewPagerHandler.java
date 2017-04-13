package jumapp.com.smartest.Exercise.ViewManager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.ColorItem;
import jumapp.com.smartest.QuestionViewer.DragSelecter.FragmentDragSelecter;
import jumapp.com.smartest.R;
import jumapp.com.smartest.fragments.SimulationEndFragment;

/**
 * Created by marco on 13/04/2017.
 */
public class FinishViewPagerHandler implements ViewPager.OnPageChangeListener {
    private ViewPager   mViewPager;
    private int         mCurrentPosition;
    private int         mScrollState;
    Activity activity;

    public FinishViewPagerHandler(final ViewPager viewPager, Activity activity) {
        mViewPager = viewPager;
        this.activity=activity;
    }

    @Override
    public void onPageSelected(final int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        handleScrollState(state);
        mScrollState = state;
    }

    private void handleScrollState(final int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setNextItemIfNeeded();
        }
    }

    public void correctAnswer(){
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

    private void setNextItemIfNeeded() {
        if (!isScrollStateSettling()) {
            handleSetNextItem();
        }
    }

    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    private void handleSetNextItem() {
        final int lastPosition = mViewPager.getAdapter().getCount() - 1;
        if(mCurrentPosition == 0) {
        } else if(mCurrentPosition == lastPosition) {
            finish();
        }
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    public void finish(){
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Vuoi terminare l'esercitazione?")
                .setContentText("Non sarà più possibile tornare indietro!")
                .setCancelText("No,torna indietro!")
                .setConfirmText("Si,termina!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.setContentText("Puoi termianre l'esercitazione in qualsiasi momento accendo al menu dall'icona in alto nello schermo")
                                .setTitleText("RICORDA")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        correctAnswer();
                    }
                })
                .show();
    }
}