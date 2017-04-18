package jumapp.com.smartest.Storage.DAOImpl.StatisticsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;

/**
 * Created by giulio on 04/04/17.
 */

public class ExerciseDAOImpl extends SQLiteOpenHelper implements ExerciseDAO{

    public static final String DATABASE_NAME = "Exercises.db";
    public static final String CONTACTS_TABLE_NAME = "myExercises";

    Context context;

    public ExerciseDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(exercise_id integer primary key ,contest_id integer,category_name text, num_answered integer, tot_questions integer, percentage integer)"
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
    public void insert(Exercise e, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("contest_id",  e.getId_contest() );
        contentValues.put("category_name",  e.getCategoryName() );
        contentValues.put("num_answered ", e.getNumAnswered());
        contentValues.put("tot_questions", e.getTotQuestions());
        contentValues.put("percentage",e.getPercentage());
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
    }

    public SQLiteDatabase openConnection(){
        return this.getReadableDatabase();

    }

    public void closeConnection(SQLiteDatabase db){
        db.close();
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
    public ArrayList<Exercise> getExerciseByContestId(long contestId, SQLiteDatabase dbExercise) {


        ArrayList<Exercise> array_list = new ArrayList<Exercise>();

        Cursor res =  dbExercise.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where contest_id='"+contestId+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {

            String category_name = res.getString(res.getColumnIndex("category_name"));
            int num_answered = res.getInt(res.getColumnIndex("num_answered"));
            int tot_questions = res.getInt(res.getColumnIndex("tot_questions"));
            int percentage = res.getInt(res.getColumnIndex("percentage"));

            Exercise tmp= new Exercise(contestId,category_name,num_answered,tot_questions,percentage);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }



    @Override
    public ArrayList<String> getAllCategoriesByContestId(long contest_id, SQLiteDatabase dbExercise) {
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res =  dbExercise.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where contest_id='"+contest_id+"' group by category_name", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("category_name")));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }


    @Override
    public ArrayList<Exercise> deleteExerciseByContestId(long contest_id, SQLiteDatabase dbExercise) {
        ArrayList<Exercise> result= getExerciseByContestId(contest_id, dbExercise);
        if(result!=null) {
            //SQLiteDatabase db = this.getWritableDatabase();
            dbExercise.delete(CONTACTS_TABLE_NAME, "contest_id='" + contest_id + "'", null);
            //db.close();
        }
        return result;
    }


}
