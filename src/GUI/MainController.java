/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.IOHelper;
import core.Project;
import java.util.Optional;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author ltrask
 */
public class MainController {

    private MainWindow mainWindow;

    private final Stage stage;

    private Project proj;

    /**
     * Indicates if the project has been started, bound to the activeStep
     * property
     */
    private final SimpleBooleanProperty projectStarted = new SimpleBooleanProperty(false);
    /**
     * Index of the step currently active.
     */
    private final SimpleIntegerProperty activeStep = new SimpleIntegerProperty(-1);
    /**
     * Index of the sub step currently active.
     */
    private final SimpleIntegerProperty[] activeSubStep = new SimpleIntegerProperty[Project.NUM_STEPS];

    public MainController(Stage stage) {
        this.stage = stage;
        proj = new Project("WZITS Project");
        for (int stepIdx = 0; stepIdx < activeSubStep.length; stepIdx++) {
            activeSubStep[stepIdx] = new SimpleIntegerProperty(-2);
        }

        activeStep.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                if (!projectStarted.get() && (int) newVal >= 0) {
                    projectStarted.set(true);
                }
            }
        });

    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public Window getWindow() {
        return stage.getOwner();
    }

    public void setMainWindowTitleLabel(String newLabelText) {
        mainWindow.setTitleLabel(newLabelText, true);
    }

    public void updateMainWindowTitle() {
        if (getActiveStep() < 0) {
            setMainWindowTitleLabel(INTRO_TITLE);
        } else if (getActiveStep() == Project.NUM_STEPS) {
            setMainWindowTitleLabel(SUMMARY_TITLE);
        } else {
            setMainWindowTitleLabel("Step " + String.valueOf(getActiveStep() + 1) + ": " + STEP_TITLES[getActiveStep()]);
        }
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

    public SimpleBooleanProperty projectStartedProperty() {
        return projectStarted;
    }

    public ReadOnlyDoubleProperty stageWidthProperty() {
        return stage.widthProperty();
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
        } else if (activeStep.get() == 0 && activeSubStep[activeStep.get()].get() < 0) {
            selectStep(-1, -1);
        } else {
            if (activeSubStep[activeStep.get()].get() >= 0) {
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
        stage.hide();
        stage.setMaximized(true);
        mainWindow.begin();
        stage.show();
        //stage.setMinWidth(mainWindow.getMinWidth());
    }

    public void newProjectOpened() {
        stage.hide();
        stage.setMaximized(true);
        Scene newScene = new Scene(new MainWindow(this, false));
        newScene.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        stage.setScene(newScene);
        stage.show();
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
                    return new Tooltip("Step 1: Assessment of Needs");
                }
            case 1:
                if (subStepIdx >= 0 && subStepIdx < STEP_2_TOOLTIPS.length) {
                    return new Tooltip(STEP_2_TOOLTIPS[subStepIdx]);
                } else {
                    return new Tooltip("Step 2: Concept Development and Feasibility");
                }
        }
    }

    public void openProject() {
        Project openedProj = IOHelper.openProject(this);
        if (openedProj != null) {
            this.proj.setFromProject(openedProj);
            this.newProjectOpened();
        }
    }

    public void saveProject() {
        IOHelper.saveProject(this, proj);
    }

    public void saveAsProject() {
        IOHelper.saveAsProject(this, proj);
    }

    public void exitProgram() {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
                "Save Project Before Exiting?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        al.setTitle("Exiting WZITS Tool");
        al.setHeaderText("WZITS Tool");
        Optional<ButtonType> result = al.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.NO) {
                stage.close();
            } else if (result.get() == ButtonType.YES) {
                int saveResult = IOHelper.saveProject(this, proj);
                if (saveResult == IOHelper.SAVE_COMPLETED) {
                    stage.close();
                }
            } else {
                // Cancelled by user, do nothing
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

    public static final String[] STEP_TITLES = new String[]{
        "Assessment of Needs & Feasibility",
        "Concept Development",
        "System Planning & Design",
        "Procurement",
        "System Deployment",
        "System Operation, Maintenance & Evaluation"
    };

    public static final String INTRO_TITLE = "Project Introduction";

    public static final String SUMMARY_TITLE = "Project Summary";

}
