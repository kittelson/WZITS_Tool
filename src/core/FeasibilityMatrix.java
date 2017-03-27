/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.NodeFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ltrask
 */
public class FeasibilityMatrix {

    private final SimpleIntegerProperty feasibility = new SimpleIntegerProperty(0);

    private final ObservableList<QuestionOption> qFeasOptList;
    private final ObservableList<QuestionYN> qFeasYNList;

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

    public BorderPane createSummaryPanel() {
        computeFeasibility();
        BorderPane bPane = new BorderPane();
        GridPane scoreGrid = new GridPane();
        Label scoreTitleLabel = NodeFactory.createFormattedLabel("Feasibility Score:", "feasibility-output-title-bold");
        Label scoreLabel = NodeFactory.createFormattedLabel(String.valueOf(feasibility.get()), "feasibility-output-title");
        scoreGrid.add(scoreTitleLabel, 0, 0);
        scoreGrid.add(scoreLabel, 1, 0);
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(50);
        scoreGrid.getColumnConstraints().addAll(cc1, cc2);

        Label descriptionlabel;
        if (feasibility.get() >= 30) {
            descriptionlabel = NodeFactory.createFormattedLabel(DESC_30PLUS, "feasibility-output-desc");
        } else if (feasibility.get() >= 10) {
            descriptionlabel = NodeFactory.createFormattedLabel(DESC_10_TO_29, "feasibility-output-desc");
        } else {
            descriptionlabel = NodeFactory.createFormattedLabel(DESC_LESS_THAN_10, "feasibility-output-desc");
        }
        bPane.setTop(scoreGrid);
        bPane.setCenter(descriptionlabel);
        return bPane;
    }

    public SimpleIntegerProperty feasibilityProperty() {
        return feasibility;
    }

    public static final String DESC_30PLUS = "30 or more: ITS is likely to provide significant benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_10_TO_29 = "10 to 29: ITS may provide some benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_LESS_THAN_10 = "Less than 10: Its may not provide enough benefit to justify the associate costs.";

}
