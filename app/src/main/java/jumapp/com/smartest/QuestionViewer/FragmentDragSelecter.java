package jumapp.com.smartest.QuestionViewer;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import jumapp.com.smartest.R;

/**
 * Created by marco on 31/03/2017.
 */
public class FragmentDragSelecter  extends Fragment implements
        MainAdapter.ClickListener, DragSelectRecyclerViewAdapter.SelectionListener, MaterialCab.Callback {


    private DragSelectRecyclerView mList;
    public static MainAdapter mAdapter;
    private MaterialCab mCab;
    private static int numberOfButtonSelected;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.question_viewer_drag_selecter, container, false);

        prefs = getActivity().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        numberOfButtonSelected=0;

        // Setup adapter and callbacks
        mAdapter = new MainAdapter(this);
        // Receives selection updates, recommended to set before restoreInstanceState() so initial reselection is received
        mAdapter.setSelectionListener(this);
        // Restore selected indices after Activity recreation
        mAdapter.restoreInstanceState(savedInstanceState);

        // Setup the RecyclerView
        mList = (DragSelectRecyclerView) view.findViewById(R.id.list_question_viewer);
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mList.setAdapter(mAdapter);

        mCab = MaterialCab.restoreState(savedInstanceState, (AppCompatActivity) getActivity(), this);


        //to override on back pressed from a fragment
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                Log.i("####","sono nel on key");

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mAdapter.getSelectedCount() > 0)
                        mAdapter.clearSelected();
                    else if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    }


                    return true;
                }
                return false;
            }
        });


        return view;
    }



    @Override
    public void onClick(int index) {
        if(numberOfButtonSelected>0)mAdapter.toggleSelected(index);
        else{
            Log.i("LISTENER", "Onclick");
        }

    }




    @Override
    public void onLongClick(int index) {
        Log.i("LISTENER", "onLongClick");
        mList.setDragSelectActive(true, index);
    }

    @Override
    public void onDragSelectionChanged(int count) {
        Log.i("LISTENER", "onDragSelectionChanged: "+count);
        numberOfButtonSelected=count;

        editor.putInt("numberOfButtonSelected",numberOfButtonSelected);
        editor.commit();
        if (count > 0) {
            if (mCab == null) {
                mCab = new MaterialCab((AppCompatActivity) getActivity(), R.id.cab_stub)
                        .setMenu(R.menu.question_view_cab)
                        .setCloseDrawableRes(R.drawable.ic_close)
                        .start(this);
            }
            mCab.setTitleRes(R.string.question_viewer_cab_title_x, count);
        } else if (mCab != null && mCab.isActive()) {
            mCab.reset().finish();
            mCab = null;
        }
    }

    // Material CAB Callbacks

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        Log.i("LISTENER", "onCabItemClicked");
        if (item.getItemId() == R.id.done) {
            StringBuilder sb = new StringBuilder();
            int traverse = 0;
            for (Integer index : mAdapter.getSelectedIndices()) {
                if (traverse > 0) sb.append(", ");
                sb.append(mAdapter.getItem(index));
                traverse++;
            }

            mAdapter.clearSelected();
        }
        return true;
    }



    @Override
    public boolean onCabFinished(MaterialCab cab) {
        Log.i("LISTENER", "onCabFinished");
        mAdapter.clearSelected();
        return true;
    }
}
