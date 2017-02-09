/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Step.Step1Panel;
import GUI.Launch.LaunchPane;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author jlake
 */
public class MainWindowPagination extends BorderPane {

    private final MainController control;
    
    public MainWindowPagination(MainController control) {

        this.control = control;
        //control.setMainWindow(this);
        
        titleLabel.getStylesheets().add(this.getClass().getResource("/GUI/Launch/title1.css").toExternalForm());
        titleLabel.setMaxWidth(MainController.MAX_WIDTH);
        
        stepFlow = new Pagination(6);
        stepFlow.setPageFactory(new Callback<Integer, Node>(){
            @Override
            public Node call(Integer pageIndex) {
                return getStepPane(pageIndex);
            }
        });
        stepFlow.getStylesheets().add(this.getClass().getResource("/GUI/CSS/stepFlowPagination.css").toExternalForm());
        toolBarBox.getChildren().addAll(titleLabel);

        // Setting up sliding panes
        //step1Pane.setCenter(new Label("Step 1"));
        step1Pane = new Step1Panel(control);
        step2Pane.setCenter(new Label("Step 2"));
        step3Pane.setCenter(new Label("Step 3"));
        step4Pane.setCenter(new Label("Step 4"));
        step5Pane.setCenter(new Label("Step 5"));
        step6Pane.setCenter(new Label("Step 6"));
        
        // Creating Panel
        //this.setTop(stepFlow);
        //this.setBottom(allStepsPane);
        launch = new LaunchPane(control);
        this.setCenter(launch);

        setupActionListeners();
        setupPropertyBindings();
    }

    private void setupActionListeners() {
        
    }

    private void setupPropertyBindings() {
        
    }
    
    private Node getStepPane(int stepIdx) {
        switch (stepIdx) {
            default:
            case 0:
                return step1Pane;
            case 1:
                return step2Pane;
            case 2:
                return step3Pane;
            case 3:
                return step4Pane;
            case 4:
                return step5Pane;
            case 5:
                return step6Pane;
        }
    }
    
    public void begin() {
        this.getChildren().remove(launch);
        this.setTop(toolBarBox);
        this.setCenter(stepFlow);
    }

    /**
     * Number of steps of the WZITS Tool
     */
    private final int numSteps = 6;
    /**
     * Layout box for holding the Title and Flow Controls
     */
    private final VBox toolBarBox = new VBox();
    /**
     * Title Label
     */
    private final Label titleLabel = new Label("Work Zone ITS Tool");
    /**
     * Flow of steps for the User.
     */
    private final Pagination stepFlow;

    /**
     *
     */
    private final LaunchPane launch;
    //private final GridPane allStepsPane = new GridPane();
    private final BorderPane step1Pane;
    private final BorderPane step2Pane = new BorderPane();
    private final BorderPane step3Pane = new BorderPane();
    private final BorderPane step4Pane = new BorderPane();
    private final BorderPane step5Pane = new BorderPane();
    private final BorderPane step6Pane = new BorderPane();

    /**
     * Index of the step currently active.
     */
    private SimpleIntegerProperty activeStep = new SimpleIntegerProperty(-1);

    /**
     * Index of the max step filled out by user.
     */
    private int maxStep = 0;

    public static final String COLOR_STEP_HL = "#833C0C";
    public static final String COLOR_STEP = "#ED7D31";
    

}
