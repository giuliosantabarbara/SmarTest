package jumapp.com.smartest.Storage.DAOObject;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marco on 18/03/2017.
 */
public class Alternative {

    private long question_id, alternative_id;
    private String text;
    private boolean right, hasAttach;
    private ArrayList<Attachment> attachments= new ArrayList<Attachment>();


    public Alternative(long alternative_id,  String text,boolean isRight,long question_id, ArrayList <Attachment> attachments,boolean hasAttach) {
        this.question_id = question_id;
        this.alternative_id = alternative_id;
        this.right = isRight;
        this.text = text;
        this.attachments=attachments;
        this.hasAttach=hasAttach;
    }


    public void printLog(String tag){
        Log.i(tag, "" + this.alternative_id);
        Log.i(tag, ""+this.text);
        Log.i(tag, ""+this.right);
        Log.i(tag, ""+this.question_id);
        if(attachments!=null && attachments.size()>0)for(Attachment a: attachments) a.printLog("ATT");
    }

    public Alternative(){}


    public boolean getHasAttach() {
        return hasAttach;
    }

    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
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

    public long getAlternative_id() {
        return alternative_id;
    }

    public void setAlternative_id(long alternative_id) {
        this.alternative_id = alternative_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
