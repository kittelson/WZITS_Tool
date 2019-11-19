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

import javafx.animation.*;
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
public class Step3Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 2;

    private final VBox mainVBox = new VBox();

    private final ScrollPane centerScroll = new ScrollPane();
    private final VBox quesPane = new VBox();
    private final VBox centerPane = new VBox();

    private final GridPane stepIntroGrid = new GridPane();
    //private final BorderPane conOpsPane = new BorderPane();
    //private final BorderPane sysReqPane = new BorderPane();
    //private final BorderPane testingStratPane = new BorderPane();
    //private final BorderPane opsMaintPane = new BorderPane();
    //private final BorderPane staffTrainingPane = new BorderPane();
    //private final BorderPane sysSecurityPane = new BorderPane();
    //private final BorderPane projEvalPane = new BorderPane();
    //private final BorderPane sysBCPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();

    // all panes below this comment are new panes associated with the new step 3-6 redesign
    private final BorderPane rootPane = new BorderPane();
    private final GridPane topGridLabel = new GridPane();
    private final GridPane bottomGridNav = new GridPane();
    private final GridPane topGridMaster = new GridPane();
    private final GridPane questions = new GridPane();
    // labels and inputs associated with new redesign
    private final Label lblTitle = new Label("Step 3");
    private final Label lblStepName = new Label("System Planning and Design");

    //labels for questions
    // Navigation bar captions / Hyperlinks
    final static String[] captions = new String[]{
            "Document Concepts of Operations", "Requirements", "Test Strategy", "Operations & Maintenance","Staff Training & Needs",
            "System Security", "Evaluation", "Benefit / Cost"
    };
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length];

    // Hashmap to help map caption strings to specific pane
    HashMap<Integer, Pane> hash_map = new HashMap<Integer, Pane>();

    public Step3Panel(MainController control) {
        this.control = control;

        // -- Creating sub step panes
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


        GridPane.setHgrow(sysReqNode, Priority.ALWAYS);
        GridPane.setHgrow(docConOps,Priority.ALWAYS);
        GridPane.setHgrow(testingStratNode, Priority.ALWAYS);
        GridPane.setHgrow(opsMaintNode,Priority.ALWAYS);
        GridPane.setHgrow(staffTrainNode,Priority.ALWAYS);
        GridPane.setHgrow(sysSecurityNode,Priority.ALWAYS);
        GridPane.setHgrow(projEvaluationNode,Priority.ALWAYS);
        GridPane.setHgrow(sysBcNode,Priority.ALWAYS);

//        sysReqNode.setMaxHeight(500);
//        docConOps.setMaxHeight(500);
//        testingStratNode.setMaxHeight(500);
//        opsMaintNode.setMaxHeight(500);
//        staffTrainNode.setMaxHeight(500);
//        sysSecurityNode.setMaxHeight(500);
//        projEvaluationNode.setMaxHeight(500);
//        sysBcNode.setMaxHeight(500);

        for (int i = 0; i < captions.length; i++) {
            btnCaptions[i] = new Hyperlink(captions[i]);
//            int finalI = i;
//            final Pane displayPane = hash_map.get(i);
            final int finalI = i;
            btnCaptions[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    selectSubStep(finalI);
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
        GridPane.setHalignment(lblTitle, HPos.RIGHT);
        GridPane.setHgrow(lblStepName, Priority.ALWAYS);
        GridPane.setHgrow(lblTitle, Priority.ALWAYS);
        GridPane.setHgrow(topGridLabel, Priority.ALWAYS);
        GridPane.setHgrow(bottomGridNav, Priority.ALWAYS);
        GridPane.setHgrow(topGridMaster,Priority.ALWAYS);

        topGridLabel.add(lblTitle,0,0);
        topGridLabel.add(lblStepName,0,1);

        for (int i = 0; i < btnCaptions.length; i++) {
            btnCaptions[i].setMaxWidth(Integer.MAX_VALUE);
            GridPane.setHgrow(btnCaptions[i], Priority.ALWAYS);
            bottomGridNav.add(btnCaptions[i],i,0);
        }

        topGridMaster.add(topGridLabel,0,0);
        topGridMaster.add(bottomGridNav,0,1);
        rootPane.setTop(topGridMaster);

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

        // Width and height bindings to inform the figure title image resize behavior
        DoubleBinding widthBinding = new DoubleBinding() {
            {
                super.bind(widthProperty());
            }

            @Override
            protected double computeValue() {
                return (widthProperty().get() - 150);
            }
        };
        DoubleBinding heightBinding = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue() {
                return heightProperty().get() * 0.35;
            }
        };

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_3);
        figStep.fitWidthProperty().bind(widthBinding);
        figStep.fitHeightProperty().bind(heightBinding);
        figStep.setPreserveRatio(true);
        figStep.setSmooth(true);
        figStep.setCache(true);

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

        // Step Report Pane
        stepReportPane.setCenter(Step3TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

//        centerPane.getChildren().add(stepIntroGrid);

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);

        this.setTop(topGridMaster);
//        this.setCenter(centerPane);
        for (int i = 0; i < captions.length; i++) {
//            centerPane.getChildren().add(hash_map.get(i));
            BorderPane subsectionPane = new BorderPane();
            Label subsectionTitle = NodeFactory.createFormattedLabel(captions[i], "substep-title-label");// TODO set real style
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
        centerPane.getChildren().addAll(quesPane, fillerPane);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setMaxWidth(1400);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        BorderPane.setAlignment(centerPane, Pos.TOP_CENTER);
        borderPane.setStyle("-fx-border-radius: 5px");
        centerScroll.setFitToWidth(true);
        centerScroll.setContent(borderPane);
        this.setCenter(centerScroll);
        BorderPane.setAlignment(topGridMaster, Pos.CENTER);
        BorderPane.setAlignment(centerPane, Pos.TOP_CENTER);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {

    }

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

        control.activeSubStepProperty(stepIndex).addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
                //selectSubStep(getActiveSubStep());
                //control.getProject().setSubStepStarted(stepIndex, getActiveSubStep(), true);
                //control.getProject().setSubStepComplete(stepIndex, getActiveSubStep() - 1, true);
                //if (getActiveSubStep() == Project.NUM_SUB_STEPS[stepIndex]) {
                //    stepReportPane.setCenter(Step3TableHelper.createStepSummary(control));
                //}
                //control.checkProceed();
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
        if (control.getActiveStep() != 2) {
            return;
        }
        if (subStepIndex > -2) {
            if (!animated) {

            } else {
                int i = 0;
                double locVal = 0.0;
                double rootHeight = centerPane.getHeight();
                double scrollHeight = centerScroll.getHeight();
                double overflow = rootHeight - scrollHeight;

                while (i < subStepIndex) {
//                    double componentHeight = hash_map.get(i).getBoundsInLocal().getHeight();
                    double componentHeight = hash_map.get(i).getHeight();
                    locVal += componentHeight / overflow;
                    i++;
                }
                final Timeline timeline = new Timeline();
                final KeyValue kv = new KeyValue(centerScroll.vvalueProperty(), Math.min(locVal, 1.0));
                final KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
                Label selectedTitleLabel = (Label) ((BorderPane) hash_map.get(subStepIndex)).getTop();
                final Animation animation = new Transition() {

                    {
                        setCycleDuration(Duration.millis(2000));
                        setInterpolator(Interpolator.EASE_OUT);
                    }

                    @Override
                    protected void interpolate(double frac) {
                        // Grey RGB(89, 89, 89)
                        // Orange RGB(237, 125, 49)
                        double r = 237 - (237 - 89)*frac;
                        double g = 125 - (125-89)*frac;
                        double b = 49 + (89-49)*frac;
                        Color vColor = new Color(r/256.0, g/256.0, b/256.0, 1.0);
                        selectedTitleLabel.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                };
//                animation.play();
                selectedTitleLabel.setBackground(new Background(new BackgroundFill(Color.web("#ed7d31"), CornerRadii.EMPTY, Insets.EMPTY)));
                PauseTransition pt = new PauseTransition(Duration.millis(1000));
                SequentialTransition st = new SequentialTransition();
                st.getChildren().addAll(pt, animation);
                st.play();
            }
        }
    }

    public Node getFactSheet5Node() {
        return this.stepReportPane.getCenter();
    }
}
