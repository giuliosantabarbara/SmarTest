package jumapp.com.smartest.QuestionViewer.DragSelecter;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import java.util.ArrayList;

import jumapp.com.smartest.QuestionViewer.QuestionsSingleton;
import jumapp.com.smartest.QuestionViewer.StudyFragment;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

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
    private  Toolbar toolbar;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.question_viewer_drag_selecter, container, false);
        context=view.getContext();

        prefs = getActivity().getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        numberOfButtonSelected=0;
        String category_selected=null;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category_selected = bundle.getString("category_selected");
        }

        // Setup adapter and callbacks
        editor.putString("drag_selecter_created","study");
        editor.commit();

        mAdapter = new MainAdapter(this);
        mAdapter.setContext(context);
        // Receives selection updates, recommended to set before restoreInstanceState() so initial reselection is received
        mAdapter.setSelectionListener(this);
        // Restore selected indices after Activity recreation
        mAdapter.restoreInstanceState(savedInstanceState);


        // Setup the RecyclerView
        mList = (DragSelectRecyclerView) view.findViewById(R.id.list_question_viewer);
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mList.setAdapter(mAdapter);

        mCab = MaterialCab.restoreState(savedInstanceState, (AppCompatActivity) getActivity(), this);

        toolbar = (Toolbar) view.findViewById(R.id.main_toolbar_question_viewer);
        toolbar.setTitle("Tenere premuto per selezionare");


        //to override on back pressed from a fragment
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                Log.i("####", "sono nel on key");

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        toolbar.setVisibility(View.INVISIBLE);
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
              int traverse = 0;
            ArrayList<Question> questions= new ArrayList<Question>();
            QuestionsSingleton sing= QuestionsSingleton.getInstance();
            for (Integer index : mAdapter.getSelectedIndices()) {
                //if (traverse > 0) sb.append(", ");
                questions.add(sing.getQuestionByIndex(index));
            }
            mAdapter.clearSelected();


            FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            StudyFragment stdFrg= new StudyFragment();
            Bundle b= new Bundle();
            b.putParcelableArrayList("questions_parceable",questions);
            stdFrg.setArguments(b);

            fragmentTransaction.add(R.id.activity_main, stdFrg);
            fragmentTransaction.addToBackStack("back");
            fragmentTransaction.commit();

        }
        return true;
    }



    @Override
    public boolean onCabFinished(MaterialCab cab) {
        Log.i("LISTENER", "onCabFinished");
        toolbar.setVisibility(View.VISIBLE);
        mAdapter.clearSelected();
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Select Questions");
        return true;
    }
}
