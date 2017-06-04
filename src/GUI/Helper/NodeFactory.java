/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import java.util.Optional;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author ltrask
 */
public class NodeFactory {

    public static Label createFormattedLabel(String text, String formatStyle) {
        Label lbl = new Label(text);
        lbl.setMaxWidth(MainController.MAX_WIDTH);
        lbl.setMaxHeight(MainController.MAX_HEIGHT);
        lbl.getStyleClass().add(formatStyle);
        return lbl;
    }

    public static Label createFormattedDescLabel(String text, String formatStyle, int fontSize, int numLines) {
        Label lbl = new Label(text);
        lbl.setMaxWidth(MainController.MAX_WIDTH);
        lbl.setMinHeight(numLines * (fontSize + 2));
        lbl.setMaxHeight(numLines * (fontSize + 2));
        lbl.getStyleClass().add(formatStyle);
        return lbl;
    }

    public static Node createCommentLink(String hString, StringProperty sp) {
        Hyperlink commLink = new Hyperlink("Add Comment");
        commLink.getStyleClass().add("wz-comment-hyperlink");
        commLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ae) {
                Alert al = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
                al.initOwner(MainController.getWindow());
                al.setTitle("Additional Comments");
                al.setHeaderText(hString);
                TextArea commTA = new TextArea(sp.get());
                commTA.setPromptText("Enter additional comments here...");
                //commTA.textProperty().bindBidirectional(sp);
                al.getDialogPane().setContent(commTA);
                Optional<ButtonType> result = al.showAndWait();
                if (result.get() == ButtonType.OK) {
                    sp.setValue(commTA.getText());
                }
            }
        });
        return commLink;
    }

    public static int NAVIGATOR_MAX_WIDTH = 200;

}
