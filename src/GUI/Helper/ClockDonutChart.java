package GUI.Helper;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ClockDonutChart extends PieChart {
    private final Circle innerCircle;
    private final ArrayList<Line> clockTickLines;
    private final ArrayList<Text> clockTickTexts;

    public ClockDonutChart(ObservableList<Data> pieData) {
        super(pieData);

        innerCircle = new Circle();

        // just styled in code for demo purposes,
        // use a style class instead to style via charts.css.
        innerCircle.getStyleClass().add("inner-circle");
        clockTickLines = new ArrayList<Line>();
        clockTickTexts = new ArrayList<Text>();
        CETime midnight = new CETime(0, 0);
        CETime inc = new CETime(0, 15);
        for (int i = 0; i < 23; i++) {
            Line clockTickLine = new Line();
            clockTickLine.getStyleClass().add("clock-tick-mark");
            clockTickLines.add(clockTickLine);
        }
        for (int i = 0; i < 8; i++) {
            Text clockTickLabel = new Text(CETime.addTime(midnight, inc, i*12).toHourAMPMString());
            clockTickLabel.getStyleClass().add("clock-tick-label");
            clockTickTexts.add(clockTickLabel);
        }
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);

        addInnerCircleIfNotPresent();
        updateInnerCircleLayout();
    }

    private void addInnerCircleIfNotPresent() {
        if (getData().size() > 0) {
            Node pie = getData().get(0).getNode();
            if (pie.getParent() instanceof Pane) {
                Pane parent = (Pane) pie.getParent();

                if (!parent.getChildren().contains(innerCircle)) {
                    parent.getChildren().add(innerCircle);
                }
                for (Line clockTick : clockTickLines) {
                    if (!parent.getChildren().contains(clockTick)) {
                        parent.getChildren().add(clockTick);
                    }
                }
                for (Text clockTickText : clockTickTexts) {
                    if (!parent.getChildren().contains(clockTickText)) {
                        parent.getChildren().add(clockTickText);
                    }
                }
            }
        }
    }

    private void updateInnerCircleLayout() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (PieChart.Data data: getData()) {
            Node node = data.getNode();

            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        Double xCenter = minX + (maxX - minX) / 2;
        Double yCenter = minY + (maxY - minY) / 2;
        Double radius = (maxX - minX) / 3;
        innerCircle.setCenterX(xCenter);
        innerCircle.setCenterY(yCenter);
        innerCircle.setRadius(radius);
        for (int i = 0; i < clockTickLines.size(); i++) {
            Line clockTick = clockTickLines.get(i);
            double lineOffset = i % 3 == 0 ? 10 : 3;
            clockTick.setStartX(radius * Math.cos((i * Math.PI) / 12) + xCenter);
            clockTick.setStartY(radius * Math.sin((i * Math.PI) / 12) + yCenter);
            clockTick.setEndX((radius - lineOffset) * Math.cos((i * Math.PI) / 12) + xCenter);
            clockTick.setEndY((radius - lineOffset) * Math.sin((i * Math.PI) / 12) + yCenter);
        }
        for (int i = 0; i < clockTickTexts.size(); i++) {
            Text clockTickText = clockTickTexts.get(i);
            double textX = (radius - 10) * Math.cos((3 * (i-2) * Math.PI)/ 12 ) + xCenter;
            double textY = (radius - 10) * Math.sin((3 * (i-2) * Math.PI) / 12) + yCenter;
            double offsetX = 0;
            double offsetY = 0;
            if (i == 0) {
                offsetX = -1 * clockTickText.getLayoutBounds().getWidth() / 2;
                offsetY = clockTickText.getLayoutBounds().getHeight();
            } else if (i == 1) {
                offsetX = -1 * clockTickText.getLayoutBounds().getWidth() / 2;
                offsetY = clockTickText.getLayoutBounds().getHeight();
            } else if (i == 2) {
                offsetX = -1 * clockTickText.getLayoutBounds().getWidth();
                offsetY = clockTickText.getLayoutBounds().getHeight() / 2;
            } else if (i == 3) {
                offsetX = -1 * clockTickText.getLayoutBounds().getWidth();
                offsetY = 0;
            } else if (i == 4) {
                offsetX = -1 * clockTickText.getLayoutBounds().getWidth() / 2;
                offsetY = 0;
            } else if (i == 5) {
                offsetX = 0;
                offsetY = 0;
            } else if (i == 6) {
                offsetX = 0;
                offsetY = clockTickText.getLayoutBounds().getHeight() / 2;
            } else if (i == 7) {
                offsetX = 0;
                offsetY = clockTickText.getLayoutBounds().getHeight();
            }

            clockTickText.setX(textX + offsetX);
            clockTickText.setY(textY + offsetY);

        }
    }

}
