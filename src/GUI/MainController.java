/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import core.Project;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 *
 * @author ltrask
 */
public class MainController {

    private MainWindow mainWindow;

    private final Stage stage;

    private final Project proj;
    /**
     * Number of steps for the WZ-ITS Tool
     */
    private final int numSteps = 6;
    /**
     * Index of the step currently active.
     */
    private final SimpleIntegerProperty activeStep = new SimpleIntegerProperty(-1);
    /**
     * Index of the sub step currently active.
     */
    private final SimpleIntegerProperty[] activeSubStep = new SimpleIntegerProperty[numSteps];

    public MainController(Stage stage) {
        this.stage = stage;
        proj = new Project("Sample Project");
        for (int stepIdx = 0; stepIdx < activeSubStep.length; stepIdx++) {
            activeSubStep[stepIdx] = new SimpleIntegerProperty(-1);
        }
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public int getActiveStep() {
        return this.activeStep.get();
    }

    public void setActiveStep(int stepIdx) {
        this.activeStep.set(stepIdx);
    }

    public SimpleIntegerProperty activeStepProperty() {
        return activeStep;
    }

    /**
     * Check the active substep for a given step.
     *
     * @param stepIdx
     * @return
     */
    public int getActiveSubStep(int stepIdx) {
        if (stepIdx >= 0 && stepIdx < activeSubStep.length) {
            return activeSubStep[stepIdx].get();
        } else {
            return -1;
        }
    }

    /**
     * Set the active substep for a given step.
     *
     * @param stepIdx
     * @param subStepIdx
     */
    public void setActiveSubStep(int stepIdx, int subStepIdx) {
        if (stepIdx >= 0) {
            this.activeSubStep[stepIdx].set(subStepIdx);
        }
    }

    /**
     * Getter for the active substep property for a given step.
     *
     * @param stepIdx
     * @return
     */
    public SimpleIntegerProperty activeSubStepProperty(int stepIdx) {
        return activeSubStep[stepIdx];
    }

    public void selectStep(int stepIdx) {
        selectStep(stepIdx, -1);
    }

    public void selectStep(int stepIdx, int subStepIdx) {
        setActiveStep(stepIdx);
        setActiveSubStep(stepIdx, subStepIdx);
    }

    public void stepBack() {
        if (activeStep.get() < 0) {
            //selectStep(-1, -1);
        } else if (activeStep.get() == 0 && activeSubStep[activeStep.get()].get() == 0) {
            selectStep(-1, -1);
        } else {
            if (activeSubStep[activeStep.get()].get() > 0) {
                selectStep(activeStep.get(), activeSubStep[activeStep.get()].get() - 1);
            } else {
                selectStep(activeStep.get() - 1, activeSubStep[activeStep.get() - 1].get());
            }
        }
    }

    public void stepForward() {
        if (activeStep.get() < 0) {
            selectStep(0, -1);
        } else if (activeStep.get() < Project.NUM_STEPS) {
            if (activeSubStep[activeStep.get()].get() < Project.NUM_SUB_STEPS[activeStep.get()]) {
                selectStep(activeStep.get(), activeSubStep[activeStep.get()].get() + 1);
            } else {
                selectStep(activeStep.get() + 1, -1);
            }
        } else {
            //selectStep(-1, -1);
        }
    }

    public void begin() {
        //stage.hide();
        //stage.setMaximized(true);
        //stage.show();
        mainWindow.begin();
        //stage.setMinWidth(mainWindow.getMinWidth());
    }

    public double getAppWidth() {
        return stage.getWidth();
    }

    public Project getProject() {
        return proj;
    }

    public void checkProceed() {
        //mainWindow.enableProceed(proj.isStepComplete(stepIndex));
        mainWindow.checkProceed();
    }

    public static Tooltip getTooltip(int stepIdx, int subStepIdx) {
        switch (stepIdx) {
            default:
                return null;
            case 0:
                if (subStepIdx >= 0 && subStepIdx < STEP_1_TOOLTIPS.length) {
                    return new Tooltip(STEP_1_TOOLTIPS[subStepIdx]);
                } else {
                    return new Tooltip("Step 1: Assesment of Needs");
                }
            case 1:
                if (subStepIdx >= 0 && subStepIdx < STEP_2_TOOLTIPS.length) {
                    return new Tooltip(STEP_2_TOOLTIPS[subStepIdx]);
                } else {
                    return new Tooltip("Step 2: Concept Development and Feasibility");
                }
        }
    }

    public static final int MAX_WIDTH = 999999;
    public static final int MAX_HEIGHT = 999999;

    public static final String[] STEP_1_TOOLTIPS = new String[]{
        "Project Info and Work Zone Metadata",
        "User Needs",
        "User Needs Supplemental",
        "System Goals",
        "Step Summary"};
    public static final String[] STEP_2_TOOLTIPS = new String[]{
        "Project Info and Work Zone Metadata",
        "User Needs",
        "User Needs Supplemental",
        "System Goals",
        "Step Summary"};

}
