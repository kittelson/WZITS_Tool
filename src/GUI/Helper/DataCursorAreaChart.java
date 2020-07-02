package GUI.Helper;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.shape.Line;

/**
 * Adapted from https://stackoverflow.com/questions/28952133/how-to-add-two-vertical-lines-with-javafx-linechart
 * @param <X>
 * @param <Y>
 */
public class DataCursorAreaChart<X, Y> extends AreaChart<X, Y> {
    /**
     * Vertical line cursor that will follow the mouse of the user.
     */
    private final CustomData<X, Y> verticalMarker;

    public DataCursorAreaChart(Axis<X> xAxis, Axis<Y> yAxis, X dataCursorInitX, Y dataCursorInitY) {
        super(xAxis, yAxis);
        verticalMarker = new CustomData<>(dataCursorInitX, dataCursorInitY);
        Line line = new Line();
        verticalMarker.setNode(line);
        verticalMarker.XValueProperty().addListener((observableValue, oldVal, newVal) -> { layoutPlotChildren(); });
        verticalMarker.visibleProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal) {
                getPlotChildren().add(line);
            } else {
                getPlotChildren().remove(line);
            }
            layoutPlotChildren();
        });
        this.setOnMouseEntered(mouseEvent -> {showDataCursor();});
        this.setOnMouseExited(mouseEvent -> {hideDataCursor();});
    }

    public void setDataCursorX(X x) {
        verticalMarker.setXValue(x);
    }

    private void showDataCursor() {
        verticalMarker.setVisible(true);
    }

    private void hideDataCursor() {
        verticalMarker.setVisible(false);
    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();
        if (verticalMarker.isVisible()) {
            Line line = (Line) verticalMarker.getNode();
            line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()) + 0.5);  // 0.5 for crispness
            line.setEndX(line.getStartX());
            line.setStartY(0d);
            line.setEndY(getBoundsInLocal().getHeight());
        }
    }

    private class CustomData<X, Y> {

        private final Data<X, Y> data;
        private final SimpleBooleanProperty visible = new SimpleBooleanProperty(false);

        public CustomData(X x, Y y) {
            this.data = new Data<>(x, y);
        }

        public X getXValue() {
            return data.getXValue();
        }

        public void setXValue(X x) {
            this.data.setXValue(x);
        }

        public ObjectProperty<X> XValueProperty() {
            return data.XValueProperty();
        }

        public boolean isVisible() {
            return visible.get();
        }

        public SimpleBooleanProperty visibleProperty() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible.set(visible);
        }

        public void setNode(Node node) {
            this.data.setNode(node);
        }

        public Node getNode() {
            return this.data.getNode();
        }
    }
}
