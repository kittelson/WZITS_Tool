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
public class Step6TableHelper extends TableView {

    private static final int STEP_INDEX = 1;

    public static final int APP_WIZARD = 0;

    private static final String STEP2_TABLE_CSS = "step-one-table";

    public static TableView createChangingConditionsNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qChangingConditionsList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createSharingInfoNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qSharingInfoList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createStaffingNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qStaffingList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createPublicSupportNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qPublicSupportList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

    public static TableView createMonitoringEvalNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qMonitoringEvalList, new TableHelper.Options(STEP2_TABLE_CSS));
    }

}
