/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author ltrask
 */
public class UserGoal extends QuestionYN {

    private SimpleBooleanProperty active = new SimpleBooleanProperty(true);

    public UserGoal(int idx, String qText) {
        this(idx, qText, -1);
    }

    public UserGoal(int idx, String qText, int resVal) {
        super(idx, Question.GOAL_USER_NEEDS, qText);
    }

    public boolean getActive() {
        return active.get();
    }

    public void setActive(boolean val) {
        active.set(val);
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }
}
