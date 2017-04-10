package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by marco on 18/03/2017.
 */
public class Contest {

    private long id_contest;
    private String type, scope, position, questionsURL, attachmentsURL, alternativesURL, announcementURL, shortDescription;
    private int year;
    private ArrayList<Question> questions= new ArrayList<Question>();
    private ArrayList<String> categories= new ArrayList<>();

    public  Contest(){}

    public Contest(long id_contest, String type, String scope, String position, String questionsURL, String attachmentsURL,
                   String alternativesURL, String announcementURL, int year, String shortDescription,
                   ArrayList<Question> questions, ArrayList<String> categories) {
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
        for(Question q:questions){
            q.printLog("QUEST");
        }
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getQuestionsURL() {
        return questionsURL;
    }

    public void setQuestionsURL(String questionsURL) {
        this.questionsURL = questionsURL;
    }

    public String getAttachmentsURL() {
        return attachmentsURL;
    }

    public void setAttachmentsURL(String attachmentsURL) {
        this.attachmentsURL = attachmentsURL;
    }

    public String getAlternativesURL() {
        return alternativesURL;
    }

    public void setAlternativesURL(String alternativesURL) {
        this.alternativesURL = alternativesURL;
    }

    public String getAnnouncementURL() {
        return announcementURL;
    }

    public void setAnnouncementURL(String announcementURL) {
        this.announcementURL = announcementURL;
    }


    public long getId_contest() {
        return id_contest;
    }

    public void setId_contest(long id_contest) {
        this.id_contest = id_contest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}