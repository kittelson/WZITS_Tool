/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author jlake
 */
public class QuestionYN extends Question {

    public final SimpleBooleanProperty answerIsYes = new SimpleBooleanProperty(false);

    public final SimpleBooleanProperty answerIsNo = new SimpleBooleanProperty(false);

    public QuestionYN(int idx, String goal, String questionText) {
        this(idx, goal, questionText, false);
    }

    public QuestionYN(int idx, String goal, String questionText, boolean isYes) {
        super(idx, goal, questionText);
        //answerIsYes.bind(answerIsNo.not());
        answerIsYes.set(isYes);
        answerIsYes.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                //System.out.println("AnswerIsYes change from " + oldVal.toString() + " to " + newVal.toString());
                if (newVal) {
                    responseIdx.set(1);
                    answerIsNo.set(false);
                } else {
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
}
