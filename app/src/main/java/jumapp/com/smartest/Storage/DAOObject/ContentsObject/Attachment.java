package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

import android.util.Log;

/**
 * Created by marco on 18/03/2017.
 */
public class Attachment {

    public Attachment(){}

    private long  attachment_id, link_id;
    private String fireurl, localurl,type;

    public Attachment(long attachment_id, String fireurl, String localurl,long link_id, String type) {
        this.link_id = link_id;
        this.fireurl = fireurl;
        this.localurl = localurl;
        this.attachment_id=attachment_id;
        this.type=type;
    }

    public void printLog(String Tag){
        Log.i(Tag, "" + getAttachment_id());
        Log.i(Tag,""+getFireurl());
        Log.i(Tag,""+getLocalurl());
        Log.i(Tag,""+getLink_id());
        Log.i(Tag,""+getType());
    }




    public long getLink_id() {
        return link_id;
    }

    public void setLink_id(long link_id) {
        this.link_id = link_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(long attachment_id) {
        this.attachment_id = attachment_id;
    }


    public String getFireurl() {
        return fireurl;
    }

    public void setFireurl(String fireurl) {
        this.fireurl = fireurl;
    }

    public String getLocalurl() {
        return localurl;
    }

    public void setLocalurl(String localurl) {
        this.localurl = localurl;
    }
}
