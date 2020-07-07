/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.IOHelper;
import GUI.Helper.NodeFactory;
import GUI.Helper.PDFIOHelper;
import GUI.Launch.LaunchPane;
import GUI.Step.IntroPane;
import GUI.Step.Step1Panel;
import GUI.Step.Step2Panel;
import GUI.Step.Step3Panel;
import GUI.Step.Step4Panel;
import GUI.Step.Step5Panel;
import GUI.Step.Step6Panel;
import GUI.Step.SummaryPanel;
import core.Project;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author jlake
 */
public class MainWindow extends BorderPane {

    private final MainController control;

    public MainWindow(final MainController mc, boolean launchSplash) {

        this.control = mc;
        control.setMainWindow(this);

        // Creating MenuBar
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuNavigation = new Menu("Results");
        Menu menuTemplate = new Menu("Templates");
        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuNavigation, menuHelp); // menuTemplate
        // Creating File Menu
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save as");
        MenuItem exitMenuItem = new MenuItem("Exit");
        newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.newProject();
            }
        });
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.openProject();
            }
        });
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                int saveResult = control.saveProject();
                IOHelper.confirm(saveResult, null);
            }
        });
        saveAsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                int saveResult = control.saveAsProject();
                IOHelper.confirm(saveResult, null);
            }
        });
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.exitProgram();
            }
        });
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        menuFile.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem);
        // Creating Edit Menu
        MenuItem projInfoMenuItem = new MenuItem("Project Info/WZ Metadata");
        menuEdit.getItems().add(projInfoMenuItem);
        projInfoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0, 0);
            }
        });
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
        Menu menuExportResults = new Menu("Export to PDF");
        MenuItem fsAllMenuItem = new MenuItem("Full Summary Report");
        fsAllMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeSummaryReport(control);
            }
        });
        MenuItem fs1MenuItem = new MenuItem("Fact Sheet 1");
        fs1MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 1);
            }
        });
        MenuItem fs2MenuItem = new MenuItem("Fact Sheet 2");
        fs2MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 2);
            }
        });
        MenuItem fs3MenuItem = new MenuItem("Fact Sheet 3");
        fs3MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 3);
            }
        });
        MenuItem fs4MenuItem = new MenuItem("Fact Sheet 4");
        fs4MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 4);
            }
        });
        MenuItem fs5MenuItem = new MenuItem("Fact Sheet 5");
        fs5MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 5);
            }
        });
        MenuItem fs6MenuItem = new MenuItem("Fact Sheet 6");
        fs6MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 6);
            }
        });
        MenuItem fs7MenuItem = new MenuItem("Fact Sheet 7");
        fs7MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 7);
            }
        });
        MenuItem fs8MenuItem = new MenuItem("Fact Sheet 8");
        fs8MenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeStepSummary(control, 8);
            }
        });
        MenuItem fs1MenuItemV2 = new MenuItem("Fact Sheet 1 (V2)");
        fs1MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 1);
            }
        });
        MenuItem fs2MenuItemV2 = new MenuItem("Fact Sheet 2 (V2)");
        fs2MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 2);
            }
        });
        MenuItem fs3MenuItemV2 = new MenuItem("Fact Sheet 3 (V2)");
        fs3MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 3);
            }
        });
        MenuItem fs4MenuItemV2 = new MenuItem("Fact Sheet 4 (V2)");
        fs4MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 4);
            }
        });
        MenuItem fs5MenuItemV2 = new MenuItem("Fact Sheet 5 (V2)");
        fs5MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 5);
            }
        });
        MenuItem fs6MenuItemV2 = new MenuItem("Fact Sheet 6 (V2)");
        fs6MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 6);
            }
        });
        MenuItem fs7MenuItemV2 = new MenuItem("Fact Sheet 7 (V2)");
        fs7MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 7);
            }
        });
        MenuItem fs8MenuItemV2 = new MenuItem("Fact Sheet 8 (V2)");
        fs8MenuItemV2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                PDFIOHelper.writeFactSheetPDF(control, 8);
            }
        });
        menuExportResults.getItems().addAll(fsAllMenuItem, new SeparatorMenuItem(), fs1MenuItem, fs2MenuItem, fs3MenuItem,
                fs4MenuItem, fs5MenuItem, fs6MenuItem, fs7MenuItem, fs8MenuItem,
                new SeparatorMenuItem(),
                fs1MenuItemV2, fs2MenuItemV2, fs3MenuItemV2, fs4MenuItemV2, fs5MenuItemV2, fs6MenuItemV2, fs7MenuItemV2, fs8MenuItemV2);
        goalWizMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0, Project.GOAL_SELECTION_INDEX);
            }
        });
        feasibilityWizMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0, Project.FEAS_WIZARD_SUMMARY_INDEX);
            }
        });
        stakeholdersWizMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0, Project.STAKEHOLDER_WIZARD_SUMMARY_INDEX);
            }
        });
        step1SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(0, Project.NUM_SUB_STEPS[0]);
            }
        });
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(control.activeStepProperty());
                super.bind(control.activeSubStepProperty(0));
            }

            @Override
            public boolean computeValue() {
                return control.getActiveStep() == 0 && control.getActiveSubStep(0) == Project.NUM_SUB_STEPS[0];
            }
        };
//        exportStep1SummaryMenuItem.disableProperty().bind(bb.not());
//        exportStep1SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent ae) {
//                PDFIOHelper.writeStep1Summary(((Step1Panel) step1Pane).getSummaryNode());
//            }
//        });

        appWizMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(1, Project.APP_WIZARD_SUMMARY_INDEX);
            }
        });
        step2SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(1, Project.NUM_SUB_STEPS[1]);
            }
        });
        step3SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(2, Project.NUM_SUB_STEPS[2]);
            }
        });
        step4SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(3, Project.NUM_SUB_STEPS[3]);
            }
        });
        step5SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(4, Project.NUM_SUB_STEPS[4]);
            }
        });
        step6SummaryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                control.selectStep(5, Project.NUM_SUB_STEPS[5]);
            }
        });
        menuNavigation.getItems().addAll(menuStep1Results, menuStep2Results,
                menuStep3Results, menuStep4Results, menuStep5Results,
                menuStep6Results);
        menuNavigation.getItems().add(menuExportResults);
        // Creating Templates Menu
        MenuItem loadTemplateMenuItem = new MenuItem("Load");
        MenuItem createTemplateMenuItem = new MenuItem("Create");
        MenuItem exportTemplateMenuItem = new MenuItem("Export");
        loadTemplateMenuItem.setDisable(true);
        createTemplateMenuItem.setDisable(true);
        exportTemplateMenuItem.setDisable(true);
        menuTemplate.getItems().addAll(loadTemplateMenuItem, createTemplateMenuItem, exportTemplateMenuItem);
        // Creating HelpMenu
        MenuItem helpMenuItem = new MenuItem("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                LaunchPane lp = new LaunchPane(control, true);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.initOwner(mc.getWindow());
                al.getDialogPane().getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
                al.getDialogPane().setMaxHeight(500);
                al.getDialogPane().setMaxWidth(700);
                al.setHeight(600);
                al.setWidth(800);
                al.setTitle("Help: WZITS Tool");
                al.setHeaderText("About");
                al.getDialogPane().setContent(lp);
                al.showAndWait();
            }
        });
        helpMenuItem.setDisable(true);
        menuHelp.getItems().addAll(aboutMenuItem, helpMenuItem);

        titleLabel1.getStyleClass().add("launch-title-label-top");
        titleLabel1.setPadding(new Insets(0, 20, 0, 20));
        titleLabel1.setMaxWidth(MainController.MAX_WIDTH);

        titleLabel2.getStyleClass().add("launch-title-label-top");
        titleLabel2.setPadding(new Insets(0, 20, 0, 20));
        titleLabel2.setMaxWidth(MainController.MAX_WIDTH);

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
        introPane = new IntroPane(control);
        step1Pane = new Step1Panel(control);
        step2Pane = new Step2Panel(control);
        step3Pane = new Step3Panel(control);
        step4Pane = new Step4Panel(control);
        step5Pane = new Step5Panel(control);
        step6Pane = new Step6Panel(control);
        summaryPane.setCenter(SummaryPanel.createStepSummary(control));
        stepPanesList.add(introPane);
        stepPanesList.add(step1Pane);
        stepPanesList.add(step2Pane);
        stepPanesList.add(step3Pane);
        stepPanesList.add(step4Pane);
        stepPanesList.add(step5Pane);
        stepPanesList.add(step6Pane);
        stepPanesList.add(summaryPane);
        //allStepsPane.add(introPane, 0, 0);
        //allStepsPane.add(step1Pane, 1, 0);
        //allStepsPane.add(step2Pane, 2, 0);
        //allStepsPane.add(step3Pane, 3, 0);
        //allStepsPane.add(step4Pane, 4, 0);
        //allStepsPane.add(step5Pane, 5, 0);
        //allStepsPane.add(step6Pane, 6, 0);
        //allStepsPane.add(summaryPane, 7, 0);
        centerPane.setCenter(introPane);

        //int numPanes = 8;
        //for (int colIdx = 0; colIdx < numPanes; colIdx++) {
        //    ColumnConstraints tcc = new ColumnConstraints();
        //    tcc.setPercentWidth(100.0 / numPanes);
        //    allStepsPane.getColumnConstraints().add(tcc);
        //}
        //GridPane.setVgrow(introPane, Priority.ALWAYS);
        //GridPane.setVgrow(step1Pane, Priority.ALWAYS);
        //GridPane.setVgrow(step2Pane, Priority.ALWAYS);
        //GridPane.setVgrow(step3Pane, Priority.ALWAYS);
        //GridPane.setVgrow(step4Pane, Priority.ALWAYS);
        //GridPane.setVgrow(step5Pane, Priority.ALWAYS);
        //GridPane.setVgrow(step6Pane, Priority.ALWAYS);
        //GridPane.setVgrow(summaryPane, Priority.ALWAYS);
        //GridPane.setHgrow(introPane, Priority.ALWAYS);
        //GridPane.setHgrow(step1Pane, Priority.ALWAYS);
        //GridPane.setHgrow(step2Pane, Priority.ALWAYS);
        //GridPane.setHgrow(step3Pane, Priority.ALWAYS);
        //GridPane.setHgrow(step4Pane, Priority.ALWAYS);
        //GridPane.setHgrow(step5Pane, Priority.ALWAYS);
        //GridPane.setHgrow(step6Pane, Priority.ALWAYS);
        //GridPane.setHgrow(summaryPane, Priority.ALWAYS);
        // Creating Panel
        setupActionListeners();
        setupPropertyBindings();

        launch = new LaunchPane(control);
        if (launchSplash) {
            this.setCenter(launch);
        } else {
            this.begin();
        }

    }

    private void setupActionListeners() {

    }

    private void setupPropertyBindings() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {

            }
        });

        control.activeStepProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
                if (control.getActiveStep() == Project.NUM_STEPS) {
                    summaryPane.setCenter(SummaryPanel.createStepSummary(control));
                }
                selectStep(control.getActiveStep());

                if (control.getActiveStep() >= 0) {
                    navigator.getRoot().setExpanded(true);
                    control.getProject().setStepStarted(control.getActiveStep(), true);
                }

            }
        });

    }

    public void begin() {
        this.getChildren().remove(launch);
        this.setTop(toolBarBox);
        BorderPane.setMargin(toolBarBox, new Insets(0, 0, 7, 0));
        this.setCenter(centerPane);  //allStepsPane
        this.setLeft(leftBar); // Navigator
        this.setRight(new BorderPane());
        this.setBottom(statusBar);
        this.projectFlowBar.setProceedButtonFlashing(true);
    }

    public void checkProceed() {
        projectFlowBar.checkProceed();
    }

    public void selectStep(int stepIndex) {
        //moveScreen((stepIndex + 1) * introPane.getWidth(), 0);
        changePanel(stepIndex, true);
    }

    private void changePanel(int stepIndex, boolean animated) {
        if (!animated) {
            centerPane.setCenter(this.stepPanesList.get(stepIndex + 1));
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);

            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    centerPane.setCenter(stepPanesList.get(stepIndex + 1));
                    FadeTransition ft2 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
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

    public Node goToFactSheet(int fsIdx) {
        int stepIdx;
        switch (fsIdx) {
            default:
            case 1:
                stepIdx = 0;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step1Panel) step1Pane).getFactSheet1Node();
            case 2:
                stepIdx = 0;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step1Panel) step1Pane).getFactSheet2Node();
            case 3:
                stepIdx = 1;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step2Panel) step2Pane).getFactSheet3Node();
            case 4:
                stepIdx = 1;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step2Panel) step2Pane).getFactSheet4Node();
            case 5:
                stepIdx = 2;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step3Panel) step3Pane).getFactSheet5Node();
            case 6:
                stepIdx = 3;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step4Panel) step4Pane).getFactSheet6Node();
            case 7:
                stepIdx = 4;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step5Panel) step5Pane).getFactSheet7Node();
            case 8:
                stepIdx = 5;
                control.selectStep(stepIdx, Project.NUM_SUB_STEPS[stepIdx]);
                return ((Step6Panel) step6Pane).getFactSheet8Node();
        }
    }

    public Node goToSummaryFactSheet(int fsIdx, boolean applyPause) {
        if (control.activeStepProperty().get() != Project.NUM_STEPS) {
            control.selectStep(Project.NUM_STEPS);
        }
        ((TabPane) ((BorderPane) summaryPane.getCenter()).getCenter()).getSelectionModel().select(fsIdx - 1);
        if (applyPause) {
            // Lets the node render
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
        return ((TabPane) ((BorderPane) summaryPane.getCenter()).getCenter()).getTabs().get(fsIdx - 1).getContent();
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
    //private final GridPane allStepsPane = new GridPane();
    private final BorderPane centerPane = new BorderPane();
    private final BorderPane introPane;
    private final BorderPane step1Pane;
    private final BorderPane step2Pane;
    private final BorderPane step3Pane;
    private final BorderPane step4Pane;
    private final BorderPane step5Pane;
    private final BorderPane step6Pane;
    private final BorderPane summaryPane = new BorderPane();

    private final ArrayList<BorderPane> stepPanesList = new ArrayList();

    private final TreeView<Project> navigator;
    private final BorderPane leftBar = new BorderPane();

}
