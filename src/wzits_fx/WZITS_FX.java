/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wzits_fx;

import GUI.MainController;
import GUI.MainWindow;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        BorderPane mainPane = new MainWindow(control, true);
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int maxScreenWidth = gd.getDisplayMode().getWidth();
        int maxScreenHeight = gd.getDisplayMode().getHeight();
        primaryStage.setMinHeight(Math.min(maxScreenHeight, 600));
        primaryStage.setMinWidth(Math.min(maxScreenWidth, 800));
        primaryStage.setMaxHeight(maxScreenHeight);
        primaryStage.setMaxWidth(maxScreenWidth);

        //Image wzitsIcon16 = new Image(WZITS_FX.class.getResourceAsStream("/GUI/Icon/wzits_icon_16.png"));
        //Image wzitsIcon32 = new Image(WZITS_FX.class.getResourceAsStream("/GUI/Icon/wzits_icon_32.png"));
        //Image wzitsIcon48 = new Image(WZITS_FX.class.getResourceAsStream("/GUI/Icon/wzits_icon_48.png"));
        Image wzitsIcon64 = new Image(WZITS_FX.class.getResourceAsStream("/GUI/Icon/wzits_icon_64.png"), 64, 64, true, true);
        //Image wzitsIcon40 = new Image(WZITS_FX.class.getResourceAsStream("/GUI/Icon/19419_logo-02.png"), 120, 120, true, true);
        //primaryStage.getIcons().addAll(wzitsIcon16, wzitsIcon32, wzitsIcon48, wzitsIcon64);
        primaryStage.getIcons().addAll(wzitsIcon64);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
