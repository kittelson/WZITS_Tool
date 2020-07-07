/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.image.BufferedImage;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author ltrask
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 123456789L;

    public static final int NUM_STEPS = 6;

    public static final int[] NUM_SUB_STEPS = {9, 8, 8, 4, 4, 5};

    // Project information
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty agency = new SimpleStringProperty();
    private SimpleStringProperty analyst = new SimpleStringProperty();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleStringProperty limits = new SimpleStringProperty();
    private SimpleStringProperty urlLink = new SimpleStringProperty();
    private ObjectProperty<Image> projPhoto = new SimpleObjectProperty<>();
    private ObjectProperty<Image> conOpsDiagram = new SimpleObjectProperty();

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

    public Image getConOpsDiagram() {
        return conOpsDiagram.get();
    }

    public void setConOpsDiagram(Image value) {
        conOpsDiagram.set(value);
    }

    public ObjectProperty conOpsDiagramProperty() {
        return conOpsDiagram;
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
    private StringProperty areaType = new SimpleStringProperty();

    public String getAreaType() {
        return areaType.get();
    }

    public void setAreaType(String value) {
        areaType.set(value);
    }

    public StringProperty areaTypeProperty() {
        return areaType;
    }
    private StringProperty signalizedCorridor = new SimpleStringProperty();

    public String getSignalizedCorridor() {
        return signalizedCorridor.get();
    }

    public void setSignalizedCorridor(String value) {
        signalizedCorridor.set(value);
    }

    public StringProperty signalizedCorridorProperty() {
        return signalizedCorridor;
    }
    private StringProperty nationalHighwaySystem = new SimpleStringProperty();

    public String getNationalHighwaySystem() {
        return nationalHighwaySystem.get();
    }

    public void setNationalHighwaySystem(String value) {
        nationalHighwaySystem.set(value);
    }

    public StringProperty nationalHighwaySystemProperty() {
        return nationalHighwaySystem;
    }
    private StringProperty shoulderClosure = new SimpleStringProperty();

    public String getShoulderClosure() {
        return shoulderClosure.get();
    }

    public void setShoulderClosure(String value) {
        shoulderClosure.set(value);
    }

    public StringProperty shoulderClosureProperty() {
        return shoulderClosure;
    }
    private StringProperty federalAid = new SimpleStringProperty();

    public String getFederalAid() {
        return federalAid.get();
    }

    public void setFederalAid(String value) {
        federalAid.set(value);
    }

    public StringProperty federalAidProperty() {
        return federalAid;
    }

    private IntegerProperty wzSpeedLimit = new SimpleIntegerProperty(45);
    private IntegerProperty futureIntProp2 = new SimpleIntegerProperty();
    private IntegerProperty futureIntProp3 = new SimpleIntegerProperty();
    private IntegerProperty futureIntProp4 = new SimpleIntegerProperty();
    private IntegerProperty futureIntProp5 = new SimpleIntegerProperty();
    private DoubleProperty laneWidthBase = new SimpleDoubleProperty(12.0);
    private DoubleProperty laneWidthWZ = new SimpleDoubleProperty(12.0);
    private DoubleProperty futureDoubleProp3 = new SimpleDoubleProperty();
    private DoubleProperty futureDoubleProp4 = new SimpleDoubleProperty();
    private DoubleProperty futureDoubleProp5 = new SimpleDoubleProperty();

    public int getWzSpeedLimit() {
        return wzSpeedLimit.get();
    }

    public void setWzSpeedLimit(int value) {
        wzSpeedLimit.set(value);
    }

    public IntegerProperty wzSpeedLimitProperty() {
        return wzSpeedLimit;
    }

    public double getLaneWidthBase() {
        return laneWidthBase.get();
    }

    public void setLaneWidthBase(double value) {
        laneWidthBase.set(value);
    }

    public DoubleProperty laneWidthBaseProperty() {
        return laneWidthBase;
    }

    public double getLaneWidthWZ() {
        return laneWidthWZ.get();
    }

    public void setLaneWidthWZ(double value) {
        laneWidthWZ.set(value);
    }

    public DoubleProperty laneWidthWZProperty() {
        return laneWidthWZ;
    }
    private StringProperty fcrComment = new SimpleStringProperty();

    public String getFcrComment() {
        return fcrComment.get();
    }

    public void setFcrComment(String value) {
        fcrComment.set(value);
    }

    public StringProperty fcrCommentProperty() {
        return fcrComment;
    }
    private StringProperty maComment = new SimpleStringProperty();

    public String getMaComment() {
        return maComment.get();
    }

    public void setMaComment(String value) {
        maComment.set(value);
    }

    public StringProperty maCommentProperty() {
        return maComment;
    }
    private StringProperty atComment = new SimpleStringProperty();

    public String getAtComment() {
        return atComment.get();
    }

    public void setAtComment(String value) {
        atComment.set(value);
    }

    public StringProperty atCommentProperty() {
        return atComment;
    }
    private StringProperty aadtComment = new SimpleStringProperty();

    public String getAadtComment() {
        return aadtComment.get();
    }

    public void setAadtComment(String value) {
        aadtComment.set(value);
    }

    public StringProperty aadtCommentProperty() {
        return aadtComment;
    }
    private StringProperty nrlComment = new SimpleStringProperty();

    public String getNrlComment() {
        return nrlComment.get();
    }

    public void setNrlComment(String value) {
        nrlComment.set(value);
    }

    public StringProperty nrlCommentProperty() {
        return nrlComment;
    }
    private StringProperty swComment = new SimpleStringProperty();

    public String getSwComment() {
        return swComment.get();
    }

    public void setSwComment(String value) {
        swComment.set(value);
    }

    public StringProperty swCommentProperty() {
        return swComment;
    }
    private StringProperty pslComment = new SimpleStringProperty();

    public String getPslComment() {
        return pslComment.get();
    }

    public void setPslComment(String value) {
        pslComment.set(value);
    }

    public StringProperty pslCommentProperty() {
        return pslComment;
    }
    private StringProperty scComment = new SimpleStringProperty();

    public String getScComment() {
        return scComment.get();
    }

    public void setScComment(String value) {
        scComment.set(value);
    }

    public StringProperty scCommentProperty() {
        return scComment;
    }
    private StringProperty nhsComment = new SimpleStringProperty();

    public String getNhsComment() {
        return nhsComment.get();
    }

    public void setNhsComment(String value) {
        nhsComment.set(value);
    }

    public StringProperty nhsCommentProperty() {
        return nhsComment;
    }
    private StringProperty wzlComment = new SimpleStringProperty();

    public String getWzlComment() {
        return wzlComment.get();
    }

    public void setWzlComment(String value) {
        wzlComment.set(value);
    }

    public StringProperty wzlCommentProperty() {
        return wzlComment;
    }
    private StringProperty wztComment = new SimpleStringProperty();

    public String getWztComment() {
        return wztComment.get();
    }

    public void setWztComment(String value) {
        wztComment.set(value);
    }

    public StringProperty wztCommentProperty() {
        return wztComment;
    }
    private StringProperty wzslComment = new SimpleStringProperty();

    public String getWzslComment() {
        return wzslComment.get();
    }

    public void setWzslComment(String value) {
        wzslComment.set(value);
    }

    public StringProperty wzslCommentProperty() {
        return wzslComment;
    }
    private StringProperty nlcComment = new SimpleStringProperty();

    public String getNlcComment() {
        return nlcComment.get();
    }

    public void setNlcComment(String value) {
        nlcComment.set(value);
    }

    public StringProperty nlcCommentProperty() {
        return nlcComment;
    }
    private StringProperty wzlwComment = new SimpleStringProperty();

    public String getWzlwComment() {
        return wzlwComment.get();
    }

    public void setWzlwComment(String value) {
        wzlwComment.set(value);
    }

    public StringProperty wzlwCommentProperty() {
        return wzlwComment;
    }
    private StringProperty lwComment = new SimpleStringProperty();

    public String getLwComment() {
        return lwComment.get();
    }

    public void setLwComment(String value) {
        lwComment.set(value);
    }

    public StringProperty lwCommentProperty() {
        return lwComment;
    }
    private StringProperty shcComment = new SimpleStringProperty();

    public String getShcComment() {
        return shcComment.get();
    }

    public void setShcComment(String value) {
        shcComment.set(value);
    }

    public StringProperty shcCommentProperty() {
        return shcComment;
    }
    private StringProperty fapComment = new SimpleStringProperty();

    public String getFapComment() {
        return fapComment.get();
    }

    public void setFapComment(String value) {
        fapComment.set(value);
    }

    public StringProperty fapCommentProperty() {
        return fapComment;
    }

    private SimpleStringProperty feasibilityJustification = new SimpleStringProperty();

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

        s.writeInt(wzSpeedLimit.get());
        s.writeInt(futureIntProp2.get());
        s.writeInt(futureIntProp3.get());
        s.writeInt(futureIntProp4.get());
        s.writeInt(futureIntProp5.get());
        s.writeDouble(laneWidthBase.get());
        s.writeDouble(laneWidthWZ.get());
        s.writeDouble(futureDoubleProp3.get());
        s.writeDouble(futureDoubleProp4.get());
        s.writeDouble(futureDoubleProp5.get());
        s.writeObject(areaType.get());
        s.writeObject(signalizedCorridor.get());
        s.writeObject(nationalHighwaySystem.get());
        s.writeObject(shoulderClosure.get());
        s.writeObject(federalAid.get());

        s.writeObject(fcrComment.get());
        s.writeObject(maComment.get());
        s.writeObject(atComment.get());
        s.writeObject(aadtComment.get());
        s.writeObject(nrlComment.get());
        s.writeObject(swComment.get());
        s.writeObject(pslComment.get());
        s.writeObject(lwComment.get());
        s.writeObject(scComment.get());
        s.writeObject(nhsComment.get());
        s.writeObject(wzlComment.get());
        s.writeObject(wztComment.get());
        s.writeObject(wzslComment.get());
        s.writeObject(nlcComment.get());
        s.writeObject(wzlwComment.get());
        s.writeObject(shcComment.get());
        s.writeObject(fapComment.get());
        s.writeObject(feasibilityJustification.get());

        if (getProjPhoto() != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(getProjPhoto(), null), "png", s);
        } else {
            s.writeObject(getProjPhoto());
        }
        if (getConOpsDiagram() != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(getConOpsDiagram(), null), "png", s);
        } else {
            s.writeObject(getConOpsDiagram());
        }
        s.flush();
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

        setWzSpeedLimit(proj.getWzSpeedLimit());
        setLaneWidthBase(proj.getLaneWidthBase());
        setLaneWidthWZ(proj.getLaneWidthBase());
        setAreaType(proj.getAreaType());
        setSignalizedCorridor(proj.getSignalizedCorridor());
        setNationalHighwaySystem(proj.getNationalHighwaySystem());
        setShoulderClosure(proj.getShoulderClosure());
        setFederalAid(proj.getFederalAid());

        this.setFcrComment(proj.getFcrComment());
        this.setMaComment(proj.getMaComment());
        this.setAtComment(proj.getAtComment());
        this.setAadtComment(proj.getAadtComment());
        this.setNrlComment(proj.getNrlComment());
        this.setSwComment(proj.getSwComment());
        this.setPslComment((proj.getPslComment()));
        this.setLwComment(proj.getLwComment());
        this.setScComment(proj.getScComment());
        this.setNhsComment(proj.getNhsComment());

        this.setWzlComment(proj.getWzlComment());
        this.setWztComment(proj.getWztComment());
        this.setWzslComment(proj.getWzslComment());
        this.setNlcComment(proj.getNlcComment());
        this.setWzlwComment(proj.getWzlwComment());
        this.setShcComment(proj.getShcComment());
        this.setFapComment(proj.getFapComment());
        this.setFeasibilityJustification(proj.getFeasibilityJustification());

        setAutomatedEnforcementAllowed(proj.isAutomatedEnforcementAllowed());
        setCrashDataAvailable(proj.isCrashDataAvailable());

        setConOpsDiagram(proj.getConOpsDiagram());

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
        //projPhoto = new SimpleObjectProperty<>((Image) s.readObject());
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

        wzSpeedLimit = new SimpleIntegerProperty(s.readInt());
        futureIntProp2 = new SimpleIntegerProperty(s.readInt());
        futureIntProp3 = new SimpleIntegerProperty(s.readInt());
        futureIntProp4 = new SimpleIntegerProperty(s.readInt());
        futureIntProp5 = new SimpleIntegerProperty(s.readInt());
        laneWidthBase = new SimpleDoubleProperty(s.readDouble());
        laneWidthWZ = new SimpleDoubleProperty(s.readDouble());
        futureDoubleProp3 = new SimpleDoubleProperty(s.readDouble());
        futureDoubleProp4 = new SimpleDoubleProperty(s.readDouble());
        futureDoubleProp5 = new SimpleDoubleProperty(s.readDouble());
        areaType = new SimpleStringProperty((String) s.readObject());
        signalizedCorridor = new SimpleStringProperty((String) s.readObject());
        nationalHighwaySystem = new SimpleStringProperty((String) s.readObject());
        shoulderClosure = new SimpleStringProperty((String) s.readObject());
        federalAid = new SimpleStringProperty((String) s.readObject());

        try {
            String ss = (String) s.readObject();
            fcrComment = new SimpleStringProperty(ss);
        } catch (IOException e) {
            fcrComment = new SimpleStringProperty("");
        }
        try {
            maComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            maComment = new SimpleStringProperty("");
        }
        try {
            atComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            atComment = new SimpleStringProperty("");
        }
        try {
            aadtComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            aadtComment = new SimpleStringProperty("");
        }
        try {
            nrlComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            nrlComment = new SimpleStringProperty("");
        }
        try {
            swComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            swComment = new SimpleStringProperty("");
        }
        try {
            pslComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            pslComment = new SimpleStringProperty("");
        }
        try {
            lwComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            lwComment = new SimpleStringProperty("");
        }
        try {
            scComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            scComment = new SimpleStringProperty("");
        }
        try {
            nhsComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            nhsComment = new SimpleStringProperty("");
        }
        try {
            wzlComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            wzlComment = new SimpleStringProperty("");
        }
        try {
            wztComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            wztComment = new SimpleStringProperty("");
        }
        try {
            wzslComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            wzslComment = new SimpleStringProperty("");
        }
        try {
            nlcComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            nlcComment = new SimpleStringProperty("");
        }
        try {
            wzlwComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            wzlwComment = new SimpleStringProperty("");
        }
        try {
            shcComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            shcComment = new SimpleStringProperty("");
        }
        try {
            fapComment = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            fapComment = new SimpleStringProperty("");
        }

        try {
            feasibilityJustification = new SimpleStringProperty((String) s.readObject());
        } catch (IOException e) {
            feasibilityJustification = new SimpleStringProperty("");
        }

        BufferedImage bimg = ImageIO.read(s);
        if (bimg != null) {
            projPhoto = new SimpleObjectProperty<>(SwingFXUtils.toFXImage(bimg, null));
        } else {
            projPhoto = new SimpleObjectProperty<>();
        }
        BufferedImage conOpsImg = ImageIO.read(s);
        if (conOpsImg != null) {
            conOpsDiagram = new SimpleObjectProperty<>(SwingFXUtils.toFXImage(conOpsImg, null));
        } else {
            conOpsDiagram = new SimpleObjectProperty<>();
        }
    }

    public SimpleStringProperty feasibilityJustificationProperty() {
        return feasibilityJustification;
    }

    public String getFeasibilityJustification() {
        return feasibilityJustification.get();
    }

    public void setFeasibilityJustification(String newJustification) {
        this.feasibilityJustification.set(newJustification);
    }

    public static class Step implements Serializable {

        private static final long serialVersionUID = 123456789L;

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

    public static class SubStep implements Serializable {

        private static final long serialVersionUID = 123456789L;

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

    public static final int GOAL_SELECTION_INDEX = 2;
    public static final int GOAL_WIZARD_SUMMARY_INDEX = 3; // Step 1
    public static final int FEAS_WIZARD_SUMMARY_INDEX = 4; // Step 1
    public static final int STAKEHOLDER_WIZARD_SUMMARY_INDEX = 6;  // Step 1
    public static final int TEAM_SUMMARY_INDEX = 7; // Step 1
    public static final int APP_WIZARD_SUMMARY_INDEX = 1; // Step 2

    public static final String[][] STEP_NAMES = {
        //{"Step 1", "WZ Metadata", "User Needs", "User Needs Support", "Major Goals", "Goal Wizard", "Feasibility", "Feasibility Wizard", "Stakeholders", "Stakeholders Wizard & Team Selection", "Team Members", "ITS Resources"},
        {"Step 1", "General Info", "User Needs", "Goals Selection", "Selected Goals", "Feasibility Assessment", "Stakeholder Assessment", "Stakeholders & Team Selection", "Team Members", "ITS Resources"}, //"User Needs Support"
        {"Step 2", "Initial Applications", "Application Wizard", "Benefits", "Costs", "Institutional/Jurisdictional", "Legal/Policy", "Stakeholder Buy-In", "Develop Concept of Operations"},
        //{"Step 3", "Document Concept of Operations", "Requirements", "System Design", "Testing Strategy", "Operations & Maintenance", "Staff Training Needs", "Public Outreach", "System Security", "Evaluation", "Benefit/Cost"},
        {"Step 3", "Document Concept of Operations", "Requirements", "Testing Strategy", "Operations & Maintenance", "Staff Training Needs", "System Security", "Evaluation", "Benefit/Cost"},
        {"Step 4", "Direct/Indirect", "Award Mechanism", "RFP Requirements", "Selected Vendor"},
        {"Step 5", "Implementing System Plans", "Scheduling Decisions", "System Acceptance Testing", "Handling Deployment Issues"},
        {"Step 6", "Changing Work Zone", "Using/Sharing ITS Info", "Maintaining Adequate Staff", "Leveraging Public Support", "System Monitoring/Evaluation"}
    };

    public static final String[] FUNCTIONAL_CLASS_LIST = {"Interstate", "Other Freeway", "Principal Arterial", "Minor Arterial", "Major Collector", "Minor Collector", "Local"};
    public static final String[] MUTCD_LIST = {"Long-Term Stationary", "Intermediate-Term Stationary", "Short-Term Stationary", "Short Duartion", "Mobile"};
    public static final String[] AGENCY_LIST = {"State", "County", "City/Town", "Other"};

}
