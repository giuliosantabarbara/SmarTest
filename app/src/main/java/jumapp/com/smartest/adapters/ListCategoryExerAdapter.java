package jumapp.com.smartest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

import jumapp.com.smartest.R;

/**
 * Created by marco on 04/04/2017.
 */
public class ListCategoryExerAdapter  extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> categories;

    public ListCategoryExerAdapter(Context context, ArrayList<String> categories) {
        super(context, 0, categories);
        this.context=context;
        this.categories=categories;

    }
    //this method will be called for every item of your listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.exercise_category_list, parent, false);

        TextView tx = (TextView) convertView.findViewById(R.id.exercise_category_tv); //recognize your view like this
        tx.setText(categories.get(position));

        return convertView;
    }

}
