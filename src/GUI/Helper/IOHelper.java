/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import core.Project;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

/**
 *
 * @author ltrask
 */
public class IOHelper {

    public static int saveProject(MainController mc, Project proj) {
        File saveFile = proj.getSaveFile(); //mc.getMainWindow()
        if (saveFile != null) {
            try {
                proj.setSaveFile(saveFile);
                FileOutputStream fos = new FileOutputStream(saveFile);
                GZIPOutputStream gzos = new GZIPOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(gzos);
                oos.writeObject(proj);
                oos.close();
                return SAVE_COMPLETED;
            } catch (IOException e) {
                return saveAsProject(mc, proj);
            }
        } else {
            return saveAsProject(mc, proj);
        }
    }

    public static int saveAsProject(MainController mc, Project proj) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save WZITS Tool Project");
        fc.getExtensionFilters().add(new ExtensionFilter("WZITS Project File (.wzp)", "*.wzp"));
        if (proj.getSaveFile() != null) {
            File initDir = proj.getSaveFile().getParentFile();
            if (initDir.isDirectory()) {
                fc.setInitialDirectory(initDir);
            }
        }
        File saveFile = fc.showSaveDialog(mc.getWindow());  //mc.getMainWindow()
        if (saveFile != null) {
            try {
                if (!saveFile.getName().endsWith(".wzp")) {
                    saveFile.renameTo(new File(saveFile.getAbsolutePath() + ".wzp"));
                }
                proj.setSaveFile(saveFile);
                FileOutputStream fos = new FileOutputStream(saveFile);
                GZIPOutputStream gzos = new GZIPOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(gzos);
                oos.writeObject(proj);
                oos.close();
                return SAVE_COMPLETED;
            } catch (IOException e) {
                e.printStackTrace();
                return SAVE_FAILED;
            }
        }
        return SAVE_CANCELLED;
    }

    public static Project openProject(MainController mc) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open WZITS Tool Project");
        fc.getExtensionFilters().add(new ExtensionFilter("WZITS Project File (.wzp)", "*.wzp"));
        File openFile = fc.showOpenDialog(null);
        Project proj = null;
        if (openFile != null) {
            try {
                FileInputStream fis = new FileInputStream(openFile);
                GZIPInputStream gzis = new GZIPInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(gzis);
                proj = (Project) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return proj;
    }

    public static boolean getProjectImage(MainController mc) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select WZITS Project Image");
        fc.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File openFile = fc.showOpenDialog(MainController.getWindow());  //mc.getMainWindow()
        if (openFile != null) {
            if (fc.getSelectedExtensionFilter().getExtensions().get(0).equalsIgnoreCase("*.pdf")) {
                try {
                    PDDocument doc = PDDocument.load(openFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(doc);
                    BufferedImage image = pdfRenderer.renderImage(0);
                    //ImageIOUtil.writeImage(image, "C:\\Users\\ltrask\\Documents\\test_image.png", 300);
                    Image convertedImage = SwingFXUtils.toFXImage(image, null);
                    mc.getProject().setProjPhoto(convertedImage);
                    doc.close();
                    return true;
                } catch (IOException e) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("WZITS Tool");
                    al.setHeaderText("The selected PDF is password protected");
                    al.showAndWait();
                }
            } else {
                try {
                    mc.getProject().setProjPhoto(new Image(new FileInputStream(openFile)));
                    return true;
                } catch (FileNotFoundException e) {

                }
            }
        }
        return false;
    }

    public static Image openImage(MainController mc) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select WZITS Project Image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        File openFile = fc.showOpenDialog(mc.getWindow());  //mc.getMainWindow()
        if (openFile != null) {
            if (fc.getSelectedExtensionFilter().getExtensions().get(0).equalsIgnoreCase("*.pdf")) {
                try {
                    PDDocument doc = PDDocument.load(openFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(doc);
                    BufferedImage image = pdfRenderer.renderImage(0);
                    Image convertedImage = SwingFXUtils.toFXImage(image, null);
                    doc.close();
                    return convertedImage;
                } catch (IOException e) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("WZITS Tool");
                    al.setHeaderText("The selected PDF is password protected");
                    al.showAndWait();
                }
            } else {
                try {
                    return new Image(new FileInputStream(openFile));
                } catch (FileNotFoundException e) {

                }
            }
        }
        return null;
    }

    public static void confirm(int saveResult) {
        if (saveResult != SAVE_CANCELLED) {
            boolean saveSuccess = saveResult == SAVE_COMPLETED;
            String alTitle = "WZITS Tool";
            String alHeader = saveSuccess ? "Project file saved successfully." : "Failed to save project file.";
            Alert al = new Alert(saveSuccess ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR);
            al.setTitle(alTitle);
            al.setHeaderText(alHeader);
            al.showAndWait();
        }
    }

    public static final int SAVE_COMPLETED = 1;
    public static final int SAVE_FAILED = 0;
    public static final int SAVE_CANCELLED = -1;

    public static File lastSaveLocation = null;

}
