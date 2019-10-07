/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Step;

import GUI.MainController;
import GUI.Tables.Step1TableHelper;
import GUI.Tables.Step2TableHelper;
import GUI.Tables.Step3TableHelper;
import GUI.Tables.Step4TableHelper;
import GUI.Tables.Step5TableHelper;
import GUI.Tables.Step6TableHelper;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.bouncycastle.util.test.Test;

/**
 *
 * @author ltrask
 */
public class SummaryPanel {

//    public static Node createStepSummary(MainController mc) {
//        TabPane tp = new TabPane();
//        Tab fs1 = new Tab();
//        fs1.setText("Fact Sheet 1");
//        fs1.setContent(Step1TableHelper.createStepSummary1(mc));
//        Tab fs2 = new Tab();
//        fs2.setText("Fact Sheet 2");
//        fs2.setContent(Step1TableHelper.createStepSummary2(mc));
//        Tab fs3 = new Tab();
//        fs3.setText("Fact Sheet 3");
//        fs3.setContent(Step2TableHelper.createStepSummary1(mc));
//        Tab fs4 = new Tab();
//        fs4.setText("Fact Sheet 4");
//        fs4.setContent(Step2TableHelper.createStepSummary2(mc));
//        Tab fs5 = new Tab();
//        fs5.setText("Fact Sheet 5");
//        fs5.setContent(Step3TableHelper.createStepSummary(mc));
//        Tab fs6 = new Tab();
//        fs6.setText("Fact Sheet 6");
//        fs6.setContent(Step4TableHelper.createStepSummary(mc));
//        Tab fs7 = new Tab();
//        fs7.setText("Fact Sheet 7");
//        fs7.setContent(Step5TableHelper.createStepSummary(mc));
//        Tab fs8 = new Tab();
//        fs8.setText("Fact Sheet 8");
//        fs8.setContent(Step6TableHelper.createStepSummary(mc));
//
//        tp.getTabs().addAll(fs1, fs2, fs3, fs4, fs5, fs6, fs7, fs8);
//
//        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
//
//        return tp;
//    }

    public static Node createStepSummary(MainController mc) {
        final JFXTabPane inputTabPanel = new JFXTabPane();
        Tab fs1 = new Tab();
        fs1.setText("Fact Sheet 1");
        fs1.setContent(Step1TableHelper.createStepSummary1(mc));
        Tab fs2 = new Tab();
        fs2.setText("Fact Sheet 2");
        fs2.setContent(Step1TableHelper.createStepSummary2(mc));
        Tab fs3 = new Tab();
        fs3.setText("Fact Sheet 3");
        fs3.setContent(Step2TableHelper.createStepSummary1(mc));
        Tab fs4 = new Tab();
        fs4.setText("Fact Sheet 4");
        fs4.setContent(Step2TableHelper.createStepSummary2(mc));
        Tab fs5 = new Tab();
        fs5.setText("Fact Sheet 5");
        fs5.setContent(Step3TableHelper.createStepSummary(mc));
        Tab fs6 = new Tab();
        fs6.setText("Fact Sheet 6");
        fs6.setContent(Step4TableHelper.createStepSummary(mc));
        Tab fs7 = new Tab();
        fs7.setText("Fact Sheet 7");
        fs7.setContent(Step5TableHelper.createStepSummary(mc));
        Tab fs8 = new Tab();
        fs8.setText("Fact Sheet 8");
        fs8.setContent(Step6TableHelper.createStepSummary(mc));

        inputTabPanel.getTabs().addAll(fs1, fs2, fs3, fs4, fs5, fs6, fs7, fs8);
        inputTabPanel.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        BorderPane inputLabel = new BorderPane();
        inputLabel.getStylesheets().add("GUI/CSS/globalStyle.css");
        inputLabel.setCenter(inputTabPanel);
        inputLabel.setMinHeight(225);

        return inputLabel;
    }
}
