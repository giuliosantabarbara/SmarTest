package jumapp.com.smartest.Storage.DAOObject.ContentsObject;

import android.util.Log;

/**
 * Created by marco on 18/03/2017.
 */
public class Attachment {

    /**
     * Instantiates a new Attachment.
     */
    public Attachment(){}

    private long  attachment_id, link_id;
    private String fireurl, localurl,type;

    /**
     * Instantiates a new Attachment.
     *
     * @param attachment_id the attachment id
     * @param fireurl       the fireurl
     * @param localurl      the localurl
     * @param link_id       the link id
     * @param type          the type
     */
    public Attachment(long attachment_id, String fireurl, String localurl,long link_id, String type) {
        this.link_id = link_id;
        this.fireurl = fireurl;
        this.localurl = localurl;
        this.attachment_id=attachment_id;
        this.type=type;
    }

    /**
     * Print log.
     *
     * @param Tag the tag
     */
    public void printLog(String Tag){
        Log.i(Tag, "" + getAttachment_id());
        Log.i(Tag,""+getFireurl());
        Log.i(Tag,""+getLocalurl());
        Log.i(Tag,""+getLink_id());
        Log.i(Tag,""+getType());
    }


    /**
     * Gets link id.
     *
     * @return the link id
     */
    public long getLink_id() {
        return link_id;
    }

    /**
     * Sets link id.
     *
     * @param link_id the link id
     */
    public void setLink_id(long link_id) {
        this.link_id = link_id;
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
     * Gets attachment id.
     *
     * @return the attachment id
     */
    public long getAttachment_id() {
        return attachment_id;
    }

    /**
     * Sets attachment id.
     *
     * @param attachment_id the attachment id
     */
    public void setAttachment_id(long attachment_id) {
        this.attachment_id = attachment_id;
    }


    /**
     * Gets fireurl.
     *
     * @return the fireurl
     */
    public String getFireurl() {
        return fireurl;
    }

    /**
     * Sets fireurl.
     *
     * @param fireurl the fireurl
     */
    public void setFireurl(String fireurl) {
        this.fireurl = fireurl;
    }

    /**
     * Gets localurl.
     *
     * @return the localurl
     */
    public String getLocalurl() {
        return localurl;
    }

    /**
     * Sets localurl.
     *
     * @param localurl the localurl
     */
    public void setLocalurl(String localurl) {
        this.localurl = localurl;
    }
}
