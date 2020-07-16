/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.FlowButton;
import GUI.Helper.IconHelper;
import core.Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;

/**
 *
 * @author ltrask
 */
public class FlowBarV2 extends BorderPane {

    private final MainController control;

    private final GridPane flowGrid = new GridPane();

    private final Button prevButton = new Button();
    private final Button nextButton = new Button();

    private final FlowButton introButton = new FlowButton("Intro", "intro-button");
    private final FlowButton step1Button = new FlowButton("Step 1");
    private final FlowButton step2Button = new FlowButton("Step 2");
    private final FlowButton step3Button = new FlowButton("Step 3");
    private final FlowButton step4Button = new FlowButton("Step 4");
    private final FlowButton step5Button = new FlowButton("Step 5");
    private final FlowButton step6Button = new FlowButton("Step 6");
    private final FlowButton summaryButton = new FlowButton("Summary", "summary-button");

    private final FlowButton step1Sub1Button = new FlowButton(Project.STEP_NAMES[0][1]);
    private final FlowButton step1Sub2Button = new FlowButton(Project.STEP_NAMES[0][2]);
    private final FlowButton step1Sub3Button = new FlowButton(Project.STEP_NAMES[0][3]);
    private final FlowButton step1Sub4Button = new FlowButton(Project.STEP_NAMES[0][4]);
    private final FlowButton step1Sub5Button = new FlowButton(Project.STEP_NAMES[0][5]);
    private final FlowButton step1Sub6Button = new FlowButton(Project.STEP_NAMES[0][6]);
    private final FlowButton step1Sub7Button = new FlowButton(Project.STEP_NAMES[0][7]);
    private final FlowButton step1Sub8Button = new FlowButton(Project.STEP_NAMES[0][8]);
    private final FlowButton step1Sub9Button = new FlowButton(Project.STEP_NAMES[0][9]); //
    //private final FlowButton step1Sub10Button = new FlowButton(Project.STEP_NAMES[0][10]);
    //private final FlowButton step1Sub11Button = new FlowButton(Project.STEP_NAMES[0][11]);
    private final FlowButton step1ReportButton = new FlowButton("Step 1 Report");

    private final FlowButton step2Sub1Button = new FlowButton(Project.STEP_NAMES[1][1]);
    private final FlowButton step2Sub2Button = new FlowButton(Project.STEP_NAMES[1][2]);
    private final FlowButton step2Sub3Button = new FlowButton(Project.STEP_NAMES[1][3]);
    private final FlowButton step2Sub4Button = new FlowButton(Project.STEP_NAMES[1][4]);
    private final FlowButton step2Sub5Button = new FlowButton("Institutional & Jurisdictional"); //Project.STEP_NAMES[1][5]
    private final FlowButton step2Sub6Button = new FlowButton(Project.STEP_NAMES[1][6]);
    private final FlowButton step2Sub7Button = new FlowButton(Project.STEP_NAMES[1][7]);
    private final FlowButton step2Sub8Button = new FlowButton(Project.STEP_NAMES[1][8]);
    private final FlowButton step2ReportButton = new FlowButton("Step 2 Report");

    private final FlowButton step3Sub1Button = new FlowButton(Project.STEP_NAMES[2][1]);
    private final FlowButton step3Sub2Button = new FlowButton(Project.STEP_NAMES[2][2]);
    private final FlowButton step3Sub3Button = new FlowButton(Project.STEP_NAMES[2][3]);
    private final FlowButton step3Sub4Button = new FlowButton(Project.STEP_NAMES[2][4]);
    private final FlowButton step3Sub5Button = new FlowButton(Project.STEP_NAMES[2][5]);
    private final FlowButton step3Sub6Button = new FlowButton(Project.STEP_NAMES[2][6]);
    private final FlowButton step3Sub7Button = new FlowButton(Project.STEP_NAMES[2][7]);
    private final FlowButton step3Sub8Button = new FlowButton(Project.STEP_NAMES[2][8]);
    //private final FlowButton step3Sub9Button = new FlowButton(Project.STEP_NAMES[2][9]);
    //private final FlowButton step3Sub10Button = new FlowButton(Project.STEP_NAMES[2][10]);
    private final FlowButton step3ReportButton = new FlowButton("Step 3 Report");

    private final FlowButton step4Sub1Button = new FlowButton(Project.STEP_NAMES[3][1]);
    private final FlowButton step4Sub2Button = new FlowButton(Project.STEP_NAMES[3][2]);
    private final FlowButton step4Sub3Button = new FlowButton(Project.STEP_NAMES[3][3]);
    private final FlowButton step4Sub4Button = new FlowButton(Project.STEP_NAMES[3][4]);
    private final FlowButton step4ReportButton = new FlowButton("Step 4 Report");

    private final FlowButton step5Sub1Button = new FlowButton(Project.STEP_NAMES[4][1]);
    private final FlowButton step5Sub2Button = new FlowButton(Project.STEP_NAMES[4][2]);
    private final FlowButton step5Sub3Button = new FlowButton(Project.STEP_NAMES[4][3]);
    private final FlowButton step5Sub4Button = new FlowButton(Project.STEP_NAMES[4][4]);
    private final FlowButton step5ReportButton = new FlowButton("Step 5 Report");

    private final FlowButton step6Sub1Button = new FlowButton(Project.STEP_NAMES[5][1]);
    private final FlowButton step6Sub2Button = new FlowButton(Project.STEP_NAMES[5][2]);
    private final FlowButton step6Sub3Button = new FlowButton("Maintaining\nAdequate Staff"); //Project.STEP_NAMES[5][3]
    private final FlowButton step6Sub4Button = new FlowButton("Leveraging\nPublic Support"); //Project.STEP_NAMES[5][4]
    private final FlowButton step6Sub5Button = new FlowButton(Project.STEP_NAMES[5][5]);
    private final FlowButton step6ReportButton = new FlowButton("Step 6 Report");

    private final ArrayList<FlowButton> allButtonList = new ArrayList<>();
    private final ArrayList<FlowButton> flowList = new ArrayList<>();
    private final ArrayList<FlowButton> step1List = new ArrayList<>();
    private final ArrayList<FlowButton> step2List = new ArrayList<>();
    private final ArrayList<FlowButton> step3List = new ArrayList<>();
    private final ArrayList<FlowButton> step4List = new ArrayList<>();
    private final ArrayList<FlowButton> step5List = new ArrayList<>();
    private final ArrayList<FlowButton> step6List = new ArrayList<>();

    private ColumnConstraints introCC;
    private ColumnConstraints summaryCC;

    private final ArrayList<ColumnConstraints> ccStep = new ArrayList<>();
    private final ArrayList<ArrayList<ColumnConstraints>> ccSubSteps = new ArrayList<>();

    public static final int FLOW_BAR_HEIGHT = 90;

    public FlowBarV2(MainController mc) {
        this.control = mc;
        this.getStyleClass().add("flow-v2");
        setupButtonLists();
        addButtons();
        createCCLists();

        setupActionListeners();
        setupPropertyBindings();

        setFormatting();

        flowGrid.setMinHeight(FLOW_BAR_HEIGHT);
        flowGrid.setPrefHeight(FLOW_BAR_HEIGHT);
        flowGrid.setMaxHeight(FLOW_BAR_HEIGHT);
        prevButton.setMinHeight(FLOW_BAR_HEIGHT);
        prevButton.setPrefHeight(FLOW_BAR_HEIGHT);
        prevButton.setMaxHeight(FLOW_BAR_HEIGHT);
        nextButton.setMinHeight(FLOW_BAR_HEIGHT);
        nextButton.setPrefHeight(FLOW_BAR_HEIGHT);
        nextButton.setMaxHeight(FLOW_BAR_HEIGHT);

        this.setLeft(prevButton);
        this.setCenter(flowGrid);
        this.setRight(nextButton);
        BorderPane.setMargin(flowGrid, new Insets(0, 3, 0, 3));

        selectStep(-1, -1);
    }

    private void createCCLists() {
        introCC = new ColumnConstraints();
        flowGrid.getColumnConstraints().add(introCC);
        for (int i = 0; i < Project.NUM_STEPS; i++) {
            ColumnConstraints stepCC = new ColumnConstraints();
            ccStep.add(stepCC);
            flowGrid.getColumnConstraints().add(stepCC);
            ccSubSteps.add(new ArrayList<>());
            for (int j = 0; j < Project.NUM_SUB_STEPS[i] + 1; j++) { // Add one to account for step report
                ccSubSteps.get(i).add(new ColumnConstraints());
                flowGrid.getColumnConstraints().add(ccSubSteps.get(i).get(j));
            }
        }
        summaryCC = new ColumnConstraints();
        flowGrid.getColumnConstraints().add(summaryCC);
    }

    private void setupButtonLists() {
        flowList.add(introButton);
        flowList.add(step1Button);
        flowList.add(step2Button);
        flowList.add(step3Button);
        flowList.add(step4Button);
        flowList.add(step5Button);
        flowList.add(step6Button);
        flowList.add(summaryButton);

        step1List.add(step1Button);
        step1List.add(step1Sub1Button);
        step1List.add(step1Sub2Button);
        step1List.add(step1Sub3Button);
        step1List.add(step1Sub4Button);
        step1List.add(step1Sub5Button);
        step1List.add(step1Sub6Button);
        step1List.add(step1Sub7Button);
        step1List.add(step1Sub8Button);
        step1List.add(step1Sub9Button);
        //step1List.add(step1Sub10Button);
        //step1List.add(step1Sub11Button);
        step1List.add(step1ReportButton);

        step2List.add(step2Button);
        step2List.add(step2Sub1Button);
        step2List.add(step2Sub2Button);
        step2List.add(step2Sub3Button);
        step2List.add(step2Sub4Button);
        step2List.add(step2Sub5Button);
        step2List.add(step2Sub6Button);
        step2List.add(step2Sub7Button);
        step2List.add(step2Sub8Button);
        step2List.add(step2ReportButton);

        step3List.add(step3Button);
        step3List.add(step3Sub1Button);
        step3List.add(step3Sub2Button);
        step3List.add(step3Sub3Button);
        step3List.add(step3Sub4Button);
        step3List.add(step3Sub5Button);
        step3List.add(step3Sub6Button);
        step3List.add(step3Sub7Button);
        step3List.add(step3Sub8Button);
        //step3List.add(step3Sub9Button);
        //step3List.add(step3Sub10Button);
        step3List.add(step3ReportButton);

        step4List.add(step4Button);
        step4List.add(step4Sub1Button);
        step4List.add(step4Sub2Button);
        step4List.add(step4Sub3Button);
        step4List.add(step4Sub4Button);
        //step4List.add(step4Sub5Button);
        step4List.add(step4ReportButton);

        step5List.add(step5Button);
        step5List.add(step5Sub1Button);
        step5List.add(step5Sub2Button);
        step5List.add(step5Sub3Button);
        step5List.add(step5Sub4Button);
        //step5List.add(step5Sub5Button);
        step5List.add(step5ReportButton);

        step6List.add(step6Button);
        step6List.add(step6Sub1Button);
        step6List.add(step6Sub2Button);
        step6List.add(step6Sub3Button);
        step6List.add(step6Sub4Button);
        step6List.add(step6Sub5Button);
        step6List.add(step6ReportButton);

        allButtonList.add(introButton);
        allButtonList.add(step1Button);
        allButtonList.add(step2Button);
        allButtonList.add(step3Button);
        allButtonList.add(step4Button);
        allButtonList.add(step5Button);
        allButtonList.add(step6Button);
        allButtonList.add(summaryButton);

        allButtonList.add(step1Button);
        allButtonList.add(step1Sub1Button);
        allButtonList.add(step1Sub2Button);
        allButtonList.add(step1Sub3Button);
        allButtonList.add(step1Sub4Button);
        allButtonList.add(step1Sub5Button);
        allButtonList.add(step1Sub6Button);
        allButtonList.add(step1Sub7Button);
        allButtonList.add(step1Sub8Button);
        allButtonList.add(step1Sub9Button);
        //allButtonList.add(step1Sub10Button);
        //allButtonList.add(step1Sub11Button);
        allButtonList.add(step1ReportButton);

        allButtonList.add(step2Button);
        allButtonList.add(step2Sub1Button);
        allButtonList.add(step2Sub2Button);
        allButtonList.add(step2Sub3Button);
        allButtonList.add(step2Sub4Button);
        allButtonList.add(step2Sub5Button);
        allButtonList.add(step2Sub6Button);
        allButtonList.add(step2Sub7Button);
        allButtonList.add(step2Sub8Button);
        allButtonList.add(step2ReportButton);

        allButtonList.add(step3Button);
        allButtonList.add(step3Sub1Button);
        allButtonList.add(step3Sub2Button);
        allButtonList.add(step3Sub3Button);
        allButtonList.add(step3Sub4Button);
        allButtonList.add(step3Sub5Button);
        allButtonList.add(step3Sub6Button);
        allButtonList.add(step3Sub7Button);
        allButtonList.add(step3Sub8Button);
        //allButtonList.add(step3Sub9Button);
        //allButtonList.add(step3Sub10Button);
        allButtonList.add(step3ReportButton);

        allButtonList.add(step4Button);
        allButtonList.add(step4Sub1Button);
        allButtonList.add(step4Sub2Button);
        allButtonList.add(step4Sub3Button);
        allButtonList.add(step4Sub4Button);
        //allButtonList.add(step4Sub5Button);
        allButtonList.add(step4ReportButton);

        allButtonList.add(step5Button);
        allButtonList.add(step5Sub1Button);
        allButtonList.add(step5Sub2Button);
        allButtonList.add(step5Sub3Button);
        allButtonList.add(step5Sub4Button);
        //allButtonList.add(step5Sub5Button);
        allButtonList.add(step5ReportButton);

        allButtonList.add(step6Button);
        allButtonList.add(step6Sub1Button);
        allButtonList.add(step6Sub2Button);
        allButtonList.add(step6Sub3Button);
        allButtonList.add(step6Sub4Button);
        allButtonList.add(step6Sub5Button);
        allButtonList.add(step6ReportButton);
    }

    private void addButtons() {
        int colIdx = 0;
        flowGrid.add(introButton, colIdx++, 0);
        for (Button b : step1List) {
            flowGrid.add(b, colIdx++, 0);
        }
        for (Button b : step2List) {
            flowGrid.add(b, colIdx++, 0);
        }
        for (Button b : step3List) {
            flowGrid.add(b, colIdx++, 0);
        }
        for (Button b : step4List) {
            flowGrid.add(b, colIdx++, 0);
        }
        for (Button b : step5List) {
            flowGrid.add(b, colIdx++, 0);
        }
        for (Button b : step6List) {
            flowGrid.add(b, colIdx++, 0);
        }
        flowGrid.add(summaryButton, colIdx, 0);
    }

    private void setFormatting() {

        FontIcon prevIcon = IconHelper.createIcon(FontAwesomeSolid.ARROW_LEFT,Color.BLACK,40);
        prevButton.setGraphic(prevIcon);

        FontIcon nextIcon = IconHelper.createIcon(FontAwesomeSolid.ARROW_RIGHT,Color.BLACK,40);
        nextButton.setGraphic(nextIcon);

//        introButton.getStyleClass().setAll("custom-button", "intro-button", "color-orange");
//        step1Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        step2Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        step3Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        step4Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        step5Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        step6Button.getStyleClass().setAll("custom-button", "step-button", "color-light-grey");
//        summaryButton.getStyleClass().setAll("custom-button", "summary-button", "color-light-grey");

        for (int subStepIdx = 1; subStepIdx < step1List.size(); subStepIdx++) {
            if(subStepIdx < step1List.size() - 1 && control.getProject().getStep(0).getSubStep(subStepIdx - 1).isWizardSummary()) {
                updateButtonColor(step1List.get(subStepIdx), "color-blue");
            } else {
                updateButtonColor(step1List.get(subStepIdx), "color-burnt-orange");
            }
        }
//        for (FlowButton b : step1List) {
//            updateButtonColor(b, "color-burnt-orange");
//        }
//        for (Button b : step2List) {
//            b.getStyleClass().add("color-light-grey");
//        }
//        for (Button b : step3List) {
//            b.getStyleClass().add("color-light-grey");
//        }
//        for (Button b : step4List) {
//            b.getStyleClass().setAll("color-light-grey");
//        }
//        for (Button b : step5List) {
//            b.getStyleClass().setAll("color-light-grey");
//        }
//        for (Button b : step6List) {
//            b.getStyleClass().setAll("color-light-grey");
//        }

        for (Button b : allButtonList) {
            GridPane.setHgrow(b, Priority.ALWAYS);
            GridPane.setVgrow(b, Priority.ALWAYS);
        }
    }

    public void selectStep(int stepIdx, int subStepIdx) {
        if (stepIdx < 0) {
            setProjectStartLayout();
        } else if (stepIdx == Project.NUM_STEPS) {
            // Reached summary steps
            setProjectStartLayout();
            this.introButton.setGraphic(null);
            this.summaryButton.setGraphic(null);
        } else {
            double split = 10;
            // Compute collapsed button widths
            double wCollapse = split / Project.NUM_STEPS + 1;  // One of the (Project.NUM_STEPS+2) Buttons will be expanded, the rest are collapsed
            // Compute expanded button widths
            double wExpand = (100 - split) / (1 + (Project.NUM_SUB_STEPS[stepIdx] + 1));

            // Shrink all Steps before stepIdx
            this.introCC.setPercentWidth(wCollapse);
            this.introButton.setText(""); //Intro
            Label l1 = new Label("Intro");
            l1.setRotate(270);
            this.introButton.setGraphic(new Group(l1));
            for (int i = 0; i < stepIdx; i++) {
                ccStep.get(i).setPercentWidth(wCollapse);
                this.flowList.get(i + 1).setText(String.valueOf(i + 1));
            }

            // Expand Step and SubSteps
            ccStep.get(stepIdx).setPercentWidth(wExpand);
            this.flowList.get(stepIdx + 1).setText("Step " + (stepIdx + 1));
            for (int i = 0; i < Project.NUM_STEPS; i++) {
                if (i == stepIdx) {
                    for (int j = 0; j < Project.NUM_SUB_STEPS[i] + 1; j++) {
                        ccSubSteps.get(i).get(j).setPercentWidth(wExpand);
                    }
                } else {
                    for (int j = 0; j < Project.NUM_SUB_STEPS[i] + 1; j++) {
                        ccSubSteps.get(i).get(j).setPercentWidth(0);
                    }
                }
            }

            // Shrink all Steps after stepIdx
            for (int i = stepIdx + 1; i < Project.NUM_STEPS; i++) {
                ccStep.get(i).setPercentWidth(wCollapse);
                this.flowList.get(i + 1).setText(String.valueOf(i + 1));
            }
            this.summaryCC.setPercentWidth(wCollapse);
            this.summaryButton.setText("");
            Label sl = new Label("Summary");
            sl.setRotate(270);
            this.summaryButton.setGraphic(new Group(sl));
        }
    }

    /**
     * Makes all Step Buttons equal size in tool bar and sets the width assigned
     * to each sub step button to be 0.
     */
    private void setProjectStartLayout() {
        double pctW = 100.0 / (Project.NUM_STEPS + 2);
        introCC.setPercentWidth(pctW);
        for (int i = 0; i < Project.NUM_STEPS; i++) {
            ccStep.get(i).setPercentWidth(pctW);
            for (ColumnConstraints ccSub : ccSubSteps.get(i)) {
                ccSub.setPercentWidth(0);
            }
        }
        summaryCC.setPercentWidth(pctW);
        this.introButton.setText("Intro");
        this.step1Button.setText("Step 1");
        this.step2Button.setText("Step 2");
        this.step3Button.setText("Step 3");
        this.step4Button.setText("Step 4");
        this.step5Button.setText("Step 5");
        this.step6Button.setText("Step 6");
        this.summaryButton.setText("Summary");
    }

    private void setupActionListeners() {

        prevButton.setOnAction(actionEvent -> control.stepBack());

        nextButton.setOnAction(actionEvent -> control.stepForward());

        step1Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 0) {
                control.setActiveStep(0);
            } else {
                control.setActiveSubStep(0, -1);
            }
        });
        step2Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 1) {
                control.setActiveStep(1);
            } else {
                control.setActiveSubStep(1, -1);
            }
        });
        step3Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 2) {
                control.setActiveStep(2);
            } else {
                control.setActiveSubStep(2, -1);
            }
        });
        step4Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 3) {
                control.setActiveStep(3);
            } else {
                control.setActiveSubStep(3, -1);
            }
        });
        step5Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 4) {
                control.setActiveStep(4);
            } else {
                control.setActiveSubStep(4, -1);
            }
        });
        step6Button.setOnAction(actionEvent -> {
            if (control.getActiveStep() != 5) {
                control.setActiveStep(5);
            } else {
                control.setActiveSubStep(5, -1);
            }
        });
        summaryButton.setOnAction(actionEvent -> control.setActiveStep(6));

        for (int bIdx = 1; bIdx < this.step1List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step1List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(0, subStepIdx));
        }

        for (int bIdx = 1; bIdx < this.step2List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step2List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(1, subStepIdx));
        }

        for (int bIdx = 1; bIdx < this.step3List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step3List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(2, subStepIdx));
        }

        for (int bIdx = 1; bIdx < this.step4List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step4List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(3, subStepIdx));
        }

        for (int bIdx = 1; bIdx < this.step5List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step5List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(4, subStepIdx));
        }

        for (int bIdx = 1; bIdx < this.step6List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step6List.get(bIdx).setOnAction(actionEvent -> control.setActiveSubStep(5, subStepIdx));
        }
    }

    private void updateButtonColor(Button button, String newColorStyle) {
        ArrayList<String> toRemove = new ArrayList<>();
        for (String styleClass : button.getStyleClass()) {
            if (styleClass.contains("color-")) {
                toRemove.add(styleClass);
            }
        }
        button.getStyleClass().removeAll(toRemove);
        button.getStyleClass().add(newColorStyle);
    }

    private void setupPropertyBindings() {
        control.activeStepProperty().addListener((observableValue, oldVal, newVal) -> {
            // Select the active step
            selectStep(control.getActiveStep(), control.getActiveSubStep(control.getActiveStep()));

            final int currStep = control.getActiveStep();
            final int currSubStep = control.getActiveSubStep(control.getActiveStep());
            // Update formatting
            updateButtonColor(introButton, "color-light-grey");
            for (int stepButtonIdx = 0; stepButtonIdx < flowList.size() - 1; stepButtonIdx++) {  // -1 to skip summary button
                final FlowButton currStepButton = flowList.get(stepButtonIdx + 1); // +1 to skip introbutton
                if (currStep == stepButtonIdx) {
                    if (currSubStep < 0) {
                        updateButtonColor(currStepButton, "color-orange");
                        currStepButton.setActive(true);
                    } else {
                        updateButtonColor(currStepButton, "color-burnt-orange");
                        currStepButton.setActive(false);
                    }
                } else {
                    currStepButton.setActive(false);
                    updateButtonColor(currStepButton, "color-light-grey");
                }
            }
            updateButtonColor(summaryButton, currStep == 6 ? "color-orange" : "color-light-grey");

            prevButton.setDisable(control.getActiveStep() == 0 && control.getActiveSubStep(0) == -1);
            nextButton.setDisable(
                    control.getActiveStep() == Project.NUM_STEPS
            );

            control.updateMainWindowTitle();
        });
        step1Button.disableProperty().bind(control.getProject().getStep(0).stepStartedProperty().not());
        step2Button.disableProperty().bind(control.getProject().getStep(1).stepStartedProperty().not());
        step3Button.disableProperty().bind(control.getProject().getStep(2).stepStartedProperty().not());
        step4Button.disableProperty().bind(control.getProject().getStep(3).stepStartedProperty().not());
        step5Button.disableProperty().bind(control.getProject().getStep(4).stepStartedProperty().not());
        step6Button.disableProperty().bind(control.getProject().getStep(5).stepStartedProperty().not());
        summaryButton.disableProperty().bind(control.getProject().getStep(5).stepFinishedProperty().not());

        control.activeSubStepProperty(0).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step1Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step1Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step1List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[0] && control.getProject().getStep(0).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step1List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step1List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step1List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }

            prevButton.setDisable(control.getActiveStep() == 0 && control.getActiveSubStep(0) == -1);
        });
        step1Sub1Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(0).stepStartedProperty().not());
        step1Sub2Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(1).stepStartedProperty().not());
        step1Sub3Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(2).stepStartedProperty().not());
        step1Sub4Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(3).stepStartedProperty().not());
        //step1Sub5Button.disableProperty().bind(control.getProject().progressGoal.lessThan(1.0));
        step1Sub6Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(5).stepStartedProperty().not());
        //step1Sub7Button.disableProperty().bind(control.getProject().progressFeas.lessThan(1.0));
        step1Sub8Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(7).stepStartedProperty().not());
        //step1Sub9Button.disableProperty().bind(control.getProject().progressStake.lessThan(1.0));
        //step1Sub10Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(9).stepStartedProperty().not());
        //step1Sub11Button.disableProperty().bind(control.getProject().getStep(0).getSubStep(10).stepStartedProperty().not());

        control.activeSubStepProperty(1).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step2Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step2Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step2List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[1] && control.getProject().getStep(1).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step2List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step2List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step2List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }
        });
        step2Sub1Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(0).stepStartedProperty().not());
        step2Sub2Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(1).stepStartedProperty().not());
        step2Sub3Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(2).stepStartedProperty().not());
        step2Sub4Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(3).stepStartedProperty().not());
        //step2Sub5Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(3).stepFinishedProperty().not());

        // The following listener prevents buttons from remaining active when moving between full steps
        control.activeSubStepProperty(2).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step3Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step3Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step3List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[2] && control.getProject().getStep(2).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step3List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step3List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step3List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }
        });
        step3Sub1Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(0).stepStartedProperty().not());
        step3Sub2Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(1).stepStartedProperty().not());
        step3Sub3Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(2).stepStartedProperty().not());
        step3Sub4Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(3).stepStartedProperty().not());
        //step3Sub5Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(3).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step4Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step4Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step4List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[3] && control.getProject().getStep(3).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step4List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step4List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step4List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }
        });
        step4Sub1Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(0).stepStartedProperty().not());
        step4Sub2Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(1).stepStartedProperty().not());
        step4Sub3Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(2).stepStartedProperty().not());
        step4Sub4Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(3).stepStartedProperty().not());
        //step4Sub5Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(4).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step5Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step5Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step5List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[4] && control.getProject().getStep(4).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step5List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step5List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step5List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }
        });
        step5Sub1Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(0).stepStartedProperty().not());
        step5Sub2Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(1).stepStartedProperty().not());
        step5Sub3Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(2).stepStartedProperty().not());
        step5Sub4Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(3).stepStartedProperty().not());
        //step5Sub5Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(5).addListener((observableValue, oldSubStepIdx, newSubStepIdx) -> {
            updateButtonColor(step6Button, newSubStepIdx.intValue() < 0 ? "color-orange" : "color-burnt-orange");
            step6Button.setActive(newSubStepIdx.intValue() < 0);
            // Iterating through substeps
            for (int bIdx = 1; bIdx < step6List.size(); bIdx++) {
                if (bIdx < Project.NUM_SUB_STEPS[5] && control.getProject().getStep(5).getSubStep(bIdx - 1).isWizardSummary()) {
                    updateButtonColor(step6List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-light-blue" : "color-blue");
                } else {
                    updateButtonColor(step6List.get(bIdx), newSubStepIdx.intValue() == (bIdx - 1) ? "color-orange" : "color-burnt-orange");
                }
                step6List.get(bIdx).setActive(newSubStepIdx.intValue() == (bIdx - 1));
            }
        });
        step6Sub1Button.disableProperty().bind(control.getProject().getStep(5).getSubStep(0).stepStartedProperty().not());
        step6Sub2Button.disableProperty().bind(control.getProject().getStep(5).getSubStep(1).stepStartedProperty().not());
        step6Sub3Button.disableProperty().bind(control.getProject().getStep(5).getSubStep(2).stepStartedProperty().not());
        step6Sub4Button.disableProperty().bind(control.getProject().getStep(5).getSubStep(3).stepStartedProperty().not());
        //step6Sub5Button.disableProperty().bind(control.getProject().getStep(5).getSubStep(3).stepFinishedProperty().not());

    }

    public void checkProceed() {
        nextButton.setDisable(
                !(control.getProject().isStepComplete(control.getActiveStep())
                || control.getProject().isSubStepFinished(control.getActiveStep(), control.getActiveSubStep(control.getActiveStep()))
                || control.getProject().isSubStepStarted(control.getActiveStep(), control.getActiveSubStep(control.getActiveStep()) + 1))
        );
    }

}
