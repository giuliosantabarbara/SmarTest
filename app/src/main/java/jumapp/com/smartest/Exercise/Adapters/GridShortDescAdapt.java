package jumapp.com.smartest.Exercise.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jumapp.com.smartest.Exercise.Pair;
import jumapp.com.smartest.R;

/**
 * Created by marco on 08/04/2017.
 */
public class GridShortDescAdapt extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflator;
    private ArrayList<Pair> pairs= new ArrayList<Pair>();
    private View view;


    public GridShortDescAdapt(Context c, ArrayList<Pair> pairs,View view) {
        Log.i("###","ENTRO NELL COSTRUTTORE ADAPTER");
        context = c;
        this.pairs=pairs;
        this.view=view;
        layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        Log.i("###","ENTRO NELL GET COUNT "+pairs.size());
        return pairs.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View grid, ViewGroup parent) {
        Log.i("###","ENTRO NELL ADAPTER GRID");
        grid = layoutInflator.inflate(R.layout.short_description_layout, null);
        final TextView text= (TextView)grid.findViewById(R.id.exercise_desc_text);
        final GridView gridView= (GridView) view.findViewById(R.id.grid_layout_short_descr);

        text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ViewGroup.LayoutParams params = text.getLayoutParams();
                params.height =(int) (text.getWidth()) ;
                if(params.height> (gridView.getHeight()*0.75)) params.height=(int)(gridView.getHeight()*0.75);
                text.setLayoutParams(params);
                text.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        text.setText(pairs.get(position).getNumber()+"\n"+pairs.get(position).getCategory());
        return grid;

    }

}