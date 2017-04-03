package jumapp.com.smartest.adapters;

import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jumapp.com.smartest.R;
import jumapp.com.smartest.utility.ColorItem;


public class DemoColorPagerAdapter extends PagerAdapter {

    private List<ColorItem> mItems = new ArrayList<>();

    public DemoColorPagerAdapter() {
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_exercise_simulation, container, false);

        LinearLayout frame=(LinearLayout)view.findViewById(R.id.layout_exercise_frame);
        final TextView tv = (TextView) view.findViewById(R.id.textViewQuestionStudy);
        tv.setMovementMethod(new ScrollingMovementMethod());
        final LinearLayout linear= (LinearLayout) view.findViewById(R.id.layout_exercise_linear);

        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int tvH=tv.getHeight();
                int lH= linear.getHeight();

                if(tvH> lH*0.7 ){
                    ViewGroup.LayoutParams params = tv.getLayoutParams();
                    params.height = (int) (lH*0.7);
                    tv.setLayoutParams(params);
                    Log.i("###", "sono nel if ed ho modificato la size");
                }

                if(tvH< lH*0.2){
                    ViewGroup.LayoutParams params = tv.getLayoutParams();
                    params.height = (int) (lH*0.2);
                    tv.setLayoutParams(params);
                    Log.i("###", "sono nel if ed ho modificato la size");
                }

                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });



        int i=0;
        while(i<10) {
             View viewQuest=LayoutInflater.from(frame.getContext())
                    .inflate(R.layout.answer_layout_tmp, container, false);
            frame.addView(viewQuest);

            i++;
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return mItems.get(position).name;
    }

    public ColorItem getColorItem(int position) {
        return mItems.get(position);
    }

    public void addAll(List<ColorItem> items) {
        mItems = new ArrayList<>(items);
    }
}
