/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import GUI.Helper.ColorHelper;
import GUI.Helper.NodeFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import GUI.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

/**
 *
 * @author ltrask
 */
public class FeasibilityMatrix implements Serializable {

    private static final long serialVersionUID = 123456789L;

    private SimpleIntegerProperty feasibility = new SimpleIntegerProperty(0);

    private final ObservableList<QuestionOption> qFeasOptList;
    private final ObservableList<QuestionYN> qFeasYNList;

    public FeasibilityMatrix(ObservableList<QuestionOption> qFeasOptList, ObservableList<QuestionYN> qFeasYNList) {
        this.qFeasOptList = qFeasOptList;
        this.qFeasYNList = qFeasYNList;
        FeasibilityMatrix.loadJSON();
    }

    public static JSONObject loadJSON() {
        JSONParser parser = new JSONParser();
        JSONObject returnJSON = null;
        try {
            File customMatrix = new File(MainController.getScoringMatrixFolder() + "feasibilityCustomMatrix.json");
            File defaultMatrix = new File(MainController.getScoringMatrixFolder() + "feasibilityDefaultMatrix.json");
            if (customMatrix.exists()) {
                returnJSON = (JSONObject) parser.parse(new FileReader(customMatrix));
            } else {
                returnJSON = (JSONObject) parser.parse(new FileReader(defaultMatrix));
            }
//            System.out.println(returnJSON);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return returnJSON;
    }

    public void computeFeasibility() {
        int feasScore = 0;
        for (QuestionOption qOpt : qFeasOptList) {
            feasScore += qOpt.getOptionScore();
        }
        for (QuestionYN qYN : qFeasYNList) {
            feasScore += qYN.getScore();
        }
        feasibility.set(feasScore);
    }

    public Node createSummaryPanel(Project project) {
        this.computeFeasibility();
        // Create Score and Score Description
        // -- Label to show numeric score
        Label scoreLabel = NodeFactory.createFormattedLabel(String.valueOf(feasibility.get()), "feasibility-output-title");
        scoreLabel.textProperty().bind(feasibility.asString());
        scoreLabel.setMinWidth(150);
        // -- Label to show score description
        final Label labelDescription = NodeFactory.createFormattedLabel(DESC_30PLUS, "feasibility-output-desc");
        labelDescription.setAlignment(Pos.CENTER);
        labelDescription.setWrapText(true);
        Tooltip labelDescriptionToolTip = new Tooltip();
        Tooltip.install(labelDescription, labelDescriptionToolTip);
        if (feasibility.get() >= 30) {
            labelDescription.setText(DESC_30PLUS);
            labelDescriptionToolTip.setText(DESC_30PLUS);
        } else if (feasibility.get() >= 10) {
            labelDescription.setText(DESC_10_TO_29);
            labelDescriptionToolTip.setText(DESC_10_TO_29);
        } else {
            labelDescription.setText(DESC_LESS_THAN_10);
            labelDescriptionToolTip.setText(DESC_LESS_THAN_10);
        }
        feasibility.addListener((ov, oldVal, newVal) -> {
            if (newVal.intValue() >= 30) {
                labelDescription.setText(DESC_30PLUS);
                labelDescriptionToolTip.setText(DESC_30PLUS);
            } else if (newVal.intValue() >= 10) {
                labelDescription.setText(DESC_10_TO_29);
                labelDescriptionToolTip.setText(DESC_10_TO_29);
            } else {
                labelDescription.setText(DESC_LESS_THAN_10);
                labelDescriptionToolTip.setText(DESC_LESS_THAN_10);
            }
        });
        // Creation additional comments section
        Label labelCommentDisplay = NodeFactory.createFormattedLabel("", "feasibility-justification");
        labelCommentDisplay.textProperty().bind(project.feasibilityJustificationProperty());
        Hyperlink addCommentButton = new Hyperlink("Edit/View");
        addCommentButton.setGraphic(NodeFactory.createIcon(FontAwesomeSolid.PENCIL_ALT, Color.web(ColorHelper.WZ_ORANGE)));
        addCommentButton.setAlignment(Pos.CENTER);
        addCommentButton.setMaxHeight(Integer.MAX_VALUE);
        addCommentButton.getStyleClass().add("feasibility-comment-hyperlink");
        addCommentButton.setOnAction(actionEvent -> {
            JFXTextArea txtArea = new JFXTextArea(labelCommentDisplay.getText());
            txtArea.getStyleClass().add("modal-comment-text-area");
            JFXDialogLayout summaryDialog = new JFXDialogLayout();
            summaryDialog.setHeading(NodeFactory.createFormattedLabel("Feasibility Assessment: Additional Justification", "modal-title"));
            summaryDialog.setBody(txtArea);
            JFXDialog dialogModal = new JFXDialog(MainController.getRootStackPane(), summaryDialog, JFXDialog.DialogTransition.CENTER);
            JFXButton saveComment = new JFXButton("Save Comments");
            saveComment.getStyleClass().add("modal-pane-button");
            saveComment.setOnAction(e -> {
//                labelCommentDisplay.setText(txtArea.getText());
                project.setFeasibilityJustification(txtArea.getText());
                dialogModal.close();
            });
            JFXButton btnCloseModal = new JFXButton("Cancel");
            btnCloseModal.getStyleClass().add("comment-pane-buttonClose");
            btnCloseModal.setOnAction(actionEvent1 -> dialogModal.close());
            summaryDialog.setMinWidth(600);
            dialogModal.setOverlayClose(false);
            dialogModal.show();
            summaryDialog.setActions(btnCloseModal, saveComment);
        });

        BorderPane scoreDisplay = new BorderPane();
        Label scoreTitleLabel = NodeFactory.createFormattedLabel("Feasibility Score:", "feasibility-output-title-bold");
        scoreTitleLabel.setMaxHeight(Integer.MAX_VALUE);
        scoreLabel.setMaxHeight(Integer.MAX_VALUE);
        labelDescription.setMaxHeight(Integer.MAX_VALUE);
        GridPane.setVgrow(scoreDisplay, Priority.ALWAYS);
        GridPane.setVgrow(labelDescription, Priority.ALWAYS);
        scoreDisplay.setCenter(scoreTitleLabel);
        scoreDisplay.setRight(scoreLabel);
        GridPane scoreGrid = new GridPane();
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setPercentWidth(50);
        scoreGrid.getColumnConstraints().addAll(cc1, cc2);
        scoreGrid.add(scoreDisplay, 0, 0);
        scoreGrid.add(labelDescription, 1, 0);

        BorderPane additionalCommentsPane = new BorderPane();
        additionalCommentsPane.setStyle("-fx-background-color: white");
        Label additionalCommentsTitleLabel = NodeFactory.createFormattedLabel("Additional Justification:", "label-additional-justification");
        BorderPane.setMargin(additionalCommentsTitleLabel, new Insets(0, 10, 0, 10));
        BorderPane additionalCommentsTitlePane = new BorderPane();
        additionalCommentsTitlePane.setCenter(additionalCommentsTitleLabel);
        additionalCommentsTitlePane.setRight(addCommentButton);
        additionalCommentsPane.setLeft(additionalCommentsTitlePane);
        ScrollPane scrollPane = new ScrollPane();
        labelCommentDisplay.setMaxHeight(Integer.MAX_VALUE);
        scrollPane.setContent(labelCommentDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        BorderPane scrollWrapper = new BorderPane();
        scrollWrapper.getStyleClass().add("feasibility-justification-card");
        scrollWrapper.setCenter(scrollPane);
        additionalCommentsPane.setCenter(scrollWrapper);

        scoreGrid.setMinHeight(55);
        additionalCommentsPane.setMinHeight(55);
        additionalCommentsPane.setMaxHeight(100);
        BorderPane summaryPane = new BorderPane();
        summaryPane.setCenter(scoreGrid);
        summaryPane.setBottom(additionalCommentsPane);

        return summaryPane;
    }

    public SimpleIntegerProperty feasibilityProperty() {
        return feasibility;
    }

    public int getFeasibility() {
        return feasibility.get();
    }

//    private void writeObject(ObjectOutputStream s) throws IOException {
//        s.writeInt(getFeasibility());
//        s.writeInt(qFeasYNList.size());
//        for (int i = 0; i < qFeasYNList.size(); i++) {
//            s.writeObject(qFeasYNList.get(i));
//        }
//        s.writeInt(qFeasOptList.size());
//        for (int i = 0; i < qFeasOptList.size(); i++) {
//            s.writeObject(qFeasOptList.get(i));
//        }
//    }
//
//    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
//        feasibility = new SimpleIntegerProperty(s.readInt());
//        int numYN = s.readInt();
//        qFeasYNList = FXCollections.observableArrayList();
//        for (int i = 0; i < numYN; i++) {
//            qFeasYNList.add((QuestionYN) s.readObject());
//        }
//        int numOpt = s.readInt();
//        qFeasOptList = FXCollections.observableArrayList();
//        for (int i = 0; i < numOpt; i++) {
//            qFeasOptList.add((QuestionOption) s.readObject());
//        }
//    }
    public static final String DESC_30PLUS = "30 or more: ITS is likely to provide significant benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_10_TO_29 = "10 to 29: ITS may provide some benefits and should be considered as a treatment to mitigate impacts.";
    public static final String DESC_LESS_THAN_10 = "Less than 10: ITS may not provide enough benefit to justify the associated costs.";

}
