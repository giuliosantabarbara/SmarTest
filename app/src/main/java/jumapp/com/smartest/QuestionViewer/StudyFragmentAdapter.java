package jumapp.com.smartest.QuestionViewer;

import android.content.Context;
import android.content.SharedPreferences;
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
import jumapp.com.smartest.Storage.DAOObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 02/04/2017.
 */
public class StudyFragmentAdapter extends PagerAdapter {

    private ArrayList<Question> mItems = new ArrayList<>();

    private SharedPreferences prefs;
    private View view;

    public StudyFragmentAdapter() {
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_exercise_simulation, container, false);

        prefs = view.getContext().getSharedPreferences("jumapp", Context.MODE_PRIVATE);

        QuestionsByCategorySingleton qsing = QuestionsByCategorySingleton.getInstance();
        final Question quest = qsing.getQuestionByIndex(prefs.getInt("question_selected", 0)+position);

        Log.i("###", "POSITION/QUESTION_SELECTED: " + position);

        LinearLayout frame = (LinearLayout) view.findViewById(R.id.layout_exercise_frame);
        final TextView tv = (TextView) view.findViewById(R.id.textViewQuestionStudy);
        tv.setText(quest.getText());
        tv.setMovementMethod(new ScrollingMovementMethod());
        final LinearLayout linear = (LinearLayout) view.findViewById(R.id.layout_exercise_linear);

        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int tvH = tv.getHeight();
                int lH = linear.getHeight();

                if (tvH > lH * 0.7) {
                    ViewGroup.LayoutParams params = tv.getLayoutParams();
                    params.height = (int) (lH * 0.7);
                    tv.setLayoutParams(params);
                }

                if (tvH < lH * 0.2) {
                    ViewGroup.LayoutParams params = tv.getLayoutParams();
                    params.height = (int) (lH * 0.2);
                    tv.setLayoutParams(params);
                }
                tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });


        ArrayList<Alternative> alternatives = quest.getAlternatives();
        Log.i("!!!!: ","");


        for (Alternative a : alternatives) {

            View viewQuest = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.answer_layout_tmp, container, false);
            TextView txt = (TextView) viewQuest.findViewById(R.id.text_view_answer_alternative);
            txt.setText(a.getText());
            frame.addView(viewQuest);

            Log.i("prova: ","::::"+txt.getText());
            Log.i("!!!!: ",""+a.getText());

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
        return mItems.get(position).getText();
    }

    public Question getColorItem(int position) {
        return mItems.get(position);
    }

    public void addAll(List<Question> items) {
        mItems = new ArrayList<>(items);
    }
}
