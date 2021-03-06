package jumapp.com.smartest.Exercise;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import java.util.ArrayList;
import java.util.List;

import jumapp.com.smartest.ContestSingleton;
import jumapp.com.smartest.Exercise.Adapters.ExerciseAdapter;
import jumapp.com.smartest.Exercise.Singleton.DictionaryCategoryNumberSingleton;
import jumapp.com.smartest.Exercise.Util.QuestionProofreaderManager;
import jumapp.com.smartest.Exercise.Util.WhatsappManager;
import jumapp.com.smartest.Exercise.ViewManager.AlertContestSingleton;
import jumapp.com.smartest.Exercise.ViewManager.FinishViewPagerHandler;
import jumapp.com.smartest.R;
import jumapp.com.smartest.Exercise.RecyclerViewUtils.CustomRecyclerViewAdapter;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

/**
 * Created by giuli on 08/03/2017.
 */

public class ExerciseFragment extends Fragment implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    public ExerciseFragment() {

    }

    private ContextMenuDialogFragment mMenuDialogFragment;
    View view;
    Context context;
    private FragmentManager fragmentManager;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ArrayList<Pair> pairs;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_exercise_simulation, container, false);
        context = view.getContext();

        prefs = context.getSharedPreferences("jumapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        viewPager = (ViewPager) view.findViewById(R.id.view_pag_fragment_exercise);

        //code to copy to init toolbar
        setHasOptionsMenu(true);
        fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        ExerciseAdapter adapter = new ExerciseAdapter();
        pairs = DictionaryCategoryNumberSingleton.getInstance().getPairs();

        //handle question rendering
        int last_contest_singoleton_id = prefs.getInt("contest_singleton_id", 0);
        if (last_contest_singoleton_id == 0)
            new AlertContestSingleton(getActivity(), view, viewPager, 500, adapter, pairs).Start();
        else {

            Bundle extras = getArguments();
            ArrayList<Question> arraylist  = extras.getParcelableArrayList("questions_parceable");

            adapter.addAllQuestion(arraylist);
            //adapter.addAllQuestion(ContestSingleton.getInstance(context).getRandomQuestions(pairs));
            viewPager.setAdapter(adapter);
            viewPager.setOnPageChangeListener(new FinishViewPagerHandler(viewPager, getActivity()));
            RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) view.findViewById(R.id.recycler_tab_layout_exercise);
            recyclerTabLayout.setUpWithAdapter(new CustomRecyclerViewAdapter(viewPager));
        }
        return view;
    }




    //all code below should be copy to init toolbar
    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) view.findViewById(R.id.text_view_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).onBackPressed();
            }
        });
        mToolBarTextView.setTextColor(Color.WHITE);
        mToolBarTextView.setText("Esercito Ufficiali 2017");
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject addFav = new MenuObject("Aggiungi a Favorita");
        addFav.setResource(R.drawable.favorites);

        MenuObject send = new MenuObject("Invia ad un amico");
        send.setResource(R.drawable.icn_1);

        MenuObject studied = new MenuObject("Aggiungi a Studiata");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        studied.setBitmap(b);


        MenuObject block = new MenuObject("Termina simulazione");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(addFav);
        menuObjects.add(studied);
        menuObjects.add(send);
        menuObjects.add(block);

        return menuObjects;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        QuestionDAO quest= new QuestionDAOImpl(context);
        Question q=ExerciseAdapter.getQuestions().get(viewPager.getCurrentItem());

        if(position==1)quest.setQuestionFavorited(q.getQuestion_id(),true,quest.openWritableConnection());
        else if(position==2)quest.setQuestionStudied(q.getQuestion_id(), true, quest.openWritableConnection());
        else if(position==3){
            String message=q.getText()+"\n";
            ArrayList<Alternative> alt=q.getAlternatives();
            for(int pos=0; pos<alt.size(); pos++) message=message.concat(""+(pos+1)+") "+alt.get(pos).getText()+"\n");
            message=message.concat("\nwww.jumapp.com");
            WhatsappManager.sendMessage(getActivity(),message);
        }
        else if(position==4) QuestionProofreaderManager.correctQuestion(getActivity());

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
