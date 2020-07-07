/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wzits_fx;

import GUI.Helper.IOHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.MainWindow;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author jlake
 */
public class WZITS_FX extends Application {

    /**
     * Version of the WZITS Tool
     */
    public static String VERSION = "20170930";

    /**
     * Version of GUI and restrictions
     */
    public static final String LICENSE_VERSION = "DEVELOP_KAI";
    /**
     * Expiration Date
     */
    public static final Date EXPIRE = new Date(1506834000000L); // Expire on 10/01/2017  //1506834000000L

    @Override
    public void start(Stage primaryStage) {

        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        if (false) {  // time.compareTo(EXPIRE) > 0
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("WZITS Tool Testing Period Expired");
            al.setHeaderText("This version of the WZITS Tool has expired.");
            al.showAndWait();
            primaryStage.close();
        } else {
            launchWZITSTool(primaryStage);
        }

    }

    public void launchWZITSTool(Stage primaryStage) {
        primaryStage.setTitle("Work Zone Intelligent Transportations Systems Tool");

        // Creating Panes
        MainController control = new MainController(primaryStage);
        BorderPane mainPane = new MainWindow(control, true);
        StackPane rootStackPane = new StackPane();
        control.setRootStackPane(rootStackPane);
        rootStackPane.getChildren().add(mainPane);
        Scene scene = new Scene(rootStackPane);
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
//                Alert al = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//                al.setTitle("WZITS Tool");
//                al.setHeaderText("Do you want to save the WZITS project before exiting?");
//                al.setContentText("");
//                Optional<ButtonType> result = al.showAndWait();
//                if (result.get() == ButtonType.YES) {
//                    int saveResult = control.saveProject();
//                    IOHelper.confirm(saveResult, null);
//                    if (saveResult == IOHelper.SAVE_CANCELLED) {
//                        we.consume();
//                    }
//                } else if (result.get() == ButtonType.CANCEL) {
//                    we.consume();
//                }
                we.consume(); // Stops the program from closing
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(NodeFactory.createFormattedLabel("Exiting WZITS Tool", "modal-title"));
                content.setBody(NodeFactory.createFormattedLabel("Save project before exiting?", ""));

                JFXDialog dlg = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);

                JFXButton yesButton = new JFXButton("Yes");
                yesButton.setStyle("-fx-font-size: 12pt;");
                yesButton.setOnAction(actionEvent -> {
                    dlg.close();
                    final int saveResult = control.saveProject();
                    Runnable closeAfterSaving = new Runnable() {
                        @Override
                        public void run() {
                            if (saveResult != IOHelper.SAVE_CANCELLED) {
                                primaryStage.close();
                            }
                        }
                    };
                    IOHelper.confirm(saveResult, closeAfterSaving);
                });
                JFXButton noButton = new JFXButton("No");
                noButton.setStyle("-fx-font-size: 12pt;");
                noButton.setOnAction(actionEvent -> {
                    dlg.close();
                    dlg.setOnDialogClosed(jfxDialogEvent -> {
                        primaryStage.close();
                    });
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
        });
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
