/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Project;
import javafx.scene.control.TableView;

/**
 *
 * @author ltrask
 */
public class Step2TableHelper extends TableView {

    private static final int STEP_INDEX = 1;

    public static final int APP_WIZARD = 0;

    private static final String STEP2_TABLE_CSS = "step-one-table";

    public static TableView createApplicationWizard(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getAppWizardQs(), new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createBenefitsNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qBenefitList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createCostsNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qCostList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createInstJurNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qJurisdictionalList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createLegalNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qLegalList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createStakeholderBuyInNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qStakeholderBuyInList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createConOpsNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qConOpsList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

}
