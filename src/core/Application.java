/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static core.ApplicationMatrix.HIGH_CAT_LABEL;
import static core.ApplicationMatrix.LOW_CAT_LABEL;
import static core.ApplicationMatrix.LOW_CAT_MAX;
import static core.ApplicationMatrix.LOW_CAT_MIN;
import static core.ApplicationMatrix.MED_CAT_LABEL;
import static core.ApplicationMatrix.MED_CAT_MAX;
import static core.ApplicationMatrix.MED_CAT_MIN;
import static core.ApplicationMatrix.ZERO_SCORE_TXT;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ltrask
 */
public class Application implements Serializable {

    private static final long serialVersionUID = 123456789L;

    /**
     * Name of the application.
     */
    private SimpleStringProperty name;
    /**
     * Score associated with the application.
     */
    private SimpleIntegerProperty score;
    /**
     * Indicates if the application has been selected by the user.
     */
    private BooleanProperty selected = new SimpleBooleanProperty(false);

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

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(getName());
        s.writeInt(getScore());
        s.writeBoolean(isSelected());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        name = new SimpleStringProperty((String) s.readObject());
        score = new SimpleIntegerProperty(s.readInt());
        selected = new SimpleBooleanProperty(s.readBoolean());
    }

    public static String getScoreString(int score) {
        if (score == 0) {
            return ZERO_SCORE_TXT;
        } else if (score >= LOW_CAT_MIN && score <= LOW_CAT_MAX) {
            return LOW_CAT_LABEL;
        } else if (score >= MED_CAT_MIN && score <= MED_CAT_MAX) {
            return MED_CAT_LABEL;
        } else {
            return HIGH_CAT_LABEL;
        }
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
