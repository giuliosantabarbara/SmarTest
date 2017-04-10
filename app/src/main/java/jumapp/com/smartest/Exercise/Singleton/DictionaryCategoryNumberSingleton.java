package jumapp.com.smartest.Exercise.Singleton;

import android.app.Application;

import java.util.ArrayList;

import jumapp.com.smartest.Exercise.Pair;


/**
 * Created by marco on 08/04/2017.
 */
public class DictionaryCategoryNumberSingleton extends Application {

    private static DictionaryCategoryNumberSingleton mInstance;

    private ArrayList<Pair> pairs= new ArrayList<Pair>();

    protected DictionaryCategoryNumberSingleton(){}

    public static  DictionaryCategoryNumberSingleton getInstance(){
        if(null == mInstance){
            mInstance = new DictionaryCategoryNumberSingleton();
        }
        return mInstance;
    }

    public ArrayList<Pair> getPairs(){
        return this.pairs;
    }

    public void add(Pair p){
        boolean find=false;
        int i=0;
        while(!find && i<pairs.size() ){
            if(pairs.get(i).getCategory().equalsIgnoreCase(p.getCategory())){
                pairs.get(i).setNumber(p.getNumber());
                find=true;
            }
            i++;
        }
        if(find==false){
            this.pairs.add(p);
        }
    }

    public int getNumberOfQuestion(){
        int result=0;
        for(Pair p: pairs){
            result+= p.getNumber();
        }
        return result;
    }


}
