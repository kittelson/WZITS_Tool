/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.Project;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author ltrask
 */
public class Step5TableHelper extends TableView {

    private static final int STEP_INDEX = 1;

    public static final int APP_WIZARD = 0;

    private static final String STEP2_TABLE_CSS = "step-one-table";

    public static Node createSysPlansNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysPlansList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qSysPlansList);
    }

    public static Node createSchedulingNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSchedulingList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qSchedulingList);
    }

    public static Node createAcceptanceTrainingNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qAcceptanceTrainingList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qAcceptanceTrainingList);
    }

    public static Node createDeploymentIssuesNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qDeploymentIssuesList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qDeploymentIssuesList);
    }

    public static Node createStepSummary(MainController mc) {
        int lfs = 16;
        final Project p = mc.getProject();
        BorderPane bPane = new BorderPane();
        bPane.setMinWidth(1000);
        bPane.setMaxWidth(1000);
        bPane.setPrefWidth(1000);
        bPane.getStyleClass().add("fact-sheet-pane");
        bPane.setTop(NodeFactory.createFormattedLabel("WZITS System Deployment", "fact-sheet-title-large"));
        final GridPane infoGrid = new GridPane();
        int infoC1Width = 115;
        int rowIdx = 0;
        infoGrid.add(NodeFactory.createFormattedLabel("State Agency:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedLabel(p.getAgency(), "fact-sheet-label"), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Analyst:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedLabel(p.getAnalyst(), "fact-sheet-label"), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Date:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedLabel(p.getDateString(), "fact-sheet-label"), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Project Name:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedLabel(p.getName(), "fact-sheet-label"), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Project Description:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedDescLabel(p.getDescription(), "fact-sheet-description", lfs, 4), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Project Limits:", "fact-sheet-label-bold"), 0, rowIdx);
        infoGrid.add(NodeFactory.createFormattedDescLabel(p.getLimits(), "fact-sheet-description", lfs, 4), 1, rowIdx++);
        infoGrid.add(NodeFactory.createFormattedLabel("Project Website:", "fact-sheet-label-bold"), 0, rowIdx);
        Hyperlink projHL = new Hyperlink(p.getUrlLink());
        projHL.getStyleClass().add("fact-sheet-label-url");
        projHL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                try {
                    Runtime.getRuntime().exec("cmd /c start " + p.getUrlLink());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        projHL.setMaxWidth(MainController.MAX_WIDTH);
        projHL.setMaxHeight(MainController.MAX_HEIGHT);
        infoGrid.add(projHL, 1, rowIdx++);

        ColumnConstraints igcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints igcc2 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        infoGrid.getColumnConstraints().addAll(igcc1, igcc2);

        VBox factSheetVBox = new VBox();
        factSheetVBox.getChildren().addAll(infoGrid);
        ScrollPane sp = new ScrollPane();

        bPane.setCenter(factSheetVBox);
        sp.setContent(bPane);
        //System.out.println("Here");
        return sp;
    }

}
