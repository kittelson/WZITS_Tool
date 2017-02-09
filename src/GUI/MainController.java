/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.stage.Stage;

/**
 *
 * @author ltrask
 */
public class MainController {
    
    private MainWindow mainWindow;
    
    private final Stage stage;
    
    public MainController(Stage stage) {
        this.stage = stage;
    }
    
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    public void begin() {
        //stage.hide();
        //stage.setMaximized(true);
        //stage.show();
        mainWindow.begin();
        //stage.setMinWidth(mainWindow.getMinWidth());
    }

    public double getAppWidth() {
        return stage.getWidth();
    }
    
    public static final int MAX_WIDTH = 999999;
    public static final int MAX_HEIGHT = 999999;
    
}
