package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;

/**
 * Created by marco on 18/03/2017.
 */
public interface AlternativeDAO {

    /**
     * Gets all alternatives by question id.
     *
     * @param questionId the question id
     * @param db         the db
     * @param dbAtt      the db att
     * @return the all alternatives by question id
     */
    public ArrayList<Alternative> getAllAlternativesByQuestionId(long questionId, SQLiteDatabase db,SQLiteDatabase dbAtt);

    /**
     * Gets all alternatives.
     *
     * @param db the db
     * @return the all alternatives
     */
    public ArrayList<Alternative> getAllAlternatives(SQLiteDatabase db);

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
     * Gets alternative by id.
     *
     * @param alternativeId the alternative id
     * @param dbAtt         the db att
     * @return the alternative by id
     */
    public Alternative getAlternativeById(long alternativeId,SQLiteDatabase dbAtt);

    /**
     * Insert.
     *
     * @param a  the a
     * @param db the db
     */
    public void insert(Alternative a,SQLiteDatabase db);

    /**
     * Delete alterantive alternative.
     *
     * @param alternativeId the alternative id
     * @param dbAtt         the db att
     * @return the alternative
     */
    public Alternative deleteAlterantive(long alternativeId,SQLiteDatabase dbAtt);

    /**
     * Open connection sq lite database.
     *
     * @return the sq lite database
     */
    public SQLiteDatabase openConnection();

}
