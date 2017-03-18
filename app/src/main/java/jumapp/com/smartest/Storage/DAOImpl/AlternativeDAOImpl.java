package jumapp.com.smartest.Storage.DAOImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public class AlternativeDAOImpl extends SQLiteOpenHelper implements AlternativeDAO {


    public static final String DATABASE_NAME = "Alternatives.db";
    public static final String CONTACTS_TABLE_NAME = "myAlternatives";

    Context context;


    public AlternativeDAOImpl(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(alternative_id integer primary key autoincrement,Text text, isRight integer," +
                        "question_id integer, hasAttachment integer)"
        );

        db.execSQL("CREATE INDEX alternative_question_id_index on " + CONTACTS_TABLE_NAME + " (question_id);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS" + CONTACTS_TABLE_NAME);
        onCreate(db);
        db.close();
    }



    public void deleteAll() {
        // TODO Auto-generated method stub
        SQLiteDatabase dbN = this.getWritableDatabase();
        dbN.execSQL("DROP TABLE IF EXISTS \""+CONTACTS_TABLE_NAME+"\"");
        onCreate(dbN);
        dbN.close();
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        db.close();
        return numRows;
    }

    @Override
    public void insert(Alternative a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int isRight=0;
        if(a.getRight()) isRight=1;
        contentValues.put("alternative_id", a.getAlternative_id());
        contentValues.put("Text", a.getText());
        contentValues.put("isRight", isRight);
        contentValues.put("question_id", a.getQuestion_id());
        contentValues.put("hasAttachment",a.getHasAttach());

        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        db.close();
    }

    @Override
    public Alternative deleteAlterantive(long alternativeId, SQLiteDatabase dbAtt) {
        Alternative result= getAlternativeById(alternativeId,dbAtt);
        if(result!=null) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(CONTACTS_TABLE_NAME, "alternative_id='" + alternativeId + "'", null);
            db.close();
        }
        return result;
    }


    @Override
    public ArrayList<Alternative> getAllAlternatives() {
        ArrayList<Alternative> array_list = new ArrayList<Alternative>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){

            long alternative_id=Long.parseLong(res.getString(res.getColumnIndex("alternative_id")));
            String text=res.getString(res.getColumnIndex("Text"));
            int isRightint= Integer.parseInt(res.getString(res.getColumnIndex("isRight")));
            boolean isRight=false;
            if(isRightint==1) isRight=true;
            long question_id=Long.parseLong(res.getString(res.getColumnIndex("question_id")));
            boolean hasAttachment=false;
            int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
            if(intHasAttachment==1) hasAttachment=true;


            AttachmentDAOImpl att= new AttachmentDAOImpl(context);
            SQLiteDatabase dbAtt=att.openConnection();
            ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "alternative",dbAtt);
            dbAtt.close();

            Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }


    public SQLiteDatabase openConnection(){
        return this.getReadableDatabase();
    }

    public void closeConnection(SQLiteDatabase db){
        db.close();
    }

    @Override
    public ArrayList<Alternative> getAllAlternativesByQuestionId(long questionId,
                                                                 SQLiteDatabase dbAlt, SQLiteDatabase dbAtt) {
        long st=System.currentTimeMillis();
        ArrayList<Alternative> array_list = new ArrayList<Alternative>();
        Cursor res =  dbAlt.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where question_id='" + questionId + "'", null);
        res.moveToFirst();
        AttachmentDAOImpl att= new AttachmentDAOImpl(context);

        long sumAtt=0;

        while(res.isAfterLast() == false){

            long alternative_id=Long.parseLong(res.getString(res.getColumnIndex("alternative_id")));
            String text=res.getString(res.getColumnIndex("Text"));
            int isRightint= Integer.parseInt(res.getString(res.getColumnIndex("isRight")));
            boolean isRight=false;
            if(isRightint==1) isRight=true;
            long question_id=Long.parseLong(res.getString(res.getColumnIndex("question_id")));
            boolean hasAttachment=false;
            int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
            if(intHasAttachment==1) hasAttachment=true;



            long stAttach=System.currentTimeMillis();
            ArrayList<Attachment> attachments=null;
            if (hasAttachment) {
                attachments = att.getAllAttachmentsByLinkId(alternative_id, "alternative", dbAtt);
                if(alternative_id==6) Log.i("#####################", "SIZE: " + attachments.size());
            }

            long endAttach = System.currentTimeMillis();
            sumAtt=sumAtt+(endAttach-stAttach);

            Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();

        long end=System.currentTimeMillis();
        //    Log.i("###", "Tempo del get Attachments in Alternatives:" + sumAtt);
        //   Log.i("###","Tempo alternatives extra operations:"+(end-st-sumAtt));

        return array_list;
    }


    @Override
    public Alternative getAlternativeById(long alternativeId, SQLiteDatabase dbAtt) {
        Cursor res =  dbAtt.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where alternative_id='"+alternativeId+"'", null );
        res.moveToFirst();

        long alternative_id=Long.parseLong(res.getString(res.getColumnIndex("alternative_id")));
        String text=res.getString(res.getColumnIndex("Text"));
        int isRightint= Integer.parseInt(res.getString(res.getColumnIndex("isRight")));
        boolean isRight=false;
        if(isRightint==1) isRight=true;
        long question_id=Long.parseLong(res.getString(res.getColumnIndex("question_id")));
        boolean hasAttachment=false;
        int intHasAttachment=Integer.parseInt(res.getString(res.getColumnIndex("hasAttachment")));
        if(intHasAttachment==1) hasAttachment=true;

        AttachmentDAOImpl att= new AttachmentDAOImpl(context);
        ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "alternative", dbAtt);

        Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
        res.close();
        return tmp;
    }




}
