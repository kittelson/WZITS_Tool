/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.Question;
import core.QuestionOption;
import core.QuestionOptionMS;
import core.QuestionYN;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
public class TableHelper {

    public static TableView createQuestionYNTable(final ObservableList<QuestionYN> qList, Options opts) {
        final TableView<QuestionYN> table = new TableView();
        table.setEditable(true);

        // Setting up table columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn indexCol = new TableColumn("#");
        if (!opts.autoIndex) {
            indexCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        } else {
            indexCol.setCellValueFactory(new Callback<CellDataFeatures<QuestionYN, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<QuestionYN, String> p) {
                    return new ReadOnlyObjectWrapper(Integer.toString(table.getItems().indexOf(p.getValue()) + 1));
                }
            });
        }
        indexCol.setPrefWidth(35);
        indexCol.setMaxWidth(35);
        indexCol.setMinWidth(35);
        indexCol.getStyleClass().add("col-style-center-bold");

        TableColumn questionCol = new TableColumn(opts != null && opts.qColumnHeader != null ? opts.qColumnHeader : "Input Question");
        questionCol.setEditable(false);
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        questionCol.setCellFactory(new Callback<TableColumn<QuestionYN, String>, TableCell<QuestionYN, String>>() {
            @Override
            public TableCell<QuestionYN, String> call(TableColumn<QuestionYN, String> tc) {
                final TextFieldTableCell<QuestionYN, String> tfe = new TextFieldTableCell<QuestionYN, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item);
                            if (qList.get(this.getIndex()).hasMoreInfo) {
                                Hyperlink hl = new Hyperlink("(more info)");
                                hl.getStyleClass().add("wz-input-hyperlink");
                                final String comm = qList.get(this.getIndex()).getComment();
                                hl.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent ae) {
                                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                                        al.initOwner(MainController.getWindow());
                                        al.setTitle("More Information");
                                        al.setHeaderText("Emergency Response Corridor");
                                        al.setContentText(comm);
                                        al.showAndWait();
                                    }
                                });
                                this.setGraphic(hl);
                                this.setContentDisplay(ContentDisplay.RIGHT);
                                //setTextFill(qList.get(this.getIndex()).isVisible() ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                            }
                        }
                    }
                };
                tfe.setEditable(false);
                tfe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, TableRow newVal) {
                        if (newVal.getItem() != null) {
                            final Question q = (Question) newVal.getItem();

                            tfe.setTextFill(q.isVisible() ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                            //tfe.getStyleClass().add(q.visibleProperty().get() ? "question-visible" : "question-hidden");
                            q.visibleProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                                    tfe.setTextFill(newVal ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                                }
                            });
                            tfe.textFillProperty().addListener(new ChangeListener<Paint>() {
                                @Override
                                public void changed(ObservableValue<? extends Paint> ov, Paint oldVal, Paint newVal) {
                                    if (!tfe.isHover()) {
                                        tfe.setTextFill(q.visibleProperty().get() ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                                    }
                                }
                            });
                            tfe.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent me) {
                                    if (!q.isVisible()) {
                                        tfe.setTextFill(Color.BLACK);
                                        tfe.updateItem(tfe.getItem(), tfe.isEmpty());
                                    }
                                }
                            });
                            tfe.setOnMouseExited(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent me) {
                                    if (!q.isVisible()) {
                                        tfe.setTextFill(TableHelper.COLOR_HIDDEN);
                                    }
                                }
                            });
                            newVal.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                                    tfe.setStyle(newVal ? "-fx-background-insets: 0, 1 0 1 0" : "-fx-background-insets: 0, 0 0 1 0");
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
        table.getColumns().add(indexCol);

        // Adding optional columns
        if (opts.showAppWizardGoalCategory) {
            TableColumn catCol = new TableColumn("Category");
            catCol.setCellValueFactory(new PropertyValueFactory<>("goal"));
            int catWidth = 115;
            catCol.setPrefWidth(catWidth);
            catCol.setMinWidth(catWidth);
            catCol.setMaxWidth(catWidth);
            catCol.getStyleClass().add("col-style-center");
            table.getColumns().add(catCol);
        }

        if (opts.showRedundantQIdx) {
            TableColumn refQIdxCol = new TableColumn("Ref #");
            refQIdxCol.setCellValueFactory(new PropertyValueFactory<>("refText"));
            int colWidth = 150;
            refQIdxCol.setPrefWidth(colWidth);
            refQIdxCol.setMinWidth(colWidth);
            refQIdxCol.setMaxWidth(colWidth);
            refQIdxCol.getStyleClass().add("col-style-center");
            table.getColumns().add(refQIdxCol);
        }

        table.getColumns().addAll(questionCol, responseCol);

        if (opts.showFeasibilityScore) {
            TableColumn scoreCol = new TableColumn<>("Contributed Score");
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
            scoreCol.setPrefWidth(100);
            scoreCol.setMaxWidth(100);
            scoreCol.setMinWidth(100);
            scoreCol.getStyleClass().add("col-style-center-bold");
            table.getColumns().add(scoreCol);
        }

        // goalCol
        //final ObservableList<QuestionYN> qList = ;
        table.setItems(qList);
        table.getStyleClass().add(opts.tableStyleCSS);

        ContextMenu cMenu = new ContextMenu();
        MenuItem fillAllYesMenuItem = new MenuItem("Fill All Yes");
        fillAllYesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : qList) {
                    if (!q.isLocked()) {
                        q.setAnswerIsYes(Boolean.TRUE);
                    }
                }
            }
        });
        MenuItem fillAllNoMenuItem = new MenuItem("Fill All No");
        fillAllNoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (QuestionYN q : qList) {
                    if (!q.isLocked()) {
                        q.setAnswerIsNo(Boolean.TRUE);
                    }
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

        return table;
    }

    public static TableView createQuestionOptionTable(ObservableList<QuestionOption> qList, Options opts) {
        final TableView<QuestionOption> table = new TableView();
        table.setEditable(true);

        // Setting up table columns
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn indexCol = new TableColumn("#");
        if (!opts.autoIndex) {
            indexCol.setCellValueFactory(new PropertyValueFactory<>("idx"));
        } else {
            indexCol.setCellValueFactory(new Callback<CellDataFeatures<QuestionYN, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<QuestionYN, String> p) {
                    return new ReadOnlyObjectWrapper(Integer.toString(table.getItems().indexOf(p.getValue()) + 1));
                }
            });
        }
        indexCol.setPrefWidth(35);
        indexCol.setMaxWidth(35);
        indexCol.setMinWidth(35);
        indexCol.getStyleClass().add("col-style-center-bold");

        TableColumn questionCol = new TableColumn(opts != null && opts.qColumnHeader != null ? opts.qColumnHeader : "Input Question");
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        questionCol.setCellFactory(new Callback<TableColumn<QuestionYN, String>, TableCell<QuestionYN, String>>() {
            @Override
            public TableCell<QuestionYN, String> call(TableColumn<QuestionYN, String> tc) {
                final TextFieldTableCell<QuestionYN, String> tfe = new TextFieldTableCell();
                tfe.tableRowProperty().addListener(new ChangeListener<TableRow>() {
                    @Override
                    public void changed(ObservableValue<? extends TableRow> ov, TableRow oldVal, TableRow newVal) {
                        if (newVal.getItem() != null) {
                            final Question q = (Question) newVal.getItem();
                            q.visibleProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                                    tfe.setTextFill(newVal ? Color.BLACK : TableHelper.COLOR_HIDDEN);
                                }
                            });
                            tfe.textFillProperty().addListener(new ChangeListener<Paint>() {
                                @Override
                                public void changed(ObservableValue<? extends Paint> ov, Paint oldVal, Paint newVal) {
                                    tfe.setTextFill(q.visibleProperty().get() ? Color.BLACK : TableHelper.COLOR_HIDDEN);
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
        responseCol.setCellFactory(new Callback<TableColumn<QuestionOption, String>, TableCell<QuestionOption, String>>() {
            @Override
            public TableCell<QuestionOption, String> call(TableColumn<QuestionOption, String> param) {
                //ObservableList<String> optList = FXCollections.observableArrayList(param.getTableView().getItems().get(0).getOptions());
                //ObservableList<String> optList = FXCollections.observableArrayList(param.getTableView());
                return new ComboBoxTableCell() {
                    @Override
                    public ObservableList<String> getItems() {
                        return FXCollections.observableArrayList(((QuestionOption) this.getTableRow().getItem()).getOptions());
                    }
                };
            }
        });
        responseCol.setOnEditCommit(new EventHandler<CellEditEvent<QuestionOption, String>>() {
            @Override
            public void handle(CellEditEvent<QuestionOption, String> t) {
                ((QuestionOption) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAnswer(t.getNewValue());
            }
        });

        table.getColumns().addAll(indexCol, questionCol, responseCol);  // goalCol

        //final ObservableList<QuestionYN> qList = ;
        table.setItems(qList);
        table.getStyleClass().add(opts.tableStyleCSS);

        ContextMenu cMenu = new ContextMenu();
        Menu fillByTemplateMenu = new Menu("Fill By Template");
        MenuItem fillUrbanMenuItem = new MenuItem("Urban Template");
        MenuItem fillRuralMenuItem = new MenuItem("Rural Template");
        fillByTemplateMenu.getItems().addAll(fillUrbanMenuItem, fillRuralMenuItem);
        cMenu.getItems().addAll(fillByTemplateMenu);

        table.setContextMenu(cMenu);

        for (TableColumn tc : table.getColumns()) {
            tc.setSortable(false);
        }

        return table;
    }

    public static Node createCommentQ(int idx, Question q) {
        GridPane gPane = new GridPane();
        gPane.getStyleClass().add("comment-q-pane");
        Label idxLabel = NodeFactory.createFormattedLabel(String.valueOf(idx) + ":", "opt-pane-question-idx");
        Label qText = NodeFactory.createFormattedLabel(q.getQuestionText(), "opt-pane-question");
        // Ensuring Components will grow
        GridPane.setVgrow(idxLabel, Priority.ALWAYS);
        GridPane.setVgrow(qText, Priority.ALWAYS);
        GridPane.setHgrow(idxLabel, Priority.ALWAYS);
        GridPane.setHgrow(qText, Priority.ALWAYS);
        // Adding components to sub gridpane
        GridPane subGrid = new GridPane();
        subGrid.add(idxLabel, 0, 0);
        subGrid.add(qText, 1, 0);
        // Adding column constraints, idx is fixed, question text fills remaining space
        ColumnConstraints cc1 = new ColumnConstraints(35, 35, 35, Priority.NEVER, HPos.CENTER, true);
        ColumnConstraints cc2 = new ColumnConstraints(1, 350, MainController.MAX_HEIGHT, Priority.ALWAYS, HPos.LEFT, true);

        subGrid.getColumnConstraints().addAll(cc1, cc2);

        switch (q.getCommentQType()) {
            case Question.COMMENT_QTYPE_YN:
                QuestionYN qyn = (QuestionYN) q;
                CheckBox yesCheck = new CheckBox("Yes");
                yesCheck.getStyleClass().add("comment-pane-checkbox");
                yesCheck.selectedProperty().bindBidirectional(qyn.answerIsYesProperty());
                CheckBox noCheck = new CheckBox("No");
                noCheck.getStyleClass().add("comment-pane-checkbox");
                noCheck.selectedProperty().bindBidirectional(qyn.answerIsNoProperty());
                GridPane.setVgrow(yesCheck, Priority.ALWAYS);
                GridPane.setVgrow(noCheck, Priority.ALWAYS);
                GridPane.setHgrow(yesCheck, Priority.ALWAYS);
                GridPane.setHgrow(noCheck, Priority.ALWAYS);
                subGrid.add(yesCheck, 2, 0);
                subGrid.add(noCheck, 3, 0);
                ColumnConstraints ccY = new ColumnConstraints(85, 85, 85, Priority.NEVER, HPos.CENTER, true);
                ColumnConstraints ccN = new ColumnConstraints(85, 85, 85, Priority.NEVER, HPos.CENTER, true);
                subGrid.getColumnConstraints().addAll(ccY, ccN);
                break;
            case Question.COMMENT_QTYPE_OPT:
                QuestionOption qo = (QuestionOption) q;
                ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(qo.getOptions()));
                cb.setMaxWidth(MainController.MAX_WIDTH);
                GridPane.setHgrow(cb, Priority.ALWAYS);
                GridPane.setVgrow(cb, Priority.ALWAYS);
                if (qo.getResponseIdx() >= 0) {
                    cb.getSelectionModel().select(idx);
                }
                qo.responseIdxProperty().bind(cb.getSelectionModel().selectedIndexProperty());
                subGrid.add(cb, 2, 0);
                ColumnConstraints ccChoice = new ColumnConstraints(350, 350, 350, Priority.NEVER, HPos.CENTER, true);
                subGrid.getColumnConstraints().add(ccChoice);
                break;
            case Question.COMMENT_QTYPE_NA:
                // Do Nothing
                break;
        }

        TextArea commentPane = new TextArea();
        commentPane.setPromptText(q.getCommentPrompt());
        commentPane.textProperty().bindBidirectional(q.commentProperty());
        GridPane.setMargin(commentPane, new Insets(0, 10, 5, 10));
        commentPane.setMinHeight(20);
        //commentPane.setPrefRowCount(5);
        commentPane.setMaxHeight(100);

        RowConstraints rc1 = new RowConstraints(40, 40, 40, Priority.NEVER, VPos.BASELINE, true);
        RowConstraints rc2 = new RowConstraints(1, 120, MainController.MAX_HEIGHT, Priority.ALWAYS, VPos.TOP, true);
        gPane.getRowConstraints().addAll(rc1, rc2);

        gPane.add(subGrid, 0, 0);
        gPane.add(commentPane, 0, 1);
        GridPane.setVgrow(subGrid, Priority.ALWAYS);
        GridPane.setHgrow(subGrid, Priority.ALWAYS);
        GridPane.setHgrow(commentPane, Priority.ALWAYS);
        return gPane;
    }

    public static Node createCommentPage(ObservableList<Question> qList) {
        GridPane gPane = new GridPane();

        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            Node n = createCommentQ(qIdx + 1, qList.get(qIdx));
            gPane.add(n, 0, qIdx);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(1.0 / qList.size() * 100.0);
            gPane.getRowConstraints().add(rc);
            GridPane.setHgrow(n, Priority.ALWAYS);
        }

        BorderPane bPane = new BorderPane();
        bPane.setTop(NodeFactory.createFormattedLabel("Answer the following questions and enter any comments as necessary.", "opt-pane-title"));
        bPane.setCenter(gPane);
        return bPane;
    }

    public static Node createCommentPageYN(ObservableList<QuestionYN> qList) {
        GridPane gPane = new GridPane();

        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            Node n = createCommentQ(qIdx + 1, qList.get(qIdx));
            gPane.add(n, 0, qIdx);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(1.0 / qList.size() * 100.0);
            gPane.getRowConstraints().add(rc);
            GridPane.setHgrow(n, Priority.ALWAYS);
        }

        BorderPane bPane = new BorderPane();
        bPane.setTop(NodeFactory.createFormattedLabel("Answer the following yes/no questions and enter any comments as necessary.", "opt-pane-title"));
        bPane.setCenter(gPane);
        return bPane;
    }

    public static Node createMarkAllNode(QuestionOptionMS q) {
        int rowHeight = 40;
        GridPane gPane = new GridPane();
        gPane.getStyleClass().add("comment-q-pane");

        GridPane subGrid = new GridPane();
        subGrid.getStyleClass().add("comment-q-subpane");
        subGrid.add(NodeFactory.createFormattedLabel("1", "opt-pane-question-idx"), 0, 0);
        subGrid.add(NodeFactory.createFormattedLabel(q.getQuestionText(), "opt-pane-question"), 1, 0);
        ColumnConstraints cc1 = new ColumnConstraints(35, 35, 35, Priority.NEVER, HPos.CENTER, true);
        ColumnConstraints cc2 = new ColumnConstraints(1, 350, MainController.MAX_HEIGHT, Priority.ALWAYS, HPos.LEFT, true);
        subGrid.getColumnConstraints().addAll(cc1, cc2);

        GridPane.setHgrow(subGrid, Priority.ALWAYS);

        gPane.add(subGrid, 0, 0);
        gPane.getRowConstraints().add(new RowConstraints(rowHeight, rowHeight, rowHeight, Priority.NEVER, VPos.CENTER, true));

        int rowCount = 1;
        for (String option : q.getOptions()) {
            GridPane optSubGrid = new GridPane();
            optSubGrid.getStyleClass().add("comment-q-subpane");
            //optSubGrid.add(NodeFactory.createFormattedLabel(idx), 0, 0);
            CheckBox includedCheck = new CheckBox(option);
            includedCheck.getStyleClass().add("markall-pane-checkbox");
            includedCheck.selectedProperty().bindBidirectional(q.getOptionIncludedProperty(rowCount - 1));
            optSubGrid.add(includedCheck, 0, 0);
            GridPane.setMargin(includedCheck, new Insets(0, 0, 0, 25));
            //optSubGrid.add(NodeFactory.createFormattedLabel(option, "opt-pane-question"), 1, 0);
            //optSubGrid.add(new CheckBox(""),2,0);
            //optSubGrid.getColumnConstraints().add(new ColumnConstraints(35, 35, 35, Priority.NEVER, HPos.CENTER, true));
            //optSubGrid.getColumnConstraints().add(new ColumnConstraints(1, 350, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
            //optSubGrid.getRowConstraints().add(new RowConstraints(35,35,35,Priority.NEVER));

            gPane.add(optSubGrid, 0, rowCount++);
            gPane.getRowConstraints().add(new RowConstraints(rowHeight, rowHeight, rowHeight, Priority.NEVER, VPos.BASELINE, true));
            GridPane.setHgrow(optSubGrid, Priority.ALWAYS);

        }
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Enter additional comments here...");
        commentArea.textProperty().bindBidirectional(q.commentProperty());
        GridPane.setMargin(commentArea, new Insets(0, 10, 5, 10));
        commentArea.setMinHeight(20);
        gPane.add(commentArea, 0, rowCount);
        gPane.getRowConstraints().add(new RowConstraints(1, 350, MainController.MAX_HEIGHT, Priority.ALWAYS, VPos.CENTER, true));

        return gPane;
    }

    public static final ObservableList<QuestionYN> STEP_1_FEASIBILITY = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_FEASIBILITY, "Traffic speed variability"),
            new QuestionYN(2, Question.GOAL_FEASIBILITY, "Back of queue and other sight distance issues"),
            new QuestionYN(3, Question.GOAL_FEASIBILITY, "High speeds/chronic speeding"),
            new QuestionYN(4, Question.GOAL_FEASIBILITY, "Work zone congestion"),
            new QuestionYN(5, Question.GOAL_FEASIBILITY, "No alternate route availability"),
            new QuestionYN(6, Question.GOAL_FEASIBILITY, "Merging conflicts and hazards at work zone tapers"),
            new QuestionYN(7, Question.GOAL_FEASIBILITY, "Work zone hazards/complex traffic control layout"),
            new QuestionYN(8, Question.GOAL_FEASIBILITY, "Frequently changing operating conditions for traffic"),
            new QuestionYN(9, Question.GOAL_FEASIBILITY, "Variable work activities"),
            new QuestionYN(10, Question.GOAL_FEASIBILITY, "Oversize vehicles"),
            new QuestionYN(11, Question.GOAL_FEASIBILITY, "Construction vehilce entry/exit speed differential relative to traffic"),
            new QuestionYN(12, Question.GOAL_FEASIBILITY, "Data collection for work zone performance measures"),
            new QuestionYN(13, Question.GOAL_FEASIBILITY, "Unusual or unpredictable weather patterns")
    );

    public static final ObservableList<QuestionOption> STEP_1_FEASIBILITY_OPTIONS = FXCollections.observableArrayList(
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "What is the duration of the long-term stationary work?",
                    new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "To what extent will users be impacted for the duration of the work zone?",
                    new String[]{"Significant", "Moderate", "Minimal"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "How long are the queues expected to extend?",
                    new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "Less than 1 mile for less than 1 hour per day"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "During which time periods listed below are unreasonable traffic impacts expected to occur?",
                    new String[]{"More than morning and afternoon peak hours in both directions",
                        "During most of the morning and afternoon peaks hours in either direction",
                        "During most of a single peak hour in a single direction",
                        "Unpredictable"})
    );

    private static final ObservableList<QuestionYN> STEP_2_APP_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(2, Question.GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"),
            new QuestionYN(3, Question.GOAL_MOBILITY, "Will this work zone be active during the day?"),
            new QuestionYN(4, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"),
            new QuestionYN(5, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"),
            new QuestionYN(6, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"),
            new QuestionYN(7, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"),
            new QuestionYN(8, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"),
            new QuestionYN(9, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(10, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"),
            new QuestionYN(11, Question.GOAL_SAFETY, "Do you expect congestion impacts to be difficult to realized by drivers?"),
            new QuestionYN(12, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"),
            new QuestionYN(13, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"),
            new QuestionYN(14, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(15, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"),
            new QuestionYN(16, Question.GOAL_PROD, "Will vehicles access site from travel lanes?"),
            new QuestionYN(17, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"),
            new QuestionYN(18, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"),
            new QuestionYN(19, Question.GOAL_PROD, "Will existing equipment be used for the WZ?"),
            new QuestionYN(20, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"),
            new QuestionYN(21, Question.GOAL_REG, "Is automated enforcement legal in your state?"),
            new QuestionYN(22, Question.GOAL_REG, "Are there specific agency policies for work zones?"),
            new QuestionYN(23, Question.GOAL_REG, "Does the agency have existing performance targets for work zone?"),
            new QuestionYN(24, Question.GOAL_REG, "Is there a mobility goal?"),
            new QuestionYN(25, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"),
            new QuestionYN(26, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?")
    );

    private static final ObservableList<QuestionYN> STEP_3_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_4_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_5_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_6_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    public static Color COLOR_HIDDEN = Color.LIGHTGRAY;

    public static class Options {

        public String tableStyleCSS;
        public String qColumnHeader = "Input Question";
        public boolean autoIndex = true;
        public boolean showFeasibilityScore = false;
        public boolean showAppWizardGoalCategory = false;
        public boolean showRedundantQIdx = false;

        public Options(String tableStyleCSS) {
            this.tableStyleCSS = tableStyleCSS;
        }
    }
}
