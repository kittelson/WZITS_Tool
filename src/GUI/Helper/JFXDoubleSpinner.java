package GUI.Helper;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class JFXDoubleSpinner extends StackPane {

    private final JFXTextField textField;

    private final SimpleDoubleProperty value = new SimpleDoubleProperty();
    private final SimpleDoubleProperty increment = new SimpleDoubleProperty();
    private final SimpleDoubleProperty minimum = new SimpleDoubleProperty();
    private final SimpleDoubleProperty maximum = new SimpleDoubleProperty();


    public JFXDoubleSpinner(double minVal, double maxVal, double initVal, double increment) {

        this.getStyleClass().add("jfx-value-spinner");
        textField = new JFXTextField(String.valueOf(initVal));
        setValue(initVal);
        setIncrement(increment);
        setMinimum(minVal);
        setMaximum(maxVal);

        NodeFactory.setTextFieldDecimalInputOnly(textField);
        textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            try {
                double newIntVal = Float.parseFloat(newVal.replace(",", ""));
                if (newIntVal < getMinimum()) {
                    textField.setText(String.valueOf(getMinimum()));
                } else if (newIntVal > getMaximum()){
                    textField.setText(String.valueOf(getMaximum()));
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
        double newVal = getValue() + (increment.get() * numSteps);
        if (newVal <= getMaximum()) {
            textField.setText(String.valueOf(newVal));
        } else {
            textField.setText(String.valueOf(getMaximum()));
        }
    }

    public void decrementValue(int numSteps) {
        double newVal = getValue() - (increment.get() * numSteps);
        if (newVal >= getMinimum()) {
            textField.setText(String.valueOf(newVal));
        } else {
            textField.setText(String.valueOf(getMinimum()));
        }
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public double getIncrement() {
        return increment.get();
    }

    public SimpleDoubleProperty incrementProperty() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment.set(increment);
    }

    public double getMinimum() {
        return minimum.get();
    }

    public SimpleDoubleProperty minimumProperty() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum.set(minimum);
    }

    public double getMaximum() {
        return maximum.get();
    }

    public SimpleDoubleProperty maximumProperty() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum.set(maximum);
    }
}
