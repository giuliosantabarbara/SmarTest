package jumapp.com.smartest.Storage.DAOInterface;

import java.util.ArrayList;

import jumapp.com.smartest.Storage.DAOObject.Contest;

/**
 * Created by marco on 18/03/2017.
 */
public interface ContestDAO {

    public ArrayList<Contest> getAllContests();
    public Contest getContestById(long constestId);
    public void insert(Contest c);
    public Contest deleteContest(long contestId);
    public ArrayList<String> getAllContestsScopes();
    public ArrayList<String> getAllContestsPositionByScope(String scope);
    public ArrayList<String> getAllContestsYearsByScopeAndPosition(String scope,String position);
    public Contest getContestByScopePositionYear(String scope, String position, int year);
    public int numberOfRows();

}
