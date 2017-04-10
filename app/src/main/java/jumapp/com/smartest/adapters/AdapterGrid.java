package jumapp.com.smartest.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 06/04/17.
 */
public class AdapterGrid extends ArrayAdapter<GridType> {


    private Context context;
    private int layoutResourceId;
    private ArrayList<GridType> arrayList = new ArrayList<GridType>();


    public AdapterGrid(Context context,int layoutResourceId, ArrayList<GridType> arrayList) {
        super(context, layoutResourceId,arrayList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.arrayList=arrayList;
        Log.i("Dentro ADAPTER","");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.text_view_exercise_statistic);
            holder.pieView = (PieView) row.findViewById(R.id.pieViewEcercise);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridType item = arrayList.get(position);
        holder.title.setText(item.getCategoryName());
        holder.pieView.setPercentage(item.getPercentage());
        //holder.pieView.setMainBackgroundColor(R.color.main_color_middle);
        //holder.pieView.setMainBackgroundColor(R.color.main_color_middle);
        //holder.pieView.setInnerBackgroundColor(R.color.main_color_light);
        //holder.pieView.setMainBackgroundColor(R.color.white);
        //holder.pieView.setPercentageBackgroundColor(R.color.main_color_light);
        PieAngleAnimation animation = new PieAngleAnimation(holder.pieView);
        animation.setDuration(500); //This is the duration of the animation in millis
        holder.pieView.startAnimation(animation);
        return row;

    }


    static class ViewHolder {
        TextView title;
        PieView pieView;
    }
}
