package wzits_fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WZITS_VCwizard extends Application {
    @Override
    public void start(Stage primaryStage) {
        VolumeToCapacityWizard wizard = new VolumeToCapacityWizard();

        primaryStage.setScene(new Scene(wizard, 1000, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
