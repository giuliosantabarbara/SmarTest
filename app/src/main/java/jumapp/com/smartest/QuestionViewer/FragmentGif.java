package jumapp.com.smartest.QuestionViewer;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.io.IOException;
import java.util.ArrayList;

import jumapp.com.smartest.R;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Util.PrefManager;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static jumapp.com.smartest.Util.PrefManager.DRAG_GIF;

/**
 * Created by Apoll on 19/08/2017.
 */

public class FragmentGif extends Fragment {

    private GifDrawable gifFromResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.gif_drag_selecter, container, false);

        CheckBox check_drag_gif = (CheckBox) view.findViewById(R.id.check_drag_gif);
        check_drag_gif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)PrefManager.setBooleanPref(getActivity(),DRAG_GIF,true);
                else PrefManager.setBooleanPref(getActivity(),DRAG_GIF,false);
            }
        }
        );


        try {
            gifFromResource = new GifDrawable(getResources(), R.raw.gif_drag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GifImageView mGigImageView = (GifImageView) view.findViewById(R.id.loader_anim);
        mGigImageView.setImageDrawable(gifFromResource);

        Button b = (Button) view.findViewById(R.id.button_drag_selecter);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }


}


