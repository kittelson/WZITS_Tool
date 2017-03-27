/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author ltrask
 */
public class StakeholderMatrix {

    private final ObservableList qOptList;
    private final ObservableList qYNList;

    public StakeholderMatrix(ObservableList<QuestionOption> qOptList, ObservableList<QuestionYN> qYNList) {
        this.qOptList = qOptList;
        this.qYNList = qYNList;
    }

    public TableView createSummaryTable() {
        return null;
    }

}
