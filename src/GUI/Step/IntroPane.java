/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.IconHelper;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author ltrask
 */
public class IntroPane extends BorderPane {

    private final MainController control;

    private final Button startNewButton = new Button("Begin Work Zone ITS Assessment");

    public IntroPane(MainController mc) {
        this.control = mc;

        startNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0);
            }
        });

        GridPane subGrid = new GridPane();
        Label startLabel = new Label("Work Zone");
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("ITS");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label("Begin a New Project\nor\nOpen an Existing Project from the File Menu");
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
        instructionLabel.setAlignment(Pos.CENTER);
        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");
        instructionLabel.getStyleClass().add("intro-instructions");

        startNewButton.setStyle("-fx-font-size: 36");

        subGrid.add(startLabel, 0, 0, 1, 3);
        subGrid.add(infoLabel, 0, 3, 1, 3);
        subGrid.add(new ImageView(IconHelper.FIG_FLOW_ALL_STEPS), 1, 0, 1, 2);
        subGrid.add(instructionLabel, 1, 2, 1, 2);
        subGrid.add(startNewButton, 1, 4, 1, 2);
        subGrid.setStyle("-fx-background-color: white");

        double oneSixth = 100.0 / 6.0;
        RowConstraints rowConst1 = new RowConstraints();
        rowConst1.setPercentHeight(oneSixth);
        RowConstraints rowConst2 = new RowConstraints();
        rowConst2.setPercentHeight(oneSixth);
        RowConstraints rowConst3 = new RowConstraints();
        rowConst3.setPercentHeight(oneSixth);
        RowConstraints rowConst4 = new RowConstraints();
        rowConst4.setPercentHeight(oneSixth);
        RowConstraints rowConst5 = new RowConstraints();
        rowConst5.setPercentHeight(oneSixth);
        RowConstraints rowConst6 = new RowConstraints();
        rowConst6.setPercentHeight(oneSixth);
        subGrid.getRowConstraints().addAll(rowConst1, rowConst2, rowConst3, rowConst4, rowConst5, rowConst6);

        ColumnConstraints colConst1 = new ColumnConstraints(150, 150, 150);
        ColumnConstraints colConst2 = new ColumnConstraints(1, 150, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true);
        subGrid.getColumnConstraints().addAll(colConst1, colConst2);
        //GridPane.setHgrow(startLabel, Priority.ALWAYS);
        GridPane.setVgrow(startLabel, Priority.ALWAYS);
        //GridPane.setHgrow(infoLabel, Priority.ALWAYS);
        GridPane.setVgrow(infoLabel, Priority.ALWAYS);
        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        this.setCenter(subGrid);
    }

}
