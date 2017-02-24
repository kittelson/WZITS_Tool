/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Application;
import core.Question;
import core.QuestionOption;
import core.QuestionYN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class TableHelper {

    public static ObservableList<QuestionYN> getStepQuestions(int stepIndex) {
        switch (stepIndex) {
            default:
            case 0:
                return getStep1Questions();
            case 1:
                return getStep2Questions();
        }
    }

    private static ObservableList<QuestionYN> getStep1Questions() {
        return STEP_1_GOAL_QLIST;
    }

    private static ObservableList<QuestionYN> getStep2Questions() {
        return STEP_2_APP_QLIST;
    }

    public static int getNumberOfQuestionsByStep(int stepIdx) {
        switch (stepIdx) {
            default:
                return 0;
            case 0:
                return STEP_1_GOAL_QLIST.size();
            case 1:
                return STEP_2_APP_QLIST.size();
            case 2:
                return STEP_3_QLIST.size();
            case 3:
                return STEP_4_QLIST.size();
            case 4:
                return STEP_5_QLIST.size();
            case 5:
                return STEP_6_QLIST.size();
        }
    }

    public static ObservableList<Application> getStepSummary(int stepIdx) {
        if (stepIdx == 0) {

        }
        return FXCollections.observableArrayList(new Application("Queue Warning", 5));
    }

    private static final ObservableList<QuestionYN> STEP_1_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

//    private static final ObservableList<Question> STEP_1_GOAL_QLIST = FXCollections.observableArrayList(
//            new UserGoal(1, "Do you expect congestion impacts to be noticable to drivers?"),
//            new UserGoal(2, "Is driver diversion expected onto alternate routes?"),
//            new UserGoal(3, "Do you anticipate significant queuing as a result of this work zone?"),
//            new UserGoal(4, "Will this work zone have reduced lane widths?"),
//            new UserGoal(5, "Will this work zone have reduced sight distance impact?"),
//            new UserGoal(6, "Will transit vehicles need to travel through the work zone?"),
//            new UserGoal(7, "Are you using rapid-set concrete mix?"),
//            new UserGoal(8, "Will construction vehicles access site from travel lanes?"),
//            new UserGoal(9, "Are there specific agency policies for work zones as required by the WZ safety and mobility rule?"),
//            new UserGoal(10, "Does the agency have existing performance targets for work zone?"),
//            new UserGoal(11, "Will outreach and traveler information be used for this work zone?"),
//            new UserGoal(12, "Are there additional data needed before and during construction?"),
//            new UserGoal(13, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"),
//            new UserGoal(14, "Will speed imits in the work zone be lowered compared to base conditions?"),
//            new UserGoal(15, "Does state law allow use of autmoated speed enforcement in WZs?"),
//            new UserGoal(16, "Is there a traveler information goal?"),
//            new UserGoal(17, "Will there be a high volume of constructions vehicles requiring to access the work zone?"),
//            new UserGoal(18, "Will ramp geometry under WZ conditions constrain acceleration lanes?"),
//            new UserGoal(19, "Will work zone activities disable ramp meters (if applicable)?"),
//            new UserGoal(20, "Will the WZ be included in the federally-mandated biannual process review?")
//    );
//
//    private static final ObservableList<Question> STEP_1_UN_SUP_QLIST = FXCollections.observableArrayList(new UserGoal(1, "Are there existing sensors, closed-circuit TV camveras, CMS, or travel time data?"),
//            new UserGoal(2, "Are probe data available for the work zone?"),
//            new UserGoal(3, "Are crash data available, and how current are those data?"),
//            new UserGoal(4, "Are there communications systems available?"),
//            new UserGoal(5, "Are there computer options available?"),
//            new UserGoal(6, "Is there software available?")
//    );
//
//    private static final ObservableList<Question> STEP_1_ITS_RESOURCES = FXCollections.observableArrayList(new UserGoal(1, "Are technologies to communicate with drivers available?"),
//            new UserGoal(2, "Are there weather monitoring stations along the work zone?"),
//            new UserGoal(3, "Is there a local, regional or state TMC that monitors the roadway?"),
//            new UserGoal(4, "Is there an existing website or traveler information system?"),
//            new UserGoal(5, "Do you have ITS on-call contracts?"),
//            new UserGoal(6, "Do you have access to leased or temporary ITS?")
//    );
//
//    private static final ObservableList<Question> STEP_1_SYSTEM_GOALS = FXCollections.observableArrayList(new UserGoal(1, "Is there a mobility goal?"),
//            new UserGoal(2, "Is there a safety goal?"),
//            new UserGoal(3, "Is there a productivity goal?"),
//            new UserGoal(4, "Is there a regulatory goal?"),
//            new UserGoal(5, "Is there a traveler information goal?")
//    );
    private static final ObservableList<QuestionYN> STEP_1_GOAL_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_USER_NEEDS, "Do you expect congestion impacts to be noticable to drivers?"),
            new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is driver diversion expected onto alternate routes?"),
            new QuestionYN(3, Question.GOAL_USER_NEEDS, "Do you anticipate significant queuing as a result of this work zone?"),
            new QuestionYN(4, Question.GOAL_USER_NEEDS, "Will this work zone have reduced lane widths?"),
            new QuestionYN(5, Question.GOAL_USER_NEEDS, "Will this work zone have reduced sight distance impact?"),
            new QuestionYN(6, Question.GOAL_USER_NEEDS, "Will transit vehicles need to travel through the work zone?"),
            new QuestionYN(7, Question.GOAL_USER_NEEDS, "Are you using rapid-set concrete mix?"),
            new QuestionYN(8, Question.GOAL_USER_NEEDS, "Will construction vehicles access site from travel lanes?"),
            new QuestionYN(9, Question.GOAL_USER_NEEDS, "Are there specific agency policies for work zones as required by the WZ safety and mobility rule?"),
            new QuestionYN(10, Question.GOAL_USER_NEEDS, "Does the agency have existing performance targets for work zone?"),
            new QuestionYN(11, Question.GOAL_USER_NEEDS, "Will outreach and traveler information be used for this work zone?"),
            new QuestionYN(12, Question.GOAL_USER_NEEDS, "Are there additional data needed before and during construction?"),
            new QuestionYN(13, Question.GOAL_USER_NEEDS, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"),
            new QuestionYN(14, Question.GOAL_USER_NEEDS, "Will speed imits in the work zone be lowered compared to base conditions?"),
            new QuestionYN(15, Question.GOAL_USER_NEEDS, "Does state law allow use of autmoated speed enforcement in WZs?"),
            new QuestionYN(16, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?"),
            new QuestionYN(17, Question.GOAL_USER_NEEDS, "Will there be a high volume of constructions vehicles requiring to access the work zone?"),
            new QuestionYN(18, Question.GOAL_USER_NEEDS, "Will ramp geometry under WZ conditions constrain acceleration lanes?"),
            new QuestionYN(19, Question.GOAL_USER_NEEDS, "Will work zone activities disable ramp meters (if applicable)?"),
            new QuestionYN(20, Question.GOAL_USER_NEEDS, "Will the WZ be included in the federally-mandated biannual process review?")
    );

    public static final ObservableList<QuestionYN> STEP_1_UN_SUP_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are there existing sensors, closed-circuit TV camveras, CMS, or travel time data?"),
            new QuestionYN(2, Question.GOAL_USER_NEEDS, "Are probe data available for the work zone?"),
            new QuestionYN(3, Question.GOAL_USER_NEEDS, "Are crash data available, and how current are those data?"),
            new QuestionYN(4, Question.GOAL_USER_NEEDS, "Are there communications systems available?"),
            new QuestionYN(5, Question.GOAL_USER_NEEDS, "Are there computer options available?"),
            new QuestionYN(6, Question.GOAL_USER_NEEDS, "Is there software available?")
    );

    public static final ObservableList<QuestionYN> STEP_1_ITS_RESOURCES = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_USER_NEEDS, "Are technologies to communicate with drivers available?"),
            new QuestionYN(2, Question.GOAL_USER_NEEDS, "Are there weather monitoring stations along the work zone?"),
            new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there a local, regional or state TMC that monitors the roadway?"),
            new QuestionYN(4, Question.GOAL_USER_NEEDS, "Is there an existing website or traveler information system?"),
            new QuestionYN(5, Question.GOAL_USER_NEEDS, "Do you have ITS on-call contracts?"),
            new QuestionYN(6, Question.GOAL_USER_NEEDS, "Do you have access to leased or temporary ITS?")
    );

    public static final ObservableList<QuestionYN> STEP_1_SYSTEM_GOALS = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_USER_NEEDS, "Is there a mobility goal?"),
            new QuestionYN(2, Question.GOAL_USER_NEEDS, "Is there a safety goal?"),
            new QuestionYN(3, Question.GOAL_USER_NEEDS, "Is there a productivity goal?"),
            new QuestionYN(4, Question.GOAL_USER_NEEDS, "Is there a regulatory goal?"),
            new QuestionYN(5, Question.GOAL_USER_NEEDS, "Is there a traveler information goal?")
    );

    public static final ObservableList<QuestionYN> STEP_1_FEASIBILITY = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_FEASIBILITY, "Traffic speed variability"),
            new QuestionYN(2, Question.GOAL_FEASIBILITY, "Back of queue and other sight distance issues"),
            new QuestionYN(3, Question.GOAL_FEASIBILITY, "High speeds/chronic speeding"),
            new QuestionYN(4, Question.GOAL_FEASIBILITY, "Work zone congestion"),
            new QuestionYN(5, Question.GOAL_FEASIBILITY, "No alternate route availability"),
            new QuestionYN(6, Question.GOAL_FEASIBILITY, "Merging conflicts and hazards at work zone tapers"),
            new QuestionYN(7, Question.GOAL_FEASIBILITY, "Work zone hazards/complex traffic control layout"),
            new QuestionYN(8, Question.GOAL_FEASIBILITY, "Frequently changing operating conditions for traffic"),
            new QuestionYN(9, Question.GOAL_FEASIBILITY, "Variable work activities"),
            new QuestionYN(10, Question.GOAL_FEASIBILITY, "Oversize vehicles"),
            new QuestionYN(11, Question.GOAL_FEASIBILITY, "Construction vehilce entry/exit speed differential relative to traffic"),
            new QuestionYN(12, Question.GOAL_FEASIBILITY, "Data collection for work zone performance measures"),
            new QuestionYN(13, Question.GOAL_FEASIBILITY, "Unusual or unpredictable weather patterns")
    );

    public static final ObservableList<QuestionOption> STEP_1_FEASIBILITY_OPTIONS = FXCollections.observableArrayList(
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "What is the duration of the long-term stationary work?",
                    new String[]{"> 1 Construction Seaons", "4-10 Months", "< 4 Months"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "To what extent will users be impacted for the duration of the work zone?",
                    new String[]{"Significant", "Moderate", "Minimal"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "How long are the queues expected to extend?",
                    new String[]{"At least 2 miles for at least 2 hours per day", "1-2 miles for 1-2 hours per day", "Less than 1 mile for less than 1 hour per day"}),
            new QuestionOption(1, Question.GOAL_FEASIBILITY, "During which time periods listed below are unreasonable traffic impacts expected to occur?",
                    new String[]{"More than morning and afternoon peak hours in both directions",
                        "During most of the morning and afternoon peaks hours in either direction",
                        "During most of a single peak hour in a single direction",
                        "Unpredictable"})
    );

    private static final ObservableList<QuestionYN> STEP_2_APP_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(2, Question.GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"),
            new QuestionYN(3, Question.GOAL_MOBILITY, "Will this work zone be active during the day?"),
            new QuestionYN(4, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"),
            new QuestionYN(5, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"),
            new QuestionYN(6, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"),
            new QuestionYN(7, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"),
            new QuestionYN(8, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"),
            new QuestionYN(9, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(10, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"),
            new QuestionYN(11, Question.GOAL_SAFETY, "Do you expect congestion impacts to be difficult to realized by drivers?"),
            new QuestionYN(12, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"),
            new QuestionYN(13, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"),
            new QuestionYN(14, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(15, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"),
            new QuestionYN(16, Question.GOAL_PROD, "Will vehicles access site from travel lanes?"),
            new QuestionYN(17, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"),
            new QuestionYN(18, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"),
            new QuestionYN(19, Question.GOAL_PROD, "Will existing equipment be used for the WZ?"),
            new QuestionYN(20, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"),
            new QuestionYN(21, Question.GOAL_REG, "Is automated enforcement legal in your state?"),
            new QuestionYN(22, Question.GOAL_REG, "Are there specific agency policies for work zones?"),
            new QuestionYN(23, Question.GOAL_REG, "Does the agency have existing performance targets for work zone?"),
            new QuestionYN(24, Question.GOAL_REG, "Is there a mobility goal?"),
            new QuestionYN(25, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"),
            new QuestionYN(26, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?")
    );

    private static final ObservableList<QuestionYN> STEP_3_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_4_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_5_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<QuestionYN> STEP_6_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

}
