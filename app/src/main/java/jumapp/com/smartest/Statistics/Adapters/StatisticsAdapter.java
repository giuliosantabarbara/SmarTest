package jumapp.com.smartest.Statistics.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/**
 * Created by giuli on 08/03/2017.
 */
public class StatisticsAdapter extends BaseAdapter {
    /**
     * The Con.
     */
    Context con;
    /**
     * The Array list.
     */
    ArrayList<?> arrayList;

    /**
     * Instantiates a new Statistics adapter.
     *
     * @param context   the context
     * @param arrayList the array list
     */
    public StatisticsAdapter(Context context, ArrayList<?> arrayList)
    {
        this.con = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
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
