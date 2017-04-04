package jumapp.com.smartest.Storage.DAOObject.StatisticsObject;

/**
 * Created by giulio on 04/04/17.
 */

public class Exercise {

    private long id_contest;
    private String categoryName;
    private int numAnswered, totQuestions;
    private int percentage;


    public Exercise(long id_contest, String categoryName, int numAnswered, int totQuestions){


        this.id_contest=id_contest;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;


    }

    public Exercise(long id_contest, String categoryName, int numAnswered, int totQuestions, int percentage){


        this.id_contest=id_contest;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
        this.percentage=percentage;


    }

    public long getId_contest() {
        return id_contest;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public int getNumAnswered() {
        return numAnswered;
    }

    public int getTotQuestions() {
        return totQuestions;
    }

    public int getPercentage() {

        Double d = (((double)numAnswered/(double)totQuestions)*100);
        percentage = d.intValue();
        return percentage;
    }
}
