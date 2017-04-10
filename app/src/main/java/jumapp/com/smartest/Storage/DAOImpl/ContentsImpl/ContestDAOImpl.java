package jumapp.com.smartest.Storage.DAOImpl.ContentsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.ContestDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Question;

/**
 * Created by marco on 18/03/2017.
 */
public class ContestDAOImpl  extends SQLiteOpenHelper implements ContestDAO {


    public static final String DATABASE_NAME = "Contest.db";
    public static final String CONTACTS_TABLE_NAME = "myContests";

    Context context;


    public ContestDAOImpl(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(contest_id integer primary key ,Type text,Scope text, Position text,Year integer, QuestionsURL text," +
                        "AttachmentsURL text, AlternativesURL text, AnnouncementsURL text, ShortDescription text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS" + CONTACTS_TABLE_NAME);
        onCreate(db);
    }



    public void deleteAll() {
        // TODO Auto-generated method stub
        SQLiteDatabase dbN = this.getWritableDatabase();
        dbN.execSQL("DROP TABLE IF EXISTS \"" + CONTACTS_TABLE_NAME + "\"");
        onCreate(dbN);
        dbN.close();
    }

    @Override
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        db.close();
        return numRows;
    }

    @Override
    public SQLiteDatabase openWritableConnection(){
        return this.getWritableDatabase();
    }

    @Override
    public void insert(Contest c,SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();

        contentValues.put("contest_id", c.getId_contest());
        contentValues.put(" Type",  c.getType() );
        contentValues.put("Scope",  c.getScope() );
        contentValues.put("Position ", c.getPosition());
        contentValues.put("Year", c.getYear());
        contentValues.put("QuestionsURL", c.getQuestionsURL());
        contentValues.put("AttachmentsURL", c.getAttachmentsURL());
        contentValues.put("AlternativesURL", c.getAlternativesURL());
        contentValues.put("AnnouncementsURL", c.getAnnouncementURL());
        contentValues.put("ShortDescription", c.getShortDescription());
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);

    }

    @Override
    public ArrayList<Contest> getAllContests(SQLiteDatabase db) {
        ArrayList<Contest> array_list = new ArrayList<Contest>();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\"", null);
        res.moveToFirst();

        long st=System.currentTimeMillis();
        QuestionDAOImpl quest= new QuestionDAOImpl(context);
        AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);
        SQLiteDatabase dbQuest=quest.openConnection();
        SQLiteDatabase dbAlt=alt.openConnection();
        SQLiteDatabase dbAtt=att.openConnection();

        while(res.isAfterLast() == false){
            long contest_id=Long.parseLong(res.getString(res.getColumnIndex("contest_id")));
            String type=res.getString(res.getColumnIndex("Type"));
            String scope=res.getString(res.getColumnIndex("Scope"));
            String position=res.getString(res.getColumnIndex("Position"));
            int year= Integer.parseInt(res.getString(res.getColumnIndex("Year")));
            String questionsURL=res.getString(res.getColumnIndex("QuestionsURL"));
            String attachmentsURL=res.getString(res.getColumnIndex("AttachmentsURL"));
            String alternativesURL=res.getString(res.getColumnIndex("AlternativesURL"));
            String announcmentsURL=res.getString(res.getColumnIndex("AnnouncementsURL"));
            String shortDescription= res.getString(res.getColumnIndex("ShortDescription"));

            ArrayList<Question> questions=quest.getAllQuestionsByContestId(contest_id,dbAlt, dbQuest,dbAtt);
            // ArrayList<String> categories=quest.getAllCategoriesByContestId(contest_id);

            Contest tmp= new Contest(contest_id, type, scope, position,questionsURL,
                    attachmentsURL,alternativesURL,announcmentsURL,year,shortDescription,questions,null);
            array_list.add(tmp);
            res.moveToNext();
        }

        quest.closeConnection(dbQuest);
        alt.closeConnection(dbAlt);
        att.closeConnection(dbAtt);
        long end=System.currentTimeMillis();
        Log.i("###", "time to get all Questions + extra operation della classe contestDAO " + (end - st));

        res.close();
        return array_list;
    }


    @Override
    public ArrayList<Contest> getAllContests() {
        ArrayList<Contest> array_list = new ArrayList<Contest>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\"", null);
        res.moveToFirst();

        long st=System.currentTimeMillis();
        QuestionDAOImpl quest= new QuestionDAOImpl(context);
        AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);
        SQLiteDatabase dbQuest=quest.openConnection();
        SQLiteDatabase dbAlt=alt.openConnection();
        SQLiteDatabase dbAtt=att.openConnection();

        while(res.isAfterLast() == false){
            long contest_id=Long.parseLong(res.getString(res.getColumnIndex("contest_id")));
            String type=res.getString(res.getColumnIndex("Type"));
            String scope=res.getString(res.getColumnIndex("Scope"));
            String position=res.getString(res.getColumnIndex("Position"));
            int year= Integer.parseInt(res.getString(res.getColumnIndex("Year")));
            String questionsURL=res.getString(res.getColumnIndex("QuestionsURL"));
            String attachmentsURL=res.getString(res.getColumnIndex("AttachmentsURL"));
            String alternativesURL=res.getString(res.getColumnIndex("AlternativesURL"));
            String announcmentsURL=res.getString(res.getColumnIndex("AnnouncementsURL"));
            String shortDescription= res.getString(res.getColumnIndex("ShortDescription"));

            ArrayList<Question> questions=quest.getAllQuestionsByContestId(contest_id,dbAlt, dbQuest,dbAtt);
            // ArrayList<String> categories=quest.getAllCategoriesByContestId(contest_id);

            Contest tmp= new Contest(contest_id, type, scope, position,questionsURL,
                    attachmentsURL,alternativesURL,announcmentsURL,year,shortDescription,questions,null);
            array_list.add(tmp);
            res.moveToNext();
        }

        quest.closeConnection(dbQuest);
        alt.closeConnection(dbAlt);
        att.closeConnection(dbAtt);
        long end=System.currentTimeMillis();
        Log.i("###", "time to get all Questions + extra operation della classe contestDAO " + (end - st));

        res.close();
        db.close();
        return array_list;
    }

    @Override
    public Contest getContestById(long contest_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where contest_id='" + contest_id + "'", null);
        res.moveToFirst();

        String type=res.getString(res.getColumnIndex("Type"));
        String scope=res.getString(res.getColumnIndex("Scope"));
        String position=res.getString(res.getColumnIndex("Position"));
        int year= Integer.parseInt(res.getString(res.getColumnIndex("Year")));
        String questionsURL=res.getString(res.getColumnIndex("QuestionsURL"));
        String attachmentsURL=res.getString(res.getColumnIndex("AttachmentsURL"));
        String alternativesURL=res.getString(res.getColumnIndex("AlternativesURL"));
        String announcmentsURL=res.getString(res.getColumnIndex("AnnouncementsURL"));
        String shortDescription= res.getString(res.getColumnIndex("ShortDescription"));

        long st=System.currentTimeMillis();
        QuestionDAOImpl quest= new QuestionDAOImpl(context);
        AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);

        SQLiteDatabase dbQuest=quest.openConnection();
        SQLiteDatabase dbAlt=alt.openConnection();
        SQLiteDatabase dbAtt=att.openConnection();

        ArrayList<Question> questions=quest.getAllQuestionsByContestId(contest_id,dbAlt, dbQuest,dbAtt);
        quest.closeConnection(dbQuest);
        alt.closeConnection(dbAlt);
        att.closeConnection(dbAtt);
        //ArrayList<String> categories=quest.getAllCategoriesByContestId(contest_id);

        Contest tmp= new Contest(contest_id, type, scope, position,questionsURL,attachmentsURL,
                alternativesURL,announcmentsURL,year, shortDescription,questions,null);

        res.close();
        db.close();
        return tmp;
    }



    @Override
    public Contest deleteContest(long contestId){
        return null;
    }

    @Override
    public ArrayList<String> getAllContestsScopes() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\"", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            String scope=res.getString(res.getColumnIndex("Scope"));
            array_list.add(scope);
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }

    @Override
    public ArrayList<String> getAllContestsPositionByScope(String scope) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where Scope='" + scope+ "'", null);
        res.moveToFirst();

        ArrayList<String> array_list = new ArrayList<String>();
        while(res.isAfterLast() == false){
            String tmp=res.getString(res.getColumnIndex("Position"));
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;

    }

    @Override
    public ArrayList<String> getAllContestsYearsByScopeAndPosition(String scope, String position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where Scope='" + scope+ "' AND Position='"+position+"'", null);
        res.moveToFirst();

        ArrayList<String> array_list = new ArrayList<String>();
        while(res.isAfterLast() == false){
            String year=res.getString(res.getColumnIndex("Year"));
            array_list.add(year);
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }

    @Override
    public Contest getContestByScopePositionYear(String scope, String position, int year) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where Scope='" + scope + "' AND " +
                "Position='"+position+"' AND Year='"+year+"'", null);
        res.moveToFirst();

        long contest_id=Long.parseLong(res.getString(res.getColumnIndex("contest_id")));
        String type=res.getString(res.getColumnIndex("Type"));
        String questionsURL=res.getString(res.getColumnIndex("QuestionsURL"));
        String attachmentsURL=res.getString(res.getColumnIndex("AttachmentsURL"));
        String alternativesURL=res.getString(res.getColumnIndex("AlternativesURL"));
        String announcmentsURL=res.getString(res.getColumnIndex("AnnouncementsURL"));
        String shortDescription= res.getString(res.getColumnIndex("ShortDescription"));

        long st=System.currentTimeMillis();
        QuestionDAOImpl quest= new QuestionDAOImpl(context);
        AlternativeDAOImpl alt= new AlternativeDAOImpl(context);
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);

        SQLiteDatabase dbQuest=quest.openConnection();
        SQLiteDatabase dbAlt=alt.openConnection();
        SQLiteDatabase dbAtt=att.openConnection();

        ArrayList<Question> questions=quest.getAllQuestionsByContestId(contest_id,dbAlt, dbQuest,dbAtt);
        quest.closeConnection(dbQuest);
        alt.closeConnection(dbAlt);
        att.closeConnection(dbAtt);
        ArrayList<String> categories=quest.getAllCategoriesByContestId(contest_id);



        Contest tmp= new Contest(contest_id, type, scope, position,questionsURL,attachmentsURL,
                alternativesURL,announcmentsURL,year, shortDescription,questions,categories);

        res.close();
        db.close();
        return tmp;
    }


}
