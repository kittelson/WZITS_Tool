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
import GUI.Tables.Step4TableHelper;
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
public class Step4Panel extends BorderPane {

    private final MainController control;
    private final int stepIndex = 3;
    private final String stepTitle = "Procurement";
    private final VBox centerPane = new VBox();
    private final GridPane stepIntroGrid = new GridPane();
    private final GridPane topGridNav = new GridPane();
    private final GridPane btmGridNav = new GridPane();
    private final GridPane topGridLbl = new GridPane();
    private final Label lblStepNum = new Label("Step 4");
    private final Label lblStepName = new Label("Procurement");
    private final BorderPane rootPane = new BorderPane();
    private final BorderPane stepReportPane = new BorderPane();
    private final ArrayList<Node> subStepPanesList = new ArrayList();
    /* adds scroll feature for new step 3-6 redesign */
    private final ScrollPane centerScroll = new ScrollPane();
    private final VBox quesPane = new VBox(10);
    private final VBox centerVpane = new VBox();
    //navigation bar captions / Hyperlinks
    final static String[] captions = new String[]{
            "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"
    };
    final Hyperlink[] btnCaptions = new Hyperlink[captions.length];
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
            final int finalI = i;
            btnCaptions[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {selectSubStep(finalI);}});}

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
        rootPane.setTop(topGridNav);

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
        centerPane.getChildren().addAll(quesPane, fillerPane);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setMaxWidth(1400);
//        centerPane.setStyle("-fx-border-radius: 5px");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        BorderPane.setAlignment(centerPane, Pos.TOP_CENTER);
        borderPane.setStyle("-fx-border-radius: 5px; -fx-padding: 15;");
        centerScroll.setFitToWidth(true);
        centerScroll.setContent(borderPane);
        this.setCenter(centerScroll);
        BorderPane.setAlignment(topGridNav, Pos.CENTER);
        BorderPane.setAlignment(centerPane, Pos.TOP_CENTER);

        setupActionListeners();
        setupPropertyBindings();

//        mainVBox.setFillWidth(true);
//        Label startLabel = new Label("Step");
//        startLabel.setWrapText(true);
//        startLabel.setAlignment(Pos.BOTTOM_CENTER);
//        startLabel.setTextAlignment(TextAlignment.CENTER);
//        startLabel.setMaxHeight(MainController.MAX_HEIGHT);
//        startLabel.setMaxWidth(MainController.MAX_WIDTH);
//        Label infoLabel = new Label("4");
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

        ImageView figStep = new ImageView(IconHelper.FIG_FLOW_STEP_4);
        //figStep1.setFitWidth(1500);
        figStep.fitWidthProperty().bind(widthBinding);
        figStep.fitHeightProperty().bind(heightBinding);
        figStep.setPreserveRatio(true);
        figStep.setSmooth(true);
        figStep.setCache(true);

//        stepIntroGrid.add(startLabel, 0, 0);
//        stepIntroGrid.add(infoLabel, 0, 1);
//        stepIntroGrid.add(instructionLabel, 1, 1);
//        stepIntroGrid.add(figStep, 1, 0);
//        stepIntroGrid.setStyle("-fx-background-color: white");

//        GridPane.setHgrow(instructionLabel, Priority.ALWAYS);

        int subStepIndex = 1;
        // Initial Applications Questions Panel
//        directIndirectPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        directIndirectPane.setCenter(Step4TableHelper.createDirectIndirectNode(control.getProject()));
//        directIndirectPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Application Wizard Summary Panel
//        mechanismPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        mechanismPane.setCenter(Step4TableHelper.createMechanismNode(control.getProject()));
//        mechanismPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Benefits Questions Panel
//        rfpNode.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        rfpNode.setCenter(Step4TableHelper.createRFPNode(control.getProject()));
//        rfpNode.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));
//
//        // Costs Questions Panel
//        vendorSelectionPane.setTop(NodeFactory.createFormattedLabel(Project.STEP_NAMES[stepIndex][subStepIndex++], "substep-title-label"));
//        vendorSelectionPane.setCenter(Step4TableHelper.createVendorNode(control.getProject()));
//        vendorSelectionPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

        // Step Report Pane
        stepReportPane.setTop(NodeFactory.createFormattedLabel("Report: " + stepTitle, "substep-title-label"));
//        stepReportPane.setCenter(Step4TableHelper.createStepSummary(control));
        stepReportPane.setBottom(NodeFactory.createFormattedLabel("", "substep-title-label"));

//        mainVBox.getChildren().addAll(centerPane);  //allSubStepsPane
//        int i = 0;
//        allSubStepsPane.add(stepIntroGrid, i++, 0);
//        allSubStepsPane.add(directIndirectPane, i++, 0);
//        allSubStepsPane.add(mechanismPane, i++, 0);
//        allSubStepsPane.add(rfpNode, i++, 0);
//        allSubStepsPane.add(vendorSelectionPane, i++, 0);
//        allSubStepsPane.add(stepReportPane, i++, 0);
        this.subStepPanesList.add(stepIntroGrid);
//        this.subStepPanesList.add(directIndirectPane);
//        this.subStepPanesList.add(mechanismPane);
//        this.subStepPanesList.add(rfpNode);
//        this.subStepPanesList.add(vendorSelectionPane);
        this.subStepPanesList.add(stepReportPane);

//        int numPanes = getNumSubSteps() + 2;
//        for (int colIdx = 0; colIdx < numPanes; colIdx++) {
//            ColumnConstraints tcc = new ColumnConstraints();
//            tcc.setPercentWidth(100.0 / numPanes);
//            allSubStepsPane.getColumnConstraints().add(tcc);
//        }
        GridPane.setVgrow(stepIntroGrid, Priority.ALWAYS);
        GridPane.setVgrow(stepReportPane, Priority.ALWAYS);
        VBox.setVgrow(centerPane, Priority.ALWAYS);  //allSubStepsPane

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
        if (control.getActiveStep() != 3) {
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
                VBox selectedBodyLabel = (VBox) ((BorderPane) hash_map.get(subStepIndex)).getCenter();
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
    public Node getFactSheet6Node() {return this.stepReportPane.getCenter();}
}
