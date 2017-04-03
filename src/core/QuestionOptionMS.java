/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author ltrask
 */
public class QuestionOptionMS extends QuestionOption implements Serializable {

    private final long serialVersionUID = 123456789L;

    private SimpleBooleanProperty[] optionIsIncluded;

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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(optionIsIncluded.length);
        for (int i = 0; i < optionIsIncluded.length; i++) {
            s.writeBoolean(optionIsIncluded[i].get());
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int numOpts = s.readInt();
        optionIsIncluded = new SimpleBooleanProperty[numOpts];
        for (int i = 0; i < optionIsIncluded.length; i++) {
            optionIsIncluded[i] = new SimpleBooleanProperty(s.readBoolean());
        }
    }

}
