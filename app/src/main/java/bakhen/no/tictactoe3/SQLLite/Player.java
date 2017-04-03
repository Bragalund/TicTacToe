package bakhen.no.tictactoe3.SQLLite;

import java.io.Serializable;

public class Player implements Serializable {
    private String ID;
    private String userName;
    private int losses;
    private int wins;


    public Player(String ID, String userName, int losses, int wins) {
        this.ID = ID;
        this.userName = userName;
        this.losses = losses;
        this.wins = wins;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
