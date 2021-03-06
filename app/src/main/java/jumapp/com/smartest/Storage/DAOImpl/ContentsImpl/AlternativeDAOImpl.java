package jumapp.com.smartest.Storage.DAOImpl.ContentsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AlternativeDAO;
import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public class AlternativeDAOImpl extends SQLiteOpenHelper implements AlternativeDAO {


    public static final String DATABASE_NAME = "Alternatives.db";
    public static final String CONTACTS_TABLE_NAME = "myAlternatives";

    Context context;

    public AlternativeDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(alternative_id integer primary key autoincrement,Text text, isRight integer," +
                        "question_id integer, hasAttachment integer)"
        );

        db.execSQL("CREATE INDEX alternative_question_id_index on " + CONTACTS_TABLE_NAME + " (question_id);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + CONTACTS_TABLE_NAME);
        onCreate(db);
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
    public void deleteAll(SQLiteDatabase dbN) {
        dbN.execSQL("DROP TABLE IF EXISTS \""+CONTACTS_TABLE_NAME+"\"");
        onCreate(dbN);
    }

    @Override
    public int numberOfRows(SQLiteDatabase db){
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public void deleteAll() {
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
    public void insert(Alternative a,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        db.beginTransaction();
        int isRight=0;
        if(a.getRight()) isRight=1;
        contentValues.put("alternative_id", a.getAlternative_id());
        contentValues.put("Text", a.getText());
        contentValues.put("isRight", isRight);
        contentValues.put("question_id", a.getQuestion_id());
        contentValues.put("hasAttachment", a.getHasAttach());
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public Alternative deleteAlterantive(long alternativeId, SQLiteDatabase dbAtt) {
        Alternative result= getAlternativeById(alternativeId,dbAtt);
        if(result!=null) {
            //SQLiteDatabase db = this.getWritableDatabase();
            dbAtt.delete(CONTACTS_TABLE_NAME, "alternative_id='" + alternativeId + "'", null);
            //db.close();
        }
        return result;
    }


    @Override
    public ArrayList<Alternative> getAllAlternatives(SQLiteDatabase db) {
        ArrayList<Alternative> array_list = new ArrayList<Alternative>();
        //SQLiteDatabase db = this.getReadableDatabase();
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
            //ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "alternative",dbAtt);
            ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(alternative_id, "alternative",dbAtt);
            dbAtt.close();

            Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        //db.close();
        return array_list;
    }

    @Override
    public SQLiteDatabase openConnection(){
        return this.getReadableDatabase();
    }

    public void closeConnection(SQLiteDatabase db){
        db.close();
    }

    @Override
    public ArrayList<Alternative> getAllAlternativesByQuestionId(long questionId,SQLiteDatabase dbAlt, SQLiteDatabase dbAtt) {

        ArrayList<Alternative> array_list = new ArrayList<Alternative>();
        long start=System.currentTimeMillis();
        Cursor res =  dbAlt.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where question_id='" + questionId + "'", null);
        res.moveToFirst();
        long endInit=System.currentTimeMillis();
        AttachmentDAO att= new AttachmentDAOImpl(context);


        long sumAtt=0;
       // Log.i("LLL init ALTERNATIVE: ", "" + (endInit - start));

        long startWhile=System.currentTimeMillis();
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
            }
            long endAttach = System.currentTimeMillis();
            sumAtt=sumAtt+(endAttach-stAttach);

            Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
            array_list.add(tmp);
            res.moveToNext();
        }
        res.close();
        long endWHILE=System.currentTimeMillis();
      //  Log.i("LLL ALT GET ATT TIM: ", "" + (sumAtt));
       // Log.i("LLL ALT GET all TIM: ", "" + (endWHILE-startWhile));

        return array_list;
    }


    @Override
    public Alternative getAlternativeById(long alternativeId, SQLiteDatabase db) {
        Cursor res =  db.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\" where alternative_id='"+alternativeId+"'", null );
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

        AttachmentDAO att= new AttachmentDAOImpl(context);
        SQLiteDatabase conn = att.openReadableConnection();
        //ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(question_id, "alternative", conn);
        ArrayList<Attachment> attachments= att.getAllAttachmentsByLinkId(alternative_id, "alternative",conn);

        conn.close();
        Alternative tmp= new Alternative(alternative_id,text,isRight,question_id,attachments,hasAttachment);
        res.close();
        return tmp;
    }


}
