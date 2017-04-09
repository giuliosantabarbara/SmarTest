package jumapp.com.smartest.Exercise.ViewManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.afollestad.materialcab.MaterialCab;

import jumapp.com.smartest.QuestionViewer.DragSelecter.DragExerAdapter;
import jumapp.com.smartest.QuestionViewer.DragSelecter.MainAdapter;


/**
 * Created by marco on 05/04/2017.
 */
public class DragExerciseManager implements
        MainAdapter.ClickListener, DragSelectRecyclerViewAdapter.SelectionListener, MaterialCab.Callback {

    private DragSelectRecyclerView drag;
    private Context context;
    public static MainAdapter mAdapter;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public DragExerciseManager(Context context,DragSelectRecyclerView drag){
        this.drag=drag;
        this.context=context;
        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();

        // Setup adapter and callbacks
        editor.putString("drag_selecter_created","exercise_short");
        editor.commit();
    }

    public void init(){
        mAdapter = new MainAdapter(this);
        mAdapter.setContext(context);
        mAdapter.setSelectionListener(this);

        drag.setLayoutManager(new GridLayoutManager(context, 5));
        drag.setAdapter(mAdapter);
    }

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        return false;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        return false;
    }

    @Override
    public boolean onCabFinished(MaterialCab cab) {
        return false;
    }

    @Override
    public void onClick(int index) {

    }

    @Override
    public void onLongClick(int index) {

    }

    @Override
    public void onDragSelectionChanged(int count) {

    }
}
