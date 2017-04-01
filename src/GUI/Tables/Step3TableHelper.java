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
public class Step3TableHelper extends TableView {

    private static final int STEP_INDEX = 2;

    private static final String STEP3_TABLE_CSS = "step-one-table";

    public static TableView createConOpsNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qConOpsList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createSysReqNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qSysReqList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createTestingStratNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qTestingStratList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createOpsMaintNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qOpsMaintList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createStaffTrainingNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qStaffTrainingList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createSysSecurityNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qSysSecurityList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createProjEvalNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qProjectEvalList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

    public static TableView createSysBCNode(Project proj) {
        return TableHelper.createQuestionYNTable(proj.getQGen().qSysBCList, new TableHelper.Options(STEP3_TABLE_CSS));
    }

}
