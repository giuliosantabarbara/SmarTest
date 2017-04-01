package jumapp.com.smartest.QuestionViewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;

import jumapp.com.smartest.R;

/**
 * Created by marco on 31/03/2017.
 */
public class MainAdapter extends DragSelectRecyclerViewAdapter<MainAdapter.MainViewHolder> {

    private final static int[] COLORS = color();
    private final static String[] TEXT=textInitialization();


    public static int[] color(){
        int [] insieme= new int[250];
        for(int i=0; i<20; i++){
            insieme[i]= Color.parseColor("#009688");
        }

        for(int i=20; i<40; i++){
            insieme[i]=Color.parseColor("#A9A9A9");
        }

        for(int i=40; i<250; i=i+10){
            insieme[i]=Color.parseColor("#F44336");
            insieme[i+1]=Color.parseColor("#F44336");
            insieme[i+2] = Color.parseColor("#E91E63");
            insieme[i+3]=Color.parseColor("#9C27B0");
            insieme[i+4]=Color.parseColor("#673AB7");
            insieme[i+5]=Color.parseColor("#3F51B5");
            insieme[i+6]=Color.parseColor("#2196F3");
            insieme[i+7]=Color.parseColor("#03A9F4");
            insieme[i+8]=Color.parseColor("#00BCD4");
            insieme[i+9]=Color.parseColor("#009688");

        }
        return insieme;
    }

    public static String[] textInitialization(){
        String [] insieme= new String[250];
        for(int i=0; i<250; i++){
            insieme[i]=""+i;
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

                prefs = itemView.getContext().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
                int numberOfButtonSelected=prefs.getInt("numberOfButtonSelected",0);

                if(  numberOfButtonSelected==0 ) {
                    final Animation myAnim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.bounce);
                    // Use bounce interpolator with amplitude 0.2 and frequency 20
                    MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                    myAnim.setInterpolator(interpolator);
                    v.startAnimation(myAnim);
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