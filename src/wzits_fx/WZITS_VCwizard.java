package wzits_fx;

import GUI.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class WZITS_VCwizard extends Application {

    @Override
    public void start(Stage primaryStage) {
        launchVCwizard(primaryStage);
    }

    public void launchVCwizard(Stage primaryStage) {
        MainController control = new MainController(primaryStage);
        VolumeToCapacityWizard wizard = new VolumeToCapacityWizard(control);
        Scene scene = new Scene(wizard);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int maxScreenWidth = gd.getDisplayMode().getWidth();
        int maxScreenHeight = gd.getDisplayMode().getHeight();
        primaryStage.setMinHeight(Math.min(maxScreenHeight,600));
        primaryStage.setMinWidth(Math.min(maxScreenWidth,800));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
