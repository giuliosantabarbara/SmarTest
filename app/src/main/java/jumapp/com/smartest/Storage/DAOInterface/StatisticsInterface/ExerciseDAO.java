package jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;

/**
 * Created by giulio on 04/04/17.
 */

public interface ExerciseDAO {

    public ArrayList<Exercise> getExerciseByContestId(long contestId, SQLiteDatabase dbExercise);
    public void insert(Exercise e,SQLiteDatabase db);
    public SQLiteDatabase openWritableConnection();
    public ArrayList<String> getAllCategoriesByContestId(long contest_id);
    //public ArrayList<Exercise> deleteExercise(long contest_id, SQLiteDatabase dbExercise);


}
