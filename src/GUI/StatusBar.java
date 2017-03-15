/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.NodeFactory;
import GUI.Helper.ProgressIndicatorBar;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.Duration;

/**
 *
 * @author ltrask
 */
public class StatusBar extends TitledPane {

    private final MainController control;

    private final SimpleDoubleProperty progressInfo = new SimpleDoubleProperty(1.0);
    private final SimpleDoubleProperty progressGoal = new SimpleDoubleProperty(0.8);
    private final SimpleDoubleProperty progressFeas = new SimpleDoubleProperty(0.6);
    private final SimpleDoubleProperty progressApp = new SimpleDoubleProperty(0.4);
    private final SimpleDoubleProperty progressStake = new SimpleDoubleProperty(0.2);

    private final Label feasTitle = new Label("Feasibility Assessment");
    private final Label goalTitle = new Label("Goals");
    private final Label appTitle = new Label("Applications");
    private final Label stakeTitle = new Label("Stakeholders");

    private final ProgressBar pbFeas = new ProgressBar();
    private final ProgressBar pbGoal = new ProgressBar();
    private final ProgressBar pbApp = new ProgressBar();
    private final ProgressBar pbStake = new ProgressBar();

    private final GridPane dashboardGrid = new GridPane();
    //private final Label dashTitleLabel = new Label("Assessment Complete %");
//    private final Label goalLabel = new Label("Goals");
//    private final Label feasLabel = new Label("Feasibility");
//    private final Label appLabel = new Label("Application");
//    private final Label stake1Label = new Label("Stakeholder");
    private final ToggleButton infoLabel = new ToggleButton("Project Info");
    private final ToggleButton goalLabel = new ToggleButton("Goals");
    private final ToggleButton feasLabel = new ToggleButton("Feasibility");
    private final ToggleButton appLabel = new ToggleButton("Application");
    private final ToggleButton stake1Label = new ToggleButton("Stakeholder");
    private final ToggleGroup tg = new ToggleGroup();

    private final ProgressIndicatorBar pibInfo = new ProgressIndicatorBar(progressInfo, 1.0, "%.0f%%", true);
    private final ProgressIndicatorBar pibGoal = new ProgressIndicatorBar(progressGoal, 1.0, "%.0f%%", true);
    private final ProgressIndicatorBar pibFeas = new ProgressIndicatorBar(progressFeas, 1.0, "%.0f%%", true);
    private final ProgressIndicatorBar pibApp = new ProgressIndicatorBar(progressApp, 1.0, "%.0f%%", true);
    private final ProgressIndicatorBar pibStake = new ProgressIndicatorBar(progressStake, 1.0, "%.0f%%", true);

    private final GridPane descriptGrid = new GridPane();
    private final Label descriptTitle = new Label("Current WZITS Tool Assessment Recommendations:");
    private final Label goalDescript = new Label("Recommended Goals:");
    private final Label feasDescript = new Label("Work Zone ITS Feasibility:");
    private final Label appDescript = new Label("Recommended Application:");
    private final Label stakeDescript = new Label("Top Stakeholder:");

//    private final Tab infoTab = new Tab("Project Info");
//    private final Tab feasTab = new Tab("Feasibility");
//    private final Tab goalsTab = new Tab("WZITS Goals");
//    private final Tab appTab = new Tab("Rec. Applications");
//    private final Tab stakeTab = new Tab("Stakeholders");
//    private final TabPane infoDash = new TabPane(infoTab, feasTab, goalsTab, appTab, stakeTab);
    private final BorderPane infoPane = new BorderPane();
    private final BorderPane feasPane = new BorderPane();
    private final BorderPane goalsPane = new BorderPane();
    private final BorderPane appPane = new BorderPane();
    private final BorderPane stakePane = new BorderPane();

    public StatusBar(MainController control) {
        this.getStyleClass().add("status-bar");
        this.control = control;
        Label lbl = new Label("Future Status Bar");
        lbl.setMaxWidth(MainController.MAX_WIDTH);
        lbl.setPadding(new Insets(0, 0, 5, 0));

        // Setting title and graphic
        pbFeas.progressProperty().bind(progressFeas);
        pbGoal.progressProperty().bind(progressGoal);
        pbApp.progressProperty().bind(progressApp);
        pbStake.progressProperty().bind(progressStake);

        int pad1 = 5;
        int pad2 = 25;
        GridPane contentGrid = new GridPane();
        contentGrid.add(feasTitle, 0, 0);
        GridPane.setMargin(feasTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbFeas, 1, 0);
        GridPane.setMargin(pbFeas, new Insets(0, pad2, 0, 0));
        contentGrid.add(goalTitle, 2, 0);
        GridPane.setMargin(goalTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbGoal, 3, 0);
        GridPane.setMargin(pbGoal, new Insets(0, pad2, 0, 0));
        contentGrid.add(appTitle, 4, 0);
        GridPane.setMargin(appTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbApp, 5, 0);
        GridPane.setMargin(pbApp, new Insets(0, pad2, 0, 0));
        contentGrid.add(stakeTitle, 6, 0);
        GridPane.setMargin(stakeTitle, new Insets(0, pad1, 0, 0));
        contentGrid.add(pbStake, 7, 0);
        GridPane.setMargin(pbStake, new Insets(0, pad2, 0, 0));
        this.setGraphic(contentGrid);
        this.setContentDisplay(ContentDisplay.RIGHT);

        // Setting pane content
        //dashTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        //dashTitleLabel.setAlignment(Pos.CENTER);
        //dashTitleLabel.setStyle("-fx-font-weight: bold;");
        infoLabel.setMaxWidth(MainController.MAX_WIDTH);
        goalLabel.setMaxWidth(MainController.MAX_WIDTH);
        feasLabel.setMaxWidth(MainController.MAX_WIDTH);
        appLabel.setMaxWidth(MainController.MAX_WIDTH);
        stake1Label.setMaxWidth(MainController.MAX_WIDTH);

        dashboardGrid.getStyleClass().add("output-dashboard");
        dashboardGrid.setMaxWidth(NodeFactory.NAVIGATOR_MAX_WIDTH);
        int rowCount = 0;
        //dashboardGrid.add(dashTitleLabel, 0, 0, 2, 1);
        dashboardGrid.add(infoLabel, 1, rowCount++);
        dashboardGrid.add(goalLabel, 1, rowCount++);
        dashboardGrid.add(feasLabel, 1, rowCount++);
        dashboardGrid.add(appLabel, 1, rowCount++);
        dashboardGrid.add(stake1Label, 1, rowCount++);
        rowCount = 0;
        dashboardGrid.add(pibInfo, 0, rowCount++);
        dashboardGrid.add(pibGoal, 0, rowCount++);
        dashboardGrid.add(pibFeas, 0, rowCount++);
        dashboardGrid.add(pibApp, 0, rowCount++);
        dashboardGrid.add(pibStake, 0, rowCount++);

        infoLabel.setToggleGroup(tg);
        goalLabel.setToggleGroup(tg);
        feasLabel.setToggleGroup(tg);
        appLabel.setToggleGroup(tg);
        stake1Label.setToggleGroup(tg);

        infoLabel.getStyleClass().add("dashboard-toggle");
        goalLabel.getStyleClass().add("dashboard-toggle");
        feasLabel.getStyleClass().add("dashboard-toggle");
        appLabel.getStyleClass().add("dashboard-toggle");
        stake1Label.getStyleClass().add("dashboard-toggle");

        int pad3 = 10;
        GridPane.setMargin(infoLabel, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(goalLabel, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(feasLabel, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(appLabel, new Insets(0, 0, 0, pad3));
        GridPane.setMargin(stake1Label, new Insets(0, 0, 0, pad3));

        infoLabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                infoToggleSelected();
            }
        });
        goalLabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                goalToggleSelected();
            }
        });
        feasLabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                feasToggleSelected();
            }
        });
        appLabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                appToggleSelected();
            }
        });
        stake1Label.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                stake1ToggleSelected();
            }
        });

        descriptTitle.setStyle("-fx-font-weight: bold;");
        rowCount = 0;
        descriptGrid.add(descriptTitle, 0, rowCount++);
        descriptGrid.add(goalDescript, 0, rowCount++);
        descriptGrid.add(feasDescript, 0, rowCount++);
        descriptGrid.add(appDescript, 0, rowCount++);
        descriptGrid.add(stakeDescript, 0, rowCount++);

        int pad4 = 10;
        GridPane.setMargin(descriptTitle, new Insets(0, 0, 0, pad4));
        GridPane.setMargin(goalDescript, new Insets(0, 0, 0, pad4));
        GridPane.setMargin(feasDescript, new Insets(0, 0, 0, pad4));
        GridPane.setMargin(appDescript, new Insets(0, 0, 0, pad4));
        GridPane.setMargin(stakeDescript, new Insets(0, 0, 0, pad4));

//        infoPane.setCenter(descriptTitle);
//        feasPane.setCenter(goalDescript);
//        goalsPane.setCenter(feasDescript);
//        appPane.setCenter(appDescript);
//        stakePane.setCenter(stakeDescript);
//        infoTab.setContent(infoPane);
//        feasTab.setContent(feasPane);
//        goalsTab.setContent(goalsPane);
//        appTab.setContent(appPane);
//        stakeTab.setContent(stakePane);
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(50);
        dashboardGrid.getColumnConstraints().addAll(cc1, cc2);

        this.infoLabel.setSelected(true);
        infoToggleSelected();

        dashboardGrid.getStyleClass().add("dashboard-tab-selector");
        descriptGrid.getStyleClass().add("dashboard-tab");

        BorderPane bpane = new BorderPane();
        bpane.setLeft(dashboardGrid);
        bpane.setCenter(descriptGrid); // descriptGrid
        BorderPane.setMargin(descriptGrid, new Insets(0, 0, 0, 0));

        this.setText("Project Milestones:  ");
        this.setContent(bpane);
        this.setExpanded(false);
    }

    private void infoToggleSelected() {
        if (!infoLabel.isSelected()) {
            infoLabel.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptGrid);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptTitle.setText("Project Information");
                    goalDescript.setText("Name:");
                    feasDescript.setText("Analyst:");
                    appDescript.setText("Future:");
                    stakeDescript.setText("Future:");
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptGrid);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void goalToggleSelected() {
        if (!goalLabel.isSelected()) {
            goalLabel.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptGrid);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptTitle.setText("WZITS Goals Assessment");
                    goalDescript.setText("Mobility:");
                    feasDescript.setText("Productivity:");
                    appDescript.setText("Regulatory:");
                    stakeDescript.setText("Safety:");
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptGrid);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void feasToggleSelected() {
        if (!feasLabel.isSelected()) {
            feasLabel.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptGrid);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptTitle.setText("WZITS Feasibility Assessment");
                    goalDescript.setText("Feasibility Score: 58");
                    feasDescript.setText("30 or more: ITS is likely to provide significant benefits and should be considered as a treatment to mitigate impacts.");
                    appDescript.setText("10 to 29: ITS may provide some benefits and should be considered as a treatment to mitigate impacts.");
                    stakeDescript.setText("Less than 10: Its may not provide enough benefit to justify the associate costs.");
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptGrid);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void appToggleSelected() {
        if (!appLabel.isSelected()) {
            appLabel.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptGrid);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptTitle.setText("WZITS Recommended Applications");
                    goalDescript.setText("#1:");
                    feasDescript.setText("#2:");
                    appDescript.setText("#3:");
                    stakeDescript.setText("#4:");
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptGrid);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

    private void stake1ToggleSelected() {
        if (!stake1Label.isSelected()) {
            stake1Label.setSelected(true);
        } else {
            FadeTransition ft1 = new FadeTransition(Duration.millis(125), descriptGrid);
            ft1.setFromValue(1.0);
            ft1.setToValue(0.0);
            ft1.play();

            ft1.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    descriptTitle.setText("WZITS Stakeholders");
                    goalDescript.setText("Primary:");
                    feasDescript.setText("Secondary:");
                    appDescript.setText("");
                    stakeDescript.setText("");
                    FadeTransition ft2 = new FadeTransition(Duration.millis(125), descriptGrid);
                    ft2.setFromValue(0.0);
                    ft2.setToValue(1.0);
                    ft2.play();
                }
            });
        }
    }

}
