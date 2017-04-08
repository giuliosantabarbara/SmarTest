package jumapp.com.smartest.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import co.ceryle.fitgridview.FitGridAdapter;
import jumapp.com.smartest.R;
import jumapp.com.smartest.utility.ExerciseStatisticItem;

/**
 * Created by giulio on 06/04/17.
 */
public class AdapterGrid extends ArrayAdapter<ExerciseStatisticItem> {


    private Context context;
    private int layoutResourceId;
    private ArrayList<ExerciseStatisticItem> data = new ArrayList<ExerciseStatisticItem>();

    public AdapterGrid(Context context,int layoutResourceId, ArrayList<ExerciseStatisticItem> data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data=data;
    }

    /*public AdapterGrid(Context context) {

        this.context = context;
        Log.i("ENTRATO NEL constr","");
    }*/


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

        ExerciseStatisticItem item = data.get(position);
        holder.title.setText(item.getTitle());
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
