/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class Project {

    public static final int NUM_STEPS = 6;

    public static final int[] NUM_SUB_STEPS = {11, 8, 8, 4, 4, 5};

    // Project information
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty agency = new SimpleStringProperty();
    private final SimpleStringProperty analyst = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty limits = new SimpleStringProperty();
    private final SimpleStringProperty urlLink = new SimpleStringProperty();

    // Work Zone Metadata
    private final StringProperty functionalClass = new SimpleStringProperty();
    private final StringProperty wzType = new SimpleStringProperty();
    private final StringProperty maintainingAgency = new SimpleStringProperty();
    private final StringProperty patrollingAgency = new SimpleStringProperty();
    private final IntegerProperty aadt = new SimpleIntegerProperty();
    private final FloatProperty wzLength = new SimpleFloatProperty();
    private final IntegerProperty numRoadwayLanes = new SimpleIntegerProperty();
    private final FloatProperty shoulderWidth = new SimpleFloatProperty();
    private final IntegerProperty speedLimit = new SimpleIntegerProperty();
    private final IntegerProperty numLanesClosed = new SimpleIntegerProperty();
    private final IntegerProperty activityDuration = new SimpleIntegerProperty();
    private final BooleanProperty crashDataAvailable = new SimpleBooleanProperty();

    private File saveFile = null;

    private final Step[] steps;

    private final QuestionGenerator qGen;

    private final GoalNeedsMatrix gnMat;

    private final FeasibilityMatrix feasMat;

    private final StakeholderMatrix stakeMat;

    private final ApplicationMatrix appMat;

    public Project() {
        this("New Project", null);
    }

    public Project(String projName) {
        this(projName, null);
    }

    public Project(String projName, File saveFile) {
        name.set(projName);
        this.saveFile = saveFile;
        steps = new Step[NUM_STEPS];
        for (int stepIdx = 0; stepIdx < NUM_STEPS; stepIdx++) {
            steps[stepIdx] = new Step(this, stepIdx, NUM_SUB_STEPS[stepIdx]);
        }
        qGen = new QuestionGenerator(this);
        gnMat = new GoalNeedsMatrix(qGen.getGoalWizardQs(), Need.GOAL_WIZARD_NEEDS_LIST, qGen.qMajorGoalsList);
        feasMat = new FeasibilityMatrix(qGen.qFeasOptionList, qGen.qFeasYNList);
        stakeMat = new StakeholderMatrix(this, qGen.qStakeholderOptionList, qGen.qStakeholderYNList);
        appMat = new ApplicationMatrix(qGen.qApplicationList);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        this.name.set(newName);
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public SimpleStringProperty getAgencyProperty() {
        return agency;
    }

    public SimpleStringProperty getAnalystProperty() {
        return analyst;
    }

    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public SimpleStringProperty getLimitsProperty() {
        return limits;
    }

    public SimpleStringProperty getUrlLinkProperty() {
        return urlLink;
    }

    public String getFunctionalClass() {
        return functionalClass.get();
    }

    public void setFunctionalClass(String value) {
        functionalClass.set(value);
    }

    public StringProperty functionalClassProperty() {
        return functionalClass;
    }

    public String getMaintainingAgency() {
        return maintainingAgency.get();
    }

    public void setMaintainingAgency(String value) {
        maintainingAgency.set(value);
    }

    public StringProperty maintainingAgencyProperty() {
        return maintainingAgency;
    }

    public String getPatrollingAgency() {
        return patrollingAgency.get();
    }

    public void setPatrollingAgency(String value) {
        patrollingAgency.set(value);
    }

    public StringProperty patrollingAgencyProperty() {
        return patrollingAgency;
    }

    public int getAadt() {
        return aadt.get();
    }

    public void setAadt(int value) {
        aadt.set(value);
    }

    public IntegerProperty aadtProperty() {
        return aadt;
    }

    public float getWzLength() {
        return wzLength.get();
    }

    public void setWzLength(float value) {
        wzLength.set(value);
    }

    public FloatProperty wzLengthProperty() {
        return wzLength;
    }

    public String getWzType() {
        return wzType.get();
    }

    public void setWzType(String value) {
        wzType.set(value);
    }

    public StringProperty wzTypeProperty() {
        return wzType;
    }

    public int getNumRoadwayLanes() {
        return numRoadwayLanes.get();
    }

    public void setNumRoadwayLanes(int value) {
        numRoadwayLanes.set(value);
    }

    public IntegerProperty numRoadwayLanesProperty() {
        return numRoadwayLanes;
    }

    public float getShoulderWidth() {
        return shoulderWidth.get();
    }

    public void setShoulderWidth(float value) {
        shoulderWidth.set(value);
    }

    public FloatProperty shoulderWidthProperty() {
        return shoulderWidth;
    }

    public int getSpeedLimit() {
        return speedLimit.get();
    }

    public void setSpeedLimit(int value) {
        speedLimit.set(value);
    }

    public IntegerProperty speedLimitProperty() {
        return speedLimit;
    }

    public int getNumLanesClosed() {
        return numLanesClosed.get();
    }

    public void setNumLanesClosed(int value) {
        numLanesClosed.set(value);
    }

    public IntegerProperty numLanesClosedProperty() {
        return numLanesClosed;
    }

    public int getActivityDuration() {
        return activityDuration.get();
    }

    public void setActivityDuration(int value) {
        activityDuration.set(value);
    }

    public IntegerProperty activityDurationProperty() {
        return activityDuration;
    }

    public boolean isCrashDataAvailable() {
        return crashDataAvailable.get();
    }

    public void setCrashDataAvailable(boolean value) {
        crashDataAvailable.set(value);
    }

    public BooleanProperty crashDataAvailableProperty() {
        return crashDataAvailable;
    }

    public Project.Step getStep(int index) {
        return steps[index];
    }

    public QuestionGenerator getQGen() {
        return qGen;
    }

    public ObservableList<QuestionYN> getGoalWizardQs() {
        return qGen.getGoalWizardQs();
    }

    public int getNumGoalWizardQs() {
        return qGen.getGoalWizardQCount();
    }

    public ObservableList<QuestionYN> getUNSupportQs() {
        return qGen.qUNSupportList;
    }

    public int getNumUNSupportQs() {
        return qGen.qUNSupportList.size();
    }

    public ObservableList<QuestionYN> getMajorGoalsQs() {
        return qGen.qMajorGoalsList;
    }

    public int getNumMajorGoalsQs() {
        return qGen.qMajorGoalsList.size();
    }

    public GoalNeedsMatrix getGoalNeedsMatrix() {
        return gnMat;
    }

    public ObservableList<QuestionYN> getFeasWizardYNQs() {
        return qGen.qFeasYNList;
    }

    public int getNumFeasWizardYNQs() {
        return qGen.qFeasYNList.size();
    }

    public ObservableList<QuestionOption> getFeasWizardOptQs() {
        return qGen.qFeasOptionList;
    }

    public int getNumFeasWizardOptQs() {
        return qGen.qFeasOptionList.size();
    }

    public FeasibilityMatrix getFeasibilityMatrix() {
        return feasMat;
    }

    public ObservableList<QuestionYN> getStakeWizardYNQs() {
        return qGen.qStakeholderYNList;
    }

    public int getNumStakeWizardYNQs() {
        return qGen.qStakeholderYNList.size();
    }

    public ObservableList<QuestionOption> getStakeWizardOptQs() {
        return qGen.qStakeholderOptionList;
    }

    public int getNumStakeWizardOptQs() {
        return qGen.qStakeholderOptionList.size();
    }

    public StakeholderMatrix getStakeholderMatrix() {
        return stakeMat;
    }

    public ObservableList<QuestionYN> getITSResourcesQs() {
        return qGen.qITSResourcesList;
    }

    public int getNumITSResourcesQs() {
        return qGen.qITSResourcesList.size();
    }

    public ObservableList<QuestionYN> getAppWizardQs() {
        return qGen.getAppWizardQs();
    }

    public int getNumAppWizardQs() {
        return qGen.getAppWizardQCount();
    }

    public ApplicationMatrix getApplicationMatrix() {
        return appMat;
    }

    @Override
    public String toString() {
        return name.get();
    }

    public static class Step {

        private final Project proj;

        private final String stepName;

        private final int stepIdx;

        private final int numSubSteps;

        private String description;

        private final SimpleBooleanProperty stepStarted = new SimpleBooleanProperty(false);

        private final SimpleBooleanProperty stepFinished = new SimpleBooleanProperty(false);

        private final SubStep[] subSteps;

        public Step(Project project, int index, int numSubSteps) {
            this.proj = project;
            this.stepName = "Step " + String.valueOf(index + 1);
            this.stepIdx = index;
            this.numSubSteps = numSubSteps;
            subSteps = new SubStep[numSubSteps];
            for (int subStepIdx = 0; subStepIdx < numSubSteps; subStepIdx++) {
                subSteps[subStepIdx] = new SubStep(Project.STEP_NAMES[stepIdx][subStepIdx + 1], // "Step " + String.valueOf(stepIdx + 1) + "." + String.valueOf(subStepIdx + 1)
                        Project.checkSubStepIsWizardSummary(stepIdx, subStepIdx));
            }
            subSteps[subSteps.length - 1].stepFinishedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) {
                    if (newVal) {
                        proj.setStepComplete(stepIdx, true);
                    }
                }
            });
        }

        public String getName() {
            return this.stepName;
        }

        @Override
        public String toString() {
            return getName();
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }

        public boolean isStepStarted() {
            return stepStarted.get();
        }

        public void setStepStarted(boolean stepStarted) {
            this.stepStarted.set(stepStarted);
        }

        public SimpleBooleanProperty stepStartedProperty() {
            return stepStarted;
        }

        public boolean isStepFinished() {
            return stepFinished.get();
        }

        public void setStepFinished(boolean stepFinished) {
            this.stepFinished.set(stepFinished);
        }

        public SimpleBooleanProperty stepFinishedProperty() {
            return stepFinished;
        }

        public Project.SubStep getSubStep(int subStepIdx) {
            return this.subSteps[subStepIdx];
        }

        public int getNumSubSteps() {
            return this.numSubSteps;
        }

    }

    public boolean isStepComplete(int stepIdx) {
        if (stepIdx < 0) {
            return true;
        } else if (stepIdx >= steps.length) {
            return false;
        }
        return steps[stepIdx].isStepFinished();
    }

    public void setStepComplete(int stepIdx, boolean val) {
        if (stepIdx >= 0 && stepIdx < steps.length) {
            steps[stepIdx].setStepFinished(val);
        } else {
            System.out.println("Invalid step index");
        }
    }

    public boolean isStepStarted(int stepIdx) {
        return steps[stepIdx].isStepStarted();
    }

    public void setStepStarted(int stepIdx, boolean val) {
        if (stepIdx >= 0 && stepIdx < steps.length) {
            steps[stepIdx].setStepStarted(val);
        } else {
            System.out.println("Invalid step index");
        }
    }

    public boolean isSubStepStarted(int stepIdx, int subStepIdx) {
        if (subStepIdx < 0) {
            return true;
        }
        if (subStepIdx >= NUM_SUB_STEPS[stepIdx]) {
            return true;
        }
        return steps[stepIdx].getSubStep(subStepIdx).isStepStarted();
    }

    public void setSubStepStarted(int stepIdx, int subStepIdx, boolean val) {
        if (stepIdx >= 0 && stepIdx < steps.length) {
            if (subStepIdx >= 0 && subStepIdx < steps[stepIdx].getNumSubSteps()) {
                steps[stepIdx].getSubStep(subStepIdx).setStepStarted(val);
            } else {
                System.out.println("Invalid substep index");
            }
        } else {
            System.out.println("Invalid step index");
        }
    }

    public boolean isSubStepFinished(int stepIdx, int subStepIdx) {
        if (stepIdx < 0 || subStepIdx < 0) {
            return true;
        }
        if (subStepIdx >= NUM_SUB_STEPS[stepIdx]) {
            return true;
        }
        return steps[stepIdx].getSubStep(subStepIdx).isStepFinished();
    }

    public void setSubStepComplete(int stepIdx, int subStepIdx, boolean val) {
        if (stepIdx >= 0 && stepIdx < steps.length) {
            if (subStepIdx >= 0 && subStepIdx < steps[stepIdx].getNumSubSteps()) {
                steps[stepIdx].getSubStep(subStepIdx).setStepFinished(val);
            } else {
                System.out.println("Invalid substep index");
            }
        } else {
            System.out.println("Invalid step index");
        }
    }

    public static class SubStep {

        private final String stepName;

        private String description;

        private final SimpleBooleanProperty stepStarted = new SimpleBooleanProperty(true);

        private final SimpleBooleanProperty stepFinished = new SimpleBooleanProperty(false);

        private final SimpleBooleanProperty wizardSummary = new SimpleBooleanProperty(false);

        private final DoubleProperty subStepProgress = new SimpleDoubleProperty();

        public SubStep(String stepName) {
            this(stepName, false);
        }

        public SubStep(String stepName, boolean isWizardSummary) {
            this.stepName = stepName;
            wizardSummary.set(isWizardSummary);
        }

        public String getName() {
            return this.stepName;
        }

        @Override
        public String toString() {
            return getName();
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }

        public boolean isStepStarted() {
            return stepStarted.get();
        }

        public void setStepStarted(boolean stepStarted) {
            this.stepStarted.set(stepStarted);
        }

        public SimpleBooleanProperty stepStartedProperty() {
            return stepStarted;
        }

        public boolean isStepFinished() {
            return stepFinished.get();
        }

        public void setStepFinished(boolean stepFinished) {
            this.stepFinished.set(stepFinished);
        }

        public SimpleBooleanProperty stepFinishedProperty() {
            return stepFinished;
        }

        public boolean isWizardSummary() {
            return wizardSummary.get();
        }

        public double getSubStepProgress() {
            return subStepProgress.get();
        }

        public void setSubStepProgress(double value) {
            subStepProgress.set(value);
        }

        public DoubleProperty subStepProgressProperty() {
            return subStepProgress;
        }

    }

    public static boolean checkSubStepIsWizardSummary(int stepIdx, int subStepIdx) {
        switch (stepIdx) {
            case 0:
                return subStepIdx == GOAL_WIZARD_SUMMARY_INDEX || subStepIdx == FEAS_WIZARD_SUMMARY_INDEX || subStepIdx == STAKEHOLDER_WIZARD_SUMMARY_INDEX;
            case 1:
                return subStepIdx == APP_WIZARD_SUMMARY_INDEX;
            default:
                return false;
        }
    }

    public final SimpleDoubleProperty progressInfo = new SimpleDoubleProperty(1.0);
    public final SimpleDoubleProperty progressGoal = new SimpleDoubleProperty(0.0);
    public final SimpleDoubleProperty progressFeas = new SimpleDoubleProperty(0.0);
    public final SimpleDoubleProperty progressApp = new SimpleDoubleProperty(0.0);
    public final SimpleDoubleProperty progressStake = new SimpleDoubleProperty(0.0);

    public static final int GOAL_WIZARD_SUMMARY_INDEX = 4; // Step 1
    public static final int FEAS_WIZARD_SUMMARY_INDEX = 6; // Step 1
    public static final int STAKEHOLDER_WIZARD_SUMMARY_INDEX = 8;  // Step 1
    public static final int TEAM_SUMMARY_INDEX = 9; // Step 1
    public static final int APP_WIZARD_SUMMARY_INDEX = 1; // Step 2

    public static final String[][] STEP_NAMES = {
        {"Step 1", "WZ Metadata", "User Needs", "User Needs Support", "Major Goals", "Goal Wizard", "Feasibility", "Feasibility Wizard", "Stakeholders", "Stakeholders Wizard & Member Selection", "Team Members", "ITS Resources"},
        {"Step 2", "Initial Applications", "Application Wizard", "Benefits", "Costs", "Institutional/Jurisdictional", "Legal/Policy", "Stakeholder Buy-In", "Develop Concept of Operations"},
        //{"Step 3", "Document Concept of Operations", "Requirements", "System Design", "Testing Strategy", "Ops & Maintenance", "Staff Training Needs", "Public Outreach", "System Security", "Evaluation", "Benefity/Cost"},
        {"Step 3", "Document Concept of Operations", "Requirements", "Testing Strategy", "Ops & Maintenance", "Staff Training Needs", "System Security", "Evaluation", "Benefity/Cost"},
        {"Step 4", "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"},
        {"Step 5", "Implementing System Plans", "Scheduling Decisions", "System Acceptance Testing", "Handling Deployment Issues"},
        {"Step 6", "Changin Work Zone", "Using/Sharing ITS Info", "Maintaining Adequate Staff", "Leveraging Public Support", "System Monitoring/Evaluation"}
    };

}
