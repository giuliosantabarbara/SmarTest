package jumapp.com.smartest.RemoteConnection;

/**
 * Created by marco on 18/03/2017.
 */
public interface Connector {

    public boolean downloadContestMetaData();
    public boolean downloadContest(long id);

}
