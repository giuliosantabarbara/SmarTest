package jumapp.com.smartest.Statistics.Singleton;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;


/**
 * Created by giulio on 08/04/17.
 */
public class ExerciseStatisticsSingleton {


    private static ExerciseStatisticsSingleton mInstance;
    private static ExerciseDAO exerciseDao;
    private long contestId;

    private static ArrayList<Exercise> exercises= new ArrayList<Exercise>();

    /**
     * Instantiates a new Exercise statistics singleton.
     *
     * @param exerciseDao the exercise dao
     * @param contestId   the contest id
     */
    protected ExerciseStatisticsSingleton(ExerciseDAO exerciseDao, long contestId){

        this.exerciseDao = exerciseDao;
        this.contestId = contestId;
        SQLiteDatabase conn = exerciseDao.openReadableConnection();
        exercises = exerciseDao.getExerciseByContestId(contestId,conn);
        conn.close();

    }

    /**
     * Get instance exercise statistics singleton.
     *
     * @param exerciseDao the exercise dao
     * @param contestId   the contest id
     * @return the exercise statistics singleton
     */
    public static ExerciseStatisticsSingleton getInstance(ExerciseDAO exerciseDao, long contestId){
        if(null == mInstance){
            mInstance = new ExerciseStatisticsSingleton(exerciseDao, contestId);
        }
        return mInstance;
    }

    /**
     * Gets exercises.
     *
     * @return the exercises
     */
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Get exercise by index exercise.
     *
     * @param index the index
     * @return the exercise
     */
    public Exercise getExerciseByIndex(int index){
        return exercises.get(index);
    }

    /**
     * Sets exercises.
     *
     * @param exercises the exercises
     */
    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Sets exercise.
     *
     * @param exercise the exercise
     */
    public static void setExercise(Exercise exercise) {
        exercises.add(exercise);
        SQLiteDatabase conn = exerciseDao.openWritableConnection();
        exerciseDao.insert(exercise,conn);
        conn.close();
    }
}