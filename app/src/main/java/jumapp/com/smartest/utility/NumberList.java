package jumapp.com.smartest.utility;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class NumberList {

    private static List<ColorItem> list;


    /*public NumberList(int num) {
        this.list = null;
        this.num=num;
    }*/

    public static List<ColorItem> loadDemoColorItems() {

        List<ColorItem> items = new ArrayList<>();

        for (int i=1; i<=25; i++){
            ColorItem colorItem = new ColorItem();
            colorItem.name = "   "+i;

            colorItem.color = Color.GRAY;
            items.add(colorItem);
        }
        list = items;
        /*try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, "colors.json"));
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = obj.getJSONObject(key);
                ColorItem colorItem = new ColorItem();
                colorItem.name = value.getString("name");
                colorItem.hex = value.getString("hex");
                JSONArray rgb = value.getJSONArray("rgb");
                colorItem.color = Color.rgb(rgb.getInt(0), rgb.getInt(1), rgb.getInt(2));
                items.add(colorItem);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }*/
        return items;
    }

    public static List<ColorItem> getList() {
        return list;
    }


}
