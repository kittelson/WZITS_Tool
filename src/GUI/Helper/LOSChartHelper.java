package GUI.Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.util.ArrayList;

public class LOSChartHelper {

    public static ClockDonutChart createDCRatioClockChart(ArrayList<Float> data, boolean includeTitle, String titleStr, boolean isDark) {
        int apStart = 0;
        boolean isClear = false;
        while (!isClear && apStart < 96) {
            if (data.get(apStart) < 0.85) {
                isClear = true;
            } else {
                apStart++;
            }
        }

        ObservableList<CustomPieData> lrData = FXCollections.observableArrayList();

        String prevRegime = UNDER;
        int currCount = 0;
        int beginPeriodTracker = apStart;
        for (int count = 0; count < 96; count++) {
            String currRegime = toRegimeStr(data.get((apStart + count) % 96), false);

            if (currRegime.equalsIgnoreCase(prevRegime)) {
                currCount++;
            } else {
                CustomPieData slice = new CustomPieData(prevRegime, currCount, beginPeriodTracker);
                lrData.add(slice);
                beginPeriodTracker = (beginPeriodTracker + currCount) % 96;
                currCount = 1;
            }
            prevRegime = currRegime;
        }
        CustomPieData lastSlice = new CustomPieData(prevRegime, currCount, (apStart + 96 - currCount) % 96);
        lrData.add(lastSlice);

        return createDonutChart(lrData, includeTitle, titleStr, isDark);
    }

    public static ClockDonutChart createDonutChart(ObservableList<CustomPieData> data, boolean includeTitle, String titleStr, boolean isDark) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (CustomPieData datum : data) {
            pieData.add(datum.pieData);
        }
        ClockDonutChart donutChart = new ClockDonutChart(pieData);
        donutChart.setStartAngle(90);
        if (includeTitle) {
            donutChart.setTitle(titleStr);
        }

        //donutChart.setLabelLineLength(0);
        donutChart.setLabelsVisible(false);

        donutChart.getStylesheets().add(LOSChartHelper.class.getResource("/GUI/CSS/time-of-day-chart.css").toExternalForm());
        if (isDark) {
            donutChart.getStyleClass().add("time-of-day-donut-dark");
        } else {
            donutChart.getStyleClass().add("time-of-day-donut-light");
        }
        for (CustomPieData datum : data) {
            datum.getNode().getStyleClass().add("data-reg-" + datum.getName().toLowerCase());

            String tooltipStr = datum.getName() + " D/C Threshold\n" + getRegimeRangeStr(datum.getName(), false);
            tooltipStr += "\n";
            tooltipStr += datum.getSliceTimeRange();
            Tooltip dataToolTip = new Tooltip(tooltipStr);
            dataToolTip.getStyleClass().add("tooltip-reg-" + datum.getName().toLowerCase());
            dataToolTip.setShowDelay(Duration.ZERO);
            dataToolTip.setShowDuration(Duration.INDEFINITE);

            Tooltip.install(datum.getNode(), dataToolTip);
        }

        donutChart.setLegendVisible(false);
        GridPane.setHgrow(donutChart, Priority.ALWAYS);
        return donutChart;
    }

    public static class CustomPieData  {
        final PieChart.Data pieData;
        final int beginPeriod;

        public CustomPieData(String name, double value, int beginPeriod) {
            this.pieData = new PieChart.Data(name, value);
            this.beginPeriod = beginPeriod;
        }

        public String getName() {
            return this.pieData.getName();
        }

        public Node getNode() {
            return this.pieData.getNode();
        }

        public double getPieValue() {
            return this.pieData.getPieValue();
        }

        public PieChart getChart() {
            return this.pieData.getChart();
        }

        public int getBeginPeriod() {
            return this.beginPeriod;
        }

        public String getBeginPeriodStr() {
            return CETime.getFancyAPStr(new CETime(0, 0), beginPeriod);
        }

        public String getBeginPeriodStr(CETime refStartTime) {
            return CETime.getFancyAPStr(refStartTime, beginPeriod);
        }

        public String getSliceTimeRange() {
            CETime interval = new CETime(0, 15);
            //return CETime.getTimeRangeAsString(new CETime(0, 0), beginPeriod, beginPeriod + (int) pieData.getPieValue()); // 24-Hour clock version
            CETime startTime = new CETime(0, 0);
            String timeRangeStr = CETime.addTime(startTime, interval, beginPeriod).toAMPMString();
            timeRangeStr = timeRangeStr + "-";
            timeRangeStr = timeRangeStr + CETime.addTime(startTime, interval, beginPeriod + (int) pieData.getPieValue()).toAMPMString();
            return timeRangeStr;
        }

        public String getSliceTimeRange(CETime refStartTime) {
            CETime interval = new CETime(0, 15);
            //return CETime.getTimeRangeAsString(refStartTime, beginPeriod, beginPeriod + (int) pieData.getPieValue()); // 24-Hour clock version
            String timeRangeStr = CETime.addTime(refStartTime, interval, beginPeriod).toAMPMString();
            timeRangeStr = timeRangeStr + "-";
            timeRangeStr = timeRangeStr + CETime.addTime(refStartTime, interval, beginPeriod + (int) pieData.getPieValue()).toAMPMString();
            return timeRangeStr;
        }

    }

    public static String toRegimeStr(float value, boolean includeAt) {
        if (value >= 1.05f) {
            return OVER;
        } else if (value >= 0.95f) {
            return includeAt ? AT_CAP : OVER;
        } else if (value >= 0.85f) {
            return NEAR;
        } else {
            return UNDER;
        }
    }

    public static String getRegimeRangeStr(String regimeStr, boolean encloseInParentheses) {
        String rangeStr;
        switch (regimeStr) {
            default:
            case UNDER:
                rangeStr = "D/C < 0.85";
                break;
            case NEAR:
                rangeStr = "0.85 <= D/C < 0.95";
                break;
            case AT_CAP:
            case OVER:
                rangeStr = "D/C >= 0.95";
                break;
        }
        if (encloseInParentheses) {
            return " (" + rangeStr + ")";
        } else {
            return rangeStr;
        }
    }
    public static final String UNDER = "Under";
    public static final String NEAR = "Near";
    public static final String AT_CAP = "At";
    public static final String OVER = "Over";
    public static final String NA = "N/A";
}
