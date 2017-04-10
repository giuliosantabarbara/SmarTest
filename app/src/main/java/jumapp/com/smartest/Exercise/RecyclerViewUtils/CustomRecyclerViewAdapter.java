package jumapp.com.smartest.Exercise.RecyclerViewUtils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.R;


public class CustomRecyclerViewAdapter extends RecyclerTabLayout.Adapter<CustomRecyclerViewAdapter.ViewHolder> {


            private ExerciseAdapter mAdapater;

            public CustomRecyclerViewAdapter(ViewPager viewPager) {
                super(viewPager);
                mAdapater = (ExerciseAdapter) mViewPager.getAdapter();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.i("###","on creta view");
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_exercise_tab, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                Log.i("###", "on Bind View");
                ColorItem colorItem = mAdapater.getColorItem(position);
                holder.title.setText(colorItem.name);
                holder.color.setBackgroundColor(colorItem.color);
                if( colorItem.getColorItem()== Color.RED) holder.color.setBackgroundResource(R.drawable.custom_shape_red);
                else if(colorItem.getColorItem()==Color.GREEN)holder.color.setBackgroundResource(R.drawable.custom_shape_green);
                else holder.color.setBackgroundResource(R.drawable.custom_shape_grey);

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
                    Log.i("###","listener view HOLDER");
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