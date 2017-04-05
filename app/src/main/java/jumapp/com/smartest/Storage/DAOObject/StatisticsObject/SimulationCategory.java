package jumapp.com.smartest.Storage.DAOObject.StatisticsObject;

/**
 * Created by giulio on 04/04/17.
 */

public class SimulationCategory {


    private long id_simulation;
    private String categoryName;
    private int percentage;
    private int numAnswered, totQuestions;

    public SimulationCategory(long id_simulation, String categoryName, int numAnswered, int totQuestions){

        this.id_simulation=id_simulation;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
    }

    public SimulationCategory(long id_simulation, String categoryName, int numAnswered, int totQuestions, int percentage){

        this.id_simulation=id_simulation;
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
        this.percentage = percentage;
    }

    public SimulationCategory(String categoryName, int numAnswered, int totQuestions){
        this.categoryName=categoryName;
        this.numAnswered=numAnswered;
        this.totQuestions=totQuestions;
    }


    public void setId_simulation(int id_simulation) {
        this.id_simulation = id_simulation;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setNumAnswered(int numAnswered) {
        this.numAnswered = numAnswered;
    }

    public void setTotQuestions(int totQuestions) {
        this.totQuestions = totQuestions;
    }

    public long getId_simulation() {
        return id_simulation;
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

    public int getPercentage(){
        Double d = (((double)numAnswered/(double)totQuestions)*100);
        percentage = d.intValue();
        return percentage;
    }
}
