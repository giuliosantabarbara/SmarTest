package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Attachment;

/**
 * Created by marco on 18/03/2017.
 */
public interface AttachmentDAO  {

    /**
     * Gets all attachments by link id.
     *
     * @param LinkId the link id
     * @param Type   the type
     * @param db     the db
     * @return the all attachments by link id
     */
    public ArrayList<Attachment> getAllAttachmentsByLinkId(long LinkId,String Type, SQLiteDatabase db);

    /**
     * Gets attachment by id.
     *
     * @param attachmentId the attachment id
     * @param db           the db
     * @return the attachment by id
     */
    public Attachment getAttachmentById(long attachmentId,SQLiteDatabase db);

    /**
     * Open writable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openWritableConnection();

    /**
     * Open readable connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openReadableConnection();

    /**
     * Delete all.
     *
     * @param dbN the db n
     */
    public void deleteAll(SQLiteDatabase dbN);

    /**
     * Number of rows int.
     *
     * @param db the db
     * @return the int
     */
    public int numberOfRows(SQLiteDatabase db);

    /**
     * Insert.
     *
     * @param a  the a
     * @param db the db
     */
    public void insert(Attachment a,SQLiteDatabase db);

    /**
     * Delete attachment attachment.
     *
     * @param attachmentId the attachment id
     * @param db           the db
     * @return the attachment
     */
    public Attachment deleteAttachment(long attachmentId,SQLiteDatabase db);

    /**
     * Gets all attachments.
     *
     * @param db the db
     * @return the all attachments
     */
    public ArrayList<Attachment> getAllAttachments(SQLiteDatabase db);

    /**
     * Open connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openConnection();


}
