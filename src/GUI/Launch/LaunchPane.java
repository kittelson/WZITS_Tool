/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Launch;

import GUI.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author ltrask
 */
public class LaunchPane extends BorderPane {

    private final MainController controller;

    private final Label titleLabel1, titleLabel2;

    private final TextArea infoText;

    private final Button startButton = new Button("Ok");

    public LaunchPane(final MainController controller) {
        this.controller = controller;
        titleLabel1 = new Label("Work Zone ITS Tool");
        titleLabel1.setMaxWidth(MainController.MAX_WIDTH);
        titleLabel2 = new Label("Welcome");
        titleLabel2.setMaxWidth(MainController.MAX_WIDTH);
        infoText = new TextArea("This area will display the welcome and "
                + "information text.  This area will also provide the user with "
                + "a basic outline of the program and information on how to get "
                + "started.");
        infoText.setWrapText(true);
        infoText.setEditable(false);
        infoText.setFont(Font.font("Calibri", 20));
        startButton.setStyle("-fx-font-size: 20px");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                controller.begin();
            }
        });

        setupFormatting();

        VBox vBox = new VBox();
        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleLabel1, titleLabel2, infoText, startButton);
        this.setCenter(vBox);

        startButton.setDefaultButton(true);
    }

    private void setupFormatting() {
        titleLabel1.getStyleClass().add("launch-title-label-top");
        titleLabel2.getStyleClass().add("launch-title-label-bottom");
        infoText.getStyleClass().add("launch-info-text");
    }
}
