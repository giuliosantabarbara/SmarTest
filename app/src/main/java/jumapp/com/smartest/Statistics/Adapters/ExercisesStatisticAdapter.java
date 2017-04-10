package jumapp.com.smartest.Statistics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import az.plainpie.PieView;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;


/**
 * Created by giuli on 07/03/2017.
 */

public class ExercisesStatisticAdapter extends StatisticsAdapter {

    public ExercisesStatisticAdapter(Context context, ArrayList<Exercise> exercices) {
        super(context, exercices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.grid_view_exercise_statistic, parent, false);
        TextView tx = (TextView) convertView.findViewById(R.id.text_view_exercise_statistic);
        Exercise ex = (Exercise) arrayList.get(position);
        tx.setText(ex.getCategoryName());
        PieView pieView = (PieView) convertView.findViewById(R.id.pieViewEcercise);
        pieView.setPercentage((float)ex.getPercentage());
        return convertView;
    }
}
