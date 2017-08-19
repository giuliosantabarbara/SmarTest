package jumapp.com.smartest.Exercise.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jumapp.com.smartest.QuestionViewer.DragSelecter.MyBounceInterpolator;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.ColorItem;


public class ExerciseAdapter extends PagerAdapter {

    private static List<ColorItem> mItems = new ArrayList<>();

    private static ArrayList<Question> questions= new ArrayList<Question>();

    Context contex;


    public ExerciseAdapter() {
    }




    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_exercise_simulation, container, false);

        contex=view.getContext();

        final Question quest = questions.get(position);
        LinearLayout frame = (LinearLayout) view.findViewById(R.id.layout_exercise_frame);
        final TextView tv = (TextView) view.findViewById(R.id.textViewQuestionStudy);
        tv.setText(quest.getText());
        tv.setTextColor(Color.WHITE);
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

        if(alternatives!=null)for (final Alternative a : alternatives) {

            final View viewQuest = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.answer_layout_tmp, container, false);
            final TextView txt = (TextView) viewQuest.findViewById(R.id.text_view_answer_alternative);
            txt.setText(a.getText());

            if(mItems.get(position).getSetted() &&a.getRight()){
                Drawable dr = ContextCompat.getDrawable(viewQuest.getContext(), R.drawable.shape_green);
                txt.setBackground(dr);
            }else {

                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (a.getRight()) {
                            Drawable dr = ContextCompat.getDrawable(viewQuest.getContext(), R.drawable.shape_green);
                            txt.setBackground(dr);

                            if (!mItems.get(position).getSetted()) {
                                mItems.get(position).setColorItem(Color.GREEN);
                                mItems.get(position).setSetted();
                            }


                        } else {
                            Drawable dr = ContextCompat.getDrawable(viewQuest.getContext(), R.drawable.shape_red);
                            txt.setBackground(dr);
                            final Animation myAnim = AnimationUtils.loadAnimation(viewQuest.getContext(), R.anim.bounce);
                            // Use bounce interpolator with amplitude 0.2 and frequency 20
                            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                            myAnim.setInterpolator(interpolator);
                            v.startAnimation(myAnim);

                            if (!mItems.get(position).getSetted()) {
                                mItems.get(position).setColorItem(Color.RED);
                                mItems.get(position).setSetted();
                            }

                        }
                    }
                });
            }

            frame.addView(viewQuest);

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
        Log.i("###", "get color item");
        return mItems.get(position);
    }

    public void initItems() {
        mItems = new ArrayList<>();
        for (int i=0; i< questions.size(); i++) mItems.add(new ColorItem(""+(i+1),Color.GRAY));
    }

    public void addAllQuestion(ArrayList<Question> questions) {
        this.questions=questions;
        initItems();
    }

    public static List<ColorItem> getItems(){
        return mItems;
    }

    public static ArrayList<Question> getQuestions(){
        return questions;
    }

}
