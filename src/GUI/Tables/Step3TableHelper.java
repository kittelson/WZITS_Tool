/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Project;
import javafx.scene.Node;
import javafx.scene.control.TableView;

/**
 *
 * @author ltrask
 */
public class Step3TableHelper extends TableView {

    private static final int STEP_INDEX = 2;

    private static final String STEP3_TABLE_CSS = "step-one-table";

    public static Node createConOpsNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qConOpsList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qConOpsList);
    }

    public static Node createSysReqNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysReqList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qSysReqList);
    }

    public static Node createTestingStratNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qTestingStratList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qTestingStratList);
    }

    public static Node createOpsMaintNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qOpsMaintList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qOpsMaintList);
    }

    public static Node createStaffTrainingNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qStaffTrainingList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qStaffTrainingList);
    }

    public static Node createSysSecurityNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysSecurityList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qSysSecurityList);
    }

    public static Node createProjEvalNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qProjectEvalList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qProjectEvalList);
    }

    public static Node createSysBCNode(Project proj) {
        //return TableHelper.createQuestionYNTable(proj.getQGen().qSysBCList, new TableHelper.Options(STEP3_TABLE_CSS));
        return TableHelper.createCommentPage(proj.getQGen().qSysBCList);
    }

}
