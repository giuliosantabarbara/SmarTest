package jumapp.com.smartest.Storage.DAOInterface.ContentsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.ContentsObject.Contest;

/**
 * Created by marco on 18/03/2017.
 */
public interface ContestDAO {

    /**
     * Gets contest by id.
     *
     * @param constestId the constest id
     * @param db         the db
     * @return the contest by id
     */
    public Contest getContestById(long constestId, SQLiteDatabase db);

    /**
     * Insert.
     *
     * @param c  the c
     * @param db the db
     */
    public void insert(Contest c,SQLiteDatabase db );

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
     * Gets all contests scopes.
     *
     * @param db the db
     * @return the all contests scopes
     */
    public ArrayList<String> getAllContestsScopes(SQLiteDatabase db);

    /**
     * Gets all contests.
     *
     * @param db the db
     * @return the all contests
     */
    public ArrayList<Contest> getAllContests(SQLiteDatabase db);

    /**
     * Delete all.
     *
     * @param dbN the db n
     */
    public void deleteAll(SQLiteDatabase dbN);

    /**
     * Number of rows int.
     *
     * @param dbN the db n
     * @return the int
     */
    public int numberOfRows(SQLiteDatabase dbN);

    //public Contest deleteContest(long contestId,SQLiteDatabase dbN);

    /**
     * Gets all contests position by scope.
     *
     * @param scope the scope
     * @param db    the db
     * @return the all contests position by scope
     */
    public ArrayList<String> getAllContestsPositionByScope(String scope,SQLiteDatabase db);

    /**
     * Gets all contests years by scope and position.
     *
     * @param scope    the scope
     * @param position the position
     * @param db       the db
     * @return the all contests years by scope and position
     */
    public ArrayList<String> getAllContestsYearsByScopeAndPosition(String scope,String position,SQLiteDatabase db);

    /**
     * Gets contest by scope position year.
     *
     * @param scope    the scope
     * @param position the position
     * @param year     the year
     * @param db       the db
     * @return the contest by scope position year
     */
    public Contest getContestByScopePositionYear(String scope, String position, int year,SQLiteDatabase db);


}
