package GUI.Helper;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;

import java.util.Objects;

/**
 * Adapted from https://stackoverflow.com/questions/28952133/how-to-add-two-vertical-lines-with-javafx-linechart
 * @param <X>
 * @param <Y>
 */
public class DataCursorLineChart<X, Y> extends LineChart<X, Y> {
//    private final ObservableList<Data<X, Y>> horizontalMarkers;
//    private final ObservableList<CustomData<X, Y>> verticalMarkers;
    private final CustomData<X, Y> verticalMarker;

    public DataCursorLineChart(Axis<X> xAxis, Axis<Y> yAxis, X dataCursorInitX, Y dataCursorInitY) {
        super(xAxis, yAxis);
//        horizontalMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.YValueProperty()});
//        horizontalMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
//        verticalMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
//        verticalMarkers.addListener((InvalidationListener)observable -> layoutPlotChildren());
//        addVerticalValueMarker(new CustomData<>(dataCursorInitX, dataCursorInitY));
        verticalMarker = new CustomData<>(dataCursorInitX, dataCursorInitY);
        Line line = new Line();
        verticalMarker.setNode(line);
//        getPlotChildren().add(line);
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
//        verticalMarkers.get(0).setXValue(x);
        verticalMarker.setXValue(x);
    }

    private void showDataCursor() {
//        verticalMarkers.get(0).setVisible(true);
        verticalMarker.setVisible(true);
    }

    private void hideDataCursor() {
//        verticalMarkers.get(0).setVisible(false);
        verticalMarker.setVisible(false);
    }

//    private void addVerticalValueMarker(CustomData<X, Y> marker) {
//        Objects.requireNonNull(marker, "the marker must not be null");
//        if (verticalMarkers.contains(marker)) return;
//        Line line = new Line();
//        marker.setNode(line );
//        getPlotChildren().add(line);
//        verticalMarkers.add(marker);
//    }

//    private void removeVerticalValueMarker(CustomData<X, Y> marker) {
//        Objects.requireNonNull(marker, "the marker must not be null");
//        if (marker.getNode() != null) {
//            getPlotChildren().remove(marker.getNode());
//            marker.setNode(null);
//        }
//        verticalMarkers.remove(marker);
//    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();
//        for (Data<X, Y> horizontalMarker : horizontalMarkers) {
//            Line line = (Line) horizontalMarker.getNode();
//            line.setStartX(0);
//            line.setEndX(getBoundsInLocal().getWidth());
//            line.setStartY(getYAxis().getDisplayPosition(horizontalMarker.getYValue()) + 0.5); // 0.5 for crispness
//            line.setEndY(line.getStartY());
//            //line.toFront();
//        }
//        for (CustomData<X, Y> verticalMarker : verticalMarkers) {
//            if (verticalMarker.isVisible()) {
//                Line line = (Line) verticalMarker.getNode();
//                line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()) + 0.5);  // 0.5 for crispness
//                line.setEndX(line.getStartX());
//                line.setStartY(0d);
//                line.setEndY(getBoundsInLocal().getHeight());
//                //line.toFront();
//            }
//        }
        if (verticalMarker.isVisible()) {
            Line line = (Line) verticalMarker.getNode();
            line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()) + 0.5);  // 0.5 for crispness
            line.setEndX(line.getStartX());
            line.setStartY(0d);
            line.setEndY(getBoundsInLocal().getHeight());
            //line.toFront();
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
