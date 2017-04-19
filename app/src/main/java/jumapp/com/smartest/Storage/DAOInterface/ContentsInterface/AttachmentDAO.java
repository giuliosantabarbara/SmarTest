package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public interface AttachmentDAO  {

    public ArrayList<Attachment> getAllAttachmentsByLinkId(long LinkId,String Type, SQLiteDatabase db);
    public Attachment getAttachmentById(long attachmentId,SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();
    public SQLiteDatabase openReadableConnection();
    public void deleteAll(SQLiteDatabase dbN);
    public int numberOfRows(SQLiteDatabase db);
    public void insert(Attachment a,SQLiteDatabase db);
    public Attachment deleteAttachment(long attachmentId,SQLiteDatabase db);
    public ArrayList<Attachment> getAllAttachments(SQLiteDatabase db);
    public SQLiteDatabase openConnection();


}
