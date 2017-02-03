/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author jlake
 */
public class MainWindow extends BorderPane {

    public MainWindow() {

        stepFlow = new ToolBar(step1Button,
                step2Button,
                step3Button,
                step4Button,
                step5Button,
                step6Button
        );
        step1Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStartStep.css").toExternalForm());
        step2Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStep.css").toExternalForm());
        step3Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStep.css").toExternalForm());
        step4Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStep.css").toExternalForm());
        step5Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStep.css").toExternalForm());
        step6Button.getStylesheets().add(this.getClass().getResource("/GUI/CSS/flowStep.css").toExternalForm());

        pageFlow = new ToolBar[numSteps];
        for (int i = 0; i < numSteps; i++) {
            pageFlow[i] = new ToolBar();
        }

        // Setting up sliding panes
        step1Pane.setCenter(new Label("Step 1"));
        step2Pane.setCenter(new Label("Step 2"));
        step3Pane.setCenter(new Label("Step 3"));
        step4Pane.setCenter(new Label("Step 4"));
        step5Pane.setCenter(new Label("Step 5"));
        step6Pane.setCenter(new Label("Step 6"));
        allStepsPane.add(step1Pane, 0, 0);
        allStepsPane.add(step2Pane, 1, 0);
        allStepsPane.add(step3Pane, 2, 0);
        allStepsPane.add(step4Pane, 3, 0);
        allStepsPane.add(step5Pane, 4, 0);
        allStepsPane.add(step6Pane, 5, 0);
        ColumnConstraints stepCol1 = new ColumnConstraints();
        stepCol1.setPercentWidth(16.6);
        ColumnConstraints stepCol2 = new ColumnConstraints();
        stepCol2.setPercentWidth(16.6);
        ColumnConstraints stepCol3 = new ColumnConstraints();
        stepCol3.setPercentWidth(16.6);
        ColumnConstraints stepCol4 = new ColumnConstraints();
        stepCol4.setPercentWidth(16.6);
        ColumnConstraints stepCol5 = new ColumnConstraints();
        stepCol5.setPercentWidth(16.6);
        ColumnConstraints stepCol6 = new ColumnConstraints();
        stepCol6.setPercentWidth(16.6);
        allStepsPane.getColumnConstraints().addAll(stepCol1, stepCol2, stepCol3, stepCol4, stepCol5, stepCol6);

        // Creating Panel
        this.setTop(stepFlow);
        this.setBottom(allStepsPane);

        setupActionListeners();
        setupPropertyBindings();
    }

    private void setupActionListeners() {
        step1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(0);
                selectStep(activeStep.get());
            }
        });
        step2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(1);
                selectStep(activeStep.get());
            }
        });
        step3Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(2);
                selectStep(activeStep.get());
            }
        });
        step4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(3);
                selectStep(activeStep.get());
            }
        });
        step5Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(4);
                selectStep(activeStep.get());
            }
        });
        step6Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(5);
                selectStep(activeStep.get());
            }
        });
    }

    private void setupPropertyBindings() {
        this.activeStep.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step1Button.setStyle("-fx-background-color: " + (activeStep.get() == 0 ? COLOR_STEP_HL : COLOR_STEP));
                step2Button.setStyle("-fx-background-color: " + (activeStep.get() == 1 ? COLOR_STEP_HL : COLOR_STEP));
                step3Button.setStyle("-fx-background-color: " + (activeStep.get() == 2 ? COLOR_STEP_HL : COLOR_STEP));
                step4Button.setStyle("-fx-background-color: " + (activeStep.get() == 3 ? COLOR_STEP_HL : COLOR_STEP));
                step5Button.setStyle("-fx-background-color: " + (activeStep.get() == 4 ? COLOR_STEP_HL : COLOR_STEP));
                step6Button.setStyle("-fx-background-color: " + (activeStep.get() == 5 ? COLOR_STEP_HL : COLOR_STEP));
            }
        });
    }

    private void selectStep(int stepIndex) {
        moveScreen(stepIndex * step1Pane.getWidth(), 0);
    }

    private void moveScreen(double toX, double toY) {
        TranslateTransition moveMe = new TranslateTransition(Duration.seconds(0.5), allStepsPane);
        moveMe.setToX(-1 * toX);
        moveMe.setToY(toY);
        moveMe.play();
    }

    /**
     * Number of steps of the WZITS Tool
     */
    private final int numSteps = 6;
    /**
     * Flow of steps for the User.
     */
    private final ToolBar stepFlow;
    /**
     * ToolBar button for Step 1.
     */
    private final Button step1Button = new Button("Step 1");
    /**
     * ToolBar button for Step 2.
     */
    private final Button step2Button = new Button("Step 2");
    /**
     * ToolBar button for Step 3.
     */
    private final Button step3Button = new Button("Step 3");
    /**
     * ToolBar button for Step 4.
     */
    private final Button step4Button = new Button("Step 4");
    /**
     * ToolBar button for Step 5.
     */
    private final Button step5Button = new Button("Step 5");
    /**
     * ToolBar button for Step 6.
     */
    private final Button step6Button = new Button("Step 6");
    /**
     * Flow of pages within the active step for the User.
     */
    private final ToolBar[] pageFlow;

    /**
     *
     */
    private final GridPane allStepsPane = new GridPane();
    private final BorderPane step1Pane = new BorderPane();
    private final BorderPane step2Pane = new BorderPane();
    private final BorderPane step3Pane = new BorderPane();
    private final BorderPane step4Pane = new BorderPane();
    private final BorderPane step5Pane = new BorderPane();
    private final BorderPane step6Pane = new BorderPane();

    /**
     * Index of the step currently active.
     */
    private SimpleIntegerProperty activeStep = new SimpleIntegerProperty(-1);

    /**
     * Index of the max step filled out by user.
     */
    private int maxStep = 0;

    public static final String COLOR_STEP_HL = "#833C0C";
    public static final String COLOR_STEP = "#ED7D31";
    private static final int MAX_WIDTH = 999999;

}
