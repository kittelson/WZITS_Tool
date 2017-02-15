/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class Need {

    private final SimpleStringProperty description;

    private final SimpleStringProperty goal;

    private final SimpleIntegerProperty score = new SimpleIntegerProperty(-1);

    public Need(String goal, String description) {
        this.goal = new SimpleStringProperty(goal);
        this.description = new SimpleStringProperty(description);
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

    public static final ObservableList<Need> GOAL_WIZARD_NEEDS_LIST = FXCollections.observableArrayList(
            new Need(Question.GOAL_MOBILITY, "Reduce daily peek period delays to XX minutes"),
            new Need(Question.GOAL_MOBILITY, "Facilitate the movement of emergency and construction vehicles through the work zone"),
            new Need(Question.GOAL_MOBILITY, "Reduce the number of single-vehicle trips through the work zone"),
            new Need(Question.GOAL_MOBILITY, "Reduce variability of travel times"),
            new Need(Question.GOAL_MOBILITY, ""),
            new Need(Question.GOAL_SAFETY, "Reduce rear-end crashes"),
            new Need(Question.GOAL_SAFETY, "Reduce secondary incidents"),
            new Need(Question.GOAL_SAFETY, "Reduce Incident Clearance Times"),
            new Need(Question.GOAL_SAFETY, "Work toward zero work zone fatalities"),
            new Need(Question.GOAL_SAFETY, "Provide a safe environment for roadway users and worker safety"),
            new Need(Question.GOAL_SAFETY, ""),
            new Need(Question.GOAL_PROD, "Minimize delays in construction vehicle access to the work zone"),
            new Need(Question.GOAL_PROD, "Provide an egress from work zone for haul vehicles"),
            new Need(Question.GOAL_PROD, ""),
            new Need(Question.GOAL_REG, "Reduce work zone delays to within XX minutes"),
            new Need(Question.GOAL_REG, "Monitor work zone operations and safety performance in real-time"),
            new Need(Question.GOAL_REG, "Monitor alternative route operations and safety performance in real-time"),
            new Need(Question.GOAL_REG, "Optimize contractor work periods"),
            new Need(Question.GOAL_REG, ""),
            new Need(Question.GOAL_TRAVELER_INFO, "Provide roadway users real-time work zone information"),
            new Need(Question.GOAL_TRAVELER_INFO, "Provide roadway users real-time alternate route information"),
            new Need(Question.GOAL_TRAVELER_INFO, "")
    );

}
