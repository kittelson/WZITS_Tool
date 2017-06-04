/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Launch;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author ltrask
 */
public class LaunchPane extends BorderPane {

    private final MainController controller;

    private final Label welcomeLabel;

    private final ImageView splash;

    private final TextArea infoText;

    private final Button startButton = new Button("Ok");

    public LaunchPane(final MainController controller) {
        this(controller, false);
    }

    public LaunchPane(final MainController controller, final boolean isAboutScreen) {
        this.controller = controller;
        welcomeLabel = NodeFactory.createFormattedLabel("Welcome", "launch-title-label-bottom");
        splash = new ImageView(IconHelper.SPLASH_SCREEN);
        splash.setPreserveRatio(true);
        splash.setSmooth(true);
        splash.setFitHeight(600);
        infoText = new TextArea("Disclaimer: This tool is an interim product for an FHWA Office of Operations Project: DTFH61-12-D-00049 Task Order 5003 - Work Zone ITS Implementation Tool.  "
                + "The tool should be used solely for the purpose of beta testing by FHWA and stakeholder states.  "
                + "For any questions or comments related to this tool, please contact the project principle investigator, Dr. Bastian Schroeder with Kittelson and Associates, Inc. (bschroeder@kittelson.com), or the "
                + "FHWA contract monitor, Mr. Todd Peterson (todd.peterson@dot.gov).");
        infoText.setWrapText(true);
        infoText.setEditable(false);
        infoText.setFont(Font.font("Calibri", 12));
        infoText.getStyleClass().add("launch-info-text");
        infoText.setMinHeight(100);
        infoText.setPrefRowCount(4);
        startButton.setStyle("-fx-font-size: 20px");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!isAboutScreen) {
                    controller.begin();
                }
            }
        });

        VBox vBox = new VBox();
        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);
        if (!isAboutScreen) {
            vBox.getChildren().addAll(welcomeLabel, splash, infoText, startButton);
        } else {
            vBox.getChildren().addAll(splash, infoText);
        }
        this.setCenter(vBox);

        startButton.setDefaultButton(true);

        this.setMaxHeight(650);
        this.setMaxWidth(800);
    }
}
