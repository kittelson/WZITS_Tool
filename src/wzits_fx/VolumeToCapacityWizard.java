package wzits_fx;

import GUI.Helper.IconHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import core.VCWizard.AADTDistributionHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class VolumeToCapacityWizard extends BorderPane {

    /**
     * Input text field for AADT.
     */
    JFXTextField aadtInputTextField = new JFXTextField();
    /**
     * Label that displays the computed AADT Value.
     */
    Label computedAADTLabel = new Label();
    /**
     * Slider for choosing the number of lanes.
     */
    JFXSlider slidNumLanes = new JFXSlider();
    /**
     * Slider for choosing the directional split factor.
     */
    JFXSlider directionalSplitSlider = new JFXSlider();
    /**
     * Label that displays the computed total segment capacity value.
     */
    Label computedSegmentCapacityLabel = new Label();
    /**
     * Input text field to specify the per lane capacity (veh/hr/ln)
     */
    JFXTextField inputBaseLaneCapacity = new JFXTextField();
    /**
     * Input text field for truck percentage
     */
    JFXTextField inputTruckPct = new JFXTextField();
    /**
     * Dropdown selection for the terrain type of the facility [Level, Rolling, Mountainous].
     */
    JFXComboBox<String> inputTerrainType = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT distribution profile primary type.
     */
    JFXComboBox<String> inputAADTProfile = new JFXComboBox<>();
    /**
     * XYChart Number series to display the demand profile.
     */
    XYChart.Series<Number, Number> demandSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the capacity line
     */
    XYChart.Series<Number, Number> capacitySeries = new XYChart.Series<>();


    public VolumeToCapacityWizard() {

        Label lblAADT = new Label("Bidirectional AADT");
        Label lblDirSplit = new Label("Directional Split");
        Label lblcompDir = new Label("Computed Directional AADT");
        Label lblLaneCap = new Label("Base Per Lane Capacity");
        Label lblNumLanes = new Label("Number of Lanes");
        Label lblSegCap = new Label("Total Segment Capacity");
        Label lblvehPerDay = new Label("veh/day");
        Label lblTrucks = new Label("%");
        Label lblvehLnHr = new Label("pc/ln/hr");
        final char seperatorChar = ',';

        final Pattern numPattern = Pattern.compile("[0-9" + seperatorChar + "]*");
        aadtInputTextField.setTextFormatter(new TextFormatter<>(c -> {
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

        inputTruckPct.setPromptText("Percent Trucks");
        inputTruckPct.setLabelFloat(true);

        aadtInputTextField.getStyleClass().add("jf-txtbox");
        inputBaseLaneCapacity.getStyleClass().add("jf-txtbox");
        inputTruckPct.getStyleClass().add("jf-txtbox");

        directionalSplitSlider.setShowTickMarks(true);
        directionalSplitSlider.setShowTickLabels(true);
        directionalSplitSlider.setValue(0.5);
        directionalSplitSlider.setMin(0.00);
        directionalSplitSlider.setBlockIncrement(0.01);
        directionalSplitSlider.setMajorTickUnit(.2);
        directionalSplitSlider.setMinorTickCount(19);
        directionalSplitSlider.setSnapToTicks(true);
        directionalSplitSlider.setMax(1.0);
        slidNumLanes.setShowTickMarks(true);
        slidNumLanes.setShowTickLabels(true);
        slidNumLanes.setValue(2);
        slidNumLanes.setMin(1);
        slidNumLanes.setBlockIncrement(1);
        slidNumLanes.setMajorTickUnit(1);
        slidNumLanes.setMinorTickCount(0);
        slidNumLanes.setSnapToTicks(true);
        slidNumLanes.setMax(6);

        lblLaneCap.setTooltip(new Tooltip("Base Per lane capacity is defined in Passenger-cars per Lane per Hour," +
                "and converted\n to vehicles per lane per hour (veh/ln/hr) using the specified truck percentage"));

        aadtInputTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    aadtInputTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        inputAADTProfile.getItems().add("Rural/Weekend");
        inputAADTProfile.getItems().add("AM Peak");
        inputAADTProfile.getItems().add("PM Peak");
        inputAADTProfile.setMaxWidth(Integer.MAX_VALUE);
        inputAADTProfile.setPromptText("Demand Profile");
        inputAADTProfile.getStyleClass().add("jfx-combo-style");
        inputAADTProfile.getSelectionModel().select(1);

        inputTerrainType.getItems().add("Level");
        inputTerrainType.getItems().add("Rolling");
        inputTerrainType.getItems().add("Mountainous");
        inputTerrainType.setMaxWidth(Integer.MAX_VALUE);
        inputTerrainType.setPromptText("Terrain Type");
        inputTerrainType.getStyleClass().add("jfx-combo-style");

        JFXButton btnUpdAnalysis = new JFXButton("Update Analysis");
        btnUpdAnalysis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                inputParametersUpdated();
            }
        });
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
        GridPane.setHgrow(aadtInputTextField, Priority.ALWAYS);

        DecimalFormat format_2f = new DecimalFormat("#0.00");
        directionalSplitSlider.setLabelFormatter(new StringConverter<Double>() {
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
        Label sliderLabel = new Label("(" + format_2f.format(directionalSplitSlider.getValue()) + ")");
        sliderLabel.setMaxWidth(Integer.MAX_VALUE);
        sliderLabel.setMaxHeight(Integer.MAX_VALUE);
        sliderLabel.setAlignment(Pos.TOP_CENTER);
        sliderLabel.setStyle("-fx-text-fill: Black; -fx-font-size: 14px; -fx-padding: 0 0 0 15; ");
        directionalSplitSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                if (!oldVal.equals(newVal) && !directionalSplitSlider.isValueChanging()) {
                    sliderLabel.setText("(" + format_2f.format(newVal) + ")");
                }
            }
        });

        aadtBP.setLeft(aadtInputTextField);
        aadtBP.setRight(lblvehPerDay);

        dirSplitBP.setLeft(directionalSplitSlider);
        dirSplitBP.setRight(sliderLabel);

        trucksBP.setLeft(inputTruckPct);
        trucksBP.setRight(lblTrucks);

        laneCapBP.setLeft(inputBaseLaneCapacity);
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
        inputGrids.add(computedAADTLabel, 1, 2);
        inputGrids.add(inputAADTProfile, 3, 0);
        inputGrids.add(trucksBP, 3, 1);
        inputGrids.add(inputTerrainType, 3, 2);
        inputGrids.add(lblLaneCap, 4, 0);
        inputGrids.add(lblNumLanes, 4, 1);
        inputGrids.add(lblSegCap, 4, 2);
        inputGrids.add(laneCapBP, 5, 0);
        inputGrids.add(slidNumLanes, 5, 1);
        inputGrids.add(computedSegmentCapacityLabel, 5, 2);
        inputGrids.setHgap(20);
        inputGrids.setVgap(12);
        inputGrids.setPadding(new Insets(15, 0, 10, 0));

        //creates a line chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time of Day");
        yAxis.setLabel("Demand (veh/hr)");
        final LineChart<Number, Number> demandToCapacityChart = new LineChart<Number, Number>(xAxis, yAxis);
        demandToCapacityChart.setTitle("24-Hour Demand vs Project Capacities");
        demandToCapacityChart.setCreateSymbols(false);
        //defines a series
        demandSeries.setName("Demand Data");
        capacitySeries.setName("Work Zone Capacity");
        //populates series with initial data
        for (int per = 0; per < 96; per++) {
            demandSeries.getData().add(new XYChart.Data<>(per, 0));
            capacitySeries.getData().add(new XYChart.Data<>(per, 0));
        }

        demandToCapacityChart.getData().add(capacitySeries);
        demandToCapacityChart.getData().add(demandSeries);

        graphPane.setCenter(demandToCapacityChart);
        topPane.setCenter(inputGrids);
        topPane.setBottom(navPane);
        inputGrids.setAlignment(Pos.CENTER);
        this.setTop(topPane);
        this.setCenter(graphPane);

        this.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());

        formatLabels();
        setupTooltips();
    }

    private void formatLabels() {
        computedAADTLabel.setMaxWidth(Integer.MAX_VALUE);
        computedSegmentCapacityLabel.setMaxWidth(Integer.MAX_VALUE);
    }

    private void setupTooltips() {
        final Tooltip computedAADTTooltip = new Tooltip();
        computedAADTTooltip.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                int aadt = 0;
                try {
                    aadt = Integer.parseInt(aadtInputTextField.getText().replace(",", ""));
                } catch (NumberFormatException e) {
                    // do nothing
                }

                double dirSplit = directionalSplitSlider.getValue();

                String tooltipStr = String.format("%,d", Math.round(aadt)) + " * " + String.format("%.2f", dirSplit);
                tooltipStr += " = " + String.format("%,d", Math.round(aadt * dirSplit)) + " veh/day";
                computedAADTTooltip.setText(tooltipStr);
            }
        });
//        computedAADTTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedAADTLabel, computedAADTTooltip);

//        final Tooltip computedCapacityTooltip = new Tooltip();
//        computedCapacityTooltip.setOnShown(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent windowEvent) {
//                int aadt = 0;
//                try {
//                    aadt = Integer.parseInt(aadtInputTextField.getText().replace(",", ""));
//                } catch (NumberFormatException e) {
//                     do nothing
//                }
//
//                double dirSplit = directionalSplitSlider.getValue();
//
//                String tooltipStr = ;
//                tooltipStr += " = " + String.format("%,d", Math.round(aadt * dirSplit)) + " veh/day";
//                computedCapacityTooltip.setText(tooltipStr);
//            }
//        });
//        computedCapacityTooltip.setShowDelay(Duration.ZERO);
//        Tooltip.install(computedSegmentCapacityLabel, computedCapacityTooltip);
    }

    private void inputParametersUpdated() {

        // compute aadt
        int aadt = 0;
        try {
            aadt = Integer.parseInt(aadtInputTextField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            // do nothing
        }

        double dirSplit = directionalSplitSlider.getValue();

        computedAADTLabel.setText(String.format("%,d", Math.round(aadt * dirSplit)) + " veh/day");

        // compute segment capacity
        int basePerLaneCapacityPC = -1;
        try {
            basePerLaneCapacityPC = Integer.parseInt(inputBaseLaneCapacity.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            // Do nothing
        }
        int numLanes = (int) slidNumLanes.getValue();
        float truckPCT = 0.0f;
        try {
            truckPCT = Float.parseFloat(this.inputTruckPct.getText());
        } catch (NumberFormatException e) {

        }
        float truckPCEquiv = this.inputTerrainType.getSelectionModel().getSelectedIndex() == 0 ? 2.0f : 2.5f;
        float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
        int basePerLaneCapacityVeh = Math.round(pc_to_veh(basePerLaneCapacityPC, fhv));
        int totalSegmentCapacityVeh = basePerLaneCapacityVeh * numLanes;
        computedSegmentCapacityLabel.setText(String.format("%,d", totalSegmentCapacityVeh) + " veh/hr"); // baseCapacityVeh * numLanes
        int profileType = inputAADTProfile.getSelectionModel().getSelectedIndex();
        int profileSubType = AADTDistributionHelper.TYPE_DEFAULT_SUB_AVG;
        float[] pctProfile = AADTDistributionHelper.getDefaultProfileInHourlyPct(profileType, profileSubType);
        float directionalAADT = (float) (aadt * dirSplit);
        int[] demandArray = AADTDistributionHelper.get24HourMainlineDemand(pctProfile, directionalAADT);

        for (int per = 0; per < demandSeries.getData().size(); per++) {
            capacitySeries.getData().get(per).setYValue(totalSegmentCapacityVeh);
            demandSeries.getData().get(per).setYValue(demandArray[per]);
//            System.out.println("Period: " + String.valueOf(per + 1) + ", Capacity: " + String.valueOf(totalSegmentCapacityVeh) + ", Demand: " + String.format("%,2d", demandArray[per]));
        }
    }

    /**
     * Convert pc to veh
     *
     * @param value value to be converted
     * @param coe coefficient
     * @return value in veh
     */
    public static float pc_to_veh(float value, float coe) {
        return value * coe;
    }

}
