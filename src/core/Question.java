/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author jlake
 */
public abstract class Question {

    protected final SimpleIntegerProperty idx;
    protected final SimpleStringProperty goal;
    protected final SimpleStringProperty questionText;
    protected final SimpleIntegerProperty responseIdx;

    protected final BooleanProperty visible = new SimpleBooleanProperty(true);
    protected final BooleanProperty locked = new SimpleBooleanProperty();
    private final StringProperty refText = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();

    public Question(int idx, String goal, String questionText) {
        this(idx, goal, questionText, -1);
    }

    public Question(int idx, String goal, String questionTxt, int responseIdx) {
        this.idx = new SimpleIntegerProperty(idx);
        this.goal = new SimpleStringProperty(goal);
        this.questionText = new SimpleStringProperty(questionTxt);
        this.responseIdx = new SimpleIntegerProperty(responseIdx);
        this.visibleProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                //System.out.println("Visibility Changed");
                //if (newVal == false) {
                //    questionText.set(questionText.get() + LOCKED_TEXT);
                //}
            }
        });
    }

    public int getIdx() {
        return idx.get();
    }

    public String getGoal() {
        return goal.get();
    }

    public String getQuestionText() {
        return questionText.get();
    }

    public int getResponseIdx() {
        return responseIdx.get();
    }

    public void setResponseIdx(Integer val) {
        responseIdx.set(val);
        System.out.println("Edited");
    }

    public SimpleIntegerProperty responseIdxProperty() {
        return responseIdx;
    }

    public abstract boolean isTypeYesNo();

    public abstract boolean isYes();

    public int getAnswer() {
        return responseIdx.get();
    }

    public void setAnswer(int val) {
        responseIdx.set(val);
    }

    public abstract void setAnswer(String val);

    public abstract String getAnswerString();

    public boolean isVisible() {
        return visible.get();
    }

    public void setVisible(boolean value) {
        visible.set(value);
    }

    public BooleanProperty visibleProperty() {
        return visible;
    }

    public boolean isLocked() {
        return locked.get();
    }

    public void setLocked(boolean value) {
        locked.set(value);
    }

    public BooleanProperty lockedProperty() {
        return locked;
    }

    public String getLockedQuestionText() {
        return questionText.get() + " (Locked due to " + refText.get() + ")";
    }

    public String getRefText() {
        return refText.get();
    }

    public void setRefText(String value) {
        refText.set(value);
    }

    public StringProperty refTextProperty() {
        return refText;
    }

    private final BooleanProperty redundant = new SimpleBooleanProperty(false);

    public boolean isRedundant() {
        return redundant.get();
    }

    public void setRedundant(boolean value) {
        redundant.set(value);
    }

    public BooleanProperty redundantProperty() {
        return redundant;
    }
    private final BooleanProperty dependant = new SimpleBooleanProperty(false);

    public boolean isDependant() {
        return dependant.get();
    }

    public void setDependant(boolean value) {
        dependant.set(value);
    }

    public BooleanProperty dependantProperty() {
        return dependant;
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String value) {
        comment.set(value);
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public static IntegerStringConverter yesNoConverter = new IntegerStringConverter() {
        @Override
        public Integer fromString(String sVal) {
            if (sVal.equalsIgnoreCase("Yes")) {
                return 1;
            } else if (sVal.equalsIgnoreCase("No")) {
                return 0;
            } else {
                return -1;
            }

        }

        @Override
        public String toString(Integer iVal) {
            switch (iVal) {
                default:
                    return "Select";
                case 0:
                    return "No";
                case 1:
                    return "Yes";
            }
        }
    };

    public static final String GOAL_USER_NEEDS = "User Needs";

    public static final String GOAL_FEASIBILITY = "Feasibility";

    public static final String GOAL_STAKEHOLDER = "Stakeholder";

    public static final String GOAL_MOBILITY = "Mobility";
    public static final String GOAL_SAFETY = "Safety";
    public static final String GOAL_PROD = "Productivity";
    public static final String GOAL_REG = "Regulatory";
    public static final String GOAL_TRAVELER_INFO = "Traveler Info";

    public static final String GOAL_DOCUMENTATION = "Documentation";

    public static final String LOCKED_TEXT = " (Locked)";

}
