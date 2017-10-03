/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static core.GoalNeedsMatrix.HIGH_CAT_LABEL;
import static core.GoalNeedsMatrix.LOW_CAT_LABEL;
import static core.GoalNeedsMatrix.LOW_CAT_MAX;
import static core.GoalNeedsMatrix.LOW_CAT_MIN;
import static core.GoalNeedsMatrix.MED_CAT_LABEL;
import static core.GoalNeedsMatrix.MED_CAT_MAX;
import static core.GoalNeedsMatrix.MED_CAT_MIN;
import static core.GoalNeedsMatrix.ZERO_SCORE_TXT;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Predicate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author ltrask
 */
public class Need implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private SimpleStringProperty description;

    private SimpleStringProperty goal;

    private SimpleIntegerProperty score = new SimpleIntegerProperty(-1);
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public boolean isPlaceholder;
    public boolean hasHL = false;
    public Node hl = new Hyperlink("?");

    public Need(String goal, String description) {
        this(goal, description, false);
    }

    public Need(String goal, String description, boolean isPlaceholder) {
        this.goal = new SimpleStringProperty(goal);
        this.description = new SimpleStringProperty(description);
        this.isPlaceholder = isPlaceholder;
    }

    public Need(String goal, String description, Node hl) {
        this(goal, description, false);
        this.hl = hl;
        hl.getStyleClass().add("wz-input-hyperlink");
        ((Hyperlink) hl).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                String[] tokens = getDescription().split(" ");
                TextInputDialog tid = new TextInputDialog(tokens[6]);
                tid.setTitle("Goal Specification");
                tid.setHeaderText("Specify the delay minutes");
                tid.showAndWait();
                String newMin = tid.getResult();
                String newDesc = "";
                for (int tIdx = 0; tIdx < 6; tIdx++) {
                    newDesc = newDesc + tokens[tIdx] + " ";
                }
                newDesc = newDesc + newMin;
                for (int tIdx = 7; tIdx < tokens.length; tIdx++) {
                    newDesc = newDesc + " " + tokens[tIdx];
                }
                setDescription(newDesc);
            }
        });
        this.hasHL = true;
    }

    public String getGoal() {
        return this.goal.get();
    }

    public void setGoal(String newGoal) {
        this.goal.set(newGoal);
    }

    public SimpleStringProperty goalProperty() {
        return this.goal;
    }

    public String getDescription() {
        return this.description.get();
    }

    public void setDescription(String newDescription) {
        this.description.set(newDescription);
    }

    public SimpleStringProperty descriptionProperty() {
        return this.description;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int newScore) {
        score.set(newScore);
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
        s.writeObject(getDescription());
        s.writeObject(getGoal());
        s.writeBoolean(isPlaceholder);
        s.writeInt(getScore());
        s.writeBoolean(isSelected());
        s.writeBoolean(hasHL);
        s.writeObject(((Hyperlink) hl).getText());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        description = new SimpleStringProperty((String) s.readObject());
        goal = new SimpleStringProperty((String) s.readObject());
        isPlaceholder = s.readBoolean();
        score = new SimpleIntegerProperty(s.readInt());
        selected = new SimpleBooleanProperty(s.readBoolean());
        try {
            hasHL = s.readBoolean();
            hl = new Hyperlink((String) s.readObject());
            hl.getStyleClass().add("wz-input-hyperlink");
            ((Hyperlink) hl).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    String[] tokens = getDescription().split(" ");
                    TextInputDialog tid = new TextInputDialog(tokens[6]);
                    tid.setTitle("Goal Specification");
                    tid.setHeaderText("Specify the delay minutes");
                    tid.showAndWait();
                    String newMin = tid.getResult();
                    String newDesc = "";
                    for (int tIdx = 0; tIdx < 6; tIdx++) {
                        newDesc = newDesc + tokens[tIdx] + " ";
                    }
                    newDesc = newDesc + newMin;
                    for (int tIdx = 7; tIdx < tokens.length; tIdx++) {
                        newDesc = newDesc + " " + tokens[tIdx];
                    }
                    setDescription(newDesc);
                }
            });
        } catch (EOFException e) {
            // Future
        }
    }

    public static int getTypeConverter(Need n) {
        switch (n.getGoal()) {
            case Question.GOAL_SAFETY:
                return 0;
            case Question.GOAL_MOBILITY:
                return 1;
            case Question.GOAL_PROD:
                return 2;
            case Question.GOAL_REG:
                return 3;
            case Question.GOAL_TRAVELER_INFO:
                return 4;
            default:
                return 5;
        }
    }

    public static ObservableList getSortedNeedsList(ObservableList<Need> needsList) {
        FilteredList fl = needsList.filtered(NEED_PRED);
        SortedList sl = fl.sorted(NEED_COMP);
        return sl;
    }

    public static final ObservableList<Need> GOAL_WIZARD_NEEDS_LIST = FXCollections.observableArrayList(
            new Need(Question.GOAL_MOBILITY, "Reduce daily peak period delays to XX minutes", new Hyperlink("(edit)")),
            new Need(Question.GOAL_MOBILITY, "Facilitate the movement of emergency and construction vehicles through the work zone"),
            new Need(Question.GOAL_MOBILITY, "Reduce the number of single-vehicle trips through the work zone"),
            new Need(Question.GOAL_MOBILITY, "Reduce variability of travel times"),
            //new Need(Question.GOAL_MOBILITY, "", true),
            new Need(Question.GOAL_SAFETY, "Reduce rear-end crashes"),
            new Need(Question.GOAL_SAFETY, "Reduce secondary incidents"),
            new Need(Question.GOAL_SAFETY, "Reduce Incident Clearance Times"),
            new Need(Question.GOAL_SAFETY, "Work toward zero work zone fatalities"),
            new Need(Question.GOAL_SAFETY, "Provide a safe environment for roadway users and worker safety"),
            //new Need(Question.GOAL_SAFETY, "", true),
            new Need(Question.GOAL_PROD, "Minimize delays in construction vehicle access to the work zone"),
            new Need(Question.GOAL_PROD, "Provide an egress from work zone for haul vehicles"),
            //new Need(Question.GOAL_PROD, "", true),
            //new Need(Question.GOAL_REG, "Reduce work zone delays to within XX minutes", new Hyperlink("(edit)")),
            new Need(Question.GOAL_REG, "Monitor work zone operations and safety performance in real-time"),
            new Need(Question.GOAL_REG, "Monitor alternative route operations and safety performance in real-time"),
            new Need(Question.GOAL_REG, "Optimize contractor work periods"),
            //new Need(Question.GOAL_REG, "", true),
            new Need(Question.GOAL_TRAVELER_INFO, "Provide roadway users real-time work zone information"),
            new Need(Question.GOAL_TRAVELER_INFO, "Provide roadway users real-time alternate route information")
    //new Need(Question.GOAL_TRAVELER_INFO, "", true)
    );

    private static final Comparator<Need> NEED_COMP = new Comparator<Need>() {
        @Override
        public int compare(Need n1, Need n2) {
            // lower goal type indicates "less than" (should appear above in list)
            int goalType1 = Need.getTypeConverter(n1);
            int goalType2 = Need.getTypeConverter(n2);
            if (goalType1 == goalType2) {
                return n1.getScore() > n2.getScore() ? -1 : n1.getScore() == n2.getScore() ? 0 : 1;
            } else if (goalType1 < goalType2) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    private static final Predicate<Need> NEED_PRED = new Predicate<Need>() {
        @Override
        public boolean test(Need n) {
            return !n.isPlaceholder;
        }
    };

    public static String getScoreString(int score) {
        //return String.valueOf(score);
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

}
