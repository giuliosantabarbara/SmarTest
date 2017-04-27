package jumapp.com.smartest.Statistics.ViewUtils;


/**
 * Created by giulio on 09/04/17.
 */
public class GridType {


    private String categoryName;
    private int percentage;

    /**
     * Instantiates a new Grid type.
     *
     * @param categoryName the category name
     * @param percentage   the percentage
     */
    public GridType(String categoryName, int percentage){
        this.categoryName = categoryName;
        this.percentage = percentage;


    }

    /**
     * Gets percentage.
     *
     * @return the percentage
     */
    public int getPercentage() {
        return percentage;
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
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
