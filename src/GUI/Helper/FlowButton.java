package GUI.Helper;

import GUI.FlowBarV2;
import GUI.MainController;
import javafx.animation.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;

public class FlowButton extends Button {

    private static int HEIGHT_REDUCDTION = 20;
    private static int HOVER_HEIGHT_REDUCTION = 10;

    private SimpleBooleanProperty active = new SimpleBooleanProperty(false);

    public FlowButton(String buttonText) {
        this(buttonText, "step-button");
    }

    public FlowButton(String buttonText, String buttonClass) {
        super(buttonText);
        this.getStyleClass().setAll("custom-button", buttonClass, "color-light-grey");
        this.setMaxHeight(FlowBarV2.FLOW_BAR_HEIGHT - HEIGHT_REDUCDTION);
        setWrapText(true);
        setMinWidth(0);
        setMinHeight(0);
        setMaxWidth(MainController.MAX_WIDTH);
        active.addListener((observableValue, wasActive, isActive) -> {
            Timeline growAnimation = new Timeline(new KeyFrame(Duration.millis(50), new KeyValue(maxHeightProperty(), FlowBarV2.FLOW_BAR_HEIGHT - 2)));
//            Timeline shrinkAnimation = new Timeline(new KeyFrame(Duration.millis(50), new KeyValue(maxHeightProperty(), FlowBarV2.FLOW_BAR_HEIGHT - HEIGHT_REDUCDTION)));
            if (isActive) {
//                shrinkAnimation.stop();
                growAnimation.play();
            } else {
                growAnimation.stop();
//                shrinkAnimation.play();
                setMaxHeight(FlowBarV2.FLOW_BAR_HEIGHT - HEIGHT_REDUCDTION);
            }
        });
        hoverProperty().addListener((observableValue, wasHover, isHover) -> {
            Timeline hoverStartAnimation = new Timeline(new KeyFrame(Duration.millis(50), new KeyValue(maxHeightProperty(), FlowBarV2.FLOW_BAR_HEIGHT - HOVER_HEIGHT_REDUCTION)));
            Timeline hoverEndAnimtation = new Timeline(new KeyFrame(Duration.millis(50), new KeyValue(maxHeightProperty(), FlowBarV2.FLOW_BAR_HEIGHT - (isActive() ? 2 : HEIGHT_REDUCDTION))));
            if (isHover) {
                hoverEndAnimtation.stop();
                hoverStartAnimation.play();
            } else {
                hoverStartAnimation.stop();
                hoverEndAnimtation.play();
            }
        });
    }

    private void updateColor(String newColorStyle) {
        ArrayList<String> toRemove = new ArrayList<>();
        for (String styleClass : this.getStyleClass()) {
            if (styleClass.contains("color-")) {
                toRemove.add(styleClass);
            }
        }
        this.getStyleClass().removeAll(toRemove);
        this.getStyleClass().add(newColorStyle);
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public void setHeightReducdtion(int heightReducdtion) {
        HEIGHT_REDUCDTION = heightReducdtion;
    }

    private static class CustomParallelTransition {

        private final ParallelTransition parallelTransition = new ParallelTransition();

        public CustomParallelTransition() {
            super();
            parallelTransition.getChildren().addListener((ListChangeListener<Animation>) change -> {
                if (parallelTransition.getChildren().size() == 2) {
                    parallelTransition.setOnFinished(actionEvent -> {
                        parallelTransition.getChildren().clear();
                    });
                    parallelTransition.play();
                }
            });
        }

        public void addAnimation(Timeline animation) {
            parallelTransition.getChildren().add(animation);
        }


    }
}
