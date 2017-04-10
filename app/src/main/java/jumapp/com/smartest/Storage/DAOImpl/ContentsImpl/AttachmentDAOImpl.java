package jumapp.com.smartest.Storage.DAOImpl.ContentsImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.ContentsInterface.AttachmentDAO;
import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public class AttachmentDAOImpl extends SQLiteOpenHelper implements AttachmentDAO {


    public static final String DATABASE_NAME = " Attachments.db";
    public static final String CONTACTS_TABLE_NAME = "myAttachments";

    Context context;


    public  AttachmentDAOImpl(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table \"" + CONTACTS_TABLE_NAME + "\"" +
                        "(attachment_id integer primary key autoincrement,firebaseURL text,localURL text," +
                        "link_id integer, Type text)"
        );

       // db.execSQL("CREATE INDEX attachment_linkId__type_index on "+ CONTACTS_TABLE_NAME+ " (link_id,Type);");

    }

    @Override
    public SQLiteDatabase openWritableConnection(){
        return this.getWritableDatabase();
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
    public void insert(Attachment a,SQLiteDatabase db) {
       ContentValues contentValues = new ContentValues();

        contentValues.put("attachment_id", a.getAttachment_id());
        contentValues.put("firebaseURL", a.getFireurl());
        contentValues.put("localURL", a.getLocalurl());
        contentValues.put("link_id", a.getLink_id());
        contentValues.put("Type", a.getType());

        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
    }


    @Override
    public Attachment deleteAttachment(long attachment_id) {
        Attachment result= getAttachmentById(attachment_id);
        if(result!=null) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(CONTACTS_TABLE_NAME, "attachment_id='" + attachment_id + "'", null);
            db.close();
        }
        return result;
    }


    @Override
    public ArrayList<Attachment> getAllAttachments() {
        ArrayList<Attachment> array_list = new ArrayList<Attachment>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from \""+CONTACTS_TABLE_NAME+"\"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){

            long attachment_id=Long.parseLong(res.getString(res.getColumnIndex("attachment_id")));
            String firebase_url=res.getString(res.getColumnIndex("firebaseURL"));
            String local_url=res.getString(res.getColumnIndex("localURL"));
            long link_id=Long.parseLong(res.getString(res.getColumnIndex("link_id")));
            String type=res.getString(res.getColumnIndex("Type"));

            Attachment tmp= new Attachment(attachment_id,firebase_url,local_url,link_id, type);
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
    public ArrayList<Attachment> getAllAttachmentsByLinkId(long link_id,String type, SQLiteDatabase db){
        ArrayList<Attachment> array_list = new ArrayList<Attachment>();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where link_id='" + link_id + "' " +
                "AND Type='"+type+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false){

            long attachment_id=Long.parseLong(res.getString(res.getColumnIndex("attachment_id")));
            String firebase_url=res.getString(res.getColumnIndex("firebaseURL"));
            String local_url=res.getString(res.getColumnIndex("localURL"));
            Attachment tmp= new Attachment(attachment_id,firebase_url,local_url,link_id, type);
            array_list.add(tmp);
            res.moveToNext();

        }
        res.close();
        return array_list;
    }

    @Override
    public Attachment getAttachmentById(long attachmentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from \"" + CONTACTS_TABLE_NAME + "\" where attachment_id='" + attachmentId + "'", null);
        res.moveToFirst();

        long attachment_id=Long.parseLong(res.getString(res.getColumnIndex("attachment_id")));
        String firebase_url=res.getString(res.getColumnIndex("firebaseURL"));
        String local_url=res.getString(res.getColumnIndex("localURL"));
        long link_id=Long.parseLong(res.getString(res.getColumnIndex("link_id")));
        String type=res.getString(res.getColumnIndex("type"));

        Attachment tmp= new Attachment(attachment_id,firebase_url,local_url,link_id, type);
        res.close();
        db.close();
        return tmp;
    }




}
