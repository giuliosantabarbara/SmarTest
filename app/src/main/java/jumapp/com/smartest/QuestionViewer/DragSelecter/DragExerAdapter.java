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
import jumapp.com.smartest.Storage.DAOObject.Question;

/**
 * Created by marco on 05/04/2017.
 */
public class DragExerAdapter extends DragSelectRecyclerViewAdapter<DragExerAdapter.MainViewHolder> {

    private final ClickListener mCallback;

    @Override
    public DragExerAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    public int[] color(){
        int [] insieme= new int[5];
        for(int i=0; i<5; i++){
            insieme[i]= Color.parseColor("#009688");

        }


        return insieme;
    }

    public static String[] textInitialization(){

        //ArrayList<Question> questions = ((QuestionsByCategory) parent.getContext()getApplication()).getSomeVariable();
        String [] insieme= new String[5];

        for( int i=0; i<5; i++){
            insieme[i]=""+(i+1);

        }

        return insieme;
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
        holder.colorSquare.setBackgroundColor(color()[position]);


    }

    public String getItem(int index) {
        return textInitialization()[index];
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public DragExerAdapter(ClickListener callback) {
        super();
        mCallback = callback;

    }

    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
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

           /* if(  numberOfButtonSelected==0 ) {
                final Animation myAnim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.bounce);
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                v.startAnimation(myAnim);



            }*/
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
