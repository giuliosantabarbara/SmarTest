package jumapp.com.smartest.fragments;

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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jumapp.com.smartest.R;


import jumapp.com.smartest.RemoteConnection.Connector;
import jumapp.com.smartest.RemoteConnection.FirebaseConnector;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.ContestDAOImpl;
import jumapp.com.smartest.Storage.DAOImpl.ContentsImpl.QuestionDAOImpl;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.QuestionDAO;





/**
 * Created by giuli on 08/02/2017.
 */

public class CircleHamButtonFragment extends Fragment {


    private OnSelectedButtonListener selectedButtonListener;
    public interface OnSelectedButtonListener{
        public void onSelectedButton(String s);
    }
    private int call;
    HamButton.Builder builderHam2;
    SweetAlertDialog contestDialog;


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
        final View view = inflater.inflate(R.layout.circle_ham_fragment, container,false);

        ContestDAO conDAO= new ContestDAOImpl(view.getContext());

        //Initialization third ham
        BoomMenuButton bmbHamDue = (BoomMenuButton) view.findViewById(R.id.bmbHammDue);
        Log.i("Valore ham ",""+bmbHamDue);
        final BoomMenuButton bmbFixDue = bmbHamDue;
        Log.i("Valore ham final ",""+bmbHamDue);
        //bmbHam.setVisibility(View.VISIBLE);
        bmbHamDue.setButtonEnum(ButtonEnum.Ham);
        bmbHamDue.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
        bmbHamDue.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);

        //j<bmbHamDue.getPiecePlaceEnum().pieceNumber()
        for (int j = 0; j < 1; j++) {
            HamButton.Builder builderHam = new HamButton.Builder()
                    .normalImageRes(R.drawable.bat)
                    .normalText("2017");

            bmbHamDue.addBuilder(builderHam.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    sendBodyTextToActivity("uno");

                }
            }));
        }

        //Initialization SECOND ham
        final BoomMenuButton  bmbHam2= (BoomMenuButton) view.findViewById(R.id.bmbHamm);
        bmbHam2.setButtonEnum(ButtonEnum.Ham);
        bmbHam2.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
        bmbHam2.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);


        //j<bmbHam2.getPiecePlaceEnum().pieceNumber()
        for (int j = 0; j < 1; j++) {
            builderHam2 = new HamButton.Builder();
            builderHam2.normalImageRes(R.drawable.bat);
            builderHam2.normalText("Ufficiali");

            HamButton.Builder builder=builderHam2.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    //sendBodyTextToActivity("uno");
                    Log.i("###", "SONO NEL PRIMO");
                    bmbHam2.setVisibility(View.GONE);
                    bmbFixDue.setVisibility(View.VISIBLE);
                    bmbFixDue.boom();


                }
            });

            bmbHam2.addBuilder(builder);
        }

        //Initialization FIRST ham
        final BoomMenuButton bmbHam1 = (BoomMenuButton) view.findViewById(R.id.bmbHamm0);
        bmbHam1.setNormalColor(R.color.pallino);
        SQLiteDatabase db = conDAO.openReadableConnection();
        ArrayList<String> scopes=conDAO.getAllContestsScopes(db);
        db.close();
        setEnum(bmbHam1, scopes.size());

        for (int j = 0; j < bmbHam1.getPiecePlaceEnum().pieceNumber(); j++) {
            final HamButton.Builder builderHam = new HamButton.Builder();
            String name=scopes.get(j).toLowerCase();
            String label=name.substring(0, 1).toUpperCase() + name.substring(1);

            builderHam.normalText(label);

            bmbHam1.addBuilder(builderHam.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    bmbHam1.setVisibility(View.GONE);
                    bmbHam2.setVisibility(View.VISIBLE);
                    bmbHam2.boom();
                }
            }));

        }



        //Target view Setter
        View t = view.findViewById(R.id.bmbHamm0);
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

                        ContestDAO conDAO= new ContestDAOImpl(view.getContext());
                        SQLiteDatabase db = conDAO.openReadableConnection();
                        if(conDAO.numberOfRows(db)>0) {

                            bmbHam1.boomImmediately();
                            view.dismiss(true);
                        }
                        db.close();
                    }
                });


        return view;
    }



    private void sendBodyTextToActivity(String s){
        selectedButtonListener.onSelectedButton(s);
    }

    private void setEnum(BoomMenuButton bmb, int size){
        bmb.setButtonEnum(ButtonEnum.Ham);

        switch (size){
            case 1:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);
                break;
            case 2:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_2);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_2);
                break;
            case 3:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
                break;
            case 4:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
                break;
            case 5:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_6);
                break;
            case 6:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_6);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_6);
                break;
            default:
                bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_6);
                bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_6);
                break;
        }
    }

}


