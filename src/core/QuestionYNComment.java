/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ltrask
 */
public class QuestionYNComment extends QuestionYN {

    private final StringProperty openResponse = new SimpleStringProperty();

    public QuestionYNComment(int idx, String goal, String questionText) {
        super(idx, goal, questionText);
    }

    public String getOpenResponse() {
        return openResponse.get();
    }

    public void setOpenResponse(String value) {
        openResponse.set(value);
    }

    public StringProperty openResponseProperty() {
        return openResponse;
    }

}
