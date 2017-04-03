/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author jlake
 */
public class ApplicationMatrix {

    private LinkedHashMap<Application, Integer> appToColMap;
    private LinkedHashMap<Question, Integer> questionToRowMap;
    private final int[][] matrix;

    private SortedList<Application> sAppList;

    public ApplicationMatrix(ObservableList<QuestionYN> inputQuestions) {
        ArrayList<Application> appList = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/appWizardDefaultMatrix.csv")));
            String[] header = br.readLine().split(",");
            for (String appType : header) {
                appList.add(new Application(appType.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionToRowMap = new LinkedHashMap();
        appToColMap = new LinkedHashMap();
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
        }
        for (int appIdx = 0; appIdx < appList.size(); appIdx++) {
            appToColMap.put(appList.get(appIdx), appIdx);
        }
        matrix = new int[inputQuestions.size()][appList.size()];
        loadDefaultMatrix();
    }

    public ApplicationMatrix(ObservableList<QuestionYN> inputQuestions, ArrayList<Application> appTypes) {
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
        }
        for (int appIdx = 0; appIdx < inputQuestions.size(); appIdx++) {
            appToColMap.put(appTypes.get(appIdx), appIdx);
        }
        matrix = new int[inputQuestions.size()][appTypes.size()];
        loadDefaultMatrix();
    }

    private void loadDefaultMatrix() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/appWizardDefaultMatrix.csv")));
            String[] hearder = br.readLine().split(",");
            String line = br.readLine().trim();
            int counter = 0;
            while (line != null && !line.trim().equalsIgnoreCase("")) {
                String[] scoreTokens = line.split(",");
                for (int tIdx = 0; tIdx < scoreTokens.length; tIdx++) {
                    matrix[counter][tIdx] = Integer.parseInt(scoreTokens[tIdx]);
                }
                counter++;
                line = br.readLine();
            }
        } catch (IOException e) {

        }
    }

    public void computeScores() {
        int scoreSum, currCol;
        for (Application app : appToColMap.keySet()) {
            scoreSum = 0;
            currCol = appToColMap.get(app);
            for (Question q : questionToRowMap.keySet()) {
                scoreSum += q.getResponseIdx() > 0 ? matrix[questionToRowMap.get(q)][currCol] : 0;
            }
            app.setScore(scoreSum);
        }

        ObservableList<Application> appList = FXCollections.observableArrayList(this.appToColMap.keySet());
        // Sorting the list into descending order
        sAppList = appList.sorted(new ApplicationComp());
        app1.set(sAppList.get(0).getName());
        app2.set(sAppList.get(1).getName());
        app3.set(sAppList.get(2).getName());
        app4.set(sAppList.get(3).getName());
    }

    public Node getSummaryNode() {
        computeScores();

        final TableView<Application> summary = new TableView();
        summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn indexCol = new TableColumn<>("#");
        indexCol.setEditable(false);
        indexCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stakeholder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stakeholder, String> p) {
                return new ReadOnlyObjectWrapper(Integer.toString(summary.getItems().indexOf(p.getValue()) + 1));
            }
        });
        indexCol.getStyleClass().add("col-style-center-bold");
        indexCol.setPrefWidth(85);
        indexCol.setMinWidth(85);
        indexCol.setMaxWidth(85);

        TableColumn catCol = new TableColumn("Recommended Application");
        catCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setMaxWidth(100);
        scoreCol.setMinWidth(100);
        scoreCol.getStyleClass().add("col-style-center");

        summary.getColumns().addAll(indexCol, catCol, scoreCol);

        summary.getItems().addAll(sAppList);

        summary.setPlaceholder(new Label("Initial Applications step must be completed to view."));

        return summary;
    }

    private class ApplicationComp implements Comparator<Application> {

        @Override
        public int compare(Application a1, Application a2) {
            if (a1.getScore() == a2.getScore()) {
                return 0;
            }
            if (a1.getScore() < a2.getScore()) {
                return 1; // +1 instead of -1 to allow for reverse (descending) sort
            } else {
                return -1; // -1 instead of +1 to allow for reverse (descending) sort
            }
        }
    }
    private final StringProperty app1 = new SimpleStringProperty();

    public String getApp1() {
        return app1.get();
    }

    public void setApp1(String value) {
        app1.set(value);
    }

    public StringProperty app1Property() {
        return app1;
    }
    private final StringProperty app2 = new SimpleStringProperty();

    public String getApp2() {
        return app2.get();
    }

    public void setApp2(String value) {
        app2.set(value);
    }

    public StringProperty app2Property() {
        return app2;
    }
    private final StringProperty app3 = new SimpleStringProperty();

    public String getApp3() {
        return app3.get();
    }

    public void setApp3(String value) {
        app3.set(value);
    }

    public StringProperty app3Property() {
        return app3;
    }
    private final StringProperty app4 = new SimpleStringProperty();

    public String getApp4() {
        return app4.get();
    }

    public void setApp4(String value) {
        app4.set(value);
    }

    public StringProperty app4Property() {
        return app4;
    }

}
