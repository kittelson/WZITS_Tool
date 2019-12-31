package wzits_fx;

import GUI.Helper.IconHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class vcWizard extends BorderPane {

    public vcWizard() {

        Label lblAADT = new Label("Bidirectional AADT");
        Label lblDirSplit = new Label("Directional Split");
        Label lblcompDir = new Label("Computed Directional AADT");
        Label lblLaneCap = new Label("Base Per Lane Capacity");
        Label lblNumLanes = new Label("Number of Lanes");
        Label lblSegCap = new Label("Total Segment Capacity");
        Label lblvehPerDay = new Label("veh/day");
        Label lblTrucks = new Label("%");
        Label lblvehLnHr = new Label("pc/ln/hr");
        Label lblcompValue = new Label();
        final char seperatorChar = ',';
        JFXTextField txtAADT = new JFXTextField();

        final Pattern numPattern = Pattern.compile("[0-9" + seperatorChar + "]*");
        txtAADT.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.isContentChange()) {
                return c;
            }
            String newText = c.getControlNewText();
            if (newText.isEmpty()) {
                return c;
            }
            if (!numPattern.matcher(newText).matches()) {
                return null;
            }
            int suffixCount = c.getControlText().length() - c.getRangeEnd();
            int digits = suffixCount - suffixCount / 4;
            StringBuilder sb = new StringBuilder();

            if (digits % 3 == 0 && digits > 0 && suffixCount % 4 != 0) {
                sb.append(seperatorChar);
            }
            for (int i = c.getRangeStart() + c.getText().length() - 1; i >= 0; i--) {
                char letter = newText.charAt(i);
                if (Character.isDigit(letter)) {
                    sb.append(letter);
                    digits++;
                    if (digits % 3 == 0) {
                        sb.append(seperatorChar);
                    }
                }
            }
            if (digits % 3 == 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.reverse();
            int length = sb.length();
            c.setRange(0, c.getRangeEnd());
            c.setText(sb.toString());
            c.setCaretPosition(length);
            c.setAnchor(length);

            return c;
        }));

        lblAADT.getStyleClass().add("vc-label-styles");
        lblcompDir.getStyleClass().add("vc-label-styles");
        lblDirSplit.getStyleClass().add("vc-label-styles");
        lblvehPerDay.getStyleClass().add("vc-units");
        lblLaneCap.getStyleClass().add("vc-label-styles");
        lblNumLanes.getStyleClass().add("vc-label-styles");
        lblTrucks.getStyleClass().add("vc-label-styles");
        lblSegCap.getStyleClass().add("vc-label-styles");
        lblvehLnHr.getStyleClass().add("vc-units");


        JFXButton btnPrev = new JFXButton();

        FontIcon prevIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_LEFT, Color.rgb(68,96,114), 30);
        btnPrev.setGraphic(prevIcon);
        FontIcon nextIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_RIGHT, Color.rgb(68,96,114), 30);
        btnPrev.setGraphic(nextIcon);

        JFXTextField txtLaneCap = new JFXTextField();
        JFXTextField txtPercTrucks = new JFXTextField();
        txtPercTrucks.setPromptText("Percent Trucks");
        txtPercTrucks.setLabelFloat(true);

        JFXSlider slidNumLanes = new JFXSlider();
        JFXSlider slidDirSplit = new JFXSlider();

        txtAADT.getStyleClass().add("jf-txtbox");
        txtLaneCap.getStyleClass().add("jf-txtbox");
        txtPercTrucks.getStyleClass().add("jf-txtbox");

        slidDirSplit.setShowTickMarks(true);
        slidDirSplit.setShowTickLabels(true);
        slidDirSplit.setValue(0.5);
        slidDirSplit.setMin(0.00);
        slidDirSplit.setBlockIncrement(0.01);
        slidDirSplit.setMajorTickUnit(.2);
        slidDirSplit.setMinorTickCount(19);
        slidDirSplit.setSnapToTicks(true);
        slidDirSplit.setMax(1.0);
        slidNumLanes.setShowTickMarks(true);
        slidNumLanes.setShowTickLabels(true);
        slidNumLanes.setValue(2);
        slidNumLanes.setMin(1);
        slidNumLanes.setBlockIncrement(1);
        slidNumLanes.setMajorTickUnit(1);
        slidNumLanes.setMinorTickCount(19);
        slidNumLanes.setSnapToTicks(true);
        slidNumLanes.setMax(6);

        lblLaneCap.setTooltip(new Tooltip("Base Per lane capacity is defined in Passenger-cars per Lane per Hour," +
                "and converted\n to vehicles per lane per hour (veh/ln/hr) using the specified truck percentage"));

        txtAADT.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtAADT.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        JFXComboBox<Label> cmbPeak = new JFXComboBox<>();
        cmbPeak.getItems().add(new Label("AM Peak"));
        cmbPeak.getItems().add(new Label("PM Peak"));
        cmbPeak.setMaxWidth(Integer.MAX_VALUE);
        cmbPeak.setPromptText("Demand Profile");
        cmbPeak.getStyleClass().add("jfx-combo-style");

        JFXComboBox<Label> cmbTerType = new JFXComboBox<>();
        cmbTerType.getItems().add(new Label("Level"));
        cmbTerType.getItems().add(new Label("Rolling"));
        cmbTerType.getItems().add(new Label("Mountainous"));
        cmbTerType.setMaxWidth(Integer.MAX_VALUE);
        cmbTerType.setPromptText("Terrain Type");
        cmbTerType.getStyleClass().add("jfx-combo-style");

        JFXButton btnUpdAnalysis = new JFXButton("Update Analysis");
        FontIcon update = IconHelper.createIcon(FontAwesomeSolid.SYNC_ALT, Color.WHITE, 20);
        btnUpdAnalysis.setGraphic(update);
        btnUpdAnalysis.getStyleClass().add("jfx-button-style");

        GridPane inputGrids = new GridPane();
        BorderPane navPane = new BorderPane();
        BorderPane graphPane = new BorderPane();
        BorderPane aadtBP = new BorderPane();
        BorderPane dirSplitBP = new BorderPane();
        BorderPane trucksBP = new BorderPane();
        BorderPane laneCapBP = new BorderPane();
        BorderPane topPane = new BorderPane();
        GridPane.setHgrow(txtAADT, Priority.ALWAYS);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        DecimalFormat format_2f = new DecimalFormat("#0.00");
        slidDirSplit.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                return format_2f.format(aDouble);
            }

            @Override
            public Double fromString(String s) {
                try {
                    return (double) format_2f.parse(s);
                } catch (ParseException e) {
                    return 0.0;
                }
            }
        });
        Label sliderLabel = new Label("(" + format_2f.format(slidDirSplit.getValue()) + ")");
        sliderLabel.setMaxWidth(Integer.MAX_VALUE);
        sliderLabel.setMaxHeight(Integer.MAX_VALUE);
        sliderLabel.setAlignment(Pos.TOP_CENTER);
        sliderLabel.setStyle("-fx-text-fill: Black; -fx-font-size: 14px; -fx-padding: 0 0 0 15; ");
        slidDirSplit.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                if (!oldVal.equals(newVal) && !slidDirSplit.isValueChanging()) {
                    sliderLabel.setText("(" + format_2f.format(newVal) + ")");
                }
            }
        });

        xAxis.setLabel("Time of Day");
        yAxis.setLabel("Demand (veh/hr)");

        aadtBP.setLeft(txtAADT);
        aadtBP.setRight(lblvehPerDay);

        dirSplitBP.setLeft(slidDirSplit);
        dirSplitBP.setRight(sliderLabel);

        trucksBP.setLeft(txtPercTrucks);
        trucksBP.setRight(lblTrucks);

        laneCapBP.setLeft(txtLaneCap);
        laneCapBP.setRight(lblvehLnHr);

        navPane.setCenter(btnUpdAnalysis);
        navPane.setLeft(prevIcon);
        navPane.setRight(nextIcon);
        navPane.setPadding(new Insets(7, 20, 7, 20));

        //columns, rows
        inputGrids.add(lblAADT, 0, 0);
        inputGrids.add(lblDirSplit, 0, 1);
        inputGrids.add(lblcompDir, 0, 2);
        inputGrids.add(aadtBP, 1, 0);
        inputGrids.add(dirSplitBP, 1, 1);
        inputGrids.add(lblcompValue, 1, 2);
        inputGrids.add(cmbPeak, 3, 0);
        inputGrids.add(trucksBP, 3, 1);
        inputGrids.add(cmbTerType, 3, 2);
        inputGrids.add(lblLaneCap, 4, 0);
        inputGrids.add(lblNumLanes, 4, 1);
        inputGrids.add(lblSegCap, 4, 2);
        inputGrids.add(laneCapBP, 5, 0);
        inputGrids.add(slidNumLanes, 5, 1);
        inputGrids.setHgap(20);
        inputGrids.setVgap(12);
        inputGrids.setPadding(new Insets(15, 0, 10, 0));

        //creates a line chart
        final LineChart<Number, Number> demandLine = new LineChart<Number, Number>(xAxis, yAxis);
        demandLine.setTitle("24-Hour Demand vs Project Capacities");
        demandLine.setCreateSymbols(false);
        //defines a series
        XYChart.Series demandSeries = new XYChart.Series();
        demandSeries.setName("Demand Data");
        //populates series with data
        demandSeries.getData().add(new XYChart.Data(1, 500));
        demandSeries.getData().add(new XYChart.Data(2, 400));
        demandSeries.getData().add(new XYChart.Data(3, 2000));
        demandSeries.getData().add(new XYChart.Data(4, 2500));
        demandSeries.getData().add(new XYChart.Data(5, 5500));
        demandSeries.getData().add(new XYChart.Data(6, 2500));
        demandSeries.getData().add(new XYChart.Data(7, 2700));
        demandSeries.getData().add(new XYChart.Data(8, 2900));
        demandSeries.getData().add(new XYChart.Data(9, 3100));
        demandSeries.getData().add(new XYChart.Data(10, 4000));
        demandSeries.getData().add(new XYChart.Data(11, 500));

        XYChart.Series capacitySeries = new XYChart.Series();
        capacitySeries.getData().add(new XYChart.Data(1, 4500));
        capacitySeries.getData().add(new XYChart.Data(2, 4500));
        capacitySeries.getData().add(new XYChart.Data(3, 4500));
        capacitySeries.getData().add(new XYChart.Data(4, 4500));
        capacitySeries.getData().add(new XYChart.Data(5, 4500));
        capacitySeries.getData().add(new XYChart.Data(6, 4500));
        capacitySeries.getData().add(new XYChart.Data(7, 4500));
        capacitySeries.getData().add(new XYChart.Data(8, 4500));
        capacitySeries.getData().add(new XYChart.Data(9, 4500));
        capacitySeries.getData().add(new XYChart.Data(10, 4500));
        capacitySeries.getData().add(new XYChart.Data(11, 4500));
        capacitySeries.setName("Work Zone Capacity");

        demandLine.getData().addAll(capacitySeries, demandSeries);

        graphPane.setCenter(demandLine);
        topPane.setCenter(inputGrids);
        topPane.setBottom(navPane);
        inputGrids.setAlignment(Pos.CENTER);
        this.setTop(topPane);
        this.setCenter(graphPane);

        this.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
    }
}
