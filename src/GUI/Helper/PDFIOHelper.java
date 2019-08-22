/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import core.Project;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import wzits_fx.WZITS_FX;

/**
 *
 * @author ltrask
 */
public class PDFIOHelper {

    private static final float MARGIN_XWIDTH = 58f;
    private static final float MARGIN_LEFT_X = 58f;
    private static final float MARGIN_RIGHT_X = 550f;
    private static final float MARGIN_BOTTOM_Y = 45f;
    private static final float MARGIN_TOP_Y = 735f;
    private static final double WIDTH_FACTOR = 0.5;
    private static final double HEIGHT_FACTOR = 0.5;
    private static final int MAX_DRAW_HEIGHT = 675;
    private static final int MAX_DRAW_WIDTH = 500;

    public static void writeSummaryReport(MainController mc) {

        mc.selectStep(6);
        FileChooser fc = new FileChooser();
        fc.setTitle("Save WZITS Tool Project");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File (.pdf)", "*.pdf"));
        if (mc.getProject().getSaveFile() != null) {
            File initDir = mc.getProject().getSaveFile().getParentFile();
            if (initDir.isDirectory()) {
                fc.setInitialDirectory(initDir);
            }
        }
        File saveFile = fc.showSaveDialog(MainController.getWindow());  //mc.getMainWindow()
        if (saveFile != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    boolean exportSuccess = true;
                    try {
                        PDDocument doc = new PDDocument();
                        for (int factSheetIdx = 1; factSheetIdx <= 8; factSheetIdx++) {

                            Node n = mc.goToFactSheet(factSheetIdx, true);
                            MainController.getStage().show();
                            BorderPane bp = (BorderPane) ((ScrollPane) n).getContent();

                            PDPage page = new PDPage();
                            doc.addPage(page);

                            PDPageContentStream content = new PDPageContentStream(doc, page);

                            WritableImage wi = bp.snapshot(new SnapshotParameters(), null);

                            double prefHeight = wi.getHeight();
                            double prefWidth = wi.getWidth();
                            BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);

                            PDImageXObject ximage = LosslessFactory.createFromImage(doc, bi);
                            int drawWidth = (int) Math.min(MAX_DRAW_WIDTH, Math.round(WIDTH_FACTOR * prefWidth));
                            int drawHeight = (int) Math.round(HEIGHT_FACTOR * prefHeight);
                            int numPages = (int) Math.ceil(drawHeight / MAX_DRAW_HEIGHT);
                            drawHeight = (int) Math.min(MAX_DRAW_HEIGHT, drawHeight);
                            content.drawImage(ximage, MARGIN_LEFT_X, MARGIN_TOP_Y - drawHeight, drawWidth, drawHeight);
                            content.fillAndStroke();
                            content.close();
                        }
                        drawReportHeaderFooter(doc, mc.getProject(), true);
                        doc.save(saveFile);
                        doc.close();
                    } catch (IOException ie) {
                        System.out.println("ERROR");
                        exportSuccess = false;
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
                    mc.selectStep(6);
                }
            });
        }
    }

    public static void writeStepSummary(MainController mc, int factSheetIdx) {
        Node n = mc.goToFactSheet(factSheetIdx, false);
        FileChooser fc = new FileChooser();
        fc.setTitle("Save WZITS Tool Project");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File (.pdf)", "*.pdf"));
        if (mc.getProject().getSaveFile() != null) {
            File initDir = mc.getProject().getSaveFile().getParentFile();
            if (initDir.isDirectory()) {
                fc.setInitialDirectory(initDir);
            }
        }
        File saveFile = fc.showSaveDialog(MainController.getWindow());  //mc.getMainWindow()
        if (saveFile != null) {

            BorderPane bp = (BorderPane) ((ScrollPane) n).getContent();
            //Scene scene = new Scene(bp);
            //n.applyCss();
            //bp.applyCss();
            //WritableImage wi = scene.snapshot(null);

            WritableImage wi = bp.snapshot(new SnapshotParameters(), null);

            double prefHeight = wi.getHeight();
            double prefWidth = wi.getWidth();

            BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            boolean exportSuccess = false;
            try {
                //ImageIO.write(bi, "png", new File("test.png"));
                PDPageContentStream content = new PDPageContentStream(doc, page);
                PDImageXObject ximage = LosslessFactory.createFromImage(doc, bi);
                int drawWidth = (int) Math.min(MAX_DRAW_WIDTH, Math.round(WIDTH_FACTOR * prefWidth));
                int drawHeight = (int) Math.round(HEIGHT_FACTOR * prefHeight);
                int numPages = (int) Math.ceil(drawHeight / MAX_DRAW_HEIGHT);
                drawHeight = (int) Math.min(MAX_DRAW_HEIGHT, drawHeight);
                content.drawImage(ximage, MARGIN_LEFT_X, MARGIN_TOP_Y - drawHeight, drawWidth, drawHeight);
                content.fillAndStroke();
                content.close();
                drawReportHeaderFooter(doc, mc.getProject(), true);
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

    private static void drawReportHeaderFooter(PDDocument report, Project proj, boolean headerOnFirstPage) {

        int pageIdx = headerOnFirstPage ? 0 : 1;
        int marginOffset = 10;
        try {
            PDPageContentStream cs;
            for (int p = pageIdx; p < report.getNumberOfPages(); p++) {
                cs = new PDPageContentStream(report, report.getPage(p), true, false);
                cs.setFont(PDType1Font.TIMES_ROMAN, 11);
                cs.setNonStrokingColor(Color.BLACK);
                cs.beginText();
                String dateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTime());
                cs.setTextMatrix(new Matrix(1, 0, 0, 1, MARGIN_LEFT_X, MARGIN_TOP_Y + marginOffset));
                cs.showText(dateString);
                String projectString = "WZ ITS Tool Report: " + proj.getName();
                cs.setTextMatrix(new Matrix(1, 0, 0, 1,
                        MARGIN_RIGHT_X - (PDType1Font.TIMES_ROMAN.getStringWidth(projectString) / 1000 * 11),
                        MARGIN_TOP_Y + marginOffset));
                cs.showText(projectString);
                String pageNumString = "Page " + String.valueOf(p + 1) + " of " + String.valueOf(report.getNumberOfPages());
                cs.setTextMatrix(new Matrix(1, 0, 0, 1,
                        MARGIN_LEFT_X + (MARGIN_RIGHT_X - MARGIN_LEFT_X) / 2.0f - (PDType1Font.TIMES_ROMAN.getStringWidth(pageNumString) / 1000 * 11) / 2.0f,
                        MARGIN_BOTTOM_Y - marginOffset));
                cs.showText(pageNumString);
                cs.setTextMatrix(new Matrix(1, 0, 0, 1,
                        MARGIN_LEFT_X + 20, MARGIN_BOTTOM_Y - marginOffset));
                cs.showText("WZ ITS Tool V" + WZITS_FX.VERSION);
                String analystAgencyStr = (proj.getAnalyst() != null ? proj.getAnalyst() : "")
                        + (proj.getAnalyst() != null && proj.getAgency() != null ? " / " : "")
                        + (proj.getAgency() != null ? proj.getAgency() : "");
                cs.setTextMatrix(new Matrix(1, 0, 0, 1,
                        MARGIN_RIGHT_X - (PDType1Font.TIMES_ROMAN.getStringWidth(analystAgencyStr) / 1000 * 11),
                        MARGIN_BOTTOM_Y - marginOffset));
                cs.showText(analystAgencyStr);
                cs.endText();

                BufferedImage logoWZITS = ImageIO.read(WZITS_FX.class.getResource("/GUI/Icon/wzits_icon_64.png"));
                //ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
                //op.filter(logoWZITS, logoWZITS);
                cs.drawImage(LosslessFactory.createFromImage(report, logoWZITS),
                        MARGIN_LEFT_X, MARGIN_BOTTOM_Y - marginOffset - 3, 16, 16);
                cs.close();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

    }

}
