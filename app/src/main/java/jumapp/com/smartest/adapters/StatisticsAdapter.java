package jumapp.com.smartest.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by giuli on 08/03/2017.
 */

public class StatisticsAdapter extends BaseAdapter {
    Context con;
    String[] text;
    int [] progress;

    public StatisticsAdapter(Context context, String[] text, int [] progress)
    {
        this.con = context;
        this.text = text;
        this.progress=progress;

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
        return null;
    }
}
