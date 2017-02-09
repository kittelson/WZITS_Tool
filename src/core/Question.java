/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    
    public Question(int idx, String goal, String questionText) {
        this(idx, goal, questionText, -1);
    }
    
    public Question(int idx, String goal, String questionText, int responseIdx) {
        this.idx = new SimpleIntegerProperty(idx);
        this.goal = new SimpleStringProperty(goal);
        this.questionText = new SimpleStringProperty(questionText);
        this.responseIdx = new SimpleIntegerProperty(responseIdx);
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
}
