/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.IconHelper;
import GUI.Helper.ColorHelper;
import static GUI.Helper.ColorHelper.COLOR_SUB_STEP;
import static GUI.Helper.ColorHelper.COLOR_SUB_STEP_FONT;
import static GUI.Helper.ColorHelper.COLOR_SUB_STEP_HL;
import static GUI.Helper.ColorHelper.COLOR_SUB_STEP_HL_FONT;
import static GUI.Helper.ColorHelper.COLOR_STEP;
import static GUI.Helper.ColorHelper.COLOR_STEP_FONT;
import static GUI.Helper.ColorHelper.COLOR_STEP_HL;
import static GUI.Helper.ColorHelper.COLOR_WIZARD;
import static GUI.Helper.ColorHelper.COLOR_WIZARD_FONT;
import static GUI.Helper.ColorHelper.COLOR_WIZARD_HL;
import static GUI.Helper.ColorHelper.COLOR_WIZARD_HL_FONT;
import core.Project;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class FlowBar extends BorderPane {

    private final MainController control;

    private final GridPane flowGrid = new GridPane();

    private final Button prevButton = new Button();
    private final Button nextButton = new Button();
    private final SVGPath prevSVG = new SVGPath();
    private final SVGPath nextSVG = new SVGPath();
    private final FillTransition nextFT;

    private final Button introButton = new Button("Intro");
    private final Button step1Button = new Button("Step 1");
    private final Button step2Button = new Button("Step 2");
    private final Button step3Button = new Button("Step 3");
    private final Button step4Button = new Button("Step 4");
    private final Button step5Button = new Button("Step 5");
    private final Button step6Button = new Button("Step 6");
    private final Button summaryButton = new Button("Summary");

    private final Button step1Sub1Button = new Button(Project.STEP_NAMES[0][1]);
    private final Button step1Sub2Button = new Button(Project.STEP_NAMES[0][2]);
    private final Button step1Sub3Button = new Button(Project.STEP_NAMES[0][3]);
    private final Button step1Sub4Button = new Button(Project.STEP_NAMES[0][4]);
    private final Button step1Sub5Button = new Button(Project.STEP_NAMES[0][5]);
    private final Button step1Sub6Button = new Button(Project.STEP_NAMES[0][6]);
    private final Button step1Sub7Button = new Button(Project.STEP_NAMES[0][7]);
    private final Button step1Sub8Button = new Button(Project.STEP_NAMES[0][8]);
    private final Button step1Sub9Button = new Button(Project.STEP_NAMES[0][9]); //
    //private final Button step1Sub10Button = new Button(Project.STEP_NAMES[0][10]);
    //private final Button step1Sub11Button = new Button(Project.STEP_NAMES[0][11]);
    private final Button step1ReportButton = new Button("Step 1 Report");

    private final Button step2Sub1Button = new Button(Project.STEP_NAMES[1][1]);
    private final Button step2Sub2Button = new Button(Project.STEP_NAMES[1][2]);
    private final Button step2Sub3Button = new Button(Project.STEP_NAMES[1][3]);
    private final Button step2Sub4Button = new Button(Project.STEP_NAMES[1][4]);
    private final Button step2Sub5Button = new Button("Institutional & Jurisdictional"); //Project.STEP_NAMES[1][5]
    private final Button step2Sub6Button = new Button(Project.STEP_NAMES[1][6]);
    private final Button step2Sub7Button = new Button(Project.STEP_NAMES[1][7]);
    private final Button step2Sub8Button = new Button(Project.STEP_NAMES[1][8]);
    private final Button step2ReportButton = new Button("Step 2 Report");

    private final Button step3Sub1Button = new Button(Project.STEP_NAMES[2][1]);
    private final Button step3Sub2Button = new Button(Project.STEP_NAMES[2][2]);
    private final Button step3Sub3Button = new Button(Project.STEP_NAMES[2][3]);
    private final Button step3Sub4Button = new Button(Project.STEP_NAMES[2][4]);
    private final Button step3Sub5Button = new Button(Project.STEP_NAMES[2][5]);
    private final Button step3Sub6Button = new Button(Project.STEP_NAMES[2][6]);
    private final Button step3Sub7Button = new Button(Project.STEP_NAMES[2][7]);
    private final Button step3Sub8Button = new Button(Project.STEP_NAMES[2][8]);
    //private final Button step3Sub9Button = new Button(Project.STEP_NAMES[2][9]);
    //private final Button step3Sub10Button = new Button(Project.STEP_NAMES[2][10]);
    private final Button step3ReportButton = new Button("Step 3 Report");

    private final Button step4Sub1Button = new Button(Project.STEP_NAMES[3][1]);
    private final Button step4Sub2Button = new Button(Project.STEP_NAMES[3][2]);
    private final Button step4Sub3Button = new Button(Project.STEP_NAMES[3][3]);
    private final Button step4Sub4Button = new Button(Project.STEP_NAMES[3][4]);
    private final Button step4ReportButton = new Button("Step 4 Report");

    private final Button step5Sub1Button = new Button(Project.STEP_NAMES[4][1]);
    private final Button step5Sub2Button = new Button(Project.STEP_NAMES[4][2]);
    private final Button step5Sub3Button = new Button(Project.STEP_NAMES[4][3]);
    private final Button step5Sub4Button = new Button(Project.STEP_NAMES[4][4]);
    private final Button step5ReportButton = new Button("Step 5 Report");

    private final Button step6Sub1Button = new Button(Project.STEP_NAMES[5][1]);
    private final Button step6Sub2Button = new Button(Project.STEP_NAMES[5][2]);
    private final Button step6Sub3Button = new Button("Maintaining\nAdequate Staff"); //Project.STEP_NAMES[5][3]
    private final Button step6Sub4Button = new Button("Leveraging\nPublic Support"); //Project.STEP_NAMES[5][4]
    private final Button step6Sub5Button = new Button(Project.STEP_NAMES[5][5]);
    private final Button step6ReportButton = new Button("Step 6 Report");

    private final ArrayList<Button> allButtonList = new ArrayList();
    private final ArrayList<Button> flowList = new ArrayList();
    private final ArrayList<Button> step1List = new ArrayList();
    private final ArrayList<Button> step2List = new ArrayList();
    private final ArrayList<Button> step3List = new ArrayList();
    private final ArrayList<Button> step4List = new ArrayList();
    private final ArrayList<Button> step5List = new ArrayList();
    private final ArrayList<Button> step6List = new ArrayList();

    private ColumnConstraints introCC;
    private ColumnConstraints summaryCC;

    private final ArrayList<ColumnConstraints> ccStep = new ArrayList();
    private final ArrayList<ArrayList<ColumnConstraints>> ccSubSteps = new ArrayList();

    public FlowBar(MainController mc) {
        this.control = mc;
        this.setStyle("-fx-background-color: white");
        setupButtonLists();
        addButtons();
        createCCLists();

        setupActionListeners();
        setupPropertyBindings();
//        this.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
//                selectStep(control.getActiveStep(), control.getActiveSubStep(control.getActiveStep()));
//            }
//        });
        setFormatting();

        int toolBarHeight = 75;
        flowGrid.setMinHeight(toolBarHeight);
        flowGrid.setPrefHeight(toolBarHeight);
        flowGrid.setMaxHeight(toolBarHeight);
        prevButton.setMinHeight(toolBarHeight);
        prevButton.setPrefHeight(toolBarHeight);
        prevButton.setMaxHeight(toolBarHeight);
        nextButton.setMinHeight(toolBarHeight);
        nextButton.setPrefHeight(toolBarHeight);
        nextButton.setMaxHeight(toolBarHeight);

        this.setLeft(prevButton);
        this.setCenter(flowGrid);
        this.setRight(nextButton);
        BorderPane.setMargin(flowGrid, new Insets(0, 3, 0, 3));

        nextFT = new FillTransition(Duration.millis(1000), nextSVG, Color.web("#595959"), Color.web("#ED7D31"));
        nextFT.setCycleCount(Animation.INDEFINITE);
        nextFT.setAutoReverse(true);

        selectStep(-1, -1);
    }

    private void createCCLists() {
        introCC = new ColumnConstraints();
        //ccStep.add(introCC);
        flowGrid.getColumnConstraints().add(introCC);
        for (int i = 0; i < Project.NUM_STEPS; i++) {
            ColumnConstraints stepCC = new ColumnConstraints();
            ccStep.add(stepCC);
            flowGrid.getColumnConstraints().add(stepCC);
            ccSubSteps.add(new ArrayList());
            for (int j = 0; j < Project.NUM_SUB_STEPS[i] + 1; j++) { // Add one to account for step report
                ccSubSteps.get(i).add(new ColumnConstraints());
                flowGrid.getColumnConstraints().add(ccSubSteps.get(i).get(j));
            }
        }
        summaryCC = new ColumnConstraints();
        //ccStep.add(summaryCC);
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
        flowGrid.add(summaryButton, colIdx++, 0);
    }

    private void setFormatting() {
        //prevSVG = new SVGPath();
        prevSVG.setContent(IconHelper.SVG_STR_PREV);
        //nextSVG = new SVGPath();
        nextSVG.setContent(IconHelper.SVG_STR_NEXT);
        Label lp = new Label("-");
        lp.setStyle("-fx-font-size: 60; -fx-alignment: center; -fx-text-alignment: center;");
        //lp.setRotate(270);
        lp.setPadding(new Insets(0, 10, 9, 0));
        HBox hbp = new HBox();
        hbp.setAlignment(Pos.CENTER);
        hbp.getChildren().add(lp);
        hbp.getChildren().add(prevSVG);
        prevButton.setGraphic(hbp);
        Label ln = new Label("+");
        ln.setStyle("-fx-font-size: 60; -fx-alignment: center; -fx-text-alignment: center;");
        //ln.setRotate(270);
        ln.setPadding(new Insets(0, 0, 5, 10));
        HBox hbn = new HBox();
        hbn.setAlignment(Pos.CENTER);
        hbn.getChildren().add(nextSVG);
        hbn.getChildren().add(ln);
        nextButton.setGraphic(hbn);

        introButton.getStyleClass().add("sub-flow-step-start");
        step1Button.getStyleClass().add("sub-flow-step");
        step2Button.getStyleClass().add("sub-flow-step");
        step3Button.getStyleClass().add("sub-flow-step");
        step4Button.getStyleClass().add("sub-flow-step");
        step5Button.getStyleClass().add("sub-flow-step");
        step6Button.getStyleClass().add("sub-flow-step");
        summaryButton.getStyleClass().add("sub-flow-step-end");
        for (Button b : flowList) {
            b.setStyle("-fx-background-color: " + ColorHelper.COLOR_STEP);
        }

        for (Button b : step1List) {
            b.getStyleClass().add("sub-flow-step");
        }
        for (Button b : step2List) {
            b.getStyleClass().add("sub-flow-step");
        }
        for (Button b : step3List) {
            b.getStyleClass().add("sub-flow-step");
        }
        for (Button b : step4List) {
            b.getStyleClass().add("sub-flow-step");
        }
        for (Button b : step5List) {
            b.getStyleClass().add("sub-flow-step");
        }
        for (Button b : step6List) {
            b.getStyleClass().add("sub-flow-step");
        }

        for (Button b : allButtonList) {
            b.setTextAlignment(TextAlignment.CENTER);
            b.setWrapText(true);
            b.setMinWidth(0);
            b.setMinHeight(0);
            b.setMaxWidth(MainController.MAX_WIDTH);
            b.setMaxHeight(MainController.MAX_HEIGHT);
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
            double wCollapse = split / Project.NUM_STEPS + 1;  // One of the (Project.NUM_STEPS+2) Buttons will be exanded, the rest are collapsed
            // Comput expanded button widths
            double wExpand = (100 - split) / (1 + (Project.NUM_SUB_STEPS[stepIdx] + 1));

            // Shrink all Steps before stepIdx
            this.introCC.setPercentWidth(wCollapse);
            this.introButton.setText(""); //Intro
            Label l1 = new Label("Intro");
            l1.setStyle("-fx-text-fill: white");
            l1.setRotate(270);
            this.introButton.setGraphic(new Group(l1));
            for (int i = 0; i < stepIdx; i++) {
                ccStep.get(i).setPercentWidth(wCollapse);
                this.flowList.get(i + 1).setText(String.valueOf(i + 1));
            }

            // Expand Step and SubSteps
            ccStep.get(stepIdx).setPercentWidth(wExpand);
            this.flowList.get(stepIdx + 1).setText("Step " + String.valueOf(stepIdx + 1));
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
            sl.setStyle("-fx-text-fill: white");
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

        prevButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.stepBack();
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                nextFT.stop();
                nextSVG.setFill(Color.BLACK);
                control.stepForward();
            }
        });

//        introButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                control.setActiveStep(-1);
//            }
//        });
        step1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 0) {
                    control.setActiveStep(0);
                } else {
                    control.setActiveSubStep(0, -1);
                }
            }
        });
        step2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 1) {
                    control.setActiveStep(1);
                } else {
                    control.setActiveSubStep(1, -1);
                }
            }
        });
        step3Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 2) {
                    control.setActiveStep(2);
                } else {
                    control.setActiveSubStep(2, -1);
                }
            }
        });
        step4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 3) {
                    control.setActiveStep(3);
                } else {
                    control.setActiveSubStep(3, -1);
                }
            }
        });
        step5Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 4) {
                    control.setActiveStep(4);
                } else {
                    control.setActiveSubStep(4, -1);
                }
            }
        });
        step6Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (control.getActiveStep() != 5) {
                    control.setActiveStep(5);
                } else {
                    control.setActiveSubStep(5, -1);
                }
            }
        });
        summaryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                control.setActiveStep(6);
            }
        });

        for (int bIdx = 1; bIdx < this.step1List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step1List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(0, subStepIdx);
                }
            });
        }

        for (int bIdx = 1; bIdx < this.step2List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step2List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(1, subStepIdx);
                }
            });
        }

        for (int bIdx = 1; bIdx < this.step3List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step3List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(2, subStepIdx);
                }
            });
        }

        for (int bIdx = 1; bIdx < this.step4List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step4List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(3, subStepIdx);
                }
            });
        }

        for (int bIdx = 1; bIdx < this.step5List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step5List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(4, subStepIdx);
                }
            });
        }

        for (int bIdx = 1; bIdx < this.step6List.size(); bIdx++) {
            final int subStepIdx = bIdx - 1;
            step6List.get(bIdx).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    control.setActiveSubStep(5, subStepIdx);
                }
            });
        }
    }

    private void setupPropertyBindings() {
        control.activeStepProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
                selectStep(control.getActiveStep(), control.getActiveSubStep(control.getActiveStep()));
                introButton.setStyle("-fx-background-color: " + (control.getActiveStep() == -1 ? COLOR_STEP_HL : COLOR_STEP));
                step1Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 0 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 0 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                step2Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 1 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 1 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                step3Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 2 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 2 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                step4Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 3 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 3 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                step5Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 4 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 4 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                step6Button.setStyle("-fx-background-color: " + (control.getActiveStep() == 5 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL : COLOR_STEP_HL) : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 5 ? (control.getActiveSubStep(control.getActiveStep()) < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT) : COLOR_STEP_FONT));
                summaryButton.setStyle("-fx-background-color: " + (control.getActiveStep() == 6 ? COLOR_SUB_STEP_HL : COLOR_STEP)
                        + ";-fx-text-fill: " + (control.getActiveStep() == 6 ? COLOR_SUB_STEP_HL_FONT : COLOR_STEP_FONT));
                //summaryButton.setStyle("-fx-background-color: " + (control.getActiveStep() == 6 ? COLOR_STEP_HL : COLOR_STEP));

                prevButton.setDisable(control.getActiveStep() == 0 && control.getActiveSubStep(0) == -1);
                nextButton.setDisable(
                        control.getActiveStep() == Project.NUM_STEPS
                );

                control.updateMainWindowTitle();
            }
        });
        step1Button.disableProperty().bind(control.getProject().getStep(0).stepStartedProperty().not());
        step2Button.disableProperty().bind(control.getProject().getStep(1).stepStartedProperty().not());
        step3Button.disableProperty().bind(control.getProject().getStep(2).stepStartedProperty().not());
        step4Button.disableProperty().bind(control.getProject().getStep(3).stepStartedProperty().not());
        step5Button.disableProperty().bind(control.getProject().getStep(4).stepStartedProperty().not());
        step6Button.disableProperty().bind(control.getProject().getStep(5).stepStartedProperty().not());
        summaryButton.disableProperty().bind(control.getProject().getStep(5).stepFinishedProperty().not());

        control.activeSubStepProperty(0).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step1Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                // Iterating through substeps
                for (int bIdx = 1; bIdx < step1List.size(); bIdx++) {
                    if (bIdx < Project.NUM_SUB_STEPS[0] && control.getProject().getStep(0).getSubStep(bIdx - 1).isWizardSummary()) {
                        step1List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_WIZARD_HL : COLOR_WIZARD)
                                + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_WIZARD_HL_FONT : COLOR_WIZARD_FONT));
                    } else {
                        step1List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                                + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                    }
                }

                prevButton.setDisable(control.getActiveStep() == 0 && control.getActiveSubStep(0) == -1);
            }
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

        control.activeSubStepProperty(1).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step2Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                for (int bIdx = 1; bIdx < step2List.size(); bIdx++) {
                    if (bIdx < Project.NUM_SUB_STEPS[1] && control.getProject().getStep(1).getSubStep(bIdx - 1).isWizardSummary()) {
                        step2List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_WIZARD_HL : COLOR_WIZARD)
                                + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_WIZARD_HL_FONT : COLOR_WIZARD_FONT));
                    } else {
                        step2List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                                + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                    }
                }
//                step2Sub1Button.setStyle("-fx-background-color: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step2Sub2Button.setStyle("-fx-background-color: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step2Sub3Button.setStyle("-fx-background-color: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step2Sub4Button.setStyle("-fx-background-color: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step2Sub5Button.setStyle("-fx-background-color: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step2ReportButton.setStyle("-fx-background-color: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
            }
        });
        step2Sub1Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(0).stepStartedProperty().not());
        step2Sub2Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(1).stepStartedProperty().not());
        step2Sub3Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(2).stepStartedProperty().not());
        step2Sub4Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(3).stepStartedProperty().not());
        //step2Sub5Button.disableProperty().bind(control.getProject().getStep(1).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(2).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step3Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                for (int bIdx = 1; bIdx < step3List.size(); bIdx++) {
                    step3List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                            + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                }
//                step3Sub1Button.setStyle("-fx-background-color: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step3Sub2Button.setStyle("-fx-background-color: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step3Sub3Button.setStyle("-fx-background-color: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step3Sub4Button.setStyle("-fx-background-color: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step3Sub5Button.setStyle("-fx-background-color: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step3ReportButton.setStyle("-fx-background-color: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
            }
        });
        step3Sub1Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(0).stepStartedProperty().not());
        step3Sub2Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(1).stepStartedProperty().not());
        step3Sub3Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(2).stepStartedProperty().not());
        step3Sub4Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(3).stepStartedProperty().not());
        //step3Sub5Button.disableProperty().bind(control.getProject().getStep(2).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(3).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step4Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                for (int bIdx = 1; bIdx < step4List.size(); bIdx++) {
                    step4List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                            + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                }
//                step4Sub1Button.setStyle("-fx-background-color: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step4Sub2Button.setStyle("-fx-background-color: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step4Sub3Button.setStyle("-fx-background-color: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step4Sub4Button.setStyle("-fx-background-color: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step4ReportButton.setStyle("-fx-background-color: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
            }
        });
        step4Sub1Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(0).stepStartedProperty().not());
        step4Sub2Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(1).stepStartedProperty().not());
        step4Sub3Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(2).stepStartedProperty().not());
        step4Sub4Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(3).stepStartedProperty().not());
        //step4Sub5Button.disableProperty().bind(control.getProject().getStep(3).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(4).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step5Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                for (int bIdx = 1; bIdx < step5List.size(); bIdx++) {
                    step5List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                            + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                }
//                step5Sub1Button.setStyle("-fx-background-color: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step5Sub2Button.setStyle("-fx-background-color: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step5Sub3Button.setStyle("-fx-background-color: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step5Sub4Button.setStyle("-fx-background-color: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step5ReportButton.setStyle("-fx-background-color: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
            }
        });
        step5Sub1Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(0).stepStartedProperty().not());
        step5Sub2Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(1).stepStartedProperty().not());
        step5Sub3Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(2).stepStartedProperty().not());
        step5Sub4Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(3).stepStartedProperty().not());
        //step5Sub5Button.disableProperty().bind(control.getProject().getStep(4).getSubStep(3).stepFinishedProperty().not());

        control.activeSubStepProperty(5).addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                step6Button.setStyle("-fx-background-color: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                        + ";-fx-text-fill: " + ((int) newVal < 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                for (int bIdx = 1; bIdx < step6List.size(); bIdx++) {
                    step6List.get(bIdx).setStyle("-fx-background-color: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
                            + ";-fx-text-fill: " + ((int) newVal == (bIdx - 1) ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
                }
//                step6Sub1Button.setStyle("-fx-background-color: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 0 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step6Sub2Button.setStyle("-fx-background-color: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 1 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step6Sub3Button.setStyle("-fx-background-color: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 2 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step6Sub4Button.setStyle("-fx-background-color: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 3 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step6Sub5Button.setStyle("-fx-background-color: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 4 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
//                step6ReportButton.setStyle("-fx-background-color: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL : COLOR_SUB_STEP)
//                        + ";-fx-text-fill: " + ((int) newVal == 5 ? COLOR_SUB_STEP_HL_FONT : COLOR_SUB_STEP_FONT));
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

    public void setProceedButtonFlashing(boolean toggle) {
        if (toggle) {
            nextFT.play();
        } else {
            nextFT.stop();
        }
    }

}
