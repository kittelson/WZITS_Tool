/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IOHelper;
import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.Helper.ProgressIndicatorBar;
import GUI.MainController;
import GUI.Tables.Step1TableHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import core.Project;
import core.Question;
import core.QuestionGenerator;

import java.util.ArrayList;
import java.util.Stack;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import wzits_fx.VolumeToCapacityWizard;

/**
 * @author ltrask
 */
public class Step1Panel extends BorderPane {

    //private final int numSubSteps = 4;
    private final MainController control;

    private final int stepIndex = 0;

    private final String stepTitle = "Assessment of Needs";

    private final VBox mainVBox = new VBox();

    //private final GridPane allSubStepsPane = new GridPane();
    private final BorderPane centerPane = new BorderPane();

    private final VBox pVBox = new VBox();

    private final TableView unTable;

    private final ProgressIndicatorBar pbGW;

    private final SimpleDoubleProperty goalWizProgress = new SimpleDoubleProperty(0.0);

    private final GridPane stepIntroGrid = new GridPane();
    private final GridPane projInfoGrid = new GridPane();
    private final BorderPane genInfoPane = new BorderPane();
    private final GridPane genInfoGrid;
    private final BorderPane wzMetaDataPane = new BorderPane();
    private final GridPane wzMetaDataGrid;
    private final BorderPane unPane = new BorderPane();
    //private final BorderPane unSuppPane = new BorderPane();
    private final BorderPane goalSelectionPane = new BorderPane();
    private final BorderPane gwSummaryPane = new BorderPane();
    private final BorderPane feasibilityPane = new BorderPane();
    //private final BorderPane fwSummaryPane = new BorderPane();
    private final BorderPane stakeholderPane = new BorderPane();
    private final BorderPane swSummaryPane = new BorderPane();
    private final BorderPane teamMembersPane = new BorderPane();
    private final BorderPane itsPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();

    public Step1Panel(MainController mc) {

        this.control = mc;

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("1");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label(stepTitle);
        instructionLabel.setWrapText(true);
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
        instructionLabel.setAlignment(Pos.CENTER);
        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");
        instructionLabel.getStyleClass().add("intro-instructions");

        DoubleBinding widthBinding = new DoubleBinding() {
            {
                super.bind(widthProperty());
            }

            @Override
            protected double computeValue() {
                //return Math.max(widthProperty().get() * 0.70, 700);
                return (widthProperty().get() - 150); // 0.9
                //return (widthProperty().get()) * 0.2;
            }
        };

        DoubleBinding heightBinding = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue() {
                //return Math.max(heightProperty().get() * 0.35, 150);
                return heightProperty().get() * 0.35; // 0.35
            }
        };

        ImageView figStep1 = new ImageView(IconHelper.FIG_FLOW_STEP_1);
        //figStep1.setFitWidth(1500);
        figStep1.fitWidthProperty().bind(widthBinding);
        figStep1.fitHeightProperty().bind(heightBinding);
        figStep1.setPreserveRatio(true);
        figStep1.setSmooth(true);
        figStep1.setCache(true);

        stepIntroGrid.add(startLabel, 0, 0);
        stepIntroGrid.add(infoLabel, 0, 1);
        stepIntroGrid.add(instructionLabel, 1, 1);
        stepIntroGrid.add(figStep1, 1, 0);
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

        Label genInfoTitleLabel = new Label("General Information");
        genInfoTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoTitleLabel.getStyleClass().add("launch-title-label-top");
        genInfoGrid = createGeneralInfoGrid();
        genInfoPane.setTop(genInfoTitleLabel);
        genInfoPane.setCenter(genInfoGrid);
        genInfoPane.setBottom(NodeFactory.createFormattedLabel("", "launch-title-label-top"));

        Label wzMetaDataTitleLabel = new Label("Facility and Work Zone Configuration");
        wzMetaDataTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        wzMetaDataTitleLabel.getStyleClass().add("launch-title-label-top");
        wzMetaDataGrid = createWZInputGrid();
        wzMetaDataPane.setTop(wzMetaDataTitleLabel);
        wzMetaDataPane.setCenter(wzMetaDataGrid);
        wzMetaDataPane.setBottom(NodeFactory.createFormattedLabel("", "launch-title-label-top"));

        projInfoGrid.add(genInfoPane, 0, 0);
        projInfoGrid.add(wzMetaDataPane, 1, 0);
        projInfoGrid.setHgap(5);
        projInfoGrid.setPadding(new Insets(0, 5, 0, 5));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        projInfoGrid.getColumnConstraints().addAll(col1, col2);

        // Creating User Needs Questions Pane
        pbGW = new ProgressIndicatorBar(goalWizProgress, 1.0, "%.0f%%", true);
        pbGW.getStyleClass().add("progress-bar");
        pbGW.setMaxWidth(MainController.MAX_WIDTH);
        //pagination = new Pagination(Step1TableHelper.getPageCount(control.getProject(), Step1TableHelper.GOAL_WIZARD));
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer pageIndex) {
//                return Step1TableHelper.createPageTable(control.getProject(), Step1TableHelper.GOAL_WIZARD, pageIndex, Step1TableHelper.QS_PER_PAGE);
//            }
//        });
        for (Question q : mc.getProject().getGoalWizardQs()) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    updateProgressBar();
                }
            });
        }
        updateProgressBar();
        unTable = Step1TableHelper.createUserNeeds(control.getProject());
        pVBox.getChildren().addAll(unTable, pbGW);
        VBox.setVgrow(unTable, Priority.ALWAYS);
        unPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][2], "substep-title-label"));  // "User Needs"
        unPane.setCenter(pVBox);
        unPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // User Needs Supplemental Questions Panel
        //unSuppPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][3], "substep-title-label"));  //  "User Needs Supplemental Questions"
        //unSuppPane.setCenter(Step1TableHelper.createUserNeedsSupplemental(control.getProject()));
        //unSuppPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
        // Major Goal Types Questions Panel
        goalSelectionPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][3], "substep-title-label"));  // "Goals Selection"
        //majorGoalsPane.setCenter(Step1TableHelper.getMajorGoalsTable(control.getProject()));
        goalSelectionPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
        goalSelectionPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Goal Wizard Summary Panel
        gwSummaryPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][4], "substep-title-label"));  // "Goal Wizard Summary"
        //gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
        gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSelectedGoalsNode());
        gwSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Feasibility Questions Panel
        feasibilityPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][5], "substep-title-label"));  // "Feasibility Wizard"
        GridPane fwgp1 = new GridPane();
        fwgp1.add(Step1TableHelper.getFeasibilityWizard(control.getProject()), 0, 0);
        fwgp1.add(control.getProject().getFeasibilityMatrix().createSummaryPanel(), 0, 1);
        RowConstraints rc1 = new RowConstraints();
        rc1.setPercentHeight(80);
        RowConstraints rc2 = new RowConstraints();
        rc2.setPercentHeight(20);
        fwgp1.getRowConstraints().addAll(rc1, rc2);
        feasibilityPane.setCenter(fwgp1);
        feasibilityPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        wzInputChoice1.setTooltip(new Tooltip("Select the Roadway Functional Class to launch the V/C Wizard"));

        // Feasibility Wizard panel
        //fwSummaryPane.setTop(NodeFactory.createFormattedLabel("Feasibility Wizard Summary", "substep-title-label"));
        //BorderPane fwbp2 = new BorderPane();
        //fwSummaryPane.setCenter(fwbp2);
        //fwSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
        // Stakeholders Questions Panel
        stakeholderPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][6], "substep-title-label")); // "Stakeholders"
        stakeholderPane.setCenter(Step1TableHelper.createStakeholderWizard(control.getProject()));
        stakeholderPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholder Wizard panel
        swSummaryPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][7], "substep-title-label"));  // "Stakeholder Wizard Summary"
        swSummaryPane.setCenter(control.getProject().getStakeholderMatrix().createSummaryTable());
        swSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Team Members and Stakeholders Panel
        teamMembersPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][8], "substep-title-label"));  // "Selected Core Team Members and Stakeholders"
        teamMembersPane.setCenter(control.getProject().getStakeholderMatrix().createSelectedMembersPanel());
        teamMembersPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // ITS Panel
        itsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][9], "substep-title-label"));  // "ITS Resources"
        itsPane.setCenter(Step1TableHelper.getITSResourcesPanel(control.getProject()));
        itsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));  //
        stepReportPane.setCenter(Step1TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Adding to main substep VBox
        int colIdx = 0;
        mainVBox.getChildren().addAll(centerPane); //allSubStepsPane
//        allSubStepsPane.add(stepIntroGrid, colIdx++, 0);
//        allSubStepsPane.add(projInfoGrid, colIdx++, 0);
//        allSubStepsPane.add(unPane, colIdx++, 0);
//        allSubStepsPane.add(unSuppPane, colIdx++, 0);
//        allSubStepsPane.add(goalSelectionPane, colIdx++, 0);
//        allSubStepsPane.add(gwSummaryPane, colIdx++, 0);
//        allSubStepsPane.add(feasibilityPane, colIdx++, 0);
//        //allSubStepsPane.add(fwSummaryPane, colIdx++, 0);
//        allSubStepsPane.add(stakeholderPane, colIdx++, 0);
//        allSubStepsPane.add(swSummaryPane, colIdx++, 0);
//        allSubStepsPane.add(teamMembersPane, colIdx++, 0);
//        allSubStepsPane.add(itsPane, colIdx++, 0);
//        allSubStepsPane.add(stepReportPane, colIdx++, 0);
        subStepPanesList.add(stepIntroGrid);
        subStepPanesList.add(projInfoGrid);
        subStepPanesList.add(unPane);
        //subStepPanesList.add(unSuppPane);
        subStepPanesList.add(goalSelectionPane);
        subStepPanesList.add(gwSummaryPane);
        subStepPanesList.add(feasibilityPane);
        subStepPanesList.add(stakeholderPane);
        subStepPanesList.add(swSummaryPane);
        subStepPanesList.add(teamMembersPane);
        subStepPanesList.add(itsPane);
        subStepPanesList.add(stepReportPane);

        centerPane.setCenter(stepIntroGrid);

//        int numPanes = getNumSubSteps() + 2;
//        for (int col = 0; col < numPanes; col++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(projInfoGrid, Priority.ALWAYS);
        GridPane.setVgrow(unPane, Priority.ALWAYS);
        //GridPane.setVgrow(unSuppPane, Priority.ALWAYS);
        GridPane.setVgrow(goalSelectionPane, Priority.ALWAYS);
        GridPane.setVgrow(gwSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(feasibilityPane, Priority.ALWAYS);
        //GridPane.setVgrow(fwSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(stakeholderPane, Priority.ALWAYS);
        GridPane.setVgrow(swSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(centerPane, Priority.ALWAYS);  //allSubStepsPane
        this.setCenter(mainVBox);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void updateProgressBar() {
        int sum = 0;
        int numQs = 0;
        for (Question q : control.getProject().getGoalWizardQs()) {
            if (q.getResponseIdx() > -1) {
                sum++;
            }
            numQs++;
        }
        //pb.setProgress(sum / (double) TableHelper.getNumberOfQuestionsByStep(0));
        goalWizProgress.set(sum / (double) numQs);
    }

    private void setupActionListeners() {

    }

    private int getActiveSubStep() {
        return control.getActiveSubStep(stepIndex);
    }

    private int getNumSubSteps() {
        return Project.NUM_SUB_STEPS[stepIndex];
    }

    public void setViewWidth(double viewWidth) {

    }

    private void setupPropertyBindings() {
//        this.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
//
//                }
//            }
//        });

        control.activeStepProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (newVal.intValue() > stepIndex) {
                    selectSubStep(Project.NUM_SUB_STEPS[stepIndex], false);
                } else if (newVal.intValue() < stepIndex) {
                    selectSubStep(-1, false);
                } else {
                    selectSubStep(control.getActiveSubStep(stepIndex));
                }
            }
        });

        control.activeSubStepProperty(stepIndex).addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
                selectSubStep(getActiveSubStep());
                control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
                control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);
                if (oldVal.intValue() == Project.FEAS_WIZARD_SUMMARY_INDEX && control.getProject().getFeasibilityMatrix().getFeasibility() < 10) {
                    JFXDialogLayout content = new JFXDialogLayout();
                    JFXDialog dialogAlert = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
                    dialogAlert.show();
                    content.setHeading(new Text("Low Project Feasibility Score"));
                    content.setBody(new Text("The WZITS tool feasibility assessment has indicated that WZITS may not be feasible for your project.\n" +
                            "The tool can continue to be used but the low feasibility score will be indicated in the step and summary reports."));
                    content.setStyle("-fx-font-size: 16pt");
                    btnCloseDialog.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            dialogAlert.close();
                        }
                    });
                    content.setActions(btnCloseDialog);
//                    Alert al = new Alert(Alert.AlertType.WARNING);
//                    al.initOwner(control.getWindow());
//                    al.setTitle("Feasibility Warning");
//                    al.setHeaderText("Low Project Feasibility Score");
//                    al.setContentText("The WZITS tool feasibility assessment has indicated that WZITS may not be feasible for your project.  The tool can continue to be used "
//                            + "but the low feasibility score will be indicated in the step and summary reports.");
//                    al.showAndWait();
                }
                switch (getActiveSubStep()) {
                    case Project.GOAL_SELECTION_INDEX:
                        goalSelectionPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
                    case Project.GOAL_WIZARD_SUMMARY_INDEX:
                        //gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
                        gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSelectedGoalsNode());
                        break;
                    case Project.FEAS_WIZARD_SUMMARY_INDEX:
                        //fwSummaryPane.setCenter(control.getProject().getFeasibilityMatrix().createSummaryPanel());
                        break;
                    case Project.STAKEHOLDER_WIZARD_SUMMARY_INDEX:
                        swSummaryPane.setCenter(control.getProject().getStakeholderMatrix().createSummaryTable());
                        break;
                    case Project.TEAM_SUMMARY_INDEX:
                        teamMembersPane.setCenter(control.getProject().getStakeholderMatrix().createSelectedMembersPanel());
                        break;
                }

                if (getActiveSubStep() == Project.NUM_SUB_STEPS[stepIndex]) {
                    stepReportPane.setCenter(Step1TableHelper.createStepSummary(control));
                }

                control.checkProceed();
            }
        });

        // General Info Bindings
        genInfoTF1.textProperty().bindBidirectional(control.getProject().agencyProperty());
        genInfoTF2.textProperty().bindBidirectional(control.getProject().analystProperty());
        genInfoTF3.textProperty().bindBidirectional(control.getProject().nameProperty());
        genInfoTF4.textProperty().bindBidirectional(control.getProject().urlLinkProperty());
        genInfoTA1.textProperty().bindBidirectional(control.getProject().descriptionProperty());
        genInfoTA2.textProperty().bindBidirectional(control.getProject().limitsProperty());
        photoUploaded();

        // Work zone metadata bindings
        control.getProject().aadtProperty().bindBidirectional(this.wzInputSpin1.getValueFactory().valueProperty());
        control.getProject().wzLengthProperty().bindBidirectional(this.wzInputSpin2.getValueFactory().valueProperty());
        control.getProject().numRoadwayLanesProperty().bindBidirectional(this.wzInputSpin3.getValueFactory().valueProperty());
        control.getProject().shoulderWidthProperty().bindBidirectional(this.wzInputSpin4.getValueFactory().valueProperty());
        control.getProject().shoulderWidthProperty().bindBidirectional(this.wzInputSpin10.getValueFactory().valueProperty());
        control.getProject().speedLimitProperty().bindBidirectional(this.wzInputSpin5.getValueFactory().valueProperty());
        control.getProject().laneWidthBaseProperty().bindBidirectional(this.wzInputSpin6.getValueFactory().valueProperty());
        control.getProject().wzSpeedLimitProperty().bindBidirectional(this.wzInputSpin7.getValueFactory().valueProperty());
        control.getProject().numLanesClosedProperty().bindBidirectional(this.wzInputSpin8.getValueFactory().valueProperty());
        control.getProject().laneWidthWZProperty().bindBidirectional(this.wzInputSpin9.getValueFactory().valueProperty());
        control.getProject().functionalClassProperty().bindBidirectional(this.wzInputChoice1.valueProperty());
        control.getProject().wzTypeProperty().bindBidirectional(this.wzInputChoice2.valueProperty());
        control.getProject().maintainingAgencyProperty().bindBidirectional(this.wzInputChoice3.valueProperty());
        control.getProject().areaTypeProperty().bindBidirectional(this.wzInputChoice4.valueProperty());
        control.getProject().signalizedCorridorProperty().bindBidirectional(this.wzInputChoice5.valueProperty());
        control.getProject().nationalHighwaySystemProperty().bindBidirectional(this.wzInputChoice6.valueProperty());
        control.getProject().shoulderClosureProperty().bindBidirectional(this.wzInputChoice7.valueProperty());
        control.getProject().federalAidProperty().bindBidirectional(this.wzInputChoice8.valueProperty());

        control.getProject().laneWidthBaseProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (control.getProject().getLaneWidthBase() > control.getProject().getLaneWidthWZ()) {
                    control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsNo(true);
                }
            }
        });
        control.getProject().laneWidthWZProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (control.getProject().getLaneWidthBase() > control.getProject().getLaneWidthWZ()) {
                    control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsNo(true);
                }
            }
        });

        control.getProject().shoulderWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (control.getProject().getShoulderWidth() == 0) {
                    control.getProject().getQGen().emergencyShoulderQ.setAnswerIsNo(true);
                    //control.getProject().getQGen().shoulderQIdx.setAnswerIsNo(true);
                }
            }
        });
        control.getProject().shoulderClosureProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> ov, Object oldVal, Object newVal) {
                if (control.getProject().getShoulderClosure().equalsIgnoreCase("yes")) {
                    control.getProject().getQGen().emergencyShoulderQ.setAnswerIsYes(true);
                    //control.getProject().getQGen().shoulderQIdx.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().emergencyShoulderQ.setAnswerIsNo(true);
                    //control.getProject().getQGen().shoulderQIdx.setAnswerIsNo(true);
                }
            }
        });
        control.getProject().speedLimitProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (control.getProject().getSpeedLimit() > control.getProject().getWzSpeedLimit()) {
                    control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsNo(true);
                }
            }
        });
        control.getProject().wzSpeedLimitProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (control.getProject().getSpeedLimit() > control.getProject().getWzSpeedLimit()) {
                    control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsNo(true);
                }
            }
        });

        control.getProject().functionalClassProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (!(control.getProject().getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[0]) || control.getProject().getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[1]))) {
                    control.getProject().getQGen().disableRampMetersQ.setAnswerIsNo(true);
                }
            }
        });
        control.getProject().federalAidProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (control.getProject().getFederalAid().equalsIgnoreCase("yes")) {
                    control.getProject().getQGen().biannualProcessReviewQ.setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().biannualProcessReviewQ.setAnswerIsNo(true);
                }
            }
        });

        control.getProject().wzTypeProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (control.getProject().getWzType().equalsIgnoreCase(Project.MUTCD_LIST[0])) {
                    control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setLocked(false);
                    if (control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).getAnswer() == 3) {
                        //control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setAnswer("< 4 Months");
                    }
                } else {
                    control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setAnswer(3);
                    control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setLocked(true);
                }
            }
        });

        control.getProject().signalizedCorridorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (control.getProject().getSignalizedCorridor().equalsIgnoreCase("yes")) {
                    control.getProject().getQGen().qStakeholderYNList.get(control.getProject().getQGen().signalizedCorridorQIdx).setAnswerIsYes(true);
                } else {
                    control.getProject().getQGen().qStakeholderYNList.get(control.getProject().getQGen().signalizedCorridorQIdx).setAnswerIsNo(true);
                }
            }
        });

    }

    private GridPane createGeneralInfoGrid() {
        // Setting up styles
        genInfoLabel1.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel2.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel3.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel4.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel5.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel6.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel7.setMaxWidth(MainController.MAX_WIDTH);
        genInfoLabel8.setMaxWidth(MainController.MAX_WIDTH);

        genInfoLabel1.getStyleClass().add("gen-info-label-style");
        genInfoLabel2.getStyleClass().add("gen-info-label-style");
        genInfoLabel3.getStyleClass().add("gen-info-label-style");
        genInfoLabel4.getStyleClass().add("gen-info-label-style");
        genInfoLabel5.getStyleClass().add("gen-info-label-style");
        genInfoLabel6.getStyleClass().add("gen-info-label-style");
        genInfoLabel7.getStyleClass().add("gen-info-label-style");
        genInfoLabel8.getStyleClass().add("gen-info-label-style");
        btnLaunchVCwiz.getStyleClass().add("jfx-button-style");
        //genInfoButton1.setStyle("-fx-padding: 0.7em 0.57em 0.57em 0.57em; -fx-font-size: 12pt; -jfx-button-type: RAISED; -fx-background-color:#4472c4; -fx-text-fill: white;");
        //genInfoButton1.setStyle("-fx-padding: 0.7em 0.57em 0.57em 0.57em; -fx-font-size: 12pt; -jfx-button-type: RAISED; -fx-background-color:#4472c4; -fx-text-fill: white;");
        genInfoButton1.getStyleClass().add("jfx-button-style");
        btnCloseDialog.setStyle("-fx-padding: 0.7em 0.57em 0.57em 0.57em; -fx-font-size: 12pt; -jfx-button-type: RAISED; -fx-background-color:rgb(204,85,0); -fx-text-fill: white;");

        genInfoDateToday.setText(control.getProject().getDateString());

        genInfoTA1.setWrapText(true);
        genInfoTA2.setWrapText(true);

        genInfoButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                boolean success = IOHelper.getProjectImage(control);
                if (success) {
                    photoUploaded();
                }
            }
        });

        genInfoPicGrid.add(genInfoPicLabel, 0, 0);
        genInfoPicGrid.add(genInfoButton1, 1, 0);
        genInfoButton1.setMaxWidth(125);
        //genInfoButton1.setDisable(true);
        genInfoPicLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoPicGrid.getColumnConstraints().add(0, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        genInfoPicGrid.getColumnConstraints().add(1, new ColumnConstraints(75, 75, 75, Priority.NEVER, HPos.RIGHT, true));
        genInfoPicGrid.setMaxHeight(maxProjImagePreviewHeight);

        GridPane grid = new GridPane();

        grid.add(genInfoLabel1, 0, 1);
        grid.add(genInfoLabel2, 0, 2);
        grid.add(genInfoLabel3, 0, 3);
        grid.add(genInfoLabel4, 0, 4);
        grid.add(genInfoLabel5, 0, 5);
        grid.add(genInfoLabel6, 0, 6);
        grid.add(genInfoLabel7, 0, 7);
        grid.add(genInfoLabel8, 0, 8);

        grid.add(genInfoTF1, 1, 1);
        grid.add(genInfoTF2, 1, 2);
        grid.add(genInfoDateToday, 1, 3);
        grid.add(genInfoTF3, 1, 4);
        grid.add(genInfoTA1, 1, 5);
        grid.add(genInfoTA2, 1, 6);
        grid.add(genInfoTF4, 1, 7);
        grid.add(genInfoPicGrid, 1, 8);

        double leftColsplit = 40;

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColsplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColsplit);

        grid.getColumnConstraints().addAll(col1, col2);

        return grid;
    }

    private GridPane createWZInputGrid() {

        Hyperlink mutcdButton = new Hyperlink("(?)");
        mutcdButton.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutcdButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JFXDialogLayout content = new JFXDialogLayout();
                JFXDialog dialogAlert = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
                dialogAlert.show();
                content.setHeading(new Text("MUTCD Designations (MUTCD 6G.02"));
                content.setBody(new Text("1) Long-term stationary - Work that occupies a location more than 3 days\n\n"
                        + "2) Intermediate-term stationary - Work that occupies a location more than one daylight period up to 3 days, or nightime work lasting more than 1 hour\n\n"
                        + "3) Short-term stationary - Daytime work lasting more than 1 hour within a single daylight period\n\n"
                        + "4) Short duration - Work that occupies a location up to 1 hour\n\n"
                        + "5) Mobile - Work that moves intermittently or continuously\n\n"));
                content.setStyle("-fx-font-size: 16pt");
                btnCloseDialog.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dialogAlert.close();
                    }
                });
                content.setActions(btnCloseDialog);
            }
        });

        Hyperlink mutchBtnWZL = new Hyperlink("(?)");
        mutchBtnWZL.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchBtnWZL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Lorem ipsum dolor");
                al.setHeaderText("sit amet, consectetur adipiscing");
                al.setContentText("1) Long-term stationary - Work that occupies a location more than 3 days\n\n"
                        + "2) elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et\n\n"
                        + "4) Short duration - Work thLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore e\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchBtnWZSL = new Hyperlink("(?)");
        mutchBtnWZSL.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchBtnWZSL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("consectetur adipiscing elit");
                al.setHeaderText("incididunt ut labore");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod\n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmo\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmo\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchBtnNumLane = new Hyperlink("(?)");
        mutchBtnNumLane.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchBtnNumLane.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFXDialogLayout numLanesContent = new JFXDialogLayout();
                numLanesContent.setStyle("-fx-font-size: 16pt");
                JFXDialog numLanesDialog = new JFXDialog(MainController.getRootStackPane(), numLanesContent, JFXDialog.DialogTransition.CENTER);
                numLanesContent.setHeading(new Text("Work Zone Configuration"));
                numLanesContent.setBody(new Text("Maximum number of lanes to close during the duration of the project"));
                numLanesDialog.show();
                numLanesContent.setActions(btnCloseDialog);
                btnCloseDialog.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        numLanesDialog.close();
                    }
                });;
            }
        });

        Hyperlink mutchWZLW = new Hyperlink("(?)");
        mutchWZLW.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchWZLW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("adipiscing elit");
                al.setHeaderText("psum dolor sit amet");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchSC = new Hyperlink("(?)");
        mutchSC.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchSC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("dolor sit amet");
                al.setHeaderText("dolor sit amet, consectetur");
                al.setContentText("1) eiusmod tempor incididunt ut labore et dolore magna\n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchFAP = new Hyperlink("(?)");
        mutchFAP.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchFAP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchFCR = new Hyperlink("(?)");
        mutchFCR.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchFCR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });
        Label modalHeader = new Label();
        Label modalDesc = new Label();

        Hyperlink mutchMA = new Hyperlink("(?)");
        modalHeader.setText("MUTCD Designations (MUTCH 6G.02)");
        modalDesc.setText("Who owns the roadway?");
        mutchMA.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchMA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StackPane modalStackPane = MainController.getRootStackPane();
                JFXDialogLayout content = new JFXDialogLayout();
                content.setStyle("-fx-font-size: 16pt");
                JFXDialog modalDialog = new JFXDialog(modalStackPane,content, JFXDialog.DialogTransition.CENTER);
                modalDialog.show();
                content.setHeading(modalHeader);
                content.setBody(modalDesc);
                content.setActions(btnCloseDialog);
                btnCloseDialog.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        modalDialog.close();
                    }
                });
            }
        });

        Hyperlink mutchAT = new Hyperlink("(?)");
        mutchAT.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchAT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchAADT = new Hyperlink("(?)");
        mutchAADT.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchAADT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchNRL = new Hyperlink("(?)");
        mutchNRL.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchNRL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchSW = new Hyperlink("(?)");
        mutchSW.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchSW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchPSL = new Hyperlink("(?)");
        mutchPSL.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchPSL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchLW = new Hyperlink("(?)");
        mutchLW.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchLW.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchPSC = new Hyperlink("(?)");
        mutchPSC.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchPSC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

        Hyperlink mutchNHS = new Hyperlink("(?)");
        mutchNHS.getStyleClass().add("wz-input-hyperlink-small-superscript");
        mutchNHS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("More Information");
                al.setHeaderText("MUTCD Designations (MUTCD 6G.02)");
                al.setContentText("1) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod \n\n"
                        + "2) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "3) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "4) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n"
                        + "5) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n");
                al.setWidth(300);
                al.showAndWait();
            }
        });

//        wzInputLabel11.setGraphic(mutchBtnWZL);
//        wzInputLabel11.setContentDisplay(ContentDisplay.RIGHT);
        wzInputLabel12.setGraphic(mutcdButton);
        wzInputLabel12.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel13.setGraphic(mutchBtnWZSL);
//        wzInputLabel13.setContentDisplay(ContentDisplay.RIGHT);
        wzInputLabel14.setGraphic(mutchBtnNumLane);
        wzInputLabel14.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel18.setGraphic(mutchWZLW);
//        wzInputLabel18.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel19.setGraphic(mutchSC);
//        wzInputLabel19.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel20.setGraphic(mutchFAP);
//        wzInputLabel20.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel1.setGraphic(mutchFCR);
//        wzInputLabel1.setContentDisplay(ContentDisplay.RIGHT);
        wzInputLabel2.setGraphic(mutchMA);
        wzInputLabel2.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel3.setGraphic(mutchAT);
//        wzInputLabel3.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel4.setGraphic(mutchAADT);
//        wzInputLabel4.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel5.setGraphic(mutchNRL);
//        wzInputLabel5.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel6.setGraphic(mutchSW);
//        wzInputLabel6.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel21.setGraphic(mutchSW);
//        wzInputLabel21.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel7.setGraphic(mutchPSL);
//        wzInputLabel7.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel8.setGraphic(mutchLW);
//        wzInputLabel8.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel9.setGraphic(mutchPSC);
//        wzInputLabel9.setContentDisplay(ContentDisplay.RIGHT);
//        wzInputLabel10.setGraphic(mutchNHS);
//        wzInputLabel10.setContentDisplay(ContentDisplay.RIGHT);

        wzTitleLabel1.setMaxWidth(MainController.MAX_WIDTH);
        wzTitleLabel2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel3.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel4.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel5.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel6.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel21.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel7.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel8.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel9.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel10.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel11.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel12.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel13.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel14.setMaxWidth(MainController.MAX_WIDTH);
        //wzInputLabel15.setMaxWidth(MainController.MAX_WIDTH);
        //wzInputLabel16.setMaxWidth(MainController.MAX_WIDTH);
        //wzInputLabel17.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel18.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel19.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel20.setMaxWidth(MainController.MAX_WIDTH);

        wzInputChoice1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice3.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice4.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice5.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice6.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice7.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice8.setMaxWidth(MainController.MAX_WIDTH);

        wzInputSpin1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin3.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin4.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin10.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin5.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin6.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin7.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin8.setMaxWidth(MainController.MAX_WIDTH);
        wzInputSpin9.setMaxWidth(MainController.MAX_WIDTH);

        wzTitleLabel1.getStyleClass().add("wz-input-title-style");
        wzTitleLabel2.getStyleClass().add("wz-input-title-style");
        wzInputLabel1.getStyleClass().add("wz-input-label-style");
        wzInputLabel2.getStyleClass().add("wz-input-label-style");
        wzInputLabel3.getStyleClass().add("wz-input-label-style");
        wzInputLabel4.getStyleClass().add("wz-input-label-style");
        wzInputLabel5.getStyleClass().add("wz-input-label-style");
        wzInputLabel6.getStyleClass().add("wz-input-label-style");
        wzInputLabel21.getStyleClass().add("wz-input-label-style");
        wzInputLabel7.getStyleClass().add("wz-input-label-style");
        wzInputLabel8.getStyleClass().add("wz-input-label-style");
        wzInputLabel9.getStyleClass().add("wz-input-label-style");
        wzInputLabel10.getStyleClass().add("wz-input-label-style");
        wzInputLabel11.getStyleClass().add("wz-input-label-style");
        wzInputLabel12.getStyleClass().add("wz-input-label-style");
        wzInputLabel13.getStyleClass().add("wz-input-label-style");
        wzInputLabel14.getStyleClass().add("wz-input-label-style");
        //wzInputLabel15.getStyleClass().add("wz-input-label-style");
        //wzInputLabel16.getStyleClass().add("wz-input-label-style");
        //wzInputLabel17.getStyleClass().add("wz-input-label-style");
        wzInputLabel18.getStyleClass().add("wz-input-label-style");
        wzInputLabel19.getStyleClass().add("wz-input-label-style");
        wzInputLabel20.getStyleClass().add("wz-input-label-style");
        //wzInputChoice1.getStyleClass().add("wzits-choice-box");
        //wzInputChoice2.getStyleClass().add("wzits-choice-box");
        //wzInputChoice3.getStyleClass().add("wzits-choice-box");

        wzInputSpin1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 400000, control.getProject().getAadt(), 500));
        wzInputSpin2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 50, control.getProject().getWzLength(), 0.1));
        wzInputSpin3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, control.getProject().getNumRoadwayLanes(), 1));
        wzInputSpin4.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, control.getProject().getShoulderWidth(), 0.5));
        wzInputSpin10.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, control.getProject().getShoulderWidth(), 0.5));
        wzInputSpin5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, control.getProject().getSpeedLimit(), 5));
        wzInputSpin6.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 25, control.getProject().getLaneWidthBase(), 0.5));
        wzInputSpin7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, control.getProject().getWzSpeedLimit(), 5));
        wzInputSpin8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, control.getProject().getNumLanesClosed(), 1));
        wzInputSpin9.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 25, control.getProject().getLaneWidthWZ(), 0.5));

        wzInputSpin1.setEditable(true);
        wzInputSpin1.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin1);
        });
        wzInputSpin2.setEditable(true);
        wzInputSpin2.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin2);
        });
        wzInputSpin3.setEditable(true);
        wzInputSpin3.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin3);
        });
        wzInputSpin4.setEditable(true);
        wzInputSpin4.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin4);
        });
        wzInputSpin10.setEditable(true);
        wzInputSpin10.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin10);
        });
        wzInputSpin5.setEditable(true);
        wzInputSpin5.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin5);
        });
        wzInputSpin6.setEditable(true);
        wzInputSpin6.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin6);
        });
        wzInputSpin7.setEditable(true);
        wzInputSpin7.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin7);
        });
        wzInputSpin8.setEditable(true);
        wzInputSpin8.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin8);
        });
        wzInputSpin9.setEditable(true);
        wzInputSpin9.focusedProperty().addListener((s, ov, nv) -> {
            if (nv) {
                return;
            }
            commitEditorText(wzInputSpin9);
        });

        wzInputChoice1.getSelectionModel().select(control.getProject().getFunctionalClass());
        wzInputChoice2.getSelectionModel().select(control.getProject().getWzType());
        wzInputChoice3.getSelectionModel().select(control.getProject().getMaintainingAgency());
        wzInputChoice4.getSelectionModel().select(control.getProject().getAreaType());
        wzInputChoice5.getSelectionModel().select(control.getProject().getSignalizedCorridor());
        wzInputChoice6.getSelectionModel().select(control.getProject().getNationalHighwaySystem());
        wzInputChoice7.getSelectionModel().select(control.getProject().getShoulderClosure());
        wzInputChoice8.getSelectionModel().select(control.getProject().getFederalAid());

//        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.getProject().getStep(1).setStepStarted(true);
//            }
//        });

        FontIcon vcWizardLaunchIcon = new FontIcon(FontAwesomeSolid.CHART_LINE);
        vcWizardLaunchIcon.setIconColor(Color.WHITE);
        btnLaunchVCwiz.setGraphic(vcWizardLaunchIcon);
        btnLaunchVCwiz.disableProperty().bind(
                wzInputChoice1.getSelectionModel().selectedIndexProperty().lessThan(0).or(
                wzInputChoice2.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice3.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice4.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice5.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice6.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice7.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputChoice8.getSelectionModel().selectedIndexProperty().lessThan(0))
        );
        BorderPane vcWizardIconWrapper = new BorderPane();
        FontIcon vcWizardCheckIcon = new FontIcon(FontAwesomeRegular.CHECK_CIRCLE);
        vcWizardCheckIcon.setIconColor(Color.FORESTGREEN);
        Tooltip tt1 = new Tooltip("All above inputs must be specified in order to launch the V/C wizard");
        Tooltip.install(vcWizardCheckIcon, tt1);
        FontIcon vcWizardXIcon = new FontIcon(FontAwesomeRegular.TIMES_CIRCLE);
        vcWizardXIcon.setIconColor(Color.FIREBRICK);
        Tooltip tt2 = new Tooltip("All inputs above must be specified in order to launch the V/C wizard");
        Tooltip.install(vcWizardXIcon, tt2);
        btnLaunchVCwiz.disableProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                vcWizardIconWrapper.setCenter(vcWizardXIcon);
            } else {
                vcWizardIconWrapper.setCenter(vcWizardCheckIcon);
            }
        });
        if (btnLaunchVCwiz.isDisable()) {
            vcWizardIconWrapper.setCenter(vcWizardXIcon);
        } else {
            vcWizardIconWrapper.setCenter(vcWizardCheckIcon);
        }

//        VolumeToCapacityWizard vcWiz = new VolumeToCapacityWizard(this.control);
        btnLaunchVCwiz.setOnAction(e -> centerPane.setCenter(new VolumeToCapacityWizard(this.control)));
        BorderPane btnLaunchVCWizWrapper = new BorderPane();
        btnLaunchVCWizWrapper.setCenter(btnLaunchVCwiz);
        int rowIdx = 0;
        GridPane inputGrid = new GridPane();
        inputGrid.add(wzTitleLabel1, 0, rowIdx++, 3, 1);
        inputGrid.add(wzInputLabel1, 0, rowIdx);    // Functional Class
        inputGrid.add(wzInputChoice1, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Functional Class of Roadway", control.getProject().fcrCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel2, 0, rowIdx);    // Maintaining Agency
        inputGrid.add(wzInputChoice3, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Maintaining Agency", control.getProject().maCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel3, 0, rowIdx);    // Area Type
        inputGrid.add(wzInputChoice4, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Area Type", control.getProject().atCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel4, 0, rowIdx);    // AADT
        inputGrid.add(wzInputSpin1, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Average Annual Daily Traffic", control.getProject().aadtCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel5, 0, rowIdx);    // Number of Roadway Lanes
        inputGrid.add(wzInputSpin3, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Number of Roadway Lanes", control.getProject().nrlCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel6, 0, rowIdx);    // left Shoulder Width
        inputGrid.add(wzInputSpin4, 1, rowIdx++);
        inputGrid.add(wzInputLabel21, 0, rowIdx);   // right shoulder width
        inputGrid.add(wzInputSpin10, 1, rowIdx++);

        inputGrid.add(NodeFactory.createCommentLink("Shoulder Width", control.getProject().swCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel7, 0, rowIdx);    // Posted Speed Limit
        inputGrid.add(wzInputSpin5, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Posted Speed Limit", control.getProject().pslCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel8, 0, rowIdx);    // Lane Width
        inputGrid.add(wzInputSpin6, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Lane Width", control.getProject().lwCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel9, 0, rowIdx);    // Part of a signalized corridor
        inputGrid.add(wzInputChoice5, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Part of a Signalized Corridor", control.getProject().scCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel10, 0, rowIdx);   // National highway system
        inputGrid.add(wzInputChoice6, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("National Highway System", control.getProject().nhsCommentProperty()), 2, (rowIdx - 1));

        inputGrid.add(wzTitleLabel2, 0, rowIdx++, 3, 1);
        inputGrid.add(wzInputLabel11, 0, rowIdx);    // Work Zone Length
        inputGrid.add(wzInputSpin2, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Length", control.getProject().wzlCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel12, 0, rowIdx);    // Work Zone Type
        inputGrid.add(wzInputChoice2, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Type", control.getProject().wztCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel13, 0, rowIdx);    // Work Zone Speed Limit
        inputGrid.add(wzInputSpin7, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Speed Limit", control.getProject().wzslCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel14, 0, rowIdx);    // Number of Lanes to be closed
        inputGrid.add(wzInputSpin8, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Number of Lanes to be Closed", control.getProject().nlcCommentProperty()), 2, (rowIdx - 1));
        //inputGrid.add(wzInputLabel15, 0, rowIdx);    // Daily Duration of Activity
        //inputGrid.add(wzInputSpin, 1, rowIdx++);
        //inputGrid.add(wzInputLabel16, 0, rowIdx);    // Work Zone Duration
        //inputGrid.add(wzInputSpin3, 1, rowIdx++);
        //inputGrid.add(wzInputLabel17, 0, rowIdx);    // Mobile work zone duration
        //inputGrid.add(wzInputSpin4, 1, rowIdx++);
        inputGrid.add(wzInputLabel18, 0, rowIdx);    // Reduced Lane Width
        inputGrid.add(wzInputSpin9, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Lane Width", control.getProject().wzlwCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel19, 0, rowIdx);    // Shoulder Closure
        inputGrid.add(wzInputChoice7, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Shoulder Closure", control.getProject().shcCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(wzInputLabel20, 0, rowIdx);    // Federal-Aid Project
        inputGrid.add(wzInputChoice8, 1, rowIdx++);
        inputGrid.add(NodeFactory.createCommentLink("Federal-Aid Project", control.getProject().fapCommentProperty()), 2, (rowIdx - 1));
        inputGrid.add(btnLaunchVCWizWrapper,1,rowIdx);
        inputGrid.add(vcWizardIconWrapper, 2, rowIdx++);

        //inputGrid.add(proceedButton, 1, 9);
        double leftColsplit = 65;
        double rightColSplit = 10;
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColsplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColsplit - rightColSplit);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(rightColSplit);

        inputGrid.getColumnConstraints().addAll(col1, col2, col3);

        GridPane.setHgrow(wzTitleLabel1, Priority.ALWAYS);
        GridPane.setHgrow(wzTitleLabel2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel8, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel9, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel10, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel11, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel12, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel13, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel14, Priority.ALWAYS);
        //GridPane.setHgrow(wzInputLabel15, Priority.ALWAYS);
        //GridPane.setHgrow(wzInputLabel16, Priority.ALWAYS);
        //GridPane.setHgrow(wzInputLabel17, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel18, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel19, Priority.ALWAYS);
        GridPane.setHgrow(wzInputLabel20, Priority.ALWAYS);

        GridPane.setHgrow(wzInputSpin1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin8, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin9, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice8, Priority.ALWAYS);

        return inputGrid;
    }

    private void selectSubStep(int subStepIndex) {
        selectSubStep(subStepIndex, true);
    }

    private void selectSubStep(int subStepIndex, boolean animated) {
        //moveScreen((subStepIndex + 1) * stepIntroGrid.getWidth(), 0, animated);
        changePanel(subStepIndex, animated);
    }

    private void changePanel(int subStepIndex, boolean animated) {
        if (subStepIndex > -2) {
            if (!animated) {
                centerPane.setCenter(this.subStepPanesList.get(subStepIndex + 1));
            } else {
                FadeTransition ft1 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);

                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        centerPane.setCenter(subStepPanesList.get(subStepIndex + 1));
                        FadeTransition ft2 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        }
    }

    private void photoUploaded() {
        if (control.getProject().getProjPhoto() != null) {
            this.genInfoPicLabel.setText("");
            ImageView projImageIV = new ImageView(control.getProject().getProjPhoto());
            projImageIV.setFitHeight(maxProjImagePreviewHeight);
            projImageIV.setFitWidth(maxProjImagePreviewWidth);
            projImageIV.setPreserveRatio(true);
            this.genInfoPicLabel.setGraphic(projImageIV);
        } else {
            this.genInfoPicLabel.setText("Upload from file...");
            this.genInfoPicLabel.setGraphic(null);
        }
    }

    public Node getSummaryNode() {
        return ((TabPane) this.stepReportPane.getCenter()).getSelectionModel().getSelectedItem().getContent();
    }

    public Node getFactSheet1Node() {
        return ((TabPane) this.stepReportPane.getCenter()).getTabs().get(0).getContent();
    }

    public Node getFactSheet2Node() {
        return ((TabPane) this.stepReportPane.getCenter()).getTabs().get(1).getContent();
    }

//    private void moveScreen(double toX, double toY, boolean animated) {
//        if (animated) {
//            TranslateTransition moveMe = new TranslateTransition(Duration.seconds(0.1), allSubStepsPane);
//            moveMe.setToX(-1 * toX);
//            moveMe.setToY(toY);
//            moveMe.play();
//        } else {
//            allSubStepsPane.setTranslateX(-1 * toX);
//        }
//    }

    /**
     * c&p from Spinner
     */
    private <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) {
            return;
        }
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    private final Label genInfoLabel1 = new Label("State Agency");
    private final Label genInfoLabel2 = new Label("Analyst");
    private final Label genInfoLabel3 = new Label("Date");
    private final Label genInfoLabel4 = new Label("Project Name");
    private final Label genInfoLabel5 = new Label("Project Description");
    private final Label genInfoLabel6 = new Label("Project Limits");
    private final Label genInfoLabel7 = new Label("Link to Project Website");
    private final Label genInfoLabel8 = new Label("Upload Project Photo (Optional)");

    private final TextField genInfoTF1 = new TextField("");
    private final TextField genInfoTF2 = new TextField("");
    private final Label genInfoDateToday = new Label();
    private final TextField genInfoTF3 = new TextField("");
    private final TextArea genInfoTA1 = new TextArea("");
    private final TextArea genInfoTA2 = new TextArea("");
    private final TextField genInfoTF4 = new TextField("");
    private final Label genInfoPicLabel = new Label("Select file...");
//    private final Button genInfoButton1 = new Button("Browse");
    private JFXButton genInfoButton1 = new JFXButton("Browse");
    private JFXButton btnCloseDialog = new JFXButton("Close");

    private final GridPane genInfoPicGrid = new GridPane();
    private final int maxProjImagePreviewHeight = 180;
    private final int maxProjImagePreviewWidth = 250;

    // Facility Information and Base Conditions
    private final Label wzTitleLabel1 = new Label("Facility and Base Conditions");
    private final Label wzInputLabel1 = new Label("Functional Class of Roadway:");
    private final Label wzInputLabel2 = new Label("Maintaining Agency:");
    private final Label wzInputLabel3 = new Label("Area Type:");
    private final Label wzInputLabel4 = new Label("Annual Average Daily Traffic (Bidirectional AADT):");
    private final Label wzInputLabel5 = new Label("Number of Roadway Lanes (1 Direction):");
    private final Label wzInputLabel6 = new Label("Left Shoulder Width (ft):");
    private final Label wzInputLabel21 = new Label("Right Shoulder Width (ft):");
    private final Label wzInputLabel7 = new Label("Posted Speed Limit (mph):");
    private final Label wzInputLabel8 = new Label("Lane Width:");
    private final Label wzInputLabel9 = new Label("Part of a Signalized Corridor?");
    private final Label wzInputLabel10 = new Label("On the National Highway System?");

    // Work Zone Confifuration
    private final Label wzTitleLabel2 = new Label("Work Zone Configuration");
    private final Label wzInputLabel11 = new Label("Work Zone Length (mi):");
    private final Label wzInputLabel12 = new Label("Work Zone Type (MUTCD Designation):");
    private final Label wzInputLabel13 = new Label("Work Zone Speed Limit:");
    private final Label wzInputLabel14 = new Label("Number of Lanes to be Closed:");
    //private final Label wzInputLabel15 = new Label("Daily Duration of Activity (hr):");
    //private final Label wzInputLabel16 = new Label("Work Zone Duration (days):");
    //private final Label wzInputLabel17 = new Label("NEED MOBILE DURATION INPUT:");
    private final Label wzInputLabel18 = new Label("Work Zone Lane Width:");
    private final Label wzInputLabel19 = new Label("Shoulder Closure:");
    private final Label wzInputLabel20 = new Label("Federal-Aid Project:");

    //private final Label wzInputLabel1 = new Label("");
    //private final Label wzInputLabel1 = new Label("");
    /**
     * Annual Average Daily Traffic (AADT)(Integer) Input Spinner
     */
    private final Spinner wzInputSpin1 = new Spinner();
    /**
     * Work Zone Length (Float) Input Spinner
     */
    private final Spinner wzInputSpin2 = new Spinner();
    /**
     * Number of Roadway Lanes (1 Direction) (Integer) Input Spinner
     */
    private final Spinner wzInputSpin3 = new Spinner();
    /**
     * Left Shoulder Width (ft) (Float) Input Spinner
     */
    private final Spinner wzInputSpin4 = new Spinner();
    /**
     * Left Shoulder Width (ft) (Float) Input Spinner
     */
    private final Spinner wzInputSpin10 = new Spinner();
    /**
     * Posted Speed Limit (mph) (Integer) Input Spinner
     */
    private final Spinner wzInputSpin5 = new Spinner();
    /**
     * Number of Lanes to be Closed (Integer) Input Spinner
     */
    //private final Spinner wzInputSpin = new Spinner();
    /**
     * Total Work Zone Duration (hr) (Integer) Input Spinner
     */
    //private final Spinner wzInputSpin = new Spinner();
    /**
     * Lane Width (Base Conditions)
     */
    private final Spinner wzInputSpin6 = new Spinner();
    /**
     * Work Zone Speed Limit
     */
    private final Spinner wzInputSpin7 = new Spinner();
    /**
     * Work Zone Number of Lanes to be closed
     */
    private final Spinner wzInputSpin8 = new Spinner();
    /**
     * Work Zone Reduced Lane Width
     */
    private final Spinner wzInputSpin9 = new Spinner();
    /**
     * Functional Class (prev "Select", "Freeway", "Arterial", "Local")
     */
    private final ChoiceBox wzInputChoice1 = new ChoiceBox(FXCollections.observableArrayList(Project.FUNCTIONAL_CLASS_LIST));
    /**
     * Work Zone Type (prev Select", "Mobile", "Permanent")
     */
    private final ChoiceBox wzInputChoice2 = new ChoiceBox(FXCollections.observableArrayList(Project.MUTCD_LIST));
    /**
     * Maintaining Agency
     */
    private final ChoiceBox wzInputChoice3 = new ChoiceBox(FXCollections.observableArrayList("State", "County", "City/Town", "Other"));
    /**
     * Area Type
     */
    private final ChoiceBox wzInputChoice4 = new ChoiceBox(FXCollections.observableArrayList("Urban", "Rural"));
    /**
     * Part of signalized corridor
     */
    private final ChoiceBox wzInputChoice5 = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));
    /**
     * national highway system
     */
    private final ChoiceBox wzInputChoice6 = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));
    /**
     * Has Shoulder Closure
     */
    private final ChoiceBox wzInputChoice7 = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));
    /**
     * Federal-aid project
     */
    private final ChoiceBox wzInputChoice8 = new ChoiceBox(FXCollections.observableArrayList("Yes", "No"));

    /**
     * Button to open the vc wizard from step1panel
     */
    JFXButton btnLaunchVCwiz = new JFXButton("V/C Wizard");
}
