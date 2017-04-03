/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.NodeFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author ltrask
 */
public class FeasibilityMatrix implements Serializable {

    private final long serialVersionUID = 123456789L;

    private SimpleIntegerProperty feasibility = new SimpleIntegerProperty(0);

    private ObservableList<QuestionOption> qFeasOptList;
    private ObservableList<QuestionYN> qFeasYNList;

    public FeasibilityMatrix(ObservableList<QuestionOption> qFeasOptList, ObservableList<QuestionYN> qFeasYNList) {
        this.qFeasOptList = qFeasOptList;
        this.qFeasYNList = qFeasYNList;
    }

    public void computeFeasibility() {
        int feasScore = 0;
        for (QuestionOption qOpt : qFeasOptList) {
            feasScore += qOpt.getOptionScore();
        }
        for (QuestionYN qYN : qFeasYNList) {
            feasScore += qYN.getScore();
        }
        feasibility.set(feasScore);
    }

    public GridPane createSummaryPanel() {
        computeFeasibility();
        GridPane gPane = new GridPane();
        GridPane scoreGrid = new GridPane();
        Label scoreTitleLabel = NodeFactory.createFormattedLabel("Feasibility Score:", "feasibility-output-title-bold");
        Label scoreLabel = NodeFactory.createFormattedLabel(String.valueOf(feasibility.get()), "feasibility-output-title");
        scoreLabel.textProperty().bind(feasibility.asString());
        scoreGrid.add(scoreTitleLabel, 0, 0);
        scoreGrid.add(scoreLabel, 1, 0);
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(50);
        scoreGrid.getColumnConstraints().addAll(cc1, cc2);

        final Label descriptionlabel = NodeFactory.createFormattedLabel(DESC_30PLUS, "feasibility-output-desc");
        feasibility.addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (newVal.intValue() >= 30) {
                    descriptionlabel.setText(DESC_30PLUS);
                } else if (newVal.intValue() >= 10) {
                    descriptionlabel.setText(DESC_10_TO_29);
                } else {
                    descriptionlabel.setText(DESC_LESS_THAN_10);
                }
            }
        });
//        if (feasibility.get() >= 30) {
//            descriptionlabel = NodeFactory.createFormattedLabel(DESC_30PLUS, "feasibility-output-desc");
//        } else if (feasibility.get() >= 10) {
//            descriptionlabel = NodeFactory.createFormattedLabel(DESC_10_TO_29, "feasibility-output-desc");
//        } else {
//            descriptionlabel = NodeFactory.createFormattedLabel(DESC_LESS_THAN_10, "feasibility-output-desc");
//        }
        gPane.add(scoreGrid, 0, 0);
        gPane.add(descriptionlabel, 0, 1);
        RowConstraints rc1 = new RowConstraints();
        rc1.setPercentHeight(50);
        RowConstraints rc2 = new RowConstraints();
        rc2.setPercentHeight(50);
        gPane.getRowConstraints().addAll(rc1, rc2);
        GridPane.setVgrow(scoreTitleLabel, Priority.ALWAYS);
        GridPane.setVgrow(scoreLabel, Priority.ALWAYS);
        GridPane.setHgrow(scoreTitleLabel, Priority.ALWAYS);
        GridPane.setHgrow(scoreLabel, Priority.ALWAYS);
        GridPane.setHgrow(descriptionlabel, Priority.ALWAYS);
        GridPane.setHgrow(gPane, Priority.ALWAYS);
        return gPane;
    }

    public SimpleIntegerProperty feasibilityProperty() {
        return feasibility;
    }

    public int getFeasibility() {
        return feasibility.get();
    }

//    private void writeObject(ObjectOutputStream s) throws IOException {
//        s.writeInt(getFeasibility());
//        s.writeInt(qFeasYNList.size());
//        for (int i = 0; i < qFeasYNList.size(); i++) {
//            s.writeObject(qFeasYNList.get(i));
//        }
//        s.writeInt(qFeasOptList.size());
//        for (int i = 0; i < qFeasOptList.size(); i++) {
//            s.writeObject(qFeasOptList.get(i));
//        }
//    }
//
//    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
//        feasibility = new SimpleIntegerProperty(s.readInt());
//        int numYN = s.readInt();
//        qFeasYNList = FXCollections.observableArrayList();
//        for (int i = 0; i < numYN; i++) {
//            qFeasYNList.add((QuestionYN) s.readObject());
//        }
//        int numOpt = s.readInt();
//        qFeasOptList = FXCollections.observableArrayList();
//        for (int i = 0; i < numOpt; i++) {
//            qFeasOptList.add((QuestionOption) s.readObject());
//        }
//    }
    public static final String DESC_30PLUS = "30 or more: ITS is likely to provide significant benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_10_TO_29 = "10 to 29: ITS may provide some benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_LESS_THAN_10 = "Less than 10: ITS may not provide enough benefit to justify the associated costs.";

}
