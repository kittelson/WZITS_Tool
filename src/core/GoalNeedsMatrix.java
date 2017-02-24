/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Tables.TableHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ltrask
 */
public class GoalNeedsMatrix {

    private final ObservableList<QuestionYN> qList;

    private final ObservableList<Need> needsList;

    private final LinkedHashMap<Question, Integer> qToRowMap = new LinkedHashMap();

    private final LinkedHashMap<Need, Integer> needToColMap = new LinkedHashMap();

    private final HashMap<String, SimpleBooleanProperty> includeGoalCat;

    private int[][] matrix;

    public GoalNeedsMatrix(ObservableList<QuestionYN> qList, ObservableList<Need> needsList) {
        this.qList = qList;
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            qToRowMap.put(qList.get(qIdx), qIdx);
        }
        this.needsList = needsList;
        for (int needIdx = 0; needIdx < needsList.size(); needIdx++) {
            needToColMap.put(needsList.get(needIdx), needIdx);
        }

        includeGoalCat = new HashMap();
        includeGoalCat.put(Question.GOAL_MOBILITY, TableHelper.STEP_1_SYSTEM_GOALS.get(0).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_SAFETY, TableHelper.STEP_1_SYSTEM_GOALS.get(1).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_PROD, TableHelper.STEP_1_SYSTEM_GOALS.get(2).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_REG, TableHelper.STEP_1_SYSTEM_GOALS.get(3).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_TRAVELER_INFO, TableHelper.STEP_1_SYSTEM_GOALS.get(4).answerIsYesProperty());

        matrix = new int[qList.size()][needsList.size()];

        loadDefault();

    }

    private void loadDefault() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/goalNeedsDefaultMatrix.csv")));
            String line = br.readLine();
            String[] tokens;
            int rowIdx = 0;
            while (line != null) {
                tokens = line.split(",");
                for (int colIdx = 0; colIdx < tokens.length; colIdx++) {
                    matrix[rowIdx][colIdx] = Integer.parseInt(tokens[colIdx]);
                }
                line = br.readLine();
                rowIdx++;
            }
        } catch (IOException e) {

        }
    }

    private void computeScores() {
        //for (int nIdx = 0; nIdx < needsList.size(); nIdx++) {
        for (Need n : needsList) {
            int scoreCounter = 0;
            for (Question q : qList) {
                if (q.getResponseIdx() == 1) {
                    scoreCounter += matrix[qToRowMap.get(q)][needToColMap.get(n)];
                }
            }
            n.setScore(scoreCounter);
        }
    }

    public TableView createSummaryTable() {
        computeScores();

        TableView<Need> summary = new TableView();
        summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn catCol = new TableColumn("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        catCol.setPrefWidth(175);
        catCol.setMaxWidth(175);
        catCol.setMinWidth(175);
        catCol.getStyleClass().add("col-style-center-bold");
        catCol.setSortType(SortType.ASCENDING);

        TableColumn recCol = new TableColumn("Recommended User Goals");
        recCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center-bold");
        scoreCol.setSortType(SortType.DESCENDING);

        summary.getColumns().addAll(catCol, recCol, scoreCol);

        //summary.setItems(needsList);
        for (Need n : needsList) {
            if (this.includeGoalCat.get(n.getGoal()).get()) {
                summary.getItems().add(n);
            }
        }

        summary.getSortOrder().setAll(catCol, scoreCol);
        return summary;
    }

}
