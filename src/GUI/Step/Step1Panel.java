/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.ColorHelper;
import GUI.Helper.NodeFactory;
import GUI.Helper.IconHelper;
import GUI.MainController;
import GUI.Tables.Step1TableHelper;
import core.Question;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import GUI.Helper.ProgressIndicatorBar;
import core.Project;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step1Panel extends BorderPane {

    //private final int numSubSteps = 4;
    private final MainController control;

    private final int stepIndex = 0;

    private final String stepTitle = "Assessment of Needs";

    private final VBox mainVBox = new VBox();

    private final GridPane allSubStepsPane = new GridPane();

    private final VBox pVBox = new VBox();

    private final Pagination pagination;

    private final ProgressIndicatorBar pbGW;

    private final SimpleDoubleProperty goalWizProgress = new SimpleDoubleProperty(0.0);

    private final GridPane stepIntroGrid = new GridPane();
    private final GridPane projInfoGrid = new GridPane();
    private final BorderPane genInfoPane = new BorderPane();
    private final GridPane genInfoGrid;
    private final BorderPane wzMetaDataPane = new BorderPane();
    private final GridPane wzMetaDataGrid;
    private final BorderPane unPane = new BorderPane();
    private final BorderPane unSuppPane = new BorderPane();
    private final BorderPane majorGoalsPane = new BorderPane();
    private final BorderPane gwSummaryPane = new BorderPane();
    private final BorderPane feasibilityPane = new BorderPane();
    private final BorderPane fwSummaryPane = new BorderPane();
    private final BorderPane stakeholderPane = new BorderPane();
    private final BorderPane swSummaryPane = new BorderPane();
    private final BorderPane teamMembersPane = new BorderPane();
    private final BorderPane itsPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();

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
                return (widthProperty().get() / (Project.NUM_SUB_STEPS[0] + 2) - 150); // 0.9
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

        Label wzMetaDataTitleLabel = new Label("Work Zone Meta Data");
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
        pagination = new Pagination(Step1TableHelper.getPageCount(control.getProject(), Step1TableHelper.GOAL_WIZARD));
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return Step1TableHelper.createPageTable(control.getProject(), Step1TableHelper.GOAL_WIZARD, pageIndex, Step1TableHelper.QS_PER_PAGE);
            }
        });
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setStyle(
                "-fx-accent: " + ColorHelper.WZ_ORANGE + "; "
                + "-fx-background-color: " + ColorHelper.WZ_LIGHT_GREY + "; "
        );
        for (Question q : mc.getProject().getGoalWizardQs()) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    updateProgressBar();
                }
            });
        }
        pVBox.getChildren().addAll(pagination, pbGW);
        unPane.setTop(NodeFactory.createFormattedLabel("User Needs", "substep-title-label"));
        unPane.setCenter(pVBox);
        unPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // User Needs Supplemental Questions Panel
        unSuppPane.setTop(NodeFactory.createFormattedLabel("User Needs Supplemental Questions", "substep-title-label"));
        unSuppPane.setCenter(Step1TableHelper.createUserNeedsSupplemental(control.getProject()));
        unSuppPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Major Goal Types Questions Panel
        majorGoalsPane.setTop(NodeFactory.createFormattedLabel("Major Goals", "substep-title-label"));
        majorGoalsPane.setCenter(Step1TableHelper.getMajorGoalsTable(control.getProject()));
        majorGoalsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Goal Wizard Summary Panel
        gwSummaryPane.setTop(NodeFactory.createFormattedLabel("Goal Wizard Summary", "substep-title-label"));
        gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
        gwSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Feasibility Questions Panel
        feasibilityPane.setTop(NodeFactory.createFormattedLabel("Feasibility", "substep-title-label"));
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

        // Feasibility Wizard panel
        fwSummaryPane.setTop(NodeFactory.createFormattedLabel("Feasibility Wizard Summary", "substep-title-label"));
        BorderPane fwbp2 = new BorderPane();
        fwSummaryPane.setCenter(fwbp2);
        fwSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholders Questions Panel
        stakeholderPane.setTop(NodeFactory.createFormattedLabel("Stakeholders", "substep-title-label"));
        stakeholderPane.setCenter(Step1TableHelper.createStakeholderWizard(control.getProject()));
        stakeholderPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholder Wizard panel
        swSummaryPane.setTop(NodeFactory.createFormattedLabel("Stakeholder Wizard Summary", "substep-title-label"));
        swSummaryPane.setCenter(control.getProject().getStakeholderMatrix().createSummaryTable());
        swSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Team Members and Stakeholders Panel
        teamMembersPane.setTop(NodeFactory.createFormattedLabel("Selected Core Team Members and Stakeholders", "substep-title-label"));
        teamMembersPane.setCenter(control.getProject().getStakeholderMatrix().createSelectedMembersPanel());
        teamMembersPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // ITS Panel
        itsPane.setTop(NodeFactory.createFormattedLabel("ITS Resources", "substep-title-label"));
        itsPane.setCenter(Step1TableHelper.getITSResourcesPanel(control.getProject()));
        itsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));
        stepReportPane.setCenter(new BorderPane());
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Adding to main substep VBox
        mainVBox.getChildren().addAll(allSubStepsPane);
        allSubStepsPane.add(stepIntroGrid, 0, 0);
        allSubStepsPane.add(projInfoGrid, 1, 0);
        allSubStepsPane.add(unPane, 2, 0);
        allSubStepsPane.add(unSuppPane, 3, 0);
        allSubStepsPane.add(majorGoalsPane, 4, 0);
        allSubStepsPane.add(gwSummaryPane, 5, 0);
        allSubStepsPane.add(feasibilityPane, 6, 0);
        allSubStepsPane.add(fwSummaryPane, 7, 0);
        allSubStepsPane.add(stakeholderPane, 8, 0);
        allSubStepsPane.add(swSummaryPane, 9, 0);
        allSubStepsPane.add(teamMembersPane, 10, 0);
        allSubStepsPane.add(itsPane, 11, 0);
        allSubStepsPane.add(stepReportPane, 12, 0);

        int numPanes = getNumSubSteps() + 2;
        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
            ColumnConstraints tcc = new ColumnConstraints();
            tcc.setPercentWidth(100.0 / numPanes);
            allSubStepsPane.getColumnConstraints().add(tcc);
        }

        pagination.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(pagination, Priority.ALWAYS);

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(projInfoGrid, Priority.ALWAYS);
        GridPane.setVgrow(unPane, Priority.ALWAYS);
        GridPane.setVgrow(unSuppPane, Priority.ALWAYS);
        GridPane.setVgrow(majorGoalsPane, Priority.ALWAYS);
        GridPane.setVgrow(gwSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(feasibilityPane, Priority.ALWAYS);
        GridPane.setVgrow(fwSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(stakeholderPane, Priority.ALWAYS);
        GridPane.setVgrow(swSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(allSubStepsPane, Priority.ALWAYS);
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
        if (allSubStepsPane != null) {
            allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
            allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
            moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
        }
    }

    private void setupPropertyBindings() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
                System.out.println("Step 1 Width Resized");
                if (allSubStepsPane != null && allSubStepsPane.isVisible()) {
                    allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
                    allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
                    moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
                }
            }
        });

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

        control.activeSubStepProperty(stepIndex).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                selectSubStep(getActiveSubStep());
                control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
                control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);

                switch (getActiveSubStep()) {
                    case Project.GOAL_WIZARD_SUMMARY_INDEX:
                        gwSummaryPane.setCenter(control.getProject().getGoalNeedsMatrix().createSummaryTable());
                        break;
                    case Project.FEAS_WIZARD_SUMMARY_INDEX:
                        fwSummaryPane.setCenter(control.getProject().getFeasibilityMatrix().createSummaryPanel());
                        break;
                    case Project.STAKEHOLDER_WIZARD_SUMMARY_INDEX:
                        swSummaryPane.setCenter(control.getProject().getStakeholderMatrix().createSummaryTable());
                        break;
                    case Project.TEAM_SUMMARY_INDEX:
                        teamMembersPane.setCenter(control.getProject().getStakeholderMatrix().createSelectedMembersPanel());
                        break;
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

        // Work zone metadata bindings
        control.getProject().aadtProperty().bindBidirectional(this.wzInputSpin1.getValueFactory().valueProperty());
        control.getProject().functionalClassProperty().bindBidirectional(this.wzInputChoice1.valueProperty());
        control.getProject().wzLengthProperty().bindBidirectional(this.wzInputSpin2.getValueFactory().valueProperty());
        control.getProject().wzTypeProperty().bindBidirectional(this.wzInputChoice2.valueProperty());
        control.getProject().maintainingAgencyProperty().bindBidirectional(this.wzInputChoice3.valueProperty());
        control.getProject().numRoadwayLanesProperty().bindBidirectional(this.wzInputSpin3.getValueFactory().valueProperty());
        control.getProject().shoulderWidthProperty().bindBidirectional(this.wzInputSpin4.getValueFactory().valueProperty());
        control.getProject().speedLimitProperty().bindBidirectional(this.wzInputSpin5.getValueFactory().valueProperty());
        control.getProject().numLanesClosedProperty().bindBidirectional(this.wzInputSpin6.getValueFactory().valueProperty());
        control.getProject().activityDurationProperty().bindBidirectional(this.wzInputSpin7.getValueFactory().valueProperty());
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

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        genInfoDateToday.setText(df.format(today));

        genInfoTA1.setWrapText(true);
        genInfoTA2.setWrapText(true);

        genInfoPicGrid.add(genInfoPicLabel, 0, 0);
        genInfoPicGrid.add(genInfoButton1, 1, 0);
        genInfoButton1.setMaxWidth(125);
        genInfoButton1.setDisable(true);
        genInfoPicLabel.setMaxWidth(MainController.MAX_WIDTH);
        genInfoPicGrid.getColumnConstraints().add(0, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        genInfoPicGrid.getColumnConstraints().add(1, new ColumnConstraints(75, 75, 75, Priority.NEVER, HPos.RIGHT, true));

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

        wzInputLabel1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel3.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel4.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel5.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel6.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel7.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel8.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel9.setMaxWidth(MainController.MAX_WIDTH);
        wzInputLabel10.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice1.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice2.setMaxWidth(MainController.MAX_WIDTH);
        wzInputChoice3.setMaxWidth(MainController.MAX_WIDTH);

        wzInputLabel1.getStyleClass().add("wz-input-label-style");
        wzInputLabel2.getStyleClass().add("wz-input-label-style");
        wzInputLabel3.getStyleClass().add("wz-input-label-style");
        wzInputLabel4.getStyleClass().add("wz-input-label-style");
        wzInputLabel5.getStyleClass().add("wz-input-label-style");
        wzInputLabel6.getStyleClass().add("wz-input-label-style");
        wzInputLabel7.getStyleClass().add("wz-input-label-style");
        wzInputLabel8.getStyleClass().add("wz-input-label-style");
        wzInputLabel9.getStyleClass().add("wz-input-label-style");
        wzInputLabel10.getStyleClass().add("wz-input-label-style");
        //wzInputChoice1.getStyleClass().add("wzits-choice-box");
        //wzInputChoice2.getStyleClass().add("wzits-choice-box");
        //wzInputChoice3.getStyleClass().add("wzits-choice-box");

        wzInputSpin1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(25000, 125000, control.getProject().getAadt(), 5000));
        wzInputSpin2.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 50, control.getProject().getWzLength(), 0.1));
        wzInputSpin3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 8, control.getProject().getNumRoadwayLanes(), 1));
        wzInputSpin4.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 25, control.getProject().getShoulderWidth(), 0.5));
        wzInputSpin5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, control.getProject().getSpeedLimit(), 5));
        wzInputSpin6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, control.getProject().getNumLanesClosed(), 1));
        wzInputSpin7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, control.getProject().getActivityDuration(), 1));

        wzInputChoice1.getSelectionModel().select(control.getProject().getFunctionalClass());
        wzInputChoice2.getSelectionModel().select(control.getProject().getWzType());
        wzInputChoice3.getSelectionModel().select(control.getProject().getMaintainingAgency());

//        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.getProject().getStep(1).setStepStarted(true);
//            }
//        });
        int colIdx = 0;
        GridPane inputGrid = new GridPane();
        inputGrid.add(wzInputLabel2, 0, colIdx);
        inputGrid.add(wzInputChoice1, 1, colIdx++);
        inputGrid.add(wzInputLabel4, 0, colIdx);
        inputGrid.add(wzInputChoice2, 1, colIdx++);
        inputGrid.add(wzInputLabel10, 0, colIdx);
        inputGrid.add(wzInputChoice3, 1, colIdx++);
        inputGrid.add(wzInputLabel1, 0, colIdx);
        inputGrid.add(wzInputSpin1, 1, colIdx++);
        inputGrid.add(wzInputLabel3, 0, colIdx);
        inputGrid.add(wzInputSpin2, 1, colIdx++);
        inputGrid.add(wzInputLabel5, 0, colIdx);
        inputGrid.add(wzInputSpin3, 1, colIdx++);
        inputGrid.add(wzInputLabel6, 0, colIdx);
        inputGrid.add(wzInputSpin4, 1, colIdx++);
        inputGrid.add(wzInputLabel7, 0, colIdx);
        inputGrid.add(wzInputSpin5, 1, colIdx++);
        inputGrid.add(wzInputLabel8, 0, colIdx);
        inputGrid.add(wzInputSpin6, 1, colIdx++);
        inputGrid.add(wzInputLabel9, 0, colIdx);
        inputGrid.add(wzInputSpin7, 1, colIdx++);

        //inputGrid.add(proceedButton, 1, 9);
        double leftColsplit = 65;
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(leftColsplit);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(100 - leftColsplit);

        inputGrid.getColumnConstraints().addAll(col1, col2);

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

        GridPane.setHgrow(wzInputSpin1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin3, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin4, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin5, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin6, Priority.ALWAYS);
        GridPane.setHgrow(wzInputSpin7, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice1, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice2, Priority.ALWAYS);
        GridPane.setHgrow(wzInputChoice3, Priority.ALWAYS);

        return inputGrid;
    }

    private void selectSubStep(int subStepIndex) {
        selectSubStep(subStepIndex, true);
    }

    private void selectSubStep(int subStepIndex, boolean animated) {
        moveScreen((subStepIndex + 1) * stepIntroGrid.getWidth(), 0, animated);
    }

    private void moveScreen(double toX, double toY) {
        moveScreen(toX, toY, true);
    }

    private void moveScreen(double toX, double toY, boolean animated) {
        if (animated) {
            TranslateTransition moveMe = new TranslateTransition(Duration.seconds(0.1), allSubStepsPane);
            moveMe.setToX(-1 * toX);
            moveMe.setToY(toY);
            moveMe.play();
        } else {
            allSubStepsPane.setTranslateX(-1 * toX);
        }
    }

    private final Label genInfoLabel1 = new Label("State Agency");
    private final Label genInfoLabel2 = new Label("Analyst");
    private final Label genInfoLabel3 = new Label("Date");
    private final Label genInfoLabel4 = new Label("Project Name");
    private final Label genInfoLabel5 = new Label("Project Description");
    private final Label genInfoLabel6 = new Label("Project Limits");
    private final Label genInfoLabel7 = new Label("Link to Project Website");
    private final Label genInfoLabel8 = new Label("Upload Photo");

    private final TextField genInfoTF1 = new TextField("");
    private final TextField genInfoTF2 = new TextField("");
    private final Label genInfoDateToday = new Label();
    private final TextField genInfoTF3 = new TextField("");
    private final TextArea genInfoTA1 = new TextArea("");
    private final TextArea genInfoTA2 = new TextArea("");
    private final TextField genInfoTF4 = new TextField("");
    private final Label genInfoPicLabel = new Label("Select file...");
    private final Button genInfoButton1 = new Button("Browse");
    private final GridPane genInfoPicGrid = new GridPane();

    private final Label wzInputLabel1 = new Label("Annual Average Daily Traffic (AADT):");
    private final Label wzInputLabel2 = new Label("Functional Class of Roadway:");
    private final Label wzInputLabel3 = new Label("Work Zone Length (mi):");
    private final Label wzInputLabel4 = new Label("Work Zone Type:");
    private final Label wzInputLabel5 = new Label("Number of Roadway Lanes (1 Direction):");
    private final Label wzInputLabel6 = new Label("Shoulder Width (ft):");
    private final Label wzInputLabel7 = new Label("Posted Speed Limit (mph):");
    private final Label wzInputLabel8 = new Label("Number of Lanes to be Closed:");
    private final Label wzInputLabel9 = new Label("Duration of Activity (hr):");
    private final Label wzInputLabel10 = new Label("Maintaining Agency:");

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
     * Shoulder Width (ft) (Float) Input Spinner
     */
    private final Spinner wzInputSpin4 = new Spinner();
    /**
     * Posted Speed Limit (mph) (Integer) Input Spinner
     */
    private final Spinner wzInputSpin5 = new Spinner();
    /**
     * Number of Lanes to be Closed (Integer) Input Spinner
     */
    private final Spinner wzInputSpin6 = new Spinner();
    /**
     * Duration of Activity (hr) (Integer) Input Spinner
     */
    private final Spinner wzInputSpin7 = new Spinner();

    private final ChoiceBox wzInputChoice1 = new ChoiceBox(FXCollections.observableArrayList("Select", "Freeway", "Arterial", "Local"));
    private final ChoiceBox wzInputChoice2 = new ChoiceBox(FXCollections.observableArrayList("Select", "Mobile", "Permanent"));
    private final ChoiceBox wzInputChoice3 = new ChoiceBox(FXCollections.observableArrayList("Select", "State", "County", "City/Town", "Other"));

}
