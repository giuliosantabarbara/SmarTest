package jumapp.com.smartest.Exercise.RecyclerViewUtils;

public class ColorItem {

    public String name;
    public int color;
    private boolean isSetted;

    public ColorItem(String name, int color){
        this.color=color;
        this.name=name;
        this.isSetted=false;
    }

    public ColorItem(){

    }

    public int getColorItem(){
        return color;
    }

    public void setColorItem(int color){
        this.color=color;
    }

    public void setSetted(){
        this.isSetted=true;
    }

    public boolean getSetted(){
        return isSetted;
    }
}
