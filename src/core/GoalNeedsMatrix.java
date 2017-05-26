/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.NodeFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Predicate;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 *
 * @author ltrask
 */
public class GoalNeedsMatrix implements Serializable {

    private static final long serialVersionUID = 123456789L;

    public ObservableList<QuestionYN> qList;

    public ObservableList<Need> needsList;

    private LinkedHashMap<Question, Integer> qToRowMap = new LinkedHashMap();

    private LinkedHashMap<Need, Integer> needToColMap = new LinkedHashMap();

    private final HashMap<String, SimpleIntegerProperty> hasGoalCat;

    //private int numMob;
    //private int numProd;
    //private int numReg;
    //private int numSafety;
    //private int numTravInfo;
    public int[][] matrix;

    public GoalNeedsMatrix(ObservableList<QuestionYN> qList, ObservableList<Need> needsList, ObservableList<QuestionYN> majorGoalsList) {
        this.qList = qList;
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            qToRowMap.put(qList.get(qIdx), qList.get(qIdx).getIdx() - 1);
        }
        this.needsList = needsList;
        for (int needIdx = 0; needIdx < needsList.size(); needIdx++) {
            needToColMap.put(needsList.get(needIdx), needIdx);
        }

        hasGoalCat = new HashMap();
        hasGoalCat.put(Question.GOAL_MOBILITY, majorGoalsList.get(0).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_SAFETY, majorGoalsList.get(1).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_PROD, majorGoalsList.get(2).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_REG, majorGoalsList.get(3).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_TRAVELER_INFO, majorGoalsList.get(4).responseIdxProperty());

        connectNeedsProperties();

        matrix = new int[qList.size()][needsList.size()];

        loadDefault();

    }

    public GoalNeedsMatrix(GoalNeedsMatrix gnMat, ObservableList<QuestionYN> majorGoalsList) {
        qList = gnMat.qList;
        qToRowMap = new LinkedHashMap();
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            qToRowMap.put(qList.get(qIdx), qList.get(qIdx).getIdx() - 1);
        }
        needsList = gnMat.needsList;
        needToColMap = new LinkedHashMap();
        for (int needIdx = 0; needIdx < needsList.size(); needIdx++) {
            needToColMap.put(needsList.get(needIdx), needIdx);
        }
        matrix = gnMat.matrix;

        hasGoalCat = new HashMap();
        hasGoalCat.put(Question.GOAL_MOBILITY, majorGoalsList.get(0).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_SAFETY, majorGoalsList.get(1).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_PROD, majorGoalsList.get(2).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_REG, majorGoalsList.get(3).responseIdxProperty());
        hasGoalCat.put(Question.GOAL_TRAVELER_INFO, majorGoalsList.get(4).responseIdxProperty());

        connectNeedsProperties();
    }

    private void connectNeedsProperties() {
        for (Need n : needsList) {
            n.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    updateTopGoals();
                }
            });
        }
        IntegerBinding ibMob = new IntegerBinding() {

            {
                for (Need n : getGoalListByType(Question.GOAL_MOBILITY)) {
                    super.bind(n.selectedProperty());
                }
            }

            @Override
            protected int computeValue() {
                for (Need n : getGoalListByType(Question.GOAL_MOBILITY)) {
                    if (n.isSelected()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        hasGoalCat.get(Question.GOAL_MOBILITY).bind(ibMob);
        IntegerBinding ibProd = new IntegerBinding() {

            {
                for (Need n : getGoalListByType(Question.GOAL_PROD)) {
                    super.bind(n.selectedProperty());
                }
            }

            @Override
            protected int computeValue() {
                for (Need n : getGoalListByType(Question.GOAL_PROD)) {
                    if (n.isSelected()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        hasGoalCat.get(Question.GOAL_PROD).bind(ibProd);
        IntegerBinding ibReg = new IntegerBinding() {

            {
                for (Need n : getGoalListByType(Question.GOAL_REG)) {
                    super.bind(n.selectedProperty());
                }
            }

            @Override
            protected int computeValue() {
                for (Need n : getGoalListByType(Question.GOAL_REG)) {
                    if (n.isSelected()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        hasGoalCat.get(Question.GOAL_REG).bind(ibReg);
        IntegerBinding ibSafety = new IntegerBinding() {

            {
                for (Need n : getGoalListByType(Question.GOAL_SAFETY)) {
                    super.bind(n.selectedProperty());
                }
            }

            @Override
            protected int computeValue() {
                for (Need n : getGoalListByType(Question.GOAL_SAFETY)) {
                    if (n.isSelected()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        hasGoalCat.get(Question.GOAL_SAFETY).bind(ibSafety);
        IntegerBinding ibTI = new IntegerBinding() {

            {
                for (Need n : getGoalListByType(Question.GOAL_TRAVELER_INFO)) {
                    super.bind(n.selectedProperty());
                }
            }

            @Override
            protected int computeValue() {
                for (Need n : getGoalListByType(Question.GOAL_TRAVELER_INFO)) {
                    if (n.isSelected()) {
                        return 1;
                    }
                }
                return 0;
            }
        };
        hasGoalCat.get(Question.GOAL_TRAVELER_INFO).bind(ibTI);

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
            e.printStackTrace();
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

        //numMob = numProd = numReg = numSafety = numTravInfo = 0;
        updateTopGoals();
    }

    public void updateTopGoals() {
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
                    //numMob++;
                    if (n.isSelected() && n.getScore() > topMobScore) {
                        topMobScore = n.getScore();
                        topMobNeed = n;
                    }
                    break;
                case Question.GOAL_PROD:
                    //numProd++;
                    if (n.isSelected() && n.getScore() > topProdScore) {
                        topProdScore = n.getScore();
                        topProdNeed = n;
                    }
                    break;
                case Question.GOAL_REG:
                    //numReg++;
                    if (n.isSelected() && n.getScore() > topRegScore) {
                        topRegScore = n.getScore();
                        topRegNeed = n;
                    }
                    break;
                case Question.GOAL_SAFETY:
                    //numSafety++;
                    if (n.isSelected() && n.getScore() > topSafetyScore) {
                        topSafetyScore = n.getScore();
                        topSafetyNeed = n;
                    }
                    break;
                case Question.GOAL_TRAVELER_INFO:
                    //numTravInfo++;
                    if (n.isSelected() && n.getScore() > topTIScore) {
                        topTIScore = n.getScore();
                        topTINeed = n;
                    }
                    break;

            }
        }
        this.topMobilityGoal.set(topMobNeed != null ? topMobNeed.getDescription() : "No mobility goals selected by user (See Goal Wizard in Step 1).");
        this.topProdGoal.set(topProdNeed != null ? topProdNeed.getDescription() : "No productivity goals selected by user(See Goal Wizard in Step 1).");
        this.topRegGoal.set(topRegNeed != null ? topRegNeed.getDescription() : "No regulatory goals selected by user(See Goal Wizard in Step 1).");
        this.topSafetyGoal.set(topSafetyNeed != null ? topSafetyNeed.getDescription() : "No safety goals selected by user(See Goal Wizard in Step 1).");
        this.topTIGoal.set(topTINeed != null ? topTINeed.getDescription() : "No traveler goals selected by user(See Goal Wizard in Step 1).");
    }

    public Node createSummaryTable() {
        computeScores();

        TableView<Need> summary = new TableView();
        summary.getStyleClass().add("step-summary-table");
        summary.setEditable(true);
        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn catCol = new TableColumn("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        catCol.setPrefWidth(175);
        catCol.setMaxWidth(175);
        catCol.setMinWidth(175);
        catCol.getStyleClass().add("col-style-center");
        //catCol.setSortType(SortType.ASCENDING);

        TableColumn recCol = new TableColumn("Recommended Goals");
        recCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");
        //scoreCol.setSortType(SortType.DESCENDING);

        TableColumn selectedCol = new TableColumn("Selected");
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        selectedCol.setPrefWidth(85);
        selectedCol.setMaxWidth(85);
        selectedCol.setMinWidth(85);

        summary.getColumns().addAll(catCol, recCol, scoreCol, selectedCol);

        summary.setItems(Need.getSortedNeedsList(needsList));

        //summary.getSortOrder().setAll(catCol, scoreCol);
        summary.setPlaceholder(new Label("The \"User Needs\" step must be completed to view."));

        BorderPane bPane = new BorderPane();
        bPane.setTop(NodeFactory.createFormattedLabel("Use the checkboxes in the far right column to select goals for the project.", "opt-pane-title"));
        bPane.setCenter(summary);
        return bPane;
    }

    public Node createSelectedGoalsNode() {
        computeScores();
        final TableView<Need> summary = new TableView();
        summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn indexCol = new TableColumn("#");
        indexCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<QuestionYN, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<QuestionYN, String> p) {
                return new ReadOnlyObjectWrapper(Integer.toString(summary.getItems().indexOf(p.getValue()) + 1));
            }
        });
        indexCol.setPrefWidth(35);
        indexCol.setMaxWidth(35);
        indexCol.setMinWidth(35);
        indexCol.getStyleClass().add("col-style-center-bold");

        TableColumn catCol = new TableColumn("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
        catCol.setPrefWidth(175);
        catCol.setMaxWidth(175);
        catCol.setMinWidth(175);
        catCol.getStyleClass().add("col-style-center");
        //catCol.setSortType(SortType.ASCENDING);

        TableColumn recCol = new TableColumn("Selected Goals");
        recCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");
        //scoreCol.setSortType(SortType.DESCENDING);

        summary.getColumns().addAll(catCol, recCol, scoreCol);

        FilteredList<Need> filteredNeeds = needsList.filtered(new Predicate<Need>() {
            @Override
            public boolean test(Need nn) {
                return nn.isSelected();
            }
        });
        //summary.setItems(needsList);
        summary.setItems(Need.getSortedNeedsList(filteredNeeds));

        //summary.getSortOrder().setAll(catCol, scoreCol);
        summary.setPlaceholder(new Label("At least one goal must be selected in the previous step."));

        return summary;
    }

    public ObservableList<Need> getGoalListByType(final String goalType) {
        //computeScores();
//        final ArrayList<Need> al = new ArrayList();
//        for (Need n : needsList) {
//            if (n.getGoal().equalsIgnoreCase(goalType) && !n.isPlaceholder && n.isSelected()) {
//                al.add(n);
//            }
//        }
        FilteredList al = needsList.filtered(new Predicate<Need>() {
            @Override
            public boolean test(Need nn) {
                return nn.getGoal().equalsIgnoreCase(goalType);
            }
        });

        if (al.isEmpty()) {
            return FXCollections.observableArrayList(new Need(goalType, "No selected " + goalType + " goals."));
        }
        return al;
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(qList.size());
        for (int i = 0; i < qList.size(); i++) {
            s.writeObject(qList.get(i));
        }
        s.writeInt(needsList.size());
        for (int i = 0; i < needsList.size(); i++) {
            s.writeObject(needsList.get(i));
        }

        s.writeObject(matrix);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        this.qList = FXCollections.observableArrayList();
        int numQ = s.readInt();
        qList = FXCollections.observableArrayList();
        for (int i = 0; i < numQ; i++) {
            qList.add((QuestionYN) s.readObject());
        }

        this.needsList = FXCollections.observableArrayList();
        int numNeeds = s.readInt();
        needsList = FXCollections.observableArrayList();
        for (int i = 0; i < numNeeds; i++) {
            needsList.add((Need) s.readObject());
        }

        matrix = (int[][]) s.readObject();
    }

}
