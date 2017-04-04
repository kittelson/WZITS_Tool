/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import javafx.scene.control.Label;

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

    public static int NAVIGATOR_MAX_WIDTH = 200;

}
