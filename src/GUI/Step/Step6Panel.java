/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.Tables.Step6TableHelper;
import core.Project;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
public class Step6Panel extends BorderPane {

    private final MainController control;

    private int stepIndex = 5;

    private final VBox mainVBox = new VBox();

    private final GridPane allSubStepsPane = new GridPane();

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane changingConditionsPanel = new BorderPane();
    private final BorderPane sharingInfoPanel = new BorderPane();
    private final BorderPane staffingPanel = new BorderPane();
    private final BorderPane publicSupportPanel = new BorderPane();
    private final BorderPane monitoringEvalPanel = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();

    public Step6Panel(MainController control) {

        this.control = control;

        mainVBox.setFillWidth(true);
        Label startLabel = new Label("Step");
        startLabel.setWrapText(true);
        startLabel.setAlignment(Pos.BOTTOM_CENTER);
        startLabel.setTextAlignment(TextAlignment.CENTER);
        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
        startLabel.setMaxWidth(MainController.MAX_WIDTH);
        Label infoLabel = new Label("6");
        infoLabel.setMaxHeight(MainController.MAX_HEIGHT);
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        infoLabel.setAlignment(Pos.TOP_CENTER);
        Label instructionLabel = new Label("System Operation, Maintenance, and Evaluation");
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
                return (widthProperty().get() / (Project.NUM_SUB_STEPS[1] + 2) - 150); // 0.9
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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_6);
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
        changingConditionsPanel.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        changingConditionsPanel.setCenter(Step6TableHelper.createChangingConditionsNode(control.getProject()));
        changingConditionsPanel.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Application Wizard Summary Panel
        sharingInfoPanel.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        sharingInfoPanel.setCenter(Step6TableHelper.createSharingInfoNode(control.getProject()));
        sharingInfoPanel.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Benefits Questions Panel
        staffingPanel.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        staffingPanel.setCenter(Step6TableHelper.createStaffingNode(control.getProject()));
        staffingPanel.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Costs Questions Panel
        publicSupportPanel.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        publicSupportPanel.setCenter(Step6TableHelper.createPublicSupportNode(control.getProject()));
        publicSupportPanel.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Institutional/Jurisdictional Questions Panel
        this.monitoringEvalPanel.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
        monitoringEvalPanel.setCenter(Step6TableHelper.createMonitoringEvalNode(control.getProject()));
        monitoringEvalPanel.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        mainVBox.getChildren().addAll(allSubStepsPane);
        int i = 0;
        allSubStepsPane.add(stepIntroGrid, i++, 0);
        allSubStepsPane.add(changingConditionsPanel, i++, 0);
        allSubStepsPane.add(sharingInfoPanel, i++, 0);
        allSubStepsPane.add(staffingPanel, i++, 0);
        allSubStepsPane.add(publicSupportPanel, i++, 0);
        allSubStepsPane.add(monitoringEvalPanel, i++, 0);
        allSubStepsPane.add(stepReportPane, i++, 0);

        int numPanes = getNumSubSteps() + 2;
        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
            ColumnConstraints tcc = new ColumnConstraints();
            tcc.setPercentWidth(100.0 / numPanes);
            allSubStepsPane.getColumnConstraints().add(tcc);
        }

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(changingConditionsPanel, Priority.ALWAYS);
        GridPane.setVgrow(sharingInfoPanel, Priority.ALWAYS);
        GridPane.setVgrow(staffingPanel, Priority.ALWAYS);
        GridPane.setVgrow(publicSupportPanel, Priority.ALWAYS);
        GridPane.setVgrow(monitoringEvalPanel, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(allSubStepsPane, Priority.ALWAYS);
        this.setCenter(mainVBox);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {

    }

    private void setupPropertyBindings() {
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldWidth, Number newWidth) {
                System.out.println("Step 6 Width Resized");
                if (allSubStepsPane != null && allSubStepsPane.isVisible()) {
                    allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
                    allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
                    moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
                }
            }
        });

        control.activeSubStepProperty(stepIndex).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                selectSubStep(getActiveSubStep());
                control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
                control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);

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
        if (allSubStepsPane != null) {
            allSubStepsPane.setMinWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
            allSubStepsPane.setMaxWidth((getNumSubSteps() + 2) * (control.getAppWidth() - 220));
            moveScreen((getActiveSubStep() + 1) * stepIntroGrid.getWidth(), 0, false);
        }
    }

    private void selectSubStep(int stepIndex) {
        moveScreen((stepIndex + 1) * stepIntroGrid.getWidth(), 0);
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

}
