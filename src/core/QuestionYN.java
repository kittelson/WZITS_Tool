/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author jlake
 */
public class QuestionYN extends Question {

    public final SimpleBooleanProperty answerIsYes = new SimpleBooleanProperty(false);

    public final SimpleBooleanProperty answerIsNo = new SimpleBooleanProperty(false);

    protected final IntegerProperty score = new SimpleIntegerProperty(0);

    private final int weight;

    public QuestionYN(int idx, String goal, String questionText) {
        this(idx, goal, questionText, false, 1);
    }

    public QuestionYN(int idx, String goal, String questionText, boolean isYes, int weightVal) {
        super(idx, goal, questionText);
        this.weight = weightVal;

        answerIsYes.set(isYes);
        answerIsYes.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                //System.out.println("AnswerIsYes change from " + oldVal.toString() + " to " + newVal.toString());
                if (newVal) {
                    responseIdx.set(1);
                    answerIsNo.set(false);
                } else {
                    score.set(0);
                    if (!answerIsNo.get()) {
                        responseIdx.set(-1);
                    }
                }
            }
        });
        answerIsNo.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                //System.out.println("AnswerIsNo change from " + oldVal.toString() + " to " + newVal.toString());
                if (newVal) {
                    responseIdx.set(0);
                    answerIsYes.set(false);
                } else {
                    if (!answerIsYes.get()) {
                        responseIdx.set(-1);
                    }
                }
            }
        });

        responseIdx.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (newVal.intValue() == 1 && !answerIsYes.get()) {
                    answerIsYes.set(true);
                } else if (newVal.intValue() == 0 && !answerIsNo.get()) {
                    answerIsNo.set(true);
                } else if (newVal.intValue() == -1) {
                    if (answerIsYes.get()) {
                        answerIsYes.set(false);
                    } else if (answerIsNo.get()) {
                        answerIsNo.set(false);
                    }
                }
                score.set(newVal.intValue() == 1 ? weight : 0);
            }
        });
    }

    @Override
    public boolean isTypeYesNo() {
        return true;
    }

    @Override
    public boolean isYes() {
        return answerIsYes.get();
    }

    @Override
    public String getAnswerString() {
        return answerIsYes.get() ? "Yes" : "No";
    }

    @Override
    public void setAnswer(String value) {
        responseIdx.set(Question.yesNoConverter.fromString(value));
    }

    public boolean getAnswerIsYes() {
        return answerIsYes.get();
    }

    public void setAnswerIsYes(Boolean newVal) {
        answerIsYes.set(newVal);
    }

    public SimpleBooleanProperty answerIsYesProperty() {
        return answerIsYes;
    }

    public boolean getAnswerIsNo() {
        return answerIsNo.get();
    }

    public void setAnswerIsNo(Boolean newVal) {
        answerIsNo.set(newVal);
    }

    public SimpleBooleanProperty answerIsNoProperty() {
        return answerIsNo;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int value) {
        score.set(value);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

}
