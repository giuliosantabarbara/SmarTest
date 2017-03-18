package jumapp.com.smartest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import jumapp.com.smartest.R;


/**
 * Created by giuli on 08/02/2017.
 */

public class CircleHamButtonFragment extends Fragment {

    private OnSelectedButtonListener selectedButtonListener;
    public interface OnSelectedButtonListener{
        public void onSelectedButton(String s);
    }
    private int call;


    //controlla e effettua associazione interfaccia-activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectedButtonListener = (OnSelectedButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement OnSelectedButtonListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.circle_and_ham_button, container,false);

        //Initialization second ham
        BoomMenuButton bmbHamDue = (BoomMenuButton) view.findViewById(R.id.bmbHammDue);
        Log.i("Valore ham ",""+bmbHamDue);
        final BoomMenuButton bmbFixDue = bmbHamDue;
        Log.i("Valore ham final ",""+bmbHamDue);
        //bmbHam.setVisibility(View.VISIBLE);
        bmbHamDue.setButtonEnum(ButtonEnum.Ham);
        bmbHamDue.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmbHamDue.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);

        for (int j = 0; j < bmbHamDue.getPiecePlaceEnum().pieceNumber(); j++) {
            HamButton.Builder builderHam = new HamButton.Builder()
                    .normalImageRes(R.drawable.bat)
                    .normalText("Prova");

            bmbHamDue.addBuilder(builderHam.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    sendBodyTextToActivity("uno");
                }
            }));
        }

        //Initialization first ham
        BoomMenuButton bmbHam = (BoomMenuButton) view.findViewById(R.id.bmbHamm);
        Log.i("Valore ham ",""+bmbHam);
        final BoomMenuButton bmbFix = bmbHam;
        Log.i("Valore ham final ",""+bmbHam);
        //bmbHam.setVisibility(View.VISIBLE);
        bmbHam.setButtonEnum(ButtonEnum.Ham);
        bmbHam.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmbHam.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);

        for (int j = 0; j < bmbHam.getPiecePlaceEnum().pieceNumber(); j++) {
            HamButton.Builder builderHam = new HamButton.Builder()
                    .normalImageRes(R.drawable.bat)
                    .normalText("Prova");

            bmbHam.addBuilder(builderHam.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    //sendBodyTextToActivity("uno");

                    bmbFix.setVisibility(View.GONE);
                    bmbFixDue.setVisibility(View.VISIBLE);
                    bmbFixDue.boom();


                }
            }));
        }

        //Initialization circle
        BoomMenuButton bmb = (BoomMenuButton) view.findViewById(R.id.bmbCircle);
        final BoomMenuButton bmbFixCircle = bmbHam;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);


        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.bat)
                    .normalText("Prova");

            bmb.addBuilder(builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    bmbFixCircle.setVisibility(View.GONE);
                    bmbFix.setVisibility(View.VISIBLE);
                    bmbFix.boom();
                    //sendBodyTextToActivity("uno");
                }
            }));
        }

        //Target view Setter
        View t = view.findViewById(R.id.bmbCircle);
        Log.i("Valore di t", ""+t);
        TapTargetView.showFor(getActivity(),                 // `this` is an Activity
                TapTarget.forView(t, "This is a target", "We have the best targets, believe me")
                        // All options below are optional
                        .outerCircleColor(R.color.external)      // Specify a color for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(40)                  // Specify the size (in sp) of the title text
                        //.titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        //.descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        //.dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(false)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60) ,               // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {

                        BoomMenuButton bmb = (BoomMenuButton) getActivity().findViewById(R.id.bmbCircle);
                        Log.i("Valore di bmb dentro", ""+bmb);
                        bmb.boomImmediately();
                        view.dismiss(true);
                    }
                });
        return view;
    }

    private void sendBodyTextToActivity(String s){
        selectedButtonListener.onSelectedButton(s);
    }

}


