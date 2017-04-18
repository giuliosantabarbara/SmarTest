package jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationCategoryDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 04/04/17.
 */

public class SimulationCategoryDAOImpl extends SQLiteOpenHelper implements SimulationCategoryDAO {

    public static final String DATABASE_NAME = "SimulationCategory.db";
    public static final String CONTACTS_TABLE_NAME = "mySimulationCategory";

    Context context;

    public SimulationCategoryDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(simulation_category_id integer primary key ,simulation_id integer, category_name text, num_answered integer, tot_questions integer, percentage integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+CONTACTS_TABLE_NAME);
        onCreate(db);
        db.close();
    }


    @Override
    public void deleteAll(SQLiteDatabase dbN) {
        //SQLiteDatabase dbN = this.getWritableDatabase();
        dbN.execSQL("DROP TABLE IF EXISTS \""+CONTACTS_TABLE_NAME+"\"");
        onCreate(dbN);
        //dbN.close();
    }

    @Override
    public int numberOfRows(SQLiteDatabase db){
        //SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        //db.close();
        return numRows;
    }


    @Override
    public void insert(SimulationCategory sc, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("simulation_id",  sc.getId_simulation() );
        contentValues.put("category_name",  sc.getCategoryName() );
        contentValues.put("num_answered ", sc.getNumAnswered());
        contentValues.put("tot_questions", sc.getTotQuestions());
        contentValues.put("percentage",sc.getPercentage());
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
    }

    @Override
    public SQLiteDatabase openWritableConnection(){
        return this.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase openReadableConnection(){
        return this.getReadableDatabase();
    }


    @Override
    public ArrayList<SimulationCategory> getCategoriesBySimulationId(long simulationId, SQLiteDatabase dbSimulationCategory) {

        ArrayList<SimulationCategory> array_list = new ArrayList<SimulationCategory>();

        Cursor res =  dbSimulationCategory.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where simulation_id='"+simulationId+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {

            String category_name = res.getString(res.getColumnIndex("category_name"));
            int num_answered = res.getInt(res.getColumnIndex("num_answered"));
            int tot_questions = res.getInt(res.getColumnIndex("tot_questions"));
            int percentage = res.getInt(res.getColumnIndex("percentage"));

            SimulationCategory tmp= new SimulationCategory(simulationId,category_name,num_answered,tot_questions,percentage);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    @Override
    public ArrayList<SimulationCategory> deleteAllCategories(long simulationId, SQLiteDatabase dbSimulationCategory) {
        ArrayList<SimulationCategory> result= getCategoriesBySimulationId(simulationId, dbSimulationCategory);
        if(result!=null) {
            //SQLiteDatabase db = this.getWritableDatabase();
            dbSimulationCategory.delete(CONTACTS_TABLE_NAME, "simulation_id='" + simulationId + "'", null);
            //db.close();
        }
        return result;
    }

}
