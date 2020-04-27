package GUI.PDFReports;

import com.jfoenix.controls.JFXProgressBar;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PDFReportDialog {

    private Label displayLabel;
    private JFXProgressBar progress;
    private BorderPane contentPanel;
    private int maxProgress = 3;

    public void displayPDFReportStart() {
        displayLabel.setText("Generating PDF Report...");
        progress.setProgress(-1.0);
    }

    public void pdfCreationStarted() {
        setDisplayText("Initializing...");
        setDialogControlsEnabled(false);
    }

    public void setDisplayText(String text) {
        displayLabel.setText(text);
    }

    public void setProgressMax(int max) {
        maxProgress = max;
    }

    public void resetProgress() {
        progress.setProgress(0.0);
    }

    public void incrementProgress() {
        progress.setProgress(progress.getProgress() + 1 / (float) maxProgress);
    }

    public void done() {
        displayLabel.setText("PDF Generated...");
        progress.setProgress(1.0);
        setDialogControlsEnabled(true);
    }

    private void setDialogControlsEnabled(boolean enabled) {
        //setPanelEnabled(userSpecPanel, enabled);
        setPanelEnabled(contentPanel, enabled);
    }

    private void setPanelEnabled(Pane panel, Boolean isEnabled) {
        if (panel == null) {
            return;
        }
        panel.setDisable(!isEnabled);

        ObservableList<Node> components = panel.getChildren();

        for (Node component : components) {
            if (component instanceof Pane) {
                setPanelEnabled((Pane) component, isEnabled);
            }
            component.setDisable(!isEnabled);
        }
    }
}
