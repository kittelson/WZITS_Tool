/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wzits_fx;

import GUI.MainController;
import GUI.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jlake
 */
public class WZITS_FX extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Work Zone Intelligent Transportations Systems Tool");

        // Creating Panes
        MainController control = new MainController(primaryStage);
        BorderPane borderPane = new MainWindow(control);
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1100);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
