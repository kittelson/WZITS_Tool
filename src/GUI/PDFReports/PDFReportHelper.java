
package GUI.PDFReports;

import GUI.MainController;
import GUI.PDFReports.XML.PDFGenerator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.DecimalFormat;

/**
 * @author ltrask
 */
public class PDFReportHelper {

    private static final DecimalFormat formatter1d = new DecimalFormat("#.0");
    private static DecimalFormat formatter_0f = new DecimalFormat("#,##0");
    private int stepIdx;

    private String fName = "";

    public PDFReportHelper(PDFReportDialog parent, int stepIdx) {
        this.stepIdx = stepIdx;
    }

    public void createFactSheet(MainController mainController, int factSheetIdx) {
        FileChooser fc = new FileChooser();
        String lastPDFSaveLoc = MainController.getLastSaveLocation("pdf");
        if (lastPDFSaveLoc!= null) {
            fc.setInitialDirectory(new File(lastPDFSaveLoc));
        }
        fc.setTitle("Select Report Save Location");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File saveFile = fc.showSaveDialog(MainController.getStage());
        if (saveFile != null) {
            final String fName = saveFile.getAbsolutePath() + (!saveFile.getAbsolutePath().endsWith(".pdf") ? ".pdf" : "");
            setSelectedFileName(fName);
            FactSheetReportCreator factSheetReportCreator = new FactSheetReportCreator();

            Thread generatePDFThread = new Thread(() -> Platform.runLater(() -> {
                generateReportPDF("fact-sheet-pdf-resources.xml");
            }));

            factSheetReportCreator.createReport(mainController, factSheetIdx);
            generatePDFThread.start();

        }
    }

    private void generateReportPDF(String reportResourcesXML) {
        System.out.println("Actually Creating Report");
        PDFGenerator pdfGenerator = new PDFGenerator();
        try {
            Path pdfResourcePath = MainController.getResFolderLocation();
            pdfGenerator.convertToPDF(pdfResourcePath.resolve(reportResourcesXML).toString(),
                    pdfResourcePath.resolve("template.xsl").toString(),
                    fName);
        } catch (Exception e) {
            e.printStackTrace();
//            JOptionPane.showMessageDialog(ProjectController.getMainWindow(),
//                    "<HTML><center>Something went wrong while saving the report."
//                    + " If a previously generated report<br>with the same "
//                    + "file name is currently open, please close it before <br>"
//                    + "attempting to create a new report.",
//                    "Failed to Save Report",
//                    JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Failed to Save Report");
            alert.setHeaderText("Failed to Save Report");
            alert.setContentText("<HTML><center>Something went wrong while saving the report."
                            + " If a previously generated report<br>with the same "
                            + "file name is currently open, please close it before <br>"
                            + "attempting to create a new report.");
        } finally {
//            parent.done();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success - Fact Sheet PDF Created");
            alert.setHeaderText("Fact Sheet PDF Created Successfully");
        }
    }

    private void setSelectedFileName(String selfname) {
        this.fName = selfname;
    }

//    public static final String FILE_CHART_HSDTT = "hourly-system-delay.png";
    public static final String FILE_FS1_WZIMAGE = "project_photo.jpg";
    public static final String FILE_FS2_WZIMAGE = "concept_of_operations.jpg";

}
