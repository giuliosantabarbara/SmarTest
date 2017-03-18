package jumapp.com.smartest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import az.plainpie.PieView;
import jumapp.com.smartest.R;


/**
 * Created by giuli on 08/03/2017.
 */

public class SimulationStatisticAdapter extends StatisticsAdapter {

    public SimulationStatisticAdapter(Context context, String[] text, int[] progress) {
        super(context, text, progress);
    }

    //this method will be called for every item of your listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.list_view_simulation_statistics, parent, false);
        TextView tx = (TextView) convertView.findViewById(R.id.text_view_simulation_statistic); //recognize your view like this
        tx.setText(text[position]);
        PieView pieView = (PieView) convertView.findViewById(R.id.pieViewSimulation);
        pieView.setPercentage((float)progress[position]);
        return convertView;
    }
}
