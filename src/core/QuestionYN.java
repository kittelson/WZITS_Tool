/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author jlake
 */
public class QuestionYN extends Question {

    
    public SimpleBooleanProperty answerIsYes;
    
    
    public QuestionYN(int idx, String goal, String questionText) {
        this(idx, goal, questionText, false);
    }
    
    public QuestionYN(int idx, String goal, String questionText, boolean isYes) {
        super(idx, goal, questionText);
        answerIsYes = new SimpleBooleanProperty(isYes);
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
}
