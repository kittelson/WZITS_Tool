/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.Step.Step1Panel;
import GUI.Launch.LaunchPane;
import GUI.Step.IntroPane;
import GUI.Step.Step2Panel;
import GUI.Step.Step3Panel;
import GUI.Step.Step4Panel;
import GUI.Step.Step5Panel;
import GUI.Step.Step6Panel;
import core.Project;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author jlake
 */
public class MainWindow extends BorderPane {

    private final MainController control;

    public MainWindow(final MainController control) {

        this.control = control;
        control.setMainWindow(this);

        // Creating MenuBar
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuResults = new Menu("Results");
        Menu menuTemplate = new Menu("Templates");
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuResults, menuTemplate, menuHelp);
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
        // Creating Results Menu
        Menu menuStep1Results = new Menu("Step 1");
        MenuItem goalWizMenuItem = new MenuItem("Goal Wizard");
        MenuItem feasibilityWizMenuItem = new MenuItem("Feasibility Wizard");
        MenuItem stakeholdersWizMenuItem = new MenuItem("Stakeholders Wizard");
        MenuItem step1SummaryMenuItem = new MenuItem("Step 1 Summary");
        menuStep1Results.getItems().addAll(feasibilityWizMenuItem,
                goalWizMenuItem, stakeholdersWizMenuItem, step1SummaryMenuItem);
        Menu menuStep2Results = new Menu("Step 2");
        MenuItem appWizMenuItem = new MenuItem("Application Wizard");
        MenuItem step2SummaryMenuItem = new MenuItem("Step 2 Summary");
        menuStep2Results.getItems().addAll(appWizMenuItem, step2SummaryMenuItem);
        Menu menuStep3Results = new Menu("Step 3");
        MenuItem step3SummaryMenuItem = new MenuItem("Step 3 Summary");
        menuStep3Results.getItems().addAll(step3SummaryMenuItem);
        Menu menuStep4Results = new Menu("Step 4");
        MenuItem step4SummaryMenuItem = new MenuItem("Step 4 Summary");
        menuStep4Results.getItems().addAll(step4SummaryMenuItem);
        Menu menuStep5Results = new Menu("Step 5");
        MenuItem step5SummaryMenuItem = new MenuItem("Step 5 Summary");
        menuStep5Results.getItems().addAll(step5SummaryMenuItem);
        Menu menuStep6Results = new Menu("Step 6");
        MenuItem step6SummaryMenuItem = new MenuItem("Step 6 Summary");
        menuStep6Results.getItems().addAll(step6SummaryMenuItem);
        menuResults.getItems().addAll(menuStep1Results, menuStep2Results,
                menuStep3Results, menuStep4Results, menuStep5Results,
                menuStep6Results);
        // Creating Templates Menu
        MenuItem loadTemplateMenuItem = new MenuItem("Load");
        MenuItem createTemplateMenuItem = new MenuItem("Create");
        MenuItem exportTemplateMenuItem = new MenuItem("Export");
        menuTemplate.getItems().addAll(loadTemplateMenuItem, createTemplateMenuItem, exportTemplateMenuItem);
        // Creating HelpMenu
        MenuItem helpMenuItem = new MenuItem("Help");
        menuHelp.getItems().add(helpMenuItem);

        titleLabel1.getStyleClass().add("launch-title-label-top");
        titleLabel1.setPadding(new Insets(0, 20, 0, 20));
        titleLabel1.setMaxWidth(MainController.MAX_WIDTH);

        titleLabel2.getStyleClass().add("launch-title-label-top");
        titleLabel2.setPadding(new Insets(0, 20, 0, 20));
        titleLabel2.setMaxWidth(MainController.MAX_WIDTH);

        // Styling the toolbar buttons
        SVGPath prevSVG = new SVGPath();
        prevSVG.setContent(IconHelper.SVG_STR_PREV);
        SVGPath nextSVG = new SVGPath();
        nextSVG.setContent(IconHelper.SVG_STR_NEXT);
//        prevStepButton.setGraphic(prevSVG);
//        introButton.getStyleClass().add("flow-step-start");
//        introButton.setStyle("-fx-background-color: " + COLOR_STEP_HL);
//        step1Button.getStyleClass().add("flow-step");
//        step2Button.getStyleClass().add("flow-step");
//        step3Button.getStyleClass().add("flow-step");
//        step4Button.getStyleClass().add("flow-step");
//        step5Button.getStyleClass().add("flow-step");
//        step6Button.getStyleClass().add("flow-step");
//        summaryButton.getStyleClass().add("flow-step-end");
//        nextStepButton.setGraphic(nextSVG);
//
//        // Organizing the buttons into the toolbar
//        stepFlowGrid.add(introButton, 0, 0);
//        stepFlowGrid.add(step1Button, 1, 0);
//        stepFlowGrid.add(step2Button, 2, 0);
//        stepFlowGrid.add(step3Button, 3, 0);
//        stepFlowGrid.add(step4Button, 4, 0);
//        stepFlowGrid.add(step5Button, 5, 0);
//        stepFlowGrid.add(step6Button, 6, 0);
//        stepFlowGrid.add(summaryButton, 7, 0);
//
//        ArrayList<Button> toolBarButtons = new ArrayList();
//        toolBarButtons.add(introButton);
//        toolBarButtons.add(step1Button);
//        toolBarButtons.add(step2Button);
//        toolBarButtons.add(step3Button);
//        toolBarButtons.add(step4Button);
//        toolBarButtons.add(step5Button);
//        toolBarButtons.add(step6Button);
//        toolBarButtons.add(summaryButton);

//        for (Button b : toolBarButtons) {
//            b.setWrapText(true);
//            b.setTextAlignment(TextAlignment.CENTER);
//            b.setMaxWidth(MainController.MAX_WIDTH);
//            b.setMaxHeight(MainController.MAX_HEIGHT);
//            GridPane.setHgrow(b, Priority.ALWAYS);
//            GridPane.setVgrow(b, Priority.ALWAYS);
//
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPercentWidth(100.0 / (numSteps + 2));
//            colConst.setFillWidth(true);
//            stepFlowGrid.getColumnConstraints().add(colConst);
//        }
//        stepFlowBar.setLeft(prevStepButton);
//        stepFlowBar.setCenter(stepFlowGrid);
//        stepFlowBar.setRight(nextStepButton);
//        BorderPane.setMargin(stepFlowGrid, new Insets(0, 3, 0, 3));
        //toolBarBox.setFillWidth(true);
        projectFlowBar = new FlowBar(control);
        toolBarBox.setAlignment(Pos.CENTER);
        BorderPane titleLabelPane = new BorderPane();
        titleLabelPane.setLeft(titleLabel1);
        titleLabelPane.setCenter(titleLabel2);
        toolBarBox.getChildren().addAll(menuBar, titleLabelPane, projectFlowBar); // titleLabel1
        toolBarBox.setStyle("-fx-background-color: #595959");

        // Setting up TreeView Navigator
        ProjectTreeItem projectItem = new ProjectTreeItem(control, control.getProject(), -1);
        navigator = new TreeView(projectItem);
        navigator.getStyleClass().add("navigator");
        navigator.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        navigator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    ProjectTreeItem pti = (ProjectTreeItem) navigator.getSelectionModel().getSelectedItem();
                    if (!pti.isRoot()) {
                        if (control.getProject().isStepStarted(pti.getStep()) && control.getProject().isSubStepStarted(pti.getStep(), pti.getSubStep())) {
                            control.selectStep(pti.getStep(), pti.getSubStep());
                        }
                    }
                }
            }
        });
        navigator.setCellFactory(new Callback<TreeView<Project>, TreeCell<Project>>() {
            @Override
            public TreeCell<Project> call(TreeView<Project> p) {
                return new ProjectTreeItem.ProjectTreeCell();
            }
        });
        navigator.setMaxWidth(NodeFactory.NAVIGATOR_MAX_WIDTH);

        //dashboard = new Dashboard(control);
        leftBar.setCenter(navigator);
        //leftBar.setBottom(dashboard);

        statusBar = new StatusBar(control);

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

        int numPanes = 8;
        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
            ColumnConstraints tcc = new ColumnConstraints();
            tcc.setPercentWidth(100.0 / numPanes);
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

        GridPane.setHgrow(introPane, Priority.ALWAYS);
        GridPane.setHgrow(step1Pane, Priority.ALWAYS);
        GridPane.setHgrow(step2Pane, Priority.ALWAYS);
        GridPane.setHgrow(step3Pane, Priority.ALWAYS);
        GridPane.setHgrow(step4Pane, Priority.ALWAYS);
        GridPane.setHgrow(step5Pane, Priority.ALWAYS);
        GridPane.setHgrow(step6Pane, Priority.ALWAYS);
        GridPane.setHgrow(summaryPane, Priority.ALWAYS);

        //allStepsPane.setStyle("-fx-background-color: green");
        // Creating Panel
        //this.setTop(stepFlow);
        //this.setBottom(allStepsPane);
        launch = new LaunchPane(control);
        this.setCenter(launch);

//        setupActionListeners();
        setupPropertyBindings();

//        prevStepButton.setDisable(true);
    }

//    private void setupActionListeners() {
//
//        prevStepButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(control.getActiveStep() - 1);
//            }
//        });
//
//        nextStepButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(control.getActiveStep() + 1);
//            }
//        });
//
//        introButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(-1);
//            }
//        });
//
//        step1Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(0);
//            }
//        });
//        step2Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(1);
//            }
//        });
//        step3Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(2);
//            }
//        });
//        step4Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(3);
//            }
//        });
//        step5Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(4);
//            }
//        });
//        step6Button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(5);
//            }
//        });
//        summaryButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(6);
//            }
//        });
//    }
    private void setupPropertyBindings() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
                if (allStepsPane != null && allStepsPane.isVisible()) {
                    allStepsPane.setMinWidth((numSteps + 2) * (control.getAppWidth() - 220));
                    allStepsPane.setMaxWidth((numSteps + 2) * (control.getAppWidth() - 220));
                    toolBarBox.setMaxWidth(control.getAppWidth() - 20);
                    ((Step1Panel) step1Pane).setViewWidth(introPane.getWidth());
                    moveScreen((control.getActiveStep() + 1) * introPane.getWidth(), 0, false);
                }
            }
        });

        control.activeStepProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
                selectStep(control.getActiveStep());
//                introButton.setStyle("-fx-background-color: " + (control.getActiveStep() == -1 ? COLOR_STEP_HL : COLOR_STEP));
//                step1Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 0 ? COLOR_STEP_HL : COLOR_STEP));
//                step2Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 1 ? COLOR_STEP_HL : COLOR_STEP));
//                step3Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 2 ? COLOR_STEP_HL : COLOR_STEP));
//                step4Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 3 ? COLOR_STEP_HL : COLOR_STEP));
//                step5Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 4 ? COLOR_STEP_HL : COLOR_STEP));
//                step6Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 5 ? COLOR_STEP_HL : COLOR_STEP));
//                summaryButton.setStyle("-fx-background-color: " + (control.getActiveStep() == 6 ? COLOR_STEP_HL : COLOR_STEP));
                if (control.getActiveStep() >= 0) {
                    navigator.getRoot().setExpanded(true);
                    control.getProject().setStepStarted(control.getActiveStep(), true);
                }
//                prevStepButton.setDisable(control.getActiveStep() == -1);
//                nextStepButton.setDisable(control.getActiveStep() == numSteps
//                        || !control.getProject().isStepComplete(control.getActiveStep()));
            }
        });
//        step1Button.disableProperty().bind(control.getProject().getStep(0).stepStartedProperty().not());
//        step2Button.disableProperty().bind(control.getProject().getStep(1).stepStartedProperty().not());
//        step3Button.disableProperty().bind(control.getProject().getStep(2).stepStartedProperty().not());
//        step4Button.disableProperty().bind(control.getProject().getStep(3).stepStartedProperty().not());
//        step5Button.disableProperty().bind(control.getProject().getStep(4).stepStartedProperty().not());
//        step6Button.disableProperty().bind(control.getProject().getStep(5).stepStartedProperty().not());
//        summaryButton.disableProperty().bind(control.getProject().getStep(5).stepFinishedProperty().not());

    }

    public void begin() {
        this.getChildren().remove(launch);
        this.setTop(toolBarBox);
        BorderPane.setMargin(toolBarBox, new Insets(0, 0, 7, 0));
        this.setCenter(allStepsPane);
        this.setLeft(leftBar); // Navigator
        this.setRight(new BorderPane());
        this.setBottom(statusBar);
        this.projectFlowBar.setProceedButtonFlashing(true);
        //this.statusBar.setStatusEnabled(false);
    }

    public void checkProceed() {
        projectFlowBar.checkProceed();
    }

    public void selectStep(int stepIndex) {
        moveScreen((stepIndex + 1) * introPane.getWidth(), 0);
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

    public void setTitleLabel(final String newTitleLabelText, boolean animated) {
        if (!animated) {
            titleLabel2.setText(newTitleLabelText);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), titleLabel2);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);

            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    titleLabel2.setText(newTitleLabelText);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), titleLabel2);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
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
     * Title Label positioned above the FlowBar
     */
    private final Label titleLabel1 = new Label("Work Zone ITS Tool");
    /**
     * Title Label positioned below the FlowBar
     */
    private final Label titleLabel2 = new Label("Project Introduction");
//    /**
//     * Grid for organize the step flow toolbar
//     */
//    private final GridPane stepFlowGrid = new GridPane(); //private final ToolBar stepFlow;
//    /**
//     * Flow of steps for the User.
//     */
//    private final BorderPane stepFlowBar = new BorderPane(); //private final ToolBar stepFlow;
//    /**
//     * ToolBar Button to move to the previous step.
//     */
//    private final Button prevStepButton = new Button();
//    /**
//     * ToolBar button for the start/infoPanel.
//     */
//    private final Button introButton = new Button("Start");
//    /**
//     * ToolBar button for Step 1.
//     */
//    private final Button step1Button = new Button("Step 1");
//    /**
//     * ToolBar button for Step 2.
//     */
//    private final Button step2Button = new Button("Step 2");
//    /**
//     * ToolBar button for Step 3.
//     */
//    private final Button step3Button = new Button("Step 3");
//    /**
//     * ToolBar button for Step 4.
//     */
//    private final Button step4Button = new Button("Step 4");
//    /**
//     * ToolBar button for Step 5.
//     */
//    private final Button step5Button = new Button("Step 5");
//    /**
//     * ToolBar button for Step 6.
//     */
//    private final Button step6Button = new Button("Step 6");
//    /**
//     * ToolBar process end button
//     */
//    private final Button summaryButton = new Button("End");
//    /**
//     * ToolBar button to move to the next step.
//     */
//    private final Button nextStepButton = new Button();

    /**
     * Flow control toolbar for the main window of the tool.
     */
    private final FlowBar projectFlowBar;
    /**
     * Status bar positioned at the bottom of the mainwindow.
     */
    private final StatusBar statusBar;
    /**
     * Launch/Landing screen for the tool
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
    private final BorderPane leftBar = new BorderPane();

}
