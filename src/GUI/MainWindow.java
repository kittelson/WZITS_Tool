/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Step.Step1Panel;
import GUI.Launch.LaunchPane;
import GUI.Step.IntroPane;
import GUI.Step.Step2Panel;
import GUI.Step.Step3Panel;
import GUI.Step.Step4Panel;
import GUI.Step.Step5Panel;
import GUI.Step.Step6Panel;
import core.Project;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author jlake
 */
public class MainWindow extends BorderPane {

    private final MainController control;

    public MainWindow(MainController control) {

        this.control = control;
        control.setMainWindow(this);

        // Creating MenuBar
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuWindow = new Menu("Window");
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuWindow, menuHelp);
        // Creating File Menu
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save as");
        MenuItem exitMenuItem = new MenuItem("Exit");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        menuFile.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem);
        // Creating Edit Menu
        // Creating Window Menu
        // Creating HelpMenu
        MenuItem helpMenuItem = new MenuItem("Help");
        menuHelp.getItems().add(helpMenuItem);

        titleLabel.getStyleClass().add("launch-title-label-top");
        titleLabel.setMaxWidth(MainController.MAX_WIDTH);

        stepFlow = new ToolBar(
                introButton,
                step1Button,
                step2Button,
                step3Button,
                step4Button,
                step5Button,
                step6Button,
                summaryButton
        );
        //stepFlow.setStyle("-fx-background-color: blue");

        introButton.getStyleClass().add("flow-step-start");
        introButton.setStyle("-fx-background-color: " + COLOR_STEP_HL);
        step1Button.getStyleClass().add("flow-step");
        step2Button.getStyleClass().add("flow-step");
        step3Button.getStyleClass().add("flow-step");
        step4Button.getStyleClass().add("flow-step");
        step5Button.getStyleClass().add("flow-step");
        step6Button.getStyleClass().add("flow-step");
        summaryButton.getStyleClass().add("flow-step-end");

        //toolBarBox.setFillWidth(true);
        toolBarBox.setAlignment(Pos.CENTER);
        toolBarBox.getChildren().addAll(menuBar, titleLabel, stepFlow);

        // Setting up TreeView Navigator
        navigator = new TreeView();

        // Setting up sliding panes
        //step1Pane.setCenter(new Label("Step 1"));
        introPane = new IntroPane(control);
        step1Pane = new Step1Panel(control);
        step2Pane = new Step2Panel(control);
        step3Pane = new Step3Panel(control);
        step4Pane = new Step4Panel(control);
        step5Pane = new Step5Panel(control);
        step6Pane = new Step6Panel(control);
        summaryPane.setCenter(new Label("Summary"));
        allStepsPane.add(introPane, 0, 0);
        allStepsPane.add(step1Pane, 1, 0);
        allStepsPane.add(step2Pane, 2, 0);
        allStepsPane.add(step3Pane, 3, 0);
        allStepsPane.add(step4Pane, 4, 0);
        allStepsPane.add(step5Pane, 5, 0);
        allStepsPane.add(step6Pane, 6, 0);
        allStepsPane.add(summaryPane, 7, 0);
        //double colPctWidth = 12.5;
        //ColumnConstraints stepCol1 = new ColumnConstraints();
        //stepCol1.setPercentWidth(colPctWidth);
        //ColumnConstraints stepCol2 = new ColumnConstraints();
        //stepCol2.setPercentWidth(colPctWidth);
        //ColumnConstraints stepCol3 = new ColumnConstraints();
        //stepCol3.setPercentWidth(colPctWidth);
        //ColumnConstraints stepCol4 = new ColumnConstraints();
        //stepCol4.setPercentWidth(colPctWidth);
        //ColumnConstraints stepCol5 = new ColumnConstraints();
        //stepCol5.setPercentWidth(colPctWidth);
        //ColumnConstraints stepCol6 = new ColumnConstraints();
        //stepCol6.setPercentWidth(colPctWidth);
        //allStepsPane.getColumnConstraints().addAll(stepCol1, stepCol2, stepCol3, stepCol4, stepCol5, stepCol6);
        int numPanes = 8;
        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
            ColumnConstraints tcc = new ColumnConstraints();
            tcc.setPercentWidth(100.0 / 8);
            allStepsPane.getColumnConstraints().add(tcc);
        }

        GridPane.setVgrow(introPane, Priority.ALWAYS);
        GridPane.setVgrow(step1Pane, Priority.ALWAYS);
        GridPane.setVgrow(step2Pane, Priority.ALWAYS);
        GridPane.setVgrow(step3Pane, Priority.ALWAYS);
        GridPane.setVgrow(step4Pane, Priority.ALWAYS);
        GridPane.setVgrow(step5Pane, Priority.ALWAYS);
        GridPane.setVgrow(step6Pane, Priority.ALWAYS);
        GridPane.setVgrow(summaryPane, Priority.ALWAYS);

        // Creating Panel
        //this.setTop(stepFlow);
        //this.setBottom(allStepsPane);
        launch = new LaunchPane(control);
        this.setCenter(launch);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
                if (allStepsPane != null && allStepsPane.isVisible()) {
                    allStepsPane.setMinWidth(8 * control.getAppWidth());
                    moveScreen((activeStep.get() + 1) * step1Pane.getWidth(), 0, false);
                }
            }
        });

        introButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(-1);
                selectStep(activeStep.get());
            }
        });

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
        summaryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                activeStep.set(6);
                selectStep(activeStep.get());
            }
        });
    }

    private void setupPropertyBindings() {
        this.activeStep.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                introButton.setStyle("-fx-background-color: " + (activeStep.get() == -1 ? COLOR_STEP_HL : COLOR_STEP));
                step1Button.setStyle("-fx-background-color: " + (activeStep.get() == 0 ? COLOR_STEP_HL : COLOR_STEP));
                step2Button.setStyle("-fx-background-color: " + (activeStep.get() == 1 ? COLOR_STEP_HL : COLOR_STEP));
                step3Button.setStyle("-fx-background-color: " + (activeStep.get() == 2 ? COLOR_STEP_HL : COLOR_STEP));
                step4Button.setStyle("-fx-background-color: " + (activeStep.get() == 3 ? COLOR_STEP_HL : COLOR_STEP));
                step5Button.setStyle("-fx-background-color: " + (activeStep.get() == 4 ? COLOR_STEP_HL : COLOR_STEP));
                step6Button.setStyle("-fx-background-color: " + (activeStep.get() == 5 ? COLOR_STEP_HL : COLOR_STEP));
                summaryButton.setStyle("-fx-background-color: " + (activeStep.get() == 6 ? COLOR_STEP_HL : COLOR_STEP));
            }
        });
    }

    public void begin() {
        this.getChildren().remove(launch);
        this.setTop(toolBarBox);
        this.setCenter(allStepsPane);
    }

    private void selectStep(int stepIndex) {
        moveScreen((stepIndex + 1) * step1Pane.getWidth(), 0);
    }

    private void moveScreen(double toX, double toY) {
        moveScreen(toX, toY, true);
    }

    private void moveScreen(double toX, double toY, boolean animated) {
        if (animated) {
            TranslateTransition moveMe = new TranslateTransition(Duration.seconds(0.1), allStepsPane);
            moveMe.setToX(-1 * toX);
            moveMe.setToY(toY);
            moveMe.play();
        } else {
            allStepsPane.setTranslateX(-1 * toX);
        }
    }

    /**
     * Number of steps of the WZITS Tool
     */
    private final int numSteps = 6;
    /**
     * Layout box for holding the Title and Flow Controls.
     */
    private final VBox toolBarBox = new VBox();
    /**
     * Title Label
     */
    private final Label titleLabel = new Label("Work Zone ITS Tool");
    /**
     * Flow of steps for the User.
     */
    private final ToolBar stepFlow;
    /**
     * ToolBar button for the start/infoPanel
     */
    private final Button introButton = new Button("Start");
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
     * ToolBar process end button
     */
    private final Button summaryButton = new Button("End");

    /**
     *
     */
    private final LaunchPane launch;
    private final MenuBar menuBar;
    private final GridPane allStepsPane = new GridPane();
    private final BorderPane introPane;
    private final BorderPane step1Pane;
    private final BorderPane step2Pane;
    private final BorderPane step3Pane;
    private final BorderPane step4Pane;
    private final BorderPane step5Pane;
    private final BorderPane step6Pane;
    private final BorderPane summaryPane = new BorderPane();

    private final TreeView<Project> navigator;

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

}
