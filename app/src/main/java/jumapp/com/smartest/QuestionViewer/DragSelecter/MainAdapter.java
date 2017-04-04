package jumapp.com.smartest.QuestionViewer.DragSelecter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import jumapp.com.smartest.QuestionViewer.QuestionsByCategorySingleton;
import jumapp.com.smartest.QuestionViewer.StudyFragment;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 31/03/2017.
 */
public class MainAdapter extends DragSelectRecyclerViewAdapter<MainAdapter.MainViewHolder> {

    private final static int[] COLORS = color();
    private final static String[] TEXT=textInitialization();
    private static Context context;
    private SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    //

    public static int[] color(){
       ArrayList<Question> questions = QuestionsByCategorySingleton.getInstance().getQuestions();
        int [] insieme= new int[questions.size()];
        for(int i=0; i<questions.size(); i++){
            if(questions.get(i).getStudied()) insieme[i]= Color.parseColor("#009688");
            else insieme[i]=Color.parseColor("#A9A9A9");
        }


        return insieme;
    }

    public static String[] textInitialization(){

        //ArrayList<Question> questions = ((QuestionsByCategory) parent.getContext()getApplication()).getSomeVariable();
        ArrayList<Question> questions = QuestionsByCategorySingleton.getInstance().getQuestions();
        String [] insieme= new String[questions.size()];
        for(int i=0; i<questions.size(); i++){
            insieme[i]=""+(i+1);
        }
        return insieme;
    }

    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
    }

    private final ClickListener mCallback;

    protected MainAdapter(ClickListener callback) {
        super();
        mCallback = callback;

    }

    public String getItem(int index) {
        return TEXT[index];
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_viewer_griditem_main, parent, false);

        context=parent.getContext();
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();

        return new MainViewHolder(v, mCallback);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
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
        holder.colorSquare.setBackgroundColor(COLORS[position]);


    }



    @Override
    public int getItemCount() {
        return TEXT.length;
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

                Log.i("###", "POSITION " + getAdapterPosition());

                prefs = itemView.getContext().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
                int numberOfButtonSelected=prefs.getInt("numberOfButtonSelected",0);
                editor.putInt("question_selected", getAdapterPosition());
                editor.commit();

                if(  numberOfButtonSelected==0 ) {
                    final Animation myAnim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.bounce);
                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    v.startAnimation(myAnim);

                    editor.putInt("question_selected", getAdapterPosition());
                    editor.commit();

                    FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.activity_main, new StudyFragment());
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