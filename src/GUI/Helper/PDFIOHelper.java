/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
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

    public static void writeStep1Summary(Node n) {
        BorderPane bp = (BorderPane) ((ScrollPane) n).getContent();
        //Scene scene = new Scene(bp);
        //WritableImage wi = scene.snapshot(null);
        WritableImage wi = bp.snapshot(new SnapshotParameters(), null);

        double prefHeight = wi.getHeight();
        double prefWidth = wi.getWidth();
        System.out.println(String.valueOf(prefHeight));
        System.out.println(String.valueOf(prefWidth));
        BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        try {
            ImageIO.write(bi, "png", new File("test.png"));
            PDPageContentStream content = new PDPageContentStream(doc, page);
            PDImageXObject ximage = LosslessFactory.createFromImage(doc, bi);
            content.drawImage(ximage, 50, 50, Math.min(500, Math.round(0.4 * prefWidth)), Math.min(695, Math.round(0.5 * prefHeight)));
            content.fillAndStroke();
            content.close();
            doc.save("test.pdf");
            doc.close();
        } catch (IOException ie) {
            System.out.println("ERROR");
        }

    }

}
