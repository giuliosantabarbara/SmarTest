package jumapp.com.smartest.Exercise.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by marco on 22/04/2017.
 */
public class WhatsappManager {

public static void sendMessage(Activity activity,  String message){
    boolean isWhatsappInstalled= whatsappInstalledOrNot(activity,"com.whatsapp");
    if (isWhatsappInstalled) {

        Intent sendIntent2 = new Intent();
        sendIntent2.setAction(Intent.ACTION_SEND);
        sendIntent2.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent2.setType("text/plain");
        sendIntent2.setPackage("com.whatsapp");
        activity.startActivity(sendIntent2);


    } else {
        Toast.makeText(activity, "WhatsApp not Installed",
                Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse("market://details?id=com.whatsapp");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(goToMarket);

    }
}

    private static boolean whatsappInstalledOrNot(Activity activity, String uri) {
        PackageManager pm = activity.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}
