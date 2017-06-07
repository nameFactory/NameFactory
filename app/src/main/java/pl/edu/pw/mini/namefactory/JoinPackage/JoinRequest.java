package pl.edu.pw.mini.namefactory.JoinPackage;

/**
 * Created by Asus on 07.06.2017.
 */

public class JoinRequest {
    private String rankingName;
    private int ID;
    private String userName;

    public JoinRequest(int id, String rankingName, String username) {
        this.rankingName = rankingName;
        this.ID = id;
        this.userName = username;
    }

    public int getID() {return ID;}

    public String getUserName() {return userName;}

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String name) {
        this.rankingName = name;
    }
    
    public void setUserName(String name) {
        this.userName = name;
    }
}
