/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import GUI.MainController;
import core.Project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
                e.printStackTrace();
                return SAVE_FAILED;
            }
        } else {
            return saveAsProject(mc, proj);
        }
    }

    public static int saveAsProject(MainController mc, Project proj) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save WZITS Tool Project");
        fc.getExtensionFilters().add(new ExtensionFilter("WZITS Project File (.wzp)", "*.wzp"));
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
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File openFile = fc.showOpenDialog(mc.getWindow());  //mc.getMainWindow()
        if (openFile != null) {
            try {
                mc.getProject().setProjPhoto(new Image(new FileInputStream(openFile)));
                return true;
            } catch (FileNotFoundException e) {

            }
        }
        return false;
    }

    public static Image openImage(MainController mc) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select WZITS Project Image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File openFile = fc.showOpenDialog(mc.getWindow());  //mc.getMainWindow()
        if (openFile != null) {
            try {
                return new Image(new FileInputStream(openFile));
            } catch (FileNotFoundException e) {

            }
        }
        return null;
    }

    public static final int SAVE_COMPLETED = 1;
    public static final int SAVE_FAILED = 0;
    public static final int SAVE_CANCELLED = -1;

}
