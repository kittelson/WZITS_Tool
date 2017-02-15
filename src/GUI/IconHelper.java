/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;

/**
 *
 * @author ltrask
 */
public class IconHelper {

    public static final String SVG_STR_PREV = "M 25 0 L 25 50 L 0 25 z";

    public static final String SVG_STR_NEXT = "M 0 0 L 0 50 L 25 25 z";

    public static final String SVG_STR_PREV_SMALL = "M 25 0 L 25 25 L 0 12 z";

    public static final String SVG_STR_NEXT_SMALL = "M 0 0 L 0 25 L 25 12 z";

    public static final Image TREE_NODE_PROJ_CLOSE = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/folder_close_48.png"), 16, 16, true, true);

    public static final Image TREE_NODE_PROJ_OPEN = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/folder_open_48.png"), 16, 16, true, true);

    public static final Image TREE_NODE_STEP_COMPLETE = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/check16.png"));

    public static final Image FIG_FLOW_ALL_STEPS = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/all_steps.PNG"));

    public static final Image FIG_FLOW_STEP_1 = new Image(IconHelper.class.getResourceAsStream("/GUI/Icon/step_1.PNG"));

}
