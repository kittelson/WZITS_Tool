/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ltrask
 */
public class QuestionGenerator implements Serializable {

    private static final long serialVersionUID = 123456789L;

    /**
     * List of yes/no questions for the goal wizard.
     */
    public ObservableList<QuestionYN> qGoalWizardList;

    /**
     * List of major goals questions.
     */
    public ObservableList<QuestionYN> qMajorGoalsList;

    /**
     * List of user needs support questions.
     */
    public ObservableList<QuestionYN> qUNSupportList;
    /**
     * list of option questions for the Feasibility Wizard.
     */
    public ObservableList<QuestionOption> qFeasOptionList;

    /**
     * List of yes/no questions for the Feasibility Wizard.
     */
    public ObservableList<QuestionYN> qFeasYNList;

    /**
     * List of yes/no questions for the stakeholder wizard.
     */
    public ObservableList<QuestionYN> qStakeholderYNList;

    /**
     * List of option questions for the stakeholder wizard.
     */
    public ObservableList<QuestionOption> qStakeholderOptionList;

    /**
     * List of yes/no questions for the ITS resources step.
     */
    public ObservableList<QuestionYN> qITSResourcesList;

    /**
     *
     */
    public ObservableList<QuestionYN> qApplicationList;

    /**
     *
     */
    public ObservableList<QuestionOptionMS> qBenefitList;

    /**
     *
     */
    public ObservableList<Question> qCostList;

    /**
     *
     */
    public ObservableList<QuestionYN> qJurisdictionalList;

    /**
     *
     */
    public ObservableList<QuestionYN> qLegalList;

    /**
     *
     */
    public ObservableList<QuestionYN> qStakeholderBuyInList;

    /**
     *
     */
    public ObservableList<QuestionYN> qConOpsList;

    /**
     *
     */
    public ObservableList<QuestionYN> qSysReqList;

    /**
     *
     */
    public ObservableList<QuestionYN> qTestingStratList;
    /**
     *
     */
    public ObservableList<QuestionYN> qOpsMaintList;
    /**
     *
     */
    public ObservableList<QuestionYN> qStaffTrainingList;
    /**
     *
     */
    public ObservableList<QuestionYN> qSysSecurityList;
    /**
     *
     */
    public ObservableList<QuestionYN> qProjectEvalList;
    /**
     *
     */
    public ObservableList<QuestionYN> qSysBCList;
    /**
     *
     */
    public ObservableList<QuestionYN> qDirectIndirectList;
    /**
     *
     */
    public ObservableList<QuestionYN> qMechanismList;
    /**
     *
     */
    public ObservableList<QuestionYN> qRFPList;
    /**
     *
     */
    public ObservableList<QuestionYN> qVendorSelectionList;
    /**
     *
     */
    public ObservableList<QuestionYN> qSysPlansList;
    /**
     *
     */
    public ObservableList<QuestionYN> qSchedulingList;
    /**
     *
     */
    public ObservableList<QuestionYN> qAcceptanceTrainingList;
    /**
     *
     */
    public ObservableList<QuestionYN> qDeploymentIssuesList;
    /**
     *
     */
    public ObservableList<QuestionYN> qChangingConditionsList;
    /**
     *
     */
    public ObservableList<QuestionYN> qSharingInfoList;
    /**
     *
     */
    public ObservableList<QuestionYN> qStaffingList;
    /**
     *
     */
    public ObservableList<QuestionYN> qPublicSupportList;
    /**
     *
     */
    public ObservableList<QuestionYN> qMonitoringEvalList;

    /**
     * WZITS Project associated with the Question Generator
     */
    private final Project proj;

    /**
     *
     * @param project
     */
    public QuestionGenerator(Project project) {
        this.proj = project;

        qGoalWizardList = FXCollections.observableArrayList();
        qUNSupportList = FXCollections.observableArrayList();
        qMajorGoalsList = FXCollections.observableArrayList();
        qFeasOptionList = FXCollections.observableArrayList();
        qFeasYNList = FXCollections.observableArrayList();
        qStakeholderYNList = FXCollections.observableArrayList();
        qStakeholderOptionList = FXCollections.observableArrayList();
        qITSResourcesList = FXCollections.observableArrayList();
        qApplicationList = FXCollections.observableArrayList();
        qBenefitList = FXCollections.observableArrayList();
        qCostList = FXCollections.observableArrayList();
        qJurisdictionalList = FXCollections.observableArrayList();
        qLegalList = FXCollections.observableArrayList();
        this.qStakeholderBuyInList = FXCollections.observableArrayList();
        this.qConOpsList = FXCollections.observableArrayList();
        this.qSysReqList = FXCollections.observableArrayList();
        this.qTestingStratList = FXCollections.observableArrayList();
        this.qOpsMaintList = FXCollections.observableArrayList();
        this.qStaffTrainingList = FXCollections.observableArrayList();
        this.qSysSecurityList = FXCollections.observableArrayList();
        this.qProjectEvalList = FXCollections.observableArrayList();
        this.qSysBCList = FXCollections.observableArrayList();
        this.qDirectIndirectList = FXCollections.observableArrayList();
        this.qMechanismList = FXCollections.observableArrayList();
        this.qRFPList = FXCollections.observableArrayList();
        this.qVendorSelectionList = FXCollections.observableArrayList();
        this.qSysPlansList = FXCollections.observableArrayList();
        this.qSchedulingList = FXCollections.observableArrayList();
        this.qAcceptanceTrainingList = FXCollections.observableArrayList();
        this.qDeploymentIssuesList = FXCollections.observableArrayList();
        this.qChangingConditionsList = FXCollections.observableArrayList();
        this.qSharingInfoList = FXCollections.observableArrayList();
        this.qStaffingList = FXCollections.observableArrayList();
        this.qPublicSupportList = FXCollections.observableArrayList();
        this.qMonitoringEvalList = FXCollections.observableArrayList();

        initializeQuestions();
        connectToProgressIndicators();

    }

    public QuestionGenerator(QuestionGenerator qGen, Project project) {
        this.proj = project;

        qGoalWizardList = qGen.qGoalWizardList;
        qUNSupportList = qGen.qUNSupportList;
        qMajorGoalsList = qGen.qMajorGoalsList;
        qFeasOptionList = qGen.qFeasOptionList;
        qFeasYNList = qGen.qFeasYNList;
        qStakeholderYNList = qGen.qStakeholderYNList;
        qStakeholderOptionList = qGen.qStakeholderOptionList;
        qITSResourcesList = qGen.qITSResourcesList;
        qApplicationList = qGen.qApplicationList;
        qBenefitList = qGen.qBenefitList;
        qCostList = qGen.qCostList;
        qJurisdictionalList = qGen.qJurisdictionalList;
        qLegalList = qGen.qLegalList;
        this.qStakeholderBuyInList = qGen.qStakeholderBuyInList;
        this.qConOpsList = qGen.qConOpsList;
        this.qSysReqList = qGen.qSysReqList;
        this.qTestingStratList = qGen.qTestingStratList;
        this.qOpsMaintList = qGen.qOpsMaintList;
        this.qStaffTrainingList = qGen.qStaffTrainingList;
        this.qSysSecurityList = qGen.qSysSecurityList;
        this.qProjectEvalList = qGen.qProjectEvalList;
        this.qSysBCList = qGen.qSysBCList;
        this.qDirectIndirectList = qGen.qDirectIndirectList;
        this.qMechanismList = qGen.qMechanismList;
        this.qRFPList = qGen.qRFPList;
        this.qVendorSelectionList = qGen.qVendorSelectionList;
        this.qSysPlansList = qGen.qSysPlansList;
        this.qSchedulingList = qGen.qSchedulingList;
        this.qAcceptanceTrainingList = qGen.qAcceptanceTrainingList;
        this.qDeploymentIssuesList = qGen.qDeploymentIssuesList;
        this.qChangingConditionsList = qGen.qChangingConditionsList;
        this.qSharingInfoList = qGen.qSharingInfoList;
        this.qStaffingList = qGen.qStaffingList;
        this.qPublicSupportList = qGen.qPublicSupportList;
        this.qMonitoringEvalList = qGen.qMonitoringEvalList;

        createQuestionBindings();
        connectToProgressIndicators();

    }

    private void initializeQuestions() {
//        createGoalWizardQuestions();
        createGoalWizardQuestionsV2();
        createUserNeedsSupportQuestions();
        createMajorGoalsQuestions();
//        createFeasWizardQuestions();
        createFeasWizardQuestionsV2();
//        createStakeholderWizardQuestions();
        createStakeholderWizardQuestionsV2();
        createITSResourcesQuestions();
//        createApplicationWizardQuestions();
        createApplicationWizardQuestionsV2();
        createProjectDocumentationQuesitons();
    }

    private void createFeasWizardQuestionsV2() {
        JSONObject json = FeasibilityMatrix.loadJSON();
        JSONArray jArr = (JSONArray) json.get("Feasibility Matrix");
        int qIdx = 0;
        JSONObject q = (JSONObject) jArr.get(qIdx++);
        JSONArray workDurationAnswers = (JSONArray) q.get("Answers");
        String[] workDurationAnswerLabels = new String[4];
        workDurationAnswerLabels[0] = ((JSONObject) workDurationAnswers.get(0)).get("Label").toString();
        workDurationAnswerLabels[1] = ((JSONObject) workDurationAnswers.get(1)).get("Label").toString();
        workDurationAnswerLabels[2] = ((JSONObject) workDurationAnswers.get(2)).get("Label").toString();
        workDurationAnswerLabels[3] = "N/A";
        int[] workDurationAnswerScores = new int[4];
        workDurationAnswerScores[0] = Integer.parseInt(((JSONObject) workDurationAnswers.get(0)).getOrDefault("Score", "0").toString());
        workDurationAnswerScores[1] = Integer.parseInt(((JSONObject) workDurationAnswers.get(1)).getOrDefault("Score", "0").toString());
        workDurationAnswerScores[2] = Integer.parseInt(((JSONObject) workDurationAnswers.get(2)).getOrDefault("Score", "0").toString());
        workDurationAnswerScores[3] = 0;
        final QuestionOption workDurationQ = new QuestionOption(qIdx, Question.GOAL_FEASIBILITY,
                q.get("Question").toString(), // "What is the duration of long-term stationary work?"
                workDurationAnswerLabels, //new String[]{"> 1 Construction Season", "4-10 Months", "< 4 Months", "N/A"},
                workDurationAnswerScores //new int[]{8, 5, 1, 0});
        );
        workDurationQ.setRefText("MUTCD Designation");

        q = (JSONObject) jArr.get(qIdx++);
        JSONArray impactExtentAnswers = (JSONArray) q.get("Answers");
        String[] impactExtentAnswerLabels = new String[3];
        impactExtentAnswerLabels[0] = ((JSONObject) impactExtentAnswers.get(0)).get("Label").toString();
        impactExtentAnswerLabels[1] = ((JSONObject) impactExtentAnswers.get(1)).get("Label").toString();
        impactExtentAnswerLabels[2] = ((JSONObject) impactExtentAnswers.get(2)).get("Label").toString();
        int[] impactExtentAnswerScores = new int[3];
        impactExtentAnswerScores[0] = Integer.parseInt(((JSONObject) impactExtentAnswers.get(0)).getOrDefault("Score", "0").toString());
        impactExtentAnswerScores[1] = Integer.parseInt(((JSONObject) impactExtentAnswers.get(1)).getOrDefault("Score", "0").toString());
        impactExtentAnswerScores[2] = Integer.parseInt(((JSONObject) impactExtentAnswers.get(2)).getOrDefault("Score", "0").toString());
        final QuestionOption impactExtentQ = new QuestionOption(qIdx, Question.GOAL_FEASIBILITY,
                q.get("Question").toString(), // "To what extent will users be impacted for the duration of the work zone?"
                impactExtentAnswerLabels, //new String[]{"Significant", "Moderate", "Minimal"},
                impactExtentAnswerScores //new int[]{8, 5, 1}
        );

        q = (JSONObject) jArr.get(qIdx++);
        JSONArray queueExpectationAnswers = (JSONArray) q.get("Answers");
        String[] queueExpectationAnswerLabels = new String[3];
        queueExpectationAnswerLabels[0] = ((JSONObject) queueExpectationAnswers.get(0)).get("Label").toString();
        queueExpectationAnswerLabels[1] = ((JSONObject) queueExpectationAnswers.get(1)).get("Label").toString();
        queueExpectationAnswerLabels[2] = ((JSONObject) queueExpectationAnswers.get(2)).get("Label").toString();
        int[] queueExpectationAnswerScores = new int[3];
        queueExpectationAnswerScores[0] = Integer.parseInt(((JSONObject) queueExpectationAnswers.get(0)).getOrDefault("Score", "0").toString());
        queueExpectationAnswerScores[1] = Integer.parseInt(((JSONObject) queueExpectationAnswers.get(1)).getOrDefault("Score", "0").toString());
        queueExpectationAnswerScores[2] = Integer.parseInt(((JSONObject) queueExpectationAnswers.get(2)).getOrDefault("Score", "0").toString());
        final QuestionOption queueExpectationQ = new QuestionOption(qIdx, Question.GOAL_FEASIBILITY,
                q.get("Question").toString(),  // "How long are queues expected to extend?"
                queueExpectationAnswerLabels, //new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "<1 mile for <1 hour per day"},
                queueExpectationAnswerScores //new int[]{8, 5, 1}
        );
        bindDependantQs(queueExpectationQ, this.significantQueueingQ, "User Needs #3");

        q = (JSONObject) jArr.get(qIdx++);
        JSONArray noticeableTimePeriodAnswers = (JSONArray) q.get("Answers");
        String[] noticeableTimePeriodAnswerLabels = new String[4];
        noticeableTimePeriodAnswerLabels[0] = ((JSONObject) noticeableTimePeriodAnswers.get(0)).get("Label").toString();
        noticeableTimePeriodAnswerLabels[1] = ((JSONObject) noticeableTimePeriodAnswers.get(1)).get("Label").toString();
        noticeableTimePeriodAnswerLabels[2] = ((JSONObject) noticeableTimePeriodAnswers.get(2)).get("Label").toString();
        noticeableTimePeriodAnswerLabels[3] = ((JSONObject) noticeableTimePeriodAnswers.get(3)).get("Label").toString();
        int[] noticeableTimePeriodAnswerScores = new int[4];
        noticeableTimePeriodAnswerScores[0] = Integer.parseInt(((JSONObject) noticeableTimePeriodAnswers.get(0)).getOrDefault("Score", "0").toString());
        noticeableTimePeriodAnswerScores[1] = Integer.parseInt(((JSONObject) noticeableTimePeriodAnswers.get(1)).getOrDefault("Score", "0").toString());
        noticeableTimePeriodAnswerScores[2] = Integer.parseInt(((JSONObject) noticeableTimePeriodAnswers.get(2)).getOrDefault("Score", "0").toString());
        noticeableTimePeriodAnswerScores[3] = Integer.parseInt(((JSONObject) noticeableTimePeriodAnswers.get(3)).getOrDefault("Score", "0").toString());
        final QuestionOption noticeableTimePeriodQ = new QuestionOption(qIdx, Question.GOAL_FEASIBILITY,
                q.get("Question").toString(),  // "During which time periods are noticeable traffic impacts expected to occur?"
                noticeableTimePeriodAnswerLabels, //new String[]{"More than morning and afternoon peak hours in both directions", "During most of the morning or afternoon peaks hours in either direction", "Single peak hour or less in a single direction"},
                noticeableTimePeriodAnswerScores //new int[]{8, 5, 1, 0}
        );
        bindDependantQs(noticeableTimePeriodQ, this.congestionNoticableQ, "User Needs #1");

        qFeasOptionList.addAll(workDurationQ, impactExtentQ, noticeableTimePeriodQ, queueExpectationQ);

        // List of yes no questions
        q = (JSONObject) jArr.get(qIdx);
        JSONArray ynFeasQuestionsArr = (JSONArray) q.get("Answers");
        qIdx = 0;
        JSONObject answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(), // "Is traffic speed variability expected to occur?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Do you expect back of queue and other sight distance issues?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString()))); // Dependant
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.significantQueueingQ, "User Needs #3");
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Are high speeds/chronic speeding expected to occur?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString() + " (See User Needs #2)",  // "Is driver diversion expected onto alternate routes? (See User Needs #2)"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        bindRedundantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.driverDiversionQ, "User Needs #2");
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Are merging conflicts and hazards at work zone tapers expected to occur?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Do you expect the work zone layout to cause driver confusion or trouble wayfinding?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Will frequently changing operating conditions for traffic be used?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Will variable work activities occur?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Are oversize vehicles expected?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Do you expect a construction vehicle entry/exit speed differential relative to traffic?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.highVolumeConstructionVehsQ, "User Needs #7");
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Will data be collected for work zone performance measures?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
        answerOpt = (JSONObject) ynFeasQuestionsArr.get(qIdx++);
        qFeasYNList.add(new QuestionYN(qIdx, Question.GOAL_FEASIBILITY,
                answerOpt.get("Label").toString(),  // "Do you expect any unusual or unpredictable weather patterns to occur?"
                false,
                Integer.parseInt(answerOpt.getOrDefault("Score", "0").toString())));
    }

    private void createFeasWizardQuestions() {
        int qIdx = 1;
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "What is the duration of long-term stationary work?",
                new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months", "N/A"},
                new int[]{8, 5, 1, 0}));
        qFeasOptionList.get(qFeasOptionList.size() - 1).setRefText("MUTCD Designation");
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "To what extent will users be impacted for the duration of the work zone?",
                new String[]{"Significant", "Moderate", "Minimal"},
                new int[]{8, 5, 1}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "During which time periods are noticable traffic impacts expected to occur?",
                new String[]{"More than morning and afternoon peak hours in both directions",
                    "During most of the morning or afternoon peaks hours in either direction",
                    "Single peak hour or less in a single direction"},
                new int[]{8, 5, 1, 0}));
        bindDependantQs(qFeasOptionList.get(qFeasOptionList.size() - 1), this.congestionNoticableQ, "User Needs #1");
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "How long are queues expected to extend?",
                new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "<1 mile for <1 hour per day"},
                new int[]{8, 5, 1}));
        bindDependantQs(qFeasOptionList.get(qFeasOptionList.size() - 1), this.significantQueueingQ, "User Needs #3");
        qIdx = 1;
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is traffic speed variability expected to occur?", false, 1));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect back of queue and other sight distance issues?", false, 3)); // Dependant
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.significantQueueingQ, "User Needs #3");
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are high speeds/chronic speeding expected to occur?", false, 2));
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Work zone congestion", false, 1)); // Consolidated
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is driver diversion expected onto alternate routes? (See User Needs #2)", false, 1));
        bindRedundantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.driverDiversionQ, "User Needs #2");
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are merging conflicts and hazards at work zone tapers expected to occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect the work zone layout to cause driver confusion or trouble wayfinding?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will frequently changing operating conditions for traffic be used?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will variable work activities occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are oversize vehicles expected?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect a construction vehicle entry/exit speed differential relative to traffic?", false, 2));
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.highVolumeConstructionVehsQ, "User Needs #7");
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will data be collected for work zone performance measures?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect any unusual or unpredictable weather patterns to occur?", false, 3));
        // Just in stakeholder wizard as not scored in excel feasibility wizard
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is the work zone located on an emergency response corridor?", false, 0));
        // Moved to application wizard as not scored in excel feasibility wizard
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will existing equipment be used for the work zone?", false, 0));
        // Just in goal wizard as not scored in excel feasibility wizard
        ///qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are there specific agency policies for work zones?", false, 0));
        // Moved to application wizard as not scored in excel feasibility wizard
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are there access points with vertical or horizontal sight distance restrictions?", false, 0));
        //verticalHorizontalSightDistanceQ = qFeasYNList.get(qFeasYNList.size() - 1);
    }

    private void createGoalWizardQuestionsV2() {
        JSONObject json = GoalNeedsMatrix.loadJSON();
        JSONArray jArr = (JSONArray) json.get("Goals Matrix");
        int qIdx = 0;
        JSONObject q = (JSONObject) jArr.get(qIdx++);
        this.congestionNoticableQ = new QuestionYN(1, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.driverDiversionQ = new QuestionYN(2, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.significantQueueingQ = new QuestionYN(3, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.reducedLaneWidthQ = new QuestionYN(4, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.reducedSightDistanceQ = new QuestionYN(5, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        QuestionYN transitVehicleQ = new QuestionYN(6, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.highVolumeConstructionVehsQ = new QuestionYN(7, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.specificAgencyPoliciesQ = new QuestionYN(8, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.existingPerformanceMeasuresQ = new QuestionYN(9, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.outreachTravelerInfoQ = new QuestionYN(10, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        QuestionYN dataCollectionNeedsQ = new QuestionYN(11, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.emergencyShoulderQ = new QuestionYN(12, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.loweredSpeedLimitsQ = new QuestionYN(13, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.allowAutomatedSpeedEnforcementQ = new QuestionYN(14, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        proj.automatedEnforcementAllowedProperty().bindBidirectional(allowAutomatedSpeedEnforcementQ.answerIsYesProperty());
        q = (JSONObject) jArr.get(qIdx++);
        this.rampGeometryQ = new QuestionYN(15, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.disableRampMetersQ = new QuestionYN(16, Question.GOAL_USER_NEEDS, q.get("Question").toString());
        q = (JSONObject) jArr.get(qIdx++);
        this.biannualProcessReviewQ = new QuestionYN(17, Question.GOAL_USER_NEEDS, q.get("Question").toString());

        qGoalWizardList.add(this.congestionNoticableQ);
        this.congestionNoticableQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.driverDiversionQ);
        this.driverDiversionQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.significantQueueingQ);
        this.significantQueueingQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.reducedLaneWidthQ);
        this.reducedLaneWidthQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.reducedSightDistanceQ);
        this.reducedSightDistanceQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(transitVehicleQ);
        qGoalWizardList.add(this.highVolumeConstructionVehsQ);
        this.highVolumeConstructionVehsQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.specificAgencyPoliciesQ);
        this.specificAgencyPoliciesQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.existingPerformanceMeasuresQ);
        this.existingPerformanceMeasuresQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.outreachTravelerInfoQ);
        this.outreachTravelerInfoQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(dataCollectionNeedsQ);
        qGoalWizardList.add(this.emergencyShoulderQ);
        this.emergencyShoulderQidx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.loweredSpeedLimitsQ);
        this.loweredSpeedLimitsQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.allowAutomatedSpeedEnforcementQ);
        this.allowAutomatedSpeedEnforcementQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.rampGeometryQ);
        this.rampGeometryQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.disableRampMetersQ);
        this.disableRampMetersQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(this.biannualProcessReviewQ);
        this.biannualProcessReviewQIdx = qGoalWizardList.size() - 1;
    }


    private void createGoalWizardQuestions() {
        qGoalWizardList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Do you expect congestion impacts to be noticeable to drivers?"));
        this.congestionNoticableQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.congestionNoticableQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is driver diversion expected onto alternate routes?"));
        this.driverDiversionQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.driverDiversionQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Do you anticipate significant queuing as a result of this work zone?"));
        this.significantQueueingQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.significantQueueingQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Will this work zone have reduced lane widths?"));
        this.reducedLaneWidthQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.reducedLaneWidthQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Will this work zone result in reduced sight distances that impact roadway users?"));
        this.reducedSightDistanceQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.reducedSightDistanceQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(6, Question.GOAL_USER_NEEDS, "Will transit vehicles need to travel through the work zone?"));
        //qGoalWizardList.add(new QuestionYN(7, Question.GOAL_USER_NEEDS, "Are you using rapid-set concrete mix?"));
        qGoalWizardList.add(new QuestionYN(7, Question.GOAL_USER_NEEDS, "Will there be a high volume of construction vehicles requiring access to the work zone via travel lanes?"));
        this.highVolumeConstructionVehsQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.highVolumeConstructionVehsQIdx = qGoalWizardList.size() - 1;
        //qGoalWizardList.add(new QuestionYN(8, Question.GOAL_USER_NEEDS, "Will construction vehicles access site from travel lanes?"));
        //this.constructionVehAccessQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        //bindDependantQs(constructionVehAccessQ, highVolumeConstructionVehsQ, "User Needs #8");
        qGoalWizardList.add(new QuestionYN(8, Question.GOAL_USER_NEEDS, "Are there specific agency policies for work zones as required by the work zone safety and mobility rule?"));
        this.specificAgencyPoliciesQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.specificAgencyPoliciesQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(9, Question.GOAL_USER_NEEDS, "Does the agency have existing performance targets for work zones?"));
        this.existingPerformanceMeasuresQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.existingPerformanceMeasuresQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(10, Question.GOAL_USER_NEEDS, "Will outreach and traveler information be used for this work zone?"));
        this.outreachTravelerInfoQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.outreachTravelerInfoQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(11, Question.GOAL_USER_NEEDS, "Is additional data collection needed before and/or during construction for work zone performance measures?"));
        qGoalWizardList.add(new QuestionYN(12, Question.GOAL_USER_NEEDS, "Will the shoulder in this work zone be eliminated/reduced (to where breakdowns can't be accomodated)?"));
        this.emergencyShoulderQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.emergencyShoulderQidx = qGoalWizardList.size() - 1;
        //qGoalWizardList.add(new QuestionYN(14, Question.GOAL_USER_NEEDS, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"));
        //this.emergencyShoulderQIdx = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(13, Question.GOAL_USER_NEEDS, "Will speed imits in the work zone be lowered compared to base conditions?"));
        this.loweredSpeedLimitsQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.loweredSpeedLimitsQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(14, Question.GOAL_USER_NEEDS, "Does state law allow use of automated speed enforcement in work zones?"));
        this.allowAutomatedSpeedEnforcementQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.allowAutomatedSpeedEnforcementQIdx = qGoalWizardList.size() - 1;
        proj.automatedEnforcementAllowedProperty().bindBidirectional(allowAutomatedSpeedEnforcementQ.answerIsYesProperty());
        qGoalWizardList.add(new QuestionYN(15, Question.GOAL_USER_NEEDS, "Will ramp geometry under work zone conditions constrain acceleration lanes?"));
        this.rampGeometryQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.rampGeometryQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(16, Question.GOAL_USER_NEEDS, "Will work zone activities disable ramp meters (if applicable)?"));
        this.disableRampMetersQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.disableRampMetersQIdx = qGoalWizardList.size() - 1;
        qGoalWizardList.add(new QuestionYN(17, Question.GOAL_USER_NEEDS, "Will the work zone be included in the federally-mandated biannual process review?"));
        this.biannualProcessReviewQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        this.biannualProcessReviewQIdx = qGoalWizardList.size() - 1;
    }

    private void createMajorGoalsQuestions() {
        qMajorGoalsList.addAll(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Is there a mobility goal?"));
        this.mobilityGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        this.mobilityGoalQ.setAnswerIsNo(true);
        qMajorGoalsList.addAll(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a safety goal?"));
        this.safetyGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        this.safetyGoalQ.setAnswerIsNo(true);
        qMajorGoalsList.addAll(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there a productivity goal?"));
        this.productivityGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        this.productivityGoalQ.setAnswerIsNo(true);
        qMajorGoalsList.addAll(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Is there a regulatory goal?"));
        this.regulatoryGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        this.regulatoryGoalQ.setAnswerIsNo(true);
        qMajorGoalsList.addAll(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?"));
        this.travelerInfoGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        this.travelerInfoGoalQ.setAnswerIsNo(true);
    }

    private void createUserNeedsSupportQuestions() {
        qUNSupportList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are there existing sensors, closed-circuit TV cameras, variable message signs, or travel time sensors in the work zone?"));
        qUNSupportList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Are probe data available for the work zone?"));
        qUNSupportList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Are crash data available?"));
        qUNSupportList.get(qUNSupportList.size() - 1).answerIsYesProperty().bindBidirectional(proj.crashDataAvailableProperty());
        qUNSupportList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Are there communications systems available?"));
        qUNSupportList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Are there computer options available?"));
        qUNSupportList.add(new QuestionYN(6, Question.GOAL_USER_NEEDS, "Is there software available?"));
        QuestionOption qUNS1 = new QuestionOption(1, Question.GOAL_USER_NEEDS, "How current are those data",
                new String[]{"Within 1 year", "1-3 years", "4+ Years"});
    }

    private void createITSResourcesQuestions() {
        qITSResourcesList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are there weather monitoring stations along the work zone?"));
        qITSResourcesList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a local regional or state traffic management center (TMC) that monitors the roadway?"));
        qITSResourcesList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there an existing website or traveler information system?"));
        qITSResourcesList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Do you have ITS on-call contracts?"));
        qITSResourcesList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Do you have access to leased or temporary ITS devices?"));
    }

    private void createApplicationWizardQuestions() {
        QuestionYN refQ;
        int qIdx = 1;
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"));
        qApplicationList.get(qIdx - 2).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"));
        qApplicationList.get(qIdx - 2).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will this work zone be active during the day?"));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Do you expect congestion impacts to be noticable to drivers?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable to drivers?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you expect the work zone to cause V/C to be greater than 1.0 during peak periods?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you expect the work zone to cause V/C to be greater than 1.0 during off-peak periods?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"));
        refQ = this.significantQueueingQ; // GW#3 Do you expect significant queueing
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #3");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"));
        refQ = this.loweredSpeedLimitsQ; // GW#14 Will speed imits in the work zone be lowered compared to base conditions?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #13");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"));
        refQ = this.disableRampMetersQ; //  GW#19 Will work zone activities disable ramp meters?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #16");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"));
        refQ = this.emergencyShoulderQ; // GW#13 Will the work zone result in closure of emergency shoulders?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #12");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"));
        refQ = emergencyResponseCorridorQIdx; // F14 Is the work zone located on an emergency response corridor?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "Stakeholder #6");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"));
        //qApplicationList.get(qIdx - 2).visibleProperty().bind(proj.crashDataAvailableProperty());
        // Redundent on GW#4 = Yes or GW#5 = Yes
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"));
        BooleanBinding bb = new BooleanBinding() {

            {
                super.bind(reducedLaneWidthQ.answerIsYesProperty());
                super.bind(reducedSightDistanceQ.answerIsYesProperty());
            }

            @Override
            protected boolean computeValue() {
                return reducedLaneWidthQ.getAnswerIsYes() || reducedSightDistanceQ.getAnswerIsYes();
            }
        };
        qApplicationList.get(qApplicationList.size() - 1).setLocked(true);
        qApplicationList.get(qApplicationList.size() - 1).setVisible(false);
        qApplicationList.get(qApplicationList.size() - 1).setRedundant(true);
        qApplicationList.get(qApplicationList.size() - 1).setRefText("User Needs #5");
        qApplicationList.get(qApplicationList.size() - 1).answerIsYesProperty().bind(bb);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"));
        refQ = this.rampGeometryQ; // GW#18 Will temporary ramp geometry constrain acceleration lanes?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #15");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will construction vehicles access site from travel lanes?"));
        //refQ = this.constructionVehAccessQ; //
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), highVolumeConstructionVehsQ, "User Needs #7");
        //bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #9");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Are there ramps or construction access points with vertical or horizontal sight distance restrictions?"));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will there be a high volume of construction vehicles requiring access to the work zone?"));
        refQ = this.highVolumeConstructionVehsQ; // GW#17 Will there be a high volume of construction vehicles?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #7");
        //qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will existing equipment be used for the work zone?"));
        //this.existingEquipmentQ = qApplicationList.get(qApplicationList.size() - 1);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the smart work zone?"));
        //refQ = this.existingEquipmentQ; // AW (previous q) Will existing equipment be used for the WZ?
        //bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "previous question");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Is automated enforcement legal in your state?"));
        refQ = this.allowAutomatedSpeedEnforcementQ; // Same as GW#15 Does state law allow use of automated speed enforcement in WZs?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #14");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Are there specific agency policies for work zones?"));
        refQ = this.specificAgencyPoliciesQ; // GW#9 Are there specific agency policies for work zones as required by the WZ safety and mobility rule?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #8");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Does the agency have existing performance targets for work zones?"));
        refQ = this.existingPerformanceMeasuresQ; // GW#10 Does the agency have existing performance targets for work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #9");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Is there a mobility goal?"));
        refQ = this.mobilityGoalQ; // Is there a mobility goal?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "Selected Goals #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"));
        refQ = this.biannualProcessReviewQ; // GW#20 Will the work zone be included in the federally-mandated biannual process review?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #17");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?"));
        refQ = this.outreachTravelerInfoQ; // GW#11 Will outreach and traveler information be used for this work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #10");
    }

    private void createApplicationWizardQuestionsV2() {
        JSONObject json = ApplicationMatrix.loadJSON();
        JSONArray jArr = (JSONArray) json.get("Application Matrix");
        QuestionYN refQ;
        int qIdx = 0;
        JSONObject q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        qApplicationList.get(qIdx - 1).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        qApplicationList.get(qIdx - 1).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable to drivers?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticeable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticeable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        refQ = this.significantQueueingQ; // GW#3 Do you expect significant queueing
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #3");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        refQ = this.loweredSpeedLimitsQ; // GW#14 Will speed limits in the work zone be lowered compared to base conditions?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #13");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_MOBILITY, q.get("Question").toString()));
        refQ = this.disableRampMetersQ; //  GW#19 Will work zone activities disable ramp meters?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #16");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        refQ = this.emergencyShoulderQ; // GW#13 Will the work zone result in closure of emergency shoulders?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #12");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        refQ = emergencyResponseCorridorQIdx; // F14 Is the work zone located on an emergency response corridor?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "Stakeholder #6");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        //qApplicationList.get(qIdx - 2).visibleProperty().bind(proj.crashDataAvailableProperty());
        // Redundent on GW#4 = Yes or GW#5 = Yes

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        BooleanBinding bb = new BooleanBinding() {

            {
                super.bind(reducedLaneWidthQ.answerIsYesProperty());
                super.bind(reducedSightDistanceQ.answerIsYesProperty());
            }

            @Override
            protected boolean computeValue() {
                return reducedLaneWidthQ.getAnswerIsYes() || reducedSightDistanceQ.getAnswerIsYes();
            }
        };
        qApplicationList.get(qApplicationList.size() - 1).setLocked(true);
        qApplicationList.get(qApplicationList.size() - 1).setVisible(false);
        qApplicationList.get(qApplicationList.size() - 1).setRedundant(true);
        qApplicationList.get(qApplicationList.size() - 1).setRefText("User Needs #5");
        qApplicationList.get(qApplicationList.size() - 1).answerIsYesProperty().bind(bb);

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_SAFETY, q.get("Question").toString()));
        refQ = this.rampGeometryQ; // GW#18 Will temporary ramp geometry constrain acceleration lanes?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #15");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_PROD, q.get("Question").toString())); // "Will construction vehicles access site from travel lanes?"
        //refQ = this.constructionVehAccessQ; //
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), highVolumeConstructionVehsQ, "User Needs #7");
        //bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #9");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_PROD, q.get("Question").toString())); //"Are there ramps or construction access points with vertical or horizontal sight distance restrictions?"

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_PROD, q.get("Question").toString())); // "Will there be a high volume of construction vehicles requiring access to the work zone?"
        refQ = this.highVolumeConstructionVehsQ; // GW#17 Will there be a high volume of construction vehicles?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #7");
        //qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will existing equipment be used for the work zone?"));
        //this.existingEquipmentQ = qApplicationList.get(qApplicationList.size() - 1);

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_PROD, q.get("Question").toString())); // "Will any exisiting ITS devices be incorporated into the smart work zone?"
        //refQ = this.existingEquipmentQ; // AW (previous q) Will existing equipment be used for the WZ?
        //bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "previous question");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_REG, q.get("Question").toString())); // "Is automated enforcement legal in your state?"
        refQ = this.allowAutomatedSpeedEnforcementQ; // Same as GW#15 Does state law allow use of automated speed enforcement in WZs?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #14");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_REG, q.get("Question").toString())); // "Are there specific agency policies for work zones?"
        refQ = this.specificAgencyPoliciesQ; // GW#9 Are there specific agency policies for work zones as required by the WZ safety and mobility rule?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #8");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_REG, q.get("Question").toString())); // "Does the agency have existing performance targets for work zones?"
        refQ = this.existingPerformanceMeasuresQ; // GW#10 Does the agency have existing performance targets for work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #9");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_REG, q.get("Question").toString())); // "Is there a mobility goal?"
        refQ = this.mobilityGoalQ; // Is there a mobility goal?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "Selected Goals #1");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_REG, q.get("Question").toString())); // "Will the work zone be included in the federally-mandated biannual process review?"
        refQ = this.biannualProcessReviewQ; // GW#20 Will the work zone be included in the federally-mandated biannual process review?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #17");

        q = (JSONObject) jArr.get(qIdx++);
        qApplicationList.add(new QuestionYN(qIdx, Question.GOAL_TRAVELER_INFO, q.get("Question").toString())); // "Will outreach and traveler information be used for this work zone?"
        refQ = this.outreachTravelerInfoQ; // GW#11 Will outreach and traveler information be used for this work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #10");
    }

    /**
     * Creates the list of Stakeholder wizard questions. Two observable
     * ArrayLists are filled. The first (qStakeholderYNList) consists of the
     * yes/no questions, and the second (qStakeholderOptionList) holds the
     * questions with additional options.
     */
    private void createStakeholderWizardQuestions() {
        int qIdx = 1;
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there schools or universities in the area?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Do you expect a high percentage of tourists or unfamiliar drivers using the routes?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a special event venue nearby?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Does a transit line run parallel to the work zone?"));
        bindDependantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.qGoalWizardList.get(this.transitVehQIdx), "User Needs #6");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there other work zones in the area?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone located on a priority route for an emergency response system?"));
        qStakeholderYNList.get(qStakeholderYNList.size() - 1).hasMoreInfo = true;
        qStakeholderYNList.get(qStakeholderYNList.size() - 1).setComment("An emergency response corridor includes priority first responder routes, evacuation routes and all other \"weather emergency\" routes.");
        emergencyResponseCorridorQIdx = qStakeholderYNList.get(qStakeholderYNList.size() - 1);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there local businesses/shopping centers located near the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Will there be restrictions to side streets?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is this a freight or shipping corridor?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there ped/bike routes impacted by the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone on a roadway that is part of a signalized / coordinated system?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a concern the work zone will cause unwanted diversion on to local roads?"));
        bindDependantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.driverDiversionQ, "User Needs #2");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a mobility goal? (Refer to \"Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.mobilityGoalQ, "Selected Goals #1");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a productivity goal? (Refer to \"Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.productivityGoalQ, "Selected Goals #3");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a regulatory goal? (Refer to \"Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.regulatoryGoalQ, "Selected Goals #4");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a safety goal? (Refer to \"Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.safetyGoalQ, "Selected Goals #2");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a traveler information goal? (Refer to \"Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.travelerInfoGoalQ, "Selected Goals #5");
        qIdx = 1;
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What agencies are responsible for responding to incidents/patrolling the roadway?)",
                new String[]{"Local Police/Sheriff", "State Police", "Service Patrol or Contractor", "N/A"}));
        final QuestionOption patrolQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        proj.patrollingAgencyProperty().bindBidirectional(patrolQ.answerStringProperty());
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Will the work zone require lane closures? (Specified in WZ Metadata)",
                new String[]{"Yes", "No"}));
        final QuestionOption numLanesQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        numLanesQ.setResponseIdx(proj.getNumLanesClosed() > 0 ? 0 : 1);
        proj.numLanesClosedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                numLanesQ.setAnswer(newVal.intValue() > 0 ? "Yes" : "No");
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What is the functional class of the roadway? (Specified in WZ Metadata)",
                Project.FUNCTIONAL_CLASS_LIST));
        final QuestionOption funcQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        proj.functionalClassProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                funcQ.setAnswer(newVal);
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Which agency is reponsible for maintaining the roadway? (Specified in WZ Metadata)",
                Project.AGENCY_LIST));
        proj.maintainingAgencyProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setAnswer(newVal);
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);

    }

    /**
     * Creates the list of Stakeholder wizard questions. Two observable
     * ArrayLists are filled. The first (qStakeholderYNList) consists of the
     * yes/no questions, and the second (qStakeholderOptionList) holds the
     * questions with additional options.
     */
    private void createStakeholderWizardQuestionsV2() {
        JSONObject json = StakeholderMatrix.loadJSON();
        JSONArray jArr = (JSONArray) json.get("Stakeholders Matrix");
        int qIdx = 0;
        JSONObject q = (JSONObject) jArr.get(qIdx++); // Skip the first question in the matrix
        q = (JSONObject) jArr.get(qIdx++);
        final QuestionOption funcQ = new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER,
                q.get("Question").toString() + " (Specified in WZ Metadata)", // "What is the functional class of the roadway? (Specified in WZ Metadata)"
                Project.FUNCTIONAL_CLASS_LIST);
        proj.functionalClassProperty().addListener((ov, oldVal, newVal) -> funcQ.setAnswer(newVal));
        funcQ.setLocked(true);


        q = (JSONObject) jArr.get(qIdx++);
        final QuestionOption numLanesQ = new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER,
                q.get("Question").toString() + " (Specified in WZ Metadata)", // "Will the work zone require lane closures? (Specified in WZ Metadata)"
                new String[]{"Yes", "No"});
        numLanesQ.setResponseIdx(proj.getNumLanesClosed() > 0 ? 0 : 1);
        proj.numLanesClosedProperty().addListener((ov, oldVal, newVal) -> numLanesQ.setAnswer(newVal.intValue() > 0 ? "Yes" : "No"));
        numLanesQ.setLocked(true);

        q = (JSONObject) jArr.get(qIdx++);
        final QuestionOption maintainingAgencyQ = new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER,
                q.get("Question").toString() + " (Specified in WZ Metadata)", // "Which agency is responsible for maintaining the roadway? (Specified in WZ Metadata)"
                Project.AGENCY_LIST);
        proj.maintainingAgencyProperty().addListener((ov, oldVal, newVal) -> maintainingAgencyQ.setAnswer(newVal));
        maintainingAgencyQ.setLocked(true);

        q = (JSONObject) jArr.get(qIdx++);
        final QuestionOption patrolQ = new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER,
                q.get("Question").toString(), // "What agencies are responsible for responding to incidents/patrolling the roadway?)"
                new String[]{"Local Police/Sheriff", "State Police", "Service Patrol or Contractor", "N/A"});
        proj.patrollingAgencyProperty().bindBidirectional(patrolQ.answerStringProperty());

        qStakeholderOptionList.addAll(patrolQ, numLanesQ, funcQ, maintainingAgencyQ);

        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString())); // "Are there schools or universities in the area?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Do you expect a high percentage of tourists or unfamiliar drivers using the routes?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString())); // "Is there a special event venue nearby?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Does a transit line run parallel to the work zone?"
        bindDependantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.qGoalWizardList.get(this.transitVehQIdx), "User Needs #6");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Are there other work zones in the area?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Is the work zone located on a priority route for an emergency response system?"
        qStakeholderYNList.get(qStakeholderYNList.size() - 1).hasMoreInfo = true;
        qStakeholderYNList.get(qStakeholderYNList.size() - 1).setComment("An emergency response corridor includes priority first responder routes, evacuation routes and all other \"weather emergency\" routes.");
        emergencyResponseCorridorQIdx = qStakeholderYNList.get(qStakeholderYNList.size() - 1);
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Are there local businesses/shopping centers located near the work zone?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER,q.get("Question").toString()));  // "Will there be restrictions to side streets?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Is this a freight or shipping corridor?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString()));  // "Are there ped/bike routes impacted by the work zone?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString())); // "Is the work zone on a roadway that is part of a signalized / coordinated system?"
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString())); // "Is there a concern the work zone will cause unwanted diversion on to local roads?"
        bindDependantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.driverDiversionQ, "User Needs #2");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString() + " (Refer to \"Goals\" Step)")); // "Is there a mobility goal? (Refer to \"Goals\" Step)"
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.mobilityGoalQ, "Selected Goals #1");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString() + " (Refer to \"Goals\" Step)"));  //"Is there a productivity goal? (Refer to \"Goals\" Step)"
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.productivityGoalQ, "Selected Goals #3");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString() + " (Refer to \"Goals\" Step)"));  //"Is there a regulatory goal? (Refer to \"Goals\" Step)"
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.regulatoryGoalQ, "Selected Goals #4");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString() + " (Refer to \"Goals\" Step)"));  // "Is there a safety goal? (Refer to \"Goals\" Step)"
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.safetyGoalQ, "Selected Goals #2");
        q = (JSONObject) jArr.get(qIdx++);
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, q.get("Question").toString() + " (Refer to \"Goals\" Step)"));  // "Is there a traveler information goal? (Refer to \"Goals\" Step)"
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.travelerInfoGoalQ, "Selected Goals #5");

    }

    /**
     * Creates this list of project documentation Questions
     */
    private void createProjectDocumentationQuesitons() {
        int qIdx = 0;
        // Benefits (Big list, mark all that apply) (Step 2)
        String[] benefitOpts = {"Reducing traffic volumes",
            "Reducing speed variance", "Reducing queue-related crashes",
            "Reducing vehicle conflicts",
            "Reducing/eliminating dangerous merges",
            "Reducing travel time",
            "Reducing incident response time",
            "Reducing speeds/increasing motorist speed compliance"
        };
        this.qBenefitList.add(new QuestionOptionMS(qIdx++, Question.GOAL_DOCUMENTATION, "What will the WZITS be used for?", benefitOpts));
        // Costs
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will any sensors be deployed?"));
        qCostList.get(qCostList.size() - 1).setCommentPrompt("How many / what type?  Additional comments...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will portable variable message signs will be deployed?"));
        qCostList.get(qCostList.size() - 1).setCommentPrompt("How many / what type?  Additional comments...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will cameras will be deployed?"));
        qCostList.get(qCostList.size() - 1).setCommentPrompt("How many / what type?  Additional comments...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Which ITS devices will be rented/leased?"));
        qCostList.get(qCostList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        qCostList.get(qCostList.size() - 1).setCommentPrompt("Describe...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Which ITS devices will be purchased?"));
        qCostList.get(qCostList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        qCostList.get(qCostList.size() - 1).setCommentPrompt("Describe...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Who will maintain and calibrate the system?"));
        qCostList.get(qCostList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        qCostList.get(qCostList.size() - 1).setCommentPrompt("Describe...");
        qCostList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have system goals, objectives and a concept of operations been defined?"));
        qCostList.add(new QuestionOption(qIdx++, Question.GOAL_DOCUMENTATION, "How much time is scheduled for smart work zone set-up?",
                new String[]{"<1 Week", "1-4 Weeks", "4-12 Weeks", "12-25 Weeks", "25+ Weeks"}));
        qCostList.add(new QuestionOption(qIdx++, Question.GOAL_DOCUMENTATION, "What type of software will be to manage the smart work zone?",
                new String[]{"Standalone", "Customizable", "N/A"}));
        // Institutional/Jurisdictional
        this.qJurisdictionalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient funding been provided in the construction contract for the work zone?"));
        qJurisdictionalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will implementation of ITS for the work zone require coordination between internal stakeholders such as STMC, TMC and/or IT?" +
                " If so, please list reasoning and contact information for applicable parties."));
        qJurisdictionalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Do your agency ITS experts interact with road design and construction experts in relation to work zones?"));
        qJurisdictionalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Does your agency have staff with experience in smart work zone ITS?"));
        qJurisdictionalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Do long standard operational procedures (SOPs) need to be adopted?"));
        // Legal/Policy
        this.qLegalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is the type of system being implemented permitted under the current laws and regulations?"));
        qLegalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any liability issues for placing ITS equipment in a work zone?"));
        qLegalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there restrictions regarding the archiving of data in your state?"));
        qLegalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any liability issues regarding the posting of warning messages?"));
        // Stakeholder buy-in
        this.qStakeholderBuyInList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have inter-agency relationships been established between stakeholders?"));
        qStakeholderBuyInList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are regular meetings held with stakeholders to keep them apprised of the project?"));
        qStakeholderBuyInList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a champion for the project?"));
        qStakeholderBuyInList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have the estimated benefits of the project been documented?"));
        // Concept of operations (End Step 2/Start Step 3)
        this.qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there data flow between system components?"));
        qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there information flow between the agency and the public?"));
        qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Do you have communication flow charts?"));
        qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "How will the system ultimately operate?"));
        qConOpsList.get(qConOpsList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there an overall strategy for the system?"));
        qConOpsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "How does the work zone fit into the overall construction project?"));
        qConOpsList.get(qConOpsList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        // System Requirement (Start Step 3)
        this.qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have physical requirements been defined?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have functional requirements been defined?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have system requirements been defined?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has a traceability matrix been established?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has interoperability or connectivity been established with other ITS equipment in the region?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has connectivity been established with the traffic management center (TMC) in the region?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will changes in the work zone environment be addressed over time?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has a contingency plan been included with the design to address delays in the project?"));
        qSysReqList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have operation, maintenance, and calibration requirements been defined?"));
        // System Design (?)
        // Testing Strategy
        this.qTestingStratList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has a test plan for the project been developed?"));
        // Operations & Maintenance
        this.qOpsMaintList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there requirements for response times when there are equipment failures?"));
        qOpsMaintList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a procedure for enforcing penalties for system downtime?"));
        // Staff Training needs
        this.qStaffTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Where will training be held?"));
        qStaffTrainingList.get(qStaffTrainingList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        qStaffTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has staff been trained on contractor notification procedures?"));
        // System Security
        this.qSysSecurityList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is access to the smart work zone ITS application protected from unauthorized users?"));
        qSysSecurityList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has protection and recovery from vandalism and stolen system components such as batteries been addressed?"));
        // Project Evaluation
        this.qProjectEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is a system evaluation of the project planned?"));
        qProjectEvalList.get(qProjectEvalList.size() - 1).setCommentPrompt("Describe the planned evaluation, or elaborate as to why it is not being considered...");
        qProjectEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Were the system goals and objectives explicity stated?"));
        //qProjectEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Was an evaluation for the project considered at the beginning of the project?"));
        qProjectEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a mechanism in place for the public to offer feedback on the smart work zone system?"));
        qProjectEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Should changes be made to optimize the system or improve performance?"));
        // System Benefit/Cost (End Step 3)
        this.qSysBCList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on mobility been considered?"));
        qSysBCList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on safety been considered?"));
        qSysBCList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the cost estimate been re-examined now that the system requirements have been defined?"));
        // Direct/Indirect (Start Step 4)
        this.qDirectIndirectList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Does the agency want control over the work zone setup, operation and management? If yes, please describe the extent below."));
        // Mechanism
        this.qMechanismList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "What type of award mechanism do you plan to use?"));
        qMechanismList.get(qMechanismList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        // Request for Proposals
        this.qRFPList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is your agency issuing a request for proposals (RFP)?"));
        qRFPList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have clear specifications for the system been defined?"));
        qRFPList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will the agency hire an independent evaluator for the system?"));
        // Vendor Selection (End Step 4)
        this.qVendorSelectionList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has a selection committee been formed?"));
        qVendorSelectionList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have the proposal review evaluation criteria been specified?"));
        // System Plans (Start Step 5)
        this.qSysPlansList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will the system be operated as a stand-alone system?"));
        qSysPlansList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "What is the level of agency involvement?"));
        qSysPlansList.get(qSysPlansList.size() - 1).setCommentQType(Question.COMMENT_QTYPE_NA);
        // Scheduling
        this.qSchedulingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient lead time to deploy the smart work zone ITS system been included in the construction project schedule?"));
        qSchedulingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient testing and calibration time and effort been included in the construction project schedule?"));
        qSchedulingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the evaluation process been included in the schedule?"));
        // Acceptance Testing
        this.qAcceptanceTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has system acceptance testing been conducted using the test plan?"));
        qAcceptanceTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is the smart work zone ITS system flexible enough to incorporate construction design changes and delays?"));
        qAcceptanceTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the system been verified from a driver's expectation?"));
        qAcceptanceTrainingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the system deployment been field verified by agency personnel?"));
        // PDeploymjent Issues (End Step 5)
        this.qDeploymentIssuesList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a contingency plan for addressing communication issues with the equipment?"));
        qDeploymentIssuesList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any weather related issues that need to be considered with the smart work zone ITS deployment?"));
        qDeploymentIssuesList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has an experienced/qualified contractor been selected?"));
        qDeploymentIssuesList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is regular communication with the stakeholders continuing during the deployment phase?"));
        qDeploymentIssuesList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a way to incorporate user feedback into the system?"));
        // Changing Conditions (Start Step 6)
        this.qChangingConditionsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has support for changing work zone requirements been considered?"));
        qChangingConditionsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is the agency or contractor personnel prepared to make changes to the work zone?"));
        qChangingConditionsList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is coordination between construction team, agency personnel and ITS operators clearly defined?"));
        // Using/Sharing Info
        this.qSharingInfoList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is data being collected on the project?"));
        qSharingInfoList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the agency considered making the data available through an XML feed?"));
        qSharingInfoList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will camera images be made available to the public through the agency website?"));
        qSharingInfoList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are performance measures being collected through the smart work zone ITS data?"));
        // Staffing
        this.qStaffingList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Will consistent personnel be available through the duration of the project?"));
        // Public Support
        this.qPublicSupportList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is there public support for the project?"));
        qPublicSupportList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the public been adequately prepared for the project?"));
        qPublicSupportList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Have elected officials been adequately briefed on the project?"));
        qPublicSupportList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has the media and public affairs office been adequately briefed on the project?"));
        // Monitoring/Evaluation (End Step 6)
        this.qMonitoringEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Has a system been put in place for ongoing monitoring and evaluation of the project?"));
        qMonitoringEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Are there provisions in place to modify or recalibrate the smart work zone ITS set-up throughout the project?"));
        qMonitoringEvalList.add(new QuestionYN(qIdx++, Question.GOAL_DOCUMENTATION, "Is a final evaluation of the project planned?"));

    }

    private void connectToProgressIndicators() {
        connectGoalsProgress();
        connectFeasibilityProgress();
        connectStakeholderProgress();
        connectApplicationProgress();
    }

    private void connectGoalsProgress() {
        final int numRequiredQs = this.qGoalWizardList.size();// + this.qMajorGoalsList.size();
        for (QuestionYN q : qGoalWizardList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressGoal.set(Math.max(0, proj.progressGoal.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressGoal.set(Math.min(1.0, proj.progressGoal.get() + 1.0 / numRequiredQs));
                    }
                    proj.getGoalNeedsMatrix().computeScores();
                }
            });
        }
        for (QuestionYN q : qMajorGoalsList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressGoal.set(Math.max(0, proj.progressGoal.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressGoal.set(Math.min(1.0, proj.progressGoal.get() + 1.0 / numRequiredQs));
                    }
                    proj.getGoalNeedsMatrix().computeScores();
                }
            });
        }
    }

    private void connectFeasibilityProgress() {
        final int numRequiredQs = this.qFeasYNList.size() + this.qFeasOptionList.size();
        for (QuestionYN q : qFeasYNList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressFeas.set(Math.max(0, proj.progressFeas.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressFeas.set(Math.min(1.0, proj.progressFeas.get() + 1.0 / numRequiredQs));
                    }
                    proj.getFeasibilityMatrix().computeFeasibility();
                }
            });
        }
        for (QuestionOption q : qFeasOptionList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressFeas.set(Math.max(0, proj.progressFeas.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressFeas.set(Math.min(1.0, proj.progressFeas.get() + 1.0 / numRequiredQs));
                    }
                    proj.getFeasibilityMatrix().computeFeasibility();
                }
            });
        }
    }

    private void connectStakeholderProgress() {
        final int numRequiredQs = 3 + this.qStakeholderYNList.size() - 5; // + this.qStakeholderOptionList.size();
        proj.functionalClassProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (newVal == null || newVal.equalsIgnoreCase("Select")) {
                    if (proj.progressStake.get() > 0) {
                        proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                    }
                } else if (oldVal == null || oldVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                }
                proj.getStakeholderMatrix().computeStakeholders();
            }
        });
        proj.maintainingAgencyProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (newVal == null || newVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                } else if (oldVal == null || oldVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                }
                proj.getStakeholderMatrix().computeStakeholders();
            }
        });
        if (proj.getNumLanesClosed() > 0) {
            proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
        }
        proj.numLanesClosedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (newVal.intValue() <= 0) {
                    proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                } else {
                    proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                }
                proj.getStakeholderMatrix().computeStakeholders();
            }
        });
        proj.patrollingAgencyProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (newVal == null || newVal.equalsIgnoreCase("Select")) {
                    if (proj.progressStake.get() > 0) {
                        proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                    }
                } else if (oldVal == null || oldVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                }
                proj.getStakeholderMatrix().computeStakeholders();
            }
        });
        for (QuestionYN q : qStakeholderYNList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                    }
                    proj.getStakeholderMatrix().computeStakeholders();
                }
            });
        }
    }

    private void connectApplicationProgress() {
        final int numRequiredQs = this.qApplicationList.size() - 1;
        for (Question q : this.qApplicationList) {
            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                    if (newVal.intValue() < 0) {
                        proj.progressApp.set(Math.max(0, proj.progressApp.get() - 1.0 / numRequiredQs));
                    } else if (oldVal.intValue() < 0) {
                        proj.progressApp.set(Math.min(1.0, proj.progressApp.get() + 1.0 / numRequiredQs));
                    }
                    proj.getApplicationMatrix().computeScores();
                }
            });
        }
    }

    /**
     * Getter for the list of Goal Wizard Questions
     *
     * @return
     */
    public ObservableList<QuestionYN> getGoalWizardQs() {
        return this.qGoalWizardList;
    }

    /**
     * Getter for the number of questions of the goal wizard.
     *
     * @return
     */
    public int getGoalWizardQCount() {
        return this.qGoalWizardList.size();
    }

    public ObservableList<QuestionYN> getAppWizardQs() {
        return this.qApplicationList;
    }

    public int getAppWizardQCount() {
        return this.qApplicationList.size();
    }

    /**
     * Binds redundant questions, newQ is bound unidirectionally to origQ, which
     * means newQ is not editable if origQ is answered.
     *
     * @param newQ
     * @param origQ
     */
    private void bindRedundantQs(Question newQ, Question origQ, String refText) {
        //newQ.lockedProperty().bind(origQ.responseIdxProperty().greaterThanOrEqualTo(0));
        if (!newQ.lockedProperty().isBound()) {
            newQ.setLocked(true);
        }
        //newQ.setLocked(true);
        newQ.setRedundant(true);
        if (!newQ.visibleProperty().isBound()) {
            newQ.setVisible(false);
        }
        //newQ.setVisible(false);
        newQ.responseIdxProperty().bindBidirectional(origQ.responseIdxProperty());
        newQ.setRefText(refText);
    }

    /**
     * Binds newQ as dependent on the response of origQ. Question newQ will be
     * disabled depending on origQs response.
     *
     * @param newQ
     * @param origQ
     */
    private void bindDependantQs(Question newQ, QuestionYN origQ, String refText) {
        newQ.visibleProperty().bind(origQ.answerIsNoProperty().not());
        newQ.lockedProperty().bind(origQ.answerIsNoProperty());
        newQ.setRefText(refText);
    }

    private void createQuestionBindings() {
        // Setting specific questions
        this.congestionNoticableQ = qGoalWizardList.get(congestionNoticableQIdx);
        this.driverDiversionQ = qGoalWizardList.get(driverDiversionQIdx);
        this.significantQueueingQ = qGoalWizardList.get(significantQueueingQIdx);
        this.reducedLaneWidthQ = qGoalWizardList.get(reducedLaneWidthQIdx);
        this.reducedSightDistanceQ = qGoalWizardList.get(reducedSightDistanceQIdx);
        this.highVolumeConstructionVehsQ = qGoalWizardList.get(highVolumeConstructionVehsQIdx);
        //this.constructionVehAccessQ = qGoalWizardList.get(8);
        this.specificAgencyPoliciesQ = qGoalWizardList.get(specificAgencyPoliciesQIdx);
        this.existingPerformanceMeasuresQ = qGoalWizardList.get(existingPerformanceMeasuresQIdx);
        this.outreachTravelerInfoQ = qGoalWizardList.get(outreachTravelerInfoQIdx);
        this.emergencyShoulderQ = qGoalWizardList.get(emergencyShoulderQidx);
        this.loweredSpeedLimitsQ = qGoalWizardList.get(loweredSpeedLimitsQIdx);
        this.allowAutomatedSpeedEnforcementQ = qGoalWizardList.get(allowAutomatedSpeedEnforcementQIdx);
        this.rampGeometryQ = qGoalWizardList.get(rampGeometryQIdx);
        this.disableRampMetersQ = qGoalWizardList.get(disableRampMetersQIdx);
        this.biannualProcessReviewQ = qGoalWizardList.get(biannualProcessReviewQIdx);

        this.mobilityGoalQ = qMajorGoalsList.get(0);
        this.safetyGoalQ = qMajorGoalsList.get(1);
        this.productivityGoalQ = qMajorGoalsList.get(2);
        this.regulatoryGoalQ = qMajorGoalsList.get(3);
        this.travelerInfoGoalQ = qMajorGoalsList.get(4);

        this.emergencyResponseCorridorQIdx = qStakeholderYNList.get(5);

        // User Needs (Goal Wizard)
        //bindDependantQs(constructionVehAccessQ, highVolumeConstructionVehsQ, "User Needs #8");
        proj.automatedEnforcementAllowedProperty().bindBidirectional(allowAutomatedSpeedEnforcementQ.answerIsYesProperty());

        // User Needs Support
        qUNSupportList.get(2).answerIsYesProperty().bindBidirectional(proj.crashDataAvailableProperty());

        // Feasibility Wizard
        bindDependantQs(qFeasOptionList.get(2), this.congestionNoticableQ, "User Needs #1");
        bindDependantQs(qFeasOptionList.get(3), this.significantQueueingQ, "User Needs #3");
        bindDependantQs(qFeasYNList.get(1), this.significantQueueingQ, "User Needs #3");
        bindRedundantQs(qFeasYNList.get(3), this.driverDiversionQ, "User Needs #2");
        bindDependantQs(qFeasYNList.get(9), this.highVolumeConstructionVehsQ, "User Needs #7");

        // StakeholderWizard
        bindDependantQs(qStakeholderYNList.get(3), this.qGoalWizardList.get(this.transitVehQIdx), "User Needs #6");
        bindDependantQs(qStakeholderYNList.get(11), this.driverDiversionQ, "User Needs #2");
        bindRedundantQs(qStakeholderYNList.get(12), this.mobilityGoalQ, "Selected Goals #1");
        bindRedundantQs(qStakeholderYNList.get(13), this.productivityGoalQ, "Selected Goals #3");
        bindRedundantQs(qStakeholderYNList.get(14), this.regulatoryGoalQ, "Selected Goals #4");
        bindRedundantQs(qStakeholderYNList.get(15), this.safetyGoalQ, "Selected Goals #2");
        bindRedundantQs(qStakeholderYNList.get(16), this.travelerInfoGoalQ, "Selected Goals #5");
        proj.patrollingAgencyProperty().bindBidirectional(qStakeholderOptionList.get(0).answerStringProperty());
        final QuestionOption numLanesQ = qStakeholderOptionList.get(1);
        numLanesQ.setResponseIdx(proj.getNumLanesClosed() > 0 ? 0 : 1);
        proj.numLanesClosedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                numLanesQ.setAnswer(newVal.intValue() > 0 ? "Yes" : "No");
            }
        });
        numLanesQ.setLocked(true);
        final QuestionOption funcQ = qStakeholderOptionList.get(2);
        proj.functionalClassProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                funcQ.setAnswer(newVal);
            }
        });
        funcQ.setLocked(true);
        proj.maintainingAgencyProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                qStakeholderOptionList.get(3).setAnswer(newVal);
            }
        });
        qStakeholderOptionList.get(3).setLocked(true);

        //Application Wizard
        QuestionYN refQ;
        qApplicationList.get(0).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        qApplicationList.get(1).visibleProperty().bind(proj.numLanesClosedProperty().greaterThan(0));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable to drivers?
        bindRedundantQs(qApplicationList.get(3), refQ, "User Needs #1");
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(4), refQ, "User Needs #1");
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(5), refQ, "User Needs #1");
        refQ = this.significantQueueingQ; // GW#3 Do you expect significant queueing
        bindRedundantQs(qApplicationList.get(6), refQ, "User Needs #3");
        refQ = this.loweredSpeedLimitsQ; // GW#14 Will speed imits in the work zone be lowered compared to base conditions?
        bindRedundantQs(qApplicationList.get(7), refQ, "User Needs #13");
        refQ = this.disableRampMetersQ; //  GW#19 Will work zone activities disable ramp meters?
        bindRedundantQs(qApplicationList.get(8), refQ, "User Needs #16");
        refQ = this.emergencyShoulderQ; // GW#13 Will the work zone result in closure of emergency shoulders?
        bindRedundantQs(qApplicationList.get(9), refQ, "User Needs #12");
        refQ = emergencyResponseCorridorQIdx; // F14 Is the work zone located on an emergency response corridor?
        bindRedundantQs(qApplicationList.get(10), refQ, "Stakeholder #6");
        //qApplicationList.get(11).visibleProperty().bind(proj.crashDataAvailableProperty());
        // Redundent on GW#4 = Yes or GW#5 = Yes
        BooleanBinding bb = new BooleanBinding() {

            {
                super.bind(reducedLaneWidthQ.answerIsYesProperty());
                super.bind(reducedSightDistanceQ.answerIsYesProperty());
            }

            @Override
            protected boolean computeValue() {
                return reducedLaneWidthQ.getAnswerIsYes() || reducedSightDistanceQ.getAnswerIsYes();
            }
        };
        qApplicationList.get(12).setLocked(true);
        qApplicationList.get(12).setVisible(false);
        qApplicationList.get(12).setRedundant(true);
        qApplicationList.get(12).setRefText("User Needs #5");
        qApplicationList.get(12).answerIsYesProperty().bind(bb);
        refQ = this.rampGeometryQ; // GW#18 Will temporary ramp geometry constrain acceleration lanes?
        bindRedundantQs(qApplicationList.get(13), refQ, "User Needs #15");
        //refQ = this.constructionVehAccessQ; //
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), highVolumeConstructionVehsQ, "User Needs #7");
        //bindRedundantQs(qApplicationList.get(14), refQ, "User Needs #9");
        refQ = this.highVolumeConstructionVehsQ; // GW#17 Will there be a high volume of construction vehicles?
        bindRedundantQs(qApplicationList.get(16), refQ, "User Needs #7");
        refQ = this.allowAutomatedSpeedEnforcementQ; // Same as GW#15 Does state law allow use of automated speed enforcement in WZs?
        bindRedundantQs(qApplicationList.get(18), refQ, "User Needs #14");
        refQ = this.specificAgencyPoliciesQ; // GW#9 Are there specific agency policies for work zones as required by the WZ safety and mobility rule?
        bindRedundantQs(qApplicationList.get(19), refQ, "User Needs #8");
        refQ = this.existingPerformanceMeasuresQ; // GW#10 Does the agency have existing performance targets for work zones?
        bindRedundantQs(qApplicationList.get(20), refQ, "User Needs #9");
        refQ = this.mobilityGoalQ; // Is there a mobility goal?
        bindRedundantQs(qApplicationList.get(21), refQ, "Selected Goals");
        refQ = this.biannualProcessReviewQ; // GW#20 Will the work zone be included in the federally-mandated biannual process review?
        bindRedundantQs(qApplicationList.get(22), refQ, "User Needs #17");
        refQ = this.outreachTravelerInfoQ; // GW#11 Will outreach and traveler information be used for this work zone?
        bindRedundantQs(qApplicationList.get(23), refQ, "User Needs #10");

    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        // Writing question lists
        s.writeObject(this.qAcceptanceTrainingList.toArray(new QuestionYN[qAcceptanceTrainingList.size()]));
        s.writeObject(this.qApplicationList.toArray(new QuestionYN[qApplicationList.size()]));
        s.writeObject(this.qBenefitList.toArray(new QuestionOptionMS[qBenefitList.size()]));
        s.writeObject(this.qChangingConditionsList.toArray(new QuestionYN[qChangingConditionsList.size()]));
        s.writeObject(this.qConOpsList.toArray(new QuestionYN[qConOpsList.size()]));
        s.writeObject(this.qCostList.toArray(new Question[qCostList.size()]));
        s.writeObject(this.qDeploymentIssuesList.toArray(new QuestionYN[qDeploymentIssuesList.size()]));
        s.writeObject(this.qDirectIndirectList.toArray(new QuestionYN[qDirectIndirectList.size()]));
        s.writeObject(this.qFeasOptionList.toArray(new QuestionOption[qFeasOptionList.size()]));
        s.writeObject(this.qFeasYNList.toArray(new QuestionYN[qFeasYNList.size()]));
        s.writeObject(this.qGoalWizardList.toArray(new QuestionYN[qGoalWizardList.size()]));
        s.writeObject(this.qITSResourcesList.toArray(new QuestionYN[qITSResourcesList.size()]));
        s.writeObject(this.qJurisdictionalList.toArray(new QuestionYN[qJurisdictionalList.size()]));
        s.writeObject(this.qLegalList.toArray(new QuestionYN[qLegalList.size()]));
        s.writeObject(this.qMajorGoalsList.toArray(new QuestionYN[qMajorGoalsList.size()]));
        s.writeObject(this.qMechanismList.toArray(new QuestionYN[qMechanismList.size()]));
        s.writeObject(this.qMonitoringEvalList.toArray(new QuestionYN[qMonitoringEvalList.size()]));
        s.writeObject(this.qOpsMaintList.toArray(new QuestionYN[qOpsMaintList.size()]));
        s.writeObject(this.qProjectEvalList.toArray(new QuestionYN[qProjectEvalList.size()]));
        s.writeObject(this.qPublicSupportList.toArray(new QuestionYN[qPublicSupportList.size()]));
        s.writeObject(this.qRFPList.toArray(new QuestionYN[qRFPList.size()]));
        s.writeObject(this.qSchedulingList.toArray(new QuestionYN[qSchedulingList.size()]));
        s.writeObject(this.qSharingInfoList.toArray(new QuestionYN[qSharingInfoList.size()]));
        s.writeObject(this.qStaffTrainingList.toArray(new QuestionYN[qStaffTrainingList.size()]));
        s.writeObject(this.qStaffingList.toArray(new QuestionYN[qStaffingList.size()]));
        s.writeObject(this.qStakeholderBuyInList.toArray(new QuestionYN[qStakeholderBuyInList.size()]));
        s.writeObject(this.qStakeholderOptionList.toArray(new QuestionOption[qStakeholderOptionList.size()]));
        s.writeObject(this.qStakeholderYNList.toArray(new QuestionYN[qStakeholderYNList.size()]));
        s.writeObject(this.qSysBCList.toArray(new QuestionYN[qSysBCList.size()]));
        s.writeObject(this.qSysPlansList.toArray(new QuestionYN[qSysPlansList.size()]));
        s.writeObject(this.qSysReqList.toArray(new QuestionYN[qSysReqList.size()]));
        s.writeObject(this.qSysSecurityList.toArray(new QuestionYN[qSysSecurityList.size()]));
        s.writeObject(this.qTestingStratList.toArray(new QuestionYN[qTestingStratList.size()]));
        s.writeObject(this.qUNSupportList.toArray(new QuestionYN[qUNSupportList.size()]));
        s.writeObject(this.qVendorSelectionList.toArray(new QuestionYN[qVendorSelectionList.size()]));

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        this.qAcceptanceTrainingList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qApplicationList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qBenefitList = FXCollections.observableArrayList((QuestionOptionMS[]) s.readObject());
        this.qChangingConditionsList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qConOpsList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qCostList = FXCollections.observableArrayList((Question[]) s.readObject());
        this.qDeploymentIssuesList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qDirectIndirectList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qFeasOptionList = FXCollections.observableArrayList((QuestionOption[]) s.readObject());
        this.qFeasYNList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qGoalWizardList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qITSResourcesList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qJurisdictionalList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qLegalList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qMajorGoalsList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qMechanismList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qMonitoringEvalList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qOpsMaintList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qProjectEvalList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qPublicSupportList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qRFPList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSchedulingList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSharingInfoList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qStaffTrainingList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qStaffingList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qStakeholderBuyInList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qStakeholderOptionList = FXCollections.observableArrayList((QuestionOption[]) s.readObject());
        this.qStakeholderYNList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSysBCList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSysPlansList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSysReqList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qSysSecurityList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qTestingStratList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qUNSupportList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
        this.qVendorSelectionList = FXCollections.observableArrayList((QuestionYN[]) s.readObject());
    }

    public QuestionYN congestionNoticableQ, significantQueueingQ,
            loweredSpeedLimitsQ, disableRampMetersQ, emergencyShoulderQ,
            driverDiversionQ, reducedLaneWidthQ,
            reducedSightDistanceQ, //constructionVehAccessQ,
            specificAgencyPoliciesQ, existingPerformanceMeasuresQ,
            outreachTravelerInfoQ, allowAutomatedSpeedEnforcementQ,
            highVolumeConstructionVehsQ, rampGeometryQ, biannualProcessReviewQ;

    public int congestionNoticableQIdx = 0;
    public int driverDiversionQIdx = 1;
    public int significantQueueingQIdx = 2;
    public int reducedLaneWidthQIdx = 3;
    public int reducedSightDistanceQIdx = 4;
    public int transitVehQIdx = 5;
    public int highVolumeConstructionVehsQIdx = 6;
    public int specificAgencyPoliciesQIdx = 7;
    public int existingPerformanceMeasuresQIdx = 8;
    public int outreachTravelerInfoQIdx = 9;
    public int emergencyShoulderQidx = 11;
    public int loweredSpeedLimitsQIdx = 12;
    public int allowAutomatedSpeedEnforcementQIdx = 13;
    public int rampGeometryQIdx = 14;
    public int disableRampMetersQIdx = 15;
    public int biannualProcessReviewQIdx = 16;
    //public int constructionVehAccessQIdx=0;

    public int signalizedCorridorQIdx = 10;

    public QuestionYN emergencyResponseCorridorQIdx;

    private QuestionYN mobilityGoalQ, productivityGoalQ, regulatoryGoalQ, safetyGoalQ, travelerInfoGoalQ;

    //private QuestionYN existingEquipmentQ;
    public static final int Q_FEAS_LONG_TERM_DURATION = 0;
}
