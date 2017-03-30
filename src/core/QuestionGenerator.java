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
     *
     */
    public final ObservableList<QuestionYN> qApplicationList;

    /**
     *
     */
    public final ObservableList<Question> list6;

    /**
     *
     */
    public final ObservableList<Question> list7;

    /**
     *
     */
    public final ObservableList<Question> list8;

    /**
     *
     */
    public final ObservableList<Question> list9;

    /**
     *
     */
    public final ObservableList<Question> qProjectDocumentation;

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
        qApplicationList = FXCollections.observableArrayList();
        list6 = FXCollections.observableArrayList();
        list7 = FXCollections.observableArrayList();
        list8 = FXCollections.observableArrayList();
        list9 = FXCollections.observableArrayList();
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
        createApplicationWizardQuestions();
    }

    private void createFeasWizardQuestions() {
        int qIdx = 1;
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "What is the duration of long-term stationary work?",
                new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months"},
                new int[]{8, 5, 1}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "To what extent will users be impacted for the duration of the work zone?",
                new String[]{"Significant", "Moderate", "Minimal"},
                new int[]{8, 5, 1}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "During which time periods are noticable traffic impacts expected to occur? (Use check boxes) (Skip if 1.1.1.1 = no)",
                new String[]{"More than morning and afternoon peak hours in both directions",
                    "During most of the morning or afternoon peaks hours in either direction",
                    "Single peak hour or less in a single direction"},
                new int[]{8, 5, 1, 0}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "How long are queues expected to extend?",
                new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "<1 mile for <1 hour per day"},
                new int[]{8, 5, 1}));
        qIdx = 1;
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is traffic speed variability expected to occur?", false, 1));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect back of queue and other sight distance issues?", false, 3)); // Dependant
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.significantQueueingQ);
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are high speeds/chronic speeding expected to occur?", false, 2));
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Work zone congestion", false, 1)); // Consolidated
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is driver diversion expected onto alternate routes?", false, 1));
        bindRedundantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.driverDiversionQ);
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are merging conflicts and hazards at WZ tapers expected to occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect the work zone layout to cause driver confusion or trouble wayfinding?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will frequently changing operating conditions for traffic be used?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will variable work activities occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are oversize vehicles expected?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Construction vehicle entry/exit speed differential relative to traffic?", false, 2));
        bindDependantQs(qFeasYNList.get(qFeasYNList.size() - 1), this.highVolumeConstructionVehsQ);
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Data collection for work zone performance measures?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are unusual or unpredictable weather patterns expected?", false, 3));
        // Just in stakeholder wizard as not scored in excel feasibility wizard
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is the work zone located on an emergency response corridor?", false, 0));
        // Moved to application wizard as not scored in excel feasibility wizard
        //qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will existing equipment be used for the WZ?", false, 0));
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
        qGoalWizardList.add(new QuestionYN(17, Question.GOAL_USER_NEEDS, "Will there be a high volume of constructions vehicles requiring to access the work zone?"));
        this.highVolumeConstructionVehsQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(8, Question.GOAL_USER_NEEDS, "Will construction vehicles access site from travel lanes?"));
        this.constructionVehAccessQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        bindDependantQs(constructionVehAccessQ, highVolumeConstructionVehsQ);
        qGoalWizardList.add(new QuestionYN(10, Question.GOAL_USER_NEEDS, "Are there specific agency policies for work zones as required by the WZ safety and mobility rule?"));
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
        qGoalWizardList.add(new QuestionYN(16, Question.GOAL_USER_NEEDS, "Does state law allow use of automated speed enforcement in WZs?"));
        this.allowAutomatedSpeedEnforcementQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(18, Question.GOAL_USER_NEEDS, "Will ramp geometry under WZ conditions constrain acceleration lanes?"));
        this.rampGeometryQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(19, Question.GOAL_USER_NEEDS, "Will work zone activities disable ramp meters (if applicable)?"));
        this.disableRampMetersQ = qGoalWizardList.get(qGoalWizardList.size() - 1);
        qGoalWizardList.add(new QuestionYN(20, Question.GOAL_USER_NEEDS, "Will the WZ be included in the federally-mandated biannual process review?"));
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
        qUNSupportList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Are crash data available"));
        qUNSupportList.get(qUNSupportList.size() - 1).answerIsYesProperty().bindBidirectional(proj.crashDataAvailableProperty());
        qUNSupportList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Are there communications systems available?"));
        qUNSupportList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Are there computer options available?"));
        qUNSupportList.add(new QuestionYN(6, Question.GOAL_USER_NEEDS, "Is there software available?"));
        QuestionOption qUNS1 = new QuestionOption(1, Question.GOAL_USER_NEEDS, "How current are those data",
                new String[]{"Within 1 year", "1-3 years", "4+ Years"});
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
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"));
        refQ = this.congestionNoticableQ; // GW#1 Do you expect congestion impacts to be noticable
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
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
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), highVolumeConstructionVehsQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"));
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"));
        refQ = this.highVolumeConstructionVehsQ; // GW#17 Will there be a high volume of construction vehicles?
        bindRedundantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will existing equipment be used for the WZ?"));
        this.existingEquipmentQ = qApplicationList.get(qApplicationList.size() - 1);
        qApplicationList.add(new QuestionYN(qIdx++, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"));
        refQ = this.existingEquipmentQ; // AW (previous q) Will existing equipment be used for the WZ?
        bindDependantQs(qApplicationList.get(qApplicationList.size() - 1), refQ);
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

//        What is the latest year of crash data available? (include a check box for "No Crash Data Available")
//Is there a local, regional or state TMC that monitors the roadway?
//Do you have ITS on-call contracts?
//Do you have access to leased or temporary ITS devices?
//What will the WZITS be used for? (reducing traffic volumes, reducing speed variance, reducing queue-related crashes, reducing vehicle conflicts, reducing/eliminating dangerous merges, reducing travel time, reducing incident response time, reducing speeds/increasing motorist speed compliance)
//How many / what type of sensors will be deployed?
//How many / what type of portable variable message signs will be deployed?
//How many / what type of cameras will be deployed?
//Which ITS devices will be rented/leased?
//Which ITS devices will be purchased?
//Who will maintain and calibrate the system?
//Have system goals, objectives and a concept of operations been defined?
//How much time is scheduled for SWZ set-up?
//Stand-alone or customized software to manage the SWZ?
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
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Does a transit line run parallel to the WZ?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there other work zones in the area?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone located on an emergency response corridor?"));
        emergencyResponseCorridorQIdx = qStakeholderYNList.get(qStakeholderYNList.size() - 1);
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there local businesses/shopping centers located near the WZ?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Will there be restrictions to side streets?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is this a freight or shipping corridor?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Are there ped/bike routes impacted by the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx++, Question.GOAL_STAKEHOLDER, "Is the work zone on a roadway that is part of a signalized / coordinated system?"));
        qIdx = 1;
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What agencies are responsible for responding to incidents/patrolling the roadway?",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "What is the anticipated source of funds for the project?",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Which of the stakeholders are the \"must haves\" for the project team? (use check boxes to select stakeholders)",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderOptionList.add(new QuestionOption(qIdx++, Question.GOAL_STAKEHOLDER, "Which of the stakeholders are potential additions or \"maybes\" for the project team?",
                new String[]{"State DOT", "Municipal"}));

    }

    /**
     * Creates this list of project documentation Questions
     */
    private void createProjectDocumentationQuesitons() {
        int qIdx = 0;
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has sufficient funding been provided in the construction contract for the work zone?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is your agency relunctant to adopt SWZ ITS?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Do your agency ITS experts interact with road design and construction experts in relation to work zones?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Does your agency have staff with experience in SWZ ITS?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Do long standing operational procedures (SOPs)  need to be adopted?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is the type of system being implemented permitted under the current laws and regulations?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there any liability issues for placing ITS equipment in a work zone?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there restrictions regarding the archiving of data in your state?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there any liability issues regarding the posting of warning messages?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have inter-agency relationships been established between stakeholders?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are regular meetings held with stakeholders to keep them appraised of the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there a champion for the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have the estimated benefits of the project been documented?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there data flow between system components?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Information flow between the agency and the public?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Do you have communication flow charts?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "How will the system ultimately operate?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there an overall strategy for the system?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "How does the work zone fit into the overall construction project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have physical requirements been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have functional requirements been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have system requirements been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has a traceability matrix been established?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has interoperability or connectivity been established with other ITS equipment in the region?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has connectivity been established with the TMC in the region?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Will changes in the work zone environment be addressed over time?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has a contingency plan been included with the design to address delays in the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have operation, maintenance, and calibration requirements been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has a test plan for the project been developed?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there requirements for response times when there are equipment failures?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there a procedure for enforcing penalties for system downtime?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Where will training be held?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has staff been trained on contractor notification procedures?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is access to the SWZ ITS application protected from unauthorized users?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has protection and recovery from vandalism and stolen system components such as batteries been addressed?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is a system evaluation of the project planned?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Were the system goals and objectives explicity stated?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Was an evaluation for the project considered at the beginning of the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there a mechanism in place for the public to offer feedback on the SWZ system?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Should changes be made to optimize the system or improve performance?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on mobility been considered?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have the benefits that the system will have on safety been considered?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the cost estimate been re-examined now that the system requirements have been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "How much control does the agency want to have over the work zone setup, opeation and management?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "What type of award mechanism do you plan to use?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is your agency issuing a request for proposals (RFP)?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have clear specifications for the system been defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Will the agency hire an independent evaluator for the system?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has a selection committee been formed?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the evaluation criteria been specified for reviewing proposals with a value for each evaluation criterion?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Will the system be operated as a stand-alone system?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "What is the level of agency involvement?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has sufficient lead time to deploy the SWZ ITS system been included in the construction project schedule?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has sufficient testing and calibration time and effort been included in the construction project schedule?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the evaluation process been included in the schedule?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has system acceptance testing been conducted using the test plan?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is the SWZ ITS system flexible enough to incorporate construction design changes and delays?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the system been verified from a drivers' expectation?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the system deployment been field verified by agency personnel?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there a contingency plan for addressing communication issues with the equipment?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there any weather related issues that need to be considered with the SWZ ITS deployment?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has an experienced/qualified contractor been selected?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is regular communication with the stakeholders continuing during the deployment phase?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there a way to incorporate user feedback into the system?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has support for changing work zone requirements been considered?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is the agency or contractor personnel prepared to make changes to the work zone?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is coordination between construction team, agency personnel and ITS operators clearly defined?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is data being collected on the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the agency considered making the data available through an XML feed?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Will camera images be made available to the public through the agency website?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are performance measures being collected through the SWZ ITS data?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Will consistent personnel be available through the duration of the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is there public support for the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the public been adequately prepared for the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Have elected officials been adequately briefed on the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has the media and public affairs office been adequately briefed on the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Has a system been put in place for ongoing monitoring and evaluation of the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Are there provisions in place to modify or recalibrate the SWZ ITS set-up throughout the project?"));
        qProjectDocumentation.add(new QuestionYN(qIdx, Question.GOAL_DOCUMENTATION, "Is a final evaluation of the project planned?"));

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
        final int numRequiredQs = 2 + this.qStakeholderYNList.size(); // + this.qStakeholderOptionList.size();
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
        newQ.lockedProperty().bind(origQ.responseIdxProperty().greaterThanOrEqualTo(0));
        newQ.responseIdxProperty().bindBidirectional(origQ.responseIdxProperty());
    }

    /**
     * Binds newQ as dependent on the response of origQ. Question newQ will be
     * disabled depending on origQs response.
     *
     * @param newQ
     * @param origQ
     */
    private void bindDependantQs(Question newQ, QuestionYN origQ) {
        newQ.visibleProperty().bind(origQ.answerIsNoProperty().not());
        newQ.lockedProperty().bind(origQ.answerIsNoProperty());
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
