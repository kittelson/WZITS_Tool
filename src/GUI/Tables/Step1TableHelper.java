/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.Project;
import core.Question;
import core.QuestionOption;
import core.QuestionYN;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

/**
 *
 * @author ltrask
 */
public class Step1TableHelper {

    private static final int STEP_INDEX = 0;

    public static final int QS_PER_PAGE = 20;

    private static final String STEP1_TABLE_CSS = "step-one-table";

    public static TableView createPageTable(Project proj, int tableType, int page, int questionsPerPage) {
        switch (tableType) {
            case GOAL_WIZARD:
                return getGoalWizardPageTable(proj, page, questionsPerPage);
        }
        return null;
    }

    public static int getPageCount(Project proj, int tableType, int questionsPerPage) {
        switch (tableType) {
            default:
                return 1;
            case GOAL_WIZARD:
                return Math.floorDiv(proj.getNumGoalWizardQs(), questionsPerPage);
        }
    }

    public static int getPageCount(Project proj, int tableType) {
        return getPageCount(proj, tableType, QS_PER_PAGE);
    }

    public static TableView getGoalWizardPageTable(Project proj, int page, int questionsPerPage) {
        int startRow = page * questionsPerPage;
        int endRow = Math.min((page + 1) * questionsPerPage, proj.getNumGoalWizardQs());

        final TableView<QuestionYN> table = new TableView();
        table.setEditable(true);

        // Setting up table columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn indexCol = new TableColumn("#");
        //indexCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        indexCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<QuestionYN, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<QuestionYN, String> p) {
                return new ReadOnlyObjectWrapper(Integer.toString(table.getItems().indexOf(p.getValue()) + 1));
            }
        });
        indexCol.setPrefWidth(40);
        indexCol.setMaxWidth(40);
        indexCol.setMinWidth(40);
        indexCol.getStyleClass().add("col-style-center-bold");

        TableColumn questionCol = new TableColumn("Input Question");
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        questionCol.setCellFactory(new Callback<TableColumn<QuestionYN, String>, TableCell<QuestionYN, String>>() {
            @Override
            public TableCell<QuestionYN, String> call(TableColumn<QuestionYN, String> tc) {
                final TextFieldTableCell<QuestionYN, String> tfe = new TextFieldTableCell();
                tfe.setEditable(false);
                tfe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, final TableRow newVal) {
                        if (newVal.getItem() != null) {
                            final Question q = (Question) newVal.getItem();
                            q.visibleProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                                    tfe.setTextFill(newVal ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                                    //if (newVal) {
                                    //    tfe.setStyle("-fx-background-color: white;-fx-text-fill: black");
                                    //} else {
                                    //    tfe.setStyle("-fx-background-color: grey;-fx-text-fill: grey");
                                    //}
                                }
                            });
                            tfe.textFillProperty().addListener(new ChangeListener<Paint>() {
                                @Override
                                public void changed(ObservableValue<? extends Paint> ov, Paint oldVal, Paint newVal) {
                                    tfe.setTextFill(q.visibleProperty().get() ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                                    //if (q.visibleProperty().get()) {
                                    //    tfe.setStyle("-fx-background-color: white;-fx-text-fill: black");
                                    //} else {
                                    //    tfe.setStyle("-fx-background-color: grey;-fx-text-fill: grey");
                                    //}
                                }
                            });
                        }
                    }
                });
                return tfe;
            }
        });

        TableColumn responseCol = new TableColumn("User Response");
        responseCol.setPrefWidth(150);
        responseCol.setMaxWidth(150);
        responseCol.setMinWidth(150);
        responseCol.setCellValueFactory(new PropertyValueFactory<>("responseIdx"));

        final TableColumn yesCol = new TableColumn<>("Yes");
        yesCol.setCellValueFactory(new PropertyValueFactory<>("answerIsYes"));
        //yesCol.setCellFactory(CheckBoxTableCell.forTableColumn(yesCol));
        yesCol.setCellFactory(new Callback<TableColumn<QuestionYN, Boolean>, TableCell<QuestionYN, Boolean>>() {
            @Override
            public TableCell<QuestionYN, Boolean> call(TableColumn<QuestionYN, Boolean> tc) {
                final CheckBoxTableCell<QuestionYN, Boolean> cbe = new CheckBoxTableCell();
                cbe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, TableRow newVal) {
                        //System.out.println("Called");
                        if (newVal.getItem() != null) {
                            //cbe.disableProperty().bind(((Question) newVal.getItem()).lockedProperty());
                            cbe.editableProperty().bind(((Question) newVal.getItem()).lockedProperty().not());
                        }
                    }
                });
                return cbe;
            }
        });

        TableColumn noCol = new TableColumn("No");
        noCol.setCellValueFactory(new PropertyValueFactory<>("answerIsNo"));
        //noCol.setCellFactory(CheckBoxTableCell.forTableColumn(noCol));
        noCol.setCellFactory(new Callback<TableColumn<QuestionYN, Boolean>, TableCell<QuestionYN, Boolean>>() {
            @Override
            public TableCell<QuestionYN, Boolean> call(TableColumn<QuestionYN, Boolean> tc) {
                final CheckBoxTableCell<QuestionYN, Boolean> cbe = new CheckBoxTableCell();
                cbe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, TableRow newVal) {
                        //System.out.println("Called");
                        if (newVal.getItem() != null) {
                            //cbe.disableProperty().bind(((Question) newVal.getItem()).lockedProperty());
                            cbe.editableProperty().bind(((Question) newVal.getItem()).lockedProperty().not());
                        }
                    }
                });
                return cbe;
            }
        });

        yesCol.setPrefWidth(75);
        yesCol.setMaxWidth(75);
        yesCol.setMinWidth(75);
        noCol.setPrefWidth(75);
        noCol.setMaxWidth(75);
        noCol.setMinWidth(75);
        responseCol.getColumns().addAll(yesCol, noCol);

        table.getColumns().addAll(indexCol, questionCol, responseCol); // goalCol

        // Setting Table Content
        final ObservableList<QuestionYN> stepQuestions = FXCollections.observableArrayList(proj.getGoalWizardQs().subList(startRow, endRow));
        //stepQuestions.add(new QuestionYN(-1, "", CONTINUE_STR));
        //table.setItems(FXCollections.observableArrayList(stepQuestions.subList(startRow, endRow)));
        table.setItems(stepQuestions);
        table.getStyleClass().add("step-one-table");

        ContextMenu cMenu = new ContextMenu();
        MenuItem fillAllYesMenuItem = new MenuItem("Fill All Yes");
        fillAllYesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : stepQuestions) {
                    q.setAnswerIsYes(Boolean.TRUE);
                }
            }
        });
        MenuItem fillAllNoMenuItem = new MenuItem("Fill All No");
        fillAllNoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : stepQuestions) {
                    q.setAnswerIsNo(Boolean.TRUE);
                }
            }
        });
        Menu fillByTemplateMenu = new Menu("Fill By Template");
        MenuItem fillUrbanMenuItem = new MenuItem("Urban Template");
        MenuItem fillRuralMenuItem = new MenuItem("Rural Template");
        fillByTemplateMenu.getItems().addAll(fillUrbanMenuItem, fillRuralMenuItem);
        cMenu.getItems().addAll(fillAllYesMenuItem, fillAllNoMenuItem, fillByTemplateMenu);

        table.setContextMenu(cMenu);

        for (TableColumn tc : table.getColumns()) {
            tc.setSortable(false);
            if (tc.getColumns().size() > 0) {
                for (Object tcc : tc.getColumns()) {
                    ((TableColumn) tcc).setSortable(false);
                }
            }
        }

        // Uncomment to disable selection for the table
        //table.setSelectionModel(null);
        return table;
    }

    public static TableView createUserNeedsSupplemental(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getUNSupportQs(), new TableHelper.Options(STEP1_TABLE_CSS));
    }

    public static Node getITSResourcesPanel(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getITSResourcesQs(), new TableHelper.Options(STEP1_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qITSResourcesList);
    }

    public static TableView getMajorGoalsTable(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getMajorGoalsQs(), new TableHelper.Options(STEP1_TABLE_CSS));
    }

    public static GridPane getFeasibilityWizard(Project proj) {
        final GridPane pane = new GridPane();

        Node t1 = createFeasibilityOptTable(proj);//createFeasibilityOptTable(proj);
        TableView t2 = createFeasibilityYNTable(proj);
        pane.add(t1, 0, 0);
        pane.add(t2, 0, 1);

        int rowHeight = 170;
        pane.getRowConstraints().add(new RowConstraints(rowHeight, rowHeight, rowHeight, Priority.NEVER, VPos.CENTER, true));
        pane.getRowConstraints().add(new RowConstraints(1, 125, MainController.MAX_HEIGHT, Priority.ALWAYS, VPos.CENTER, true));
        GridPane.setHgrow(t1, Priority.ALWAYS);
        GridPane.setHgrow(t2, Priority.ALWAYS);

        return pane;
    }

    private static Node createFeasibilityOptTable(Project proj) {
        //return TableHelper.createQuestionOptionTable(proj.getFeasWizardOptQs(), STEP1_TABLE_CSS);
        return createFeasOptGrid(proj);
    }

    private static TableView createFeasibilityYNTable(Project proj) {
        TableHelper.Options tOpts = new TableHelper.Options(STEP1_TABLE_CSS);
        tOpts.qColumnHeader = "Mark All That Apply";
        tOpts.showFeasibilityScore = true;
        return TableHelper.createQuestionYNTable(proj.getFeasWizardYNQs(), tOpts);
    }

    private static GridPane createFeasOptGrid(Project proj) {
        GridPane pane = new GridPane();
        pane.add(NodeFactory.createFormattedLabel("#", "opt-pane-title"), 0, 0);
        pane.add(NodeFactory.createFormattedLabel("Input Question", "opt-pane-title"), 1, 0);
        pane.add(NodeFactory.createFormattedLabel("User Response", "opt-pane-title"), 2, 0);
        pane.add(NodeFactory.createFormattedLabel("Contributed Score", "opt-pane-title"), 3, 0);
        double rowSplit = 100.0 / (proj.getFeasWizardOptQs().size() + 2);
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(rowSplit * 2);
        pane.getRowConstraints().add(rc);
        int qIdx = 1;
        for (final QuestionOption q : proj.getFeasWizardOptQs()) {
            final ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(q.getOptions()));
            if (q.getResponseIdx() >= 0) {
                cb.getSelectionModel().select(q.getResponseIdx());
            }
            cb.setMaxWidth(MainController.MAX_WIDTH);
            cb.setMaxHeight(MainController.MAX_HEIGHT);
            cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    q.setAnswer((String) cb.getSelectionModel().getSelectedItem());
                }
            });
            cb.disableProperty().bind(q.lockedProperty());
            cb.disableProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldVal, Boolean newVal) {
                    // If disable, select lowest impact (last) option
                    if (newVal) {
                        cb.getSelectionModel().selectLast();
                    }
                }
            });
            Label qIdxLabel = NodeFactory.createFormattedLabel(String.valueOf(qIdx), "opt-pane-question-idx");
            qIdxLabel.disableProperty().bind(q.lockedProperty());
            final Label qLabel = NodeFactory.createFormattedLabel(q.getQuestionText(), "opt-pane-question");
            qLabel.disableProperty().bind(q.lockedProperty());
            qLabel.disableProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldVal, Boolean newVal) {
                    // If disable, select lowest impact (last) option
                    if (newVal) {
                        qLabel.setText(q.getLockedQuestionText());
                    } else {
                        qLabel.setText(q.getQuestionText());
                    }
                }
            });
            final Label qScoreLabel = NodeFactory.createFormattedLabel("0", "opt-pane-question-idx");
            qScoreLabel.textProperty().bind(q.scoreProperty().asString());
            pane.add(qIdxLabel, 0, qIdx);
            pane.add(qLabel, 1, qIdx);
            pane.add(cb, 2, qIdx);
            pane.add(qScoreLabel, 3, qIdx);
            rc = new RowConstraints();
            rc.setPercentHeight(rowSplit);
            pane.getRowConstraints().add(rc);
            qIdx++;
        }

        int col1Width = 25;
        int col3Width = 350;
        int col4Width = 100;
        pane.getColumnConstraints().add(0, new ColumnConstraints(col1Width, col1Width, col1Width, Priority.NEVER, HPos.RIGHT, true));
        pane.getColumnConstraints().add(1, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        pane.getColumnConstraints().add(2, new ColumnConstraints(col3Width, col3Width, col3Width, Priority.NEVER, HPos.RIGHT, true));
        pane.getColumnConstraints().add(3, new ColumnConstraints(col4Width, col4Width, col4Width, Priority.NEVER, HPos.RIGHT, true));

        return pane;
    }

    public static GridPane createStakeholderWizard(Project proj) {
        final GridPane pane = new GridPane();

        Node t1 = createStakeholderOptTable(proj);
        TableView t2 = createStakeholderYNTable(proj);
        pane.add(t1, 0, 0);
        pane.add(t2, 0, 1);

        int rowHeight = 170;
        pane.getRowConstraints().add(new RowConstraints(rowHeight, rowHeight, rowHeight, Priority.NEVER, VPos.CENTER, true));
        pane.getRowConstraints().add(new RowConstraints(1, 125, MainController.MAX_HEIGHT, Priority.ALWAYS, VPos.CENTER, true));
        GridPane.setHgrow(t1, Priority.ALWAYS);
        GridPane.setHgrow(t2, Priority.ALWAYS);
        return pane;
    }

    private static Node createStakeholderOptTable(Project proj) {
        //return TableHelper.createQuestionOptionTable(proj.getStakeWizardOptQs(), STEP1_TABLE_CSS);
        return createStakeholderOptGrid(proj);
    }

    private static GridPane createStakeholderOptGrid(Project proj) {
        GridPane pane = new GridPane();
        pane.add(NodeFactory.createFormattedLabel("#", "opt-pane-title"), 0, 0);
        pane.add(NodeFactory.createFormattedLabel("Input Question", "opt-pane-title"), 1, 0);
        pane.add(NodeFactory.createFormattedLabel("User Response", "opt-pane-title"), 2, 0);
        double rowSplit = 100.0 / (proj.getNumStakeWizardOptQs() + 2);
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(rowSplit * 2);
        pane.getRowConstraints().add(rc);
        int qIdx = 1;
        for (final QuestionOption q : proj.getStakeWizardOptQs()) {
            Node displayNode;
            if (!q.isLocked()) {
                final ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(q.getOptions()));
                if (q.getResponseIdx() >= 0) {
                    cb.getSelectionModel().select(q.getResponseIdx());
                }
                cb.setMaxWidth(MainController.MAX_WIDTH);
                cb.setMaxHeight(MainController.MAX_HEIGHT);
                cb.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent ae) {
                        q.setAnswer((String) cb.getSelectionModel().getSelectedItem());
                    }
                });
                displayNode = cb;
            } else {
                displayNode = NodeFactory.createFormattedLabel("", "opt-pane-question-response");
                ((Label) displayNode).textProperty().bind(q.answerStringProperty());
            }
            pane.add(NodeFactory.createFormattedLabel(String.valueOf(qIdx), "opt-pane-question-idx"), 0, qIdx);
            pane.add(NodeFactory.createFormattedLabel(q.getQuestionText(), "opt-pane-question"), 1, qIdx);
            pane.add(displayNode, 2, qIdx);
            rc = new RowConstraints();
            rc.setPercentHeight(rowSplit);
            pane.getRowConstraints().add(rc);
            qIdx++;
        }

        int col1Width = 25;
        int col3Width = 350;
        pane.getColumnConstraints().add(0, new ColumnConstraints(col1Width, col1Width, col1Width, Priority.NEVER, HPos.RIGHT, true));
        pane.getColumnConstraints().add(1, new ColumnConstraints(1, 125, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        pane.getColumnConstraints().add(2, new ColumnConstraints(col3Width, col3Width, col3Width, Priority.NEVER, HPos.RIGHT, true));

        return pane;
    }

    private static TableView createStakeholderYNTable(Project proj) {
        TableHelper.Options tOpts = new TableHelper.Options(STEP1_TABLE_CSS);
        tOpts.qColumnHeader = "Mark All That Apply";
        return TableHelper.createQuestionYNTable(proj.getStakeWizardYNQs(), tOpts);
    }

    public static final int GOAL_WIZARD = 0;
    public static final int FEASIBILITY_WIZARD = 1;
    public static final int STAKEHOLDER_WIZARD = 2;

    private static final String CONTINUE_STR = "Continued on next page (Use arrows below)";
}
