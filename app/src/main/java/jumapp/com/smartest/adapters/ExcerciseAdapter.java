package jumapp.com.smartest.adapters;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import jumapp.com.smartest.R;
import jumapp.com.smartest.utility.ColorItem;


public class ExcerciseAdapter extends RecyclerTabLayout.Adapter<ExcerciseAdapter.ViewHolder> {

    private DemoColorPagerAdapter mAdapater;

    public ExcerciseAdapter(ViewPager viewPager) {
        super(viewPager);
        mAdapater = (DemoColorPagerAdapter) mViewPager.getAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exercise_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorItem colorItem = mAdapater.getColorItem(position);
        holder.title.setText(colorItem.name);
        holder.color.setBackgroundColor(colorItem.color);
        holder.color.setBackgroundResource(R.drawable.custom_shape);

        SpannableString name = new SpannableString(colorItem.name);
        if (position == getCurrentIndicatorPosition()) {
            name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
        }
        holder.title.setText(name);
    }

    @Override
    public int getItemCount() {
        return mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View color;
        public TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            color =  itemView.findViewById(R.id.colorQuestion);
            Resources res = itemView.getResources();

             itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getViewPager().setCurrentItem(getAdapterPosition());
                    View c = v.findViewById(R.id.colorQuestion);
                     c.setBackgroundResource(R.drawable.custom_shape_yellow);
                }
            });
        }
    }
}
