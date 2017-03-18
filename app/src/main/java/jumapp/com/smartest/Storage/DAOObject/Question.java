package jumapp.com.smartest.Storage.DAOObject;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marco on 18/03/2017.
 */
public class Question {

    private long question_id, contest_id;
    private String category, text;
    private boolean studied,favorited,hasAttach;
    private ArrayList<Alternative> alternatives;
    private ArrayList<Attachment> attachments;


    public Question(){}

    public Question(long question_id, String category, String text, boolean isFavorited, boolean isStudied,long contest_id,
                    ArrayList<Alternative> alternatives, ArrayList<Attachment> attachments, boolean hasAttach) {
        this.question_id = question_id;
        this.contest_id = contest_id;
        this.category = category;
        this.text = text;
        this.favorited = isFavorited;
        this.studied = isStudied;
        this.alternatives = alternatives;
        this.attachments = attachments;
        this.hasAttach=hasAttach;
    }

    public Question(long question_id, String category, String text, boolean isFavorited, boolean isStudied,long contest_id, boolean hasAttach) {
        this.question_id = question_id;
        this.contest_id = contest_id;
        this.category = category;
        this.text = text;
        this.favorited = isFavorited;
        this.studied = isStudied;
        this.hasAttach= hasAttach;
    }


    public void printLog(String TAG){
        Log.i(TAG, "" + this.getQuestion_id());
        Log.i(TAG, "" + this.getCategory());
        Log.i(TAG, ""+this.getFavorited());
        Log.i(TAG, ""+this.getStudied());
        Log.i(TAG, ""+this.getContest_id());
        Log.i(TAG, ""+this.getText());
        if(alternatives!=null)
            for (Alternative alt: alternatives){
                alt.printLog("ALT");
            }
        if(attachments!=null)
            for (Attachment att: attachments){
                att.printLog("ATT");
            }


    }


    public boolean getAttach() {
        return hasAttach;
    }

    public void setHasAttach(boolean attach) {
        this.hasAttach = attach;
    }

    public boolean getHasAttach(){return this.hasAttach;}

    public long getContest_id() {
        return contest_id;
    }

    public void setContest_id(long contest_id) {
        this.contest_id = contest_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getStudied() {
        return studied;
    }

    public void setStudied(boolean isStudied) {
        this.studied = isStudied;
    }

    public boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(boolean isFavorited) {
        this.favorited = isFavorited;
    }

    public ArrayList<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(ArrayList<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }
}
