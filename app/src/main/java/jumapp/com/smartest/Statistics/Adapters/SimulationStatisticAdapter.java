package jumapp.com.smartest.Statistics.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

import az.plainpie.PieView;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;


/**
 * Created by giuli on 08/03/2017.
 */

public class SimulationStatisticAdapter extends StatisticsAdapter {

    public SimulationStatisticAdapter(Context context, ArrayList<Simulation> simulations) {
        super(context, simulations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.list_view_simulation_statistics, parent, false);
        TextView tx = (TextView) convertView.findViewById(R.id.text_view_simulation_statistic); //recognize your view like this
        Simulation s = (Simulation)(arrayList.get(position));
        tx.setText("Simulazione " +s.getId_simulation());
        int tot =0;
        ArrayList <SimulationCategory> sc = s.getSimulationCategories();

        for(SimulationCategory smC : sc){

            tot+=smC.getPercentage();
        }

        PieView pieView = (PieView) convertView.findViewById(R.id.pieViewSimulation);
        pieView.setPercentage((float)tot/sc.size());

        return convertView;
    }
}
