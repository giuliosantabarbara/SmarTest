package jumapp.com.smartest.Statistics.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import jumapp.com.smartest.R;


/**
 * Created by giuli on 07/03/2017.
 */
public class CategoriesStatisticAdapter extends BaseAdapter {

    /**
     * The Context.
     */
    Context context;
    /**
     * The Text.
     */
    String[] text;
    /**
     * The Progress.
     */
    int [] progress;

    /**
     * Instantiates a new Categories statistic adapter.
     *
     * @param context  the context
     * @param text     the text
     * @param progress the progress
     */
    public CategoriesStatisticAdapter(Context context,String text[], int[] progress) {

        this.text=text;
        this.progress=progress;
        this.context=context;
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
