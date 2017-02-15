/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Application;
import core.Question;
import core.QuestionYN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ltrask
 */
public class Step2Table extends TableView {

    private static final int STEP_INDEX = 1;

    public static TableView createPageTable(int page, int questionsPerPage) {

        int startRow = page * questionsPerPage;
        int endRow = Math.min((page + 1) * questionsPerPage, TableHelper.getNumberOfQuestionsByStep(STEP_INDEX));

        TableView<QuestionYN> table = new TableView();
        table.setEditable(true);

        // Setting up table columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn indexCol = new TableColumn("#");
        indexCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        indexCol.setPrefWidth(25);
        indexCol.setMaxWidth(25);
        indexCol.setMinWidth(25);
        indexCol.getStyleClass().add("col-style-center");

        TableColumn goalCol = new TableColumn("WZITS Goal Category");
        goalCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        goalCol.setPrefWidth(200);
        goalCol.setMaxWidth(200);
        goalCol.setMinWidth(200);
        goalCol.getStyleClass().add("col-style-center");

        TableColumn questionCol = new TableColumn("Input Question");
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));

        TableColumn responseCol = new TableColumn("User Response");
        responseCol.setPrefWidth(150);
        responseCol.setMaxWidth(150);
        responseCol.setMinWidth(150);
        responseCol.setCellValueFactory(new PropertyValueFactory<>("responseIdx"));
//        responseCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(Question.yesNoConverter, FXCollections.observableArrayList(-1, 0, 1)));
//        responseCol.setOnEditCommit(new EventHandler<CellEditEvent<Question, Integer>>() {
//            @Override
//            public void handle(CellEditEvent<Question, Integer> t) {
//                ((Question) t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponseIdx(t.getNewValue());
//            }
//        });

        TableColumn yesCol = new TableColumn("Yes");
        yesCol.setCellValueFactory(new PropertyValueFactory<>("responseIdx"));
        //yesCol.setCellFactory(CheckBoxTableCell.forTableColumn((Integer idx) -> new SimpleBooleanProperty(idx == 1 ? Boolean.TRUE : Boolean.FALSE)));
        yesCol.setCellValueFactory(CheckBoxTableCell.forTableColumn(yesCol));
        yesCol.setOnEditCommit(new EventHandler<CellEditEvent<Question, Integer>>() {
            @Override
            public void handle(CellEditEvent<Question, Integer> t) {
                ((Question) t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponseIdx(t.getNewValue());
            }
        });
        TableColumn noCol = new TableColumn("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("responseIdx"));
        //noCol.setCellFactory(CheckBoxTableCell.forTableColumn((Integer idx) -> new SimpleBooleanProperty(idx == 0 ? Boolean.TRUE : Boolean.FALSE)));
        noCol.setCellValueFactory(CheckBoxTableCell.forTableColumn(noCol));
        noCol.setOnEditCommit(new EventHandler<CellEditEvent<Question, Integer>>() {
            @Override
            public void handle(CellEditEvent<Question, Integer> t) {
                ((Question) t.getTableView().getItems().get(t.getTablePosition().getRow())).setResponseIdx(t.getNewValue());
            }
        });
        yesCol.setPrefWidth(75);
        yesCol.setMaxWidth(75);
        yesCol.setMinWidth(75);
        noCol.setPrefWidth(75);
        noCol.setMaxWidth(75);
        noCol.setMinWidth(75);
        responseCol.getColumns().addAll(yesCol, noCol);

        table.getColumns().addAll(indexCol, goalCol, questionCol, responseCol);

        // Setting Table Content
        ObservableList<QuestionYN> stepQuestions = FXCollections.observableArrayList(TableHelper.getStepQuestions(STEP_INDEX).subList(startRow, endRow));
        table.setItems(stepQuestions);

        return table;

    }

    public static TableView createSummaryTable() {
        TableView summary = new TableView();
        summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn appCol = new TableColumn("Recommended WZITS Applications");
        appCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");

        summary.getColumns().addAll(appCol, scoreCol);

        ObservableList<Application> stepSummary = TableHelper.getStepSummary(STEP_INDEX);
        summary.setItems(stepSummary);
        return summary;
    }

    public static int getPageCount(int stepIdx, int questionsPerPage) {
        return Math.floorDiv(TableHelper.getNumberOfQuestionsByStep(stepIdx), questionsPerPage) + 1;
    }

    public static int getPageCount(int stepIdx) {
        return getPageCount(stepIdx, 10);
    }
}
