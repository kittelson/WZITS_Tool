/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.*;
import GUI.MainController;
import GUI.Tables.Step1TableHelper;
import com.jfoenix.controls.*;
import core.Project;
import core.Question;
import core.QuestionGenerator;

import java.util.ArrayList;

import core.QuestionYN;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    private final MainController control;

    private final int stepIndex = 0;

    private final BorderPane centerPane = new BorderPane();

    private final SimpleDoubleProperty goalWizProgress = new SimpleDoubleProperty(0.0);


    private final BorderPane goalSelectionPane = new BorderPane();
    private final BorderPane gwSummaryPane = new BorderPane();
    private final BorderPane swSummaryPane = new BorderPane();
    private final BorderPane teamMembersPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList<>();

    public Step1Panel(MainController mc) {

        this.control = mc;

        final VBox mainVBox = new VBox();
        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("1");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);

        startLabel.getStyleClass().add("launch-title-label-top");
        infoLabel.getStyleClass().add("launch-title-label-bottom");

        // -- Deprecated (for now)
////        baseInputAADT = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 400000, control.getProject().getAadt(), 500));
////        wzInputWorkZoneLength = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 50, control.getProject().getWzLength(), 0.1));
////        baseInputNumRoadwayLanes = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, control.getProject().getNumRoadwayLanes(), 1));
////        baseInputLeftShoulderWidth = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, control.getProject().getShoulderWidth(), 0.5));
////        baseInputRightShoulderWidth = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, control.getProject().getShoulderWidth(), 0.5));
////        baseInputPostedSpeedLimit = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, control.getProject().getSpeedLimit(), 5));
////        baseInputLaneWidth = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 25, control.getProject().getLaneWidthBase(), 0.5));
////        wzInputWorkZoneSpeedLimit = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, control.getProject().getWzSpeedLimit(), 5));
////        wzInputWorkZoneNumLanesClosed = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, control.getProject().getNumLanesClosed(), 1));
////        wzInputWorkLaneWidth = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 25, control.getProject().getLaneWidthWZ(), 0.5));
//        baseInputAADT = new Spinner<>(1000, 400000, control.getProject().getAadt(), 500);
//        wzInputWorkZoneLength = new Spinner<>(0.1, 50.0, control.getProject().getWzLength(), 0.1);
//        baseInputNumRoadwayLanes = new Spinner<>(1, 8, control.getProject().getNumRoadwayLanes(), 1);
//        baseInputLeftShoulderWidth = new Spinner<>(0.0, 25.0, control.getProject().getShoulderWidth(), 0.5);
//        baseInputRightShoulderWidth = new Spinner<>(0.0, 25.0, control.getProject().getShoulderWidth(), 0.5);
//        baseInputPostedSpeedLimit = new Spinner<>(5, 100, control.getProject().getSpeedLimit(), 5);
//        baseInputLaneWidth = new Spinner<>(5.0, 25.0, control.getProject().getLaneWidthBase(), 0.5);
//        wzInputWorkZoneSpeedLimit = new Spinner<>(5, 100, control.getProject().getWzSpeedLimit(), 5);
//        wzInputWorkZoneNumLanesClosed = new Spinner<>(0, 8, control.getProject().getNumLanesClosed(), 1);
//        wzInputWorkLaneWidth = new Spinner<>(5.0, 25.0, control.getProject().getLaneWidthWZ(), 0.5);
//        configureSpinnerEditingBehavior();

        // -----------------------------------------------------------------------------------
        baseInputAADT = new JFXIntegerSpinner(1000, 400000, control.getProject().getAadt(), 500);
        wzInputWorkZoneLength = new JFXDoubleSpinner(0.1, 50.0, control.getProject().getWzLength(), 0.1);
        baseInputNumRoadwayLanes = new JFXIntegerSpinner(1, 8, control.getProject().getNumRoadwayLanes(), 1);
        baseInputLeftShoulderWidth = new JFXDoubleSpinner(0.0, 25.0, control.getProject().getShoulderWidth(), 0.5);
        baseInputRightShoulderWidth = new JFXDoubleSpinner(0.0, 25.0, control.getProject().getRightShoulderWidth(), 0.5);
        baseInputPostedSpeedLimit = new JFXIntegerSpinner(5, 100, control.getProject().getSpeedLimit(), 5);
        baseInputLaneWidth = new JFXDoubleSpinner(5.0, 25.0, control.getProject().getLaneWidthBase(), 0.5);
        wzInputWorkZoneSpeedLimit = new JFXIntegerSpinner(5, 100, control.getProject().getWzSpeedLimit(), 5);
        wzInputWorkZoneNumLanesClosed = new JFXIntegerSpinner(0, 8, control.getProject().getNumLanesClosed(), 1);
        wzInputWorkLaneWidth = new JFXDoubleSpinner(5.0, 25.0, control.getProject().getLaneWidthWZ(), 0.5);
        baseInputFunctionalClass.getStyleClass().add("custom-jfx-combo-box");
        baseInputMaintainingAgency.getStyleClass().add("custom-jfx-combo-box");
        baseInputAreaType.getStyleClass().add("custom-jfx-combo-box");
        baseInputSignalizedCorridor.getStyleClass().add("custom-jfx-combo-box");
        baseInputNationalHighwaySystem.getStyleClass().add("custom-jfx-combo-box");
        wzInputWorkZoneType.getStyleClass().add("custom-jfx-combo-box");
        wzInputHasShoulderClosure.getStyleClass().add("custom-jfx-combo-box");
        wzInputFederalAidProject.getStyleClass().add("custom-jfx-combo-box");
        genInfoInputStateAgency.getStyleClass().add("custom-jfx-text-field");
        genInfoInputAnalyst.getStyleClass().add("custom-jfx-text-field");
        genInfoInputProjectName.getStyleClass().add("custom-jfx-text-field");
        genInfoInputProjectWebsite.getStyleClass().add("custom-jfx-text-field");
        genInfoInputProjectDescription.getStyleClass().add("custom-jfx-text-area");
        genInfoInputProjectLimits.getStyleClass().add("custom-jfx-text-area");

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
        figStep1.fitWidthProperty().bind(widthBinding);
        figStep1.fitHeightProperty().bind(heightBinding);
        figStep1.setPreserveRatio(true);
        figStep1.setSmooth(true);
        figStep1.setCache(true);

        Label stepSubtitleLabel = NodeFactory.createFormattedLabel("Assessment of Needs & Feasibility", "intro-instructions");
        stepSubtitleLabel.setAlignment(Pos.CENTER);

        final GridPane stepIntroGrid = new GridPane();
        stepIntroGrid.add(startLabel, 0, 0);
        stepIntroGrid.add(infoLabel, 0, 1);
        stepIntroGrid.add(stepSubtitleLabel, 1, 1);
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

        Label genInfoTitleLabel = new Label("General Information");
        genInfoTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoTitleLabel.getStyleClass().add("launch-title-label-top");
        final GridPane genInfoGrid = createGeneralInfoGrid();
        final BorderPane genInfoPane = new BorderPane();
        genInfoPane.setTop(genInfoTitleLabel);
        genInfoPane.setCenter(genInfoGrid);
        genInfoPane.setBottom(NodeFactory.createFormattedLabel("", "launch-title-label-top"));

        Label wzMetaDataTitleLabel = new Label("Facility and Work Zone Configuration");
        wzMetaDataTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        wzMetaDataTitleLabel.getStyleClass().add("launch-title-label-top");
        final GridPane wzMetaDataGrid = createWZInputGrid();
        final BorderPane wzMetaDataPane = new BorderPane();
        wzMetaDataPane.setTop(wzMetaDataTitleLabel);
        wzMetaDataPane.setCenter(wzMetaDataGrid);
        wzMetaDataPane.setBottom(NodeFactory.createFormattedLabel("", "launch-title-label-top"));

        final GridPane projInfoGrid = new GridPane();
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
        final ProgressIndicatorBar progressBarUserNeeds;
        progressBarUserNeeds = new ProgressIndicatorBar(goalWizProgress, 1.0, "%.0f%%", true);
        progressBarUserNeeds.getStyleClass().add("progress-bar");
        progressBarUserNeeds.setMaxWidth(MainController.MAX_WIDTH);
        //pagination = new Pagination(Step1TableHelper.getPageCount(control.getProject(), Step1TableHelper.GOAL_WIZARD));
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer pageIndex) {
//                return Step1TableHelper.createPageTable(control.getProject(), Step1TableHelper.GOAL_WIZARD, pageIndex, Step1TableHelper.QS_PER_PAGE);
//            }
//        });
        for (Question q : mc.getProject().getGoalWizardQs()) {
            q.responseIdxProperty().addListener((ov, oldVal, newVal) -> updateProgressBar());
        }
        updateProgressBar();
        final TableView<QuestionYN> userNeedsTable = Step1TableHelper.createUserNeeds(control.getProject());
        final VBox userNeedsVBox = new VBox();
        userNeedsVBox.getChildren().addAll(userNeedsTable, progressBarUserNeeds);
        VBox.setVgrow(userNeedsTable, Priority.ALWAYS);
        final BorderPane userNeedsPane = new BorderPane();
        userNeedsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][2], "substep-title-label"));  // "User Needs"
        userNeedsPane.setCenter(userNeedsVBox);
        userNeedsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Major Goal Types Questions Panel
        goalSelectionPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][3], "substep-title-label"));  // "Goals Selection"
        //majorGoalsPane.setCenter(Step1TableHelper.getMajorGoalsTable(control.getProject()));
        goalSelectionPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
        goalSelectionPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Goal Wizard Summary Panel
        gwSummaryPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][4], "substep-title-label"));  // "Goal Wizard Summary"
        gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSelectedGoalsNode());
        gwSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Feasibility Questions Panel
        final BorderPane feasibilityPane = new BorderPane();
        feasibilityPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][5], "substep-title-label"));  // "Feasibility Wizard"
        BorderPane feasibilityWizardMainPane = new BorderPane();
        feasibilityWizardMainPane.setCenter(Step1TableHelper.getFeasibilityWizard(control.getProject()));
        feasibilityWizardMainPane.setBottom(control.getProject().getFeasibilityMatrix().createSummaryPanel(control.getProject()));
        feasibilityPane.setCenter(feasibilityWizardMainPane);
        feasibilityPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholders Questions Panel
        final BorderPane stakeholderPane = new BorderPane();
        stakeholderPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][6], "substep-title-label")); // "Stakeholders"
        stakeholderPane.setCenter(Step1TableHelper.createStakeholderWizard(control.getProject(), control));
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
        final BorderPane itsPane = new BorderPane();
        itsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[0][9], "substep-title-label"));  // "ITS Resources"
        itsPane.setCenter(Step1TableHelper.getITSResourcesPanel(control.getProject()));
        itsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: Assessment of Needs & Feasibility", "substep-title-label"));  //
        stepReportPane.setCenter(Step1TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Adding to main substep VBox
        mainVBox.getChildren().addAll(centerPane);
        subStepPanesList.add(stepIntroGrid);
        subStepPanesList.add(projInfoGrid);
        subStepPanesList.add(userNeedsPane);
        subStepPanesList.add(goalSelectionPane);
        subStepPanesList.add(gwSummaryPane);
        subStepPanesList.add(feasibilityPane);
        subStepPanesList.add(stakeholderPane);
        subStepPanesList.add(swSummaryPane);
        subStepPanesList.add(teamMembersPane);
        subStepPanesList.add(itsPane);
        subStepPanesList.add(stepReportPane);

        centerPane.setCenter(stepIntroGrid);

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(projInfoGrid, Priority.ALWAYS);
        GridPane.setVgrow(userNeedsPane, Priority.ALWAYS);
        GridPane.setVgrow(goalSelectionPane, Priority.ALWAYS);
        GridPane.setVgrow(gwSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(feasibilityPane, Priority.ALWAYS);
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

//    private int getNumSubSteps() {
//        return Project.NUM_SUB_STEPS[stepIndex];
//    }

//    public void setViewWidth(double viewWidth) {
//
//    }

    private void setupPropertyBindings() {

        control.activeStepProperty().addListener((ov, oldVal, newVal) -> {
            if (newVal.intValue() > stepIndex) {
                selectSubStep(Project.NUM_SUB_STEPS[stepIndex], false);
            } else if (newVal.intValue() < stepIndex) {
                selectSubStep(-1, false);
            } else {
                selectSubStep(control.getActiveSubStep(stepIndex));
            }
        });

        control.activeSubStepProperty(stepIndex).addListener((o, oldVal, newVal) -> {
            selectSubStep(getActiveSubStep());
            control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
            control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);
            if (oldVal.intValue() == Project.FEAS_WIZARD_SUMMARY_INDEX && control.getProject().getFeasibilityMatrix().getFeasibility() < 10) {
                JFXDialogLayout content = new JFXDialogLayout();
                Label modalHeading = NodeFactory.createFormattedLabel("Low Project Feasibility Score", "");
                modalHeading.setStyle("-fx-text-fill: " + ColorHelper.WZ_ORANGE);
                modalHeading.setGraphic(NodeFactory.createIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE, Color.web(ColorHelper.WZ_ORANGE), 24));
                modalHeading.setContentDisplay(ContentDisplay.LEFT);
                content.setHeading(modalHeading);
                content.setBody(new Text("The WZITS tool feasibility assessment has indicated that WZITS may not be feasible for your project.\n" +
                        "The tool can continue to be used but the low feasibility score will be indicated in the step and summary reports."));
                JFXDialog dialogAlert = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
                dialogAlert.setStyle("-fx-font-size: 14pt");
                JFXButton btnCloseDialog = new JFXButton("Close");
                btnCloseDialog.setOnAction(actionEvent -> dialogAlert.close());
                content.setActions(btnCloseDialog);
                dialogAlert.setOverlayClose(false);
                dialogAlert.show();
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
        });

        // General Info Bindings
        genInfoInputStateAgency.textProperty().bindBidirectional(control.getProject().agencyProperty());
        genInfoInputAnalyst.textProperty().bindBidirectional(control.getProject().analystProperty());
        genInfoInputProjectName.textProperty().bindBidirectional(control.getProject().nameProperty());
        genInfoInputProjectWebsite.textProperty().bindBidirectional(control.getProject().urlLinkProperty());
        genInfoInputProjectDescription.textProperty().bindBidirectional(control.getProject().descriptionProperty());
        genInfoInputProjectLimits.textProperty().bindBidirectional(control.getProject().limitsProperty());
        photoUploaded();

        // Work zone metadata bindings
//        control.getProject().aadtProperty().bindBidirectional(this.baseInputAADT.getValueFactory().valueProperty());
//        control.getProject().numRoadwayLanesProperty().bindBidirectional(this.baseInputNumRoadwayLanes.getValueFactory().valueProperty());
//        control.getProject().shoulderWidthProperty().bindBidirectional(this.baseInputLeftShoulderWidth.getValueFactory().valueProperty());
//        control.getProject().shoulderWidthProperty().bindBidirectional(this.baseInputRightShoulderWidth.getValueFactory().valueProperty());
//        control.getProject().speedLimitProperty().bindBidirectional(this.baseInputPostedSpeedLimit.getValueFactory().valueProperty());
//        control.getProject().laneWidthBaseProperty().bindBidirectional(this.baseInputLaneWidth.getValueFactory().valueProperty());
//        control.getProject().wzSpeedLimitProperty().bindBidirectional(this.wzInputWorkZoneSpeedLimit.getValueFactory().valueProperty());
//        control.getProject().numLanesClosedProperty().bindBidirectional(this.wzInputWorkZoneNumLanesClosed.getValueFactory().valueProperty());
//        control.getProject().laneWidthWZProperty().bindBidirectional(this.wzInputWorkLaneWidth.getValueFactory().valueProperty());
//        control.getProject().wzLengthProperty().bindBidirectional(this.wzInputWorkZoneLength.getValueFactory().valueProperty());
        control.getProject().aadtProperty().bindBidirectional(this.baseInputAADT.valueProperty());
        control.getProject().numRoadwayLanesProperty().bindBidirectional(this.baseInputNumRoadwayLanes.valueProperty());
        control.getProject().shoulderWidthProperty().bindBidirectional(this.baseInputLeftShoulderWidth.valueProperty());
        control.getProject().rightShoulderWidthProperty().bindBidirectional(this.baseInputRightShoulderWidth.valueProperty());
        control.getProject().speedLimitProperty().bindBidirectional(this.baseInputPostedSpeedLimit.valueProperty());
        control.getProject().laneWidthBaseProperty().bindBidirectional(this.baseInputLaneWidth.valueProperty());
        control.getProject().wzSpeedLimitProperty().bindBidirectional(this.wzInputWorkZoneSpeedLimit.valueProperty());
        control.getProject().numLanesClosedProperty().bindBidirectional(this.wzInputWorkZoneNumLanesClosed.valueProperty());
        control.getProject().laneWidthWZProperty().bindBidirectional(this.wzInputWorkLaneWidth.valueProperty());
        control.getProject().wzLengthProperty().bindBidirectional(this.wzInputWorkZoneLength.valueProperty());


        control.getProject().functionalClassProperty().bindBidirectional(this.baseInputFunctionalClass.valueProperty());
        control.getProject().wzTypeProperty().bindBidirectional(this.wzInputWorkZoneType.valueProperty());
        control.getProject().maintainingAgencyProperty().bindBidirectional(this.baseInputMaintainingAgency.valueProperty());
        control.getProject().areaTypeProperty().bindBidirectional(this.baseInputAreaType.valueProperty());
        control.getProject().signalizedCorridorProperty().bindBidirectional(this.baseInputSignalizedCorridor.valueProperty());
        control.getProject().nationalHighwaySystemProperty().bindBidirectional(this.baseInputNationalHighwaySystem.valueProperty());
        control.getProject().shoulderClosureProperty().bindBidirectional(this.wzInputHasShoulderClosure.valueProperty());
        control.getProject().federalAidProperty().bindBidirectional(this.wzInputFederalAidProject.valueProperty());

        control.getProject().laneWidthBaseProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getLaneWidthBase() > control.getProject().getLaneWidthWZ()) {
                control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsNo(true);
            }
        });
        control.getProject().laneWidthWZProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getLaneWidthBase() > control.getProject().getLaneWidthWZ()) {
                control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().reducedLaneWidthQ.setAnswerIsNo(true);
            }
        });

        control.getProject().shoulderWidthProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getShoulderWidth() == 0) {
                control.getProject().getQGen().emergencyShoulderQ.setAnswerIsNo(true);
                //control.getProject().getQGen().shoulderQIdx.setAnswerIsNo(true);
            }
        });
        control.getProject().shoulderClosureProperty().addListener((ChangeListener<Object>) (ov, oldVal, newVal) -> {
            if (control.getProject().getShoulderClosure() != null && control.getProject().getShoulderClosure().equalsIgnoreCase("yes")) {
                control.getProject().getQGen().emergencyShoulderQ.setAnswerIsYes(true);
                //control.getProject().getQGen().shoulderQIdx.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().emergencyShoulderQ.setAnswerIsNo(true);
                //control.getProject().getQGen().shoulderQIdx.setAnswerIsNo(true);
            }
        });
        control.getProject().speedLimitProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getSpeedLimit() > control.getProject().getWzSpeedLimit()) {
                control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsNo(true);
            }
        });
        control.getProject().wzSpeedLimitProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getSpeedLimit() > control.getProject().getWzSpeedLimit()) {
                control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().loweredSpeedLimitsQ.setAnswerIsNo(true);
            }
        });

        control.getProject().functionalClassProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getFunctionalClass() == null || !(control.getProject().getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[0]) || control.getProject().getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[1]))) {
                control.getProject().getQGen().disableRampMetersQ.setAnswerIsNo(true);
            }
        });
        control.getProject().federalAidProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getFederalAid() != null && control.getProject().getFederalAid().equalsIgnoreCase("yes")) {
                control.getProject().getQGen().biannualProcessReviewQ.setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().biannualProcessReviewQ.setAnswerIsNo(true);
            }
        });

        control.getProject().wzTypeProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getWzType() == null || control.getProject().getWzType().equalsIgnoreCase(Project.MUTCD_LIST[0])) {
                control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setLocked(false);
//                if (control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).getAnswer() == 3) {
//                    control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setAnswer("< 4 Months");
//                }
            } else {
                control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setAnswer(3);
                control.getProject().getQGen().qFeasOptionList.get(QuestionGenerator.Q_FEAS_LONG_TERM_DURATION).setLocked(true);
            }
        });

        control.getProject().signalizedCorridorProperty().addListener((ov, oldVal, newVal) -> {
            if (control.getProject().getSignalizedCorridor() != null && control.getProject().getSignalizedCorridor().equalsIgnoreCase("yes")) {
                control.getProject().getQGen().qStakeholderYNList.get(control.getProject().getQGen().signalizedCorridorQIdx).setAnswerIsYes(true);
            } else {
                control.getProject().getQGen().qStakeholderYNList.get(control.getProject().getQGen().signalizedCorridorQIdx).setAnswerIsNo(true);
            }
        });

    }

    private GridPane createGeneralInfoGrid() {

        genInfoDateToday.getStyleClass().add("wz-input-label-style");
        genInfoDateToday.setText(control.getProject().getDateString());

        genInfoInputProjectDescription.setWrapText(true);
        genInfoInputProjectLimits.setWrapText(true);


        final JFXButton uploadProjectPhotoButton = new JFXButton("Browse");
        uploadProjectPhotoButton.getStyleClass().add("jfx-button-style");
        uploadProjectPhotoButton.setMaxWidth(125);
        uploadProjectPhotoButton.setOnAction(actionEvent -> {
            boolean success = IOHelper.getProjectImage(control);
            if (success) {
                photoUploaded();
            }
        });

        genInfoPicLabel.getStyleClass().add("wz-input-label-style");
        genInfoPicGrid.add(genInfoPicLabel, 0, 0);
        genInfoPicGrid.add(uploadProjectPhotoButton, 1, 0);
        genInfoPicLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoPicGrid.getColumnConstraints().add(0, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        genInfoPicGrid.getColumnConstraints().add(1, new ColumnConstraints(75, 75, 75, Priority.NEVER, HPos.RIGHT, true));
        genInfoPicGrid.setMaxHeight(IMAGE_PREVIEW_MAX_HEIGHT);

        GridPane grid = new GridPane();
        int ri = 0;
        grid.setVgap(10);
        grid.add(NodeFactory.createFormattedLabel("State Agency", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputStateAgency, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Analyst", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputAnalyst, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Date", "gen-info-label-style"), 0, ri);
        grid.add(genInfoDateToday, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Project Name", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputProjectName, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Project Description", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputProjectDescription, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Project Limits", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputProjectLimits, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Link to Project Website", "gen-info-label-style"), 0, ri);
        grid.add(genInfoInputProjectWebsite, 1, ri);
        ri++;
        grid.add(NodeFactory.createFormattedLabel("Upload Project Photo (Optional)", "gen-info-label-style"), 0, ri);
        grid.add(genInfoPicGrid, 1, ri);

        double leftColSplit = 40;
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColSplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColSplit);

        grid.getColumnConstraints().addAll(col1, col2);

        return grid;
    }

    private GridPane createWZInputGrid() {

        baseInputFunctionalClass.setMaxWidth(MainController.MAX_WIDTH);
        wzInputWorkZoneType.setMaxWidth(MainController.MAX_WIDTH);
        baseInputMaintainingAgency.setMaxWidth(MainController.MAX_WIDTH);
        baseInputAreaType.setMaxWidth(MainController.MAX_WIDTH);
        baseInputSignalizedCorridor.setMaxWidth(MainController.MAX_WIDTH);
        baseInputNationalHighwaySystem.setMaxWidth(MainController.MAX_WIDTH);
        wzInputHasShoulderClosure.setMaxWidth(MainController.MAX_WIDTH);
        wzInputFederalAidProject.setMaxWidth(MainController.MAX_WIDTH);

        baseInputAADT.setMaxWidth(MainController.MAX_WIDTH);
        wzInputWorkZoneLength.setMaxWidth(MainController.MAX_WIDTH);
        baseInputNumRoadwayLanes.setMaxWidth(MainController.MAX_WIDTH);
        baseInputLeftShoulderWidth.setMaxWidth(MainController.MAX_WIDTH);
        baseInputRightShoulderWidth.setMaxWidth(MainController.MAX_WIDTH);
        baseInputPostedSpeedLimit.setMaxWidth(MainController.MAX_WIDTH);
        baseInputLaneWidth.setMaxWidth(MainController.MAX_WIDTH);
        wzInputWorkZoneSpeedLimit.setMaxWidth(MainController.MAX_WIDTH);
        wzInputWorkZoneNumLanesClosed.setMaxWidth(MainController.MAX_WIDTH);
        wzInputWorkLaneWidth.setMaxWidth(MainController.MAX_WIDTH);

        baseInputFunctionalClass.getSelectionModel().select(control.getProject().getFunctionalClass());
        wzInputWorkZoneType.getSelectionModel().select(control.getProject().getWzType());
        baseInputMaintainingAgency.getSelectionModel().select(control.getProject().getMaintainingAgency());
        baseInputAreaType.getSelectionModel().select(control.getProject().getAreaType());
        baseInputSignalizedCorridor.getSelectionModel().select(control.getProject().getSignalizedCorridor());
        baseInputNationalHighwaySystem.getSelectionModel().select(control.getProject().getNationalHighwaySystem());
        wzInputHasShoulderClosure.getSelectionModel().select(control.getProject().getShoulderClosure());
        wzInputFederalAidProject.getSelectionModel().select(control.getProject().getFederalAid());

//        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.getProject().getStep(1).setStepStarted(true);
//            }
//        });

        FontIcon vcWizardLaunchIcon = new FontIcon(FontAwesomeSolid.CHART_LINE);
        vcWizardLaunchIcon.setIconColor(Color.WHITE);

        JFXButton btnLaunchVCwiz = new JFXButton("Launch V/C Wizard");
        btnLaunchVCwiz.getStyleClass().add("jfx-button-style");
        btnLaunchVCwiz.setGraphic(vcWizardLaunchIcon);
        btnLaunchVCwiz.disableProperty().bind(
                baseInputFunctionalClass.getSelectionModel().selectedIndexProperty().lessThan(0).or(
                wzInputWorkZoneType.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                baseInputMaintainingAgency.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                baseInputAreaType.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                baseInputSignalizedCorridor.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                baseInputNationalHighwaySystem.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputHasShoulderClosure.getSelectionModel().selectedIndexProperty().lessThan(0)).or(
                wzInputFederalAidProject.getSelectionModel().selectedIndexProperty().lessThan(0))
        );
        BorderPane vcWizardIconWrapper = new BorderPane();
        FontIcon vcWizardCheckIcon = NodeFactory.createIcon(FontAwesomeRegular.CHECK_CIRCLE, Color.FORESTGREEN, 20);
        Tooltip tt1 = new Tooltip("Please complete all inputs before launching the V/C Wizard");
        tt1.setStyle("-fx-font-size: 12pt");
        tt1.setShowDelay(Duration.ZERO);
        Tooltip.install(vcWizardCheckIcon, tt1);
        FontIcon vcWizardXIcon = NodeFactory.createIcon(FontAwesomeRegular.TIMES_CIRCLE, Color.FIREBRICK, 20);
        Tooltip tt2 = new Tooltip("Please complete all inputs before launching the V/C Wizard");
        tt2.setStyle("-fx-font-size: 12pt");
        tt2.setShowDelay(Duration.ZERO);
        Tooltip.install(vcWizardXIcon, tt2);
        final Tooltip vcButtonTooltip = new Tooltip();
        vcButtonTooltip.setText("Please complete all inputs before launching the V/C Wizard.");
        Tooltip.install(btnLaunchVCwiz, vcButtonTooltip);
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
        btnLaunchVCwiz.setOnAction(e -> {
            centerPane.setCenter(new VolumeToCapacityWizard(this.control, centerPane.getCenter()));
//            control.disableFlowBar();
        });
        BorderPane btnLaunchVCWizWrapper = new BorderPane();
        btnLaunchVCWizWrapper.setCenter(btnLaunchVCwiz);
        int rowIdx = 0;
        GridPane inputGrid = new GridPane();
        inputGrid.setVgap(10);
        inputGrid.add(NodeFactory.createFormattedLabel("Facility and Base Conditions", "wz-input-title-style"), 0, rowIdx, 4, 1);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Functional Class of Roadway:", "wz-input-label-style"), 0, rowIdx);    // Functional Class
        inputGrid.add(baseInputFunctionalClass, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().fcrCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Functional Class of Roadway", control.getProject().fcrCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Maintaining Agency:", "wz-input-label-style", "Who owns the roadway?", true), 0, rowIdx);    // Maintaining Agency
        inputGrid.add(baseInputMaintainingAgency, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().maCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Maintaining Agency", control.getProject().maCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Area Type:", "wz-input-label-style"), 0, rowIdx);    // Area Type
        inputGrid.add(baseInputAreaType, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().atCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Area Type", control.getProject().atCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Annual Average Daily Traffic:", "wz-input-label-style", "Bidirectional AADT", true), 0, rowIdx);    // AADT
        inputGrid.add(baseInputAADT, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().aadtCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Average Annual Daily Traffic", control.getProject().aadtCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Number of Roadway Lanes (1 Direction):", "wz-input-label-style"), 0, rowIdx);    // Number of Roadway Lanes
        inputGrid.add(baseInputNumRoadwayLanes, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().nrlCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Number of Roadway Lanes", control.getProject().nrlCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Left Shoulder Width (ft):", "wz-input-label-style"), 0, rowIdx);    // left Shoulder Width
        inputGrid.add(baseInputLeftShoulderWidth, 1, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Right Shoulder Width (ft):", "wz-input-label-style"), 0, rowIdx);   // right shoulder width
        inputGrid.add(baseInputRightShoulderWidth, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().swCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Shoulder Width", control.getProject().swCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Posted Speed Limit (mph):", "wz-input-label-style"), 0, rowIdx);    // Posted Speed Limit
        inputGrid.add(baseInputPostedSpeedLimit, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().pslCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Posted Speed Limit", control.getProject().pslCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Lane Width:", "wz-input-label-style"), 0, rowIdx);    // Lane Width
        inputGrid.add(baseInputLaneWidth, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().lwCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Lane Width", control.getProject().lwCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Part of a Signalized Corridor?", "wz-input-label-style"), 0, rowIdx);    // Part of a signalized corridor
        inputGrid.add(baseInputSignalizedCorridor, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().scCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Part of a Signalized Corridor", control.getProject().scCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("On the National Highway System?", "wz-input-label-style"), 0, rowIdx);   // National highway system
        inputGrid.add(baseInputNationalHighwaySystem, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().nhsCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("National Highway System", control.getProject().nhsCommentProperty()), 3, rowIdx);
        rowIdx++;

        inputGrid.add(NodeFactory.createFormattedLabel("Work Zone Configuration", "wz-input-title-style"), 0, rowIdx, 4, 1);
        rowIdx++;
        inputGrid.add(
                NodeFactory.createFormattedLabel("Work Zone Length (mi):", "wz-input-label-style",
                "The length in miles of the physical limits of the work zone work activities.",
                true), 0, rowIdx);    // Work Zone Length
        inputGrid.add(wzInputWorkZoneLength, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().wzlCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Length", control.getProject().wzlCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Work Zone Type (MUTCD Designation):", "wz-input-label-style",
                "MUTCD Designations (2009, MUTCD 6G.02)\n\n" +
                        "1) Long-term stationary - Work that occupies a location more than 3 days.\n" +
                        "2) Intermediate-term stationary - Work that occupies a location more than one daylight period up to 3 days, or night work lasting more than 1 hour.\n" +
                        "3) Short-term stationary - Daytime work lasting more than 1 hour within a single daylight period.\n" +
                        "4) Short duration - Work that occupies a location up to 1 hour.\n" +
                        "5) Mobile - Work that moves intermittently or continuously.\n",
                true), 0, rowIdx);    // Work Zone Type
        inputGrid.add(wzInputWorkZoneType, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().wztCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Type", control.getProject().wztCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Work Zone Speed Limit:", "wz-input-label-style"), 0, rowIdx);    // Work Zone Speed Limit
        inputGrid.add(wzInputWorkZoneSpeedLimit, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().wzslCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Speed Limit", control.getProject().wzslCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Number of Lanes to be Closed:", "wz-input-label-style", "Maximum number of lanes to close during the duration of the project.", true), 0, rowIdx);    // Number of Lanes to be closed
        inputGrid.add(wzInputWorkZoneNumLanesClosed, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().nlcCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Number of Lanes to be Closed", control.getProject().nlcCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Work Zone Lane Width:", "wz-input-label-style"), 0, rowIdx);    // Reduced Lane Width
        inputGrid.add(wzInputWorkLaneWidth, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().wzlwCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Work Zone Lane Width", control.getProject().wzlwCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Shoulder Closure:", "wz-input-label-style"), 0, rowIdx);    // Shoulder Closure
        inputGrid.add(wzInputHasShoulderClosure, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().shcCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Shoulder Closure", control.getProject().shcCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(NodeFactory.createFormattedLabel("Federal-Aid Project:", "wz-input-label-style"), 0, rowIdx);    // Federal-Aid Project
        inputGrid.add(wzInputFederalAidProject, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().fapCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Federal-Aid Project", control.getProject().fapCommentProperty()), 3, rowIdx);
        rowIdx++;
        inputGrid.add(btnLaunchVCWizWrapper,0,rowIdx);
        inputGrid.add(vcWizardIconWrapper, 1, rowIdx);
        inputGrid.add(NodeFactory.createCommentStatusIcon(control.getProject().vcWizardCommentProperty()), 2, rowIdx);
        inputGrid.add(NodeFactory.createCommentLink("Volume-to-Capacity Wizard", control.getProject().vcWizardCommentProperty()), 3, rowIdx);
        GridPane.setMargin(btnLaunchVCWizWrapper, new Insets(0, 0, 10, 0));
        //inputGrid.add(proceedButton, 1, 9);
//        double leftColsplit = 65;
//        double rightColSplit = 10;
        ColumnConstraints col1 = new ColumnConstraints();
//        col1.setPercentWidth(leftColsplit);
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
//        col2.setPercentWidth(100 - leftColsplit - rightColSplit);
        col2.setMinWidth(200);
        col2.setMaxWidth(200);
        ColumnConstraints col3 = new ColumnConstraints();
//        col3.setPercentWidth(rightColSplit);
        col3.setMinWidth(20);
        col3.setMaxWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setMinWidth(80);
        col4.setMaxWidth(80);

        inputGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

//        GridPane.setHgrow(wzTitleLabel1, Priority.ALWAYS);
//        GridPane.setHgrow(wzTitleLabel2, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel1, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel3, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel4, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel5, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel6, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel7, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel8, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel9, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel10, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel11, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel13, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel18, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel19, Priority.ALWAYS);
//        GridPane.setHgrow(wzInputLabel20, Priority.ALWAYS);

        GridPane.setHgrow(baseInputAADT, Priority.ALWAYS);
        GridPane.setHgrow(wzInputWorkZoneLength, Priority.ALWAYS);
        GridPane.setHgrow(baseInputNumRoadwayLanes, Priority.ALWAYS);
        GridPane.setHgrow(baseInputLeftShoulderWidth, Priority.ALWAYS);
        GridPane.setHgrow(baseInputPostedSpeedLimit, Priority.ALWAYS);
        GridPane.setHgrow(baseInputLaneWidth, Priority.ALWAYS);
        GridPane.setHgrow(wzInputWorkZoneSpeedLimit, Priority.ALWAYS);
        GridPane.setHgrow(wzInputWorkZoneNumLanesClosed, Priority.ALWAYS);
        GridPane.setHgrow(wzInputWorkLaneWidth, Priority.ALWAYS);
        GridPane.setHgrow(baseInputFunctionalClass, Priority.ALWAYS);
        GridPane.setHgrow(wzInputWorkZoneType, Priority.ALWAYS);
        GridPane.setHgrow(baseInputMaintainingAgency, Priority.ALWAYS);
        GridPane.setHgrow(baseInputAreaType, Priority.ALWAYS);
        GridPane.setHgrow(baseInputSignalizedCorridor, Priority.ALWAYS);
        GridPane.setHgrow(baseInputNationalHighwaySystem, Priority.ALWAYS);
        GridPane.setHgrow(wzInputHasShoulderClosure, Priority.ALWAYS);
        GridPane.setHgrow(wzInputFederalAidProject, Priority.ALWAYS);

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

                ft1.setOnFinished(actionEvent -> {
                    centerPane.setCenter(subStepPanesList.get(subStepIndex + 1));
                    FadeTransition ft2 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                });
            }
        }
    }

    private void photoUploaded() {
        if (control.getProject().getProjPhoto() != null) {
            this.genInfoPicLabel.setText("");
            ImageView projImageIV = new ImageView(control.getProject().getProjPhoto());
            projImageIV.setFitHeight(IMAGE_PREVIEW_MAX_HEIGHT);
            projImageIV.setFitWidth(IMAGE_PREVIEW_MAX_WIDTH);
            projImageIV.setPreserveRatio(true);
            this.genInfoPicLabel.setGraphic(projImageIV);
        } else {
            this.genInfoPicLabel.setText("Upload from file...");
            this.genInfoPicLabel.setGraphic(null);
        }
    }

//    public Node getSummaryNode() {
//        return ((TabPane) this.stepReportPane.getCenter()).getSelectionModel().getSelectedItem().getContent();
//    }

    public Node getFactSheet1Node() {
        return ((TabPane) this.stepReportPane.getCenter()).getTabs().get(0).getContent();
    }

    public Node getFactSheet2Node() {
        return ((TabPane) this.stepReportPane.getCenter()).getTabs().get(1).getContent();
    }

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

//    private void configureSpinnerEditingBehavior() {
//        baseInputAADT.setEditable(true);
//        baseInputAADT.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputAADT);
//        });
//        baseInputAADT.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputAADT.getValueFactory().setValue(oldVal);
//            }
//        }));
//        wzInputWorkZoneLength.setEditable(true);
//        wzInputWorkZoneLength.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(wzInputWorkZoneLength);
//        });
//        wzInputWorkZoneLength.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                wzInputWorkZoneLength.getValueFactory().setValue(oldVal);
//            }
//        }));
//        baseInputNumRoadwayLanes.setEditable(true);
//        baseInputNumRoadwayLanes.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputNumRoadwayLanes);
//        });
//        baseInputNumRoadwayLanes.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputNumRoadwayLanes.getValueFactory().setValue(oldVal);
//            }
//        }));
//        baseInputLeftShoulderWidth.setEditable(true);
//        baseInputLeftShoulderWidth.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputLeftShoulderWidth);
//        });
//        baseInputLeftShoulderWidth.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputLeftShoulderWidth.getValueFactory().setValue(oldVal);
//            }
//        }));
//        baseInputRightShoulderWidth.setEditable(true);
//        baseInputRightShoulderWidth.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputRightShoulderWidth);
//        });
//        baseInputRightShoulderWidth.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputRightShoulderWidth.getValueFactory().setValue(oldVal);
//            }
//        }));
//        baseInputPostedSpeedLimit.setEditable(true);
//        baseInputPostedSpeedLimit.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputPostedSpeedLimit);
//        });
//        baseInputPostedSpeedLimit.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputPostedSpeedLimit.getValueFactory().setValue(oldVal);
//            }
//        }));
//        baseInputLaneWidth.setEditable(true);
//        baseInputLaneWidth.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(baseInputLaneWidth);
//        });
//        baseInputLaneWidth.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                baseInputLaneWidth.getValueFactory().setValue(oldVal);
//            }
//        }));
//        wzInputWorkZoneSpeedLimit.setEditable(true);
//        wzInputWorkZoneSpeedLimit.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(wzInputWorkZoneSpeedLimit);
//        });
//        wzInputWorkZoneSpeedLimit.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                wzInputWorkZoneSpeedLimit.getValueFactory().setValue(oldVal);
//            }
//        }));
//        wzInputWorkZoneNumLanesClosed.setEditable(true);
//        wzInputWorkZoneNumLanesClosed.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(wzInputWorkZoneNumLanesClosed);
//        });
//        wzInputWorkZoneNumLanesClosed.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                wzInputWorkZoneNumLanesClosed.getValueFactory().setValue(oldVal);
//            }
//        }));
//        wzInputWorkLaneWidth.setEditable(true);
//        wzInputWorkLaneWidth.focusedProperty().addListener((s, ov, nv) -> {
//            if (nv) {
//                return;
//            }
//            commitEditorText(wzInputWorkLaneWidth);
//        });
//        wzInputWorkLaneWidth.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
//            if (newVal == null) {
//                wzInputWorkLaneWidth.getValueFactory().setValue(oldVal);
//            }
//        }));
//    }

    private static final int IMAGE_PREVIEW_MAX_HEIGHT = 180;
    private static final int IMAGE_PREVIEW_MAX_WIDTH = 250;

//    private final TextField genInfoInputStateAgency = new TextField("");
//    private final TextField genInfoInputAnalyst = new TextField("");
//    private final TextField genInfoInputProjectName = new TextField("");
//    private final TextArea genInfoInputProjectDescription = new TextArea("");
//    private final TextArea genInfoInputProjectLimits = new TextArea("");
//    private final TextField genInfoInputProjectWebsite = new TextField("");
    private final JFXTextField genInfoInputStateAgency = new JFXTextField("");
    private final JFXTextField genInfoInputAnalyst = new JFXTextField("");
    private final JFXTextField genInfoInputProjectName = new JFXTextField("");
    private final JFXTextArea genInfoInputProjectDescription = new JFXTextArea("");
    private final JFXTextArea genInfoInputProjectLimits = new JFXTextArea("");
    private final JFXTextField genInfoInputProjectWebsite = new JFXTextField("");

    private final Label genInfoDateToday = new Label();
    private final Label genInfoPicLabel = new Label();

    private final GridPane genInfoPicGrid = new GridPane();
//    /**
//     * Annual Average Daily Traffic (AADT)(Integer) Input Spinner
//     */
//    private final Spinner baseInputAADT;
//    /**
//     * Work Zone Length (Float) Input Spinner
//     */
//    private final Spinner wzInputWorkZoneLength;
//    /**
//     * Number of Roadway Lanes (1 Direction) (Integer) Input Spinner
//     */
//    private final Spinner baseInputNumRoadwayLanes;
//    /**
//     * Left Shoulder Width (ft) (Float) Input Spinner
//     */
//    private final Spinner baseInputLeftShoulderWidth;
//    /**
//     * Right Shoulder Width (ft) (Float) Input Spinner
//     */
//    private final Spinner baseInputRightShoulderWidth;
//    /**
//     * Posted Speed Limit (mph) (Integer) Input Spinner
//     */
//    private final Spinner baseInputPostedSpeedLimit;
//    /**
//     * Lane Width (Base Conditions)
//     */
//    private final Spinner baseInputLaneWidth;
//    /**
//     * Work Zone Speed Limit
//     */
//    private final Spinner wzInputWorkZoneSpeedLimit;
//    /**
//     * Work Zone Number of Lanes to be closed
//     */
//    private final Spinner wzInputWorkZoneNumLanesClosed;
//    /**
//     * Work Zone Reduced Lane Width
//     */
//    private final Spinner wzInputWorkLaneWidth;
//    /**
//     * Functional Class (prev "Select", "Freeway", "Arterial", "Local")
//     */
//    private final ChoiceBox<String> baseInputFunctionalClass = new ChoiceBox<>(FXCollections.observableArrayList(Project.FUNCTIONAL_CLASS_LIST));
//    /**
//     * Work Zone Type (prev Select", "Mobile", "Permanent")
//     */
//    private final ChoiceBox<String> wzInputWorkZoneType = new ChoiceBox<>(FXCollections.observableArrayList(Project.MUTCD_LIST));
//    /**
//     * Maintaining Agency
//     */
//    private final ChoiceBox<String> baseInputMaintainingAgency = new ChoiceBox<>(FXCollections.observableArrayList("State", "County", "City/Town", "Other"));
//    /**
//     * Area Type
//     */
//    private final ChoiceBox<String> baseInputAreaType = new ChoiceBox<>(FXCollections.observableArrayList("Urban", "Rural"));
//    /**
//     * Part of signalized corridor
//     */
//    private final ChoiceBox<String> baseInputSignalizedCorridor = new ChoiceBox<>(FXCollections.observableArrayList("Yes", "No"));
//    /**
//     * national highway system
//     */
//    private final ChoiceBox<String> baseInputNationalHighwaySystem = new ChoiceBox<>(FXCollections.observableArrayList("Yes", "No"));
//    /**
//     * Has Shoulder Closure
//     */
//    private final ChoiceBox<String> wzInputHasShoulderClosure = new ChoiceBox<>(FXCollections.observableArrayList("Yes", "No"));
//    /**
//     * Federal-aid project
//     */
//    private final ChoiceBox<String> wzInputFederalAidProject = new ChoiceBox<>(FXCollections.observableArrayList("Yes", "No"));

    // ----------------------------------------------------------------------------------------------------------------------------
    /**
     * Annual Average Daily Traffic (AADT)(Integer) Input Spinner
     */
    private final JFXIntegerSpinner baseInputAADT;
    /**
     * Work Zone Length (Float) Input Spinner
     */
    private final JFXDoubleSpinner wzInputWorkZoneLength;
    /**
     * Number of Roadway Lanes (1 Direction) (Integer) Input Spinner
     */
    private final JFXIntegerSpinner baseInputNumRoadwayLanes;
    /**
     * Left Shoulder Width (ft) (Float) Input Spinner
     */
    private final JFXDoubleSpinner baseInputLeftShoulderWidth;
    /**
     * Right Shoulder Width (ft) (Float) Input Spinner
     */
    private final JFXDoubleSpinner baseInputRightShoulderWidth;
    /**
     * Posted Speed Limit (mph) (Integer) Input Spinner
     */
    private final JFXIntegerSpinner baseInputPostedSpeedLimit;
    /**
     * Lane Width (Base Conditions)
     */
    private final JFXDoubleSpinner baseInputLaneWidth;
    /**
     * Work Zone Speed Limit
     */
    private final JFXIntegerSpinner wzInputWorkZoneSpeedLimit;
    /**
     * Work Zone Number of Lanes to be closed
     */
    private final JFXIntegerSpinner wzInputWorkZoneNumLanesClosed;
    /**
     * Work Zone Reduced Lane Width
     */
    private final JFXDoubleSpinner wzInputWorkLaneWidth;
    /**
     * Functional Class (prev "Select", "Freeway", "Arterial", "Local")
     */
    private final JFXComboBox<String> baseInputFunctionalClass = new JFXComboBox<>(FXCollections.observableArrayList(Project.FUNCTIONAL_CLASS_LIST));
    /**
     * Work Zone Type (prev Select", "Mobile", "Permanent")
     */
    private final JFXComboBox<String> wzInputWorkZoneType = new JFXComboBox<>(FXCollections.observableArrayList(Project.MUTCD_LIST));
    /**
     * Maintaining Agency
     */
    private final JFXComboBox<String> baseInputMaintainingAgency = new JFXComboBox<>(FXCollections.observableArrayList("State", "County", "City/Town", "Other"));
    /**
     * Area Type
     */
    private final JFXComboBox<String> baseInputAreaType = new JFXComboBox<>(FXCollections.observableArrayList("Urban", "Rural"));
    /**
     * Part of signalized corridor
     */
    private final JFXComboBox<String> baseInputSignalizedCorridor = new JFXComboBox<>(FXCollections.observableArrayList("Yes", "No"));
    /**
     * national highway system
     */
    private final JFXComboBox<String> baseInputNationalHighwaySystem = new JFXComboBox<>(FXCollections.observableArrayList("Yes", "No"));
    /**
     * Has Shoulder Closure
     */
    private final JFXComboBox<String> wzInputHasShoulderClosure = new JFXComboBox<>(FXCollections.observableArrayList("Yes", "No"));
    /**
     * Federal-aid project
     */
    private final JFXComboBox<String> wzInputFederalAidProject = new JFXComboBox<>(FXCollections.observableArrayList("Yes", "No"));
}
