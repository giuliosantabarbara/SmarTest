package jumapp.com.smartest.Exercise;

/**
 * Created by marco on 08/04/2017.
 */
public class Pair{

    private String category;
    private int number;

    public Pair(String category, int number){
        this.category=category;
        this.number=number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}