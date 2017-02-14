/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.MainController;
import GUI.Tables.Step2Table;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author ltrask
 */
public class Step4Panel extends BorderPane {

    private final MainController control;

    private final VBox mainVBox = new VBox();

    private final Pagination pagination;

    private final ProgressBar pb;

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane stepSummaryPane = new BorderPane();

    public Step4Panel(MainController control) {

        this.control = control;

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setWrapText(true);
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setTextAlignment(TextAlignment.CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("4");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label("Procurement");
        instructionLabel.setWrapText(true);
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
        instructionLabel.setAlignment(Pos.CENTER);
        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");
        instructionLabel.getStyleClass().add("intro-instructions");

        stepIntroGrid.add(startLabel, 0, 0);
        stepIntroGrid.add(infoLabel, 0, 1);
        stepIntroGrid.add(instructionLabel, 1, 0, 1, 2);
        stepIntroGrid.setStyle("-fx-background-color: white");

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        row1.setVgrow(Priority.ALWAYS);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        row2.setVgrow(Priority.ALWAYS);
        stepIntroGrid.getRowConstraints().addAll(row1, row2);
        ColumnConstraints colConst1 = new ColumnConstraints(150, 150, 150);
        ColumnConstraints colConst2 = new ColumnConstraints(1, 150, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true);
        stepIntroGrid.getColumnConstraints().addAll(colConst1, colConst2);
        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        pb = new ProgressBar(0);
        pb.setMaxWidth(MainController.MAX_WIDTH);

        pagination = new Pagination(Step2Table.getPageCount(3) + 2);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if (pageIndex == 0) {
                    return stepIntroGrid;
                } else if (pageIndex == pagination.getPageCount() - 1) {
                    return Step2Table.createSummaryTable();
                } else {
                    return Step2Table.createPageTable(pageIndex - 1, 10);
                }
                //return Step1Table.createPageTable(pageIndex, 10);
            }
        });
        //pagination.getStylesheets().add(this.getClass().getResource("/GUI/Step/step1Pane.css").toExternalForm());
        pagination.getStyleClass().add("step-subpagination");

        mainVBox.getChildren().addAll(pagination, pb);

        pagination.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(pagination, Priority.ALWAYS);

        this.setCenter(mainVBox);

    }

}
