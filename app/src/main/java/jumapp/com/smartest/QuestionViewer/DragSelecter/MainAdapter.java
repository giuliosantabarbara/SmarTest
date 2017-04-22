package jumapp.com.smartest.QuestionViewer.DragSelecter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;

import java.util.ArrayList;

import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;
import jumapp.com.smartest.QuestionViewer.StudyFragment;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;


/**
 * Created by marco on 31/03/2017.
 */
public class MainAdapter extends DragSelectRecyclerViewAdapter<MainAdapter.MainViewHolder> {

    private static Context context;
    private SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    //

    public void setContext(Context context) {
        this.context = context;
    }

    public int[] color() {
        //Log.i("@@@","color");

        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        String created_by = prefs.getString("drag_selecter_created", null);

        ArrayList<Question> questions;
        questions = QuestionsSingleton.getInstance().getQuestions();

        int[] insieme = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getStudied()) insieme[i] = Color.parseColor("#009688");
            else if(questions.get(i).getFavorited()) insieme[i] = Color.parseColor("#FFE118");
            else insieme[i] = Color.parseColor("#A9A9A9");
        }


        return insieme;
    }

    public String[] textInitialization() {

        //Log.i("@@@","text");

        //ArrayList<Question> questions = ((QuestionsByCategory) parent.getContext()getApplication()).getSomeVariable();
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        String created_by = prefs.getString("drag_selecter_created", null);

        ArrayList<Question> questions;
        String[] insieme;
            questions = QuestionsSingleton.getInstance().getQuestions();
            insieme = new String[questions.size()];
            for (int i = 0; i < questions.size(); i++) {
                insieme[i] = "" + (i + 1);
            }


        return insieme;
    }

    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
    }

    private final ClickListener mCallback;

    public MainAdapter(ClickListener callback) {
        super();
        mCallback = callback;

    }

    public String getItem(int index) {
        //Log.i("@@@","getItem");

        return textInitialization()[index];
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.i("@@@","onCreateView");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_viewer_griditem_main, parent, false);


        context = parent.getContext();
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();

        return new MainViewHolder(v, mCallback);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Log.i("%%%", "bindView");
        holder.label.setText(getItem(position));

        final Drawable d;
        final Context c = holder.itemView.getContext();

        if (isIndexSelected(position)) {
            d = new ColorDrawable(ContextCompat.getColor(c, R.color.grid_foreground_selected));
            holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_selected));
        } else {
            d = null;
            holder.label.setTextColor(ContextCompat.getColor(c, R.color.grid_label_text_normal));
        }

        //noinspection RedundantCast
        ((FrameLayout) holder.colorSquare).setForeground(d);
        holder.colorSquare.setBackgroundColor(color()[position]);
    }



    @Override
    public int getItemCount() {
        Log.i("@@@", "getItem");
        return textInitialization().length;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        SharedPreferences prefs;


        public final TextView label;
        public final RectangleView colorSquare;
        private final ClickListener mCallback;
        private int previousButtonSelected;

        public MainViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            //Log.i("@@@", "main view holder");
            mCallback = callback;

            this.label = (TextView) itemView.findViewById(R.id.label);
            this.colorSquare = (RectangleView) itemView.findViewById(R.id.colorSquare);
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onClick(getAdapterPosition());

                //Log.i("###", "POSITION " + getAdapterPosition());

                prefs = itemView.getContext().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
                int numberOfButtonSelected = prefs.getInt("numberOfButtonSelected", 0);

                if (numberOfButtonSelected == 0) {
                   /* final Animation myAnim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.bounce);
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    v.startAnimation(myAnim);*/


                    FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    StudyFragment stdFrg = new StudyFragment();
                    ArrayList<Question> questions = QuestionsSingleton.getInstance().getQuestions();
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("questions_parceable", questions);
                    b.putInt("question_selected", getAdapterPosition());
                    stdFrg.setArguments(b);

                    fragmentTransaction.add(R.id.activity_main, stdFrg);
                    fragmentTransaction.addToBackStack("back");
                    fragmentTransaction.commit();

                }
            }

        }

        @Override
        public boolean onLongClick(View v) {
            if (mCallback != null)
                mCallback.onLongClick(getAdapterPosition());
            return true;
        }
    }


}