/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Helper.ColorHelper;
import GUI.Helper.IOHelper;
import GUI.Helper.NodeFactory;
import GUI.PDFReports.PDFReportHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.javafx.scene.NodeHelper;
import core.Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Optional;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author ltrask
 */
public class MainController {

    private MainWindow mainWindow;

    private static Stage stage;

    private static StackPane rootStackPane;

    private Project proj;

    private static boolean inLaunchWindow = true;

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

    /**
     * Mapping to store the last utilized save location for file types
     */
    public static final HashMap<String, Object> lastSaveLocations = new HashMap<>();


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

    public static Stage getStage() {
        return stage;
    }

    public static Window getWindow() {
        //return stage.getOwner();
        return stage.getOwner();
    }

    public static void setRootStackPane(StackPane rootStackPane) {
        MainController.rootStackPane = rootStackPane;
    }

    public static StackPane getRootStackPane() {
        return MainController.rootStackPane;
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
        if (stepIdx >= 0 && stepIdx < Project.NUM_STEPS) {
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
            if (activeStep.get() < activeSubStep.length && activeSubStep[activeStep.get()].get() >= 0) {
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
        Alert al = new Alert(Alert.AlertType.NONE);
        al.initOwner(getWindow());
        al.setTitle("Please Wait");
        al.setHeaderText("Loading WZITS Project...");
        ProgressBar ipb = new ProgressBar(1.0);
        ipb.setStyle("-fx-accent: " + ColorHelper.WZ_ORANGE);
        al.setGraphic(ipb);
        al.getButtonTypes().add(ButtonType.OK);
        Button cancelButton = (Button) al.getDialogPane().lookupButton(ButtonType.OK);
        cancelButton.setText("Loading...");
        cancelButton.setDisable(true);
        al.show();
        stage.setMaximized(true);
        BorderPane mainPane = new MainWindow(this, false);
        StackPane rootStackPane = new StackPane();
        this.setRootStackPane(rootStackPane);
        rootStackPane.getChildren().add(mainPane);
        Scene newScene = new Scene(rootStackPane); // new MainWindow(this, false)
        newScene.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        stage.setScene(newScene);
        stage.show();
        cancelButton.setDisable(false);
        cancelButton.fire();
        selectStep(-1);
        // Hack to force an update on the flow bar buttons
        selectStep(0, -1);
//        if (getActiveSubStep(0) == -1) {
//            selectStep(0, 0);
//        } else {
//            selectStep(0, -1);
//        }

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

    public void newProject() {
//        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
//                "Save Current Project?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        al.setTitle("WZITS Tool");
//        al.setHeaderText("WZITS Tool");
//        Optional<ButtonType> result = al.showAndWait();
//        if (result.isPresent()) {
//            if (result.get() != ButtonType.CANCEL) {
//                if (result.get() == ButtonType.YES) {
//                    int saveResult = saveProject();
//                    IOHelper.confirm(saveResult);
//                }
//                this.proj.setFromProject(new Project());
//                this.newProjectOpened();
//                MainController.updateProgramHeader(this.proj);
//            }
//        }

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(NodeFactory.createFormattedLabel("WZITS Tool", "modal-title"));
        content.setBody(NodeFactory.createFormattedLabel("Save current project before creating new project?", ""));
        JFXDialog dlg = new JFXDialog(getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
        Runnable createNewProject = new Runnable() {
            @Override
            public void run() {
                proj.setFromProject(new Project());
                newProjectOpened();
                MainController.updateProgramHeader(proj);
                dlg.close();
            }
        };
        JFXButton yesButton = new JFXButton("Yes");
        yesButton.setStyle("-fx-font-size: 12pt");
        yesButton.setOnAction(actionEvent -> {
            dlg.close();
            int saveResult = saveProject();
            IOHelper.confirm(saveResult, createNewProject);
        });
        JFXButton noButton = new JFXButton("No");
        noButton.setStyle("-fx-font-size: 12pt");
        noButton.setOnAction(actionEvent -> {
            dlg.close();
            createNewProject.run();
        });
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setStyle("-fx-font-size: 12pt");
        cancelButton.setOnAction(actionEvent -> {
            dlg.close();
        });
        content.getActions().addAll(yesButton, noButton, cancelButton);
        dlg.setOverlayClose(false);
        dlg.show();
    }

    public void openProject() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(NodeFactory.createFormattedLabel("Save Current Project?", "modal-title"));
        JFXDialog dlg = new JFXDialog(getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
        final MainController mainControllerRef = this;
        Runnable onConfrim = new Runnable() {
            @Override
            public void run() {
                Project openedProj = IOHelper.openProject(mainControllerRef);
                if (openedProj != null) {
                    proj.setFromProject(openedProj);
                    newProjectOpened();
                    MainController.updateProgramHeader(proj);
                }
            }
        };
        JFXButton yesButton = new JFXButton("Yes");
        yesButton.setStyle("-fx-font-size: 12pt;");
        yesButton.setOnAction(actionEvent -> {
            dlg.close();
            int saveResult = saveProject();
            IOHelper.confirm(saveResult, onConfrim);
        });
        JFXButton noButton = new JFXButton("No");
        noButton.setStyle("-fx-font-size: 12pt;");
        noButton.setOnAction(actionEvent -> {
            dlg.close();
            onConfrim.run();
        });
        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.setStyle("-fx-font-size: 12pt;");
        cancelButton.setOnAction(actionEvent -> {
            dlg.close();
        });
        content.getActions().addAll(yesButton, noButton, cancelButton);
        dlg.setOverlayClose(false);
        dlg.show();
    }

//    public void openProject() {
//        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
//                "Save Current Project?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        al.setTitle("WZITS Tool");
//        al.setHeaderText("WZITS Tool");
//        Optional<ButtonType> result = al.showAndWait();
//        if (result.isPresent()) {
//            if (result.get() != ButtonType.CANCEL) {
//                if (result.get() == ButtonType.YES) {
//                    int saveResult = saveProject();
//                    IOHelper.confirm(saveResult);
//                }
//                Project openedProj = IOHelper.openProject(this);
//                if (openedProj != null) {
//                    this.proj.setFromProject(openedProj);
//                    this.newProjectOpened();
//                    MainController.updateProgramHeader(this.proj);
//                }
//            }
//        }
//    }

    public int saveProject() {
        int res = IOHelper.saveProject(this, proj);
        if (res == IOHelper.SAVE_COMPLETED) {
            MainController.updateProgramHeader(this.proj);
        }
        return res;
    }

    public int saveAsProject() {
        int res = IOHelper.saveAsProject(this, proj);
        if (res == IOHelper.SAVE_COMPLETED) {
            MainController.updateProgramHeader(this.proj);
        }
        return res;
    }

    public void exitProgram() {
//        Alert al = new Alert(Alert.AlertType.CONFIRMATION,
//                "Save Project Before Exiting?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        al.setTitle("Exiting WZITS Tool");
//        al.setHeaderText("WZITS Tool");
//        Optional<ButtonType> result = al.showAndWait();
//        if (result.isPresent()) {
//            if (result.get() == ButtonType.NO) {
//                stage.close();
//            } else if (result.get() == ButtonType.YES) {
//                int saveResult = IOHelper.saveProject(this, proj);
//                if (saveResult == IOHelper.SAVE_COMPLETED) {
//                    stage.close();
//                }
//            } else {
//                // Cancelled by user, do nothing
//            }
//        }
        if (!MainController.isInLaunchWindow()) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(NodeFactory.createFormattedLabel("Exiting WZITS Tool", "modal-title"));
            content.setBody(NodeFactory.createFormattedLabel("Save project before exiting?", ""));

            JFXDialog dlg = new JFXDialog(getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);

            JFXButton yesButton = new JFXButton("Yes");
            yesButton.setStyle("-fx-font-size: 12pt;");
            yesButton.setOnAction(actionEvent -> {
                dlg.close();
                final int saveResult = saveProject();
                Runnable closeAfterSaving = new Runnable() {
                    @Override
                    public void run() {
                        if (saveResult != IOHelper.SAVE_CANCELLED)
                            stage.close();
                    }
                };
                IOHelper.confirm(saveResult, closeAfterSaving);
            });
            JFXButton noButton = new JFXButton("No");
            noButton.setStyle("-fx-font-size: 12pt;");
            noButton.setOnAction(actionEvent -> {
                dlg.setOnDialogClosed(jfxDialogEvent -> stage.close());
                dlg.close();
            });
            JFXButton cancelButton = new JFXButton("Cancel");
            cancelButton.setStyle("-fx-font-size: 12pt;");
            cancelButton.setOnAction(actionEvent -> {
                dlg.close();
            });
            content.getActions().addAll(yesButton, noButton, cancelButton);
            dlg.setOverlayClose(false);
            dlg.show();
        }
    }

    public static void updateProgramHeader(Project p) {
        String titleString = "Work Zone Intelligent Transportations Systems Tool";
        titleString = titleString + (p.getName() != null ? " - " + p.getName() : "");
        titleString = titleString + (p.getSaveFile() != null ? " (" + p.getSaveFile().getAbsolutePath() + ")" : "");
        stage.setTitle(titleString);
    }

    public Node goToFactSheet(int factSheetIdx, boolean useSummary) {
        if (!useSummary) {
            return mainWindow.goToFactSheet(factSheetIdx);
        } else {
            return mainWindow.goToSummaryFactSheet(factSheetIdx, false);
        }
    }

    public static void setLastSaveLocation(String fileType, String directory) {
        MainController.lastSaveLocations.put(fileType, directory);
    }

    public static String getLastSaveLocation(String fileType) {
        Object lastDir = MainController.lastSaveLocations.getOrDefault(fileType, null);
        if (lastDir != null) {
            return lastDir.toString();
        }
        return null;
    }

    public static String getResFolderLocation() {
        String location = MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        location = location.replaceAll("%20", " ");
        if (location.contains("/build/classes")) {
            location = location.substring(0, location.lastIndexOf("/build")) + "/"; // + "resources" + "/";
        }
        location = location.substring(0, location.lastIndexOf("/")) + "/" + "pdfres";
        File pdfresFolder = new File(location);
        if (!pdfresFolder.exists()) {
            pdfresFolder.mkdirs();
        }
        checkAndRepairPDFResourceFiles(pdfresFolder);
        return location + "/";
    }

    private static void checkAndRepairPDFResourceFiles(File pdfresFolder) {
        String repairFileDir = "/resources/pdfresForRepair/";
        String[] pdfFiles = new String[]{
                "us_dot_logo.png",
                "wzits_icon_64.png",
                "template.xsl"
        };
        for (String repairFileName: pdfFiles) {
            if (!(new File(pdfresFolder, repairFileName)).exists()) {
                InputStream stream = null;
                OutputStream resStreamOut = null;
                try {
                    stream = PDFReportHelper.class.getResourceAsStream(repairFileDir + repairFileName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
                    if (stream == null) {
                        throw new Exception("Cannot get resource \"" + repairFileName + "\" from Jar file.");
                    }

                    int readBytes;
                    byte[] buffer = new byte[4096];
//                    jarFolder = new File(ExecutingClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
                    resStreamOut = new FileOutputStream(pdfresFolder.getAbsolutePath() + "/" + repairFileName);
                    while ((readBytes = stream.read(buffer)) > 0) {
                        resStreamOut.write(buffer, 0, readBytes);
                    }
                } catch (Exception ex) {
//                    throw ex;
                    ex.printStackTrace(System.out);
                } finally {
                    try {
                        stream.close();
                        resStreamOut.close();
                    } catch (Exception ie) {

                    }
                }
            }
        }
    }

    public static String getScoringMatrixFolder() {
        String location = MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        location = location.replaceAll("%20", " ");
        if (location.contains("/build/classes")) {
            location = location.substring(0, location.lastIndexOf("/build")) + "/"; // + "resources" + "/";
        }
        location = location.substring(0, location.lastIndexOf("/")) + "/" + "scoringMatrix";
        File pdfresFolder = new File(location);
        if (!pdfresFolder.exists()) {
            pdfresFolder.mkdirs();
        }
        checkAndRepairScoringMatrices(pdfresFolder);
        return location + "/";
    }

    private static void checkAndRepairScoringMatrices(File pdfresFolder) {
        String repairFileDir = "/core/defaults/";
        String[] matrixFiles = new String[]{
                "appWizardDefaultMatrix.json",
                "feasibilityDefaultMatrix.json",
                "goalNeedsDefaultMatrix.json",
                "stakeholderDefaultMatrix.json"
        };
        for (String repairFileName: matrixFiles) {
            if (!(new File(pdfresFolder, repairFileName)).exists()) {
                InputStream stream = null;
                OutputStream resStreamOut = null;
                try {
                    stream = PDFReportHelper.class.getResourceAsStream(repairFileDir + repairFileName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
                    if (stream == null) {
                        throw new Exception("Cannot get resource \"" + repairFileName + "\" from Jar file.");
                    }

                    int readBytes;
                    byte[] buffer = new byte[4096];
//                    jarFolder = new File(ExecutingClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
                    resStreamOut = new FileOutputStream(pdfresFolder.getAbsolutePath() + "/" + repairFileName);
                    while ((readBytes = stream.read(buffer)) > 0) {
                        resStreamOut.write(buffer, 0, readBytes);
                    }
                } catch (Exception ex) {
//                    throw ex;
                    ex.printStackTrace(System.out);
                } finally {
                    try {
                        stream.close();
                        resStreamOut.close();
                    } catch (Exception ie) {

                    }
                }
            }
        }
    }

    public static void launchScoringMatrixInfo() {
        BorderPane contentPanel = new BorderPane();
        contentPanel.setTop(
                NodeFactory.createFormattedLabel("The scoring matrices for the four wizards can be adjusted to match an " +
                        "agency's specific needs and policies using the associated Master Matrix Generator excel file (.xlsm).",
                        "")
        );

        File customGoalsMatrix = new File(MainController.getScoringMatrixFolder() + "goalNeedsCustomMatrix.json");
        File customFeasibilityMatrix = new File(MainController.getScoringMatrixFolder() + "feasibilityCustomMatrix.json");
        File customStakeholderMatrix = new File(MainController.getScoringMatrixFolder() + "stakeholderCustomMatrix.json");
        File customApplicationMatrix = new File(MainController.getScoringMatrixFolder() + "appWizardCustomMatrix.json");

        Label labelGoalsMatrix = NodeFactory.createFormattedLabel(customFeasibilityMatrix.exists() ? "Custom" : "Default", "");
        Label labelFeasibilityMatrix = NodeFactory.createFormattedLabel(customFeasibilityMatrix.exists() ? "Custom" : "Default", "");
        Label labelStakeholderMatrix = NodeFactory.createFormattedLabel(customFeasibilityMatrix.exists() ? "Custom" : "Default", "");
        Label labelApplicationMatrix = NodeFactory.createFormattedLabel(customFeasibilityMatrix.exists() ? "Custom" : "Default", "");

        JFXButton resetGoalsMatrixButton = new JFXButton("Reset to Default");
        resetGoalsMatrixButton.getStyleClass().add("default-text-button");
        resetGoalsMatrixButton.setDisable(!customGoalsMatrix.exists());
        resetGoalsMatrixButton.setOnAction(actionEvent -> {
            boolean wasDeleted = customGoalsMatrix.delete();
            JFXDialogLayout subContent = new JFXDialogLayout();
            if (wasDeleted) {
                subContent.setHeading(NodeFactory.createFormattedLabel("Goal-Needs Scoring Matrix Reverted to Default", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("The WZITS tool must be restarted before the changes will take effect.", ""));
                resetGoalsMatrixButton.setDisable(true);
                labelGoalsMatrix.setText("Default");
            } else {
                subContent.setHeading(NodeFactory.createFormattedLabel("Failed to Revert to Default Matrix", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("An error was encountered deleting the custom scoring matrix. Please make sure the file is not in use by another program before attempting to revert to the default matrix.", ""));
            }
            JFXDialog subDlg = new JFXDialog(MainController.getRootStackPane(), subContent, JFXDialog.DialogTransition.CENTER);
            subDlg.setOverlayClose(false);
            subDlg.setContent(subContent);
            JFXButton okAction = new JFXButton("Ok");
            okAction.getStyleClass().add("comment-pane-buttonClose");
            okAction.setOnAction(actionEvent1 -> {subDlg.close();});
            subContent.getActions().addAll(okAction);

            subDlg.show();
        });
        JFXButton resetFeasibilityMatrixButton = new JFXButton("Reset to Default");
        resetFeasibilityMatrixButton.getStyleClass().add("default-text-button");
        resetFeasibilityMatrixButton.setDisable(!customFeasibilityMatrix.exists());
        resetFeasibilityMatrixButton.setOnAction(actionEvent -> {
            boolean wasDeleted = customFeasibilityMatrix.delete();
            JFXDialogLayout subContent = new JFXDialogLayout();
            if (wasDeleted) {
                subContent.setHeading(NodeFactory.createFormattedLabel("Feasibility Scoring Matrix Reverted to Default", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("The WZITS tool must be restarted before the changes will take effect.", ""));
                resetFeasibilityMatrixButton.setDisable(true);
                labelFeasibilityMatrix.setText("Default");
            } else {
                subContent.setHeading(NodeFactory.createFormattedLabel("Failed to Revert to Default Matrix", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("An error was encountered deleting the custom scoring matrix. Please make sure the file is not in use by another program before attempting to revert to the default matrix.", ""));
            }
            JFXDialog subDlg = new JFXDialog(MainController.getRootStackPane(), subContent, JFXDialog.DialogTransition.CENTER);
            subDlg.setOverlayClose(false);
            subDlg.setContent(subContent);
            JFXButton okAction = new JFXButton("Ok");
            okAction.getStyleClass().add("comment-pane-buttonClose");
            okAction.setOnAction(actionEvent1 -> {subDlg.close();});
            subContent.getActions().addAll(okAction);

            subDlg.show();
        });
        JFXButton resetStakeholderMatrixButton = new JFXButton("Reset to Default");
        resetStakeholderMatrixButton.getStyleClass().add("default-text-button");
        resetStakeholderMatrixButton.setDisable(!customStakeholderMatrix.exists());
        resetStakeholderMatrixButton.setOnAction(actionEvent -> {
            boolean wasDeleted = customStakeholderMatrix.delete();
            JFXDialogLayout subContent = new JFXDialogLayout();
            if (wasDeleted) {
                subContent.setHeading(NodeFactory.createFormattedLabel("Stakeholder Scoring Matrix Reverted to Default", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("The WZITS tool must be restarted before the changes will take effect.", ""));
                resetStakeholderMatrixButton.setDisable(true);
                labelStakeholderMatrix.setText("Default");
            } else {
                subContent.setHeading(NodeFactory.createFormattedLabel("Failed to Revert to Default Matrix", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("An error was encountered deleting the custom scoring matrix. Please make sure the file is not in use by another program before attempting to revert to the default matrix.", ""));
            }
            JFXDialog subDlg = new JFXDialog(MainController.getRootStackPane(), subContent, JFXDialog.DialogTransition.CENTER);
            subDlg.setOverlayClose(false);
            subDlg.setContent(subContent);
            JFXButton okAction = new JFXButton("Ok");
            okAction.getStyleClass().add("comment-pane-buttonClose");
            okAction.setOnAction(actionEvent1 -> {subDlg.close();});
            subContent.getActions().addAll(okAction);

            subDlg.show();
        });
        JFXButton resetApplicationMatrixButton = new JFXButton("Reset to Default");
        resetApplicationMatrixButton.getStyleClass().add("default-text-button");
        resetApplicationMatrixButton.setDisable(!customApplicationMatrix.exists());
        resetApplicationMatrixButton.setOnAction(actionEvent -> {
            boolean wasDeleted = customApplicationMatrix.delete();
            JFXDialogLayout subContent = new JFXDialogLayout();
            if (wasDeleted) {
                subContent.setHeading(NodeFactory.createFormattedLabel("Application Scoring Matrix Reverted to Default", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("The WZITS tool must be restarted before the changes will take effect.", ""));
                resetApplicationMatrixButton.setDisable(true);
                labelApplicationMatrix.setText("Default");
            } else {
                subContent.setHeading(NodeFactory.createFormattedLabel("Failed to Revert to Default Matrix", "modal-title"));
                subContent.setBody(NodeFactory.createFormattedLabel("An error was encountered deleting the custom scoring matrix. Please make sure the file is not in use by another program before attempting to revert to the default matrix.", ""));
            }
            JFXDialog subDlg = new JFXDialog(MainController.getRootStackPane(), subContent, JFXDialog.DialogTransition.CENTER);
            subDlg.setOverlayClose(false);
            subDlg.setContent(subContent);
            JFXButton okAction = new JFXButton("Ok");
            okAction.getStyleClass().add("comment-pane-buttonClose");
            okAction.setOnAction(actionEvent1 -> {subDlg.close();});
            subContent.getActions().addAll(okAction);

            subDlg.show();
        });

        GridPane matrixSummaryPane = new GridPane();
        matrixSummaryPane.setHgap(80);
        int rowIdx = 0;
        Label headerLabel1 = NodeFactory.createFormattedLabel("Wizard", "");
        headerLabel1.setAlignment(Pos.CENTER);
        headerLabel1.setStyle("-fx-font-weight: bold");
        Label headerLabel2 = NodeFactory.createFormattedLabel("Matrix", "", "A value of \"custom\" indicates that a user-custom scoring matrix is currently being used by the tool, while a value of \"default\" indicates the default matrix is being used.", true);
        headerLabel2.setAlignment(Pos.CENTER);
        headerLabel2.setStyle("-fx-font-weight: bold");
        Label headerLabel3 = NodeFactory.createFormattedLabel("Action", "");
        headerLabel3.setAlignment(Pos.CENTER);
        headerLabel3.setStyle("-fx-font-weight: bold");
        matrixSummaryPane.add(headerLabel1, 0, rowIdx);
        matrixSummaryPane.add(headerLabel2, 1, rowIdx);
        matrixSummaryPane.add(headerLabel3, 2, rowIdx);
        rowIdx++;
        matrixSummaryPane.add(NodeFactory.createFormattedLabel("Goal-Needs", ""), 0, rowIdx);
        matrixSummaryPane.add(labelGoalsMatrix, 1, rowIdx);
        matrixSummaryPane.add(resetGoalsMatrixButton, 2, rowIdx);
        rowIdx++;
        matrixSummaryPane.add(NodeFactory.createFormattedLabel("Feasibility", ""), 0, rowIdx);
        matrixSummaryPane.add(labelFeasibilityMatrix, 1, rowIdx);
        matrixSummaryPane.add(resetFeasibilityMatrixButton, 2, rowIdx);
        rowIdx++;
        matrixSummaryPane.add(NodeFactory.createFormattedLabel("Stakeholders", ""), 0, rowIdx);
        matrixSummaryPane.add(labelStakeholderMatrix, 1, rowIdx);
        matrixSummaryPane.add(resetStakeholderMatrixButton, 2, rowIdx);
        rowIdx++;
        matrixSummaryPane.add(NodeFactory.createFormattedLabel("Applications", ""), 0, rowIdx);
        matrixSummaryPane.add(labelApplicationMatrix, 1, rowIdx);
        matrixSummaryPane.add(resetApplicationMatrixButton, 2, rowIdx);

        contentPanel.setCenter(matrixSummaryPane);
        matrixSummaryPane.setAlignment(Pos.CENTER);
        BorderPane.setMargin(matrixSummaryPane, new Insets(20, 0, 0, 0));

        JFXDialogLayout content = new JFXDialogLayout();
        content.setMinWidth(800);
        content.setHeading(NodeFactory.createFormattedLabel("WZITS Tool Scoring Matrix Info", "modal-title"));
        content.setBody(contentPanel);

        JFXDialog dlg = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
        dlg.setOverlayClose(false);
        dlg.setContent(content);
        JFXButton closeAction = new JFXButton("Close");
        closeAction.getStyleClass().add("comment-pane-buttonClose");
        closeAction.setOnAction(actionEvent -> dlg.close());
        content.getActions().addAll(closeAction);

        dlg.show();
    }

    public static boolean isInLaunchWindow() {
        return inLaunchWindow;
    }

    public static void setInLaunchWindow(boolean inLaunchWindow) {
        MainController.inLaunchWindow = inLaunchWindow;
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

    public static final int FADE_TIME = 50;

    public static final String VERSION = "2.0.0";

}
