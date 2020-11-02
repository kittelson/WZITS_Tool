/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static core.GoalNeedsMatrix.ZERO_SCORE_TXT;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

import GUI.MainController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jlake
 */
public class ApplicationMatrix implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private final Project proj;

    private LinkedHashMap<Application, Integer> appToColMap;
    private LinkedHashMap<Question, Integer> questionToRowMap;

    private ArrayList<Application> appTypes;
    private int[][] matrix;

    private SortedList<Application> sAppList;

    public ApplicationMatrix(Project proj, ObservableList<QuestionYN> inputQuestions) {
        this.proj = proj;
        appTypes = new ArrayList();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(MainController.getScoringMatrixFolder() + "appWizardDefaultMatrix.csv"));
//            String[] header = br.readLine().split(",");
//            for (String appType : header) {
//                appTypes.add(new Application(appType.trim()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        JSONObject json = ApplicationMatrix.loadJSON();
        JSONArray appMatrix = (JSONArray) json.get("Application Matrix");
        JSONArray firstEntry = (JSONArray) ((JSONObject) appMatrix.get(0)).get("Scores");
        for (int i = 0; i < firstEntry.size(); i++) {
            String appType = ((JSONObject) firstEntry.get(i)).get("Category").toString();
            appTypes.add(new Application(appType.trim()));
        }
        questionToRowMap = new LinkedHashMap();
        appToColMap = new LinkedHashMap();
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
            //System.out.println("Question: " + inputQuestions.get(qIdx).getQuestionText() + ", Row: " + String.valueOf(qIdx));
        }
        for (int appIdx = 0; appIdx < appTypes.size(); appIdx++) {
            appToColMap.put(appTypes.get(appIdx), appIdx);
            //System.out.println("Application: " + appTypes.get(appIdx).getName() + ", Row: " + String.valueOf(appIdx));
        }
        matrix = new int[inputQuestions.size()][appTypes.size()];
        connectAppProperties();
//        loadDefaultMatrix();
        loadDefaultMatrixV2();
    }


    public ApplicationMatrix(Project proj, ObservableList<QuestionYN> inputQuestions, ArrayList<Application> appTypes) {
        this.proj = proj;
        for (int qIdx = 0; qIdx < inputQuestions.size(); qIdx++) {
            questionToRowMap.put(inputQuestions.get(qIdx), qIdx);
        }
        this.appTypes = appTypes;
        for (int appIdx = 0; appIdx < appTypes.size(); appIdx++) {
            appToColMap.put(appTypes.get(appIdx), appIdx);
        }
        matrix = new int[inputQuestions.size()][appTypes.size()];
        connectAppProperties();
//        loadDefaultMatrix();
        loadDefaultMatrixV2();
    }

    public ApplicationMatrix(ApplicationMatrix am, Project proj) {
        this.proj = proj;
        this.appTypes = am.appTypes;
        questionToRowMap = new LinkedHashMap();
        for (int qIdx = 0; qIdx < proj.getQGen().qApplicationList.size(); qIdx++) {
            questionToRowMap.put(proj.getQGen().qApplicationList.get(qIdx), qIdx);
        }
        appToColMap = new LinkedHashMap();
        for (int appIdx = 0; appIdx < am.appTypes.size(); appIdx++) {
            appToColMap.put(am.appTypes.get(appIdx), appIdx);
        }
        matrix = am.matrix;
        connectAppProperties();
        computeScores();
    }

    private void connectAppProperties() {
        for (Application app : appTypes) {
            app.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    updateSelectedApps();
                }
            });
        }
    }

    @Deprecated
    private void loadDefaultMatrix() {
        BufferedReader br = null;
        try {
//            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/appWizardDefaultMatrix.csv")));
//            br = new BufferedReader(new FileReader(MainController.getScoringMatrixFolder() + "appWizardDefaultMatrix.csv"));
            br = new BufferedReader(new FileReader(MainController.getScoringMatrixFolder().resolve("appWizardDefaultMatrix.csv").toFile()));
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

    private void loadDefaultMatrixV2() {
        JSONObject json = ApplicationMatrix.loadJSON();
        JSONArray jArr = (JSONArray) json.get("Application Matrix");
        for (int qIdx = 0; qIdx < jArr.size(); qIdx++) {
            JSONObject q = (JSONObject) jArr.get(qIdx);
            JSONArray scoresArr = (JSONArray) q.get("Scores");
            for (int scoreIdx = 0; scoreIdx < scoresArr.size(); scoreIdx++) {
                JSONObject score = (JSONObject) scoresArr.get(scoreIdx);
                try {
                    matrix[qIdx][scoreIdx] = Integer.parseInt(score.getOrDefault("Score", "0").toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    matrix[qIdx][scoreIdx] = 0;
                }
            }
        }
    }

    public static JSONObject loadJSON() {
        JSONParser parser = new JSONParser();
        JSONObject returnJSON = null;
        try {
//            File customMatrix = new File(MainController.getScoringMatrixFolder() + "appWizardCustomMatrix.json");
//            File defaultMatrix = new File(MainController.getScoringMatrixFolder() + "appWizardDefaultMatrix.json");
            Path scoringMatrixFolder = MainController.getScoringMatrixFolder();
            File customMatrix = scoringMatrixFolder.resolve("appWizardCustomMatrix.json").toFile();
            File defaultMatrix = scoringMatrixFolder.resolve("appWizardDefaultMatrix.json").toFile();
            if (customMatrix.exists()) {
                returnJSON = (JSONObject) parser.parse(new FileReader(customMatrix));
            } else {
                returnJSON = (JSONObject) parser.parse(new FileReader(defaultMatrix));
            }
//            System.out.println(returnJSON.toJSONString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return returnJSON;
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

        if (!proj.isAutomatedEnforcementAllowed()) {
            for (Application app : appList) {
                if (app.getName().equalsIgnoreCase(Application.AUTO_ENFORCE)) {
                    appList.remove(app);
                    break;
                }
            }
        }
        if (proj.getFunctionalClass() != null) {
            if (!(proj.getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[0])
                    || proj.getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[1]))) {
                for (Application app : appList) {
                    if (app.getName().equalsIgnoreCase(Application.QUEUE_WARNING)) {
                        appList.remove(app);
                        break;
                    }
                }
                for (Application app : appList) {
                    if (app.getName().equalsIgnoreCase(Application.VSL)) {
                        appList.remove(app);
                        break;
                    }
                }
                for (Application app : appList) {
                    if (app.getName().equalsIgnoreCase(Application.TEMP_RAMP_METER)) {
                        appList.remove(app);
                        break;
                    }
                }
                for (Application app : appList) {
                    if (app.getName().equalsIgnoreCase(Application.DYNAMIC_LANE_MERGE)) {
                        appList.remove(app);
                        break;
                    }
                }
            }
        }

        // Sorting the list into descending order
        sAppList = appList.sorted(new ApplicationComp());
        updateSelectedApps();
    }

    private void updateSelectedApps() {
        FilteredList<Application> ssAppList = sAppList.filtered(new Predicate<Application>() {
            @Override
            public boolean test(Application app) {
                return app.isSelected();
            }
        });
        app1.set(ssAppList.size() > 0 ? ssAppList.get(0).getName() : "No applications selected by user (See Application Wizard in Step 2).");
        app2.set(ssAppList.size() > 1 ? ssAppList.get(1).getName() : "");
        app3.set(ssAppList.size() > 2 ? ssAppList.get(2).getName() : "");
        app4.set(ssAppList.size() > 3 ? ssAppList.get(3).getName() : "");
    }

    public Node getSummaryNode() {
        computeScores();

        final TableView<Application> summary = new TableView();
        summary.getStyleClass().add("step-selection-table");
        summary.setEditable(true);
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

        TableColumn scoreCol = new TableColumn(SCORE_COL_NAME);
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(SCORE_COL_WIDTH);
        scoreCol.setMaxWidth(SCORE_COL_WIDTH);
        scoreCol.setMinWidth(SCORE_COL_WIDTH);
        scoreCol.getStyleClass().add("col-style-center");
        scoreCol.setCellFactory(new Callback<TableColumn<Need, String>, TableCell<Need, String>>() {
            @Override
            public TableCell<Need, String> call(TableColumn<Need, String> tc) {
                final TextFieldTableCell<Need, String> tfe = new TextFieldTableCell();
                tfe.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                        if (newVal != null) {
                            try {
                                int score = Integer.parseInt(newVal);
                                tfe.setText(Application.getScoreString(score));
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                });
                return tfe;
            }
        });

        TableColumn selectedCol = new TableColumn("Selected");
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectedCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectedCol));
        selectedCol.setPrefWidth(85);
        selectedCol.setMaxWidth(85);
        selectedCol.setMinWidth(85);

        summary.getColumns().addAll(indexCol, catCol, scoreCol, selectedCol);

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

    public ObservableList<Application> getAppList() {
        return FXCollections.observableArrayList(appTypes);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(appTypes);
        s.writeObject(matrix);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        appTypes = (ArrayList<Application>) s.readObject();
        matrix = (int[][]) s.readObject();
    }

    public static final String SCORE_COL_NAME = "Priority";
    public static final int SCORE_COL_WIDTH = 200;
    public static final String ZERO_SCORE_TXT = "Not Recommended";
    public static final String LOW_CAT_LABEL = "Low";
    public static final String MED_CAT_LABEL = "Medium";
    public static final String HIGH_CAT_LABEL = "High";
    public static final int LOW_CAT_MIN = 1;
    public static final int LOW_CAT_MAX = 4;
    public static final int MED_CAT_MIN = 5;
    public static final int MED_CAT_MAX = 8;
    public static final int HIGH_CAT_MIN = 9;

}
