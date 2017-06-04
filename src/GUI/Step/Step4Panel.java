/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.Tables.Step4TableHelper;
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
import javafx.scene.control.Label;
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
public class Step4Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 3;

    private final String stepTitle = "Procurement";

    private final VBox mainVBox = new VBox();

    //private final GridPane allSubStepsPane = new GridPane();
    private final BorderPane centerPane = new BorderPane();

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane directIndirectPane = new BorderPane();
    private final BorderPane mechanismPane = new BorderPane();
    private final BorderPane rfpNode = new BorderPane();
    private final BorderPane vendorSelectionPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();

    public Step4Panel(MainController control) {

        this.control = control;

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setWrapText(true);
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setTextAlignment(TextAlignment.CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("4");
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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_4);
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

        int subStepIndex = 1;
        // Initial Applications Questions Panel
        directIndirectPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        directIndirectPane.setCenter(Step4TableHelper.createDirectIndirectNode(control.getProject()));
        directIndirectPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Application Wizard Summary Panel
        mechanismPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        mechanismPane.setCenter(Step4TableHelper.createMechanismNode(control.getProject()));
        mechanismPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Benefits Questions Panel
        rfpNode.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        rfpNode.setCenter(Step4TableHelper.createRFPNode(control.getProject()));
        rfpNode.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Costs Questions Panel
        vendorSelectionPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        vendorSelectionPane.setCenter(Step4TableHelper.createVendorNode(control.getProject()));
        vendorSelectionPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));
        stepReportPane.setCenter(Step4TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        mainVBox.getChildren().addAll(centerPane);  //allSubStepsPane
//        int i = 0;
//        allSubStepsPane.add(stepIntroGrid, i++, 0);
//        allSubStepsPane.add(directIndirectPane, i++, 0);
//        allSubStepsPane.add(mechanismPane, i++, 0);
//        allSubStepsPane.add(rfpNode, i++, 0);
//        allSubStepsPane.add(vendorSelectionPane, i++, 0);
//        allSubStepsPane.add(stepReportPane, i++, 0);
        this.subStepPanesList.add(stepIntroGrid);
        this.subStepPanesList.add(directIndirectPane);
        this.subStepPanesList.add(mechanismPane);
        this.subStepPanesList.add(rfpNode);
        this.subStepPanesList.add(vendorSelectionPane);
        this.subStepPanesList.add(stepReportPane);

        centerPane.setCenter(stepIntroGrid);

//        int numPanes = getNumSubSteps() + 2;
//        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(directIndirectPane, Priority.ALWAYS);
        GridPane.setVgrow(mechanismPane, Priority.ALWAYS);
        GridPane.setVgrow(rfpNode, Priority.ALWAYS);
        GridPane.setVgrow(vendorSelectionPane, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(centerPane, Priority.ALWAYS);  //allSubStepsPane
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
//                //System.out.println("Step 4 Width Resized");
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
                    stepReportPane.setCenter(Step4TableHelper.createStepSummary(control));
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
    public Node getFactSheet6Node() {
        return this.stepReportPane.getCenter();
    }
}
