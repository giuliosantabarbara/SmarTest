package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;

/**
 * Created by marco on 18/03/2017.
 */
public interface ContestDAO {

    public Contest getContestById(long constestId, SQLiteDatabase db);
    public void insert(Contest c,SQLiteDatabase db );
    public SQLiteDatabase openWritableConnection();
    public SQLiteDatabase openReadableConnection();
    public ArrayList<String> getAllContestsScopes(SQLiteDatabase db);
    public ArrayList<Contest> getAllContests(SQLiteDatabase db);
    public void deleteAll(SQLiteDatabase dbN);
    public int numberOfRows(SQLiteDatabase dbN);

    //public Contest deleteContest(long contestId,SQLiteDatabase dbN);

    public ArrayList<String> getAllContestsPositionByScope(String scope,SQLiteDatabase db);
    public ArrayList<String> getAllContestsYearsByScopeAndPosition(String scope,String position,SQLiteDatabase db);
    public Contest getContestByScopePositionYear(String scope, String position, int year,SQLiteDatabase db);


}
