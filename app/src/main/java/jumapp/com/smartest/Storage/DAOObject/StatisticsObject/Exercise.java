package jumapp.com.smartest.Storage.DAOObject.StatisticsObject;

/**
 * Created by giulio on 04/04/17.
 */

public class Exercise {

    private long id_contest;
    private String categoryName;
    private int numAnswered, totQuestions;
    private int percentage;


    /**
     * Instantiates a new Exercise.
     *
     * @param id_contest   the id contest
     * @param categoryName the category name
     * @param numAnswered  the num answered
     * @param totQuestions the tot questions
     */
    public Exercise(long id_contest, String categoryName, int numAnswered, int totQuestions){


        this.id_contest=id_contest;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;


    }

    /**
     * Instantiates a new Exercise.
     *
     * @param id_contest   the id contest
     * @param categoryName the category name
     * @param numAnswered  the num answered
     * @param totQuestions the tot questions
     * @param percentage   the percentage
     */
    public Exercise(long id_contest, String categoryName, int numAnswered, int totQuestions, int percentage){


        this.id_contest=id_contest;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
        this.percentage=percentage;


    }

    /**
     * Gets id contest.
     *
     * @return the id contest
     */
    public long getId_contest() {
        return id_contest;
    }


    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Gets num answered.
     *
     * @return the num answered
     */
    public int getNumAnswered() {
        return numAnswered;
    }

    /**
     * Gets tot questions.
     *
     * @return the tot questions
     */
    public int getTotQuestions() {
        return totQuestions;
    }

    /**
     * Gets percentage.
     *
     * @return the percentage
     */
    public int getPercentage() {

        Double d = (((double)numAnswered/(double)totQuestions)*100);
        percentage = d.intValue();
        return percentage;
    }
}
