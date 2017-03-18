package jumapp.com.smartest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import az.plainpie.PieView;
import jumapp.com.smartest.R;


/**
 * Created by giuli on 07/03/2017.
 */

public class ExercisesStatisticAdapter extends StatisticsAdapter {


    public ExercisesStatisticAdapter(Context context, String[] text, int[] progress) {
        super(context, text, progress);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.list_view_exercise_statistic, parent, false);
        TextView tx = (TextView) convertView.findViewById(R.id.text_view_exercise_statistic); //recognize your view like this
        tx.setText(text[position]);
        PieView pieView = (PieView) convertView.findViewById(R.id.pieViewEcercise);
        pieView.setPercentage((float)progress[position]);
        return convertView;
    }
}
