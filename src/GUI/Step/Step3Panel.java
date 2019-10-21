/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.Tables.Step3TableHelper;
import core.Project;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step3Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 2;

//    private final String stepTitle = "Detailed System Planning and Design";

    private final VBox mainVBox = new VBox();

    //private final GridPane allSubStepsPane = new GridPane();
    private final BorderPane centerPane = new BorderPane();

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane conOpsPane = new BorderPane();
    private final BorderPane sysReqPane = new BorderPane();
    //private final BorderPane sysDesignPane = new BorderPane();
    private final BorderPane testingStratPane = new BorderPane();
    private final BorderPane opsMaintPane = new BorderPane();
    private final BorderPane staffTrainingPane = new BorderPane();
    private final BorderPane sysSecurityPane = new BorderPane();
    private final BorderPane projEvalPane = new BorderPane();
    private final BorderPane sysBCPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();
    // all panes below this comment are new panes associated with the new step 3-6 redesign
    BorderPane rootPane = new BorderPane();
    GridPane topGridLabel = new GridPane();
    GridPane bottomGridNav = new GridPane();
    Pane activeQuesPane = null;
    GridPane topGridMaster = new GridPane();
    GridPane quesGrid = new GridPane();
    GridPane questions = new GridPane();
    // labels and inputs associated with new redesign
//    Separator topSep = new Separator();
    Label lblTitle = new Label("Step 3");
    Label lblStepName = new Label("System Planning and Design");


    //labels for questions
    // Navigation bar captions / Hyperlinks
    final static String[] captions = new String[]{
            "Document Concepts of Operations", "Requirements", "Test Strategy", "Operations & Maintenance","Staff Training & Needs",
            "System Security", "Evaluation", "Benefit / Cost"
    };
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length];
    //Hashmap to help map caption strings to specific pane
    HashMap<Integer, Pane> hash_map = new HashMap<Integer, Pane>();

    public Step3Panel(MainController control) {

        Pane docConOps = Step3TableHelper.createConOpsNode(control.getProject());
        Pane sysReqNode = Step3TableHelper.createSysReqNode(control.getProject());
        Pane testingStratNode = Step3TableHelper.createTestingStratNode(control.getProject());
        Pane opsMaintNode = Step3TableHelper.createOpsMaintNode(control.getProject());
        Pane staffTrainNode = Step3TableHelper.createStaffTrainingNode(control.getProject());
        Pane sysSecurityNode = Step3TableHelper.createSysSecurityNode(control.getProject());
        Pane projEvaluationNode = Step3TableHelper.createProjEvalNode(control.getProject());
        Pane sysBcNode = Step3TableHelper.createSysBCNode(control.getProject());
        //mapping Pane to Int keys
        hash_map.put(0,docConOps);
        hash_map.put(1,sysReqNode);
        hash_map.put(2,testingStratNode);
        hash_map.put(3,opsMaintNode);
        hash_map.put(4, staffTrainNode);
        hash_map.put(5,sysSecurityNode);
        hash_map.put(6, projEvaluationNode);
        hash_map.put(7, sysBcNode);
        this.control = control;

        for (int i = 0; i < captions.length; i++) {
            btnCaptions[i] = new Hyperlink(captions[i]);
            int finalI = i;
            btnCaptions[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (activeQuesPane != null) {
                        quesGrid.getChildren().remove(activeQuesPane);
                    }
                    quesGrid.add(hash_map.get(finalI),0,0);
                    activeQuesPane = hash_map.get(finalI);
                    quesGrid.setMaxWidth(1400);
                }
            });
            }
        //spacing for panes
        bottomGridNav.setPadding(new Insets(10,0,10,50));
        topGridMaster.setMaxWidth(1400);
        topGridMaster.setPrefWidth(500);
        topGridMaster.setMinWidth(450);
        //setup the grid labels and buttons
        bottomGridNav.getStyleClass().add("label-styles");
        lblTitle.getStyleClass().add("comment-label-style");
        lblStepName.getStyleClass().add("comment-sub-label");
        topGridMaster.getStyleClass().add("comment-border-styles");
        questions.getStyleClass().add("question-gridLeft-styles");
        lblTitle.setMaxWidth(Integer.MAX_VALUE);
        lblStepName.setMaxWidth(Integer.MAX_VALUE);
        lblTitle.setAlignment(Pos.CENTER);
        lblStepName.setAlignment(Pos.CENTER);
        GridPane.setHgrow(lblStepName, Priority.ALWAYS);
        GridPane.setHgrow(lblTitle, Priority.ALWAYS);
        GridPane.setHalignment(lblTitle, HPos.RIGHT);
        topGridLabel.add(lblTitle,0,0);
        topGridLabel.add(lblStepName,0,1);

        for (int i = 0; i < btnCaptions.length; i++) {
            btnCaptions[i].setMaxWidth(Integer.MAX_VALUE);
            GridPane.setHgrow(btnCaptions[i], Priority.ALWAYS);
            bottomGridNav.add(btnCaptions[i],i,0);
        }
        GridPane.setHgrow(topGridLabel, Priority.ALWAYS);
        GridPane.setHgrow(bottomGridNav, Priority.ALWAYS);
        topGridMaster.add(topGridLabel,0,0);
        topGridMaster.add(bottomGridNav,0,1);
        GridPane.setHgrow(topGridMaster,Priority.ALWAYS);
        rootPane.setTop(topGridMaster);

        GridPane.setHgrow(sysReqNode, Priority.ALWAYS);
        GridPane.setHgrow(docConOps,Priority.ALWAYS);
        GridPane.setHgrow(testingStratNode, Priority.ALWAYS);
        GridPane.setHgrow(opsMaintNode,Priority.ALWAYS);
        GridPane.setHgrow(staffTrainNode,Priority.ALWAYS);
        sysReqNode.setMaxHeight(500);
        docConOps.setMaxHeight(500);
        testingStratNode.setMaxHeight(500);
        opsMaintNode.setMaxHeight(500);
        staffTrainNode.setMaxHeight(500);
        sysSecurityNode.setMaxHeight(500);
        projEvaluationNode.setMaxHeight(500);
        sysBcNode.setMaxHeight(500);
        //adding questions to gridLeft
//        quesGridRight.add(Step3TableHelper.createSysReqNode(control.getProject()),0,0);
//        mainVBox.setFillWidth(true);
//        Label startLabel = new Label("Step");
//        startLabel.setWrapText(true);
//        startLabel.setAlignment(Pos.BOTTOM_CENTER);
//        startLabel.setTextAlignment(TextAlignment.CENTER);
//        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
//        startLabel.setMaxWidth(MainController.MAX_WIDTH);
//        Label infoLabel = new Label("3");
//        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
//        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
//        infoLabel.setAlignment(Pos.TOP_CENTER);
//        Label instructionLabel = new Label(stepTitle);
//        instructionLabel.setWrapText(true);
//        instructionLabel.setTextAlignment(TextAlignment.CENTER);
//        instructionLabel.setMaxHeight(MainController.MAX_HEIGHT);
//        instructionLabel.setMaxWidth(MainController.MAX_WIDTH);
//        instructionLabel.setAlignment(Pos.CENTER);
//        startLabel.getStyleClass().add("launch-title-label-top");
//        infoLabel.getStyleClass().add("launch-title-label-bottom");
//        instructionLabel.getStyleClass().add("intro-instructions");

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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_3);
        //figStep1.setFitWidth(1500);
        figStep.fitWidthProperty().bind(widthBinding);
        figStep.fitHeightProperty().bind(heightBinding);
        figStep.setPreserveRatio(true);
        figStep.setSmooth(true);
        figStep.setCache(true);

//        stepIntroGrid.add(startLabel, 0, 0);
//        stepIntroGrid.add(infoLabel, 0, 1);
//        stepIntroGrid.add(instructionLabel, 1, 1);
        stepIntroGrid.add(figStep, 1, 0);
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
//        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        int subStepIndex = 1;
        // Initial Applications Questions Panel
        conOpsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        conOpsPane.setCenter(Step3TableHelper.createConOpsNode(control.getProject()));
        conOpsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Application Wizard Summary Panel
        sysReqPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        sysReqPane.setCenter(Step3TableHelper.createSysReqNode(control.getProject()));
        sysReqPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Benefits Questions Panel
        //sysDesignPane.setTop(NodeFactory.createFormattedLabel("User Needs Supplemental Questions", "substep-title-label"));
        //sysDesignPane.setCenter(Step3TableHelper.createSysDesignNode(control.getProject()));
        //sysDesignPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
        // Costs Questions Panel
        testingStratPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        testingStratPane.setCenter(Step3TableHelper.createTestingStratNode(control.getProject()));
        testingStratPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Institutional/Jurisdictional Questions Panel
        this.opsMaintPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        opsMaintPane.setCenter(Step3TableHelper.createOpsMaintNode(control.getProject()));
        opsMaintPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Legal/Policy Questions Panel
        staffTrainingPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        staffTrainingPane.setCenter(Step3TableHelper.createStaffTrainingNode(control.getProject()));
        staffTrainingPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholder Buy-in Questions Panel
        sysSecurityPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        sysSecurityPane.setCenter(Step3TableHelper.createSysSecurityNode(control.getProject()));
        sysSecurityPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Develop Concept of Operations Questions Panel
        projEvalPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        projEvalPane.setCenter(Step3TableHelper.createProjEvalNode(control.getProject()));
        projEvalPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Develop Concept of Operations Questions Panel
        sysBCPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        sysBCPane.setCenter(Step3TableHelper.createSysBCNode(control.getProject()));
        sysBCPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
//        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));
        stepReportPane.setCenter(Step3TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        int i = 0;
//        mainVBox.getChildren().addAll(centerPane); // allSubStepsPane
//        allSubStepsPane.add(stepIntroGrid, i++, 0);
//        allSubStepsPane.add(conOpsPane, i++, 0);
//        allSubStepsPane.add(sysReqPane, i++, 0);
//        //allSubStepsPane.add(sysDesignPane, colIdx++, 0);
//        allSubStepsPane.add(testingStratPane, i++, 0);
//        allSubStepsPane.add(opsMaintPane, i++, 0);
//        allSubStepsPane.add(staffTrainingPane, i++, 0);
//        allSubStepsPane.add(sysSecurityPane, i++, 0);
//        allSubStepsPane.add(projEvalPane, i++, 0);
//        allSubStepsPane.add(sysBCPane, i++, 0);
//        allSubStepsPane.add(stepReportPane, i++, 0);
//        this.subStepPanesList.add(stepIntroGrid);
//        this.subStepPanesList.add(conOpsPane);
//        this.subStepPanesList.add(sysReqPane);
//        this.subStepPanesList.add(testingStratPane);
//        this.subStepPanesList.add(opsMaintPane);
//        this.subStepPanesList.add(staffTrainingPane);
//        this.subStepPanesList.add(sysSecurityPane);
//        this.subStepPanesList.add(projEvalPane);
//        this.subStepPanesList.add(sysBCPane);
//        this.subStepPanesList.add(stepReportPane);
//
//        centerPane.setCenter(stepIntroGrid);

//        int numPanes = getNumSubSteps() + 2;
//        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(conOpsPane, Priority.ALWAYS);
        GridPane.setVgrow(sysReqPane, Priority.ALWAYS);
        //GridPane.setVgrow(sysDesignPane, Priority.ALWAYS);
        GridPane.setVgrow(testingStratPane, Priority.ALWAYS);
        GridPane.setVgrow(opsMaintPane, Priority.ALWAYS);
        GridPane.setVgrow(staffTrainingPane, Priority.ALWAYS);
        GridPane.setVgrow(sysSecurityPane, Priority.ALWAYS);
        GridPane.setVgrow(projEvalPane, Priority.ALWAYS);
        GridPane.setVgrow(sysBCPane, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(centerPane, Priority.ALWAYS); //allSubStepsPane
        this.setTop(topGridMaster);
        this.setCenter(quesGrid);
        BorderPane.setAlignment(topGridMaster, Pos.CENTER);
        BorderPane.setAlignment(quesGrid, Pos.CENTER);
        quesGrid.getStyleClass().add("question-grid-main");

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {

    }

    private void setupPropertyBindings() {
//        this.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
//                //System.out.println("Step 3 Width Resized");
//                if (allSubStepsPane != null && allSubStepsPane.isVisible()) {
//                    allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
//                    allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
//                    moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
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

        control.activeSubStepProperty(stepIndex).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                selectSubStep(getActiveSubStep());
                control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
                control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);

                if (getActiveSubStep() == Project.NUM_SUB_STEPS[stepIndex]) {
                    stepReportPane.setCenter(Step3TableHelper.createStepSummary(control));
                }

                control.checkProceed();
            }
        });
    }

    private int getActiveSubStep() {
        return control.getActiveSubStep(stepIndex);
    }

    private int getNumSubSteps() {
        return Project.NUM_SUB_STEPS[stepIndex];
    }

    public void setViewWidth(double viewWidth) {
//        if (allSubStepsPane != null) {
//            allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
//            allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
//            moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
//        }
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
    public Node getFactSheet5Node() {
        return this.stepReportPane.getCenter();
    }
}
