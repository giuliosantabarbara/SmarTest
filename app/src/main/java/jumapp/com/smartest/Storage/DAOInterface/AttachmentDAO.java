package jumapp.com.smartest.Storage.DAOInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public interface AttachmentDAO  {

    public ArrayList<Attachment> getAllAttachmentsByLinkId(long LinkId,String Type, SQLiteDatabase db);
    public Attachment getAttachmentById(long attachmentId);
    public SQLiteDatabase openWritableConnection();
    public void insert(Attachment a,SQLiteDatabase db);
    public Attachment deleteAttachment(long attachmentId);
    public ArrayList<Attachment> getAllAttachments();
    public SQLiteDatabase openConnection();


}
