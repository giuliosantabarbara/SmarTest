package jumapp.com.smartest.Storage.DAOObject.StatisticsObject;

/**
 * Created by giulio on 04/04/17.
 */

public class SimulationCategory {


    private long id_simulation;
    private String categoryName;
    private int percentage;
    private int numAnswered, totQuestions;

    /**
     * Instantiates a new Simulation category.
     *
     * @param id_simulation the id simulation
     * @param categoryName  the category name
     * @param numAnswered   the num answered
     * @param totQuestions  the tot questions
     */
    public SimulationCategory(long id_simulation, String categoryName, int numAnswered, int totQuestions){

        this.id_simulation=id_simulation;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
    }

    /**
     * Instantiates a new Simulation category.
     *
     * @param id_simulation the id simulation
     * @param categoryName  the category name
     * @param numAnswered   the num answered
     * @param totQuestions  the tot questions
     * @param percentage    the percentage
     */
    public SimulationCategory(long id_simulation, String categoryName, int numAnswered, int totQuestions, int percentage){

        this.id_simulation=id_simulation;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
        this.percentage = percentage;
    }

    /**
     * Instantiates a new Simulation category.
     *
     * @param categoryName the category name
     * @param numAnswered  the num answered
     * @param totQuestions the tot questions
     */
    public SimulationCategory(String categoryName, int numAnswered, int totQuestions){
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
    }


    /**
     * Sets id simulation.
     *
     * @param id_simulation the id simulation
     */
    public void setId_simulation(int id_simulation) {
        this.id_simulation = id_simulation;
    }

    /**
     * Sets category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Sets percentage.
     *
     * @param percentage the percentage
     */
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    /**
     * Sets num answered.
     *
     * @param numAnswered the num answered
     */
    public void setNumAnswered(int numAnswered) {
        this.numAnswered = numAnswered;
    }

    /**
     * Sets tot questions.
     *
     * @param totQuestions the tot questions
     */
    public void setTotQuestions(int totQuestions) {
        this.totQuestions = totQuestions;
    }

    /**
     * Gets id simulation.
     *
     * @return the id simulation
     */
    public long getId_simulation() {
        return id_simulation;
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
     * Get percentage int.
     *
     * @return the int
     */
    public int getPercentage(){
        Double d = (((double)numAnswered/(double)totQuestions)*100);
        percentage = d.intValue();
        return percentage;
    }
}
