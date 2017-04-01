/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class QuestionGenerator {

    /**
     * List of yes/no questions for the goal wizard.
     */
    public final ObservableList<QuestionYN> qGoalWizardList;

    /**
     * List of major goals questions.
     */
    public final ObservableList<QuestionYN> qMajorGoalsList;

    /**
     * List of user needs support questions.
     */
    public final ObservableList<QuestionYN> qUNSupportList;
    /**
     * list of option questions for the Feasibility Wizard.
     */
    public final ObservableList<QuestionOption> qFeasOptionList;

    /**
     * List of yes/no questions for the Feasibility Wizard.
     */
    public final ObservableList<QuestionYN> qFeasYNList;

    /**
     * List of yes/no questions for the stakeholder wizard.
     */
    public final ObservableList<QuestionYN> qStakeholderYNList;

    /**
     * List of option questions for the stakeholder wizard.
     */
    public final ObservableList<QuestionOption> qStakeholderOptionList;

    /**
     * List of yes/no questions for the ITS resources step.
     */
    public final ObservableList<QuestionYN> qITSResourcesList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qApplicationList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qBenefitList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qCostList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qJurisdictionalList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qLegalList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qStakeholderBuyInList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qConOpsList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qSysReqList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qTestingStratList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qOpsMaintList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qStaffTrainingList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qSysSecurityList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qProjectEvalList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qSysBCList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qDirectIndirectList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qMechanismList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qRFPList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qVendorSelectionList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qSysPlansList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qSchedulingList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qAcceptanceTrainingList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qDeploymentIssuesList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qChangingConditionsList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qSharingInfoList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qStaffingList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qPublicSupportList;
    /**
     *
     */
    public final ObservableList<QuestionYN> qMonitoringEvalList;

    /**
     *
     */
    public final ObservableList<QuestionYN> qProjectDocumentation;

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
        qProjectDocumentation = FXCollections.observableArrayList();

        initializeQuestions();
        connectToProgressIndicators();

        ChangeListener questionAnsweredListener = new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                proj.getGoalNeedsMatrix().computeScores();
            }
        };
    }

    private void initializeQuestions() {
        createGoalWizardQuestions();
        createUserNeedsSupportQuestions();
        createMajorGoalsQuestions();
        createFeasWizardQuestions();
        createStakeholderWizardQuestions();
        createITSResourcesQuestions();
        createApplicationWizardQuestions();
        createProjectDocumentationQuesitons();
    }

    private void createFeasWizardQuestions() {
        int qIdx = 1;
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "What is the duration of long-term stationary work?",
                new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months"},
                new int[]{8, 5, 1}));
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
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is driver diversion expected onto alternate routes?", false, 1));
        bindRedundantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.driverDiversionQ);
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are merging conflicts and hazards at work zone tapers expected to occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect the work zone layout to cause driver confusion or trouble wayfinding?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will frequently changing operating conditions for traffic be used?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will variable work activities occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are oversize vehicles expected?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect a construction vehicle entry/exit speed differential relative to traffic?", false, 2));
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.highVolumeConstructionVehsQ, "User Needs #8");
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

    private void createGoalWizardQuestions() {
        qGoalWizardList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Do you expect congestion impacts to be noticable to drivers?"));
        this.congestionNoticableQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is driver diversion expected onto alternate routes?"));
        this.driverDiversionQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Do you anticipate significant queuing as a result of this work zone?"));
        this.significantQueueingQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Will this work zone have reduced lane widths?"));
        this.reducedLaneWidthQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Will this work zone have reduced sight distance impact?"));
        this.reducedSightDistanceQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(9, Question.GOAL_USER_NEEDS, "Will transit vehicles need to travel through the work zone?"));
        qGoalWizardList.add(new QuestionYN(7, Question.GOAL_USER_NEEDS, "Are you using rapid-set concrete mix?"));
        qGoalWizardList.add(new QuestionYN(17, Question.GOAL_USER_NEEDS, "Will there be a high volume of construction vehicles requiring access to the work zone?"));
        this.highVolumeConstructionVehsQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(8, Question.GOAL_USER_NEEDS, "Will construction vehicles access site from travel lanes?"));
        this.constructionVehAccessQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        bindDependantQs(constructionVehAccessQ, highVolumeConstructionVehsQ, "User Needs #8");
        qGoalWizardList.add(new QuestionYN(10, Question.GOAL_USER_NEEDS, "Are there specific agency policies for work zones as required by the work zone safety and mobility rule?"));
        this.specificAgencyPoliciesQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(11, Question.GOAL_USER_NEEDS, "Does the agency have existing performance targets for work zone?"));
        this.existingPerformanceMeasuresQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(12, Question.GOAL_USER_NEEDS, "Will outreach and traveler information be used for this work zone?"));
        this.outreachTravelerInfoQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(13, Question.GOAL_USER_NEEDS, "Are there additional data needed before and during construction?"));
        qGoalWizardList.add(new QuestionYN(6, Question.GOAL_USER_NEEDS, "Will the shoulder in this work zone be eliminated (to where breakdowns can't be accomodated)?"));
        qGoalWizardList.add(new QuestionYN(14, Question.GOAL_USER_NEEDS, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"));
        this.emergencyShoulderQIdx = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(15, Question.GOAL_USER_NEEDS, "Will speed imits in the work zone be lowered compared to base conditions?"));
        this.loweredSpeedLimitsQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(16, Question.GOAL_USER_NEEDS, "Does state law allow use of automated speed enforcement in work zones?"));
        this.allowAutomatedSpeedEnforcementQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(18, Question.GOAL_USER_NEEDS, "Will ramp geometry under work zone conditions constrain acceleration lanes?"));
        this.rampGeometryQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(19, Question.GOAL_USER_NEEDS, "Will work zone activities disable ramp meters (if applicable)?"));
        this.disableRampMetersQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(20, Question.GOAL_USER_NEEDS, "Will the work zone be included in the federally-mandated biannual process review?"));
        this.biannualProcessReviewQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
    }

    private void createMajorGoalsQuestions() {
        qMajorGoalsList.addAll(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Is there a mobility goal?"));
        this.mobilityGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        qMajorGoalsList.addAll(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a safety goal?"));
        this.safetyGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        qMajorGoalsList.addAll(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there a productivity goal?"));
        this.productivityGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        qMajorGoalsList.addAll(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Is there a regulatory goal?"));
        this.regulatoryGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
        qMajorGoalsList.addAll(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?"));
        this.travelerInfoGoalQ = qMajorGoalsList.get(qMajorGoalsList.size() - 1);
    }

    private void createUserNeedsSupportQuestions() {
        qUNSupportList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are there existing sensors, closed-circuit TV camveras, CMS, or travel time data?"));
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
        qITSResourcesList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are ther weather monitoring stations along the work zone?"));
        qITSResourcesList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a local regional or state TMC that monitors the roadway?"));
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
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "User Needs #1");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"));
        refQ = this.significantQueueingQ; // GW#3 Do you expect significant queueing
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"));
        refQ = this.loweredSpeedLimitsQ; // GW#14 Will speed imits in the work zone be lowered compared to base conditions?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"));
        refQ = this.disableRampMetersQ; //  GW#19 Will work zone activities disable ramp meters?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"));
        // Redundent on GW#4 = Yes or GW#5 = Yes
        BooleanBinding bb = new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return reducedLaneWidthQ.getAnswerIsYes() || reducedSightDistanceQ.getAnswerIsYes();
            }
        };
        qApplicationList.get(qApplicationList.size() - 1).lockedProperty().set(true);
        qApplicationList.get(qApplicationList.size() - 1).answerIsYesProperty().bind(bb);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"));
        refQ = this.emergencyShoulderQIdx; // GW#13 Will the work zone result in closure of emergency shoulders?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"));
        refQ = emergencyResponseCorridorQIdx; // F14 Is the work zone located on an emergency response corridor?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"));
        qApplicationList.get(qIdx - 2).visibleProperty().bind(proj.crashDataAvailableProperty());
        // Redundent on GW#4 = Yes or GW#5 = Yes
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"));
        qApplicationList.get(qApplicationList.size() - 1).lockedProperty().set(true);
        qApplicationList.get(qApplicationList.size() - 1).answerIsYesProperty().bind(bb);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"));
        refQ = this.rampGeometryQ; // GW#18 Will temporary ramp geometry constrain acceleration lanes?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will construction vehicles access site from travel lanes?"));
        refQ = this.constructionVehAccessQ; //
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), highVolumeConstructionVehsQ, "User Needs #8");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"));
        refQ = this.highVolumeConstructionVehsQ; // GW#17 Will there be a high volume of construction vehicles?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will existing equipment be used for the work zone?"));
        this.existingEquipmentQ = qApplicationList.get(qApplicationList.size() - 1);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the smart work zone?"));
        refQ = this.existingEquipmentQ; // AW (previous q) Will existing equipment be used for the WZ?
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ, "previous question");
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Is automated enforcement legal in your state?"));
        refQ = this.allowAutomatedSpeedEnforcementQ; // Same as GW#15 Does state law allow use of automated speed enforcement in WZs?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Are there specific agency policies for work zones?"));
        refQ = this.specificAgencyPoliciesQ; // GW#9 Are there specific agency policies for work zones as required by the WZ safety and mobility rule?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Does the agency have existing performance targets for work zone?"));
        refQ = this.existingPerformanceMeasuresQ; // GW#10 Does the agency have existing performance targets for work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Is there a mobility goal?"));
        refQ = this.mobilityGoalQ; // Is there a mobility goal?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"));
        refQ = this.biannualProcessReviewQ; // GW#20 Will the work zone be included in the federally-mandated biannual process review?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?"));
        refQ = this.outreachTravelerInfoQ; // GW#11 Will outreach and traveler information be used for this work zone?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
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
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is this a tourist route?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a special event venue nearby?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Does a transit line run parallel to the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there other work zones in the area?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone located on an emergency response corridor?"));
        emergencyResponseCorridorQIdx = qStakeholderYNList.get(qStakeholderYNList.size() - 1);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there local businesses/shopping centers located near the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Will there be restrictions to side streets?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is this a freight or shipping corridor?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there ped/bike routes impacted by the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone on a roadway that is part of a signalized / coordinated system?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a concern the work zone will cause unwanted diversion on to local roads?"));
        bindDependantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.driverDiversionQ, "User Needs #2");
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a mobility goal? (Answered in \"Major Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.mobilityGoalQ);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a productivity goal? (Answered in \"Major Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.productivityGoalQ);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a regulatory goal? (Answered in \"Major Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.regulatoryGoalQ);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a safety goal? (Answered in \"Major Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.safetyGoalQ);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is there a traveler information goal? (Answered in \"Major Goals\" Step)"));
        bindRedundantQs(qStakeholderYNList.get(qStakeholderYNList.size() - 1), this.travelerInfoGoalQ);
        qIdx = 1;
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What agencies are responsible for responding to incidents/patrolling the roadway?)",
                new String[]{"Local Police/Sheriff", "State Police", "Service Patrol or Contractor", "N/A"}));
        final QuestionOption patrolQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        proj.patrollingAgencyProperty().bind(patrolQ.answerStringProperty());
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Will the work zone require lane closures? (Specified in WZ Metadata)",
                new String[]{"Yes", "No"}));
        final QuestionOption numLanesQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        proj.numLanesClosedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                numLanesQ.setAnswer(newVal.intValue() > 0 ? "Yes" : "No");
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What is the functional class of the roadway? (Specified in WZ Metadata)",
                new String[]{"Freeway", "Arterial", "Local"}));
        final QuestionOption funcQ = qStakeholderOptionList.get(qStakeholderOptionList.size() - 1);
        proj.functionalClassProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                funcQ.setAnswer(newVal);
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Which agency is reponsible for maintaining the roadway? (Specified in WZ Metadata)",
                new String[]{"State", "Country", "City/Town", "Other"}));
        proj.maintainingAgencyProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setAnswer(newVal);
            }
        });
        qStakeholderOptionList.get(qStakeholderOptionList.size() - 1).setLocked(true);

    }

    /**
     * Creates this list of project documentation Questions
     */
    private void createProjectDocumentationQuesitons() {
        int qIdx = 0;
        // Benefits (Big list, mark all that apply) (Step 2)
        this.qBenefitList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "What will the WZITS be used for? (reducing traffic volumes, reducing speed variance, reducing queue-related crashes, reducing vehicle conflicts, reducing/eliminating dangerous merges, reducing travel time, reducing incident response time, reducing speeds/increasing motorist speed compliance)"));
        // Costs
        this.qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How many / what type of sensors will be deployed?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How many / what type of portable variable message signs will be deployed?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How many / what type of cameras will be deployed?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Which ITS devices will be rented/leased?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Which ITS devices will be purchased?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Who will maintain and calibrate the system?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have system goals, objectives and a concept of operations been defined?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How much time is scheduled for SWZ set-up?"));
        qCostList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Stand-alone or customized software to manage the SWZ?"));
        // Institutional/Jurisdictional
        this.qJurisdictionalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient funding been provided in the construction contract for the work zone?"));
        qJurisdictionalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is your agency relunctant to adopt smart work zone ITS?"));
        qJurisdictionalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Do your agency ITS experts interact with road design and construction experts in relation to work zones?"));
        qJurisdictionalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Does your agency have staff with experience in smart work zone ITS?"));
        qJurisdictionalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Do long standing operational procedures (SOPs)  need to be adopted?"));
        // Legal/Policy
        this.qLegalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is the type of system being implemented permitted under the current laws and regulations?"));
        qLegalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any liability issues for placing ITS equipment in a work zone?"));
        qLegalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there restrictions regarding the archiving of data in your state?"));
        qLegalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any liability issues regarding the posting of warning messages?"));
        // Stakeholder buy-in
        this.qStakeholderBuyInList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have inter-agency relationships been established between stakeholders?"));
        qStakeholderBuyInList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are regular meetings held with stakeholders to keep them appraised of the project?"));
        qStakeholderBuyInList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a champion for the project?"));
        qStakeholderBuyInList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have the estimated benefits of the project been documented?"));
        // Concept of operations (End Step 2/Start Step 3)
        this.qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there data flow between system components?"));
        qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Information flow between the agency and the public?"));
        qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Do you have communication flow charts?"));
        qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How will the system ultimately operate?"));
        qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there an overall strategy for the system?"));
        qConOpsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How does the work zone fit into the overall construction project?"));
        // System Requirement (Start Step 3)
        this.qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have physical requirements been defined?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have functional requirements been defined?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have system requirements been defined?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has a traceability matrix been established?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has interoperability or connectivity been established with other ITS equipment in the region?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has connectivity been established with the TMC in the region?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Will changes in the work zone environment be addressed over time?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has a contingency plan been included with the design to address delays in the project?"));
        qSysReqList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have operation, maintenance, and calibration requirements been defined?"));
        // System Design (?)
        // Testing Strategy
        this.qTestingStratList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has a test plan for the project been developed?"));
        // Operations & Maintenance
        this.qOpsMaintList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there requirements for response times when there are equipment failures?"));
        qOpsMaintList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a procedure for enforcing penalties for system downtime?"));
        // Staff Training needs
        this.qStaffTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Where will training be held?"));
        qStaffTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has staff been trained on contractor notification procedures?"));
        // System Security
        this.qSysSecurityList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is access to the smart work zone ITS application protected from unauthorized users?"));
        qSysSecurityList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has protection and recovery from vandalism and stolen system components such as batteries been addressed?"));
        // Project Evaluation
        this.qProjectEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is a system evaluation of the project planned?"));
        qProjectEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Were the system goals and objectives explicity stated?"));
        qProjectEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Was an evaluation for the project considered at the beginning of the project?"));
        qProjectEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a mechanism in place for the public to offer feedback on the smart work zone system?"));
        qProjectEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Should changes be made to optimize the system or improve performance?"));
        // System Benefit/Cost (End Step 3)
        this.qSysBCList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on mobility been considered?"));
        qSysBCList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on safety been considered?"));
        qSysBCList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the cost estimate been re-examined now that the system requirements have been defined?"));
        // Direct/Indirect (Start Step 4)
        this.qDirectIndirectList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "How much control does the agency want to have over the work zone setup, opeation and management?"));
        // Mechanism
        this.qMechanismList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "What type of award mechanism do you plan to use?"));
        // Request for Proposals
        this.qRFPList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is your agency issuing a request for proposals (RFP)?"));
        qRFPList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have clear specifications for the system been defined?"));
        qRFPList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Will the agency hire an independent evaluator for the system?"));
        // Vendor Selection (End Step 4)
        this.qVendorSelectionList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has a selection committee been formed?"));
        qVendorSelectionList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the evaluation criteria been specified for reviewing proposals with a value for each evaluation criterion?"));
        // System Plans (Start Step 5)
        this.qSysPlansList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Will the system be operated as a stand-alone system?"));
        qSysPlansList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "What is the level of agency involvement?"));
        // Scheduling
        this.qSchedulingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient lead time to deploy the smart work zone ITS system been included in the construction project schedule?"));
        qSchedulingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has sufficient testing and calibration time and effort been included in the construction project schedule?"));
        qSchedulingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the evaluation process been included in the schedule?"));
        // Acceptance Testing
        this.qAcceptanceTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has system acceptance testing been conducted using the test plan?"));
        qAcceptanceTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is the smart work zone ITS system flexible enough to incorporate construction design changes and delays?"));
        qAcceptanceTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the system been verified from a drivers' expectation?"));
        qAcceptanceTrainingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the system deployment been field verified by agency personnel?"));
        // PDeploymjent Issues (End Step 5)
        this.qDeploymentIssuesList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a contingency plan for addressing communication issues with the equipment?"));
        qDeploymentIssuesList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there any weather related issues that need to be considered with the smart work zone ITS deployment?"));
        qDeploymentIssuesList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has an experienced/qualified contractor been selected?"));
        qDeploymentIssuesList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is regular communication with the stakeholders continuing during the deployment phase?"));
        qDeploymentIssuesList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there a way to incorporate user feedback into the system?"));
        // Changing Conditions (Start Step 6)
        this.qChangingConditionsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has support for changing work zone requirements been considered?"));
        qChangingConditionsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is the agency or contractor personnel prepared to make changes to the work zone?"));
        qChangingConditionsList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is coordination between construction team, agency personnel and ITS operators clearly defined?"));
        // Using/Sharing Info
        this.qSharingInfoList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is data being collected on the project?"));
        qSharingInfoList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the agency considered making the data available through an XML feed?"));
        qSharingInfoList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Will camera images be made available to the public through the agency website?"));
        qSharingInfoList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are performance measures being collected through the smart work zone ITS data?"));
        // Staffing
        this.qStaffingList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Will consistent personnel be available through the duration of the project?"));
        // Public Support
        this.qPublicSupportList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is there public support for the project?"));
        qPublicSupportList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the public been adequately prepared for the project?"));
        qPublicSupportList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Have elected officials been adequately briefed on the project?"));
        qPublicSupportList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has the media and public affairs office been adequately briefed on the project?"));
        // Monitoring/Evaluation (End Step 6)
        this.qMonitoringEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Has a system been put in place for ongoing monitoring and evaluation of the project?"));
        qMonitoringEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Are there provisions in place to modify or recalibrate the smart work zone ITS set-up throughout the project?"));
        qMonitoringEvalList.add(new QuestionYNComment(qIdx++, Question.GOAL_DOCUMENTATION, "Is a final evaluation of the project planned?"));

    }

    private void connectToProgressIndicators() {
        connectGoalsProgress();
        connectFeasibilityProgress();
        connectStakeholderProgress();
    }

    private void connectGoalsProgress() {
        final int numRequiredQs = this.qGoalWizardList.size() + this.qMajorGoalsList.size();
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
        final int numRequiredQs = 1 + this.qStakeholderYNList.size(); // + this.qStakeholderOptionList.size();
        proj.functionalClassProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (newVal.equalsIgnoreCase("Select")) {
                    if (proj.progressStake.get() > 0) {
                        proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                    }
                } else if (oldVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.min(1.0, proj.progressStake.get() + 1.0 / numRequiredQs));
                }
                proj.getStakeholderMatrix().computeStakeholders();
            }
        });
        proj.maintainingAgencyProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                if (newVal.equalsIgnoreCase("Select")) {
                    proj.progressStake.set(Math.max(0, proj.progressStake.get() - 1.0 / numRequiredQs));
                } else if (oldVal.equalsIgnoreCase("Select")) {
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
//        for (QuestionOption q : qStakeholderOptionList) {
//            q.responseIdxProperty().addListener(new ChangeListener<Number>() {
//                @Override
//                public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
//                    if (newVal.intValue() < 0) {
//                        proj.progressStake.set(proj.progressStake.get() - 1.0 / numRequiredQs);
//                    } else if (oldVal.intValue() < 0) {
//                        proj.progressStake.set(proj.progressStake.get() + 1.0 / numRequiredQs);
//                    }
//                    proj.getStakeholderMatrix().computeStakeholders();
//                }
//            });
//    }
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
    private void bindRedundantQs(Question newQ, Question origQ) {
        //newQ.lockedProperty().bind(origQ.responseIdxProperty().greaterThanOrEqualTo(0));
        newQ.lockedProperty().set(true);
        newQ.responseIdxProperty().bindBidirectional(origQ.responseIdxProperty());
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
        newQ.setLockedReferenceText(refText);
    }

    private QuestionYN congestionNoticableQ, significantQueueingQ,
            loweredSpeedLimitsQ, disableRampMetersQ, emergencyShoulderQIdx,
            emergencyResponseCorridorQIdx, driverDiversionQ, reducedLaneWidthQ,
            reducedSightDistanceQ, constructionVehAccessQ,
            specificAgencyPoliciesQ, existingPerformanceMeasuresQ,
            outreachTravelerInfoQ, allowAutomatedSpeedEnforcementQ,
            highVolumeConstructionVehsQ, rampGeometryQ, biannualProcessReviewQ;

    private QuestionYN mobilityGoalQ, productivityGoalQ, regulatoryGoalQ, safetyGoalQ, travelerInfoGoalQ;

    private QuestionYN existingEquipmentQ;
}
