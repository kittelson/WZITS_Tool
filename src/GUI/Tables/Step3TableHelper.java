/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.Project;
import core.QuestionYN;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
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
public class Step3TableHelper extends TableView {

    private static final int STEP_INDEX = 2;

    private static final String STEP3_TABLE_CSS = "step-one-table";

    public static Node createConOpsNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qConOpsList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qConOpsList);
    }

    public static Node createSysReqNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysReqList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qSysReqList);
    }

    public static Node createTestingStratNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qTestingStratList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qTestingStratList);
    }

    public static Node createOpsMaintNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qOpsMaintList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qOpsMaintList);
    }

    public static Node createStaffTrainingNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qStaffTrainingList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qStaffTrainingList);
    }

    public static Node createSysSecurityNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysSecurityList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qSysSecurityList);
    }

    public static Node createProjEvalNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qProjectEvalList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qProjectEvalList);
    }

    public static Node createSysBCNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysBCList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qSysBCList);
    }

    public static Node createStepSummary(MainController mc) {
        int lfs = 16;
        final Project p = mc.getProject();
        BorderPane bPane = new BorderPane();
        bPane.setMinWidth(1000);
        bPane.setMaxWidth(1000);
        bPane.setPrefWidth(1000);
        bPane.getStyleClass().add("fact-sheet-pane");
        bPane.setTop(NodeFactory.createFormattedLabel("Fact Sheet #5: WZITS Project Documentation", "fact-sheet-title-large"));
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
                if (p.getUrlLink() != null) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start " + p.getUrlLink());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        projHL.setMaxWidth(MainController.MAX_WIDTH);
        projHL.setMaxHeight(MainController.MAX_HEIGHT);
        infoGrid.add(projHL, 1, rowIdx++);

        ColumnConstraints igcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints igcc2 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        infoGrid.getColumnConstraints().addAll(igcc1, igcc2);

        GridPane docGrid = new GridPane();
        docGrid.add(NodeFactory.createFormattedLabel(MainController.STEP_TITLES[STEP_INDEX], "fact-sheet-title-stake-grid"), 0, 0, 4, 1);
        rowIdx = 1;
        docGrid.add(NodeFactory.createFormattedLabel("Category", "fact-sheet-title-core-team"), 0, rowIdx, 1, 1);
        docGrid.add(NodeFactory.createFormattedLabel("Question", "fact-sheet-title-core-team"), 1, rowIdx, 1, 1);
        docGrid.add(NodeFactory.createFormattedLabel("Response", "fact-sheet-title-core-team"), 2, rowIdx, 1, 1);
        docGrid.add(NodeFactory.createFormattedLabel("Comment", "fact-sheet-title-core-team"), 3, rowIdx++, 1, 1);

        ArrayList<ObservableList<QuestionYN>> stepLists = new ArrayList();

        stepLists.add(p.getQGen().qConOpsList);
        stepLists.add(p.getQGen().qSysReqList);
        stepLists.add(p.getQGen().qTestingStratList);
        stepLists.add(p.getQGen().qOpsMaintList);
        stepLists.add(p.getQGen().qStaffTrainingList);
        stepLists.add(p.getQGen().qSysSecurityList);
        stepLists.add(p.getQGen().qProjectEvalList);
        stepLists.add(p.getQGen().qSysBCList);

        ObservableList<QuestionYN> qList;

        for (int listIdx = 0; listIdx < stepLists.size(); listIdx++) {
            qList = stepLists.get(listIdx);
            docGrid.add(NodeFactory.createFormattedLabel(Project.STEP_NAMES[STEP_INDEX][listIdx + 1], "fact-sheet-label-center"), 0, rowIdx, 1, qList.size());
            for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
                docGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getQuestionText(), "fact-sheet-label"), 1, rowIdx);
                docGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getAnswerString(), "fact-sheet-label-center"), 2, rowIdx);
                docGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getComment(), "fact-sheet-label"), 3, rowIdx++);
            }
        }

        docGrid.getColumnConstraints().add(new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.CENTER, true));
        docGrid.getColumnConstraints().add(new ColumnConstraints(300, 300, 300, Priority.NEVER, HPos.CENTER, true));
        docGrid.getColumnConstraints().add(new ColumnConstraints(100, 100, 100, Priority.NEVER, HPos.CENTER, true));
        docGrid.getColumnConstraints().add(new ColumnConstraints(1, 200, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));

        VBox factSheetVBox = new VBox();
        factSheetVBox.getChildren().addAll(infoGrid, docGrid);
        ScrollPane sp = new ScrollPane();

        bPane.setCenter(factSheetVBox);
        sp.setContent(bPane);
        //System.out.println("Here");
        return sp;
    }

}
