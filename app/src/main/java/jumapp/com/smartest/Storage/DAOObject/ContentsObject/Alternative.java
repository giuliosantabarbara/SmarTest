package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

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


    /**
     * Instantiates a new Alternative.
     *
     * @param alternative_id the alternative id
     * @param text           the text
     * @param isRight        the is right
     * @param question_id    the question id
     * @param attachments    the attachments
     * @param hasAttach      the has attach
     */
    public Alternative(long alternative_id,  String text,boolean isRight,long question_id, ArrayList <Attachment> attachments,boolean hasAttach) {
        this.question_id = question_id;
        this.alternative_id = alternative_id;
        this.right = isRight;
        this.text = text;
        this.attachments=attachments;
        this.hasAttach=hasAttach;
    }


    /**
     * Print log.
     *
     * @param tag the tag
     */
    public void printLog(String tag){
        Log.i(tag, "" + this.alternative_id);
        Log.i(tag, ""+this.text);
        Log.i(tag, ""+this.right);
        Log.i(tag, ""+this.question_id);
        if(attachments!=null && attachments.size()>0)for(Attachment a: attachments) a.printLog("ATT");
    }

    /**
     * Instantiates a new Alternative.
     */
    public Alternative(){}


    /**
     * Gets has attach.
     *
     * @return the has attach
     */
    public boolean getHasAttach() {
        return hasAttach;
    }

    /**
     * Sets has attach.
     *
     * @param hasAttach the has attach
     */
    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
    }

    /**
     * Gets attachments.
     *
     * @return the attachments
     */
    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Sets attachments.
     *
     * @param attachments the attachments
     */
    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Gets question id.
     *
     * @return the question id
     */
    public long getQuestion_id() {
        return question_id;
    }

    /**
     * Sets question id.
     *
     * @param question_id the question id
     */
    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    /**
     * Gets alternative id.
     *
     * @return the alternative id
     */
    public long getAlternative_id() {
        return alternative_id;
    }

    /**
     * Sets alternative id.
     *
     * @param alternative_id the alternative id
     */
    public void setAlternative_id(long alternative_id) {
        this.alternative_id = alternative_id;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets right.
     *
     * @return the right
     */
    public boolean getRight() {
        return right;
    }

    /**
     * Sets right.
     *
     * @param right the right
     */
    public void setRight(boolean right) {
        this.right = right;
    }

}
