/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.MainController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/**
 *
 * @author ltrask
 */
public class StakeholderMatrix implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private final Project proj;
    private final ObservableList<QuestionOption> qOptList;
    private final ObservableList<QuestionYN> qYNList;

    private int[][] optScoreMat;
    private int[][] ynScoreMat;
    //private final LinkedHashMap<String, Integer> stakeholderScore;
    public ObservableList<Stakeholder> stakeholders;
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
        optScoreMat = new int[10][numStakeholders];
        ynScoreMat = new int[18][numStakeholders];

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

    public StakeholderMatrix(StakeholderMatrix sm, Project proj) {
        this.proj = proj;
        this.qYNList = proj.getStakeWizardYNQs();
        this.qOptList = proj.getStakeWizardOptQs();

        this.stakeholders = sm.stakeholders;

        this.optScoreMat = sm.optScoreMat;
        this.ynScoreMat = sm.ynScoreMat;

        setupBindings();
        computeStakeholders();
    }

    private void setupBindings() {
        int qIdx = 0;
        this.hasSchools.bind(qYNList.get(qIdx++).answerIsYes);
        this.touristRoute.bind(qYNList.get(qIdx++).answerIsYes);
        this.specialEventVenue.bind(qYNList.get(qIdx++).answerIsYes);
        this.transitOnRoute.bind(qYNList.get(qIdx++).answerIsYes);
        this.otherWorkZones.bind(qYNList.get(qIdx++).answerIsYes);
        this.emergencyResponseCorridor.bind(qYNList.get(qIdx++).answerIsYes);
        this.businessHourLnClosures.bind(qYNList.get(qIdx++).answerIsYes);
        this.sideStreetRestrictions.bind(qYNList.get(qIdx++).answerIsYes);
        this.freightCorridor.bind(qYNList.get(qIdx++).answerIsYes);
        this.pedBikeImpacts.bind(qYNList.get(qIdx++).answerIsYes);
        this.signalizedSystem.bind(qYNList.get(qIdx++).answerIsYes);
        this.unwantedLocalDiversion.bind(qYNList.get(qIdx++).answerIsYes);
        qIdx = 0;
        this.mobilityGoal.bind(proj.getMajorGoalsQs().get(0).answerIsYes);
        this.productivityGoal.bind(proj.getMajorGoalsQs().get(2).answerIsYes);
        this.regulatoryGoal.bind(proj.getMajorGoalsQs().get(3).answerIsYes);
        this.safetyGoal.bind(proj.getMajorGoalsQs().get(1).answerIsYes);
        this.travelerInfoGoal.bind(proj.getMajorGoalsQs().get(4).answerIsYes);

        for (Stakeholder sh : stakeholders) {
            sh.coreTeamMemberProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldVal, Boolean newVal) {
                    if (newVal) {
                        updateSelectedStakeholders();
                    }
                }
            });
            sh.stakeholderProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldVal, Boolean newVal) {
                    if (newVal) {
                        updateSelectedStakeholders();
                    }
                }
            });
            sh.notApplicableProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldVal, Boolean newVal) {
                    if (newVal) {
                        updateSelectedStakeholders();
                    }
                }
            });
        }
    }

    public void computeStakeholders() {
        int funcIdx = -1;
        if (proj.functionalClassProperty().get() != null) {
            for (int i = 0; i < Project.FUNCTIONAL_CLASS_LIST.length; i++) {
                if (proj.getFunctionalClass().equalsIgnoreCase(Project.FUNCTIONAL_CLASS_LIST[i])) {
                    funcIdx = i < 2 ? 0 : i < 4 ? 1 : 2;
                    break;
                }
            }
        }

        int mainIdx = -1;
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

        int patrolIdx = -1;
        if (proj.patrollingAgencyProperty().get() != null) {
            switch (proj.patrollingAgencyProperty().get()) {
                case "Local Police/Sheriff":
                    patrolIdx = 7;
                    break;
                case "State Police":
                    patrolIdx = 8;
                    break;
                case "Service Patrol or Contractor":
                    patrolIdx = 9;
                    break;
            }
        }
        int[] scores = new int[stakeholders.size()];
        for (int stakeIdx = 0; stakeIdx < stakeholders.size(); stakeIdx++) {
            if (stakeIdx < ynScoreMat[0].length) {
                scores[stakeIdx] += funcIdx >= 0 ? optScoreMat[funcIdx][stakeIdx] : 0;
                scores[stakeIdx] += mainIdx >= 3 ? optScoreMat[mainIdx][stakeIdx] : 0;
                scores[stakeIdx] += patrolIdx >= 7 ? optScoreMat[patrolIdx][stakeIdx] : 0;
                int ynIdx = 0;
                scores[stakeIdx] += proj.getNumLanesClosed() > 0 ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.hasSchools.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.touristRoute.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.specialEventVenue.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.transitOnRoute.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.otherWorkZones.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.emergencyResponseCorridor.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.businessHourLnClosures.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.sideStreetRestrictions.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.freightCorridor.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.pedBikeImpacts.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.signalizedSystem.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.mobilityGoal.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.safetyGoal.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.productivityGoal.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.regulatoryGoal.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.travelerInfoGoal.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                scores[stakeIdx] += this.unwantedLocalDiversion.get() ? ynScoreMat[ynIdx][stakeIdx] : 0;
                ynIdx++;
                stakeholders.get(stakeIdx).setScore(scores[stakeIdx]);
            }
            if (false) {
                if (scores[stakeIdx] > 2) {
                    stakeholders.get(stakeIdx).setCoreTeamMember(true);
                } else if (scores[stakeIdx] > 0) {
                    stakeholders.get(stakeIdx).setStakeholder(true);
                } else {
                    stakeholders.get(stakeIdx).setNotApplicable(true);
                }
            }
        }

        ObservableList<Stakeholder> osl = FXCollections.observableArrayList(stakeholders);
        StakeholderComp sc = new StakeholderComp();
        sortedStakeholders = osl.sorted(sc);
        updateSelectedStakeholders();
    }

    private void updateSelectedStakeholders() {
        if (sortedStakeholders != null) {
            FilteredList<Stakeholder> fsl = sortedStakeholders.filtered(new Predicate<Stakeholder>() {
                @Override
                public boolean test(Stakeholder sh) {
                    return sh.isCoreTeamMember();
                }
            });
            primaryStakeholder.set(fsl.size() > 0 ? fsl.get(0).getName() : "No core team members selected.");
            secondaryStakeholder.set(fsl.size() > 1 ? fsl.get(1).getName() : "");
            fsl = sortedStakeholders.filtered(new Predicate<Stakeholder>() {
                @Override
                public boolean test(Stakeholder sh) {
                    return sh.isStakeholder();
                }
            });
            additionalStakeholder.set(fsl.size() > 0 ? fsl.get(0).getName() : "No non-team member stakeholders selected.");
        }
    }

    public TableView createSummaryTable() {
        computeStakeholders();

        final TableView<Stakeholder> summary = new TableView();
        summary.getStyleClass().add("step-selection-table");
        summary.setEditable(true);

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

        TableColumn recCol = new TableColumn("Name");
        recCol.setCellValueFactory(new PropertyValueFactory<>("name"));

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
                                if (score == 0) {
                                    tfe.setText(ZERO_SCORE_TXT);
                                } else if (score >= LOW_CAT_MIN && score <= LOW_CAT_MAX) {
                                    tfe.setText(LOW_CAT_LABEL);
                                } else if (score >= MED_CAT_MIN && score <= MED_CAT_MAX) {
                                    tfe.setText(MED_CAT_LABEL);
                                } else if (score < 0) {
                                    tfe.setText(UNSCORED_LABEL);
                                } else {
                                    tfe.setText(HIGH_CAT_LABEL);
                                }
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                });
                return tfe;
            }
        });

        TableColumn coreTeamCol = new TableColumn("Core Team");
        coreTeamCol.setCellValueFactory(new PropertyValueFactory<>("coreTeamMember"));
        coreTeamCol.setCellFactory(CheckBoxTableCell.forTableColumn(coreTeamCol));

        TableColumn stakeholderCol = new TableColumn("Stakeholder");
        stakeholderCol.setCellValueFactory(new PropertyValueFactory<>("stakeholder"));
        stakeholderCol.setCellFactory(CheckBoxTableCell.forTableColumn(stakeholderCol));

        TableColumn notApplicableCol = new TableColumn("N/A");
        notApplicableCol.setCellValueFactory(new PropertyValueFactory<>("notApplicable"));
        notApplicableCol.setCellFactory(CheckBoxTableCell.forTableColumn(notApplicableCol));

        coreTeamCol.setPrefWidth(100);
        coreTeamCol.setMaxWidth(100);
        coreTeamCol.setMinWidth(100);
        stakeholderCol.setPrefWidth(100);
        stakeholderCol.setMaxWidth(100);
        stakeholderCol.setMinWidth(100);
        notApplicableCol.setPrefWidth(75);
        notApplicableCol.setMaxWidth(75);
        notApplicableCol.setMinWidth(75);

        TableColumn teamTypeCol = new TableColumn("Team Members");
        teamTypeCol.getColumns().addAll(coreTeamCol, stakeholderCol, notApplicableCol);

        summary.getColumns().addAll(indexCol, recCol, scoreCol, teamTypeCol);

//        int sIdx = 1;
//        for (String key : this.stakeholderScore.keySet()) {
//            summary.getItems().add(new Stakeholder(sIdx, key, stakeholderScore.get(key)));
//            sIdx++;
//        }
        summary.setItems(sortedStakeholders);

        summary.setPlaceholder(new Label("Previous steps must be completed to view."));

        return summary;
    }

    public Node createSelectedMembersPanel() {
        GridPane gPane = new GridPane();

        ObservableList<Stakeholder> teamCore = FXCollections.observableArrayList();
        ObservableList<Stakeholder> teamStakeholder = FXCollections.observableArrayList();
        ObservableList<Stakeholder> teamNA = FXCollections.observableArrayList();
        for (Stakeholder sh : stakeholders) {
            if (sh.isCoreTeamMember()) {
                teamCore.add(sh);
            } else if (sh.isStakeholder()) {
                teamStakeholder.add(sh);
            } else {
                teamNA.add(sh);
            }
        }

        Node coreNode = createMemberTable(teamCore, Stakeholder.CORE_TEAM);
        Node shNode = createMemberTable(teamStakeholder, Stakeholder.STAKEHOLDER);
        Node naNode = createMemberTable(teamNA, Stakeholder.NA);

        gPane.add(coreNode, 0, 0);
        gPane.add(shNode, 0, 1);
        gPane.add(naNode, 0, 2);

        GridPane.setHgrow(coreNode, Priority.ALWAYS);
        GridPane.setHgrow(shNode, Priority.ALWAYS);
        GridPane.setHgrow(naNode, Priority.ALWAYS);

        RowConstraints rc1 = new RowConstraints();
        rc1.setPercentHeight(50);
        RowConstraints rc2 = new RowConstraints();
        rc2.setPercentHeight(30);
        RowConstraints rc3 = new RowConstraints();
        rc3.setPercentHeight(20);
        gPane.getRowConstraints().addAll(rc1, rc2, rc3);

        return gPane;
    }

    private Node createMemberTable(ObservableList<Stakeholder> sList, int listType) {
        final TableView<Stakeholder> table = new TableView();
        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn indexCol = new TableColumn<>("#");
        indexCol.setEditable(false);
        indexCol.setCellValueFactory(new Callback<CellDataFeatures<Stakeholder, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Stakeholder, String> p) {
                return new ReadOnlyObjectWrapper(Integer.toString(table.getItems().indexOf(p.getValue()) + 1));
            }
        });
        indexCol.getStyleClass().add("col-style-center-bold");
        indexCol.setPrefWidth(85);
        indexCol.setMinWidth(85);
        indexCol.setMaxWidth(85);

        TableColumn nameCol = new TableColumn<>();
        nameCol.setEditable(false);
        Hyperlink addAdditional = new Hyperlink("(Add Additional)");
        addAdditional.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                Stakeholder newSH = Stakeholder.createNew(sList.size() + 1, listType);
                if (newSH != null) {
                    sList.add(newSH);
                    stakeholders.add(newSH);
                }
            }
        });
        addAdditional.getStyleClass().add("hyperlink-add-core");
        nameCol.setGraphic(addAdditional);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(new Callback<TableColumn<QuestionYN, String>, TableCell<QuestionYN, String>>() {
            @Override
            public TableCell<QuestionYN, String> call(TableColumn<QuestionYN, String> tc) {
                final TextFieldTableCell<QuestionYN, String> tfe = new TextFieldTableCell<QuestionYN, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item);
                            Hyperlink hl = new Hyperlink("(edit)");
                            hl.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent ae) {
                                    Stakeholder.editInfo(sList.get(getIndex()));
                                }
                            });
                            hl.getStyleClass().add("wz-input-hyperlink");
                            if (sList.get(getIndex()).getScore() >= 0) {
                                setGraphic(hl);
                            } else {
                                Hyperlink hl2 = new Hyperlink("(remove)");
                                hl2.getStyleClass().add("wz-input-hyperlink");
                                hl2.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent ae) {
                                        Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the custom stakeholder?", ButtonType.OK, ButtonType.CANCEL);
                                        al.initOwner(MainController.getWindow());
                                        al.setTitle("Remove custom stakeholder?");
                                        al.setHeaderText(item);
                                        Optional<ButtonType> result = al.showAndWait();
                                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                            Stakeholder deleted = sList.remove(getIndex());
                                            stakeholders.remove(deleted);
                                        }
                                    }
                                });
                                GridPane gp = new GridPane();
                                gp.add(hl, 0, 0);
                                gp.add(hl2, 1, 0);
                                setGraphic(gp);
                            }

                            setContentDisplay(ContentDisplay.RIGHT);
                        }
                    }
                };
                tfe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, TableRow newVal) {
                        if (newVal != null) {
                            newVal.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> ob, Boolean oldVal, Boolean newVal) {
                                    if (tfe.getGraphic() != null) {
                                        if (newVal) {
                                            ((Hyperlink) tfe.getGraphic()).setStyle("-fx-text-fill: white");
                                        } else {
                                            ((Hyperlink) tfe.getGraphic()).setStyle("-fx-text-fill: #ED7D31");
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                return tfe;
            }
        });

        switch (listType) {
            default:
            case Stakeholder.CORE_TEAM:
                table.setPlaceholder(new Label("No Core Team Members Selected in the Stakeholder Wizard"));
                nameCol.setText("Selected Core Team Members");
                table.getStyleClass().add("list-table-core-team");
                break;
            case Stakeholder.STAKEHOLDER:
                table.setPlaceholder(new Label("No Stakeholder Agencies Indicated in the Stakeholder Wizard"));
                nameCol.setText("Selected Project Stakeholders");
                table.getStyleClass().add("list-table-stakeholder");
                break;
            case Stakeholder.NA:
                table.setPlaceholder(new Label("No Unlikely Agencies Specified in the Stakeholder Wizard"));
                nameCol.setText("Agencies with Unlikely Interest");
                table.getStyleClass().add("list-table-na");
                break;
        }

        TableColumn emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellFactory(new Callback<TableColumn<QuestionYN, String>, TableCell<QuestionYN, String>>() {
            @Override
            public TableCell<QuestionYN, String> call(TableColumn<QuestionYN, String> tc) {
                final TextFieldTableCell<QuestionYN, String> tfe = new TextFieldTableCell<QuestionYN, String>();
                tfe.setConverter(new DefaultStringConverter());
//                tfe.setOnKeyPressed(new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent t) {
//                        switch (t.getCode()) {
//                            case ENTER:
//                                tfe.commitEdit(tfe.getText());
//                                break;
//                            case ESCAPE:
//                                tfe.cancelEdit();
//                                break;
//                            case TAB:
//                                tfe.commitEdit(tfe.getText());
//                                break;
//                        }
//                    }
//                });
                return tfe;
            }
        });
        emailCol.setEditable(true);
        emailCol.setPrefWidth(150);
        emailCol.setMinWidth(150);
        emailCol.setMaxWidth(150);

        TableColumn phoneCol = new TableColumn<>("Phone #");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setEditable(true);
        phoneCol.setPrefWidth(150);
        phoneCol.setMinWidth(150);
        phoneCol.setMaxWidth(150);

        table.getColumns().addAll(indexCol, nameCol, emailCol, phoneCol);

        table.setItems(sList);

        return table;
    }

    private <T> void commitEditorText(TextFieldTableCell<QuestionYN, String> tfe) {
        if (!tfe.isEditable()) {
            return;
        }
        tfe.commitEdit(tfe.getText());
    }

    private BooleanProperty hasSchools = new SimpleBooleanProperty();

    public boolean isHasSchools() {
        return hasSchools.get();
    }

    public void setHasSchools(boolean value) {
        hasSchools.set(value);
    }

    public BooleanProperty hasSchoolsProperty() {
        return hasSchools;
    }
    private BooleanProperty touristRoute = new SimpleBooleanProperty();

    public boolean isTouristRoute() {
        return touristRoute.get();
    }

    public void setTouristRoute(boolean value) {
        touristRoute.set(value);
    }

    public BooleanProperty touristRouteProperty() {
        return touristRoute;
    }
    private BooleanProperty specialEventVenue = new SimpleBooleanProperty();

    public boolean isSpecialEventVenue() {
        return specialEventVenue.get();
    }

    public void setSpecialEventVenue(boolean value) {
        specialEventVenue.set(value);
    }

    public BooleanProperty specialEventVenueProperty() {
        return specialEventVenue;
    }
    private BooleanProperty transitOnRoute = new SimpleBooleanProperty();

    public boolean isTransitOnRoute() {
        return transitOnRoute.get();
    }

    public void setTransitOnRoute(boolean value) {
        transitOnRoute.set(value);
    }

    public BooleanProperty transitOnRouteProperty() {
        return transitOnRoute;
    }
    private BooleanProperty otherWorkZones = new SimpleBooleanProperty();

    public boolean isOtherWorkZones() {
        return otherWorkZones.get();
    }

    public void setOtherWorkZones(boolean value) {
        otherWorkZones.set(value);
    }

    public BooleanProperty otherWorkZonesProperty() {
        return otherWorkZones;
    }
    private BooleanProperty emergencyResponseCorridor = new SimpleBooleanProperty();

    public boolean isEmergencyResponseCorridor() {
        return emergencyResponseCorridor.get();
    }

    public void setEmergencyResponseCorridor(boolean value) {
        emergencyResponseCorridor.set(value);
    }

    public BooleanProperty emergencyResponseCorridorProperty() {
        return emergencyResponseCorridor;
    }
    private BooleanProperty businessHourLnClosures = new SimpleBooleanProperty();

    public boolean isBusinessHourLnClosures() {
        return businessHourLnClosures.get();
    }

    public void setBusinessHourLnClosures(boolean value) {
        businessHourLnClosures.set(value);
    }

    public BooleanProperty businessHourLnClosuresProperty() {
        return businessHourLnClosures;
    }
    private BooleanProperty sideStreetRestrictions = new SimpleBooleanProperty();

    public boolean isSideStreetRestrictions() {
        return sideStreetRestrictions.get();
    }

    public void setSideStreetRestrictions(boolean value) {
        sideStreetRestrictions.set(value);
    }

    public BooleanProperty sideStreetRestrictionsProperty() {
        return sideStreetRestrictions;
    }
    private BooleanProperty freightCorridor = new SimpleBooleanProperty();

    public boolean isFreightCorridor() {
        return freightCorridor.get();
    }

    public void setFreightCorridor(boolean value) {
        freightCorridor.set(value);
    }

    public BooleanProperty freightCorridorProperty() {
        return freightCorridor;
    }
    private BooleanProperty pedBikeImpacts = new SimpleBooleanProperty();

    public boolean isPedBikeImpacts() {
        return pedBikeImpacts.get();
    }

    public void setPedBikeImpacts(boolean value) {
        pedBikeImpacts.set(value);
    }

    public BooleanProperty pedBikeImpactsProperty() {
        return pedBikeImpacts;
    }
    private BooleanProperty signalizedSystem = new SimpleBooleanProperty();

    public boolean isSignalizedSystem() {
        return signalizedSystem.get();
    }

    public void setSignalizedSystem(boolean value) {
        signalizedSystem.set(value);
    }

    public BooleanProperty signalizedSystemProperty() {
        return signalizedSystem;
    }
    private BooleanProperty unwantedLocalDiversion = new SimpleBooleanProperty();

    public boolean isUnwantedLocalDiversion() {
        return unwantedLocalDiversion.get();
    }

    public void setUnwantedLocalDiversion(boolean value) {
        unwantedLocalDiversion.set(value);
    }

    public BooleanProperty unwantedLocalDiversionProperty() {
        return unwantedLocalDiversion;
    }

    private BooleanProperty mobilityGoal = new SimpleBooleanProperty();

    public boolean isMobilityGoal() {
        return mobilityGoal.get();
    }

    public void setMobilityGoal(boolean value) {
        mobilityGoal.set(value);
    }

    public BooleanProperty mobilityGoalProperty() {
        return mobilityGoal;
    }
    private BooleanProperty productivityGoal = new SimpleBooleanProperty();

    public boolean isProductivityGoal() {
        return productivityGoal.get();
    }

    public void setProductivityGoal(boolean value) {
        productivityGoal.set(value);
    }

    public BooleanProperty productivityGoalProperty() {
        return productivityGoal;
    }
    private BooleanProperty regulatoryGoal = new SimpleBooleanProperty();

    public boolean isRegulatoryGoal() {
        return regulatoryGoal.get();
    }

    public void setRegulatoryGoal(boolean value) {
        regulatoryGoal.set(value);
    }

    public BooleanProperty regulatoryGoalProperty() {
        return regulatoryGoal;
    }
    private BooleanProperty safetyGoal = new SimpleBooleanProperty();

    public boolean isSafetyGoal() {
        return safetyGoal.get();
    }

    public void setSafetyGoal(boolean value) {
        safetyGoal.set(value);
    }

    public BooleanProperty safetyGoalProperty() {
        return safetyGoal;
    }
    private BooleanProperty travelerInfoGoal = new SimpleBooleanProperty();

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
    private StringProperty primaryStakeholder = new SimpleStringProperty();

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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeBoolean(this.isBusinessHourLnClosures());
        s.writeBoolean(this.isEmergencyResponseCorridor());
        s.writeBoolean(this.isFreightCorridor());
        s.writeBoolean(this.isHasSchools());
        s.writeBoolean(this.isMobilityGoal());
        s.writeBoolean(this.isOtherWorkZones());
        s.writeBoolean(this.isPedBikeImpacts());
        s.writeBoolean(this.isProductivityGoal());
        s.writeBoolean(this.isRegulatoryGoal());
        s.writeBoolean(this.isSafetyGoal());
        s.writeBoolean(this.isSideStreetRestrictions());
        s.writeBoolean(this.isSignalizedSystem());
        s.writeBoolean(this.isSpecialEventVenue());
        s.writeBoolean(this.isTouristRoute());
        s.writeBoolean(this.isTransitOnRoute());
        s.writeBoolean(this.isTravelerInfoGoal());
        s.writeBoolean(this.isUnwantedLocalDiversion());

        s.writeObject(optScoreMat);
        s.writeObject(ynScoreMat);

        s.writeObject(stakeholders.toArray(new Stakeholder[stakeholders.size()]));

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        this.businessHourLnClosures = new SimpleBooleanProperty(s.readBoolean());
        this.emergencyResponseCorridor = new SimpleBooleanProperty(s.readBoolean());
        this.freightCorridor = new SimpleBooleanProperty(s.readBoolean());
        this.hasSchools = new SimpleBooleanProperty(s.readBoolean());
        this.mobilityGoal = new SimpleBooleanProperty(s.readBoolean());
        this.otherWorkZones = new SimpleBooleanProperty(s.readBoolean());
        this.pedBikeImpacts = new SimpleBooleanProperty(s.readBoolean());
        this.productivityGoal = new SimpleBooleanProperty(s.readBoolean());
        this.regulatoryGoal = new SimpleBooleanProperty(s.readBoolean());
        this.safetyGoal = new SimpleBooleanProperty(s.readBoolean());
        this.sideStreetRestrictions = new SimpleBooleanProperty(s.readBoolean());
        this.signalizedSystem = new SimpleBooleanProperty(s.readBoolean());
        this.specialEventVenue = new SimpleBooleanProperty(s.readBoolean());
        this.touristRoute = new SimpleBooleanProperty(s.readBoolean());
        this.transitOnRoute = new SimpleBooleanProperty(s.readBoolean());
        this.travelerInfoGoal = new SimpleBooleanProperty(s.readBoolean());
        this.unwantedLocalDiversion = new SimpleBooleanProperty(s.readBoolean());

        optScoreMat = (int[][]) s.readObject();
        ynScoreMat = (int[][]) s.readObject();

        stakeholders = FXCollections.observableArrayList((Stakeholder[]) s.readObject());

    }

    public static final String SCORE_COL_NAME = "Priority";
    public static final int SCORE_COL_WIDTH = 200;
    public static final String ZERO_SCORE_TXT = "Not Recommended";
    public static final String LOW_CAT_LABEL = "Low";
    public static final String MED_CAT_LABEL = "Medium";
    public static final String HIGH_CAT_LABEL = "High";
    public static final String UNSCORED_LABEL = "Unscored";
    public static final int LOW_CAT_MIN = 1;
    public static final int LOW_CAT_MAX = 4;
    public static final int MED_CAT_MIN = 5;
    public static final int MED_CAT_MAX = 8;
    public static final int HIGH_CAT_MIN = 9;

}
