package de.cofinpro.dojo.minefx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ABorger on 29.08.2015.
 */
public class UserScoreEntry {

    private StringProperty userName = new SimpleStringProperty();
    private StringProperty color = new SimpleStringProperty();
    private IntegerProperty points = new SimpleIntegerProperty();

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public void addPoints(int points) {
        this.points.setValue(this.points.get() + points);
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
