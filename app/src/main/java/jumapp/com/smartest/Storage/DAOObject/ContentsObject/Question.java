package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marco on 18/03/2017.
 */
public class Question  implements Parcelable {

    private long question_id, contest_id;
    private String category, text;
    private boolean studied,favorited,hasAttach;
    private ArrayList<Alternative> alternatives;
    private ArrayList<Attachment> attachments;


    /**
     * Instantiates a new Question.
     */
    public Question(){}

    /**
     * Instantiates a new Question.
     *
     * @param question_id  the question id
     * @param category     the category
     * @param text         the text
     * @param isFavorited  the is favorited
     * @param isStudied    the is studied
     * @param contest_id   the contest id
     * @param alternatives the alternatives
     * @param attachments  the attachments
     * @param hasAttach    the has attach
     */
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

    /**
     * Instantiates a new Question.
     *
     * @param question_id the question id
     * @param category    the category
     * @param text        the text
     * @param isFavorited the is favorited
     * @param isStudied   the is studied
     * @param contest_id  the contest id
     * @param hasAttach   the has attach
     */
    public Question(long question_id, String category, String text, boolean isFavorited, boolean isStudied,long contest_id, boolean hasAttach) {
        this.question_id = question_id;
        this.contest_id = contest_id;
        this.category = category;
        this.text = text;
        this.favorited = isFavorited;
        this.studied = isStudied;
        this.hasAttach= hasAttach;
    }


    /**
     * Instantiates a new Question.
     *
     * @param in the in
     */
    protected Question(Parcel in) {
        question_id = in.readLong();
        contest_id = in.readLong();
        category = in.readString();
        text = in.readString();
        studied = in.readByte() != 0;
        favorited = in.readByte() != 0;
        hasAttach = in.readByte() != 0;
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    /**
     * Print log.
     *
     * @param TAG the tag
     */
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


    /**
     * Gets attach.
     *
     * @return the attach
     */
    public boolean getAttach() {
        return hasAttach;
    }

    /**
     * Sets has attach.
     *
     * @param attach the attach
     */
    public void setHasAttach(boolean attach) {
        this.hasAttach = attach;
    }

    /**
     * Get has attach boolean.
     *
     * @return the boolean
     */
    public boolean getHasAttach(){return this.hasAttach;}

    /**
     * Gets contest id.
     *
     * @return the contest id
     */
    public long getContest_id() {
        return contest_id;
    }

    /**
     * Sets contest id.
     *
     * @param contest_id the contest id
     */
    public void setContest_id(long contest_id) {
        this.contest_id = contest_id;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
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
     * Gets studied.
     *
     * @return the studied
     */
    public boolean getStudied() {
        return studied;
    }

    /**
     * Sets studied.
     *
     * @param isStudied the is studied
     */
    public void setStudied(boolean isStudied) {
        this.studied = isStudied;
    }

    /**
     * Gets favorited.
     *
     * @return the favorited
     */
    public boolean getFavorited() {
        return favorited;
    }

    /**
     * Sets favorited.
     *
     * @param isFavorited the is favorited
     */
    public void setFavorited(boolean isFavorited) {
        this.favorited = isFavorited;
    }

    /**
     * Gets alternatives.
     *
     * @return the alternatives
     */
    public ArrayList<Alternative> getAlternatives() {
        return alternatives;
    }

    /**
     * Sets alternatives.
     *
     * @param alternatives the alternatives
     */
    public void setAlternatives(ArrayList<Alternative> alternatives) {
        this.alternatives = alternatives;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(question_id);
        dest.writeLong(contest_id);
        dest.writeString(category);
        dest.writeString(text);
        dest.writeInt(studied ? 1 : 0);
        dest.writeInt(favorited ? 1 : 0);
        dest.writeInt(hasAttach ? 1 : 0);
        dest.writeList(alternatives);
        dest.writeList(attachments);
    }
}
