/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author jlake
 */
public class QuestionOption extends Question {

    private final String[] options;

    private final int[] scores;

    private final SimpleStringProperty answerString = new SimpleStringProperty();

    private final SimpleIntegerProperty score = new SimpleIntegerProperty();

    public QuestionOption(int idx, String category, String text, String[] options) {
        this(idx, category, text, options, new int[options.length]);
    }

    public QuestionOption(int idx, String category, String text, String[] opts, int[] scoresList) {
        super(idx, category, text);
        this.options = opts;
        this.scores = scoresList;
        responseIdx.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (options != null && newVal.intValue() >= 0 && newVal.intValue() < options.length) {
                    answerString.set(options[newVal.intValue()]);
                    score.set(scores[newVal.intValue()]);
                } else {
                    score.set(0);
                    answerString.set("No Answer");
                }
            }
        });
    }

    @Override
    public String getAnswerString() {
        return options[this.responseIdx.get()];
    }

    @Override
    public void setAnswer(String ans) {
        for (int opIdx = 0; opIdx < options.length; opIdx++) {
            if (options[opIdx].equalsIgnoreCase(ans)) {
                this.responseIdx.set(opIdx);
                return;
            }
        }
        // If the answer is not found, set to -1 (unanswered)
        this.responseIdx.set(-1);
    }

    @Override
    public boolean isYes() {
        return false;
    }

    @Override
    public boolean isTypeYesNo() {
        return false;
    }

    public String getOption(int optIdx) {
        if (optIdx < 0 || optIdx >= options.length) {
            return "Select Answer";
        } else {
            return options[optIdx];
        }
    }

    public int getOptionScore() {
        if (responseIdx.get() >= 0 && scores != null) {
            return scores[responseIdx.get()];
        }
        return 0;
    }

    public String[] getOptions() {
        return options;
    }

    public SimpleStringProperty answerStringProperty() {
        return answerString;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
