package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;


/**
 * The interface Exercise dao.
 *
 * Created by giulio on 04/04/17.
 */
public interface ExerciseDAO {

    /**
     * Gets exercise by contest id.
     *
     * @param contestId  the contest id
     * @param dbExercise the db exercise
     * @return the exercise by contest id
     */
    public ArrayList<Exercise> getExerciseByContestId(long contestId, SQLiteDatabase dbExercise);

    /**
     * Insert.
     *
     * @param e  the e
     * @param db the db
     */
    public void insert(Exercise e,SQLiteDatabase db);

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
     * Number of rows int.
     *
     * @param db the db
     * @return the int
     */
    public int numberOfRows(SQLiteDatabase db);

    /**
     * Delete all.
     *
     * @param dbN the db n
     */
    public void deleteAll(SQLiteDatabase dbN);

    /**
     * Gets all categories by contest id.
     *
     * @param contest_id the contest id
     * @param dbExercise the db exercise
     * @return the all categories by contest id
     */
    public ArrayList<String> getAllCategoriesByContestId(long contest_id,SQLiteDatabase dbExercise);

    /**
     * Delete exercise by contest id array list.
     *
     * @param contest_id the contest id
     * @param dbExercise the db exercise
     * @return the array list
     */
    public ArrayList<Exercise> deleteExerciseByContestId(long contest_id, SQLiteDatabase dbExercise);


}
