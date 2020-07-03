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
import java.util.HashMap;

import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step4Panel extends BorderPane {

    private final MainController control;
    private final int stepIndex = 3;
    private final String stepTitle = "Procurement";
    private final BorderPane centerPane = new BorderPane();
    private final VBox questionPanelBox = new VBox();
    private final GridPane stepIntroGrid = new GridPane();
    private final Label lblStepNum = new Label("Step 4");
    private final Label lblStepName = new Label("Procurement");
    private final BorderPane stepReportPane = new BorderPane();
    /* adds scroll feature for new step 3-6 redesign */
    private final ScrollPane centerScroll = new ScrollPane();
    private final VBox quesPane = new VBox(10);
    private final VBox centerVpane = new VBox();
    //navigation bar captions / Hyperlinks
    final static String[] captions = new String[]{
            "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"
    };
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length + 1];
    //hashmap helps map captions strings to specific pane
    HashMap<Integer, Pane> hash_map = new HashMap<>();



    public Step4Panel(MainController control) {

        this.control = control;

        Pane directIndirectPne = Step4TableHelper.createDirectIndirectNode(control.getProject());
        Pane awdMechanismPane = Step4TableHelper.createMechanismNode(control.getProject());
        Pane rfpReqs = Step4TableHelper.createRFPNode(control.getProject());
        Pane selctVendor = Step4TableHelper.createVendorNode(control.getProject());
        //maps Pane to Int Keys
        hash_map.put(0, directIndirectPne);
        hash_map.put(1, awdMechanismPane);
        hash_map.put(2, rfpReqs);
        hash_map.put(3, selctVendor);

        GridPane.setHgrow(directIndirectPne, Priority.ALWAYS);
        GridPane.setHgrow(awdMechanismPane, Priority.ALWAYS);
        GridPane.setHgrow(rfpReqs, Priority.ALWAYS);
        GridPane.setHgrow(selctVendor, Priority.ALWAYS);

        for (int i = 0; i < captions.length; i++) {
            btnCaptions[i] = new Hyperlink(captions[i]);
            btnCaptions[i].setStyle("-fx-border-color: transparent; -fx-padding: 4 0 4 0;");
            final int finalI = i;
            btnCaptions[i].setOnAction(actionEvent -> control.setActiveSubStep(stepIndex, finalI));
        }
        btnCaptions[btnCaptions.length - 1] = new Hyperlink("Step 4 Report");
        btnCaptions[btnCaptions.length - 1].setStyle("-fx-border-color: transparent; -fx-padding: 4 0 4 0;");
        btnCaptions[btnCaptions.length - 1].setOnAction(actionEvent -> {
            control.setActiveSubStep(stepIndex, Project.NUM_SUB_STEPS[stepIndex]);
        });


        final GridPane topGridNav = new GridPane();
        final GridPane btmGridNav = new GridPane();
        final GridPane topGridLbl = new GridPane();
        btmGridNav.setPadding(new Insets(10,10,10,50));
        topGridNav.setMaxWidth(1400);
        topGridNav.setPrefWidth(500);
        topGridNav.setMinWidth(450);
        //sets grid labels and buttons
        btmGridNav.getStyleClass().add("label-styles");
        lblStepNum.getStyleClass().add("comment-label-style");
        lblStepName.getStyleClass().add("comment-sub-label");
        topGridNav.getStyleClass().add("comment-border-styles");
        lblStepNum.setMaxWidth(Integer.MAX_VALUE);
        lblStepName.setMaxWidth(Integer.MAX_VALUE);
        lblStepNum.setAlignment(Pos.CENTER);
        lblStepName.setAlignment(Pos.CENTER);
        GridPane.setHalignment(lblStepNum, HPos.RIGHT);
        GridPane.setHgrow(lblStepName, Priority.ALWAYS);
        GridPane.setHgrow(lblStepNum, Priority.ALWAYS);
        GridPane.setHgrow(topGridNav, Priority.ALWAYS);
        GridPane.setHgrow(btmGridNav, Priority.ALWAYS);
        GridPane.setHgrow(topGridLbl, Priority.ALWAYS);

        topGridLbl.add(lblStepNum,0,0);
        topGridLbl.add(lblStepName,0,1);

        for (int i = 0; i < btnCaptions.length; i++) {
            btnCaptions[i].setMaxWidth(Integer.MAX_VALUE);
            GridPane.setHgrow(btnCaptions[i], Priority.ALWAYS);
            btmGridNav.add(btnCaptions[i],i,0);
        }

        topGridNav.add(topGridLbl,0,0);
        topGridNav.add(btmGridNav,0,1);

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

        // Step Report Pane
        stepReportPane.setCenter(Step4TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);

        this.setTop(topGridNav);

        for (int i = 0; i < captions.length; i++) {
            BorderPane subsectionPane = new BorderPane();
            Label subsectionTitle = NodeFactory.createFormattedLabel(captions[i], "substep-list-title-label");
            subsectionPane.setTop(subsectionTitle);
            subsectionPane.setCenter(hash_map.get(i));
            hash_map.replace(i, subsectionPane);
        }
        for (int i = 0; i < captions.length; i++) {
            quesPane.getChildren().add(hash_map.get(i));
        }
        BorderPane fillerPane = new BorderPane();
        fillerPane.setMinHeight(500);
        fillerPane.setMaxHeight(500);
        questionPanelBox.getChildren().addAll(quesPane, fillerPane);
        questionPanelBox.setAlignment(Pos.CENTER);
        questionPanelBox.setMaxWidth(1400);
//        centerPane.setStyle("-fx-border-radius: 5px");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(questionPanelBox);
        BorderPane.setAlignment(questionPanelBox, Pos.TOP_CENTER);
        borderPane.setStyle("-fx-border-radius: 5px; -fx-padding: 15;");
        topGridNav.maxWidthProperty().bind(borderPane.widthProperty());
        topGridNav.setAlignment(Pos.TOP_LEFT);
        centerScroll.setFitToWidth(true);
        centerScroll.setContent(borderPane);
        centerPane.setCenter(centerScroll);
        this.setCenter(centerPane);
        BorderPane.setAlignment(topGridNav, Pos.CENTER);
        BorderPane.setAlignment(questionPanelBox, Pos.TOP_CENTER);

        setupActionListeners();
        setupPropertyBindings();

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

//        int numPanes = getNumSubSteps() + 2;
//        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(questionPanelBox, Priority.ALWAYS);  //allSubStepsPane

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {

    }

    private HashMap<Integer, Double> pings;
    private boolean bypassAutoScroll = false;
    private boolean bypassScrollUpdateCheck = false;

    private void setupPropertyBindings() {

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

        centerScroll.vvalueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (pings == null) {
                    pings = new HashMap<>();
                    pings.put(-1, -999.0);
                    for (int subStepIndex = 0; subStepIndex < Project.NUM_SUB_STEPS[stepIndex]; subStepIndex++) {
                        int i = 0;
                        double locVal = 0.0;
                        double rootHeight = questionPanelBox.getHeight();
                        double scrollHeight = centerScroll.getHeight();
                        double overflow = rootHeight - scrollHeight;
                        while (i < subStepIndex) {
                            double componentHeight = hash_map.get(i).getHeight();
                            locVal += componentHeight / overflow;
                            i++;
                        }
                        pings.put(subStepIndex, Math.min(locVal, 1.0));
                    }
                }
                if (bypassScrollUpdateCheck) {
                    return;
                }
                if (getActiveSubStep() < (Project.NUM_SUB_STEPS[stepIndex]-1) && centerScroll.getVvalue() > pings.get(getActiveSubStep() + 1) - 0.05) {
                    bypassAutoScroll = true;
                    try {
                        control.setActiveSubStep(stepIndex, getActiveSubStep() + 1);
                    } catch(Exception e) {
                        System.out.println("Exception at Step " + (stepIndex + 1) + " while updating substep via scrolling");
                        e.printStackTrace();
                    } finally {
                        bypassAutoScroll = false;
                    }
                } else if (centerScroll.getVvalue() <= pings.get(getActiveSubStep() - 1) && getActiveSubStep() > 0) {
                    bypassAutoScroll = true;
                    try {
                        control.setActiveSubStep(stepIndex, getActiveSubStep() - 1);
                    } catch(Exception e) {
                        System.out.println("Exception at Step " + (stepIndex + 1) + " while updating substep via scrolling");
                        e.printStackTrace();
                    } finally {
                        bypassAutoScroll = false;
                    }
                }
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
        if (control.getActiveStep() != 3) {
            return;
        }
        if (subStepIndex > -2) {
            if (!animated) {
                if (subStepIndex == Project.NUM_SUB_STEPS[stepIndex]) {
                    if (centerPane.getCenter() != stepReportPane) {
                        centerPane.setCenter(stepReportPane);
                    }
                } else {
                    if (centerPane.getCenter() != centerScroll) {
                        centerPane.setCenter(centerScroll);
                    }
                    scrollToSubstep(subStepIndex, animated);
                }
            } else {
                if (subStepIndex == Project.NUM_SUB_STEPS[stepIndex]) {
                    if (centerPane.getCenter() != stepReportPane) {
                        FadeTransition ft1 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                        ft1.setFromValue(1.0);
                        ft1.setToValue(0.0);
                        ft1.play();
                        ft1.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent ae) {
                                centerPane.setCenter(stepReportPane);
                                FadeTransition ft2 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                                ft2.setFromValue(0.0);
                                ft2.setToValue(1.0);
                                ft2.play();
                            }
                        });
                    }
                } else {
                    if (centerPane.getCenter() != centerScroll) {
                        FadeTransition ft1 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                        ft1.setFromValue(1.0);
                        ft1.setToValue(0.0);
                        ft1.play();
                        ft1.setOnFinished(ae -> {
                            centerPane.setCenter(centerScroll);
                            FadeTransition ft2 = new FadeTransition(Duration.millis(MainController.FADE_TIME), centerPane);
                            ft2.setFromValue(0.0);
                            ft2.setToValue(1.0);
                            ft2.setOnFinished(actionEvent -> {scrollToSubstep(subStepIndex, animated);});
                            ft2.play();
                        });
                    } else {
                        scrollToSubstep(subStepIndex, animated);
                    }
                }
            }
            for (int si = 0; si <= Project.NUM_SUB_STEPS[stepIndex]; si++) {
                BorderPane currQuestionCard = (BorderPane) hash_map.get(si);
                if (currQuestionCard != null) {
                    Label selectedTitleLabel = (Label) currQuestionCard.getTop();
                    if (si == subStepIndex) {
                        selectedTitleLabel.getStyleClass().setAll("substep-list-title-label-selected");
                    } else {
                        selectedTitleLabel.getStyleClass().setAll("substep-list-title-label");

                    }
                }
                if (si == subStepIndex) {
                    btnCaptions[si].setStyle("-fx-font-weight: bold; -fx-text-fill: #ED7D31; -fx-underline: true");
                } else {
                    btnCaptions[si].setStyle("-fx-font-weight: normal; -fx-underline: false");
                    if (si < subStepIndex) {
                        btnCaptions[si].setVisited(true);
                    } else {
                        btnCaptions[si].setVisited(false);
                    }
                }
            }
        }
    }

    private void scrollToSubstep(int subStepIndex, boolean animated) {
        if (hash_map.get(subStepIndex) == null) {
            return;
        }
        int i = 0;
        double locVal = 0.0;
        double rootHeight = questionPanelBox.getHeight();
        double scrollHeight = centerScroll.getHeight();
        double overflow = rootHeight - scrollHeight;

        while (i < subStepIndex) {
//                    double componentHeight = hash_map.get(i).getBoundsInLocal().getHeight();
            double componentHeight = hash_map.get(i).getHeight();
            locVal += componentHeight / overflow;
            i++;
        }
        if (!animated) {
            centerScroll.setVvalue(Math.min(locVal, 1.0));
        } else {
            if (!bypassAutoScroll && centerScroll.getVvalue() != Math.min(locVal, 1.0)) {
                bypassScrollUpdateCheck = true;
                try {
                    final Timeline timeline = new Timeline();
                    final KeyValue kv = new KeyValue(centerScroll.vvalueProperty(), Math.min(locVal, 1.0));
                    final KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
                    timeline.getKeyFrames().add(kf);
                    timeline.setOnFinished(actionEvent -> {bypassScrollUpdateCheck = false;});
                    timeline.play();
                } catch (Exception e) {
                    System.out.println("Exception at Step " + (stepIndex + 1) + " while playing autoscroll transition");
                    e.printStackTrace();
                    bypassScrollUpdateCheck = false;
                }
            }
        }
    }

    public Node getFactSheet6Node() {return this.stepReportPane.getCenter();}
}
