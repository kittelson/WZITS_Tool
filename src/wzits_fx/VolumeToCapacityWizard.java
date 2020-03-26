package wzits_fx;

import GUI.Helper.IconHelper;
import GUI.Helper.NodeFactory;
import GUI.MainController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import core.Project;
import core.VCWizard.AADTDistributionHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
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
    private final MainController control;

    /**
     * Input text field for AADT.
     */
    Label lblAadtInput = new Label();
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
    JFXComboBox<TerrainTypeItem> inputTerrainType = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT distribution profile primary type.
     */
    /***
     * dropdown selction for volume profile
     */
    JFXComboBox<VolumeProfileItem> inputVolumeProfile = new JFXComboBox<>();
    JFXComboBox<String> inputAADTProfile = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT distribution profile subtype
     */
    JFXComboBox<ProfileSubTypeItem> inputAADTProfileSubType = new JFXComboBox<>();
    /**
     *  Dropdown selection for volume scaling item
     */
    JFXComboBox<VolumeScalingItem> inputVolumeScaling = new JFXComboBox<>();
    /**
     * XYChart Number series to display the Intersection type.
     */
    JFXComboBox<IntxTypeInput> inputIntxType = new JFXComboBox<>();
    /**
     * drop down to select the workzone type
     */
    JFXComboBox<WorkzoneTypeItem> inputWorkzoneType = new JFXComboBox<>();
    /**
     * text input for work zone lake capacity
     */
    JFXTextField txtWorkzoneCap = new JFXTextField();
    /**
     * combobox for user to select how many lanes are closed in the workzone
     */
    JFXComboBox<WZnumLanesClosedInput> inputWZnumLanesClosed = new JFXComboBox<>();
    /**
     * XYChart Number series to display the Intersection type.
     */
    XYChart.Series<Number, Number> demandSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the capacity line
     */
    XYChart.Series<Number, Number> capacitySeries = new XYChart.Series<>();
    /**
     * tabpane that will hold various user inputs
     */
    TabPane vcInputPanes = new TabPane();
    /**
     * tabs for tabpane
     */
    Tab tabGeneral = new Tab();
    Tab tabWZconfig = new Tab();
    BorderPane testPane = new BorderPane();
    BorderPane topPane = new BorderPane();
    GridPane inputGrids = new GridPane();
    GridPane gridWZconfig = new GridPane();


    Project proj = new Project();

    Label lblTitle = new Label("Volume-to-Capacity Wizard");
    Label lblAADT = new Label("Bidirectional AADT:");
    Label lblDirSplit = new Label("Directional Split:");
    Label lblcompDir = new Label("Computed Directional AADT:");
    Label lblIntxTyp = new Label("Intersection Type");
    Label lblLaneCap = new Label("Base Per Lane Capacity:");
    Label lblNumLanes = new Label("Number of Lanes:");
    Label lblNumberofLanesInput = new Label();
    Label lblSegCap = new Label("Total Segment Capacity:");
    Label lblvehPerDay = new Label("veh/day");
    Label lblTrucks = new Label("%");
    Label lblvehLnHr = new Label("pc/ln/hr");
    Label lblPercentTrucks = new Label("Percent Trucks:");
    Label lblDemandProfile = new Label("Demand Profile:");
    Label lblTerrainType = new Label("Terrain Type:");
    Label lblAADTsubType = new Label("AADT Subtype:");
    Label lblInputVolScaling = new Label("Volume Scaling:");
    Label lblWrkZoneTyp = new Label("Work zone Type:");
    Label lblWZLaneCap = new Label("Work zone Lane Capacity:");
    Label lblNumWZlaneClosed = new Label("Work zone number of lanes closed:");
    Label lblMaxVCratio = new Label("Max V/C ratio:");
    Label lblHrsAbovCap = new Label("Total Hours Above Capacity:");
    Label lblTimeAboveCap = new Label("Longest Time Above Capacity:");
    Label lblLvlofCongest = new Label("Level of Congestion:");
    Label lblEstQueLength = new Label("Estimated Queue Length:");
    Label lblMaxVCratioComputed = new Label("Placeholder text will be an output value");
    Label lblHrsAbovCapOutputComputed = new Label("Placeholder text will be an output value");
    Label lblTimeAboveCapOutputComputed = new Label("Placeholder text will be an output value");
    Label lblLvlofCongestOutputComputed = new Label("Placeholder text will be an output value");
    Label lblEstQueLengthOutputComputed = new Label("Placeholder text will be an output value");
    Label lblVolumeProfile = new Label("Volume Profile");
    Label lblAreaTypeInput = new Label();
    Label lblFunctionalClass = new Label("Functional Class of Roadway:");
    Label lblFunctionalClassInput = new Label();
    Label lblAreaType = new Label("Area Type:");
    JFXButton btnUpdAnalysis = new JFXButton("Update Analysis");


    public VolumeToCapacityWizard(MainController mc) {
        this.control = mc;

        JFXButton btnPrev = new JFXButton();

        FontIcon prevIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_LEFT, Color.rgb(68, 96, 114), 30);
        btnPrev.setGraphic(prevIcon);
        FontIcon nextIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_RIGHT, Color.rgb(68, 96, 114), 30);
        btnPrev.setGraphic(nextIcon);

        directionalSplitSlider.setShowTickMarks(true);
        directionalSplitSlider.setShowTickLabels(true);
        //directionalSplitSlider.setValue(0.5); // COMMENT: Moved to different method, just an organization thing.
        directionalSplitSlider.setMin(0.00);
        directionalSplitSlider.setBlockIncrement(0.01);
        directionalSplitSlider.setMajorTickUnit(.2);
        directionalSplitSlider.setMinorTickCount(19);
        directionalSplitSlider.setSnapToTicks(true);
        directionalSplitSlider.setMax(1.0);
        slidNumLanes.setShowTickMarks(true);
        slidNumLanes.setShowTickLabels(true);
        //slidNumLanes.setValue(2); // COMMENT: Moved to different method, just an organization thing.
        slidNumLanes.setMin(1);
        slidNumLanes.setBlockIncrement(1);
        slidNumLanes.setMajorTickUnit(1);
        slidNumLanes.setMinorTickCount(0);
        slidNumLanes.setSnapToTicks(true);
        slidNumLanes.setMax(6);

        Tooltip baseCapacityTooltip = new Tooltip("Base Per lane capacity is defined in Passenger-cars per Lane per Hour," +
                "and converted\n to vehicles per lane per hour (veh/ln/hr) using the specified truck percentage");
        baseCapacityTooltip.setShowDuration(Duration.INDEFINITE);
        lblLaneCap.setTooltip(baseCapacityTooltip);
        lblAadtInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    lblAadtInput.setText(newValue.replaceAll("[^\\d]", ""));
                    System.out.println(lblAadtInput);
                }
            }
        });

        inputBaseLaneCapacity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observableValue,
                    String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputBaseLaneCapacity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        control.getProject().functionalClassProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old_val, String new_val) {
                lblFunctionalClassInput.setText(new_val);
            }
        });
        control.getProject().areaTypeProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old_val, String new_val) {
                lblAreaTypeInput.setText(new_val);
            }
        });
        control.getProject().aadtProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old_val, Number new_val) {
                lblAadtInput.setText(String.valueOf(new_val));
            }
        });
        control.getProject().numRoadwayLanesProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old_val, Number new_val) {
                lblNumberofLanesInput.setText(String.valueOf(new_val));
            }
        });

        inputAADTProfile.getItems().add("Rural/Weekend");
        inputAADTProfile.getItems().add("AM Peak");
        inputAADTProfile.getItems().add("PM Peak");
        inputAADTProfile.setMaxWidth(Integer.MAX_VALUE);
        inputAADTProfile.getSelectionModel().select(1);

        inputTerrainType.setMaxWidth(Integer.MAX_VALUE);
        inputAADTProfileSubType.setMaxWidth(Integer.MAX_VALUE);
        inputVolumeScaling.setMaxWidth(Integer.MAX_VALUE);
        inputIntxType.setMaxWidth(Integer.MAX_VALUE);
        inputWorkzoneType.setMaxWidth(Integer.MAX_VALUE);
        inputWZnumLanesClosed.setMaxWidth(Integer.MAX_VALUE);
        inputVolumeProfile.setMaxWidth(Integer.MAX_VALUE);
        inputVolumeProfile.setMaxWidth(Integer.MAX_VALUE);

        btnUpdAnalysis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                inputParametersUpdated();
            }
        });
        FontIcon update = IconHelper.createIcon(FontAwesomeSolid.SYNC_ALT, Color.WHITE, 20);
        btnUpdAnalysis.setGraphic(update);

        GridPane outputPane = new GridPane(); // this gridpane holds the the results to the right of the graph
        BorderPane navPane = new BorderPane();
        BorderPane graphPane = new BorderPane();
        BorderPane aadtBP = new BorderPane();
        BorderPane dirSplitBP = new BorderPane();
        BorderPane trucksBP = new BorderPane();
        BorderPane laneCapBP = new BorderPane();
        GridPane.setHgrow(lblAadtInput, Priority.ALWAYS);

        /*
        formats the label next to the directional split slider to be 2 decimal places
         */
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
        aadtBP.setLeft(lblAadtInput);
        aadtBP.setRight(lblvehPerDay);

        dirSplitBP.setBottom(directionalSplitSlider);
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
        inputGrids.add(lblFunctionalClass,0,0);
        inputGrids.add(lblAADT,0,1);
        inputGrids.add(lblNumLanes, 0, 2);
        inputGrids.add(lblAreaType,0,3);

        inputGrids.add(lblVolumeProfile,2,3);
        inputGrids.add(lblInputVolScaling,2,4);
        inputGrids.add(lblFunctionalClassInput, 1,0);
        inputGrids.add(aadtBP, 1, 1);
        inputGrids.add(lblNumberofLanesInput,1,2);
        inputGrids.add(lblAreaTypeInput,1,3);
        inputGrids.add(inputVolumeScaling,3,4);
        inputGrids.add(lblPercentTrucks,2,0);
        inputGrids.add(lblTerrainType, 2,2);
        inputGrids.add(lblDirSplit,2,1);
        inputGrids.add(directionalSplitSlider,3,1);
        inputGrids.add(trucksBP, 3, 0);
        inputGrids.add(inputTerrainType, 3, 2);
        inputGrids.add(inputVolumeProfile,3,3);

        gridWZconfig.add(lblLaneCap, 0, 0);
        gridWZconfig.add(lblIntxTyp, 0,1);
        gridWZconfig.add(lblWrkZoneTyp,0,2);
        gridWZconfig.add(lblWZLaneCap, 0, 3);
        gridWZconfig.add(lblNumWZlaneClosed,0,4);

        gridWZconfig.add(laneCapBP, 1, 0);
        gridWZconfig.add(inputIntxType, 1,1);
        gridWZconfig.add(inputWorkzoneType, 1, 2);
        gridWZconfig.add(txtWorkzoneCap, 1, 3);
        gridWZconfig.add(inputWZnumLanesClosed, 1,4);


        //gridWZconfig.add(slidNumLanes, 5, 1);
        gridWZconfig.add(computedSegmentCapacityLabel, 5, 2);
        gridWZconfig.setHgap(20);
        gridWZconfig.setVgap(30);
        gridWZconfig.setPadding(new Insets(15, 0, 10, 0));

        outputPane.add(lblMaxVCratio,0,0);
        outputPane.add(lblHrsAbovCap,0,1);
        outputPane.add(lblTimeAboveCap,0,2);
        outputPane.add(lblLvlofCongest,0,3);
        outputPane.add(lblEstQueLength,0,4);
        outputPane.add(lblMaxVCratioComputed,1,0);
        outputPane.add(lblHrsAbovCapOutputComputed,1,1);
        outputPane.add(lblTimeAboveCapOutputComputed,1,2);
        outputPane.add(lblLvlofCongestOutputComputed,1,3);
        outputPane.add(lblEstQueLengthOutputComputed,1,4);

        outputPane.setHgap(20);
        outputPane.setVgap(30);
        outputPane.setPadding(new Insets(15, 0, 10, 0));
        outputPane.setPrefWidth(650);
        outputPane.setMinWidth(500);

        testPane.setStyle("-fx-background-color: Red");
        //BorderPane.setAlignment(testPane, Pos.BASELINE_RIGHT);
        //testPane.setCenter(gridWZconfig);
        //adding tabs to tabpane
        tabGeneral.setText("General");
        tabGeneral.setContent(inputGrids);
        tabWZconfig.setContent(gridWZconfig);

        tabWZconfig.setText("Work zone Configuration");
        vcInputPanes.getTabs().addAll(tabGeneral,tabWZconfig);
        vcInputPanes.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //creates a line chart
        final NumberAxis xAxis = new NumberAxis("Time of Day",0,24,1);
        final NumberAxis yAxis = new NumberAxis();
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
        topPane.setTop(lblTitle);
        lblTitle.setAlignment(Pos.CENTER);
        topPane.setCenter(inputGrids);
        topPane.setBottom(navPane);
        topPane.setTop(vcInputPanes);
        inputGrids.setAlignment(Pos.CENTER);
        gridWZconfig.setAlignment(Pos.CENTER);
        inputGrids.setVgap(35);
        inputGrids.setHgap(20);
        this.setTop(topPane);
        this.setCenter(graphPane);
        this.setRight(outputPane);

        this.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());

        setupComboBoxInputs();
        formatLabels();
        setupTooltips();
        configureInitialDefaults();
        setLabelStyles();
        formnatTextfields(inputBaseLaneCapacity);
    }

    private void formatLabels() {
        lblTitle.setMaxWidth(Integer.MAX_VALUE);
        computedAADTLabel.setMaxWidth(Integer.MAX_VALUE);
        computedSegmentCapacityLabel.setMaxWidth(Integer.MAX_VALUE);
        lblMaxVCratioComputed.setMaxWidth(Integer.MAX_VALUE);
    }
    /*
       method for inputs to format textfields to add a "," seperator ever 3 decimal places
     */
    private void formnatTextfields(JFXTextField textFieldInput) {
        final char seperatorChar = ',';

        final Pattern numPattern = Pattern.compile("[0-9" + seperatorChar + "]*");
        textFieldInput.setTextFormatter(new TextFormatter<>(c -> {
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
    }

    private void setLabelStyles() {
        lblTitle.getStyleClass().add("vc-title");
        lblAADT.getStyleClass().add("vc-label-styles");
        lblcompDir.getStyleClass().add("vc-label-styles");
        lblDirSplit.getStyleClass().add("vc-label-styles");
        lblAADTsubType.getStyleClass().add("vc-label-styles");
        lblTerrainType.getStyleClass().add("vc-label-styles");
        lblPercentTrucks.getStyleClass().add("vc-label-styles");
        lblDemandProfile.getStyleClass().add("vc-label-styles");
        lblvehPerDay.getStyleClass().add("vc-units");
        lblLaneCap.getStyleClass().add("vc-label-styles");
        lblNumLanes.getStyleClass().add("vc-label-styles");
        lblTrucks.getStyleClass().add("vc-label-styles");
        lblSegCap.getStyleClass().add("vc-label-styles");
        lblvehLnHr.getStyleClass().add("vc-units");
        computedAADTLabel.getStyleClass().add("vc-label-styles");
        lblAadtInput.getStyleClass().add("vc-label-input");
        inputBaseLaneCapacity.getStyleClass().add("jf-txtbox");
        inputTruckPct.getStyleClass().add("jf-txtbox");
        inputAADTProfile.getStyleClass().add("jfx-combo-style");
        btnUpdAnalysis.getStyleClass().add("jfx-button-style");
        inputTerrainType.getStyleClass().add("jfx-combo-style");
        computedSegmentCapacityLabel.getStyleClass().add("vc-label-styles");
        inputAADTProfileSubType.getStyleClass().add("jfx-combo-style");
        lblInputVolScaling.getStyleClass().add("vc-label-styles");
        lblWrkZoneTyp.getStyleClass().add("vc-label-styles");
        lblWZLaneCap.getStyleClass().add("vc-label-styles");
        lblNumWZlaneClosed.getStyleClass().add("vc-label-styles");
        lblMaxVCratio.getStyleClass().add("vc-label-styles");
        lblHrsAbovCap.getStyleClass().add("vc-label-styles");
        lblTimeAboveCap.getStyleClass().add("vc-label-styles");
        lblLvlofCongest.getStyleClass().add("vc-label-styles");
        lblEstQueLength.getStyleClass().add("vc-label-styles");
        lblMaxVCratioComputed.getStyleClass().add("vc-label-input");
        lblHrsAbovCapOutputComputed.getStyleClass().add("vc-label-input");
        lblTimeAboveCapOutputComputed.getStyleClass().add("vc-label-input");
        lblIntxTyp.getStyleClass().add("vc-label-styles");
        lblLvlofCongestOutputComputed.getStyleClass().add("vc-label-input");
        lblEstQueLengthOutputComputed.getStyleClass().add("vc-label-input");
        lblFunctionalClassInput.getStyleClass().add("vc-label-input");
        lblFunctionalClass.getStyleClass().add("vc-label-styles");
        lblAreaType.getStyleClass().add("vc-label-styles");
        lblAreaTypeInput.getStyleClass().add("vc-label-input");
        lblNumberofLanesInput.getStyleClass().add("vc-label-input");
        lblVolumeProfile.getStyleClass().add("vc-label-styles");
        inputVolumeProfile.getStyleClass().add("jfx-combo-style");
        inputVolumeScaling.getStyleClass().add("jfx-combo-style");
    }

    private void configureInitialDefaults() {

        lblFunctionalClassInput.textProperty().bindBidirectional(new SimpleStringProperty(proj.getFunctionalClass()));
        lblAreaTypeInput.textProperty().bindBidirectional(new SimpleStringProperty(proj.getAreaType()));
        directionalSplitSlider.setValue(0.5);
        inputAADTProfile.getSelectionModel().selectFirst();
        inputTruckPct.setText("5");
        inputTerrainType.getSelectionModel().selectFirst();
        inputBaseLaneCapacity.setText("2400"); // HCM Default Capacity
        slidNumLanes.setValue(2); // COMMENT: Moved from constructor to here
        inputAADTProfileSubType.getSelectionModel().selectFirst();
        inputVolumeScaling.getSelectionModel().select(2);

    }

    private void setupComboBoxInputs() {
        inputAADTProfileSubType.getItems().addAll(
                new ProfileSubTypeItem("Average", AADTDistributionHelper.TYPE_DEFAULT_SUB_AVG),
                new ProfileSubTypeItem("Minimum", AADTDistributionHelper.TYPE_DEFAULT_SUB_MIN),
                new ProfileSubTypeItem("25th Percentile", AADTDistributionHelper.TYPE_DEFAULT_SUB_25),
                new ProfileSubTypeItem("Median", AADTDistributionHelper.TYPE_DEFAULT_SUB_MEDIAN),
                new ProfileSubTypeItem("75th Percentile", AADTDistributionHelper.TYPE_DEFAULT_SUB_75),
                new ProfileSubTypeItem("Maximum", AADTDistributionHelper.TYPE_DEFAULT_SUB_MAX)
        );

        inputTerrainType.getItems().addAll(
                new TerrainTypeItem("Level", TERRAIN_TYPE_LEVEL),
                new TerrainTypeItem("Rolling", TERRAIN_TYPE_ROLLING),
                new TerrainTypeItem("Mountainous", TERRAIN_TYPE_MOUNTAIN)
        );
        inputVolumeScaling.getItems().addAll(
                //todo get correct string values rather then high, medium, low and their associated values
                new VolumeScalingItem("Very High", 3),
                new VolumeScalingItem("High", 2),
                new VolumeScalingItem("Average", 3),
                new VolumeScalingItem("Moderate", 2),
                new VolumeScalingItem("Peaking", 1)
        );
        inputIntxType.getItems().addAll(
                //todo updates these strings and values
                new IntxTypeInput("None", 1),
                new IntxTypeInput("Roundabout", .50),
                new IntxTypeInput("Two-Way Stop", 1),
                new IntxTypeInput("All Way Stop", 0.30),
                new IntxTypeInput("Two-Phase Signal", 0.60),
                new IntxTypeInput("Three Phase Signal", 0.45),
                new IntxTypeInput("Four Phase Signal", 0.35),
                new IntxTypeInput("Custom", 0.0)
        );
        inputWorkzoneType.getItems().addAll(
                //todo update strings and values
                new WorkzoneTypeItem("1-Lane Closure", 4),
                new WorkzoneTypeItem("Shoulder Closure", 3),
                new WorkzoneTypeItem("None", 2),
                new WorkzoneTypeItem("Crossover", 5),
                new WorkzoneTypeItem("Custom", 6)
                );
        inputWZnumLanesClosed.getItems().addAll(
                new WZnumLanesClosedInput("Zero", 0),
                new WZnumLanesClosedInput("One", 1),
                new WZnumLanesClosedInput("Two", 2)
        );
        inputVolumeProfile.getItems().addAll(
                new VolumeProfileItem("Rural", 2),
                new VolumeProfileItem("Urban", 1)
        );
    }

    private void setupTooltips() {

        final Tooltip computedAADTTooltip = new Tooltip();
        computedAADTTooltip.setShowDuration(Duration.INDEFINITE);
        computedAADTTooltip.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                int aadt = 0;
                try {
                    aadt = Integer.parseInt(lblAadtInput.getText().replace(",", ""));
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
    /*
    Gives user an error message if they fail to enter a value in a textfield, currently only used
    in the aadtInput textfield
     */
    private void validateUserInput(RequiredFieldValidator textValidator, JFXTextField textInput) {
        textValidator.setMessage("Input Required");
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        textValidator.setIcon(warnIcon);
        textInput.getValidators().add(textValidator);
        textInput.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                textInput.validate();
            }
        });
    }

    private void inputParametersUpdated() {

        // compute aadt
        int aadt = 0;
        try {
            aadt = Integer.parseInt(lblAadtInput.getText().replace(",", ""));
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
//        float truckPCEquiv = this.inputTerrainType.getSelectionModel().getSelectedIndex() == 0 ? 2.0f : 2.5f;
        float truckPCEquiv = this.inputTerrainType.getValue().value;
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
     * @param coe   coefficient
     * @return value in veh
     */
    public static float pc_to_veh(float value, float coe) {
        return value * coe;
    }

    private class ProfileSubTypeItem {
        /**
         * The string representation of the value that will be displayed in the list of combobox choices (using the overriden toString method)
         */
        public final String displayStr;
        /**
         * The actual value of the object (this doesn't have to be an integer, it can be anything!)
         */
        public final int value;

        /**
         * Constructor to create the object, where the display string and value are specified.
         *
         * @param displayStr
         * @param value
         */
        public ProfileSubTypeItem(String displayStr, int value) {
            // Immediately set the display string and value;
            this.displayStr = displayStr;
            this.value = value;
        }

        /**
         * When displaying each object as a string in the ComboBox choice list, the underlying code automatically calls
         * this method.  By overriding it, we can control what value is actually shown as opposed to the default
         * representation.
         *
         * @return
         */
        @Override
        public String toString() {
            return this.displayStr;
        }
    }
    private class IntxTypeInput {
        public final String displayStr;
        public final double value;

        public IntxTypeInput(String displayStr, double value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {return this.displayStr;}
    }

    private class WZnumLanesClosedInput {
        public final String displayStr;
        public final int value;

        public WZnumLanesClosedInput(String displayStr, int value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {return this.displayStr;}
    }

    private class VolumeProfileItem {
        public final String displayStr;
        public final double value;

        public VolumeProfileItem(String displayStr, double value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {return this.displayStr;}
    }

    private class VolumeScalingItem {
        /**
         * string representation of value that will be displayed in the combobox
         */
        public final String displayStr;
        /**
         * actual value of the object (not necessarily an int, can be anything
         */
        public final int value;
        /**
         * constructior that will create hte object, location that display string and value are specified
         *@param displayStr;
         *@param value;
        */
        public VolumeScalingItem(String displayStr, int value) {
            // set the display string and value
            this.displayStr = displayStr;
            this.value = value;
        }
        @Override
        public String toString() {return this.displayStr;}
    }
    private class WorkzoneTypeItem {
        public final String displayStr;
        public final int value;

        public WorkzoneTypeItem(String displayStr, int value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {return this.displayStr;}
    }


    private class TerrainTypeItem {
        public final String displayStr;
        public final float value;

        public TerrainTypeItem(String displayStr, float value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.displayStr;
        }
    }


    private static final float TERRAIN_TYPE_LEVEL = 2.0f;
    private static final float TERRAIN_TYPE_ROLLING = 2.5f;
    private static final float TERRAIN_TYPE_MOUNTAIN = 3.0f;
}
