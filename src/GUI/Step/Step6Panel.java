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
public class Step6Panel extends BorderPane {

    private final MainController control;

    private final int stepIndex = 5;

    private final Label lblStepName = new Label("System Operation, Maintenance, and Evaluation");
    private final Label lblTitle = new Label("Step 6");

    private final ScrollPane centerScroll = new ScrollPane();
    private final BorderPane rootPane = new BorderPane();
    private final VBox centerPane = new VBox();
    private final VBox quesPane = new VBox(10);
    private final ArrayList<Node> subStepPanesList = new ArrayList();
    private final GridPane topGridLabel = new GridPane();

    private final GridPane stepIntroGrid = new GridPane();
    private final GridPane bottomGridNav = new GridPane();
    private final GridPane topGridMaster = new GridPane();
    private final BorderPane stepReportPane = new BorderPane();
    final static String[] captions = new String[]{
            "Changing Work Zone", "Using/Sharing ITS Info", "Maintaining Adequate Staff", "Leveraging Public Support",
            "System Monitoring/Evaluation"};
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length];

    HashMap<Integer, Pane>hash_map = new HashMap<>();

    public Step6Panel(MainController control) {

        this.control = control;

        Pane changingConditionsPanel = Step6TableHelper.createChangingConditionsNode(control.getProject());
        Pane sharingInfoPanel = Step6TableHelper.createSharingInfoNode(control.getProject());
        Pane staffingPanel = Step6TableHelper.createStaffingNode(control.getProject());
        Pane publicSupportPanel = Step6TableHelper.createPublicSupportNode(control.getProject());
        Pane monitoringEvalPanel = Step6TableHelper.createMonitoringEvalNode(control.getProject());

        //mapping Pane to Int keys
        hash_map.put(0,changingConditionsPanel);
        hash_map.put(1,sharingInfoPanel);
        hash_map.put(2,staffingPanel);
        hash_map.put(3,publicSupportPanel);
        hash_map.put(4, monitoringEvalPanel);

        GridPane.setHgrow(changingConditionsPanel, Priority.ALWAYS);
        GridPane.setHgrow(sharingInfoPanel, Priority.ALWAYS);
        GridPane.setHgrow(staffingPanel, Priority.ALWAYS);
        GridPane.setHgrow(publicSupportPanel, Priority.ALWAYS);
        GridPane.setHgrow(monitoringEvalPanel, Priority.ALWAYS);

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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_6);
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

//        // Step Report Pane
        stepReportPane.setCenter(Step6TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);

        this.setTop(topGridMaster);
        for (int i = 0; i < captions.length; i++) {
            BorderPane subsectionPane = new BorderPane();
            Label subsectionTitle = NodeFactory.createFormattedLabel(captions[i], "substep-list-title-label");// TODO set real style
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
        if (control.getActiveStep() != 5) {
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
                        setCycleDuration(Duration.millis(500));
                        setInterpolator(Interpolator.EASE_OUT);
                    }

                    @Override
                    protected void interpolate(double frac) {
                        // Grey RGB(89, 89, 89)
                        // Orange RGB(237, 125, 49)
                        // Uncomment for interpolate to grey
//                        double r = 237 - (237 - 89) *  frac;
//                        double g = 125 - (125 - 89) *frac;
//                        double b = 49 + (89 - 49) * frac;
//                        Color vColor = new Color(r/256.0, g/256.0, b/256.0, 1.0);
//                        selectedTitleLabel.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                        // Uncomment for interpolate to white
                        double r = 89 + ((255 - 89) *  frac);
                        double g = 89 + ((255 - 89) * frac);
                        double b = 89 + ((255 - 89) * frac);
                        Color vColor = new Color(r/256.0, g/256.0, b/256.0, 1.0);
                        selectedTitleLabel.setBackground(new Background(new BackgroundFill(vColor, new CornerRadii(10, 10, 0, 0, false), Insets.EMPTY))); // radii, etc. from css
                    }
                };
//                animation.play();
                // Uncomment for interpolate to grey
//                selectedTitleLabel.setBackground(new Background(new BackgroundFill(Color.web("#ed7d31"), CornerRadii.EMPTY, Insets.EMPTY)));
                // Uncomment for interpolate to white
                selectedTitleLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10, 10, 0, 0, false), Insets.EMPTY)));
                PauseTransition pt = new PauseTransition(Duration.millis(2000));
                SequentialTransition st = new SequentialTransition();
                st.getChildren().addAll(pt, animation);
                st.play();
            }
        }
    }

    public Node getFactSheet8Node() {
        return this.stepReportPane.getCenter();
    }
}
