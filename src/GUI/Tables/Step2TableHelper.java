/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import core.Application;
import core.ApplicationMatrix;
import core.Project;
import core.QuestionOptionMS;
import core.QuestionYN;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author ltrask
 */
public class Step2TableHelper extends TableView {

    private static final int STEP_INDEX = 1;

    public static final int APP_WIZARD = 0;

    private static final String STEP2_TABLE_CSS = "step-one-table";

    public static TableView createApplicationWizard1(Project proj) {
        ObservableList<QuestionYN> fullAppList = proj.getAppWizardQs();
        FilteredList<QuestionYN> fl = fullAppList.filtered(new Predicate<QuestionYN>() {
            @Override
            public boolean test(QuestionYN q) {
                return !q.isRedundant();
            }
        });
        TableHelper.Options tOpts = new TableHelper.Options(STEP2_TABLE_CSS);
        tOpts.showAppWizardGoalCategory = true;
        return TableHelper.createQuestionYNTable(fl, tOpts);
    }

    public static TableView createApplicationWizard2(Project proj) {
        ObservableList<QuestionYN> fullAppList = proj.getAppWizardQs();
        FilteredList<QuestionYN> fl = fullAppList.filtered(new Predicate<QuestionYN>() {
            @Override
            public boolean test(QuestionYN q) {
                return q.isRedundant();
            }
        });
        TableHelper.Options tOpts = new TableHelper.Options(STEP2_TABLE_CSS);
        tOpts.showAppWizardGoalCategory = true;
        tOpts.showRedundantQIdx = true;
        tOpts.qColumnHeader = "Wizard Questions Answered in Previous Steps";
        return TableHelper.createQuestionYNTable(fl, tOpts);
    }

    public static Node createBenefitsNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qBenefitList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createMarkAllNode(proj.getQGen().qBenefitList.get(0));
    }

    public static Node createCostsNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qCostList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qCostList);
    }

    public static Node createInstJurNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qJurisdictionalList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qJurisdictionalList);
    }

    public static Node createLegalNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qLegalList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qLegalList);
    }

    public static Node createStakeholderBuyInNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qStakeholderBuyInList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qStakeholderBuyInList);
    }

    public static Node createConOpsNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qConOpsList, new TableHelper.Options(STEP2_TABLE_CSS));
        return TableHelper.createCommentPageYN(proj.getQGen().qConOpsList);
    }

    public static Node createStepSummary(MainController mc) {

        Tab fs1 = new Tab();
        fs1.setText("Fact Sheet 3");
        fs1.setContent(createStepSummary1(mc));
        Tab fs2 = new Tab();
        fs2.setText("Fact Sheet 4");
        fs2.setContent(createStepSummary2(mc));
        final TabPane summaryTabPane = new TabPane();
        summaryTabPane.getStyleClass().add("custom-subtitle-tab-pane");
        summaryTabPane.getTabs().addAll(fs1, fs2);
        summaryTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return summaryTabPane;
    }

    public static Node createStepSummary1(MainController mc) {
        int lfs = 16;
        final Project p = mc.getProject();
        BorderPane bPane = new BorderPane();
        bPane.setMinWidth(1000);
        bPane.setMaxWidth(1000);
        bPane.setPrefWidth(1000);
        bPane.getStyleClass().add("fact-sheet-pane");
        bPane.setTop(NodeFactory.createFormattedLabel("Fact Sheet #3: WZITS Applications and Refinement", "fact-sheet-title-large"));
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
                if (p.getUrlLink() != null) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start " + p.getUrlLink());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        projHL.setMaxWidth(MainController.MAX_WIDTH);
        projHL.setMaxHeight(MainController.MAX_HEIGHT);
        infoGrid.add(projHL, 1, rowIdx++);

        ColumnConstraints igcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints igcc2 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        infoGrid.getColumnConstraints().addAll(igcc1, igcc2);

        GridPane aGrid = new GridPane();
        aGrid.add(NodeFactory.createFormattedLabel("WZITS Selected Applications", "fact-sheet-title-stake-grid"), 0, 0, 4, 1);
        rowIdx = 1;
        aGrid.add(NodeFactory.createFormattedLabel("#", "fact-sheet-title-core-team"), 0, rowIdx, 1, 1);
        aGrid.add(NodeFactory.createFormattedLabel("Application", "fact-sheet-title-core-team"), 1, rowIdx, 1, 1);
        aGrid.add(NodeFactory.createFormattedLabel(ApplicationMatrix.HIGH_CAT_LABEL, "fact-sheet-title-core-team"), 2, rowIdx, 1, 1);
        aGrid.add(NodeFactory.createFormattedLabel("Selected", "fact-sheet-title-core-team"), 3, rowIdx++, 1, 1);

        for (Application app : p.getApplicationMatrix().getAppList()) {
            aGrid.add(NodeFactory.createFormattedLabel(String.valueOf(rowIdx - 1), "fact-sheet-label-center"), 0, rowIdx, 1, 1);
            aGrid.add(NodeFactory.createFormattedLabel(app.getName(), "fact-sheet-label"), 1, rowIdx, 1, 1);
            aGrid.add(NodeFactory.createFormattedLabel(Application.getScoreString(app.getScore()), "fact-sheet-label-center"), 2, rowIdx, 1, 1);
            aGrid.add(NodeFactory.createFormattedLabel(app.isSelected() ? "Yes" : "No", "fact-sheet-label-center"), 3, rowIdx++, 1, 1);
        }

        aGrid.getColumnConstraints().add(new ColumnConstraints(35, 35, 35, Priority.NEVER, HPos.CENTER, true));
        aGrid.getColumnConstraints().add(new ColumnConstraints(1, 200, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));
        aGrid.getColumnConstraints().add(new ColumnConstraints(ApplicationMatrix.SCORE_COL_WIDTH, ApplicationMatrix.SCORE_COL_WIDTH, ApplicationMatrix.SCORE_COL_WIDTH, Priority.NEVER, HPos.CENTER, true));
        aGrid.getColumnConstraints().add(new ColumnConstraints(100, 100, 100, Priority.NEVER, HPos.CENTER, true));

        GridPane arGrid = new GridPane();
        arGrid.add(NodeFactory.createFormattedLabel("Application Refinement", "fact-sheet-title-stake-grid"), 0, 0, 4, 1);
        rowIdx = 1;
        arGrid.add(NodeFactory.createFormattedLabel("Category", "fact-sheet-title-core-team"), 0, rowIdx, 1, 1);
        arGrid.add(NodeFactory.createFormattedLabel("Application Refinement", "fact-sheet-title-core-team"), 1, rowIdx, 1, 1);
        arGrid.add(NodeFactory.createFormattedLabel("Response", "fact-sheet-title-core-team"), 2, rowIdx, 1, 1);
        arGrid.add(NodeFactory.createFormattedLabel("Comment", "fact-sheet-title-core-team"), 3, rowIdx++, 1, 1);
        QuestionOptionMS beneQ = p.getQGen().qBenefitList.get(0);

        arGrid.add(NodeFactory.createFormattedLabel("Benefits", "fact-sheet-label-center"), 0, rowIdx, 1, beneQ.getOptions().length);
        arGrid.add(NodeFactory.createFormattedLabel(beneQ.getComment(), "fact-sheet-label"), 3, rowIdx, 1, beneQ.getOptions().length);
        for (int bIdx = 0; bIdx < beneQ.getOptions().length; bIdx++) {
            arGrid.add(NodeFactory.createFormattedLabel(beneQ.getOption(bIdx), "fact-sheet-label"), 1, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(beneQ.getOptionIncluded(bIdx) ? "Yes" : "No", "fact-sheet-label-center"), 2, rowIdx++);
        }

//        ObservableList<QuestionYN> qList = p.getQGen().qCostList;
//        arGrid.add(NodeFactory.createFormattedLabel("Institutional/ Jurisdictional", "fact-sheet-label-center"), 0, rowIdx, 1, qList.size());
//        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
//            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getQuestionText(), "fact-sheet-label"), 1, rowIdx);
//            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getAnswerString(), "fact-sheet-label-center"), 2, rowIdx);
//            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getComment(), "fact-sheet-label"), 3, rowIdx++);
//        }
        ObservableList<QuestionYN> qList = p.getQGen().qJurisdictionalList;
        arGrid.add(NodeFactory.createFormattedLabel("Institutional/ Jurisdictional", "fact-sheet-label-center"), 0, rowIdx, 1, qList.size());
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getQuestionText(), "fact-sheet-label"), 1, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getAnswerString(), "fact-sheet-label-center"), 2, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getComment(), "fact-sheet-label"), 3, rowIdx++);
        }

        qList = p.getQGen().qLegalList;
        arGrid.add(NodeFactory.createFormattedLabel("Legal/ Policy", "fact-sheet-label-center"), 0, rowIdx, 1, qList.size());
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getQuestionText(), "fact-sheet-label"), 1, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getAnswerString(), "fact-sheet-label-center"), 2, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getComment(), "fact-sheet-label"), 3, rowIdx++);
        }

        qList = p.getQGen().qStakeholderBuyInList;
        arGrid.add(NodeFactory.createFormattedLabel("Stakeholder Buy-in", "fact-sheet-label-center"), 0, rowIdx, 1, qList.size());
        for (int qIdx = 0; qIdx < qList.size(); qIdx++) {
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getQuestionText(), "fact-sheet-label"), 1, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getAnswerString(), "fact-sheet-label-center"), 2, rowIdx);
            arGrid.add(NodeFactory.createFormattedLabel(qList.get(qIdx).getComment(), "fact-sheet-label"), 3, rowIdx++);
        }

        arGrid.getColumnConstraints().add(new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.CENTER, true));
        arGrid.getColumnConstraints().add(new ColumnConstraints(300, 300, 300, Priority.NEVER, HPos.CENTER, true));
        arGrid.getColumnConstraints().add(new ColumnConstraints(100, 100, 100, Priority.NEVER, HPos.CENTER, true));
        arGrid.getColumnConstraints().add(new ColumnConstraints(1, 200, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true));

        VBox factSheetVBox = new VBox();
        factSheetVBox.getChildren().addAll(infoGrid, aGrid, arGrid);
        ScrollPane sp = new ScrollPane();

        bPane.setCenter(factSheetVBox);
        sp.setContent(bPane);
        //System.out.println("Here");
        return sp;
    }

    public static Node createStepSummary2(MainController mc) {
        int lfs = 16;
        final Project p = mc.getProject();
        BorderPane bPane = new BorderPane();
        bPane.setMinWidth(1000);
        bPane.setMaxWidth(1000);
        bPane.setPrefWidth(1000);
        bPane.getStyleClass().add("fact-sheet-pane");
        bPane.setTop(NodeFactory.createFormattedLabel("Fact Sheet #4: WZITS Concept of Operations", "fact-sheet-title-large"));
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
                if (p.getUrlLink() != null) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start " + p.getUrlLink());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        projHL.setMaxWidth(MainController.MAX_WIDTH);
        projHL.setMaxHeight(MainController.MAX_HEIGHT);
        infoGrid.add(projHL, 1, rowIdx++);

        ColumnConstraints igcc1 = new ColumnConstraints(infoC1Width, infoC1Width, infoC1Width, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints igcc2 = new ColumnConstraints(1, 100, MainController.MAX_WIDTH, Priority.ALWAYS, HPos.LEFT, true);
        infoGrid.getColumnConstraints().addAll(igcc1, igcc2);

        ImageView conOpsImage = new ImageView(p.getConOpsDiagram() != null ? p.getConOpsDiagram() : null);
        conOpsImage.setPreserveRatio(true);

        conOpsImage.getStyleClass().add("fact-sheet-pane");

        conOpsImage.setFitHeight(1000);
        conOpsImage.setFitWidth(1000);
        //BorderPane projImageBorder = new BorderPane();
        //projImageBorder.getChildren().addAll(projImage);
        //projImageBorder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
        //projImageBorder.maxHeightProperty().bind(projImage.fitHeightProperty());
        //projImageBorder.maxWidthProperty().bind(projImage.fitWidthProperty());

        VBox factSheetVBox = new VBox();
        factSheetVBox.getChildren().addAll(infoGrid, conOpsImage);
        factSheetVBox.setAlignment(Pos.CENTER);
        ScrollPane sp = new ScrollPane();

        bPane.setCenter(factSheetVBox);
        sp.setContent(bPane);
        //System.out.println("Here");
        return sp;
    }

}
