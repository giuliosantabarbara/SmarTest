package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Alternative;

/**
 * Created by marco on 18/03/2017.
 */
public interface AlternativeDAO {

    public ArrayList<Alternative> getAllAlternativesByQuestionId(long questionId, SQLiteDatabase db,SQLiteDatabase dbAtt);
    public ArrayList<Alternative> getAllAlternatives(SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();
    public SQLiteDatabase openReadableConnection();
    public void deleteAll(SQLiteDatabase dbN);
    public int numberOfRows(SQLiteDatabase db);
    public Alternative getAlternativeById(long alternativeId,SQLiteDatabase dbAtt);
    public void insert(Alternative a,SQLiteDatabase db);
    public Alternative deleteAlterantive(long alternativeId,SQLiteDatabase dbAtt);
    public SQLiteDatabase openConnection();

}
