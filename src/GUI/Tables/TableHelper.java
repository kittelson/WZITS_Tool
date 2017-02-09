/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tables;

import core.Application;
import core.Question;
import core.QuestionYN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class TableHelper {
    
    public static final String GOAL_MOBILITY = "Mobility";
    public static final String GOAL_SAFETY = "Safety";
    public static final String GOAL_PROD = "Productivity";
    public static final String GOAL_REG = "Regulatory";
    public static final String GOAL_TRAVELER_INFO = "Traveler Information";
    
    
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
        return STEP_1_QLIST;
    }

    private static ObservableList<Question> getStep2Questions() {
        return STEP_2_QLIST;
    }

    public static int getNumberOfQuestionsByStep(int stepIdx) {
        switch (stepIdx) {
            default:
                return 0;
            case 0:
                return STEP_1_QLIST.size();
            case 1:
                return STEP_2_QLIST.size();
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
        return FXCollections.observableArrayList(new Application("Queue Warning", 5));
    }

    private static final ObservableList<Question> STEP_1_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_2_QLIST = FXCollections.observableArrayList(
            new QuestionYN(1, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(2, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?"),
            new QuestionYN(3, GOAL_MOBILITY, "Will this work zone involve peak-hour lane closures?"),
            new QuestionYN(4, GOAL_MOBILITY, "Will this work zone be active during the day?"),
            new QuestionYN(5, GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during peak periods?"),
            new QuestionYN(6, GOAL_MOBILITY, "Do you expect the work zone to result in v/c greater than 1.0 during off-peak periods?"),
            new QuestionYN(7, GOAL_MOBILITY, "Do you anticipate significant queuing as a result of this work zone?"),
            new QuestionYN(8, GOAL_MOBILITY, "Will lower speed limits be advised in the work zone?"),
            new QuestionYN(9, GOAL_MOBILITY, "Will work zone activities disable ramp meters (Select No if not applicable)?"),
            new QuestionYN(10, GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(11, GOAL_SAFETY, "Will the work zone result in closure of emergency shoulders?"),
            new QuestionYN(12, GOAL_SAFETY, "Do you expect congestion impacts to be difficult to realized by drivers?"),
            new QuestionYN(13, GOAL_SAFETY, "Is the work zone located on an emergency response corridor?"),
            new QuestionYN(14, GOAL_SAFETY, "Does the corridor have a frequent crash problem?"),
            new QuestionYN(15, GOAL_SAFETY, "Will this work zone have reduced lane widths or reduced sight distance impact?"),
            new QuestionYN(16, GOAL_SAFETY, "Will temporary ramp geometry constrain acceleration lanes?"),
            new QuestionYN(17, GOAL_PROD, "Will vehicles access site from travel lanes?"),
            new QuestionYN(18, GOAL_PROD, "Are there access points with vertical or horizontal sight distance restrictions?"),
            new QuestionYN(19, GOAL_PROD, "Will there be a high volume of construction vehicles?"),
            new QuestionYN(20, GOAL_PROD, "Will existing equipment be used for the WZ?"),
            new QuestionYN(21, GOAL_PROD, "Will any exisiting ITS devices be incorporated into the SWZ?"),
            new QuestionYN(22, GOAL_REG, "Is automated enforcement legal in your state?"),
            new QuestionYN(23, GOAL_REG, "Are there specific agency policies for work zones?"),
            new QuestionYN(24, GOAL_REG, "Does the agency have existing performance targets for work zone?"),
            new QuestionYN(25, GOAL_REG, "Is there a mobility goal?"),
            new QuestionYN(26, GOAL_REG, "Will the work zone be included in the federally-mandated biannual process review?"),
            new QuestionYN(27, GOAL_TRAVELER_INFO, "Will outreach and traveler information be used for this work zone?")
    );

    private static final ObservableList<Question> STEP_3_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_4_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_5_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );

    private static final ObservableList<Question> STEP_6_QLIST = FXCollections.observableArrayList(
            new QuestionYN(0, GOAL_MOBILITY, "Will this work zone involve off-peak lane closures?")
    );


    

}


