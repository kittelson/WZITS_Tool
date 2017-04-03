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
public class QuestionOptionMS extends QuestionOption {

    private final SimpleBooleanProperty[] optionIsIncluded;

    public QuestionOptionMS(int idx, String category, String text, String[] options) {
        super(idx, category, text, options, new int[options.length]);
        optionIsIncluded = new SimpleBooleanProperty[options.length];
        for (int i = 0; i < optionIsIncluded.length; i++) {
            optionIsIncluded[i] = new SimpleBooleanProperty(false);
        }
    }

    public QuestionOptionMS(int idx, String category, String text, String[] opts, int[] scoresList) {
        super(idx, category, text, opts, scoresList);
        optionIsIncluded = new SimpleBooleanProperty[opts.length];
        for (int i = 0; i < optionIsIncluded.length; i++) {
            optionIsIncluded[i] = new SimpleBooleanProperty(false);
        }
    }

    public SimpleBooleanProperty getOptionIncludedProperty(int optIdx) {
        return optionIsIncluded[optIdx];
    }

    public boolean getOptionIncluded(int optIdx) {
        return optionIsIncluded[optIdx].get();
    }

    public void getOptionIncluded(int optIdx, boolean included) {
        optionIsIncluded[optIdx].set(included);
    }

}
