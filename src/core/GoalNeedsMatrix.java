/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.CellSpan;
import GUI.Helper.CellSpanTableView;
import GUI.Helper.SpanModel;
import GUI.Tables.TableHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
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

    private int numMob;
    private int numProd;
    private int numReg;
    private int numSafety;
    private int numTravInfo;

    private int[][] matrix;

    public GoalNeedsMatrix(ObservableList<QuestionYN> qList, ObservableList<Need> needsList, ObservableList<QuestionYN> majorGoalsList) {
        this.qList = qList;
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            qToRowMap.put(qList.get(qIdx), qIdx);
        }
        this.needsList = needsList;
        for (int needIdx = 0; needIdx < needsList.size(); needIdx++) {
            needToColMap.put(needsList.get(needIdx), needIdx);
        }

        includeGoalCat = new HashMap();
        includeGoalCat.put(Question.GOAL_MOBILITY, majorGoalsList.get(0).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_SAFETY, majorGoalsList.get(1).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_PROD, majorGoalsList.get(2).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_REG, majorGoalsList.get(3).answerIsYesProperty());
        includeGoalCat.put(Question.GOAL_TRAVELER_INFO, majorGoalsList.get(4).answerIsYesProperty());

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

    public void computeScores() {
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

        numMob = numProd = numReg = numSafety = numTravInfo = 0;
        // Finding Top Scores
        int topMobScore = 0;
        Need topMobNeed = null;
        int topProdScore = 0;
        Need topProdNeed = null;
        int topRegScore = 0;
        Need topRegNeed = null;
        int topSafetyScore = 0;
        Need topSafetyNeed = null;
        int topTIScore = 0;
        Need topTINeed = null;
        for (Need n : needsList) {
            switch (n.getGoal()) {
                case Question.GOAL_MOBILITY:
                    numMob++;
                    if (n.getScore() > topMobScore) {
                        topMobScore = n.getScore();
                        topMobNeed = n;
                    }
                    break;
                case Question.GOAL_PROD:
                    numProd++;
                    if (n.getScore() > topProdScore) {
                        topProdScore = n.getScore();
                        topProdNeed = n;
                    }
                    break;
                case Question.GOAL_REG:
                    numReg++;
                    if (n.getScore() > topRegScore) {
                        topRegScore = n.getScore();
                        topRegNeed = n;
                    }
                    break;
                case Question.GOAL_SAFETY:
                    numSafety++;
                    if (n.getScore() > topSafetyScore) {
                        topSafetyScore = n.getScore();
                        topSafetyNeed = n;
                    }
                    break;
                case Question.GOAL_TRAVELER_INFO:
                    numTravInfo++;
                    if (n.getScore() > topTIScore) {
                        topTIScore = n.getScore();
                        topTINeed = n;
                    }
                    break;

            }
        }
        this.topMobilityGoal.set(topMobNeed != null ? topMobNeed.getDescription() : "");
        this.topProdGoal.set(topProdNeed != null ? topProdNeed.getDescription() : "");
        this.topRegGoal.set(topRegNeed != null ? topRegNeed.getDescription() : "");
        this.topSafetyGoal.set(topSafetyNeed != null ? topSafetyNeed.getDescription() : "");
        this.topTIGoal.set(topTINeed != null ? topTINeed.getDescription() : "");
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
        catCol.getStyleClass().add("col-style-center");
        catCol.setSortType(SortType.ASCENDING);

        TableColumn recCol = new TableColumn("Recommended User Goals");
        recCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");
        scoreCol.setSortType(SortType.DESCENDING);

        summary.getColumns().addAll(catCol, recCol, scoreCol);

        //summary.setItems(needsList);
        for (Need n : needsList) {
            if (this.includeGoalCat.get(n.getGoal()).get()) {
                summary.getItems().add(n);
            }
        }

        summary.getSortOrder().setAll(catCol, scoreCol);

        summary.setPlaceholder(new Label("Steps 1.2 - 1.4 must be completed to view."));

        return summary;
    }

    public TableView createSummarySpanTable() {
        computeScores();

        final CellSpanTableView<Need> summary = new CellSpanTableView();
        //summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn catCol = new TableColumn("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        catCol.setPrefWidth(175);
        catCol.setMaxWidth(175);
        catCol.setMinWidth(175);
        catCol.getStyleClass().add("col-style-center");
        catCol.setSortType(SortType.ASCENDING);

        TableColumn recCol = new TableColumn("Recommended User Goals");
        recCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");
        scoreCol.setSortType(SortType.DESCENDING);

        summary.getColumns().addAll(catCol, recCol, scoreCol);

        //summary.setItems(needsList);
        for (Need n : needsList) {
            if (this.includeGoalCat.get(n.getGoal()).get()) {
                summary.getItems().add(n);
            }
        }

//        final int finNumMob = numMob;
//        final int finNumProd = numProd;
//        final int finNumReg = numReg;
//        final int finNumSafety = numSafety;
//        final int finNumTravInfo = numTravInfo;
        summary.setSpanModel(new SpanModel() {
            private final CellSpan mobSpan = new CellSpan(numMob, 1);
            private final CellSpan prodSpan = new CellSpan(numProd, 1);
            private final CellSpan regSpan = new CellSpan(numReg, 1);
            private final CellSpan safetySpan = new CellSpan(numSafety, 1);
            private final CellSpan travInfoSpan = new CellSpan(numTravInfo, 1);

            @Override
            public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {
                if (rowIndex == 0) {
                    return mobSpan;
                }
                if (rowIndex == numMob + 1) {
                    return prodSpan;
                }
                if (rowIndex == numMob + numProd + 2) {
                    return regSpan;
                }
                if (rowIndex == numMob + numProd + numReg + 3) {
                    return safetySpan;
                }
                if (rowIndex == numMob + numProd + numReg + numSafety + 4) {
                    return travInfoSpan;
                }
                return null;
                //return summary.getItems().get(rowIndex).getSpan();
            }

            @Override
            public boolean isCellSpanEnabled() {
                return true;
            }
        });

        summary.getSortOrder().setAll(catCol, scoreCol);

        summary.setPlaceholder(new Label("Steps 1.2 - 1.4 must be completed to view."));

        return summary;
    }

    private final StringProperty topMobilityGoal = new SimpleStringProperty();

    public String getTopMobilityGoal() {
        return topMobilityGoal.get();
    }

    public void setTopMobilityGoal(String value) {
        topMobilityGoal.set(value);
    }

    public StringProperty topMobilityGoalProperty() {
        return topMobilityGoal;
    }
    private final StringProperty topProdGoal = new SimpleStringProperty();

    public String getTopProdGoal() {
        return topProdGoal.get();
    }

    public void setTopProdGoal(String value) {
        topProdGoal.set(value);
    }

    public StringProperty topProdGoalProperty() {
        return topProdGoal;
    }
    private final StringProperty topRegGoal = new SimpleStringProperty();

    public String getTopRegGoal() {
        return topRegGoal.get();
    }

    public void setTopRegGoal(String value) {
        topRegGoal.set(value);
    }

    public StringProperty topRegGoalProperty() {
        return topRegGoal;
    }
    private final StringProperty topSafetyGoal = new SimpleStringProperty();

    public String getTopSafetyGoal() {
        return topSafetyGoal.get();
    }

    public void setTopSafetyGoal(String value) {
        topSafetyGoal.set(value);
    }

    public StringProperty topSafetyGoalProperty() {
        return topSafetyGoal;
    }
    private final StringProperty topTIGoal = new SimpleStringProperty();

    public String getTopTIGoal() {
        return topTIGoal.get();
    }

    public void setTopTIGoal(String value) {
        topTIGoal.set(value);
    }

    public StringProperty topTIGoalProperty() {
        return topTIGoal;
    }

}
