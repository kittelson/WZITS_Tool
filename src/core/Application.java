/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ltrask
 */
public class Application implements Serializable {

    private final long serialVersionUID = 123456789L;

    /**
     * Name of the application.
     */
    private SimpleStringProperty name;
    /**
     * Score associated with the application.
     */
    private SimpleIntegerProperty score;

    public Application(String name) {
        this(name, 0);
    }

    public Application(String name, int score) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String val) {
        this.name.set(val);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int val) {
        score.set(val);
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(getName());
        s.writeInt(getScore());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        name = new SimpleStringProperty((String) s.readObject());
        score = new SimpleIntegerProperty(s.readInt());
    }

    public static final String QUEUE_WARNING = "Queue Warning";
    public static final String REAL_TIME_TRAVELER_INFO = "Real Time Traveler Information";
    public static final String INC_MANAGEMENT = "Incident Management";
    public static final String DYNAMIC_LANE_MERGE = "Dynamic Lane Merge";
    public static final String VSL = "Variable Speed Limit";
    public static final String AUTO_ENFORCE = "Automated Enforcement";
    public static final String CONTR_VEH_ENTER_WARNING = "Construction Vehicle Entrance/Exit Warning";
    public static final String TEMP_RAMP_METER = "Temporary Ramp Metering";
    public static final String PERFORMANCE_MEASUREMENT = "Performance Measurement";
}
