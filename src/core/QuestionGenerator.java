/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author ltrask
 */
public class QuestionGenerator {

    /**
     *
     */
    public final ArrayList<QuestionYN> qGoalsList;

    /**
     *
     */
    public final ArrayList<Question> list2;

    /**
     *
     */
    public final ArrayList<Question> list3;

    /**
     *
     */
    public final ArrayList<Question> list4;

    /**
     *
     */
    public final ArrayList<Question> list5;

    /**
     *
     */
    public final ArrayList<Question> list6;

    /**
     *
     */
    public final ArrayList<Question> list7;

    /**
     *
     */
    public final ArrayList<Question> list8;

    /**
     *
     */
    public final ArrayList<Question> list9;

    /**
     *
     */
    public final ArrayList<Question> list10;

    /**
     * WZITS Project associated with the Question Generator
     */
    private final Project proj;

    /**
     *
     * @param project
     */
    public QuestionGenerator(Project project) {
        this.proj = project;

        qGoalsList = new ArrayList();
        list2 = new ArrayList();
        list3 = new ArrayList();
        list4 = new ArrayList();
        list5 = new ArrayList();
        list6 = new ArrayList();
        list7 = new ArrayList();
        list8 = new ArrayList();
        list9 = new ArrayList();
        list10 = new ArrayList();

        ChangeListener questionAnsweredListener = new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                proj.getGoalNeedsMatrix().computeScores();
            }
        };
    }

}
