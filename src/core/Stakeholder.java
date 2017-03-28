/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ltrask
 */
public class Stakeholder {

    private final IntegerProperty idx = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty score = new SimpleIntegerProperty();

    public Stakeholder(int idx, String name, int score) {
        this.idx.set(idx);
        this.name.set(name);
        this.score.set(score);
    }

    public int getIdx() {
        return idx.get();
    }

    public void setIdx(int value) {
        idx.set(value);
    }

    public IntegerProperty idxProperty() {
        return idx;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
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
