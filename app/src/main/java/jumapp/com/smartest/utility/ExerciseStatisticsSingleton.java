package jumapp.com.smartest.utility;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jumapp.com.smartest.Storage.DAOInterface.StatisticsInterface.ExerciseDAO;
import jumapp.com.smartest.Storage.DAOObject.StatisticsObject.Exercise;


/**
 * Created by giulio on 08/04/17.
 */

public class ExerciseStatisticsSingleton {


    private static ExerciseStatisticsSingleton mInstance;
    private ExerciseDAO exerciseDao;
    private long contestId;

    private ArrayList<Exercise> exercises= new ArrayList<Exercise>();

    protected ExerciseStatisticsSingleton(ExerciseDAO exerciseDao, long contestId){

        this.exerciseDao = exerciseDao;
        this.contestId = contestId;
        SQLiteDatabase conn = exerciseDao.openReadableConnection();
        exercises = exerciseDao.getExerciseByContestId(contestId,conn);
        conn.close();

    }

    public static ExerciseStatisticsSingleton getInstance(ExerciseDAO exerciseDao, long contestId){
        if(null == mInstance){
            mInstance = new ExerciseStatisticsSingleton(exerciseDao, contestId);
        }
        return mInstance;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public Exercise getExerciseByIndex(int index){
        return exercises.get(index);
    }

    public void setExercises(ArrayList<Exercise> exercises) {

        this.exercises = exercises;

    }

    public void setExercise(Exercise exercise) {
        this.exercises.add(exercise);
        SQLiteDatabase conn = exerciseDao.openWritableConnection();
        exerciseDao.insert(exercise,conn);
        conn.close();
    }

}