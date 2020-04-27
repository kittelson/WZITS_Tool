
package GUI.PDFReports;

import GUI.MainController;
import GUI.PDFReports.XML.PDFGenerator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

import java.text.DecimalFormat;

/**
 * @author ltrask
 */
public class PDFReportHelper {

    private static final DecimalFormat formatter1d = new DecimalFormat("#.0");
    private static DecimalFormat formatter_0f = new DecimalFormat("#,##0");
//    private PDFReportDialog parent;
    private int stepIdx;

//    private String scenarioName;
//    private String analyst;
//    private String orgName;

    private String fName = "";

    public PDFReportHelper(PDFReportDialog parent, int stepIdx) {
//        this.parent = parent;
        this.stepIdx = stepIdx;
    }

//    public void setScenarioName(String scenarioName) {
//        this.scenarioName = scenarioName;
//    }
//
//    public void setAnalyst(String analystName) {
//        this.analyst = analystName;
//    }
//
//    public void setOrganization(String orgName) {
//        this.orgName = orgName;
//    }

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

            Thread generatePDFThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        generateReportPDF("fact-sheet-pdf-resources.xml");
                    });
                }
            });

            factSheetReportCreator.createReport(mainController, factSheetIdx);
            generatePDFThread.start();

        }
    }

    private void generateReportPDF(String reportResourcesXML) {
        System.out.println("Actually Creating Report");
        PDFGenerator pdfGenerator = new PDFGenerator();
        try {
            pdfGenerator.convertToPDF(PDFReportHelper.getResFolderLocation() + reportResourcesXML,
                    PDFReportHelper.getResFolderLocation() + "template.xsl",
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

    public static String getResFolderLocation() {
        String location = PDFReportHelper.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        location = location.replaceAll("%20", " ");
        if (location.contains("/build/classes")) {
            location = location.substring(0, location.lastIndexOf("/build")) + "/"; // + "resources" + "/";
        }
        location = location.substring(0, location.lastIndexOf("/")) + "/" + "pdfres";
        File pdfresFolder = new File(location);
        if (!pdfresFolder.exists()) {
            //pdfresFolder.mkdirs();
        }
        return location + "/";
    }

//    public static final String FILE_CHART_HSDTT = "hourly-system-delay.png";
    public static final String FILE_FS1_WZIMAGE = "fact_sheet1_wz.jpg";

}
