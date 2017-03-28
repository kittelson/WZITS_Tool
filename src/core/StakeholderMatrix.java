/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ltrask
 */
public class StakeholderMatrix {

    private final Project proj;
    private final ObservableList<QuestionOption> qOptList;
    private final ObservableList<QuestionYN> qYNList;

    private final int[][] optScoreMat;
    private final int[][] ynScoreMat;
    //private final LinkedHashMap<String, Integer> stakeholderScore;
    private final ObservableList<Stakeholder> stakeholders;
    private SortedList<Stakeholder> sortedStakeholders;

    public StakeholderMatrix(Project proj, ObservableList<QuestionOption> qOptList, ObservableList<QuestionYN> qYNList) {
        this.proj = proj;
        this.qOptList = qOptList;
        this.qYNList = qYNList;
        stakeholders = FXCollections.observableArrayList();
        int numStakeholders = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/stake1.csv")));
            String[] headerTokens = br.readLine().split(",");
            numStakeholders = headerTokens.length - 1;
            for (int sIdx = 1; sIdx < headerTokens.length; sIdx++) {
                stakeholders.add(new Stakeholder(sIdx, headerTokens[sIdx], 0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        optScoreMat = new int[7][numStakeholders];
        ynScoreMat = new int[16][numStakeholders];

        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/stake1.csv")));
            br.readLine(); // Skipping header
            String line = br.readLine();
            int countIdx = 0;
            while (line != null) {
                String[] tokens = line.split(",");
                for (int entryIdx = 0; entryIdx < tokens.length - 1; entryIdx++) {
                    optScoreMat[countIdx][entryIdx] = !tokens[entryIdx + 1].trim().equalsIgnoreCase("") ? Integer.parseInt(tokens[entryIdx + 1]) : 0;
                }
                countIdx++;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/core/defaults/stake2.csv")));
            br.readLine(); // Skipping header
            String line = br.readLine();
            int countIdx = 0;
            while (line != null) {
                String[] tokens = line.split(",");
                for (int entryIdx = 0; entryIdx < tokens.length - 1; entryIdx++) {
                    ynScoreMat[countIdx][entryIdx] = !tokens[entryIdx + 1].trim().equalsIgnoreCase("") ? Integer.parseInt(tokens[entryIdx + 1]) : 0;
                }
                countIdx++;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupBindings();
    }

    private void setupBindings() {
        int qIdx = 0;
//        qYNList.get(qIdx++).answerIsYes.bind(this.hasSchools);
//        qYNList.get(qIdx++).answerIsYes.bind(this.touristRoute);
//        qYNList.get(qIdx++).answerIsYes.bind(this.specialEventVenue);
//        qYNList.get(qIdx++).answerIsYes.bind(this.transitOnRoute);
//        qYNList.get(qIdx++).answerIsYes.bind(this.otherWorkZones);
//        qYNList.get(qIdx++).answerIsYes.bind(this.businessHourLnClosures);
//        qYNList.get(qIdx++).answerIsYes.bind(this.sideStreetRestrictions);
//        qYNList.get(qIdx++).answerIsYes.bind(this.freightCorridor);
//        qYNList.get(qIdx++).answerIsYes.bind(this.pedBikeImpacts);
//        qYNList.get(qIdx++).answerIsYes.bind(this.signalizedSystem);
        this.hasSchools.bind(qYNList.get(qIdx++).answerIsYes);
        this.touristRoute.bind(qYNList.get(qIdx++).answerIsYes);
        this.specialEventVenue.bind(qYNList.get(qIdx++).answerIsYes);
        this.transitOnRoute.bind(qYNList.get(qIdx++).answerIsYes);
        this.otherWorkZones.bind(qYNList.get(qIdx++).answerIsYes);
        this.businessHourLnClosures.bind(qYNList.get(qIdx++).answerIsYes);
        this.sideStreetRestrictions.bind(qYNList.get(qIdx++).answerIsYes);
        this.freightCorridor.bind(qYNList.get(qIdx++).answerIsYes);
        this.pedBikeImpacts.bind(qYNList.get(qIdx++).answerIsYes);
        this.signalizedSystem.bind(qYNList.get(qIdx++).answerIsYes);
        qIdx = 0;
        this.mobilityGoal.bind(proj.getMajorGoalsQs().get(qIdx++).answerIsYes);
        this.productivityGoal.bind(proj.getMajorGoalsQs().get(qIdx++).answerIsYes);
        this.regulatoryGoal.bind(proj.getMajorGoalsQs().get(qIdx++).answerIsYes);
        this.safetyGoal.bind(proj.getMajorGoalsQs().get(qIdx++).answerIsYes);
        this.travelerInfoGoal.bind(proj.getMajorGoalsQs().get(qIdx++).answerIsYes);
    }

    public void computeStakeholders() {
        int funcIdx = 0;
        if (proj.functionalClassProperty().get() != null) {
            switch (proj.functionalClassProperty().get()) {
                case "Freeway":
                    funcIdx = 0;
                    break;
                case "Arterial":
                    funcIdx = 1;
                    break;
                case "Local":
                    funcIdx = 2;
                    break;
            }
        }

        int mainIdx = 3;
        if (proj.maintainingAgencyProperty().get() != null) {
            switch (proj.maintainingAgencyProperty().get()) {
                case "State":
                    mainIdx = 3;
                    break;
                case "County":
                    mainIdx = 4;
                    break;
                case "City/Town":
                    mainIdx = 5;
                    break;
                case "Other":
                    mainIdx = 6;
                    break;
            }
        }
        int[] scores = new int[stakeholders.size()];
        for (int stakeIdx = 0; stakeIdx < stakeholders.size(); stakeIdx++) {
            scores[stakeIdx] += optScoreMat[funcIdx][stakeIdx];
            scores[stakeIdx] += optScoreMat[mainIdx][stakeIdx];
            int ynIdx = 0;
            scores[stakeIdx] += this.hasSchools.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.touristRoute.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.specialEventVenue.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.transitOnRoute.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.otherWorkZones.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.emergencyResponseCorridor.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.businessHourLnClosures.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.sideStreetRestrictions.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.freightCorridor.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.pedBikeImpacts.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.signalizedSystem.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.mobilityGoal.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.safetyGoal.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.productivityGoal.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.regulatoryGoal.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            scores[stakeIdx] += this.travelerInfoGoal.get() ? ynScoreMat[ynIdx++][stakeIdx] : 0;
            stakeholders.get(stakeIdx).setScore(scores[stakeIdx]);
        }

        ObservableList<Stakeholder> osl = FXCollections.observableArrayList(stakeholders);
        StakeholderComp sc = new StakeholderComp();
        sortedStakeholders = osl.sorted(sc);
        primaryStakeholder.set(sortedStakeholders.get(0).getName());
        secondaryStakeholder.set(sortedStakeholders.get(1).getName());
        additionalStakeholder.set(sortedStakeholders.get(2).getName());
    }

    public TableView createSummaryTable() {
        computeStakeholders();

        TableView<Stakeholder> summary = new TableView();
        summary.getStyleClass().add("step-summary-table");

        summary.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn catCol = new TableColumn("#");
        catCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        catCol.setPrefWidth(25);
        catCol.setMaxWidth(25);
        catCol.setMinWidth(25);
        catCol.getStyleClass().add("col-style-center-bold");

        TableColumn recCol = new TableColumn("Stakeholder");
        recCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(75);
        scoreCol.setMaxWidth(75);
        scoreCol.setMinWidth(75);
        scoreCol.getStyleClass().add("col-style-center");

        summary.getColumns().addAll(catCol, recCol, scoreCol);

//        int sIdx = 1;
//        for (String key : this.stakeholderScore.keySet()) {
//            summary.getItems().add(new Stakeholder(sIdx, key, stakeholderScore.get(key)));
//            sIdx++;
//        }
        summary.setItems(sortedStakeholders);

        summary.setPlaceholder(new Label("Previous steps must be completed to view."));

        return summary;
    }
    private final BooleanProperty hasSchools = new SimpleBooleanProperty();

    public boolean isHasSchools() {
        return hasSchools.get();
    }

    public void setHasSchools(boolean value) {
        hasSchools.set(value);
    }

    public BooleanProperty hasSchoolsProperty() {
        return hasSchools;
    }
    private final BooleanProperty touristRoute = new SimpleBooleanProperty();

    public boolean isTouristRoute() {
        return touristRoute.get();
    }

    public void setTouristRoute(boolean value) {
        touristRoute.set(value);
    }

    public BooleanProperty touristRouteProperty() {
        return touristRoute;
    }
    private final BooleanProperty specialEventVenue = new SimpleBooleanProperty();

    public boolean isSpecialEventVenue() {
        return specialEventVenue.get();
    }

    public void setSpecialEventVenue(boolean value) {
        specialEventVenue.set(value);
    }

    public BooleanProperty specialEventVenueProperty() {
        return specialEventVenue;
    }
    private final BooleanProperty transitOnRoute = new SimpleBooleanProperty();

    public boolean isTransitOnRoute() {
        return transitOnRoute.get();
    }

    public void setTransitOnRoute(boolean value) {
        transitOnRoute.set(value);
    }

    public BooleanProperty transitOnRouteProperty() {
        return transitOnRoute;
    }
    private final BooleanProperty otherWorkZones = new SimpleBooleanProperty();

    public boolean isOtherWorkZones() {
        return otherWorkZones.get();
    }

    public void setOtherWorkZones(boolean value) {
        otherWorkZones.set(value);
    }

    public BooleanProperty otherWorkZonesProperty() {
        return otherWorkZones;
    }
    private final BooleanProperty emergencyResponseCorridor = new SimpleBooleanProperty();

    public boolean isEmergencyResponseCorridor() {
        return emergencyResponseCorridor.get();
    }

    public void setEmergencyResponseCorridor(boolean value) {
        emergencyResponseCorridor.set(value);
    }

    public BooleanProperty emergencyResponseCorridorProperty() {
        return emergencyResponseCorridor;
    }
    private final BooleanProperty businessHourLnClosures = new SimpleBooleanProperty();

    public boolean isBusinessHourLnClosures() {
        return businessHourLnClosures.get();
    }

    public void setBusinessHourLnClosures(boolean value) {
        businessHourLnClosures.set(value);
    }

    public BooleanProperty businessHourLnClosuresProperty() {
        return businessHourLnClosures;
    }
    private final BooleanProperty sideStreetRestrictions = new SimpleBooleanProperty();

    public boolean isSideStreetRestrictions() {
        return sideStreetRestrictions.get();
    }

    public void setSideStreetRestrictions(boolean value) {
        sideStreetRestrictions.set(value);
    }

    public BooleanProperty sideStreetRestrictionsProperty() {
        return sideStreetRestrictions;
    }
    private final BooleanProperty freightCorridor = new SimpleBooleanProperty();

    public boolean isFreightCorridor() {
        return freightCorridor.get();
    }

    public void setFreightCorridor(boolean value) {
        freightCorridor.set(value);
    }

    public BooleanProperty freightCorridorProperty() {
        return freightCorridor;
    }
    private final BooleanProperty pedBikeImpacts = new SimpleBooleanProperty();

    public boolean isPedBikeImpacts() {
        return pedBikeImpacts.get();
    }

    public void setPedBikeImpacts(boolean value) {
        pedBikeImpacts.set(value);
    }

    public BooleanProperty pedBikeImpactsProperty() {
        return pedBikeImpacts;
    }
    private final BooleanProperty signalizedSystem = new SimpleBooleanProperty();

    public boolean isSignalizedSystem() {
        return signalizedSystem.get();
    }

    public void setSignalizedSystem(boolean value) {
        signalizedSystem.set(value);
    }

    public BooleanProperty signalizedSystemProperty() {
        return signalizedSystem;
    }
    private final BooleanProperty mobilityGoal = new SimpleBooleanProperty();

    public boolean isMobilityGoal() {
        return mobilityGoal.get();
    }

    public void setMobilityGoal(boolean value) {
        mobilityGoal.set(value);
    }

    public BooleanProperty mobilityGoalProperty() {
        return mobilityGoal;
    }
    private final BooleanProperty productivityGoal = new SimpleBooleanProperty();

    public boolean isProductivityGoal() {
        return productivityGoal.get();
    }

    public void setProductivityGoal(boolean value) {
        productivityGoal.set(value);
    }

    public BooleanProperty productivityGoalProperty() {
        return productivityGoal;
    }
    private final BooleanProperty regulatoryGoal = new SimpleBooleanProperty();

    public boolean isRegulatoryGoal() {
        return regulatoryGoal.get();
    }

    public void setRegulatoryGoal(boolean value) {
        regulatoryGoal.set(value);
    }

    public BooleanProperty regulatoryGoalProperty() {
        return regulatoryGoal;
    }
    private final BooleanProperty safetyGoal = new SimpleBooleanProperty();

    public boolean isSafetyGoal() {
        return safetyGoal.get();
    }

    public void setSafetyGoal(boolean value) {
        safetyGoal.set(value);
    }

    public BooleanProperty safetyGoalProperty() {
        return safetyGoal;
    }
    private final BooleanProperty travelerInfoGoal = new SimpleBooleanProperty();

    public boolean isTravelerInfoGoal() {
        return travelerInfoGoal.get();
    }

    public void setTravelerInfoGoal(boolean value) {
        travelerInfoGoal.set(value);
    }

    public BooleanProperty travelerInfoGoalProperty() {
        return travelerInfoGoal;
    }

    private class StakeholderComp implements Comparator<Stakeholder> {

        @Override
        public int compare(Stakeholder s1, Stakeholder s2) {
            if (s1.getScore() == s2.getScore()) {
                return 0;
            }
            if (s1.getScore() < s2.getScore()) {
                return 1; // +1 instead of -1 to allow for reverse (descending) sort
            } else {
                return -1; // -1 instead of +1 to allow for reverse (descending) sort
            }
        }
    }
    private final StringProperty primaryStakeholder = new SimpleStringProperty();

    public String getPrimaryStakeholder() {
        return primaryStakeholder.get();
    }

    public void setPrimaryStakeholder(String value) {
        primaryStakeholder.set(value);
    }

    public StringProperty primaryStakeholderProperty() {
        return primaryStakeholder;
    }
    private final StringProperty secondaryStakeholder = new SimpleStringProperty();

    public String getSecondaryStakeholder() {
        return secondaryStakeholder.get();
    }

    public void setSecondaryStakeholder(String value) {
        secondaryStakeholder.set(value);
    }

    public StringProperty secondaryStakeholderProperty() {
        return secondaryStakeholder;
    }
    private final StringProperty additionalStakeholder = new SimpleStringProperty();

    public String getAdditionalStakeholder() {
        return additionalStakeholder.get();
    }

    public void setAdditionalStakeholder(String value) {
        additionalStakeholder.set(value);
    }

    public StringProperty additionalStakeholderProperty() {
        return additionalStakeholder;
    }

}
