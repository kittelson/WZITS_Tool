/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author ltrask
 */
public class PDFIOHelper {

    private static final float marginXwidth = 50f;
    private static final float leftMarginX = 50f;
    private static final float rightMarginX = 550f;
    private static final float bottomMarginY = 45f;
    private static final float topMarginY = 735f;

    public static void writeStepSummary(MainController mc, int factSheetIdx) {
        Node n = mc.goToFactSheet(factSheetIdx);
        FileChooser fc = new FileChooser();
        fc.setTitle("Save WZITS Tool Project");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File (.pdf)", "*.pdf"));
        if (mc.getProject().getSaveFile() != null) {
            File initDir = mc.getProject().getSaveFile().getParentFile();
            if (initDir.isDirectory()) {
                fc.setInitialDirectory(initDir);
            }
        }
        File saveFile = fc.showSaveDialog(mc.getWindow());  //mc.getMainWindow()
        if (saveFile != null) {

            BorderPane bp = (BorderPane) ((ScrollPane) n).getContent();
            //Scene scene = new Scene(bp);
            //n.applyCss();
            //bp.applyCss();
            //WritableImage wi = scene.snapshot(null);

            WritableImage wi = bp.snapshot(new SnapshotParameters(), null);

            double prefHeight = wi.getHeight();
            double prefWidth = wi.getWidth();
            //System.out.println(String.valueOf(prefHeight));
            //System.out.println(String.valueOf(prefWidth));
            BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            boolean exportSuccess = false;
            try {
                //ImageIO.write(bi, "png", new File("test.png"));
                PDPageContentStream content = new PDPageContentStream(doc, page);
                PDImageXObject ximage = LosslessFactory.createFromImage(doc, bi);
                int drawWidth = (int) Math.min(600, Math.round(0.4 * prefWidth));
                int drawHeight = (int) Math.min(695, Math.round(0.5 * prefHeight));
                content.drawImage(ximage, leftMarginX, topMarginY - drawHeight, drawWidth, drawHeight);
                content.fillAndStroke();
                content.close();
                doc.save(saveFile);
                doc.close();
                exportSuccess = true;
            } catch (IOException ie) {
                System.out.println("ERROR");
            }
            if (exportSuccess) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("WZITS Tool");
                al.setHeaderText("Fact sheet export successful");
                al.showAndWait();
            } else {
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("WZITS Tool");
                al.setHeaderText("Fact sheet export failed");
                al.showAndWait();
            }
        }
    }

}
