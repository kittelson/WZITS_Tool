package GUI.Helper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class JFXIntegerSpinner extends StackPane {

    private final JFXTextField textField;

    private final SimpleIntegerProperty value = new SimpleIntegerProperty();
    private final SimpleIntegerProperty increment = new SimpleIntegerProperty();
    private final SimpleIntegerProperty minimum = new SimpleIntegerProperty();
    private final SimpleIntegerProperty maximum = new SimpleIntegerProperty();


    public JFXIntegerSpinner(int minVal, int maxVal, int initVal, int increment) {

        this.getStyleClass().add("jfx-value-spinner");
        textField = new JFXTextField(String.format("%,d",initVal));
        setValue(initVal);
        setIncrement(increment);
        setMinimum(minVal);
        setMaximum(maxVal);

        NodeFactory.setTextFieldIntegerInputOnly(textField);
        textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            try {
                int newIntVal = Integer.parseInt(newVal.replace(",", ""));
                if (newIntVal < getMinimum()) {
                    textField.setText(String.format("%,d", getMinimum()));
//                    setValue(getMinimum());
                } else if (newIntVal > getMaximum()){
                    textField.setText(String.format("%,d", getMaximum()));
//                    setValue(getMaximum());
                } else {
                    setValue(newIntVal);
                }
            } catch (NumberFormatException e) {
                // Do nothing
            }
        });
        textField.setFocusColor(Color.web(ColorHelper.WZ_ORANGE));

        FontIcon upIcon = NodeFactory.createIcon(FontAwesomeSolid.CARET_UP, Color.BLACK, 12);
        upIcon.iconColorProperty().bind(textField.unFocusColorProperty());
        JFXButton valueUpButton = new JFXButton();
        valueUpButton.setGraphic(upIcon);

        Timeline incrementTimeline = new Timeline(new KeyFrame(Duration.millis(75), actionEvent -> {incrementValue(1);}));
        incrementTimeline.setCycleCount(Animation.INDEFINITE);
        Timeline initIncrementTimeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> {incrementValue(1);}));
        initIncrementTimeline.setOnFinished(actionEvent -> {
            incrementTimeline.play();
        });
        valueUpButton.armedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasArmed, Boolean isArmed) {
                if (isArmed) {
                    incrementValue(1);
                    initIncrementTimeline.play();
                } else {
                    initIncrementTimeline.stop();
                    incrementTimeline.stop();
                }
            }
        });
        FontIcon downIcon = NodeFactory.createIcon(FontAwesomeSolid.CARET_DOWN, Color.BLACK, 12);
        downIcon.iconColorProperty().bind(textField.unFocusColorProperty());
        JFXButton valueDownButton = new JFXButton();
        valueDownButton.setGraphic(downIcon);
        Timeline decrementTimeline = new Timeline(new KeyFrame(Duration.millis(75), actionEvent -> {decrementValue(1);}));
        decrementTimeline.setCycleCount(Animation.INDEFINITE);
        Timeline initDecrementTimeline = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> {incrementValue(1);}));
        initDecrementTimeline.setOnFinished(actionEvent -> {
            decrementTimeline.play();
        });
        valueDownButton.armedProperty().addListener((observableValue, wasArmed, isArmed) -> {
            if (isArmed) {
                decrementValue(1);
                initDecrementTimeline.play();
            } else {
                initDecrementTimeline.stop();
                decrementTimeline.stop();
            }
        });
        BorderPane buttonPane = new BorderPane();
        buttonPane.setTop(valueUpButton);
        buttonPane.setBottom(valueDownButton);

//        buttonPane.maxHeightProperty().bind(textField.heightProperty());
        valueUpButton.minHeightProperty().bind(textField.heightProperty().divide(2.0));
        valueUpButton.maxHeightProperty().bind(textField.heightProperty().divide(2.0));
        valueDownButton.minHeightProperty().bind(textField.heightProperty().divide(2.0));
        valueDownButton.maxHeightProperty().bind(textField.heightProperty().divide(2.0));
        valueDownButton.getStyleClass().add("down-button");
        valueUpButton.getStyleClass().add("up-button");

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(textField);
//        contentPane.setRight(buttonPane);
        StackPane.setAlignment(buttonPane, Pos.CENTER_RIGHT);
        buttonPane.maxWidthProperty().bind(valueUpButton.widthProperty());
        contentPane.maxHeightProperty().bind(textField.heightProperty());
        valueDownButton.setDisableVisualFocus(true);
        valueUpButton.setDisableVisualFocus(true);
        this.getChildren().addAll(contentPane, buttonPane);
        this.setAlignment(Pos.TOP_CENTER);
        this.minHeightProperty().bind(contentPane.heightProperty());
        this.maxHeightProperty().bind(contentPane.heightProperty());
    }

    public void incrementValue(int numSteps) {
        int newVal = getValue() + (increment.get() * numSteps);
        if (newVal <= getMaximum()) {
            textField.setText(String.format("%,d", newVal));
        } else {
            textField.setText(String.format("%,d", getMaximum()));
        }
    }

    public void decrementValue(int numSteps) {
        int newVal = getValue() - (increment.get() * numSteps);
        if (newVal >= getMinimum()) {
            textField.setText(String.format("%,d", newVal));
        } else {
            textField.setText(String.format("%,d", getMinimum()));
        }
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getIncrement() {
        return increment.get();
    }

    public SimpleIntegerProperty incrementProperty() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment.set(increment);
    }

    public int getMinimum() {
        return minimum.get();
    }

    public SimpleIntegerProperty minimumProperty() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum.set(minimum);
    }

    public int getMaximum() {
        return maximum.get();
    }

    public SimpleIntegerProperty maximumProperty() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum.set(maximum);
    }
}
