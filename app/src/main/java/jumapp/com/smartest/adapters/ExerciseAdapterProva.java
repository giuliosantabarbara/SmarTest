package jumapp.com.smartest.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jumapp.com.smartest.R;


/**
 * Created by giuli on 09/03/2017.
 */

public class ExerciseAdapterProva extends PagerAdapter {

    private static final int NUMBER_OF_LOOPS = 10000;

    private List<String> mItems = new ArrayList<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page_prova, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("Page: " + getValueAt(position));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size() * NUMBER_OF_LOOPS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return getValueAt(position);
    }

    public void addAll(List<String> items) {
        mItems = new ArrayList<>(items);
    }

    public int getCenterPosition(int position) {
        return mItems.size() * NUMBER_OF_LOOPS / 2 + position;
    }

    public String getValueAt(int position) {
        if (mItems.size() == 0) {
            return null;
        }
        return mItems.get(position % mItems.size());
    }
}

