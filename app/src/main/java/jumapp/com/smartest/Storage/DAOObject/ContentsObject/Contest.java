package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import jumapp.com.smartest.QuestionsHashMap;

/**
 * Created by marco on 18/03/2017.
 */
public class Contest {

    private long id_contest;
    private String type, scope, position, questionsURL, attachmentsURL, alternativesURL, announcementURL, shortDescription;
    private int year;
    private QuestionsHashMap questions;
    private ArrayList<String> categories= new ArrayList<>();

    /**
     * Instantiates a new Contest.
     */
    public  Contest(){}

    /**
     * Instantiates a new Contest.
     *
     * @param id_contest       the id contest
     * @param type             the type
     * @param scope            the scope
     * @param position         the position
     * @param questionsURL     the questions url
     * @param attachmentsURL   the attachments url
     * @param alternativesURL  the alternatives url
     * @param announcementURL  the announcement url
     * @param year             the year
     * @param shortDescription the short description
     * @param questions        the questions
     * @param categories       the categories
     */
    public Contest(long id_contest, String type, String scope, String position, String questionsURL, String attachmentsURL,
                   String alternativesURL, String announcementURL, int year, String shortDescription,
                   QuestionsHashMap questions, ArrayList<String> categories) {
        this.id_contest = id_contest;
        this.type = type;
        this.scope = scope;
        this.position = position;
        this.questionsURL = questionsURL;
        this.attachmentsURL = attachmentsURL;
        this.alternativesURL = alternativesURL;
        this.announcementURL = announcementURL;
        this.questions=questions;
        this.shortDescription=shortDescription;
        this.year = year;
        this.categories=categories;
    }

    /**
     * Instantiates a new Contest.
     *
     * @param id_contest       the id contest
     * @param type             the type
     * @param scope            the scope
     * @param position         the position
     * @param questionsURL     the questions url
     * @param attachmentsURL   the attachments url
     * @param alternativesURL  the alternatives url
     * @param announcementURL  the announcement url
     * @param year             the year
     * @param shortDescription the short description
     */
    public Contest(long id_contest, String type, String scope, String position, String questionsURL, String attachmentsURL,
                   String alternativesURL, String announcementURL, int year, String shortDescription) {
        this.id_contest = id_contest;
        this.type = type;
        this.scope = scope;
        this.position = position;
        this.questionsURL = questionsURL;
        this.attachmentsURL = attachmentsURL;
        this.alternativesURL = alternativesURL;
        this.announcementURL = announcementURL;
        this.shortDescription=shortDescription;
        this.year = year;
    }

    /**
     * Print log.
     *
     * @param tag the tag
     */
    public void printLog(String tag){
        Log.i(tag, "" + this.getId_contest());
        Log.i(tag, ""+this.getType());
        Log.i(tag, ""+this.scope);
        Log.i(tag, ""+this.position);
        Log.i(tag, ""+this.questionsURL);
        Log.i(tag, ""+this.attachmentsURL);
        Log.i(tag, ""+this.alternativesURL);
        Log.i(tag, ""+this.announcementURL);
        Log.i(tag, ""+this.year);
//        for(String s:categories) Log.i(tag,s);

    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public ArrayList<String> getCategories() {
        return categories;
    }

    /**
     * Sets categories.
     *
     * @param categories the categories
     */
    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    /**
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets short description.
     *
     * @param shortDescription the short description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets questions.
     *
     * @return the questions
     */
    public QuestionsHashMap getQuestions() {
        return questions;
    }

    /**
     * Sets questions.
     *
     * @param questions the questions
     */
    public void setQuestions(QuestionsHashMap questions) {
        this.questions = questions;
    }

    /**
     * Gets questions url.
     *
     * @return the questions url
     */
    public String getQuestionsURL() {
        return questionsURL;
    }

    /**
     * Sets questions url.
     *
     * @param questionsURL the questions url
     */
    public void setQuestionsURL(String questionsURL) {
        this.questionsURL = questionsURL;
    }

    /**
     * Gets attachments url.
     *
     * @return the attachments url
     */
    public String getAttachmentsURL() {
        return attachmentsURL;
    }

    /**
     * Sets attachments url.
     *
     * @param attachmentsURL the attachments url
     */
    public void setAttachmentsURL(String attachmentsURL) {
        this.attachmentsURL = attachmentsURL;
    }

    /**
     * Gets alternatives url.
     *
     * @return the alternatives url
     */
    public String getAlternativesURL() {
        return alternativesURL;
    }

    /**
     * Sets alternatives url.
     *
     * @param alternativesURL the alternatives url
     */
    public void setAlternativesURL(String alternativesURL) {
        this.alternativesURL = alternativesURL;
    }

    /**
     * Gets announcement url.
     *
     * @return the announcement url
     */
    public String getAnnouncementURL() {
        return announcementURL;
    }

    /**
     * Sets announcement url.
     *
     * @param announcementURL the announcement url
     */
    public void setAnnouncementURL(String announcementURL) {
        this.announcementURL = announcementURL;
    }


    /**
     * Gets id contest.
     *
     * @return the id contest
     */
    public long getId_contest() {
        return id_contest;
    }

    /**
     * Sets id contest.
     *
     * @param id_contest the id contest
     */
    public void setId_contest(long id_contest) {
        this.id_contest = id_contest;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets scope.
     *
     * @param scope the scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }
}