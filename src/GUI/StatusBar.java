/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.NodeFactory;
import GUI.Helper.ProgressIndicatorBar;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ltrask
 */
public class StatusBar extends TitledPane {

    private final MainController control;

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
    private final Label dashTitleLabel = new Label("Assessment Complete %");
    private final Label goalLabel = new Label("Goals");
    private final Label feasLabel = new Label("Feasibility");
    private final Label appLabel = new Label("Application");
    private final Label stake1Label = new Label("Stakeholder");

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
        dashTitleLabel.setMaxWidth(MainController.MAX_WIDTH);
        dashTitleLabel.setAlignment(Pos.CENTER);
        dashTitleLabel.setStyle("-fx-font-weight: bold;");
        goalLabel.setMaxWidth(MainController.MAX_WIDTH);
        feasLabel.setMaxWidth(MainController.MAX_WIDTH);
        appLabel.setMaxWidth(MainController.MAX_WIDTH);
        stake1Label.setMaxWidth(MainController.MAX_WIDTH);

        dashboardGrid.getStyleClass().add("output-dashboard");
        dashboardGrid.setMaxWidth(NodeFactory.NAVIGATOR_MAX_WIDTH);
        int rowCount = 1;
        dashboardGrid.add(dashTitleLabel, 0, 0, 2, 1);
        dashboardGrid.add(goalLabel, 0, rowCount++);
        dashboardGrid.add(feasLabel, 0, rowCount++);
        dashboardGrid.add(appLabel, 0, rowCount++);
        dashboardGrid.add(stake1Label, 0, rowCount++);
        rowCount = 1;
        dashboardGrid.add(pibGoal, 1, rowCount++);
        dashboardGrid.add(pibFeas, 1, rowCount++);
        dashboardGrid.add(pibApp, 1, rowCount++);
        dashboardGrid.add(pibStake, 1, rowCount++);

        descriptTitle.setStyle("-fx-font-weight: bold;");
        rowCount = 0;
        descriptGrid.add(descriptTitle, 0, rowCount++);
        descriptGrid.add(goalDescript, 0, rowCount++);
        descriptGrid.add(feasDescript, 0, rowCount++);
        descriptGrid.add(appDescript, 0, rowCount++);
        descriptGrid.add(stakeDescript, 0, rowCount++);

        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(50);
        dashboardGrid.getColumnConstraints().addAll(cc1, cc2);

        BorderPane bpane = new BorderPane();
        bpane.setLeft(dashboardGrid);
        bpane.setCenter(descriptGrid);
        BorderPane.setMargin(dashboardGrid, new Insets(0, 25, 0, 0));
        this.setText("Project Milestones:  ");
        this.setContent(bpane);

        this.setExpanded(false);
    }

}
