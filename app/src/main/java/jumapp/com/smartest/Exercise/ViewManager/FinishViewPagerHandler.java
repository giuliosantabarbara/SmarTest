package jumapp.com.smartest.Exercise.ViewManager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by marco on 13/04/2017.
 */
public class FinishViewPagerHandler implements ViewPager.OnPageChangeListener {
    private ViewPager   mViewPager;
    private int         mCurrentPosition;
    private int         mScrollState;
    Context context;

    public FinishViewPagerHandler(final ViewPager viewPager, Context context) {
        mViewPager = viewPager;
        this.context=context;
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
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
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

                    }
                })
                .show();
    }
}