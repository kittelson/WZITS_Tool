/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.IconHelper;
import GUI.MainController;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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

    MainController control;

    public IntroPane(MainController control) {
        this.control = control;

        GridPane subGrid = new GridPane();
        Label startLabel = new Label("Work Zone");
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("ITS");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label("Create or Open a Project to Begin");
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
        instructionLabel.setAlignment(Pos.CENTER);
        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");
        instructionLabel.getStyleClass().add("intro-instructions");

        subGrid.add(startLabel, 0, 0);
        subGrid.add(infoLabel, 0, 1);
        subGrid.add(instructionLabel, 1, 0);
        subGrid.add(new ImageView(IconHelper.FIG_FLOW_ALL_STEPS), 1, 1);
        subGrid.setStyle("-fx-background-color: white");

        RowConstraints rowConst1 = new RowConstraints();
        rowConst1.setPercentHeight(50);
        RowConstraints rowConst2 = new RowConstraints();
        rowConst2.setPercentHeight(50);
        subGrid.getRowConstraints().addAll(rowConst1, rowConst2);

        ColumnConstraints colConst1 = new ColumnConstraints(150, 150, 150);
        ColumnConstraints colConst2 = new ColumnConstraints(1, 150, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true);
        subGrid.getColumnConstraints().addAll(colConst1, colConst2);
        //GridPane.setHgrow(startLabel, Priority.ALWAYS);
        GridPane.setVgrow(startLabel, Priority.ALWAYS);
        //GridPane.setHgrow(infoLabel, Priority.ALWAYS);
        GridPane.setVgrow(infoLabel, Priority.ALWAYS);
        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        GridPane mainGrid = new GridPane();

        this.setCenter(subGrid);
    }

}
