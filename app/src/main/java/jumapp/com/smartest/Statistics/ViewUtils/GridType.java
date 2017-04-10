package jumapp.com.smartest.Statistics.ViewUtils;


/**
 * Created by giulio on 09/04/17.
 */

public class GridType {


    private String categoryName;
    private int percentage;

    public GridType(String categoryName, int percentage){
        this.categoryName = categoryName;
        this.percentage = percentage;


    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
