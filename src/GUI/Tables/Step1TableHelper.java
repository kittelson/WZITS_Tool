/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.GoalNeedsMatrix;
import core.Need;
import core.Project;
import core.Question;
import core.QuestionOption;
import core.QuestionYN;
import core.Stakeholder;
import core.StakeholderMatrix;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
        return TableHelper.createCommentPageYN(proj.getQGen().qITSResourcesList);
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

    public static Node createStepSummary(MainController mc) {
        int lfs = 16;
        final Project p = mc.getProject();
        BorderPane bPane = new BorderPane();
        bPane.getStyleClass().add("fact-sheet-pane");
        bPane.setTop(NodeFactory.createFormattedLabel("Project Info and WZ Metadata", "fact-sheet-title-large"));
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
                try {
                    Runtime.getRuntime().exec("cmd /c start " + p.getUrlLink());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        projHL.setMaxWidth(MainController.MAX_WIDTH);
        projHL.setMaxHeight(MainController.MAX_HEIGHT);
        infoGrid.add(projHL, 1, rowIdx++);

        ColumnConstraints igcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints igcc2 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        infoGrid.getColumnConstraints().addAll(igcc1, igcc2);
        infoGrid.setMaxWidth(500);

        final GridPane wzMetaGrid = new GridPane();
        rowIdx = 0;
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Work Zone Meta Data", "fact-sheet-label-bold"), 0, rowIdx, 1, 9);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Average Annual Daily Traffic:", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getAadt()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Functional Class of Roadway:", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(p.getFunctionalClass(), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Length of Work Zone (mi):", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getWzLength()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Type of Work Zone:", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(p.getWzType(), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Number of Lanes (1 Direction):", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getNumRoadwayLanes()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Shoulder Width (ft):", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getShoulderWidth()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Posted Speed Limit (mph):", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getSpeedLimit()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Number of Lanes to be Closed:", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getNumLanesClosed()), "fact-sheet-label"), 2, rowIdx++);
        wzMetaGrid.add(NodeFactory.createFormattedLabel("Duration of Activity:", "fact-sheet-label"), 1, rowIdx);
        wzMetaGrid.add(NodeFactory.createFormattedLabel(String.valueOf(p.getActivityDuration()), "fact-sheet-label"), 2, rowIdx++);

        ColumnConstraints wzgcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints wzgcc2 = new ColumnConstraints(275, 275, 275, Priority.ALWAYS, HPos.LEFT, true);
        ColumnConstraints wzgcc3 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        wzMetaGrid.getColumnConstraints().addAll(wzgcc1, wzgcc2, wzgcc3);
        wzMetaGrid.setMaxWidth(500);

        ImageView projImage = new ImageView(p.getProjPhoto() != null ? p.getProjPhoto() : IconHelper.PROJ_IMAGE);
        projImage.setPreserveRatio(true);
//        final GridPane infoMetaGrid = new GridPane();
//        infoMetaGrid.add(infoGrid, 0, 0);
//        infoMetaGrid.add(wzMetaGrid, 0, 1);
        final VBox infoMetaGrid = new VBox();
        infoMetaGrid.getChildren().addAll(infoGrid, wzMetaGrid);
        final GridPane infoAndPicGrid = new GridPane();
        infoAndPicGrid.getStyleClass().add("fact-sheet-pane-info");
//        DoubleBinding widthBinding = new DoubleBinding() {
//            {
//                super.bind(infoAndPicGrid.widthProperty());
//            }
//
//            @Override
//            protected double computeValue() {
//                return infoAndPicGrid.widthProperty().get() - 505;
//            }
//        };
//        projImage.fitWidthProperty().bind(widthBinding);
//        DoubleBinding heightBinding = new DoubleBinding() {
//            {
//                super.bind(infoGrid.heightProperty());
//                super.bind(wzMetaGrid.heightProperty());
//            }
//
//            @Override
//            protected double computeValue() {
//                return infoGrid.heightProperty().get() + wzMetaGrid.heightProperty().get();
//            }
//        };
//        projImage.fitHeightProperty().bind(heightBinding);
        projImage.setFitHeight(350);
        infoAndPicGrid.getStyleClass().add("fact-sheet-pane");
        //infoMetaGrid.setStyle("-fx-background-color: green");
        //infoAndPicGrid.setStyle("-fx-background-color: blue");
        infoAndPicGrid.add(infoMetaGrid, 0, 0);
        infoAndPicGrid.add(projImage, 1, 0);
        infoAndPicGrid.getColumnConstraints().add(new ColumnConstraints(500, 500, 500, Priority.NEVER, HPos.LEFT, true));
        infoAndPicGrid.getColumnConstraints().add(new ColumnConstraints(1, 500, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true));
        //VBox.setVgrow(infoAndPicGrid, Priority.NEVER);

        // Create recommended user goals
        GoalNeedsMatrix gnm = p.getGoalNeedsMatrix();
        gnm.computeScores();
        // Mobility Goals
        ObservableList<Need> ml = gnm.getGoalListByType(Question.GOAL_MOBILITY);
        // Safety Goals
        ObservableList<Need> sl = gnm.getGoalListByType(Question.GOAL_SAFETY);
        // Productivity Goals
        ObservableList<Need> pl = gnm.getGoalListByType(Question.GOAL_PROD);
        // Regulatory Goals
        ObservableList<Need> rl = gnm.getGoalListByType(Question.GOAL_REG);
        // Traveler Information Goals
        ObservableList<Need> tl = gnm.getGoalListByType(Question.GOAL_TRAVELER_INFO);
        // Creating panel
        GridPane goalsGrid = new GridPane();
        goalsGrid.getStyleClass().add("fact-sheet-pane-goal");
        goalsGrid.add(NodeFactory.createFormattedLabel("Category", "fact-sheet-title-small"), 0, 0);
        goalsGrid.add(NodeFactory.createFormattedLabel("Recommended User Goals by WZITS Tool", "fact-sheet-title-small"), 1, 0);
        goalsGrid.add(NodeFactory.createFormattedLabel("Score", "fact-sheet-title-small"), 2, 0);

        rowIdx = 1;
        goalsGrid.add(NodeFactory.createFormattedLabel(Question.GOAL_MOBILITY, "fact-sheet-label-goal-bold"), 0, rowIdx, 1, ml.size());
        for (Need n : ml) {
            //goalsGrid.add(NodeFactory.createFormattedLabel(n.getDescription(), "fact-sheet-label-goal"), 1, rowIdx);
            //goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()), "fact-sheet-label-goal"), 2, rowIdx++);
            if (!n.isPlaceholder) {
                goalsGrid.add(NodeFactory.createFormattedLabel((n.isSelected() ? "*" : "") + n.getDescription(),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 2, rowIdx++);
            } else {
                goalsGrid.add(NodeFactory.createFormattedLabel("(*Indicates the goal was selected by the user.)",
                        "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel("", "fact-sheet-label-goal"), 2, rowIdx++);
            }
        }

        goalsGrid.add(NodeFactory.createFormattedLabel(Question.GOAL_SAFETY, "fact-sheet-label-goal-bold"), 0, rowIdx, 1, sl.size());
        for (Need n : sl) {
            //goalsGrid.add(NodeFactory.createFormattedLabel(n.getDescription(), "fact-sheet-label-goal"), 1, rowIdx);
            //goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()), "fact-sheet-label-goal"), 2, rowIdx++);
            if (!n.isPlaceholder) {
                goalsGrid.add(NodeFactory.createFormattedLabel((n.isSelected() ? "*" : "") + n.getDescription(),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 2, rowIdx++);
            } else {
                goalsGrid.add(NodeFactory.createFormattedLabel("(*Indicates the goal was selected by the user.)",
                        "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel("", "fact-sheet-label-goal"), 2, rowIdx++);
            }
        }

        goalsGrid.add(NodeFactory.createFormattedLabel(Question.GOAL_PROD, "fact-sheet-label-goal-bold"), 0, rowIdx, 1, pl.size());
        for (Need n : pl) {
            //goalsGrid.add(NodeFactory.createFormattedLabel(n.getDescription(), "fact-sheet-label-goal"), 1, rowIdx);
            //goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()), "fact-sheet-label-goal"), 2, rowIdx++);
            if (!n.isPlaceholder) {
                goalsGrid.add(NodeFactory.createFormattedLabel((n.isSelected() ? "*" : "") + n.getDescription(),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 2, rowIdx++);
            } else {
                goalsGrid.add(NodeFactory.createFormattedLabel("(*Indicates the goal was selected by the user.)",
                        "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel("", "fact-sheet-label-goal"), 2, rowIdx++);
            }
        }

        goalsGrid.add(NodeFactory.createFormattedLabel(Question.GOAL_REG, "fact-sheet-label-goal-bold"), 0, rowIdx, 1, rl.size());
        for (Need n : rl) {
            //goalsGrid.add(NodeFactory.createFormattedLabel(n.getDescription(), "fact-sheet-label-goal"), 1, rowIdx);
            //goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()), "fact-sheet-label-goal"), 2, rowIdx++);
            if (!n.isPlaceholder) {
                goalsGrid.add(NodeFactory.createFormattedLabel((n.isSelected() ? "*" : "") + n.getDescription(),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 2, rowIdx++);
            } else {
                goalsGrid.add(NodeFactory.createFormattedLabel("(*Indicates the goal was selected by the user.)",
                        "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel("", "fact-sheet-label-goal"), 2, rowIdx++);
            }
        }

        goalsGrid.add(NodeFactory.createFormattedLabel(Question.GOAL_TRAVELER_INFO, "fact-sheet-label-goal-bold"), 0, rowIdx, 1, tl.size());
        for (Need n : tl) {
            //goalsGrid.add(NodeFactory.createFormattedLabel(n.getDescription(), "fact-sheet-label-goal"), 1, rowIdx);
            //goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()), "fact-sheet-label-goal"), 2, rowIdx++);
            if (!n.isPlaceholder) {
                goalsGrid.add(NodeFactory.createFormattedLabel((n.isSelected() ? "*" : "") + n.getDescription(),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel(String.valueOf(n.getScore()),
                        n.isSelected() ? "fact-sheet-label-goal-bold" : "fact-sheet-label-goal"), 2, rowIdx++);
            } else {
                goalsGrid.add(NodeFactory.createFormattedLabel("(*Indicates the goal was selected by the user.)",
                        "fact-sheet-label-goal"), 1, rowIdx);
                goalsGrid.add(NodeFactory.createFormattedLabel("", "fact-sheet-label-goal"), 2, rowIdx++);
            }
        }

        goalsGrid.getColumnConstraints().add(new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.CENTER, true));
        goalsGrid.getColumnConstraints().add(new ColumnConstraints(1, infoC1Width, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.CENTER, true));
        goalsGrid.getColumnConstraints().add(new ColumnConstraints(50, 50, 50, Priority.NEVER, HPos.CENTER, true));

        // Feasibility
        GridPane feasGrid = p.getFeasibilityMatrix().createSummaryPanel();
        feasGrid.getStyleClass().add("fact-sheet-pane-feasibility");

        // Team Members and Stake Holders
        StakeholderMatrix sMat = p.getStakeholderMatrix();
        sMat.computeStakeholders();
        ArrayList<Stakeholder> coreTeam = new ArrayList();
        ArrayList<Stakeholder> stakeholders = new ArrayList();
        for (Stakeholder sh : sMat.stakeholders) {
            if (sh.isCoreTeamMember()) {
                coreTeam.add(sh);
            } else if (sh.isStakeholder()) {
                stakeholders.add(sh);
            }
        }

        GridPane stakeGrid = new GridPane();
        stakeGrid.add(NodeFactory.createFormattedLabel("Selected Team and Stakeholders", "fact-sheet-title-stake-grid"), 0, 0, 4, 1);
        rowIdx = 1;
        stakeGrid.add(NodeFactory.createFormattedLabel("#", "fact-sheet-title-core-team"), 0, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Core Team Members", "fact-sheet-title-core-team"), 1, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Email", "fact-sheet-title-core-team"), 2, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Phone", "fact-sheet-title-core-team"), 3, rowIdx++, 1, 1);
        for (Stakeholder sh : coreTeam) {
            stakeGrid.add(NodeFactory.createFormattedLabel(String.valueOf(rowIdx - 1), "fact-sheet-label-core-team"), 0, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getName(), "fact-sheet-label-core-team"), 1, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getEmail(), "fact-sheet-label-core-team"), 2, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getPhone(), "fact-sheet-label-core-team"), 3, rowIdx++);
        }
        stakeGrid.add(NodeFactory.createFormattedLabel("#", "fact-sheet-title-stakeholder"), 0, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Stakeholders", "fact-sheet-title-stakeholder"), 1, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Email", "fact-sheet-title-stakeholder"), 2, rowIdx, 1, 1);
        stakeGrid.add(NodeFactory.createFormattedLabel("Phone", "fact-sheet-title-stakeholder"), 3, rowIdx++, 1, 1);
        for (Stakeholder sh : stakeholders) {
            stakeGrid.add(NodeFactory.createFormattedLabel(String.valueOf(rowIdx - 1), "fact-sheet-label-stakeholder"), 0, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getName(), "fact-sheet-label-stakeholder"), 1, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getEmail(), "fact-sheet-label-stakeholder"), 2, rowIdx);
            stakeGrid.add(NodeFactory.createFormattedLabel(sh.getPhone(), "fact-sheet-label-stakeholder"), 3, rowIdx++);
        }

        stakeGrid.getColumnConstraints().add(new ColumnConstraints(35, 35, 35, Priority.NEVER, HPos.CENTER, true));
        stakeGrid.getColumnConstraints().add(new ColumnConstraints(1, 200, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        stakeGrid.getColumnConstraints().add(new ColumnConstraints(250, 250, 250, Priority.NEVER, HPos.CENTER, true));
        stakeGrid.getColumnConstraints().add(new ColumnConstraints(150, 150, 150, Priority.NEVER, HPos.CENTER, true));

        VBox factSheetVBox = new VBox();
        factSheetVBox.getChildren().addAll(infoAndPicGrid, goalsGrid, feasGrid, stakeGrid);
        ScrollPane sp = new ScrollPane();

        bPane.setCenter(factSheetVBox);
        sp.setContent(bPane);
        //System.out.println("Here");
        return sp;
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
