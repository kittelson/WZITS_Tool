/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IconHelper;
import GUI.MainController;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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

    private final Button startNewButton = new Button("Press to Begin New \nWork Zone ITS Assessment");

    //private final FillTransition beginFT;
    public IntroPane(MainController mc) {
        this.control = mc;

        startNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                GridPane instrNode = new GridPane();
                ImageView iv = new ImageView(IconHelper.NAV_HELPER);
                instrNode.add(iv, 0, 0);
                al.setTitle("Hint: WZITS Tool Navigation");
                al.setHeaderText("WZITS Tool Navigation");
                al.getDialogPane().setContent(instrNode);
                al.showAndWait();
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

        startNewButton.getStyleClass().add("intro-begin-button");
        //startNewButton.setStyle("-fx-font-size: 36");

        DoubleBinding widthBinding = new DoubleBinding() {
            {
                super.bind(widthProperty());
            }

            @Override
            protected double computeValue() {
                //return Math.max(widthProperty().get() * 0.70, 700);
                return widthProperty().get() - 150;
            }
        };

        DoubleBinding heightBinding = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue() {
                return heightProperty().get() * 0.25;
            }
        };
        ImageView figAllSteps = new ImageView(IconHelper.FIG_FLOW_ALL_STEPS);
        figAllSteps.fitWidthProperty().bind(widthBinding);
        figAllSteps.fitHeightProperty().bind(heightBinding);
        figAllSteps.setPreserveRatio(true);
        figAllSteps.setSmooth(true);
        figAllSteps.setCache(true);

        //beginFT = new FillTransition(Duration.millis(1000), Color.web("#79bde8"), Color.web("#005596"));
        //beginFT.setShape(startNewButton.getShape());
        //beginFT.setCycleCount(Animation.INDEFINITE);
        //beginFT.setAutoReverse(true);
        //beginFT.play();
        subGrid.add(startLabel, 0, 0, 1, 3);
        subGrid.add(infoLabel, 0, 3, 1, 3);
        subGrid.add(figAllSteps, 1, 0, 1, 2);
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
