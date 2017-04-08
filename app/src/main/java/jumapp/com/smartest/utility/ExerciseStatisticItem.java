package jumapp.com.smartest.utility;

/**
 * Created by giulio on 08/04/17.
 */

public class ExerciseStatisticItem {


    private String title;
    private int percentage;

    public ExerciseStatisticItem(String title, int percentage) {
        super();
        this.percentage = percentage;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
