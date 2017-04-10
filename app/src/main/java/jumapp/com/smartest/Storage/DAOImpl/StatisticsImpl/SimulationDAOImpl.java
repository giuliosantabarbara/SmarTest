package jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.SimulationDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Simulation;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.SimulationCategory;

/**
 * Created by giulio on 04/04/17.
 */

public class SimulationDAOImpl extends SQLiteOpenHelper implements SimulationDAO {

    public static final String DATABASE_NAME = "Simulation.db";
    public static final String CONTACTS_TABLE_NAME = "mySimulation";

    Context context;

    public SimulationDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(simulation_id integer primary key ,contest_id integer, day integer, month integer, year integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+CONTACTS_TABLE_NAME);
        onCreate(db);
        db.close();
    }



    public void deleteAll() {
        SQLiteDatabase dbN = this.getWritableDatabase();
        dbN.execSQL("DROP TABLE IF EXISTS \""+CONTACTS_TABLE_NAME+"\"");
        onCreate(dbN);
        dbN.close();
    }

    public int numberOfRows(SQLiteDatabase db){
        //SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        //db.close();
        return numRows;
    }



    @Override
    public ArrayList<Simulation> getSimulationsByContestId(long contestId, SQLiteDatabase dbSimulation) {
        ArrayList<Simulation> array_list = new ArrayList<Simulation>();

        Cursor res =  dbSimulation.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where contest_id='"+contestId+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {

            int simulationId = res.getInt(res.getColumnIndex("simulation_id"));
            int day = res.getInt(res.getColumnIndex("day"));
            int month = res.getInt(res.getColumnIndex("month"));
            int year = res.getInt(res.getColumnIndex("year"));

            SimulationCategoryDAOImpl sc = new SimulationCategoryDAOImpl(context);
            //Log.i("ID: ",""+simulationId);
            ArrayList<SimulationCategory> scList = sc.getCategoriesBySimulationId(simulationId,sc.getReadableDatabase());

            Simulation tmp= new Simulation(simulationId,contestId,day,month,year,scList);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    @Override
    public Simulation getSimulationBySimulationId(long simulationId, SQLiteDatabase dbSimulation) {

        Cursor res =  dbSimulation.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where simulation_id='"+simulationId+"'", null );
        res.moveToFirst();
        Log.i("Simualtion get sim",""+res.getCount());
        Simulation tmp = null;
        SimulationCategoryDAOImpl sc = new SimulationCategoryDAOImpl(context);
        SQLiteDatabase conn = sc.openWritableConnection();
        while(res.isAfterLast() == false) {

            int contestId = res.getInt(res.getColumnIndex("contest_id"));
            int day = res.getInt(res.getColumnIndex("day"));
            int month = res.getInt(res.getColumnIndex("month"));
            int year = res.getInt(res.getColumnIndex("year"));


            ArrayList<SimulationCategory> scList = sc.getCategoriesBySimulationId(simulationId,conn);

            tmp= new Simulation(simulationId,contestId,day,month,year,scList);
            Log.i("Simualtion get sim",""+tmp);
            res.moveToNext();
        }
        conn.close();
        //openReadableConnection().close();
        res.close();

        return tmp;
    }

    @Override
    public void insert(Simulation s, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("contest_id",  s.getId_contest() );
        contentValues.put("day",  s.getDay());
        contentValues.put("month ", s.getMonth());
        contentValues.put("year", s.getYear());

        SimulationCategoryDAOImpl sc = new SimulationCategoryDAOImpl(context);

        int rowId;

        if (numberOfRows(db)==0){
            Log.i("POOOOOOO","asd");
            rowId=0;
        }

        else {
            Cursor res = db.rawQuery("SELECT max(simulation_id) as rowid FROM\"" + CONTACTS_TABLE_NAME + "\" LIMIT 1", null);
            res.moveToFirst();
            rowId = res.getInt(res.getColumnIndex("rowid"));
            //Log.i("???",""+(rowId+1));
            res.close();

        }

        SQLiteDatabase conn = sc.openWritableConnection();
        for (SimulationCategory category : s.getSimulationCategories()){
            category.setId_simulation(rowId+1);
            Log.i("SETTA",""+category.getId_simulation());
            sc.insert(category,conn);
        }
        conn.close();

        //if (!db.isOpen())
          //  db = this.openWritableConnection();
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        //db.close();

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
    public ArrayList<Simulation> deleteAllSimulation(long contestId, SQLiteDatabase dbSimulation) {
        ArrayList<Simulation> result= getSimulationsByContestId(contestId, dbSimulation);
        if(result!=null) {
            //SQLiteDatabase db = this.getWritableDatabase();

            SimulationCategoryDAOImpl simCatDAO= new SimulationCategoryDAOImpl(context);
            SQLiteDatabase conn = simCatDAO.openWritableConnection();
            for(Simulation c: result){
                ArrayList<SimulationCategory> simTmp = c.getSimulationCategories();
                for(SimulationCategory cat: simTmp){
                    simCatDAO.deleteAllCategories(cat.getId_simulation(),conn);
                }
            }

            conn.close();
            dbSimulation.delete(CONTACTS_TABLE_NAME, "contest_id='" + contestId + "'", null);
            //db.close();
        }
        return result;
    }

    @Override
    public Simulation deleteSimulation(long simulationId, SQLiteDatabase dbSimulation) {
        Simulation s = getSimulationBySimulationId(simulationId,dbSimulation);
        Log.i("Simualtion needed into",""+s);
        if (s!=null){

            SimulationCategoryDAOImpl simCatDAO= new SimulationCategoryDAOImpl(context);
            SQLiteDatabase conn = simCatDAO.openWritableConnection();

            ArrayList<SimulationCategory> simTmp = s.getSimulationCategories();
            for(SimulationCategory cat: simTmp){
                simCatDAO.deleteAllCategories(cat.getId_simulation(),conn);
            }
            conn.close();

            //SQLiteDatabase db = this.getWritableDatabase();
            dbSimulation.delete(CONTACTS_TABLE_NAME, "simulation_id='" + simulationId + "'", null);
            //db.close();
        }
        return s;
    }
}
