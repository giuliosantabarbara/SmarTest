package jumapp.com.smartest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.Question;


/**
 * Created by giuli on 07/03/2017.
 */

public class CategoriesStatisticAdapter extends StatisticsAdapter {

    public CategoriesStatisticAdapter(Context context,String nam[], int[] n,int contest_id) {
        super(context,nam , n);
    }
    //this method will be called for every item of your listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.list_view_style, parent, false);
        TextView tx = (TextView) convertView.findViewById(R.id.text_view); //recognize your view like this
        tx.setText(text[position]);
        RoundCornerProgressBar bar = (RoundCornerProgressBar) convertView.findViewById(R.id.progress);
        bar.setProgress((float) progress[position]);
        bar.setProgressBackgroundColor(Color.parseColor("#1a405f"));
        bar.setProgressColor(Color.parseColor("#4090D0"));
        return convertView;
    }

}
