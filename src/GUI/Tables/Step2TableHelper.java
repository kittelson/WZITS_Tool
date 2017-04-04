/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Project;
import core.QuestionYN;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TableView;

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

}
