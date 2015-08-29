package de.cofinpro.dojo.minefx;

/**
 * Created by ABorger on 29.08.2015.
 */
public class UserScoreEntry {

    private String userName;
    private String color;
    private int points;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int pointValue){
        this.points += pointValue;
    }

    @Override
    public String toString() {
        return "UserScoreEntry{" +
                "userName='" + userName + '\'' +
                ", color='" + color + '\'' +
                ", points=" + points +
                '}';
    }
}
