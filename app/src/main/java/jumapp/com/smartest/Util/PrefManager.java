package jumapp.com.smartest.Util;

import android.content.Context;
import android.content.SharedPreferences;

import static jumapp.com.smartest.R.id.pref;

/**
 * Created by Apoll on 19/08/2017.
 */

public class PrefManager {




    // Shared preferences file name
    private static final String PREF_NAME = "anchor";
    public static final String DRAG_GIF= "www.smartest.com.drag.gif";




    public static void setBooleanPref(Context context,String TAG,boolean isFirstTime) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor= pref.edit();

        editor.putBoolean(TAG, isFirstTime);
        editor.commit();
    }

    public static boolean getBooleanPref(Context context,String TAG) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor= pref.edit();
        return pref.getBoolean(TAG, false);
    }

}