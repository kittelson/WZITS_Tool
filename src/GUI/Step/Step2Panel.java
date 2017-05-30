/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IOHelper;
import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.Tables.Step2TableHelper;
import core.Project;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step2Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 1;

    private final String stepTitle = "Concept Development and Feasibility";

    private final VBox mainVBox = new VBox();

    //private final GridPane allSubStepsPane = new GridPane();
    private final BorderPane centerPane = new BorderPane();

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane applicationPane = new BorderPane();
    private final BorderPane awSummaryPane = new BorderPane();
    private final BorderPane benefitsPane = new BorderPane();
    private final BorderPane costPane = new BorderPane();
    private final BorderPane instJurPane = new BorderPane();
    private final BorderPane legalPane = new BorderPane();
    private final BorderPane stakeholderBuyInPane = new BorderPane();
    private final BorderPane conOpsPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();

    public Step2Panel(MainController mc) {

        this.control = mc;

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setWrapText(true);
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setTextAlignment(TextAlignment.CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("2");
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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_2);
        //figStep1.setFitWidth(1500);
        figStep.fitWidthProperty().bind(widthBinding);
        figStep.fitHeightProperty().bind(heightBinding);
        figStep.setPreserveRatio(true);
        figStep.setSmooth(true);
        figStep.setCache(true);

        stepIntroGrid.add(startLabel, 0, 0);
        stepIntroGrid.add(infoLabel, 0, 1);
        stepIntroGrid.add(instructionLabel, 1, 1);
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
        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        int subStepTitleIndex = 1;
        // Initial Applications Questions Panel
        applicationPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        TableView tv1 = Step2TableHelper.createApplicationWizard1(control.getProject());
        TableView tv2 = Step2TableHelper.createApplicationWizard2(control.getProject());
        GridPane gPane = new GridPane();
        gPane.add(tv1, 0, 0);
        gPane.add(tv2, 0, 1);
        RowConstraints rc1 = new RowConstraints();
        rc1.setPercentHeight(50);
        RowConstraints rc2 = new RowConstraints();
        rc2.setPercentHeight(50);
        gPane.getRowConstraints().addAll(rc1, rc2);
        GridPane.setHgrow(tv1, Priority.ALWAYS);
        GridPane.setHgrow(tv2, Priority.ALWAYS);
        applicationPane.setCenter(gPane);
        applicationPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Application Wizard Summary Panel
        awSummaryPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        awSummaryPane.setCenter(control.getProject().getApplicationMatrix().getSummaryNode());
        awSummaryPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Benefits Questions Panel
        benefitsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        benefitsPane.setCenter(Step2TableHelper.createBenefitsNode(control.getProject()));
        benefitsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Costs Questions Panel
        costPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        costPane.setCenter(Step2TableHelper.createCostsNode(control.getProject()));
        costPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Institutional/Jurisdictional Questions Panel
        this.instJurPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        instJurPane.setCenter(Step2TableHelper.createInstJurNode(control.getProject()));
        instJurPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Legal/Policy Questions Panel
        legalPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        legalPane.setCenter(Step2TableHelper.createLegalNode(control.getProject()));
        legalPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Stakeholder Buy-in Questions Panel
        stakeholderBuyInPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        stakeholderBuyInPane.setCenter(Step2TableHelper.createStakeholderBuyInNode(control.getProject()));
        stakeholderBuyInPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Develop Concept of Operations Questions Panel
        conOpsPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepTitleIndex++], "substep-title-label"));
        //conOpsPane.setCenter(Step2TableHelper.createConOpsNode(control.getProject()));
        BorderPane uploadPane = new BorderPane();
        Button uploadButton = new Button("Upload Concept of Operations Figure");
        uploadButton.setStyle("-fx-font-size: 24; -fx-text-wrap: true;");
        BorderPane.setAlignment(uploadButton, Pos.CENTER);
        final ImageView conOpsIV = new ImageView();
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                Image conOpsImage = IOHelper.openImage(control);
                if (conOpsImage != null) {
                    conOpsIV.setImage(conOpsImage);
                }
            }
        });
        uploadPane.setTop(uploadButton);
        uploadPane.setCenter(conOpsIV);
        conOpsPane.setCenter(uploadPane);
        conOpsPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));
        stepReportPane.setCenter(Step2TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        mainVBox.getChildren().addAll(centerPane);  //allSubStepsPane
//        allSubStepsPane.add(stepIntroGrid, 0, 0);
//        allSubStepsPane.add(applicationPane, 1, 0);
//        allSubStepsPane.add(awSummaryPane, 2, 0);
//        allSubStepsPane.add(benefitsPane, 3, 0);
//        allSubStepsPane.add(costPane, 4, 0);
//        allSubStepsPane.add(instJurPane, 5, 0);
//        allSubStepsPane.add(legalPane, 6, 0);
//        allSubStepsPane.add(stakeholderBuyInPane, 7, 0);
//        allSubStepsPane.add(conOpsPane, 8, 0);
//        allSubStepsPane.add(stepReportPane, 9, 0);
        this.subStepPanesList.add(stepIntroGrid);
        this.subStepPanesList.add(applicationPane);
        this.subStepPanesList.add(awSummaryPane);
        this.subStepPanesList.add(benefitsPane);
        this.subStepPanesList.add(costPane);
        this.subStepPanesList.add(instJurPane);
        this.subStepPanesList.add(legalPane);
        this.subStepPanesList.add(stakeholderBuyInPane);
        this.subStepPanesList.add(conOpsPane);
        this.subStepPanesList.add(stepReportPane);

        centerPane.setCenter(stepIntroGrid);

//        int numPanes = getNumSubSteps() + 2;
//        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(applicationPane, Priority.ALWAYS);
        GridPane.setVgrow(awSummaryPane, Priority.ALWAYS);
        GridPane.setVgrow(benefitsPane, Priority.ALWAYS);
        GridPane.setVgrow(costPane, Priority.ALWAYS);
        GridPane.setVgrow(instJurPane, Priority.ALWAYS);
        GridPane.setVgrow(legalPane, Priority.ALWAYS);
        GridPane.setVgrow(stakeholderBuyInPane, Priority.ALWAYS);
        GridPane.setVgrow(conOpsPane, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(centerPane, Priority.ALWAYS);
        this.setCenter(mainVBox);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {

    }

    private void setupPropertyBindings() {
//        this.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
//                //System.out.println("Step 2 Width Resized");
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

                switch (getActiveSubStep()) {
                    case Project.APP_WIZARD_SUMMARY_INDEX:
                        awSummaryPane.setCenter(control.getProject().getApplicationMatrix().getSummaryNode());
                        break;
                }

                if (getActiveSubStep() == Project.NUM_SUB_STEPS[stepIndex]) {
                    stepReportPane.setCenter(Step2TableHelper.createStepSummary(control));
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
}
