/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author ltrask
 */
public class Project implements Serializable {

    private final long serialVersionUID = 123456789L;

    public static final int NUM_STEPS = 6;

    public static final int[] NUM_SUB_STEPS = {10, 8, 8, 4, 4, 5};

    // Project information
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty agency = new SimpleStringProperty();
    private SimpleStringProperty analyst = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty limits = new SimpleStringProperty();
    private SimpleStringProperty urlLink = new SimpleStringProperty();
    private ObjectProperty<Image> projPhoto = new SimpleObjectProperty<>();

    // Work Zone Metadata
    private StringProperty functionalClass = new SimpleStringProperty();
    private StringProperty wzType = new SimpleStringProperty();
    private StringProperty maintainingAgency = new SimpleStringProperty();
    private StringProperty patrollingAgency = new SimpleStringProperty();
    private IntegerProperty aadt = new SimpleIntegerProperty(50000);
    private DoubleProperty wzLength = new SimpleDoubleProperty(2.0);
    private IntegerProperty numRoadwayLanes = new SimpleIntegerProperty(3);
    private DoubleProperty shoulderWidth = new SimpleDoubleProperty(10);
    private IntegerProperty speedLimit = new SimpleIntegerProperty(55);
    private IntegerProperty numLanesClosed = new SimpleIntegerProperty(1);
    private IntegerProperty activityDuration = new SimpleIntegerProperty(6);

    // Other Properties
    private BooleanProperty crashDataAvailable = new SimpleBooleanProperty();
    private BooleanProperty automatedEnforcementAllowed = new SimpleBooleanProperty();

    private File saveFile = null;

    private Step[] steps;

    private QuestionGenerator qGen;

    private GoalNeedsMatrix gnMat;

    private FeasibilityMatrix feasMat;

    private StakeholderMatrix stakeMat;

    private ApplicationMatrix appMat;

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
        appMat = new ApplicationMatrix(this, qGen.qApplicationList);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        this.name.set(newName);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty agencyProperty() {
        return agency;
    }

    public String getAgency() {
        return agency.get();
    }

    public void setAgency(String newAgency) {
        this.agency.set(newAgency);
    }

    public SimpleStringProperty analystProperty() {
        return analyst;
    }

    public String getAnalyst() {
        return analyst.get();
    }

    public void setAnalyst(String newAnalyst) {
        this.analyst.set(newAnalyst);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String newDesc) {
        this.description.set(newDesc);
    }

    public SimpleStringProperty limitsProperty() {
        return limits;
    }

    public String getLimits() {
        return limits.get();
    }

    public void setLimits(String newLimits) {
        this.limits.set(newLimits);
    }

    public SimpleStringProperty urlLinkProperty() {
        return urlLink;
    }

    public String getUrlLink() {
        return urlLink.get();
    }

    public void setUrlLink(String newURL) {
        this.urlLink.set(newURL);
    }

    public Image getProjPhoto() {
        return projPhoto.get();
    }

    public void setProjPhoto(Image value) {
        projPhoto.set(value);
    }

    public ObjectProperty projPhotoProperty() {
        return projPhoto;
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

    public double getWzLength() {
        return wzLength.get();
    }

    public void setWzLength(double value) {
        wzLength.set(value);
    }

    public DoubleProperty wzLengthProperty() {
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

    public double getShoulderWidth() {
        return shoulderWidth.get();
    }

    public void setShoulderWidth(Double value) {
        shoulderWidth.set(value);
    }

    public DoubleProperty shoulderWidthProperty() {
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

    public boolean isAutomatedEnforcementAllowed() {
        return automatedEnforcementAllowed.get();
    }

    public void setAutomatedEnforcementAllowed(boolean value) {
        automatedEnforcementAllowed.set(value);
    }

    public BooleanProperty automatedEnforcementAllowedProperty() {
        return automatedEnforcementAllowed;
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

    public void setSaveFile(File file) {
        this.saveFile = file;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public String getDateString() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(today);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        //s.defaultWriteObject();
        s.writeInt(getAadt());
        s.writeInt(getActivityDuration());
        s.writeObject(getAgency());
        s.writeObject(getAnalyst());
        s.writeObject(getDescription());
        s.writeObject(getFunctionalClass());
        s.writeObject(getLimits());
        s.writeObject(getMaintainingAgency());
        s.writeObject(getName());
        s.writeInt(getNumLanesClosed());
        s.writeInt(getNumRoadwayLanes());
        s.writeObject(getPatrollingAgency());
        s.writeObject(getProjPhoto());
        s.writeObject(saveFile);
        s.writeDouble(getShoulderWidth());
        s.writeInt(getSpeedLimit());
        s.writeObject(getUrlLink());
        s.writeDouble(getWzLength());
        s.writeObject(getWzType());
        s.writeBoolean(isAutomatedEnforcementAllowed());
        s.writeBoolean(isCrashDataAvailable());

        s.writeDouble(progressApp.get());
        s.writeDouble(progressFeas.get());
        s.writeDouble(progressGoal.get());
        s.writeDouble(progressInfo.get());
        s.writeDouble(progressStake.get());

        s.writeObject(qGen);

        s.writeObject(getApplicationMatrix());
        //s.writeObject(getFeasibilityMatrix());
        s.writeObject(getGoalNeedsMatrix());
        s.writeObject(getStakeholderMatrix());

        //s.writeObject(steps);
    }

    public void setFromProject(Project proj) {

        setAadt(proj.getAadt());
        setActivityDuration(proj.getActivityDuration());
        setAgency(proj.getAgency());
        setAnalyst(proj.getAnalyst());
        setDescription(proj.getDescription());
        setFunctionalClass(proj.getFunctionalClass());
        setLimits(proj.getLimits());
        setMaintainingAgency(proj.getMaintainingAgency());
        setName(proj.getName());
        setNumLanesClosed(proj.getNumLanesClosed());
        setNumRoadwayLanes(proj.getNumRoadwayLanes());
        setPatrollingAgency(proj.getPatrollingAgency());
        setProjPhoto(proj.getProjPhoto());
        saveFile = proj.getSaveFile();
        setShoulderWidth(proj.getShoulderWidth());
        setSpeedLimit(proj.getSpeedLimit());
        setUrlLink(proj.getUrlLink());
        setWzLength(proj.getWzLength());
        setWzType(proj.getWzType());

        setAutomatedEnforcementAllowed(proj.isAutomatedEnforcementAllowed());
        setCrashDataAvailable(proj.isCrashDataAvailable());

        steps = new Step[NUM_STEPS];
        for (int stepIdx = 0; stepIdx < NUM_STEPS; stepIdx++) {
            steps[stepIdx] = new Step(this, stepIdx, NUM_SUB_STEPS[stepIdx]);
        }

        progressApp.set(proj.progressApp.get());
        progressFeas.set(proj.progressFeas.get());
        progressGoal.set(proj.progressGoal.get());
        progressInfo.set(proj.progressInfo.get());
        progressStake.set(proj.progressStake.get());

        qGen = new QuestionGenerator(proj.getQGen(), this);
        appMat = new ApplicationMatrix(proj.appMat, this);
        feasMat = new FeasibilityMatrix(proj.qGen.qFeasOptionList, proj.qGen.qFeasYNList);
        gnMat = new GoalNeedsMatrix(proj.getGoalNeedsMatrix(), qGen.qMajorGoalsList);
        stakeMat = new StakeholderMatrix(proj.getStakeholderMatrix(), this);

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        aadt = new SimpleIntegerProperty(s.readInt());
        activityDuration = new SimpleIntegerProperty(s.readInt());
        agency = new SimpleStringProperty((String) s.readObject());
        analyst = new SimpleStringProperty((String) s.readObject());
        description = new SimpleStringProperty((String) s.readObject());
        functionalClass = new SimpleStringProperty((String) s.readObject());
        limits = new SimpleStringProperty((String) s.readObject());
        maintainingAgency = new SimpleStringProperty((String) s.readObject());
        name = new SimpleStringProperty((String) s.readObject());
        numLanesClosed = new SimpleIntegerProperty(s.readInt());
        numRoadwayLanes = new SimpleIntegerProperty(s.readInt());
        patrollingAgency = new SimpleStringProperty((String) s.readObject());
        projPhoto = new SimpleObjectProperty<>((Image) s.readObject());
        saveFile = (File) s.readObject();
        shoulderWidth = new SimpleDoubleProperty(s.readDouble());
        speedLimit = new SimpleIntegerProperty(s.readInt());
        urlLink = new SimpleStringProperty((String) s.readObject());
        wzLength = new SimpleDoubleProperty(s.readDouble());
        wzType = new SimpleStringProperty((String) s.readObject());

        this.automatedEnforcementAllowed = new SimpleBooleanProperty(s.readBoolean());
        this.crashDataAvailable = new SimpleBooleanProperty(s.readBoolean());

        progressApp = new SimpleDoubleProperty(s.readDouble());
        progressFeas = new SimpleDoubleProperty(s.readDouble());
        progressGoal = new SimpleDoubleProperty(s.readDouble());
        progressInfo = new SimpleDoubleProperty(s.readDouble());
        progressStake = new SimpleDoubleProperty(s.readDouble());

        qGen = (QuestionGenerator) s.readObject();
        appMat = (ApplicationMatrix) s.readObject();
        //feasMat = (FeasibilityMatrix) s.readObject();
        gnMat = (GoalNeedsMatrix) s.readObject();
        stakeMat = (StakeholderMatrix) s.readObject();

//
//        Step[] tempArr = (Step[]) s.readObject();
//        System.arraycopy(tempArr, 0, steps, 0, tempArr.length);
        // Original Implementation
//        setAadt(s.readInt());
//        setActivityDuration(s.readInt());
//        setAgency((String) s.readObject());
//        setAnalyst((String) s.readObject());
//        setDescription((String) s.readObject());
//        setFunctionalClass((String) s.readObject());
//        setLimits((String) s.readObject());
//        setMaintainingAgency((String) s.readObject());
//        setName((String) s.readObject());
//        setPatrollingAgency((String) s.readObject());
//        saveFile = (File) s.readObject();
//        setShoulderWidth(s.readFloat());
//        setSpeedLimit(s.readInt());
//        setUrlLink((String) s.readObject());
//        setWzLength(s.readFloat());
//        setWzType((String) s.readObject());
//
//        setAutomatedEnforcementAllowed(s.readBoolean());
//        setCrashDataAvailable(s.readBoolean());
//        progressApp.set(s.readDouble());
//        progressFeas.set(s.readDouble());
//        progressGoal.set(s.readDouble());
//        progressInfo.set(s.readDouble());
//        progressStake.set(s.readDouble());
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

        private void writeObject(ObjectOutputStream s) throws IOException {
//            s.writeObject(getDescription());
//            s.writeInt(numSubSteps);
//            s.writeBoolean(isStepFinished());
//            s.writeInt(stepIdx);
//            s.writeObject(stepName);
//            s.writeBoolean(isStepStarted())
        }

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

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
            //System.out.println("Invalid step index");
        }
    }

    public boolean isStepStarted(int stepIdx) {
        return steps[stepIdx].isStepStarted();
    }

    public void setStepStarted(int stepIdx, boolean val) {
        if (stepIdx >= 0 && stepIdx < steps.length) {
            steps[stepIdx].setStepStarted(val);
        } else {
            //System.out.println("Invalid step index");
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
                //System.out.println("Invalid substep index");
            }
        } else {
            //System.out.println("Invalid step index");
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
                //System.out.println("Invalid substep index");
            }
        } else {
            //System.out.println("Invalid step index");
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

        private void writeObject(ObjectOutputStream s) throws IOException {
            //s.writeObject(getOption);
        }

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

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

    public SimpleDoubleProperty progressInfo = new SimpleDoubleProperty(1.0);
    public SimpleDoubleProperty progressGoal = new SimpleDoubleProperty(0.0);
    public SimpleDoubleProperty progressFeas = new SimpleDoubleProperty(0.0);
    public SimpleDoubleProperty progressApp = new SimpleDoubleProperty(0.0);
    public SimpleDoubleProperty progressStake = new SimpleDoubleProperty(0.0);

    public static final int GOAL_SELECTION_INDEX = 3;
    public static final int GOAL_WIZARD_SUMMARY_INDEX = 4; // Step 1
    public static final int FEAS_WIZARD_SUMMARY_INDEX = 5; // Step 1
    public static final int STAKEHOLDER_WIZARD_SUMMARY_INDEX = 7;  // Step 1
    public static final int TEAM_SUMMARY_INDEX = 8; // Step 1
    public static final int APP_WIZARD_SUMMARY_INDEX = 1; // Step 2

    public static final String[][] STEP_NAMES = {
        //{"Step 1", "WZ Metadata", "User Needs", "User Needs Support", "Major Goals", "Goal Wizard", "Feasibility", "Feasibility Wizard", "Stakeholders", "Stakeholders Wizard & Team Selection", "Team Members", "ITS Resources"},
        {"Step 1", "WZ Metadata", "User Needs", "User Needs Support", "Goals", "Goal Wizard", "Feasibility Wizard", "Stakeholders", "SH Wizard & Team Selection", "Team Members", "ITS Resources"},
        {"Step 2", "Initial Applications", "Application Wizard", "Benefits", "Costs", "Institutional/Jurisdictional", "Legal/Policy", "Stakeholder Buy-In", "Develop Concept of Operations"},
        //{"Step 3", "Document Concept of Operations", "Requirements", "System Design", "Testing Strategy", "Operations & Maintenance", "Staff Training Needs", "Public Outreach", "System Security", "Evaluation", "Benefit/Cost"},
        {"Step 3", "Document Concept of Operations", "Requirements", "Testing Strategy", "Operations & Maintenance", "Staff Training Needs", "System Security", "Evaluation", "Benefit/Cost"},
        {"Step 4", "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"},
        {"Step 5", "Implementing System Plans", "Scheduling Decisions", "System Acceptance Testing", "Handling Deployment Issues"},
        {"Step 6", "Changing Work Zone", "Using/Sharing ITS Info", "Maintaining Adequate Staff", "Leveraging Public Support", "System Monitoring/Evaluation"}
    };

}
