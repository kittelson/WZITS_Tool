/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import java.util.Optional;
import java.util.regex.Pattern;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author ltrask
 */
public class NodeFactory {

    public static Label createFormattedLabel(String text, String style, String tooltipText, Boolean tooltipIcon) {
        return createFormattedLabel(text, style, tooltipText, tooltipIcon, Color.GREY, 10);
    }

    public static Label createFormattedLabel(String text, String style, String tooltipText, Boolean tooltipIcon, Color iconColor, int iconSize) {
        Label lbl = new Label(text);
        lbl.setMaxWidth(MainController.MAX_WIDTH);
        lbl.setMaxHeight(MainController.MAX_HEIGHT);
        lbl.getStyleClass().add(style);
        Tooltip labelTooltip = new Tooltip(tooltipText);
        labelTooltip.setShowDuration(Duration.INDEFINITE);
        if (tooltipIcon) {
            FontIcon tooltipIconNode = NodeFactory.createIcon(FontAwesomeRegular.QUESTION_CIRCLE, iconColor, iconSize);
            labelTooltip.setShowDelay(Duration.ZERO);
            labelTooltip.setStyle("-fx-font-size: 12pt");
            labelTooltip.setMaxWidth(500);
            labelTooltip.setWrapText(true);
            Tooltip.install(tooltipIconNode, labelTooltip);
            lbl.setGraphic(tooltipIconNode);
            lbl.setContentDisplay(ContentDisplay.RIGHT);
        } else {
            Tooltip.install(lbl, labelTooltip);
        }
        return lbl;
    }

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
                // Update
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(NodeFactory.createFormattedLabel(hString, "modal-title"));
                JFXTextArea txtArea = new JFXTextArea(sp.get());
                txtArea.setPromptText("Enter additional comments here...");
                txtArea.getStyleClass().add("modal-comment-text-area");
                content.setBody(txtArea);
                JFXDialog dlg = new JFXDialog(MainController.getRootStackPane(), content, JFXDialog.DialogTransition.CENTER);
                dlg.setStyle("-fx-font-size: 14pt;");
                JFXButton saveButton = new JFXButton("Save Comments");
                saveButton.getStyleClass().add("modal-pane-button");
                saveButton.setOnAction(actionEvent -> {
                    sp.setValue(txtArea.getText());
                    dlg.close();
                });
                JFXButton closeButton = new JFXButton("Close");
                closeButton.getStyleClass().add("comment-pane-buttonClose");
                closeButton.setOnAction(actionEvent -> {
                    dlg.close();
                });
                content.getActions().addAll(saveButton, closeButton);
                dlg.setOverlayClose(false);
                dlg.show();
            }
        });
        return commLink;
    }

    public static void assignDefaultRequiredFieldValidator(JFXComboBox validatableControl, String message, boolean triggerOnFocusLost) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage(message);
        requiredFieldValidator.setIcon(NodeFactory.createIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE, Color.web("#D34336")));
        validatableControl.setValidators(requiredFieldValidator);
        validatableControl.valueProperty().addListener((o, oldVal, newVal)->{
            validatableControl.validate();
        });
        if (triggerOnFocusLost) {
            try {
                ((Node) validatableControl).focusedProperty().addListener((o, oldVal, newVal) -> {
                    if (!oldVal && newVal) {
                        validatableControl.validate();
                    }
                });
            } catch (ClassCastException cce) {
                System.out.println("Warning: cannot assign validation trigger on focus lost");
            }
        }
    }

    public static void assignDefaultRequiredFieldValidator(JFXTextField validatableControl, String message, boolean triggerOnFocusLost) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
//        requiredFieldValidator.setStyle("-fx-font-size: 10pt");
        requiredFieldValidator.setMessage(message);
        requiredFieldValidator.setIcon(NodeFactory.createIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE, Color.web("#D34336")));
        validatableControl.setValidators(requiredFieldValidator);
        validatableControl.textProperty().addListener((o, oldVal, newVal)->{
            validatableControl.validate();
        });
        if (triggerOnFocusLost) {
            try {
                ((Node) validatableControl).focusedProperty().addListener((o, oldVal, newVal) -> {
                    if (!oldVal && newVal) {
                        validatableControl.validate();
                    }
                });
            } catch (ClassCastException cce) {
                System.out.println("Warning: cannot assign validation trigger on focus lost");
            }
        }
    }

    public static FontIcon createIcon(Ikon iconId, Color iconColor) {
        FontIcon icon = new FontIcon(iconId);
        icon.setIconColor(iconColor);
        return icon;
    }

    public static FontIcon createIcon(Ikon iconId, Color iconColor, int size) {
        FontIcon icon = new FontIcon(iconId);
        icon.setIconColor(iconColor);
        icon.setIconSize(size);
        return icon;
    }

    public static void setTextFieldIntegerInputOnly(TextField tf) {
        // Adding listener that only allows for numeric input
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        // Adding formatter
        final char seperatorChar = ',';
        final Pattern p = Pattern.compile("[0-9" + seperatorChar + "]*");
        tf.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) {
                return c; // no need for modification, if only the selection changes
            }
            String newText = c.getControlNewText();
            if (newText.isEmpty()) {
                return c;
            }
            if (!p.matcher(newText).matches()) {
                return null; // invalid change
            }

            // invert everything before the range
            int suffixCount = c.getControlText().length() - c.getRangeEnd();
            int digits = suffixCount - suffixCount / 4;
            StringBuilder sb = new StringBuilder();

            // insert seperator just before caret, if necessary
            if (digits % 3 == 0 && digits > 0 && suffixCount % 4 != 0) {
                sb.append(seperatorChar);
            }

            // add the rest of the digits in reversed order
            for (int i = c.getRangeStart() + c.getText().length() - 1; i >= 0; i--) {
                char letter = newText.charAt(i);
                if (Character.isDigit(letter)) {
                    sb.append(letter);
                    digits++;
                    if (digits % 3 == 0) {
                        sb.append(seperatorChar);
                    }
                }
            }

            // remove seperator char, if added as last char
            if (digits % 3 == 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.reverse();
            int length = sb.length();

            // replace with modified text
            c.setRange(0, c.getRangeEnd());
            c.setText(sb.toString());
            c.setCaretPosition(length);
            c.setAnchor(length);

            return c;
        }));

    }

    public static void setTextFieldDecimalInputOnly(TextField tf) {
        // Adding listener that only allows for numeric input
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
//                if (!newValue.matches("\\d*")) {
//                    tf.setText(newValue.replaceAll("[^\\d]", ""));
//                }
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("^(\\d*\\.)|\\D", "$1"));
                }
            }
        });
    }

    public static int NAVIGATOR_MAX_WIDTH = 200;

}
