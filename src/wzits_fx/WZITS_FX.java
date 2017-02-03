/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wzits_fx;

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

        primaryStage.setTitle("Work Zone ITS Tool");

        // Creating Panes
        BorderPane borderPane = new MainWindow();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
