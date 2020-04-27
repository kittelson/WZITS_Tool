package GUI.PDFReports.XML;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
//import netscape.javascript.JSObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.JDialog;

public class BrowserDialog2 extends JDialog {

    private WebEngine engine;
    public WebView webView;
    static double width = 0;
    static double height = 0;

    private int captureCount = 0;
    private CountDownLatch capturedLatch;

    String url;

    final String pdfResourceFolderPath;

    public void loaded() {
        System.out.println("loaded called");
    }

    public BrowserDialog2(CountDownLatch capturedLatch, String pdfResourceFolderPath) {
        this.capturedLatch = capturedLatch;
        this.pdfResourceFolderPath = pdfResourceFolderPath;
        JFXPanel panelFX = new JFXPanel();
        this.url = BrowserDialog2.class.getResource("/Toolbox/Facility/WebChart/chartplaceholder.html").toString();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(panelFX);
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setSize(new Dimension(1250, 500));
        Point p = new Point();
        p.setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);
        this.setLocation(p);

        this.add(panelFX);
        //panelFX.setVisible(true);
        this.setVisible(true);
    }

    /**
     * Initializes the JFXPanel, loads the HTML/JavaScript file, Sets connection
     * with JavaScript
     *
     * @param panelFX
     */
    public void initFX(JFXPanel panelFX) {
        WebView web = new WebView();
        this.webView = web;
        webView.setPrefSize(1250, 500);
        engine = web.getEngine();

        // Load URL
        engine.load(url);

        engine.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.2 (KHTML, like Gecko) JavaFX/8.0 Safari/537.2");
        // Connect java instance to javascript engine
//        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
//            JSObject window = (JSObject) engine.executeScript("window");
//            window.setMember("java", this);
//            engine.executeScript("console.log = function(message)\n"
//                    + "{\n"
//                    + "    java.log(message);\n"
//                    + "};");
//        });

        Scene scene = new Scene(web);
        panelFX.setScene(scene);
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void captureScreenshot() {
        String fname = null;
        String newChartIndex = null;
        switch (this.captureCount) {
            case 0:
//                fname = PDFReportHelper2.getResFolderLocation() + "hourly-system-delay.png";
                fname = pdfResourceFolderPath + "hourly-system-delay.png";
                newChartIndex = BrowserDialog2.class.getResource("/Toolbox/Facility/WebChart/chartplaceholder_1.html").toString();
                break;
            case 1:
//                fname = PDFReportHelper2.getResFolderLocation() + "facility-hdd.png";
                fname = pdfResourceFolderPath + "facility-hdd.png";
                newChartIndex = BrowserDialog2.class.getResource("/Toolbox/Facility/WebChart/chartplaceholder_2.html").toString();
                break;
            case 2:
//                fname = PDFReportHelper2.getResFolderLocation() + "scenTTComp.png";
                fname = pdfResourceFolderPath + "scenTTComp.png";
                newChartIndex = BrowserDialog2.class.getResource("/Toolbox/Facility/WebChart/chartplaceholder_1.html").toString();
                break;
        }

        if (fname != null) {
            this.getCapture(fname);
            this.captureCount++;
            if (captureCount < 3) {
                this.engine.load(newChartIndex);
            } else {
                this.setVisible(false);
                if (this.capturedLatch != null) {
                    this.capturedLatch.countDown();
                }
            }
        }
    }

    public File getCapture(String filepath) {
        System.out.println("Captured Started");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {

        }
        File captureFile = new File(filepath);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setTransform(new Scale(2, 2));
                WritableImage image = webView.snapshot(snapshotParameters, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                try {
                    System.out.println("copying");
                    ImageIO.write(bufferedImage, "png", captureFile);
                    System.out.println("Captured");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return captureFile;
    }

    public void setCaptureCount(int capCount) {
        this.captureCount = capCount;
    }
}
