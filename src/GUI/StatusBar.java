/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.ColorHelper;
import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.Helper.ProgressIndicatorBar;
import core.FeasibilityMatrix;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class StatusBar extends TitledPane {

    private final MainController control;

    private final Label feasTitle = new Label("Feasibility Assessment");
    private final Label goalTitle = new Label("Goals");
    private final Label appTitle = new Label("Applications");
    private final Label stakeTitle = new Label("Stakeholders");

    private final ProgressBar pbFeas = new StatusPB();
    private final ProgressBar pbGoal = new StatusPB();
    private final ProgressBar pbApp = new StatusPB();
    private final ProgressBar pbStake = new StatusPB();

    private final GridPane dashboardGrid = new GridPane();
    //private final Label dashTitleLabel = new Label("Assessment Complete %");
//    private final Label goalLabel = new Label("Goals");
//    private final Label feasLabel = new Label("Feasibility");
//    private final Label appLabel = new Label("Application");
//    private final Label stake1Label = new Label("Stakeholder");
    private final ToggleButton infoToggle = new ToggleButton("Project Info");
    private final ToggleButton goalToggle = new ToggleButton("Goals");
    private final ToggleButton feasToggle = new ToggleButton("Feasibility");
    private final ToggleButton appToggle = new ToggleButton("Application");
    private final ToggleButton stakeToggle = new ToggleButton("Stakeholder");
    private final ToggleGroup tg = new ToggleGroup();

    private final SVGPath svgArrow1 = new SVGPath();
    private final SVGPath svgArrow2 = new SVGPath();
    private final SVGPath svgArrow3 = new SVGPath();
    private final SVGPath svgArrow4 = new SVGPath();
    private final SVGPath svgArrow5 = new SVGPath();
    //private final SVGPath svgArrow1 = new SVGPath();

    private final DashboardPIB pibInfo;
    private final DashboardPIB pibGoal;
    private final DashboardPIB pibFeas;
    private final DashboardPIB pibApp;
    private final DashboardPIB pibStake;

    private final BorderPane mainPane = new BorderPane();
    private final BorderPane descriptPaneOuter = new BorderPane();
    private final BorderPane descriptPaneInner = new BorderPane();

    // Information Toggled Pane
    private final GridPane infoPane = new GridPane();
    private final Label infoPaneTitle = new Label("Project Information and Summary");
    private final Label projNameLabel1 = new Label("Name: ");
    private final Label projNameLabel2 = new Label();
    private final Label projAnalystLabel1 = new Label("Analyst: ");
    private final Label projAnalystLabel2 = new Label();
    private final Label projAgencyLabel1 = new Label("Agency: ");
    private final Label projAgencyLabel2 = new Label();
    private final Label projDescriptLabel1 = new Label("Desc: ");
    private final Label projDescriptLabel2 = new Label();

    // Feasibility Assessment Toggled Pane
    private final GridPane feasPane = new GridPane();
    private final Label feasPaneTitle = new Label("WZITS Feasibility Assessment");
    private final Label feasScoreLabel1 = new Label("Feasibility Score: ");
    private final Label feasScoreLabel2 = new Label("-");
    private final Label feasScoreDesc1 = new Label(FeasibilityMatrix.DESC_30PLUS);
    private final Label feasScoreDesc2 = new Label(FeasibilityMatrix.DESC_10_TO_29);
    private final Label feasScoreDesc3 = new Label(FeasibilityMatrix.DESC_LESS_THAN_10);

    private final GridPane goalsPane = new GridPane();
    private final Label goalsPaneTitle = new Label("WZITS Top Goals");
    private final Label goalsType1Label1 = new Label("Mobility:");
    private final Label goalsType1Label2 = new Label();
    private final Label goalsType2Label1 = new Label("Productivity:");
    private final Label goalsType2Label2 = new Label();
    private final Label goalsType3Label1 = new Label("Regulatory:");
    private final Label goalsType3Label2 = new Label();
    private final Label goalsType4Label1 = new Label("Safety:");
    private final Label goalsType4Label2 = new Label();
    private final Label goalsType5Label1 = new Label("Traveler Info:");
    private final Label goalsType5Label2 = new Label();

    private final GridPane appPane = new GridPane();
    private final Label appPaneTitle = new Label("Recommended Top WZITS Applications");
    private final Label appType1Label1 = new Label("#1 ");
    private final Label appType1Label2 = new Label();
    private final Label appType2Label1 = new Label("#2 ");
    private final Label appType2Label2 = new Label();
    private final Label appType3Label1 = new Label("#3 ");
    private final Label appType3Label2 = new Label();
    private final Label appType4Label1 = new Label("#4 ");
    private final Label appType4Label2 = new Label();

    private final GridPane stakePane = new GridPane();
    private final Label stakePaneTitle = new Label("WZITS Stakeholders");
    private final Label stakePrimaryLabel1 = new Label("Top Team Member:");
    private final Label stakePrimaryLabel2 = new Label();
    private final Label stakeSecondaryLabel1 = new Label("Next Team Member:");
    private final Label stakeSecondaryLabel2 = new Label();
    private final Label stakeAdditionalLabel1 = new Label("Top Additional Stakeholder:");
    private final Label stakeAdditionalLabel2 = new Label();

    private final SimpleBooleanProperty statusEnabledProperty = new SimpleBooleanProperty(true);

    public StatusBar(MainController control) {
        this.getStyleClass().add("status-bar");
        this.control = control;

        pibInfo = new DashboardPIB(control.getProject().progressInfo);
        pibGoal = new DashboardPIB(control.getProject().progressGoal);
        pibFeas = new DashboardPIB(control.getProject().progressFeas);
        pibApp = new DashboardPIB(control.getProject().progressApp);
        pibStake = new DashboardPIB(control.getProject().progressStake);

        setupPropertyListeners();
        setupActionListeners();

        // Setting title and graphic
        setupTitleBar();

        // Setting up dashboard controller (left panel)
        setupDashboardControlToggle();

        // Setting up toggled panels
        setupInfoPanel();
        setupFeasPanel();
        setupGoalsPanel();
        setupAppPanel();
        setupStakePanel();

        this.infoToggle.setSelected(true);
        infoToggleSelected();

        dashboardGrid.getStyleClass().add("dashboard-tab-selector");
        descriptPaneOuter.getStyleClass().add("dashboard-tab");

        descriptPaneInner.setCenter(infoPane);
        descriptPaneOuter.setCenter(descriptPaneInner);
        mainPane.setLeft(dashboardGrid);
        mainPane.setCenter(descriptPaneOuter); // descriptGrid

        this.setText("Project Milestones:  ");
        this.setContent(mainPane);
        this.setExpanded(false);
    }

    private void setupPropertyListeners() {
        control.stageWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                setMinWidth(newVal.doubleValue() - 20);
                setMaxWidth(newVal.doubleValue() - 20);
            }
        });

        infoToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    infoToggle.setGraphic(svgArrow1);
                } else {
                    infoToggle.setGraphic(null);
                }
            }
        });

        feasToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    feasToggle.setGraphic(svgArrow1);
                } else {
                    feasToggle.setGraphic(null);
                }
            }
        });

        goalToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    goalToggle.setGraphic(svgArrow1);
                } else {
                    goalToggle.setGraphic(null);
                }
            }
        });

        appToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    appToggle.setGraphic(svgArrow1);
                } else {
                    appToggle.setGraphic(null);
                }
            }
        });

        stakeToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    stakeToggle.setGraphic(svgArrow1);
                } else {
                    stakeToggle.setGraphic(null);
                }
            }
        });

        statusEnabledProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                if (!newVal) {
                    setExpanded(false);
                }

                setCollapsible(newVal);

                feasTitle.setVisible(newVal);
                pbFeas.setVisible(newVal);
                goalTitle.setVisible(newVal);
                pbGoal.setVisible(newVal);
                appTitle.setVisible(newVal);
                pbApp.setVisible(newVal);
                stakeTitle.setVisible(newVal);
                pbStake.setVisible(newVal);
            }
        });

        statusEnabledProperty.bind(control.projectStartedProperty());

        this.projNameLabel2.textProperty().bind(control.getProject().nameProperty());
        this.projAgencyLabel2.textProperty().bind(control.getProject().agencyProperty());
        this.projAnalystLabel2.textProperty().bind(control.getProject().analystProperty());
        this.projDescriptLabel2.textProperty().bind(control.getProject().descriptionProperty());

        this.goalsType1Label2.textProperty().bind(control.getProject().getGoalNeedsMatrix().topMobilityGoalProperty());
        this.goalsType2Label2.textProperty().bind(control.getProject().getGoalNeedsMatrix().topProdGoalProperty());
        this.goalsType3Label2.textProperty().bind(control.getProject().getGoalNeedsMatrix().topRegGoalProperty());
        this.goalsType4Label2.textProperty().bind(control.getProject().getGoalNeedsMatrix().topSafetyGoalProperty());
        this.goalsType5Label2.textProperty().bind(control.getProject().getGoalNeedsMatrix().topTIGoalProperty());

        this.stakePrimaryLabel2.textProperty().bind(control.getProject().getStakeholderMatrix().primaryStakeholderProperty());
        this.stakeSecondaryLabel2.textProperty().bind(control.getProject().getStakeholderMatrix().secondaryStakeholderProperty());
        this.stakeAdditionalLabel2.textProperty().bind(control.getProject().getStakeholderMatrix().additionalStakeholderProperty());

        this.appType1Label2.textProperty().bind(control.getProject().getApplicationMatrix().app1Property());
        this.appType2Label2.textProperty().bind(control.getProject().getApplicationMatrix().app2Property());
        this.appType3Label2.textProperty().bind(control.getProject().getApplicationMatrix().app3Property());
        this.appType4Label2.textProperty().bind(control.getProject().getApplicationMatrix().app4Property());

        this.feasScoreLabel2.textProperty().bind(control.getProject().getFeasibilityMatrix().feasibilityProperty().asString());
        this.feasScoreLabel2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), feasScoreLabel2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), feasScoreLabel2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
                if (Integer.valueOf(newVal) >= 30) {
                    feasScoreDesc1.setStyle("-fx-font-weight: bold");
                    feasScoreDesc2.setStyle("-fx-font-weight: normal");
                    feasScoreDesc3.setStyle("-fx-font-weight: normal");
                } else if (Integer.valueOf(newVal) >= 10) {
                    feasScoreDesc1.setStyle("-fx-font-weight: normal");
                    feasScoreDesc2.setStyle("-fx-font-weight: bold");
                    feasScoreDesc3.setStyle("-fx-font-weight: normal");
                } else {
                    feasScoreDesc1.setStyle("-fx-font-weight: normal");
                    feasScoreDesc2.setStyle("-fx-font-weight: normal");
                    feasScoreDesc3.setStyle("-fx-font-weight: bold");
                }
            }
        });

        this.goalsType1Label2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), goalsType1Label2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), goalsType1Label2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        this.goalsType2Label2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), goalsType2Label2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), goalsType2Label2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        this.goalsType3Label2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), goalsType3Label2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), goalsType3Label2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        this.goalsType4Label2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), goalsType4Label2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), goalsType4Label2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        this.goalsType5Label2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), goalsType5Label2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), goalsType5Label2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        stakePrimaryLabel2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), stakePrimaryLabel2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), stakePrimaryLabel2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        stakeSecondaryLabel2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), stakeSecondaryLabel2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), stakeSecondaryLabel2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });
        stakeAdditionalLabel2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), stakeAdditionalLabel2);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), stakeAdditionalLabel2);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        });

        appType1Label2.textProperty().addListener(createFadeFXChangeListener(appType1Label2));
        appType2Label2.textProperty().addListener(createFadeFXChangeListener(appType2Label2));
        appType3Label2.textProperty().addListener(createFadeFXChangeListener(appType3Label2));
        appType4Label2.textProperty().addListener(createFadeFXChangeListener(appType4Label2));
    }

    private void setupActionListeners() {
        infoToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                infoToggleSelected();
            }
        });
        goalToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                goalToggleSelected();
            }
        });
        feasToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                feasToggleSelected();
            }
        });
        appToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                appToggleSelected();
            }
        });
        stakeToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                stake1ToggleSelected();
            }
        });
    }

    private void setupTitleBar() {
        pbFeas.progressProperty().bind(control.getProject().progressFeas);
        pbGoal.progressProperty().bind(control.getProject().progressGoal);
        pbApp.progressProperty().bind(control.getProject().progressApp);
        pbStake.progressProperty().bind(control.getProject().progressStake);

        int pad1 = 5;
        int pad2 = 25;
        int colIdx = 0;
        GridPane contentGrid = new GridPane();
        contentGrid.add(goalTitle, colIdx++, 0);
        GridPane.setMargin(goalTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbGoal, colIdx++, 0);
        GridPane.setMargin(pbGoal, new Insets(0, pad2, 0, 0));
        contentGrid.add(feasTitle, colIdx++, 0);
        GridPane.setMargin(feasTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbFeas, colIdx++, 0);
        GridPane.setMargin(pbFeas, new Insets(0, pad2, 0, 0));
        contentGrid.add(stakeTitle, colIdx++, 0);
        GridPane.setMargin(stakeTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbStake, colIdx++, 0);
        GridPane.setMargin(pbStake, new Insets(0, pad2, 0, 0));
        contentGrid.add(appTitle, colIdx++, 0);
        GridPane.setMargin(appTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbApp, colIdx++, 0);
        GridPane.setMargin(pbApp, new Insets(0, pad2, 0, 0));

        this.setGraphic(contentGrid);
        this.setContentDisplay(ContentDisplay.RIGHT);
    }

    private ChangeListener<String> createFadeFXChangeListener(final Label lbl) {
        ChangeListener<String> cl = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                FadeTransition ft1 = new FadeTransition(Duration.millis(125), lbl);
                ft1.setFromValue(1.0);
                ft1.setToValue(0.0);
                ft1.play();

                ft1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        //descriptPaneInner.setCenter(infoPane);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(125), lbl);
                        ft2.setFromValue(0.0);
                        ft2.setToValue(1.0);
                        ft2.play();
                    }
                });
            }
        };
        return cl;
    }

    private void setupDashboardControlToggle() {

        // Creating button arrows
        svgArrow1.setContent(IconHelper.SVG_STR_RIGHTARROW_SMALL);
        svgArrow2.setContent(IconHelper.SVG_STR_RIGHTARROW_SMALL);
        svgArrow3.setContent(IconHelper.SVG_STR_RIGHTARROW_SMALL);
        svgArrow4.setContent(IconHelper.SVG_STR_RIGHTARROW_SMALL);
        svgArrow5.setContent(IconHelper.SVG_STR_RIGHTARROW_SMALL);

        svgArrow1.setFill(Color.WHITE);
        svgArrow2.setFill(Color.WHITE);
        svgArrow3.setFill(Color.WHITE);
        svgArrow4.setFill(Color.WHITE);
        svgArrow5.setFill(Color.WHITE);

        // Setting pane content
        infoToggle.setMaxWidth(MainController.MAX_WIDTH);
        goalToggle.setMaxWidth(MainController.MAX_WIDTH);
        feasToggle.setMaxWidth(MainController.MAX_WIDTH);
        appToggle.setMaxWidth(MainController.MAX_WIDTH);
        stakeToggle.setMaxWidth(MainController.MAX_WIDTH);

        infoToggle.setContentDisplay(ContentDisplay.RIGHT);
        goalToggle.setContentDisplay(ContentDisplay.RIGHT);
        feasToggle.setContentDisplay(ContentDisplay.RIGHT);
        appToggle.setContentDisplay(ContentDisplay.RIGHT);
        stakeToggle.setContentDisplay(ContentDisplay.RIGHT);

        dashboardGrid.getStyleClass().add("output-dashboard");
        dashboardGrid.setMaxWidth(NodeFactory.NAVIGATOR_MAX_WIDTH);
        int rowCount = 0;
        //dashboardGrid.add(dashTitleLabel, 0, 0, 2, 1);
        dashboardGrid.add(infoToggle, 1, rowCount++);
        dashboardGrid.add(goalToggle, 1, rowCount++);
        dashboardGrid.add(feasToggle, 1, rowCount++);
        dashboardGrid.add(stakeToggle, 1, rowCount++);
        dashboardGrid.add(appToggle, 1, rowCount++);
        rowCount = 0;
        dashboardGrid.add(pibInfo, 0, rowCount++);
        dashboardGrid.add(pibGoal, 0, rowCount++);
        dashboardGrid.add(pibFeas, 0, rowCount++);
        dashboardGrid.add(pibStake, 0, rowCount++);
        dashboardGrid.add(pibApp, 0, rowCount++);

        infoToggle.setToggleGroup(tg);
        goalToggle.setToggleGroup(tg);
        feasToggle.setToggleGroup(tg);
        appToggle.setToggleGroup(tg);
        stakeToggle.setToggleGroup(tg);

        infoToggle.getStyleClass().add("dashboard-toggle");
        goalToggle.getStyleClass().add("dashboard-toggle");
        feasToggle.getStyleClass().add("dashboard-toggle");
        appToggle.getStyleClass().add("dashboard-toggle");
        stakeToggle.getStyleClass().add("dashboard-toggle");

        int pad3 = 5;
        GridPane.setMargin(infoToggle, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(goalToggle, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(feasToggle, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(appToggle, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(stakeToggle, new Insets(0, 0, 0, pad3));

        int split = 35;
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(split);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(100 - split);
        dashboardGrid.getColumnConstraints().addAll(cc1, cc2);
    }

    private void setupInfoPanel() {

        // Adding content to pane
        int rowIdx = 0;
        this.infoPane.add(this.infoPaneTitle, 0, rowIdx++, 2, 1);
        this.infoPane.add(this.projNameLabel1, 0, rowIdx);
        this.infoPane.add(this.projNameLabel2, 1, rowIdx++);
        this.infoPane.add(this.projAnalystLabel1, 0, rowIdx);
        this.infoPane.add(this.projAnalystLabel2, 1, rowIdx++);
        this.infoPane.add(this.projAgencyLabel1, 0, rowIdx);
        this.infoPane.add(this.projAgencyLabel2, 1, rowIdx++);
        this.infoPane.add(this.projDescriptLabel1, 0, rowIdx);
        this.infoPane.add(this.projDescriptLabel2, 1, rowIdx++, 1, 2);

        // Setting grid constraints
        // Setting formatting
        infoPaneTitle.setStyle("-fx-font-weight: bold");
        projNameLabel1.setStyle("-fx-font-weight: bold");
        projAnalystLabel1.setStyle("-fx-font-weight: bold");
        projAgencyLabel1.setStyle("-fx-font-weight: bold");
        projDescriptLabel1.setStyle("-fx-font-weight: bold");
        projDescriptLabel2.setWrapText(true);
    }

    private void setupFeasPanel() {
        // Adding content to pane
        int rowIdx = 0;
        this.feasPane.add(this.feasPaneTitle, 0, rowIdx++, 2, 1);
        this.feasPane.add(this.feasScoreLabel1, 0, rowIdx);
        this.feasPane.add(this.feasScoreLabel2, 1, rowIdx++);
        this.feasPane.add(this.feasScoreDesc1, 0, rowIdx++, 2, 1);
        this.feasPane.add(this.feasScoreDesc2, 0, rowIdx++, 2, 1);
        this.feasPane.add(this.feasScoreDesc3, 0, rowIdx, 2, 1);

        // Setting grid constraints
        ColumnConstraints fcc1 = new ColumnConstraints(125);
        feasPane.getColumnConstraints().addAll(fcc1);

        // Setting formatting
        feasPaneTitle.setStyle("-fx-font-weight: bold");
        feasScoreLabel1.setStyle("-fx-font-weight: bold");
        //feasScoreDesc3.setStyle("-fx-font-weight: bold");

    }

    private void setupGoalsPanel() {

        // Adding content to pane
        int rowIdx = 0;
        this.goalsPane.add(this.goalsPaneTitle, 0, rowIdx++, 2, 1);
        this.goalsPane.add(this.goalsType1Label1, 0, rowIdx);
        this.goalsPane.add(this.goalsType1Label2, 1, rowIdx++);
        this.goalsPane.add(this.goalsType2Label1, 0, rowIdx);
        this.goalsPane.add(this.goalsType2Label2, 1, rowIdx++);
        this.goalsPane.add(this.goalsType3Label1, 0, rowIdx);
        this.goalsPane.add(this.goalsType3Label2, 1, rowIdx++);
        this.goalsPane.add(this.goalsType4Label1, 0, rowIdx);
        this.goalsPane.add(this.goalsType4Label2, 1, rowIdx++);
        this.goalsPane.add(this.goalsType5Label1, 0, rowIdx);
        this.goalsPane.add(this.goalsType5Label2, 1, rowIdx++);

        // Setting grid constraints
        ColumnConstraints gcc1 = new ColumnConstraints(125);
        goalsPane.getColumnConstraints().addAll(gcc1);

        // Setting formatting
        goalsPaneTitle.setStyle("-fx-font-weight: bold");
        goalsType1Label1.setStyle("-fx-font-weight: bold");
        goalsType2Label1.setStyle("-fx-font-weight: bold");
        goalsType3Label1.setStyle("-fx-font-weight: bold");
        goalsType4Label1.setStyle("-fx-font-weight: bold");
        goalsType5Label1.setStyle("-fx-font-weight: bold");
    }

    private void setupAppPanel() {
        // Adding content to pane
        int rowIdx = 0;
        this.appPane.add(this.appPaneTitle, 0, rowIdx++, 2, 1);
        this.appPane.add(this.appType1Label1, 0, rowIdx);
        this.appPane.add(this.appType1Label2, 1, rowIdx++);
        this.appPane.add(this.appType2Label1, 0, rowIdx);
        this.appPane.add(this.appType2Label2, 1, rowIdx++);
        this.appPane.add(this.appType3Label1, 0, rowIdx);
        this.appPane.add(this.appType3Label2, 1, rowIdx++);
        this.appPane.add(this.appType4Label1, 0, rowIdx);
        this.appPane.add(this.appType4Label2, 1, rowIdx++, 1, 2);

        // Setting grid constraints
        ColumnConstraints acc1 = new ColumnConstraints(25);
        appPane.getColumnConstraints().addAll(acc1);

        // Setting formatting
        appPaneTitle.setStyle("-fx-font-weight: bold");
        appType1Label1.setStyle("-fx-font-weight: bold");
        appType2Label1.setStyle("-fx-font-weight: bold");
        appType3Label1.setStyle("-fx-font-weight: bold");
        appType4Label1.setStyle("-fx-font-weight: bold");
    }

    private void setupStakePanel() {
        // Adding content to pane
        int rowIdx = 0;
        this.stakePane.add(this.stakePaneTitle, 0, rowIdx++, 2, 1);
        this.stakePane.add(this.stakePrimaryLabel1, 0, rowIdx);
        this.stakePane.add(this.stakePrimaryLabel2, 1, rowIdx++);
        this.stakePane.add(this.stakeSecondaryLabel1, 0, rowIdx);
        this.stakePane.add(this.stakeSecondaryLabel2, 1, rowIdx++);
        this.stakePane.add(this.stakeAdditionalLabel1, 0, rowIdx);
        this.stakePane.add(this.stakeAdditionalLabel2, 1, rowIdx++);

        // Setting grid constraints
        ColumnConstraints scc1 = new ColumnConstraints(150);
        stakePane.getColumnConstraints().addAll(scc1);

        // Setting formatting
        stakePaneTitle.setStyle("-fx-font-weight: bold");
        stakePrimaryLabel1.setStyle("-fx-font-weight: bold");
        stakeSecondaryLabel1.setStyle("-fx-font-weight: bold");
        stakeAdditionalLabel1.setStyle("-fx-font-weight: bold");
    }

    private void infoToggleSelected() {
        if (!infoToggle.isSelected()) {
            infoToggle.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptPaneInner);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptPaneInner.setCenter(infoPane);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptPaneInner);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void goalToggleSelected() {
        if (!goalToggle.isSelected()) {
            goalToggle.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptPaneInner);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptPaneInner.setCenter(goalsPane);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptPaneInner);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void feasToggleSelected() {
        if (!feasToggle.isSelected()) {
            feasToggle.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptPaneInner);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptPaneInner.setCenter(feasPane);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptPaneInner);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void appToggleSelected() {
        if (!appToggle.isSelected()) {
            appToggle.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptPaneInner);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptPaneInner.setCenter(appPane);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptPaneInner);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void stake1ToggleSelected() {
        if (!stakeToggle.isSelected()) {
            stakeToggle.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptPaneInner);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptPaneInner.setCenter(stakePane);
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptPaneInner);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private class DashboardPIB extends ProgressIndicatorBar {

        public DashboardPIB(SimpleDoubleProperty prop) {
            super(prop, 1.0, "%.0f%%", true);
        }

        @Override
        protected void syncProgress() {
            super.syncProgress();
            if (workDone.get() > totalWork - 0.01) {
                bar.setStyle("-fx-accent: limegreen");
                text.setText("Ready!");
            } else {
                bar.setStyle("-fx-accent: " + ColorHelper.WZ_ORANGE);
            }
        }

    }

    private class StatusPB extends ProgressBar {

        public StatusPB() {
            this.progressProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.doubleValue() > 0.99 && oldVal.doubleValue() < 1.0) {
                        setStyle("-fx-accent: limegreen");
                    } else if (oldVal.doubleValue() > 0.99 && newVal.doubleValue() < 1.0) {
                        setStyle("-fx-accent: " + ColorHelper.WZ_ORANGE);
                    }
                }
            });
        }
    }

}
