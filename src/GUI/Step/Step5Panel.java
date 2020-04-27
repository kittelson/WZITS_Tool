/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.Tables.Step5TableHelper;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class Step5Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 4;

    private final String stepTitle = "System Deployment";

    //private final GridPane allSubStepsPane = new GridPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();

    private final GridPane stepIntroGrid = new GridPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final GridPane topGridLabel = new GridPane();
    private final GridPane bottomGridNav = new GridPane();
    private final ScrollPane centerScroll = new ScrollPane();
    private final VBox quesPane = new VBox(10);
    private final VBox centerPane = new VBox();
    private final GridPane topGridMaster = new GridPane();
    private final Label lblTitle = new Label("Step 5");
    private final Label lblStepName = new Label("System Deployment");
    private final BorderPane rootPane = new BorderPane();

    final static String[] captions = new String[]{
            "Implementing System Plans", "Scheduling Decisions", "System Acceptance Testing",
            "Handling Deployment Issues"
    };
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length];
    //hasmap to map captions to specific pane
    HashMap<Integer, Pane>hash_map = new HashMap<>();

    public Step5Panel(MainController control) {

        this.control = control;
        Pane sysPlansPane = Step5TableHelper.createSysPlansNode(control.getProject());
        Pane schedulePane = Step5TableHelper.createSchedulingNode(control.getProject());
        Pane acceptanceTrainingPane = Step5TableHelper.createAcceptanceTrainingNode(control.getProject());
        Pane deploymentIssuesPane = Step5TableHelper.createDeploymentIssuesNode(control.getProject());
        //maps pane to int keys
        hash_map.put(0,sysPlansPane);
        hash_map.put(1,schedulePane);
        hash_map.put(2,acceptanceTrainingPane);
        hash_map.put(3,deploymentIssuesPane);

        GridPane.setHgrow(sysPlansPane, Priority.ALWAYS);
        GridPane.setHgrow(schedulePane, Priority.ALWAYS);
        GridPane.setHgrow(acceptanceTrainingPane, Priority.ALWAYS);
        GridPane.setHgrow(deploymentIssuesPane, Priority.ALWAYS);

        for (int i = 0; i < captions.length; i++) {
            btnCaptions[i] = new Hyperlink(captions[i]);
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

//        Label startLabel = new Label("Step");
//        startLabel.setWrapText(true);
//        startLabel.setAlignment(Pos.BOTTOM_CENTER);
//        startLabel.setTextAlignment(TextAlignment.CENTER);
//        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
//        startLabel.setMaxWidth(MainController.MAX_WIDTH);
//        Label infoLabel = new Label("5");
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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_5);
        //figStep1.setFitWidth(1500);
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

        // Initial Applications Questions Panel
//        sysPlansPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        sysPlansPane.setCenter(Step5TableHelper.createSysPlansNode(control.getProject()));
//        sysPlansPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Application Wizard Summary Panel
//        schedulePane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        schedulePane.setCenter(Step5TableHelper.createSchedulingNode(control.getProject()));
//        schedulePane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Benefits Questions Panel
//        acceptanceTrainingPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        acceptanceTrainingPane.setCenter(Step5TableHelper.createAcceptanceTrainingNode(control.getProject()));
//        acceptanceTrainingPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Costs Questions Panel
//        deploymentIssuesPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        deploymentIssuesPane.setCenter(Step5TableHelper.createDeploymentIssuesNode(control.getProject()));
//        deploymentIssuesPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setCenter(Step5TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);

        this.setTop(topGridMaster);

        for (int i = 0; i < captions.length; i++) {
//            centerPane.getChildren().add(hash_map.get(i));
            BorderPane subsectionPane = new BorderPane();
            Label subsectionTitle = NodeFactory.createFormattedLabel(captions[i], "substep-title-label-v2");// TODO set real style
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
        borderPane.setStyle("-fx-border-radius: 5px; -fx-padding: 15;");
        centerScroll.setFitToWidth(true);
        centerScroll.setContent(borderPane);
        this.setCenter(centerScroll);
        BorderPane.setAlignment(topGridMaster, Pos.CENTER);
        BorderPane.setAlignment(centerPane, Pos.TOP_CENTER);

        setupActionListeners();
        setupPropertyBindings();

    }

    private void setupActionListeners() {}

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
                    stepReportPane.setCenter(Step5TableHelper.createStepSummary(control));
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
        if (control.getActiveStep() != 4) {
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
    public Node getFactSheet7Node() {
        return this.stepReportPane.getCenter();
    }
}
