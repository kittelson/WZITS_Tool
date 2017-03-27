/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

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

        ChangeListener questionAnsweredListener = new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                proj.getGoalNeedsMatrix().computeScores();
            }
        };
    }

    private void initializeQuestions() {
        createFeasWizardQuestions();
        createGoalWizardQuestions();
        createUserNeedsSupportQuestions();
        createMajorGoalsQuestions();
        createApplicationWizardQuestions();
        createStakeholderWizardQuestions();
    }

    private void createFeasWizardQuestions() {
        int qIdx = 0;
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "What is the duration of long-term stationary work?",
                new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months"},
                new int[]{8, 5, 1}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "To what extent will users be impacted for the duration of the work zone?",
                new String[]{"Significant", "Moderate", "Minimal"},
                new int[]{8, 5, 1}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "During which time periods are noticable traffic impacts expected to occur? (Use check boxes) (Skip if 1.1.1.1 = no)",
                new String[]{"More than morning and afternoon peak hours in both directions",
                    "During most of the morning and afternoon peaks hours in either direction",
                    "During most of a single peak hour in a single direction",
                    "Unpredictable"},
                new int[]{6, 3, 1, 0}));
        qFeasOptionList.add(new QuestionOption(qIdx++, Question.GOAL_FEASIBILITY, "How long are queues expected to extend?",
                new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "<1 mile for <1 hour per day"},
                new int[]{8, 5, 1}));
        qIdx = 0;
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is traffic speed variability expected to occur?", false, 1));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect back of queue and other sight distance issues?", false, 3)); // Dependant
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are high speeds/chronic speeding expected to occur?", false, 2));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Work zone congestion", false, 1)); // Consolidated
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "No alternate route availability", false, 1)); // Consolidated
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are merging conflicts and hazards at WZ tapers expected to occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Do you expect the work zone layout to cause driver confusion or trouble wayfinding?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will frequently changing operating conditions for traffic be used?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will variable work activities occur?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are oversize vehicles expected?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Construction vehicle entry/exit speed differential relative to traffic?", false, 2));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Data collection for work zone performance measures?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are unusual or unpredictable weather patterns expected?", false, 3));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Is the work zone located on an emergency response corridor?", false, 0));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Will existing equipment be used for the WZ?", false, 0));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are there specific agency policies for work zones?", false, 0));
        qFeasYNList.add(new QuestionYN(qIdx++, Question.GOAL_FEASIBILITY, "Are there access points with vertical or horizontal sight distance restrictions?", false, 0));
    }

    private void createGoalWizardQuestions() {
        qGoalWizardList.add(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Do you expect congestion impacts to be noticable to drivers?"));
        qGoalWizardList.add(new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is driver diversion expected onto alternate routes?"));
        qGoalWizardList.add(new QuestionYN(3, Question.GOAL_USER_NEEDS, "Do you anticipate significant queuing as a result of this work zone?"));
        qGoalWizardList.add(new QuestionYN(4, Question.GOAL_USER_NEEDS, "Will this work zone have reduced lane widths?"));
        qGoalWizardList.add(new QuestionYN(5, Question.GOAL_USER_NEEDS, "Will this work zone have reduced sight distance impact?"));
        qGoalWizardList.add(new QuestionYN(6, Question.GOAL_USER_NEEDS, "Will transit vehicles need to travel through the work zone?"));
        qGoalWizardList.add(new QuestionYN(7, Question.GOAL_USER_NEEDS, "Are you using rapid-set concrete mix?"));
        qGoalWizardList.add(new QuestionYN(8, Question.GOAL_USER_NEEDS, "Will construction vehicles access site from travel lanes?"));
        qGoalWizardList.add(new QuestionYN(9, Question.GOAL_USER_NEEDS, "Are there specific agency policies for work zones as required by the WZ safety and mobility rule?"));
        qGoalWizardList.add(new QuestionYN(10, Question.GOAL_USER_NEEDS, "Does the agency have existing performance targets for work zone?"));
        qGoalWizardList.add(new QuestionYN(11, Question.GOAL_USER_NEEDS, "Will outreach and traveler information be used for this work zone?"));
        qGoalWizardList.add(new QuestionYN(12, Question.GOAL_USER_NEEDS, "Are there additional data needed before and during construction?"));
        qGoalWizardList.add(new QuestionYN(13, Question.GOAL_USER_NEEDS, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"));
        qGoalWizardList.add(new QuestionYN(14, Question.GOAL_USER_NEEDS, "Will speed imits in the work zone be lowered compared to base conditions?"));
        qGoalWizardList.add(new QuestionYN(15, Question.GOAL_USER_NEEDS, "Does state law allow use of autmoated speed enforcement in WZs?"));
        qGoalWizardList.add(new QuestionYN(16, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?"));
        qGoalWizardList.add(new QuestionYN(17, Question.GOAL_USER_NEEDS, "Will there be a high volume of constructions vehicles requiring to access the work zone?"));
        qGoalWizardList.add(new QuestionYN(18, Question.GOAL_USER_NEEDS, "Will ramp geometry under WZ conditions constrain acceleration lanes?"));
        qGoalWizardList.add(new QuestionYN(19, Question.GOAL_USER_NEEDS, "Will work zone activities disable ramp meters (if applicable)?"));
        qGoalWizardList.add(new QuestionYN(20, Question.GOAL_USER_NEEDS, "Will the WZ be included in the federally-mandated biannual process review?"));
    }

    private void createMajorGoalsQuestions() {
        qMajorGoalsList.addAll(new QuestionYN(1, Question.GOAL_USER_NEEDS, "Is there a mobility goal?"),
                new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a safety goal?"),
                new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there a productivity goal?"),
                new QuestionYN(4, Question.GOAL_USER_NEEDS, "Is there a regulatory goal?"),
                new QuestionYN(5, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?")
        );
    }

    private void createUserNeedsSupportQuestions() {
        qUNSupportList.addAll(
                new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are there existing sensors, closed-circuit TV camveras, CMS, or travel time data?"),
                new QuestionYN(2, Question.GOAL_USER_NEEDS, "Are probe data available for the work zone?"),
                new QuestionYN(3, Question.GOAL_USER_NEEDS, "Are crash data available, and how current are those data?"),
                new QuestionYN(4, Question.GOAL_USER_NEEDS, "Are there communications systems available?"),
                new QuestionYN(5, Question.GOAL_USER_NEEDS, "Are there computer options available?"),
                new QuestionYN(6, Question.GOAL_USER_NEEDS, "Is there software available?")
        );
    }

    private void createApplicationWizardQuestions() {
        qApplicationList.add(new QuestionYN(1, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"));
        qApplicationList.add(new QuestionYN(2, Question.GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"));
        qApplicationList.add(new QuestionYN(3, Question.GOAL_MOBILITY, "Will this work zone be active during the day?"));
        qApplicationList.add(new QuestionYN(4, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"));
        qApplicationList.add(new QuestionYN(5, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"));
        qApplicationList.add(new QuestionYN(6, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"));
        qApplicationList.add(new QuestionYN(7, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"));
        qApplicationList.add(new QuestionYN(8, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"));
        qApplicationList.add(new QuestionYN(9, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"));
        qApplicationList.add(new QuestionYN(10, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"));
        qApplicationList.add(new QuestionYN(11, Question.GOAL_SAFETY, "Do you expect congestion impacts to be difficult to realized by drivers?"));
        qApplicationList.add(new QuestionYN(12, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"));
        qApplicationList.add(new QuestionYN(13, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"));
        qApplicationList.add(new QuestionYN(14, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"));
        qApplicationList.add(new QuestionYN(15, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"));
        qApplicationList.add(new QuestionYN(16, Question.GOAL_PROD, "Will vehicles access site from travel lanes?"));
        qApplicationList.add(new QuestionYN(17, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"));
        qApplicationList.add(new QuestionYN(18, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"));
        qApplicationList.add(new QuestionYN(19, Question.GOAL_PROD, "Will existing equipment be used for the WZ?"));
        qApplicationList.add(new QuestionYN(20, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"));
        qApplicationList.add(new QuestionYN(21, Question.GOAL_REG, "Is automated enforcement legal in your state?"));
        qApplicationList.add(new QuestionYN(22, Question.GOAL_REG, "Are there specific agency policies for work zones?"));
        qApplicationList.add(new QuestionYN(23, Question.GOAL_REG, "Does the agency have existing performance targets for work zone?"));
        qApplicationList.add(new QuestionYN(24, Question.GOAL_REG, "Is there a mobility goal?"));
        qApplicationList.add(new QuestionYN(25, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"));
        qApplicationList.add(new QuestionYN(26, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?"));

    }

    /**
     * Creates the list of Stakeholder wizard questions. Two observable
     * ArrayLists are filled. The first (qStakeholderYNList) consists of the
     * yes/no questions, and the second (qStakeholderOptionList) holds the
     * questions with additional options.
     */
    private void createStakeholderWizardQuestions() {
        int qIdx = 0;
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Are there schools or universities in the area?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Is this a tourist route?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Is there a special event venue nearby?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Does a transit line run parallel to the WZ?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Are there other work zones in the area?"));
        qStakeholderOptionList.add(new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER, "What agencies are responsible for responding to incidents/patrolling the roadway?",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Are there local businesses/shopping centers located near the WZ?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Will there be restrictions to side streets?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Is this a freight or shipping corridor?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Are there ped/bike routes impacted by the work zone?"));
        qStakeholderYNList.add(new QuestionYN(qIdx, Question.GOAL_STAKEHOLDER, "Is the work zone on a roadway that is part of a signalized / coordinated system?"));
        qStakeholderOptionList.add(new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER, "What is the anticipated source of funds for the project?",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderOptionList.add(new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER, "Which of the stakeholders are the \"must haves\" for the project team? (use check boxes to select stakeholders)",
                new String[]{"State DOT", "Municipal"}));
        qStakeholderOptionList.add(new QuestionOption(qIdx, Question.GOAL_STAKEHOLDER, "Which of the stakeholders are potential additions or \"maybes\" for the project team?",
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
}
