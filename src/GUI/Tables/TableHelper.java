/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Application;
import core.UserGoal;
import core.Need;
import core.Question;
import core.QuestionYN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class TableHelper {

    public static ObservableList<Question> getStepQuestions(int stepIndex) {
        switch (stepIndex) {
            default:
            case 0:
                return getStep1Questions();
            case 1:
                return getStep2Questions();
        }
    }

    private static ObservableList<Question> getStep1Questions() {
        return STEP_1_GOAL_QLIST;
    }

    private static ObservableList<Question> getStep2Questions() {
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

    private static final ObservableList<Question> STEP_1_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_1_GOAL_QLIST = FXCollections.observableArrayList(new UserGoal(1, "Do you expect congestion impacts to be noticable to drivers?"),
            new UserGoal(2, "Is driver diversion expected onto alternate routes?"),
            new UserGoal(3, "Do you anticipate significant queuing as a result of this work zone?"),
            new UserGoal(4, "Will this work zone have reduced lane widths?"),
            new UserGoal(5, "Will this work zone have reduced sight distance impact?"),
            new UserGoal(6, "Will transit vehicles need to travel through the work zone?"),
            new UserGoal(7, "Are you using rapid-set concrete mix?"),
            new UserGoal(8, "Will construction vehicles access site from travel lanes?"),
            new UserGoal(9, "Are there specific agency policies for work zones as required by the WZ safety and mobility rule?"),
            new UserGoal(10, "Does the agency have existing performance targets for work zone?"),
            new UserGoal(11, "Will outreach and traveler information be used for this work zone?"),
            new UserGoal(12, "Are there additional data needed before and during construction?"),
            new UserGoal(13, "Will the work zone result in the closure of emergency shoulders (to where breakdowns can't be accomodated)?"),
            new UserGoal(14, "Will speed imits in the work zone be lowered compared to base conditions?"),
            new UserGoal(15, "Does state law allow use of autmoated speed enforcement in WZs?"),
            new UserGoal(16, "Is there a traveler information goal?"),
            new UserGoal(17, "Will there be a high volume of constructions vehicles requiring to access the work zone?"),
            new UserGoal(18, "Will ramp geometry under WZ conditions constrain acceleration lanes?"),
            new UserGoal(19, "Will work zone activities disable ramp meters (if applicable)?"),
            new UserGoal(20, "Will the WZ be included in the federally-mandated biannual process review?")
    );

    private static final ObservableList<Question> STEP_1_UN_SUP_QLIST = FXCollections.observableArrayList(new UserGoal(1, "Are there existing sensors, closed-circuit TV camveras, CMS, or travel time data?"),
            new UserGoal(2, "Are probe data available for the work zone?"),
            new UserGoal(3, "Are crash data available, and how current are those data?"),
            new UserGoal(4, "Are there communications systems available?"),
            new UserGoal(5, "Are there computer options available?"),
            new UserGoal(6, "Is there software available?")
    );

    private static final ObservableList<Question> STEP_1_ITS_RESOURCES = FXCollections.observableArrayList(new UserGoal(1, "Are technologies to communicate with drivers available?"),
            new UserGoal(2, "Are there weather monitoring stations along the work zone?"),
            new UserGoal(3, "Is there a local, regional or state TMC that monitors the roadway?"),
            new UserGoal(4, "Is there an existing website or traveler information system?"),
            new UserGoal(5, "Do you have ITS on-call contracts?"),
            new UserGoal(6, "Do you have access to leased or temporary ITS?")
    );

    private static final ObservableList<Question> STEP_1_SYSTEM_GOALS = FXCollections.observableArrayList(new UserGoal(1, "Is there a mobility goal?"),
            new UserGoal(2, "Is there a safety goal?"),
            new UserGoal(3, "Is there a productivity goal?"),
            new UserGoal(4, "Is there a regulatory goal?"),
            new UserGoal(5, "Is there a traveler information goal?")
    );

    private static final ObservableList<Question> STEP_2_APP_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(2, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(3, Question.GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"),
            new QuestionYN(4, Question.GOAL_MOBILITY, "Will this work zone be active during the day?"),
            new QuestionYN(5, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"),
            new QuestionYN(6, Question.GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"),
            new QuestionYN(7, Question.GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"),
            new QuestionYN(8, Question.GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"),
            new QuestionYN(9, Question.GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"),
            new QuestionYN(10, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(11, Question.GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"),
            new QuestionYN(12, Question.GOAL_SAFETY, "Do you expect congestion impacts to be difficult to realized by drivers?"),
            new QuestionYN(13, Question.GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"),
            new QuestionYN(14, Question.GOAL_SAFETY, "Does the corridor have a frequent crash problem?"),
            new QuestionYN(15, Question.GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(16, Question.GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"),
            new QuestionYN(17, Question.GOAL_PROD, "Will vehicles access site from travel lanes?"),
            new QuestionYN(18, Question.GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"),
            new QuestionYN(19, Question.GOAL_PROD, "Will there be a high volume of construction vehicles?"),
            new QuestionYN(20, Question.GOAL_PROD, "Will existing equipment be used for the WZ?"),
            new QuestionYN(21, Question.GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"),
            new QuestionYN(22, Question.GOAL_REG, "Is automated enforcement legal in your state?"),
            new QuestionYN(23, Question.GOAL_REG, "Are there specific agency policies for work zones?"),
            new QuestionYN(24, Question.GOAL_REG, "Does the agency have existing performance targets for work zone?"),
            new QuestionYN(25, Question.GOAL_REG, "Is there a mobility goal?"),
            new QuestionYN(26, Question.GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"),
            new QuestionYN(27, Question.GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?")
    );

    private static final ObservableList<Question> STEP_3_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_4_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_5_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_6_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, Question.GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

}
