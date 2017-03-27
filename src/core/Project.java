/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

/**
 *
 * @author ltrask
 */
public class Project {

    public static final int NUM_STEPS = 6;

    public static final int[] NUM_SUB_STEPS = {11, 5, 5, 4, 4, 5};

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty agency = new SimpleStringProperty();
    private final SimpleStringProperty analyst = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty limits = new SimpleStringProperty();
    private final SimpleStringProperty urlLink = new SimpleStringProperty();

    private File saveFile = null;

    private final Step[] steps;

    private final QuestionGenerator qGen;

    private final GoalNeedsMatrix gnMat;

    private final FeasibilityMatrix feasMat;

    private final StakeholderMatrix stakeMat;

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
        stakeMat = new StakeholderMatrix(qGen.qStakeholderOptionList, qGen.qFeasYNList);
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

    public Project.Step getStep(int index) {
        return steps[index];
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

    public ObservableList<QuestionYN> getAppWizardQs() {
        return qGen.getAppWizardQs();
    }

    public int getNumAppWizardQs() {
        return qGen.getAppWizardQCount();
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
                subSteps[subStepIdx] = new SubStep("Step " + String.valueOf(stepIdx + 1) + "." + String.valueOf(subStepIdx + 1));
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

        public SubStep(String stepName) {
            this.stepName = stepName;
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

    }

    public static final int GOAL_WIZARD_SUMMARY_INDEX = 4; // Step 1
    public static final int FEAS_WIZARD_SUMMARY_INDEX = 6; // Step 1
    public static final int STAKEHOLDER_WIZARD_SUMMARY_INDEX = 8;  // Step 1
    public static final int APP_WIZARD_SUMMARY_INDEX = 2; // Step 2

    public static final String[][] STEP_NAMES = {
        {"Step 1", "WZ Metadata", "User Needs", "User Needs Support", "Major Goals", "Goal Wizard Summary", "Feasibility", "Feasibility Wizard Summary", "Stakeholders", "Stakeholders Wizard Summary", "Team Members", "ITS Resources"},
        {"Step 2", "Initial Applications", "Application Wizard", "Benefits", "Costs", "Institutional/Jurisdictional", "Legal/Policy", "Stakeholder Buy-In", "Develop Concept of Operations"},
        {"Step 3", "Document Concept of Operations", "Requirements", "System Design", "Testing Strategy", "Ops & Maintenance", "Staff Training Needs", "Public Outreach", "System Security", "Evaluation", "Benefity/Cost"},
        {"Step 4", "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"},
        {"Step 5", "Implementing System Plans", "Scheduling Decisions", "System Acceptance Testing", "Handling Deployment Issues"},
        {"Step 6", "Changin Work Zone", "Using/Sharing ITS Info", "Maintaining Adequate Staff", "Leveraging Public Support", "System Monitoring/Evaluation"}
    };

}
