package wzits_fx;

import GUI.Helper.*;
import GUI.MainController;
import com.jfoenix.controls.*;
import core.VCWizard.AADTDistributionHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;

public class VolumeToCapacityWizard extends BorderPane {
    private final MainController control;
    /**
     * Input echo label for AADT.
     */
    private final Label inputEchoAADT;
    /**
     * Input echo label for the base number of lanes.
     */
    private final Label inputEchoBaseNumberOfLanes;
    /**
     * Input echo label for the area type.
     */
    private final Label inputEchoAreaType;
    /**
     * Input echo label for the functional class.
     */
    private final Label inputEchoFunctionalClass;
    /**
     * Label that displays the computed AADT Value.
     */
    private final Label computedAADTLabel;
    /**
     * Slider for choosing the directional split factor.
     */
    private final JFXSlider inputDirectionalSplitSlider = new JFXSlider();
    /**
     * Label for displaying the value of the directional split slider
     */
    private final Label inputDirectionalSplitValue;
    /**
     * Label that displays the computed total segment capacity value.
     */
    private final Label computedSegmentCapacityLabel;
    /**
     * TextField that displays the saturation flow rate adjustment factor for the selected intersection type. Allows for
     * user input only if the intersection type is "Custom", otherwise the TextField will be disabled.
     */
    private final JFXTextField inputIntxTypeAdjustment = new JFXTextField();
    /**
     * Label to
     */
    private final Label workZoneTypeAdjustmentLabel;
    /**
     * Label that displays the computed total adjusted saturation flow rate value.
     */
    private final Label computedAdjustedSatFlowRateLabel;
    /**
     * Label to display the computed per lane capacity in veh/hr/ln (freeways only)
     */
    private final Label workZoneComputedLaneCapacityVehLabel;
    /**
     * Label to display the computed per lane sat flow rate in veh/hr/ln (non-freeways only)
     */
    private final Label workZoneComputedLaneSatFlowVehLabel;
    /**
     * Label that displays the computed total capacity of the work zone
     */
    private final Label computedTotalWorkZoneCapacityLabel;
    /**
     * Label that displays the computed total saturation flow rate of the work zone
     */
    private final Label computedTotalWorkZoneSatFlowLabel;
    /**
     * Input text field to specify the per lane capacity (pc/hr/ln)
     */
    private final JFXTextField inputBaseLaneCapacity = new JFXTextField();
    /**
     * Input text field to specify base saturation flow rate (pc/hr/ln)
     */
    private final JFXTextField inputBaseLaneSatFlowRate = new JFXTextField();
    /**
     * Input text field for truck percentage
     */
    private final JFXTextField inputTruckPct = new JFXTextField();
    /**
     * Dropdown selection for the terrain type of the facility [Level, Rolling, Mountainous].
     */
    private final JFXComboBox<TerrainTypeItem> inputTerrainType = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT distribution profile primary type.
     */
    private final JFXComboBox<VolumeProfileItem> inputAADTProfile = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT default distribution profile subtype
     */
    private final JFXComboBox<ProfileSubTypeItem> inputAADTProfileSubType = new JFXComboBox<>();
    /**
     * Dropdown selection for the AADT default distribution profile subtype
     */
    private final JFXComboBox<ProfileSubTypeItem> inputAADTNationalProfileSubtype = new JFXComboBox<>();
    /**
     * Wrapper to hold the subtype combobox
     */
    private final BorderPane volumeScalingWrapper = new BorderPane();
    /**
     * Combobox for selecting the intersection type (non-freeway only)
     */
    private final JFXComboBox<IntxTypeInput> inputIntxType = new JFXComboBox<>();
    /**
     * Combobox for selecting the segment type (freeway and interstate only)
     */
    private final JFXComboBox<SegmentTypeItem> inputSegmentType = new JFXComboBox<>();
    /**
     * drop down to select the workzone type
     */
    private final JFXComboBox<WorkZoneTypeItem> inputWorkZoneType = new JFXComboBox<>();
    /**
     * text input for work zone lake capacity
     */
    private final JFXTextField inputWorkZoneLaneCapacity = new JFXTextField();

    private final JFXTextField inputWorkZoneLaneSatFlow = new JFXTextField();
    /**
     * combobox for user to select how many lanes are closed in the workzone
     */
    private final JFXComboBox<WZnumLanesClosedInput> inputWorkZoneNumLanesClosed = new JFXComboBox<>();
    /**
     * XYChart Number series to display the demand.
     */
    private final XYChart.Series<Number, Number> demandSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the capacity line for the base codnitions
     */
    private final XYChart.Series<Number, Number> baseCapacitySeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the capacity line for the work zone configuration
     */
    private final XYChart.Series<Number, Number> workZoneCapacitySeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the estimated queue length for the base conditions.
     */
    private final XYChart.Series<Number, Number> qLengthBaseSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the estimated queue length for the work zone conditions
     */
    private final XYChart.Series<Number, Number> qLengthWorkZoneSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the D/C ratio for the base conditions
     */
    private final XYChart.Series<Number, Number> dcRatioBaseSeries = new XYChart.Series<>();
    /**
     * XYChart Number series to display the D/C ratio for the work zone condtitions
     */
    private final XYChart.Series<Number, Number> dcRatioWorkZoneSeries = new XYChart.Series<>();

    private final Label lblMaxVCRatioBaseComputed;
    private final Label lblMaxVCRatioWorkZoneComputed;
    private final Label lblMaxDCRatioBaseComputed;
    private final Label lblMaxDCRatioWorkZoneComputed;
    private final Label lblHrsAboveBaseCapOutputComputed;
    private final Label lblHrsAboveWZCapOutputComputed;
    private final Label lblStreakAboveBaseCapOutputComputed;
    private final Label lblStreakAboveWZCapOutputComputed ;
    private final Label lblLvlofCongestBaseOutputComputed;
    private final Label lblLvlofCongestWZOutputComputed;
    private final Label lblEstQueLengthBaseOutputComputed;
    private final Label lblEstQueLengthWZOutputComputed;
//    JFXButton btnUpdAnalysis = new JFXButton("Update Analysis");

    private final boolean isFreeway;

    private final Node replacedStepNode;

    public VolumeToCapacityWizard(MainController mc) {
        this(mc, null);
    }

    /**
     *
      * @param mc
     * @param replacedStepNode Node that was replaced by the VC wizard, allows for the back button to work
     */
    public VolumeToCapacityWizard(MainController mc, Node replacedStepNode) {
        this.control = mc;
        this.replacedStepNode = replacedStepNode;
        String projectFuncClass = control.getProject().getFunctionalClass();
        isFreeway = projectFuncClass.equalsIgnoreCase("Interstate") || projectFuncClass.equalsIgnoreCase("Other Freeway");
        this.getStylesheets().add(getClass().getResource("/GUI/CSS/vc-wizard-style.css").toExternalForm());

        JFXButton btnPrev = new JFXButton();

        FontIcon prevIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_LEFT, Color.rgb(68, 96, 114), 30);
        btnPrev.setGraphic(prevIcon);
        FontIcon nextIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_RIGHT, Color.rgb(68, 96, 114), 30);
        btnPrev.setGraphic(nextIcon);

        inputDirectionalSplitSlider.setShowTickMarks(true);
        inputDirectionalSplitSlider.setShowTickLabels(true);
        inputDirectionalSplitSlider.setMin(0.00);
        inputDirectionalSplitSlider.setBlockIncrement(0.01);
        inputDirectionalSplitSlider.setMajorTickUnit(.2);
        inputDirectionalSplitSlider.setMinorTickCount(4);
        inputDirectionalSplitSlider.setMax(1.0);

        inputAADTProfile.setMaxWidth(Integer.MAX_VALUE);
        inputAADTProfile.getSelectionModel().select(1);
        inputTerrainType.setMaxWidth(Integer.MAX_VALUE);
        inputAADTProfileSubType.setMaxWidth(Integer.MAX_VALUE);
        inputAADTNationalProfileSubtype.setMaxWidth(Integer.MAX_VALUE);
        inputIntxType.setMaxWidth(Integer.MAX_VALUE);
        inputSegmentType.setMaxWidth(Integer.MAX_VALUE);
        inputWorkZoneType.setMaxWidth(Integer.MAX_VALUE);
        inputWorkZoneNumLanesClosed.setMaxWidth(Integer.MAX_VALUE);

        // Initialize Input Echo labels
        inputEchoFunctionalClass = NodeFactory.createFormattedLabel("", "label-input-echo");
        inputEchoAreaType = NodeFactory.createFormattedLabel("", "label-input-echo");
        inputEchoBaseNumberOfLanes = NodeFactory.createFormattedLabel("", "label-input-echo");
        inputEchoAADT = NodeFactory.createFormattedLabel("", "label-input-echo");
        // -- Intermediate output labels
        computedAADTLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        computedSegmentCapacityLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        workZoneTypeAdjustmentLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        computedAdjustedSatFlowRateLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        workZoneComputedLaneCapacityVehLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        workZoneComputedLaneSatFlowVehLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        computedTotalWorkZoneCapacityLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        computedTotalWorkZoneSatFlowLabel = NodeFactory.createFormattedLabel("--", "label-output-intermediate");
        // -- Final output labels
        lblMaxDCRatioBaseComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblMaxDCRatioWorkZoneComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblMaxVCRatioBaseComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblMaxVCRatioWorkZoneComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblHrsAboveBaseCapOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblHrsAboveWZCapOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblStreakAboveBaseCapOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblStreakAboveWZCapOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblLvlofCongestBaseOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblLvlofCongestWZOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblEstQueLengthBaseOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");
        lblEstQueLengthWZOutputComputed = NodeFactory.createFormattedLabel("--", "label-output-final");

        GridPane outputPane = new GridPane(); // this gridpane holds the the results to the right of the graph
        BorderPane linePane = new BorderPane();

        /*
        formats the label next to the directional split slider to be 2 decimal places
         */
        DecimalFormat format_2f = new DecimalFormat("#0.00");
        inputDirectionalSplitSlider.setLabelFormatter(new StringConverter<Double>() {
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
        inputDirectionalSplitValue = NodeFactory.createFormattedLabel("", "label-output-intermediate");
        BorderPane inputDirectionalSplit = new BorderPane();
        inputDirectionalSplit.setCenter(inputDirectionalSplitSlider);
        inputDirectionalSplit.setRight(inputDirectionalSplitValue);
        BorderPane.setMargin(inputDirectionalSplitValue, new Insets(0, 0, 0, 10));

        linePane.setMinHeight(5);
        linePane.setStyle("-fx-background-color: grey");
        BorderPane.setMargin(linePane, new Insets(0, 10, 0, 10));

        // Preparing tooltip text
        String ttFunctionalClass = "Functional class of the roadway; inherited from the project's Facility and Base Conditions inputs specified in the General Inputs windows.";
        String ttAreaType = "Area type classification for the roadway (urban or rural); inherited from the project's Facility and Base Conditions inputs specified in the General Inputs window;";
        String ttNumberOfLanes = "Base (non-work zone) number of lanes for a single direction of the facility; inherited from the project's Facility and Base Conditions inputs specified in the General Inputs window.";
        String ttBidirectionalAADT = "Bidirectional Annual Average Daily Traffic (AADT) or Average Daily Traffic (ADT); inherited from the project's Facility and Base Conditions inputs specified in the General Inputs window.";
        String ttDirectionalSplit = "Value between 0.0 and 1.0 that determines the proportional split of the AADT/ADT for single direction of the analysis.\n\nFor example, a value of 0.6 indicates that 60% of the AADT/ADT travels in the analysis direction.";
        String ttComputedDirectionalAADT = "Computed directional AADT/ADT value; computed as the bidirectional AADT/ADT multiplied by the directional split.";
        String ttVolumeProfile = "24-hour volume profile to distribute the computed directional AADT/ADT over the course of the analysis day.";
        String ttVolumeScaling = "When available, the scaling factor changes the characteristics of the 24-hour volume profile. Average scaling approximates \"standard\" characteristics, while minimum provides a flatter profile and moving up to very high provides increased emphasis on the peak(s).";
        String ttTerrainType = "Terrain type that has the largest affect on the work zone operations; the terrain type affects the truck-passenger car equivalent (PCE) value. Level terrain corresponds to a value of 2.0, rolling corresponds to a value of 2.5, and mountainous corresponds to a value of 3.0";
        String ttPercentTrucks = "The percentage of the directional AADT/ADT that is single unit or tractor trailer trucks.  The truck % affects the passenger car to vehicle conversion for capacity computations.";
        String ttBaseCapacity = "Base lane capacity is defined in passenger cars per lane per hour (pc/ln/hr) and converted to vehicles per lane per hour (veh/ln/hr) using the specified truck percentage and truck-passenger equivalent determined by the terrain type selection.";
        String ttComputedBaseCapacity = "The total base (non-work zone) capacity of a cross-section across all lanes of the analyzed segment. This value has been adjusted by the terrain type and truck % inputs, and is reflected by the dashed black line on the chart.";
        String ttBaseSatFlowRate = "The saturation flow rate (pc/ln/hr) for base (non-work zone) conditions on the facility. This value can be adjusted further by selecting an intersection type.";
        String ttSatFlowRateDefault = "For typical conditions, the recommended base saturation flow rate is 1,9000 pc/hr/ln for urban areas and 1,750 pc/hr/ln for rural areas";
        String ttIntersectionType;
        if (isFreeway) {
            ttIntersectionType = "Segment type of the facility on which the work zone is occurring; selecting a non-basic segment will result in a reduction of total base (non-work zone) capacity.";
        } else {
            ttIntersectionType = "Intersection type (if any) of the facility on which the work zone is occurring; selecting an intersection will result in a reduction of total base (non-work zone) capacity.\n\nIf none of the given default values are applicable, select \"Custom\" to define a site-specific adjustment value.";
        }
        String ttAdjustedSatFlowRate = "Adjusted saturation flow rate for the base (non-work zone) conditions in veh/hr.  The value is determined as the base saturation flow rate times the intersection type adjustment factor, then converted from pc/hr/ln to veh/hr/ln using the terrain type and truck % values, and finally multiplied by the number of lanes. This value is represented by the dashed black line on the chart.";
        String ttWorkZoneType = "Type of work zone active in the analysis segment.  Available choices include shoulder closures, lane closures, lane crossovers, or a site-specific custom option.";
        String ttWorkZoneTypeAdjustment = "Adjustment factor applied to the base open lane " + (isFreeway ? "capacity" : "saturation flow rate") + ".";
        String ttWorkZoneLaneCapacity = "Average capacity (pc/hr/ln) of an open lane of the work zone.  The value will be multiplied by the number of lanes to get the total capacity of a cross-section of the work zone segment.";
        String ttWorkZoneSatFlowRate = "Average saturation flow rate (pc/hr/ln) of an open lane of the work zone. The value will be multiplied by the number of lanes to get the total saturation flow rate of the work zone.";
        String ttComputedLaneCapacityWZ = "Computed capacity in veh/hr/ln for a single lane of the work zone.  The value is the work zone lane capacity adjusted for heavy vehicles.";
        String ttComputedLaneSatFlowWZ = "Computed saturation flow rate in veh/hr/ln for a single lane of the work zone.  The value is the work zone lane saturation flow rate adjusted for heavy vehicles.";
        String ttWorkZoneLanesClosed = "Total number of travel lanes closed due to the active work zone. This value will be subtracted from the base number of facility lanes specified in the project's general information (and displayed in Step 2 of this V/C Wizard.";
        String ttComputedTotalWZCapacity = "Total capacity (veh/hr/ln) of a cross-section of the segment under work zone conditions.  This value is computed as the work zone open lane capacity times the number of open lanes.";
        String ttComputedTotalWZSatFlow = "Total saturation flow rate (veh/hr/ln) in the work zone. This value is computed as the work zone open lane saturation flow rate times the number of open lanes";
        String ttMaxDC = "Maximum demand-to-capacity (D/C) ratio predicted for the base conditions or work zone configuration.";
        String ttMaxVC = "Maximum volume-to-capacity (V/C) ratio predicted for the base conditions or work zone configuration.";
        String ttTimeAboveCapacity = "Total time during which the demand exceeds the capacity of the base conditions or work zone configuration.";
        String ttLongestPeakAboveCapacity = "Longest continuous block of time during which the demand exceeds the capacity of the base conditions or work zone configuration.";
        String ttLevelOfCongestion = "Expected level of congestion experienced during the 24-hour analysis period.  Based on the maximum predicted D/C ratio (Low: D/C <= 0.85; Mild: 0.85 < D/C <= 0.95; High: 0.95 < D/C <= 1.05; Very High: D/C > 1.05" ;
        String ttEstimateQueueLength = "Maximum estimated queue length predicted based on the relationship between demand and capacity of the base conditions or work zone configuration. A queue will build during all periods in which D/C exceeds 1.0, and dissipates when the D/C falls below 1.0.  The queue density is assumed to be 190 pc/mi/ln.";

        //columns, rows
//        GridPane inputGrids = new GridPane();
        GridPane demandInputGrid = new GridPane();
        int ri = 0;
        demandInputGrid.add(NodeFactory.createFormattedLabel("Bidirectional AADT (veh/day):", "label-italic", ttBidirectionalAADT, true), 0, ri);
        demandInputGrid.add(inputEchoAADT, 1, ri);
        demandInputGrid.add(NodeFactory.createFormattedLabel("AADT Directional Split:", "label-italic", ttDirectionalSplit, true), 2, ri);
        demandInputGrid.add(inputDirectionalSplit, 3, ri);
        ri++;
        demandInputGrid.add(NodeFactory.createFormattedLabel("Volume Profile:", "label-italic", ttVolumeProfile, true), 0, ri);
        demandInputGrid.add(inputAADTProfile, 1, ri);
        demandInputGrid.add(NodeFactory.createFormattedLabel("Volume Scaling:", "label-italic", ttVolumeScaling, true), 2, ri);
        demandInputGrid.add(volumeScalingWrapper, 3, ri);
        ri++;
        demandInputGrid.add(NodeFactory.createFormattedLabel("Computed Directional AADT:", "label-italic", ttComputedDirectionalAADT, true), 0, ri);
        demandInputGrid.add(computedAADTLabel, 1, ri);

        GridPane capacityInputGrid = new GridPane();
        ri = 0;
        capacityInputGrid.add(NodeFactory.createFormattedLabel("Functional Class of Roadway:", "label-italic", ttFunctionalClass, true), 0, ri);
        capacityInputGrid.add(inputEchoFunctionalClass, 1, ri);
        capacityInputGrid.add(NodeFactory.createFormattedLabel("Study Location Area Type:", "label-italic", ttAreaType, true), 2, ri);
        capacityInputGrid.add(inputEchoAreaType, 3, ri);
        ri++;
        capacityInputGrid.add(NodeFactory.createFormattedLabel("Terrain Type:", "label-italic", ttTerrainType, true), 0, ri);
        capacityInputGrid.add(inputTerrainType, 1, ri);
        capacityInputGrid.add(NodeFactory.createFormattedLabel("Percent Trucks (%):", "label-italic", ttPercentTrucks, true), 2, ri);
        capacityInputGrid.add(inputTruckPct, 3, ri);
        GridPane.setMargin(inputTruckPct, new Insets(0, 0, 10, 0));
        ri++;
        if (isFreeway) {
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Segment Type:", "label-italic", ttIntersectionType, true), 0, ri);
            capacityInputGrid.add(inputSegmentType, 1, ri);
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Base Lane Capacity (pc/ln/hr):", "label-italic", ttBaseCapacity, true), 2, ri);
            capacityInputGrid.add(inputBaseLaneCapacity, 3, ri);
            GridPane.setMargin(inputBaseLaneCapacity, new Insets(0, 0, 10, 0));
        } else {
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Base Saturation Flow Rate (pc/ln/hr):", "label-italic", ttBaseSatFlowRate, true), 0, ri);
            capacityInputGrid.add(inputBaseLaneSatFlowRate, 1, ri);
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Default Urban: 1,900; Default Rural: 1,750", "label-italic", ttSatFlowRateDefault, true), 2, ri, 2, 1);
            GridPane.setMargin(inputBaseLaneSatFlowRate, new Insets(0, 0, 10, 0));
            ri++;
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Intersection Type:", "label-italic", ttIntersectionType, true), 0, ri);
            capacityInputGrid.add(inputIntxType, 1, ri);
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Adjustment Factor", "label-italic"), 2, ri);
            capacityInputGrid.add(inputIntxTypeAdjustment, 3, ri);
        }
        ri++;
        capacityInputGrid.add(NodeFactory.createFormattedLabel("Number of Lanes:", "label-italic", ttNumberOfLanes, true), 0, ri);
        capacityInputGrid.add(inputEchoBaseNumberOfLanes, 1, ri);
        if (isFreeway) {
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Computed Total Base Capacity:", "label-italic", ttComputedBaseCapacity, true), 2, ri);
            capacityInputGrid.add(computedSegmentCapacityLabel, 3, ri);
        } else {
            capacityInputGrid.add(NodeFactory.createFormattedLabel("Adjusted Saturation Flow Rate (pc/ln/hr):", "label-italic", ttAdjustedSatFlowRate, true), 2, ri);
            capacityInputGrid.add(computedAdjustedSatFlowRateLabel, 3, ri);
        }

        GridPane gridWZconfig = new GridPane();
        ri=0;
        gridWZconfig.add(NodeFactory.createFormattedLabel("General Work Zone Type:", "label-italic", ttWorkZoneType, true), 0, ri);
        gridWZconfig.add(inputWorkZoneType, 1, ri);
        gridWZconfig.add(NodeFactory.createFormattedLabel((isFreeway ? "Capacity" : "Saturation Flow Rate") + " Adjustment Factor", "label-italic", ttWorkZoneTypeAdjustment, true), 2, ri);
        gridWZconfig.add(workZoneTypeAdjustmentLabel, 3, ri);
        ri++;
        if (isFreeway) {
            gridWZconfig.add(NodeFactory.createFormattedLabel("Work Zone Lane Capacity (pc/hr/ln):", "label-italic", ttWorkZoneLaneCapacity, true), 0, ri);
            gridWZconfig.add(inputWorkZoneLaneCapacity, 1, ri);
            gridWZconfig.add(NodeFactory.createFormattedLabel("Computed Work Zone Lane Capacity", "label-italic", ttComputedLaneCapacityWZ, true), 2, ri);
            gridWZconfig.add(workZoneComputedLaneCapacityVehLabel, 3, ri);
        } else {
            gridWZconfig.add(NodeFactory.createFormattedLabel("Work Zone Saturation Flow Rate (pc/hr/ln):", "label-italic", ttWorkZoneSatFlowRate, true), 0, ri);
            gridWZconfig.add(inputWorkZoneLaneSatFlow, 1, ri);
            gridWZconfig.add(NodeFactory.createFormattedLabel("Computed Work Zone Saturation Flow Rate", "label-italic", ttComputedLaneSatFlowWZ, true), 2, ri);
            gridWZconfig.add(workZoneComputedLaneSatFlowVehLabel, 3, ri);
        }
        ri++;
        gridWZconfig.add(NodeFactory.createFormattedLabel("# Travel Lanes Closed due to Work Zone:", "label-italic", ttWorkZoneLanesClosed, true), 0, ri);
        gridWZconfig.add(inputWorkZoneNumLanesClosed, 1, ri);
        if (isFreeway) {
            gridWZconfig.add(NodeFactory.createFormattedLabel("Computed Total WZ Capacity:", "label-italic", ttComputedTotalWZCapacity, true), 2, ri);
            gridWZconfig.add(computedTotalWorkZoneCapacityLabel, 3, ri);
        } else {
            gridWZconfig.add(NodeFactory.createFormattedLabel("Computed Total WZ Sat Flow:", "label-italic", ttComputedTotalWZSatFlow, true), 2, ri);
            gridWZconfig.add(computedTotalWorkZoneSatFlowLabel, 3, ri);
        }

        gridWZconfig.setHgap(20);
        gridWZconfig.setVgap(35);
        gridWZconfig.setPadding(new Insets(15, 0, 10, 0));

        ri = 0;
        outputPane.add(NodeFactory.createFormattedLabel("Base", "label-italic"), 1, ri);
        outputPane.add(NodeFactory.createFormattedLabel("WZ", "label-italic"), 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Max D/C ratio:", "label-italic", ttMaxDC, true), 0, ri);
        outputPane.add(lblMaxDCRatioBaseComputed, 1, ri);
        outputPane.add(lblMaxDCRatioWorkZoneComputed, 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Max V/C ratio:", "label-italic", ttMaxVC, true), 0, ri);
        outputPane.add(lblMaxVCRatioBaseComputed, 1, ri);
        outputPane.add(lblMaxVCRatioWorkZoneComputed, 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Total Time Above Capacity:", "label-italic", ttTimeAboveCapacity, true), 0, ri);
        outputPane.add(lblHrsAboveBaseCapOutputComputed, 1, ri);
        outputPane.add(lblHrsAboveWZCapOutputComputed, 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Longest Peak Above Capacity:", "label-italic", ttLongestPeakAboveCapacity, true), 0, ri);
        outputPane.add(lblStreakAboveBaseCapOutputComputed, 1, ri);
        outputPane.add(lblStreakAboveWZCapOutputComputed, 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Level of Congestion:", "label-italic", ttLevelOfCongestion, true), 0, ri);
        outputPane.add(lblLvlofCongestBaseOutputComputed, 1, ri);
        outputPane.add(lblLvlofCongestWZOutputComputed, 2, ri);
        ri++;
        outputPane.add(NodeFactory.createFormattedLabel("Max Estimated Queue Length:", "label-italic", ttEstimateQueueLength, true), 0, ri);
        outputPane.add(lblEstQueLengthBaseOutputComputed, 1, ri);
        outputPane.add(lblEstQueLengthWZOutputComputed, 2, ri);

        outputPane.setHgap(20);
        outputPane.setVgap(30);
        outputPane.setPadding(new Insets(15, 0, 10, 0));
        outputPane.setPrefWidth(650);
        outputPane.setMinWidth(500);

        // Creating general inputs tab
        Tab tabGeneralDemand = new Tab("1) General Facility Demand");
        BorderPane demandInputsWrapper = new BorderPane();
        demandInputsWrapper.setCenter(demandInputGrid);
        BorderPane.setMargin(demandInputsWrapper, new Insets(10, 0, 10, 0));
        tabGeneralDemand.setContent(demandInputsWrapper);
        // Creating General Capacity Inputs tab
        Tab tabGeneralCapacity = new Tab("2) General Facility Capacity");
        BorderPane capacityInputsWrapper = new BorderPane();
        capacityInputsWrapper.setCenter(capacityInputGrid);
        BorderPane.setMargin(capacityInputsWrapper, new Insets(10, 0, 10, 0));
        tabGeneralCapacity.setContent(capacityInputsWrapper);
        // Creating work zone inputs tab
        Tab tabWZconfig = new Tab("3) Work Zone Configuration");
        BorderPane wzInputsWrapper = new BorderPane();
        wzInputsWrapper.setCenter(gridWZconfig);
        BorderPane.setMargin(capacityInputsWrapper, new Insets(10, 0, 10, 0));
        tabWZconfig.setContent(wzInputsWrapper);
        // Creating tab pane and adding tabs
        TabPane vcInputTabPane = new TabPane();
        vcInputTabPane.getTabs().addAll(tabGeneralDemand, tabGeneralCapacity, tabWZconfig);
        vcInputTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        vcInputTabPane.getStyleClass().add("custom-subtitle-tab-pane");
        BorderPane tabNavigationButtonBar = new BorderPane();
        JFXButton buttonTabPrevious = new JFXButton();
        buttonTabPrevious.getStyleClass().add("text-button-default");
        buttonTabPrevious.setGraphic(NodeFactory.createIcon(FontAwesomeSolid.ARROW_LEFT, Color.web(ColorHelper.WZ_ORANGE), 20));
        buttonTabPrevious.disableProperty().bind(vcInputTabPane.getSelectionModel().selectedIndexProperty().isEqualTo(0));
        buttonTabPrevious.setOnAction(actionEvent -> {
            vcInputTabPane.getSelectionModel().selectPrevious();
        });
        JFXButton buttonTabNext = new JFXButton();
        buttonTabNext.getStyleClass().add("text-button-default");
        buttonTabNext.setGraphic(NodeFactory.createIcon(FontAwesomeSolid.ARROW_RIGHT, Color.web(ColorHelper.WZ_ORANGE), 20));
        buttonTabNext.disableProperty().bind(vcInputTabPane.getSelectionModel().selectedIndexProperty().lessThan(vcInputTabPane.getTabs().size() - 1).not());
        buttonTabNext.setOnAction(actionEvent -> {
            vcInputTabPane.getSelectionModel().selectNext();
        });
        tabNavigationButtonBar.setLeft(buttonTabPrevious);
        tabNavigationButtonBar.setRight(buttonTabNext);

        BorderPane vcInputPanes = new BorderPane();
        vcInputPanes.setCenter(vcInputTabPane);
        vcInputPanes.setBottom(tabNavigationButtonBar);



        JFXButton backButton = new JFXButton("Back to Project General Information");
        backButton.getStyleClass().add("contained-button-default");
        backButton.setGraphic(NodeFactory.createIcon(FontAwesomeRegular.CARET_SQUARE_LEFT, Color.WHITE)); //Color.web(ColorHelper.WZ_ORANGE)
        backButton.setOnAction(actionEvent -> {
            mc.setActiveSubStep(0, 0);
            ((BorderPane) getParent()).setCenter(replacedStepNode);
        });

        //creates a line chart
        final Node demandToCapacityChart = createDemandToCapacityChart();
        final Node queueLengthChart = createQueueLengthChart();
        final Node dcRatioChart = createDCRatioBarChart();
        final BorderPane chartPane = new BorderPane();
        chartPane.setCenter(demandToCapacityChart);

        final JFXRadioButton vcChartRadioButton = new JFXRadioButton("Demand Vs Capacity");
        vcChartRadioButton.setSelected(true);
        vcChartRadioButton.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            chartPane.setCenter(demandToCapacityChart);
        });
        final JFXRadioButton qLenChartRatioButton = new JFXRadioButton("Estimated Queue Length");
        qLenChartRatioButton.selectedProperty().addListener((observableValue, oldVal, newVal)-> {
            chartPane.setCenter(queueLengthChart);
        });
        final JFXRadioButton dcRatioChartRatioButton = new JFXRadioButton("D/C Ratio");
        dcRatioChartRatioButton.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            chartPane.setCenter(dcRatioChart);
        });
        ToggleGroup chartTypeGroup = new ToggleGroup();
        chartTypeGroup.getToggles().addAll(vcChartRadioButton, qLenChartRatioButton, dcRatioChartRatioButton);


        HBox chartButtonPanel = new HBox(10);
        chartButtonPanel.getChildren().addAll(
                NodeFactory.createFormattedLabel("Select Chart Type:", "label-italic"),
                vcChartRadioButton, qLenChartRatioButton, dcRatioChartRatioButton);
        chartPane.setTop(chartButtonPanel);
        BorderPane.setMargin(chartButtonPanel, new Insets(10, 10, 10, 10));


        BorderPane titlePane = new BorderPane();
        titlePane.setCenter(NodeFactory.createFormattedLabel("Volume-to-Capacity Wizard", "substep-title-label"));
        BorderPane topPane = new BorderPane();
        topPane.setTop(titlePane);
        topPane.setCenter(vcInputPanes);
        topPane.setBottom(linePane);
        BorderPane.setMargin(vcInputPanes, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(demandInputGrid, new Insets(10, 0, 10, 0));
        BorderPane.setMargin(capacityInputGrid, new Insets(10, 0, 10, 0));;
        BorderPane.setMargin(gridWZconfig, new Insets(10, 0, 10, 0));
        demandInputGrid.setAlignment(Pos.TOP_CENTER);
        capacityInputGrid.setAlignment(Pos.TOP_CENTER);
        gridWZconfig.setAlignment(Pos.TOP_CENTER);
        demandInputGrid.setVgap(15);
        demandInputGrid.setHgap(20);
        capacityInputGrid.setVgap(15);
        capacityInputGrid.setHgap(20);
        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.CENTER);
        backButtonBox.getChildren().addAll(backButton);
        BorderPane.setMargin(backButtonBox, new Insets(10, 0, 10, 0));
        this.setTop(topPane);
        this.setCenter(chartPane);
        this.setRight(outputPane);
        this.setBottom(backButtonBox);
//        topPane.setStyle("-fx-background-color: white");
//        graphPane.setStyle("-fx-background-color: white");
//        outputPane.setStyle("-fx-background-color: white");


        setupComboBoxInputs();
//        formatLabels();
        setupTooltips();
        configureInitialDefaults();
        setupInputChangeListeners();
//        formnatTextfields(inputBaseLaneCapacity);
//        minMaxTextField(txtWorkzoneCap);
        inputParametersUpdated();
    }

    private String convertApToTime(int period) {
        int hour = Math.floorDiv(period, 4);
        int minute = (period % 4) * 15;
        String amPMStr = "am";
        if (hour == 0) {
            hour = 12;
        } else if (hour == 12) {
            amPMStr = "pm";
        } else if (hour > 12) {
            hour -= 12;
            amPMStr = "pm";
        }
        return hour + ":" + (minute == 0 ? "00" : minute) + amPMStr;
    }

    private void configureVCChartTooltips() {
        Tooltip vcChartTooltip = new Tooltip();
        vcChartTooltip.setStyle("-fx-font-size: 10pt");
        vcChartTooltip.setShowDelay(Duration.ZERO);
        final DataCursorLineChart<Number, Number> chart = (DataCursorLineChart<Number, Number>) demandSeries.getChart();
        Tooltip.install(chart, vcChartTooltip);
        chart.setOnMouseMoved(mouseEvent -> {
            if (vcChartTooltip.isShowing()) {
                Point2D screen = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
                double localX = chart.getXAxis().screenToLocal(screen).getX();
                double localY = chart.getYAxis().screenToLocal(screen).getY();
                float xValue = Float.parseFloat(chart.getXAxis().getValueForDisplay(localX).toString());
                int ttPer = Math.min(Math.max(Math.round(xValue), 0), 95);
                String tooltipStr = "Time: " + convertApToTime(ttPer) + "\n";
                tooltipStr = tooltipStr + "Demand: " + demandSeries.getData().get(ttPer).getYValue().intValue() + " veh/hr\n";
                tooltipStr = tooltipStr + "Base Capacity: " + baseCapacitySeries.getData().get(ttPer).getYValue().intValue() + " veh/hr\n";
                tooltipStr = tooltipStr + "WZ Capacity: " + workZoneCapacitySeries.getData().get(ttPer).getYValue().intValue() + " veh/hr\n";
                tooltipStr = tooltipStr + "------------------------\n";
                tooltipStr = tooltipStr + "Base D/C: " + String.format("%.2f", demandSeries.getData().get(ttPer).getYValue().intValue() / (float) baseCapacitySeries.getData().get(ttPer).getYValue().intValue()) + "\n";
                tooltipStr = tooltipStr + "WZ D/C: " + String.format("%.2f", demandSeries.getData().get(ttPer).getYValue().intValue() / (float) workZoneCapacitySeries.getData().get(ttPer).getYValue().intValue()) + "\n";
                vcChartTooltip.textProperty().set(tooltipStr);
                vcChartTooltip.setX(mouseEvent.getScreenX());
                vcChartTooltip.setY(mouseEvent.getScreenY());
                chart.setDataCursorX(ttPer);
            }
        });
    }

    private void configureQueueLengthChartTooltips() {
        Tooltip chartTooltip = new Tooltip();
        chartTooltip.setStyle("-fx-font-size: 10pt");
        chartTooltip.setShowDelay(Duration.ZERO);
        final DataCursorAreaChart<Number, Number> chart = (DataCursorAreaChart<Number, Number>) qLengthBaseSeries.getChart();
        Tooltip.install(chart, chartTooltip);
        chart.setOnMouseMoved(mouseEvent -> {
            if (chartTooltip.isShowing()) {
                Point2D screen = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
                double localX = chart.getXAxis().screenToLocal(screen).getX();
                double localY = chart.getYAxis().screenToLocal(screen).getY();
                float xValue = Float.parseFloat(chart.getXAxis().getValueForDisplay(localX).toString());
                int ttPer = Math.min(Math.max(Math.round(xValue), 0), 95);
                String tooltipStr = "Time: " + convertApToTime(ttPer) + "\n";
                tooltipStr = tooltipStr + "------Queue Length------\n";
                tooltipStr = tooltipStr + "Base Conditions: " + String.format("%.2f", qLengthBaseSeries.getData().get(ttPer).getYValue().floatValue()) + " miles\n";
                tooltipStr = tooltipStr + "Work Zone: " + String.format("%.2f", qLengthWorkZoneSeries.getData().get(ttPer).getYValue().floatValue()) + " miles\n";
                chartTooltip.textProperty().set(tooltipStr);
                chartTooltip.setX(mouseEvent.getScreenX());
                chartTooltip.setY(mouseEvent.getScreenY());
                chart.setDataCursorX(ttPer);
            }
        });
    }

    private void configureDCRatioChartTooltips() {
        Tooltip dcChartTooltip = new Tooltip();
        dcChartTooltip.setStyle("-fx-font-size: 10pt");
        dcChartTooltip.setShowDelay(Duration.ZERO);
        final DataCursorLineChart<Number, Number> chart = (DataCursorLineChart<Number, Number>) dcRatioBaseSeries.getChart();
        Tooltip.install(chart, dcChartTooltip);
        chart.setOnMouseMoved(mouseEvent -> {
            if (dcChartTooltip.isShowing()) {
                Point2D screen = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
                double localX = chart.getXAxis().screenToLocal(screen).getX();
                double localY = chart.getYAxis().screenToLocal(screen).getY();
                float xValue = Float.parseFloat(chart.getXAxis().getValueForDisplay(localX).toString());
                int ttPer = Math.min(Math.max(Math.round(xValue), 0), 95);
                String tooltipStr = "Time: " + convertApToTime(ttPer) + "\n";
                tooltipStr = tooltipStr + "------------------------\n";
                tooltipStr = tooltipStr + "Base D/C: " + String.format("%.2f", dcRatioBaseSeries.getData().get(ttPer).getYValue().floatValue()) + "\n";
                tooltipStr = tooltipStr + "WZ D/C: " + String.format("%.2f", dcRatioWorkZoneSeries.getData().get(ttPer).getYValue().floatValue()) + "\n";
                dcChartTooltip.textProperty().set(tooltipStr);
                dcChartTooltip.setX(mouseEvent.getScreenX());
                dcChartTooltip.setY(mouseEvent.getScreenY());
                chart.setDataCursorX(ttPer);
            }
        });
    }

    private void setupInputChangeListeners() {
        inputDirectionalSplitValue.setMaxWidth(Integer.MAX_VALUE);
        inputDirectionalSplitValue.setMaxHeight(Integer.MAX_VALUE);
        inputDirectionalSplitValue.setAlignment(Pos.CENTER);
        GridPane.setHgrow(inputDirectionalSplitValue, Priority.ALWAYS);
//        inputDirectionalSplitSlider.valueProperty().addListener((observableValue, oldVal, newVal) -> {
//            if (!oldVal.equals(newVal) && !inputDirectionalSplitSlider.isValueChanging()) {
//                inputDirectionalSplitValue.setText("(" + String.format("%.2f", newVal.floatValue()) + ")");
//            }
//        });
        inputDirectionalSplitSlider.valueChangingProperty().addListener((observableValue, oldUpdating, newUpdating) -> {
            double value = inputDirectionalSplitSlider.getValue();
            boolean notUpdatingAnymore = oldUpdating && !newUpdating;
            boolean isOnExtreme = value == inputDirectionalSplitSlider.getMin() || value == inputDirectionalSplitSlider.getMax();
            if (notUpdatingAnymore) {
                // Fix for label not adjusting at extremes
//                if (isOnExtreme) {
                    inputDirectionalSplitValue.setText("(" + String.format("%.2f", value) + ")");
//                }
                inputParametersUpdated();
            }
        });
        inputAADTProfile.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal.value == AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKDAY || newVal.value == AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKEND) {
                volumeScalingWrapper.setCenter(inputAADTNationalProfileSubtype);
            } else {
                volumeScalingWrapper.setCenter(inputAADTProfileSubType);
            }
            inputParametersUpdated();
        });

        inputAADTProfileSubType.disableProperty().bind(inputAADTProfile.valueProperty().isNull());
        inputAADTNationalProfileSubtype.disableProperty().bind(inputAADTProfile.valueProperty().isNull());
        inputAADTProfileSubType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputAADTNationalProfileSubtype.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputTerrainType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputTruckPct.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputBaseLaneCapacity.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputBaseLaneSatFlowRate.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputSegmentType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputIntxType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputIntxTypeAdjustment.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputWorkZoneType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputWorkZoneLaneCapacity.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputWorkZoneLaneSatFlow.textProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
        inputWorkZoneNumLanesClosed.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputParametersUpdated();
        });
    }

    private void configureInitialDefaults() {
        // 1) Existing/Base Demand Conditions
        // ---- Get all existing inputs from the Project object
        inputEchoAADT.textProperty().bind(Bindings.format("%,d", control.getProject().aadtProperty()));
        // ---- Set other default values
        inputTruckPct.setText("5");
        inputDirectionalSplitSlider.setValue(0.5);
        inputDirectionalSplitValue.setText("(0.50)");
        inputAADTProfile.getSelectionModel().select(1);
        inputAADTProfileSubType.getSelectionModel().selectFirst();
        inputAADTNationalProfileSubtype.getSelectionModel().selectFirst();
        volumeScalingWrapper.setCenter(inputAADTProfileSubType);

        // 2) Capacity for Existing/Base Conditions
        // ---- Get all existing inputs from the Project object
        inputEchoFunctionalClass.textProperty().bind(control.getProject().functionalClassProperty());
        inputEchoAreaType.textProperty().bind(control.getProject().areaTypeProperty());
        inputEchoBaseNumberOfLanes.textProperty().bind(control.getProject().numRoadwayLanesProperty().asString());
        // ---- Set any Default Values
        inputTerrainType.getSelectionModel().selectFirst();
        inputSegmentType.getSelectionModel().selectFirst();
        inputSegmentType.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            inputBaseLaneCapacity.setText(String.format("%,d", newVal.baseCapacity));
        });
        inputBaseLaneCapacity.setText(String.format("%,d", inputSegmentType.getValue().baseCapacity)); // HCM Default Capacity
        inputBaseLaneCapacity.disableProperty().bind(
                inputSegmentType.valueProperty().asString().isNotEqualTo("Custom")
        );
        inputBaseLaneSatFlowRate.setText(String.format("%,d", inputEchoAreaType.getText().equalsIgnoreCase("Urban") ? 1900 : 1750));
        inputIntxType.getSelectionModel().selectFirst();
        inputIntxTypeAdjustment.setText(String.format("%.2f", inputIntxType.getValue().value));
        inputIntxType.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
            inputIntxTypeAdjustment.setText(String.format("%.2f", newVal.value));
        }));
        inputIntxTypeAdjustment.disableProperty().bind(inputIntxType.valueProperty().asString().isNotEqualTo("Custom"));

        // ---- Apply Input validators
        NodeFactory.setTextFieldDecimalInputOnly(inputTruckPct);
        NodeFactory.assignDefaultRequiredFieldValidator(inputTruckPct, "Required Input", true);
        NodeFactory.assignDefaultRequiredFieldValidator(inputBaseLaneCapacity, "Required Input", true);
        NodeFactory.setTextFieldIntegerInputOnly(inputBaseLaneCapacity);
        NodeFactory.assignDefaultRequiredFieldValidator(inputBaseLaneSatFlowRate, "Required Input", true);
        NodeFactory.setTextFieldIntegerInputOnly(inputBaseLaneSatFlowRate);
        NodeFactory.setTextFieldDecimalInputOnly(inputIntxTypeAdjustment);
        NodeFactory.assignDefaultRequiredFieldValidator(inputIntxTypeAdjustment, "Required Input", true);

        // 3) Work Zone Configuration
        // ---- Set any Default Values
        inputWorkZoneType.getSelectionModel().selectFirst();
        workZoneTypeAdjustmentLabel.setText(String.format("%.2f", inputWorkZoneType.getValue().capacityAdjustment));
        inputWorkZoneType.valueProperty().addListener(((observableValue, oldVal, newVal) -> {
            if (newVal.isCustom()) {
                workZoneTypeAdjustmentLabel.setText("--");
            } else {
                workZoneTypeAdjustmentLabel.setText(String.format("%.2f", newVal.capacityAdjustment));
            }
            if (!newVal.allowsLaneClosure()) {
                inputWorkZoneNumLanesClosed.getSelectionModel().selectFirst();
            } else {
                if (inputWorkZoneNumLanesClosed.getValue().value == 0) {
                    inputWorkZoneNumLanesClosed.getSelectionModel().select(1);
                }
            }

        }));
        inputWorkZoneLaneCapacity.disableProperty().bind(inputWorkZoneType.valueProperty().asString().isNotEqualTo("Custom"));
        inputWorkZoneLaneSatFlow.disableProperty().bind(inputWorkZoneType.valueProperty().asString().isNotEqualTo("Custom"));
        inputWorkZoneNumLanesClosed.getSelectionModel().selectFirst();
        inputWorkZoneNumLanesClosed.disableProperty().bind(
                Bindings.or(
                        inputWorkZoneType.valueProperty().asString().isEqualTo("Shoulder Closure"),
                        inputWorkZoneType.valueProperty().asString().isEqualTo("None")
                        ));

        // ---- Apply Input Validators
        NodeFactory.setTextFieldIntegerInputOnly(inputWorkZoneLaneCapacity);
        NodeFactory.assignDefaultRequiredFieldValidator(inputWorkZoneLaneCapacity, "Required Input", true);
        NodeFactory.setTextFieldIntegerInputOnly(inputWorkZoneLaneSatFlow);
        NodeFactory.assignDefaultRequiredFieldValidator(inputWorkZoneLaneSatFlow, "Required Input", true);

    }

    private void setupComboBoxInputs() {
        inputAADTProfileSubType.getItems().addAll(
                new ProfileSubTypeItem("Average", AADTDistributionHelper.TYPE_DEFAULT_SUB_AVG),
                new ProfileSubTypeItem("Minimum", AADTDistributionHelper.TYPE_DEFAULT_SUB_MIN),
                new ProfileSubTypeItem("Below Average", AADTDistributionHelper.TYPE_DEFAULT_SUB_25),
                new ProfileSubTypeItem("Moderate", AADTDistributionHelper.TYPE_DEFAULT_SUB_MEDIAN),
                new ProfileSubTypeItem("High", AADTDistributionHelper.TYPE_DEFAULT_SUB_75),
                new ProfileSubTypeItem("Very High", AADTDistributionHelper.TYPE_DEFAULT_SUB_MAX)
        );
        inputAADTNationalProfileSubtype.getItems().addAll(
                new ProfileSubTypeItem("Urban", 0),
                new ProfileSubTypeItem("Rural", 1)
        );

        inputTerrainType.getItems().addAll(
                new TerrainTypeItem("Level", TERRAIN_TYPE_LEVEL),
                new TerrainTypeItem("Rolling", TERRAIN_TYPE_ROLLING),
                new TerrainTypeItem("Mountainous", TERRAIN_TYPE_MOUNTAIN)
        );
        inputSegmentType.getItems().addAll(
                new SegmentTypeItem("Basic Freeway", 2400, false),
                new SegmentTypeItem("Merge/On-Ramp", 2100, false),
                new SegmentTypeItem("Diverge/Off-Ramp", 2100, false),
                new SegmentTypeItem("Weaving Segment", 2000, false),
                new SegmentTypeItem("Custom", 2400, true)
        );
        inputIntxType.getItems().addAll(
                new IntxTypeInput("None", 1.0f),
                new IntxTypeInput("Roundabout", .50f),
                new IntxTypeInput("Two-Way Stop (Mainline)", 1.0f),
                new IntxTypeInput("All Way Stop", 0.30f),
                new IntxTypeInput("Two-Phase Signal", 0.60f),
                new IntxTypeInput("Three Phase Signal", 0.45f),
                new IntxTypeInput("Four Phase Signal", 0.35f),
                new IntxTypeInput("Custom", 1.0f)
        );
        inputWorkZoneType.getItems().addAll(
                new WorkZoneTypeItem("None", 1.0f, WorkZoneType.NONE),
                new WorkZoneTypeItem("Shoulder Closure", 0.95f, WorkZoneType.SHOULDER),
                new WorkZoneTypeItem("Lane Closure", 0.85f, WorkZoneType.LANE_CLOSURE),
                new WorkZoneTypeItem("Crossover", 0.9f, WorkZoneType.CROSSOVER),
                new WorkZoneTypeItem("Custom", 1.0f, WorkZoneType.CUSTOM)
                );
        for (int numLanes = 0; numLanes < this.control.getProject().getNumRoadwayLanes(); numLanes++) {
            inputWorkZoneNumLanesClosed.getItems().add(
                    new WZnumLanesClosedInput(numLanes + " Lane" + (numLanes != 1 ? "s" : ""), numLanes)
            );
        }
        inputAADTProfile.getItems().addAll(
                new VolumeProfileItem("Rural/Weekend", AADTDistributionHelper.TYPE_DEFAULT_UNIMODAL),
                new VolumeProfileItem("AM Peak", AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_AM),
                new VolumeProfileItem("PM Peak", AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_PM),
                new VolumeProfileItem("National Weekday", AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKDAY),
                new VolumeProfileItem("National Weekend", AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKEND)
        );
    }

    private void setupTooltips() {

        final Tooltip computedAADTTooltip = new Tooltip();
        computedAADTTooltip.setShowDuration(Duration.INDEFINITE);
        computedAADTTooltip.setOnShown(windowEvent -> {
            int aadt = 0;
            try {
                aadt = Integer.parseInt(inputEchoAADT.getText().replace(",", ""));
            } catch (NumberFormatException e) {
                // do nothing
            }

            double dirSplit = inputDirectionalSplitSlider.getValue();

            String tooltipStr = String.format("%,d", Math.round(aadt)) + " * " + String.format("%.2f", dirSplit);
            tooltipStr += " = " + String.format("%,d", Math.round(aadt * dirSplit)) + " veh/day";
            computedAADTTooltip.setText(tooltipStr);
        });
        computedAADTTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedAADTLabel, computedAADTTooltip);

        final Tooltip computedCapacityTooltip = new Tooltip();
        computedCapacityTooltip.setOnShown(windowEvent -> {
            float truckPCEquiv = inputTerrainType.getValue().value; // Allowable as it is set to a value by default
            float truckPCT = 0.0f;
            try {
                truckPCT = Float.parseFloat(inputTruckPct.getText());
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0.0f
            }
            float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
            String tooltipStr;
            tooltipStr = "Total Capacity (veh/hr) = Lane Capacity (pc/hr/ln) * Heavy Vehicle Adjustment * # Lanes";
            tooltipStr += "\n\n";
            tooltipStr += computedSegmentCapacityLabel.getText();
            tooltipStr += " = ";
            tooltipStr += inputBaseLaneCapacity.getText();
            tooltipStr += " * ";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += " * ";
            tooltipStr += inputEchoBaseNumberOfLanes.getText();
            tooltipStr += "\n\n";
            tooltipStr += "Heavy Vehicle Adjustment = (1 / (1 + (Truck % * (Truck-PCE - 1)))";
            tooltipStr += "\n";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += " = (1 / (1 + (" + String.format("%.2f",truckPCT / 100.0f) + " * (" + String.format("%.1f", truckPCEquiv) + " - 1)))";
            computedCapacityTooltip.setText(tooltipStr);
        });
        computedCapacityTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedSegmentCapacityLabel, computedCapacityTooltip);

        final Tooltip computedAdjustedSatFlowRateTooltip = new Tooltip();
        computedAdjustedSatFlowRateTooltip.setOnShown(windowEvent -> {
            float truckPCEquiv = inputTerrainType.getValue().value; // Allowable as it is set to a value by default
            float truckPCT = 0.0f;
            try {
                truckPCT = Float.parseFloat(inputTruckPct.getText());
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0.0f
            }
            float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
            String tooltipStr;
            float intxAdjFactor = 1.0f;
            try {
                intxAdjFactor = Float.parseFloat(inputIntxTypeAdjustment.getText());
            } catch (NumberFormatException e) {
                // Do nothing, defaults to 1.0
            }
            tooltipStr = "Adj. Sat Flow Rate (veh/hr) = Base Lane Sat Flow Rate (pc/hr/ln) * Intx Adj. Factor * Heavy Vehicle Adjustment * # Lanes";
            tooltipStr += "\n\n";
            tooltipStr += computedAdjustedSatFlowRateLabel.getText();
            tooltipStr += " = ";
            tooltipStr += inputBaseLaneSatFlowRate.getText();
            tooltipStr += " * ";
            tooltipStr += String.format("%.2f", intxAdjFactor);
            tooltipStr += " * ";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += " * ";
            tooltipStr += inputEchoBaseNumberOfLanes.getText();
            tooltipStr += "\n\n";
            tooltipStr += "Heavy Vehicle Adjustment = (1 / (1 + (Truck % * (Truck-PCE - 1)))";
            tooltipStr += "\n";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += " = (1 / (1 + (" + String.format("%.2f",truckPCT / 1.00f) + " * (" + String.format("%.1f", truckPCEquiv) + " - 1)))";
            computedAdjustedSatFlowRateTooltip.setText(tooltipStr);
        });
        computedAdjustedSatFlowRateTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedAdjustedSatFlowRateLabel, computedAdjustedSatFlowRateTooltip);

        final Tooltip workZoneTypeAdjustmentTooltip = new Tooltip();
        workZoneTypeAdjustmentTooltip.setOnShown(windowEvent -> {
            String tooltipString = "Adjustment Factor Defaults:\n\n";
            tooltipString += "None:\t\t\t\t1.0\n";
            tooltipString += "Shoulder Closure:\t1.0\n";
            tooltipString += "Lane Closure:\t\t1.0\n";
            tooltipString += "Crossover:\t\t\t1.0\n";
            tooltipString += "Custom:\t\t\t\t--*\n";
            tooltipString += "*For a custom work zone, the user specifies the\nwork zone lane " + (isFreeway ? "capacity" : "saturation flow reate") + " directly.";
            workZoneTypeAdjustmentTooltip.setText(tooltipString);
        });
        workZoneTypeAdjustmentTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(workZoneTypeAdjustmentLabel, workZoneTypeAdjustmentTooltip);

        final Tooltip computedWorkZoneLaneCapacityTooltip = new Tooltip();
        computedWorkZoneLaneCapacityTooltip.setOnShown(windowEvent -> {
            float truckPCEquiv = inputTerrainType.getValue().value; // Allowable as it is set to a value by default
            float truckPCT = 0.0f;
            try {
                truckPCT = Float.parseFloat(inputTruckPct.getText());
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0.0f
            }
            float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
            String tooltipStr = "WZ Lane Capacity (veh/hr/ln) = Work Zone Lane Capacity (pc/hr/ln) * Heavy Vehicle Adjustment";
            tooltipStr += "\n\n";
            tooltipStr += workZoneComputedLaneCapacityVehLabel.getText();
            tooltipStr +=  " = ";
            tooltipStr += inputWorkZoneLaneCapacity.getText();
            tooltipStr +=  " * ";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += "\n\n";
            tooltipStr += "*See the Computed Total Base Capacity Value (Step 2) for the\nheavy vehicle adjustment computation.";
            computedWorkZoneLaneCapacityTooltip.setText(tooltipStr);
        });
        computedWorkZoneLaneCapacityTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(workZoneComputedLaneCapacityVehLabel, computedWorkZoneLaneCapacityTooltip);

        final Tooltip computedWorkZoneLaneSatFlowTooltip = new Tooltip();
        computedWorkZoneLaneSatFlowTooltip.setOnShown(windowEvent -> {
            float truckPCEquiv = inputTerrainType.getValue().value; // Allowable as it is set to a value by default
            float truckPCT = 0.0f;
            try {
                truckPCT = Float.parseFloat(inputTruckPct.getText());
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0.0f
            }
            float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
            String tooltipStr = "WZ Lane Sat Flow Rate (veh/hr/ln) = Work Zone Lane Sat Flow (pc/hr/ln) * Heavy Vehicle Adjustment";
            tooltipStr += "\n\n";
            tooltipStr += workZoneComputedLaneSatFlowVehLabel.getText();
            tooltipStr +=  " = ";
            tooltipStr += inputWorkZoneLaneSatFlow.getText();
            tooltipStr +=  " * ";
            tooltipStr += String.format("%.2f", fhv);
            tooltipStr += "\n\n";
            tooltipStr += "*See the Computed Adjusted Base Sat Flow Rate (Step 2) for the\nheavy vehicle adjustment computation.";
            computedWorkZoneLaneSatFlowTooltip.setText(tooltipStr);
        });
        computedWorkZoneLaneSatFlowTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(workZoneComputedLaneSatFlowVehLabel, computedWorkZoneLaneSatFlowTooltip);

        final Tooltip computedTotalWorkZoneCapacityTooltip = new Tooltip();
        computedTotalWorkZoneCapacityTooltip.setOnShown(windowEvent -> {
            String tooltipStr = "Total WZ Capacity (veh/hr) = Work Zone Lane Capacity (veh/hr/ln) * # Open Lanes";
            tooltipStr += "\n\n";
            tooltipStr += computedTotalWorkZoneCapacityLabel.getText();
            tooltipStr +=  " = ";
            tooltipStr += workZoneComputedLaneCapacityVehLabel.getText();
            tooltipStr +=  " * ";
            tooltipStr += (Integer.parseInt(inputEchoBaseNumberOfLanes.getText()) - inputWorkZoneNumLanesClosed.getValue().value) + " lanes";
            computedTotalWorkZoneCapacityTooltip.setText(tooltipStr);
        });
        computedTotalWorkZoneCapacityTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedTotalWorkZoneCapacityLabel, computedTotalWorkZoneCapacityTooltip);

        final Tooltip computedTotalWorkZoneSatFlowTooltip = new Tooltip();
        computedTotalWorkZoneSatFlowTooltip.setOnShown(windowEvent -> {
            String tooltipStr = "Total WZ Sat Flow Rate (veh/hr) = Work Zone Lane Sat Flow Rate (veh/hr/ln) * # Open Lanes";
            tooltipStr += "\n\n";
            tooltipStr += computedTotalWorkZoneSatFlowLabel.getText();
            tooltipStr +=  " = ";
            tooltipStr += workZoneComputedLaneSatFlowVehLabel.getText();
            tooltipStr +=  " * ";
            tooltipStr += (Integer.parseInt(inputEchoBaseNumberOfLanes.getText()) - inputWorkZoneNumLanesClosed.getValue().value) + " lanes";
            computedTotalWorkZoneSatFlowTooltip.setText(tooltipStr);
        });
        computedTotalWorkZoneSatFlowTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(computedTotalWorkZoneSatFlowLabel, computedTotalWorkZoneSatFlowTooltip);
    }

    private void inputParametersUpdated() {

        //------------Step 1 - General Facility Demand------------
        // -- 1A) Initialize all variables (so as to not break computations)
        int bidirectionalAADT = 0;
        double dirSplit = inputDirectionalSplitSlider.getValue(); // Always a value by the nature of the spinner
        int profileType = -1;
        int profileSubType = -1;
        // -- 1B) Parse Input Values from UI
        try {
            bidirectionalAADT = Integer.parseInt(inputEchoAADT.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            // do nothing
        }
        if (inputAADTProfile.getValue() != null) {
            profileType = inputAADTProfile.getValue().value;
        }
        if (profileType == AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKDAY || profileType == AADTDistributionHelper.TYPE_DEFAULT_NATIONAL_WEEKEND) {
            if (inputAADTNationalProfileSubtype.getValue() != null) {
                profileSubType = inputAADTNationalProfileSubtype.getValue().value;
            }
        } else {
            if (inputAADTProfileSubType.getValue() != null) {
                profileSubType = inputAADTProfileSubType.getValue().value;
            }
        }
        // -- 1C) Compute base facility AADT
        computedAADTLabel.setText(String.format("%,d", Math.round(bidirectionalAADT * dirSplit)) + " veh/day");

        //------------Step 2 - General Facility Capacity------------
        // -- 2A) Initialize all variables (so as to not break computations)
        float truckPCEquiv = this.inputTerrainType.getValue().value; // Allowable as it is set to a value by default
        float truckPCT = 0.0f;
        int capacityPerLaneBaseSegmentPC = 0; // Freeway and Interstate only
        int satFlowPerLaneRateBasePC = 0; // Non-freeway/interstate only
        float intxAdjustmentFactor = 1.0f; // Non-freeway/interstate only
        int numLanesInput = 0;
        // -- 2B) Parse Input Values from UI
        try {
            truckPCT = Float.parseFloat(this.inputTruckPct.getText());
        } catch (NumberFormatException e) {
            // Do nothing, value defaults to 0.0f
        }
        try {
            capacityPerLaneBaseSegmentPC = Integer.parseInt(inputBaseLaneCapacity.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            // Do nothing, value defaults to 0
        }
        try {
            satFlowPerLaneRateBasePC = Integer.parseInt(inputBaseLaneSatFlowRate.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            // Do nothing, value defaults to 0
        }
        try {
            intxAdjustmentFactor = Float.parseFloat(inputIntxTypeAdjustment.getText());
        } catch (NumberFormatException e) {
            // Do nothing, value defaults to 1.0f
        }
        try {
            numLanesInput = Integer.parseInt(inputEchoBaseNumberOfLanes.getText());
        } catch(NumberFormatException nfe) {
            // Do nothing, value defaults to 0
        }
        // -- 2C) Compute base facility capacity or saturation flow rate
        // ---- Freeway/Interstate only: Compute base capacity

        float fhv = (float) (1.0 / (1.0 + (truckPCT) * ((truckPCEquiv) - 1.0) / 100.0f));
//        if (isFreeway) {
            int basePerLaneCapacityVeh = Math.round(pc_to_veh(capacityPerLaneBaseSegmentPC, fhv));
            int totalSegmentCapacityVeh = basePerLaneCapacityVeh * numLanesInput;
            computedSegmentCapacityLabel.setText(String.format("%,d", totalSegmentCapacityVeh) + " veh/hr"); // baseCapacityVeh * numLanes
//        } else {
            float intxAdjustedPerLaneSatFlowRatePC = satFlowPerLaneRateBasePC * intxAdjustmentFactor;
            int basePerLaneSatFlowRateVeh = Math.round(pc_to_veh(intxAdjustedPerLaneSatFlowRatePC, fhv));
            int totalAdjustedSatFlowRateVeh = basePerLaneSatFlowRateVeh * numLanesInput;
            computedAdjustedSatFlowRateLabel.setText(String.format("%,d", totalAdjustedSatFlowRateVeh) + " veh/hr");
//        }

        //------------Step 3 - Work Zone Configuration-----------
        // -- 3A) Initialize all variables (so as to not break computations)
        float workZoneTypeAdjustment = inputWorkZoneType.getValue().capacityAdjustment;
        int workZoneLaneCapacity = 0;
        int workZoneLaneSatFlow = 0;
        int numLanesClosed = 0;

        // -- 3B) Attempt to parse/extract work zone inputs
        if (inputWorkZoneType.getValue().isCustom()) {
            try {
                workZoneLaneCapacity = Integer.parseInt(inputWorkZoneLaneCapacity.getText().replace(",", ""));
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0
            }
            try {
                workZoneLaneSatFlow = Integer.parseInt(inputWorkZoneLaneSatFlow.getText().replace(",", ""));
            } catch (NumberFormatException e) {
                // Do nothing, value defaults to 0
            }
        } else {
            workZoneLaneCapacity = Math.round(capacityPerLaneBaseSegmentPC * workZoneTypeAdjustment);
            inputWorkZoneLaneCapacity.setText(String.format("%,d", workZoneLaneCapacity));
            workZoneLaneSatFlow = Math.round(intxAdjustedPerLaneSatFlowRatePC * workZoneTypeAdjustment);
            inputWorkZoneLaneSatFlow.setText(String.format("%,d", workZoneLaneSatFlow));
        }
        if (inputWorkZoneNumLanesClosed.getValue() != null) {
            numLanesClosed = inputWorkZoneNumLanesClosed.getValue().value;
        }

        // -- 3C) Compute work zone segment capacity/sat flow rate
        int workZoneComputedLaneCapacityVeh = Math.round(pc_to_veh(workZoneLaneCapacity, fhv));
        workZoneComputedLaneCapacityVehLabel.setText(String.format("%,d", workZoneComputedLaneCapacityVeh) + " veh/hr/ln");
        int workZoneComputedLaneSatFlowVeh = Math.round(pc_to_veh(workZoneLaneSatFlow, fhv));
        workZoneComputedLaneSatFlowVehLabel.setText(String.format("%,d", workZoneComputedLaneSatFlowVeh) + " veh/hr/ln");
        int wzLanesOpen = numLanesInput - numLanesClosed;
        int totalWorkZoneCapacityVeh = workZoneComputedLaneCapacityVeh * wzLanesOpen;
        computedTotalWorkZoneCapacityLabel.setText(String.format("%,d", totalWorkZoneCapacityVeh) + " veh/hr (" + wzLanesOpen + " lanes open)");
        int totalWorkZoneSatFlowVeh = workZoneComputedLaneSatFlowVeh * wzLanesOpen;
        computedTotalWorkZoneSatFlowLabel.setText(String.format("%,d", totalWorkZoneSatFlowVeh) + " veh/hr (" + wzLanesOpen + " lanes open)");

        // Update graph and additinal final outputs
        float[] pctProfile;
        if (profileType >= 0 && profileSubType >= 0) {
            pctProfile = AADTDistributionHelper.getDefaultProfileInHourlyPct(profileType, profileSubType);
        } else {
            pctProfile = new float[96];
            Arrays.fill(pctProfile, 0.0f);
        }
        float directionalAADT = (float) (bidirectionalAADT * dirSplit);
        int[] demandArray = AADTDistributionHelper.get24HourMainlineDemand(pctProfile, directionalAADT);

        double maxBaseDC = -1.0;
        double maxWorkZoneDC = -1.0;
        int basePeriodsAboveCapacity = 0;
        int wzPeriodsAboveCapacity = 0;
        int maxStreakAboveBaseCapacity = 0;
        int maxStreakAboveWorkZoneCapacity = 0;
        int currStreakAboveBaseCapacity = 0;
        int currStreakAboveWorkZoneCapcity = 0;

        int baseTotalCapacity = isFreeway ? totalSegmentCapacityVeh : totalAdjustedSatFlowRateVeh;
        int wzTotalCapacity = isFreeway ? totalWorkZoneCapacityVeh : totalWorkZoneSatFlowVeh;
        int[] estQueLenBase = new int[demandSeries.getData().size()];
        int maxEstQueueBase = 0;
        int[] estQueLenWZ = new int[demandSeries.getData().size()];
        int maxEstQueueWZ = 0;
        for (int per = 0; per < demandSeries.getData().size(); per++) {
            baseCapacitySeries.getData().get(per).setYValue(baseTotalCapacity);
            demandSeries.getData().get(per).setYValue(demandArray[per]);
            workZoneCapacitySeries.getData().get(per).setYValue(wzTotalCapacity);
            double currBaseDC = demandArray[per] / (double) baseTotalCapacity;
            double currWorkZoneDC = demandArray[per] / (double) wzTotalCapacity;
            if (currBaseDC > maxBaseDC) {
                maxBaseDC = currBaseDC;
            }
            if (currWorkZoneDC > maxWorkZoneDC) {
                maxWorkZoneDC = currWorkZoneDC;
            }
            if (currBaseDC >= 1.0 ) {
                basePeriodsAboveCapacity++;
                currStreakAboveBaseCapacity ++;
            } else {
                if (currStreakAboveBaseCapacity > maxStreakAboveBaseCapacity) {
                    maxStreakAboveBaseCapacity = currStreakAboveBaseCapacity;
                }
                currStreakAboveBaseCapacity = 0;
            }
            if (currWorkZoneDC >= 1.0) {
                wzPeriodsAboveCapacity++;
                currStreakAboveWorkZoneCapcity++;
            } else {
                if (currStreakAboveWorkZoneCapcity > maxStreakAboveWorkZoneCapacity) {
                    maxStreakAboveWorkZoneCapacity = currStreakAboveWorkZoneCapcity;
                }
                currStreakAboveWorkZoneCapcity = 0;
            }

            // Estimate queue accumlation (Base)
            int currDeltaBase = demandArray[per] - baseTotalCapacity;
            int prevDeltaBase = per > 0 ? estQueLenBase[per - 1] : 0;
            int currAccumQueueBase = prevDeltaBase + currDeltaBase;
            estQueLenBase[per] = Math.max(currAccumQueueBase, 0);
            if (estQueLenBase[per] > maxEstQueueBase) {
                maxEstQueueBase = estQueLenBase[per];
            }
            // Estimate queue accumlation (Work Zone)
            int currDeltaWZ = demandArray[per] - wzTotalCapacity;
            int prevDeltaWZ = per > 0 ? estQueLenWZ[per - 1] : 0;
            int currAccumQueueWZ = prevDeltaWZ + currDeltaWZ;
            estQueLenWZ[per] = Math.max(currAccumQueueWZ, 0);
            if (estQueLenWZ[per] > maxEstQueueWZ) {
                maxEstQueueWZ = estQueLenWZ[per];
            }
            qLengthBaseSeries.getData().get(per).setYValue((estQueLenBase[per] / 4.0f) / (JAM_DENSITY * numLanesInput));
            qLengthWorkZoneSeries.getData().get(per).setYValue((estQueLenWZ[per] / 4.0f) / (JAM_DENSITY * wzLanesOpen));
            dcRatioBaseSeries.getData().get(per).setYValue(currBaseDC);
            dcRatioWorkZoneSeries.getData().get(per).setYValue(currWorkZoneDC);

//            System.out.println("Period: " + String.valueOf(per + 1) + ", Capacity: " + String.valueOf(totalSegmentCapacityVeh) + ", Demand: " + String.format("%,2d", demandArray[per]));
        }
        lblMaxDCRatioBaseComputed.setText(String.format("%.2f", maxBaseDC));
        lblMaxDCRatioWorkZoneComputed.setText(String.format("%.2f", maxWorkZoneDC));
        lblMaxVCRatioBaseComputed.setText(String.format("%.2f", Math.min(maxBaseDC, 1.0f)));
        lblMaxVCRatioWorkZoneComputed.setText(String.format("%.2f", Math.min(maxWorkZoneDC,1.0f)));
        lblHrsAboveBaseCapOutputComputed.setText(convertPeriodsToHoursString(basePeriodsAboveCapacity));
        lblHrsAboveWZCapOutputComputed.setText(convertPeriodsToHoursString(wzPeriodsAboveCapacity));
        lblStreakAboveBaseCapOutputComputed.setText(convertPeriodsToHoursString(maxStreakAboveBaseCapacity));
        lblStreakAboveWZCapOutputComputed.setText(convertPeriodsToHoursString(maxStreakAboveWorkZoneCapacity));
        lblLvlofCongestBaseOutputComputed.setText(getLevelOfCongestionByVC(maxBaseDC));
        lblLvlofCongestWZOutputComputed.setText(getLevelOfCongestionByVC(maxWorkZoneDC));
        lblEstQueLengthBaseOutputComputed.setText(String.format("%.2f", (maxEstQueueBase / 4.0f) / (JAM_DENSITY * numLanesInput)) + " miles");
        lblEstQueLengthWZOutputComputed.setText(String.format("%.2f", (maxEstQueueWZ / 4.0f) / (JAM_DENSITY * wzLanesOpen)) + " miles");
//        }
    }

    private String getLevelOfCongestionByVC(double vcRatio) {
        if (vcRatio < 0.85) {
            return "Low";
        } else if (vcRatio < 0.95) {
            return "Mild";
        } else if (vcRatio < 1.05) {
            return "High";
        } else {
            return "Very High";
        }
    }

    private Pane createCustomDemandToCapacityLegend() {
        Label legendItemDemand = new Label("Demand");
        Line legendLineDemand = new Line(0, 5, 30, 5);
        legendLineDemand.setStroke(Color.web(ColorHelper.WZ_NAVY));
        legendLineDemand.setStrokeWidth(2);
        legendItemDemand.setGraphic(legendLineDemand);
        legendItemDemand.setGraphicTextGap(10);
        legendItemDemand.setContentDisplay(ContentDisplay.LEFT);
        legendItemDemand.setAlignment(Pos.CENTER);
        legendItemDemand.hoverProperty().addListener((observableValue, wasHover, isHover) -> {
            if (isHover) {
                demandSeries.getNode().getStyleClass().add("hover");
            } else {
                demandSeries.getNode().getStyleClass().remove("hover");
            }
        });
        Label legendItemBaseCapacity = new Label("Base Capacity");
        Line legendLineBaseCapacity = new Line(0, 5, 30, 5);
        legendLineBaseCapacity.setStroke(Color.web("#2f2f31"));
        legendLineBaseCapacity.setStrokeWidth(2);
        legendLineBaseCapacity.getStrokeDashArray().setAll(Arrays.asList(5d, 5d));
        legendItemBaseCapacity.setGraphic(legendLineBaseCapacity);
        legendItemBaseCapacity.setGraphicTextGap(10);
        legendItemBaseCapacity.setContentDisplay(ContentDisplay.LEFT);
        legendItemBaseCapacity.setAlignment(Pos.CENTER);
        Label legendItemWorkZone = new Label("Work Zone Capacity");
        Line legendLineWorkZone = new Line(0, 5, 30, 5);
        legendLineWorkZone.setStroke(Color.web(ColorHelper.WZ_ORANGE));
        legendLineWorkZone.setStrokeWidth(2);
        legendLineWorkZone.getStrokeDashArray().setAll(Arrays.asList(5d, 5d));
        legendItemWorkZone.setGraphic(legendLineWorkZone);
        legendItemWorkZone.setGraphicTextGap(10);
        legendItemWorkZone.setContentDisplay(ContentDisplay.LEFT);
        legendItemWorkZone.setAlignment(Pos.CENTER);
        GridPane customLegend = new GridPane();
        customLegend.setAlignment(Pos.CENTER);
        customLegend.setHgap(15);
        customLegend.add(legendItemDemand, 0, 0);
        customLegend.add(legendItemBaseCapacity, 1, 0);
        customLegend.add(legendItemWorkZone, 2, 0);
        return customLegend;
    }

    private Pane createCustomQueueLengthLegend() {
        Label legendItemBase = new Label("Base Conditions");
        Line legendLineBase = new Line(0, 5, 30, 5);
        legendLineBase.setStroke(Color.web("#2f2f31"));
        legendLineBase.setStrokeWidth(2);
        legendItemBase.setGraphic(legendLineBase);
        legendItemBase.setGraphicTextGap(10);
        legendItemBase.setContentDisplay(ContentDisplay.LEFT);
        legendItemBase.setAlignment(Pos.CENTER);

        Label legendItemWZ = new Label("Work Zone");
        Line legendLineWZ = new Line(0, 5, 30, 5);
        legendLineWZ.setStroke(Color.web(ColorHelper.WZ_ORANGE));
        legendLineWZ.setStrokeWidth(2);
        legendItemWZ.setGraphic(legendLineWZ);
        legendItemWZ.setGraphicTextGap(10);
        legendItemWZ.setContentDisplay(ContentDisplay.LEFT);
        legendItemWZ.setAlignment(Pos.CENTER);

        GridPane customLegend = new GridPane();
        customLegend.setAlignment(Pos.CENTER);
        customLegend.setHgap(15);
        customLegend.add(legendItemBase, 0, 0);
        customLegend.add(legendItemWZ, 1, 0);
        return customLegend;
    }

    private Pane createCustomDCRatioLegend() {
        Label legendItemBase = new Label("Base Conditions");
//        Rectangle legendRectBase = new Rectangle(10, 10, Color.web("#2f2f31"));
        Line legendLineBase = new Line(0, 5, 30, 5);
        legendLineBase.setStroke(Color.web("#2f2f31"));
        legendLineBase.setStrokeWidth(2);
        legendItemBase.setGraphic(legendLineBase);
        legendItemBase.setGraphicTextGap(10);
        legendItemBase.setContentDisplay(ContentDisplay.LEFT);
        legendItemBase.setAlignment(Pos.CENTER);

        Label legendItemWZ = new Label("Work Zone");
//        Rectangle legendRectWZ = new Rectangle(10, 10, Color.web(ColorHelper.WZ_ORANGE));
        Line legendLineWorkZone = new Line(0, 5, 30, 5);
        legendLineWorkZone.setStroke(Color.web(ColorHelper.WZ_ORANGE));
        legendLineWorkZone.setStrokeWidth(2);
        legendLineWorkZone.getStrokeDashArray().setAll(Arrays.asList(5d, 5d));
        legendItemWZ.setGraphic(legendLineWorkZone);
        legendItemWZ.setGraphicTextGap(10);
        legendItemWZ.setContentDisplay(ContentDisplay.LEFT);
        legendItemWZ.setAlignment(Pos.CENTER);

        GridPane customLegend = new GridPane();
        customLegend.setAlignment(Pos.CENTER);
        customLegend.setHgap(15);
        customLegend.add(legendItemBase, 0, 0);
        customLegend.add(legendItemWZ, 1, 0);
        return customLegend;
    }

    private String convertPeriodsToHoursString(int numPeriods) {
        String timeStr = "";
        int hours = Math.floorDiv(numPeriods, 4);

        if (hours == 0) {
            int minutes = numPeriods % 4;
            return (minutes * 15) + " minutes";
        } else {
            return String.format("%.2f", numPeriods / (float) 4) + " hours";
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
        public final float value;

        public IntxTypeInput(String displayStr, float value) {
            this.displayStr = displayStr;
            this.value = value;
        }

        @Override
        public String toString() {return this.displayStr;}
    }

    private class SegmentTypeItem {
        public final String displayStr;
        public final int baseCapacity;
        private final SimpleBooleanProperty custom;

        public SegmentTypeItem(String displayStr, int baseCapacity, boolean isCustom) {
            this.displayStr = displayStr;
            this.baseCapacity = baseCapacity;
            this.custom = new SimpleBooleanProperty(isCustom);
        }

        public boolean isCustom() {
            return custom.get();
        }

        public SimpleBooleanProperty customProperty() {
            return custom;
        }

        public void setCustom(boolean custom) {
            this.custom.set(custom);
        }

        @Override
        public String toString() { return this.displayStr; }
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
        public final int value;

        public VolumeProfileItem(String displayStr, int value) {
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
    private static class WorkZoneTypeItem {
        public final String displayStr;
        public final float capacityAdjustment;
        public final WorkZoneType type;

        public WorkZoneTypeItem(String displayStr, float capacityAdjustment, WorkZoneType type) {
            this.displayStr = displayStr;
            this.capacityAdjustment = capacityAdjustment;
            this.type = type;
        }

        public boolean allowsLaneClosure() {
            switch (type) {
                case NONE:
                case SHOULDER:
                    return false;
                case LANE_CLOSURE:
                case CROSSOVER:
                case CUSTOM:
                default:
                    return true;
            }
        }

        public boolean isCustom() {
            return type == WorkZoneType.CUSTOM;
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

    public class PeriodFormatter extends NumberAxis.DefaultFormatter {

        public PeriodFormatter(NumberAxis axis) {
            super(axis);
        }

        @Override
        public String toString(Number object) {
            int apIndex = object.intValue();
            int hour = Math.floorDiv(apIndex, 4);
            int minute =15 * (apIndex % 4);
            String strAMPM = hour < 12 ? "am" : "pm";
            if (hour == 0) {
                hour = 12;
            } else if (hour > 12) {
                hour -= 12;
            }

            return hour + ":" + String.format("%02d", minute) + strAMPM;
        }
    }

    private Node createDemandToCapacityChart() {
        final NumberAxis xAxis = new NumberAxis("Time of Day", 0, 96, 1);
        xAxis.setLabel("Time of Day");
        xAxis.setTickLabelFormatter(new PeriodFormatter(xAxis));
        xAxis.setMinorTickCount(0);
        xAxis.setTickUnit(4.0);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Demand (veh/hr)");
        DataCursorLineChart<Number, Number> demandToCapacityChart = new DataCursorLineChart<>(xAxis, yAxis, 0, 0);
        demandToCapacityChart.getStyleClass().add("demand-vs-capacity-chart");
        demandToCapacityChart.setTitle("24-Hour Demand vs Project Capacities");
        demandToCapacityChart.setCreateSymbols(false);
        demandToCapacityChart.setAnimated(true);
        demandToCapacityChart.setLegendVisible(false);
        //defines a series
        demandSeries.setName("Demand Data");
        baseCapacitySeries.setName("Base Capacity");
        workZoneCapacitySeries.setName("Work Zone Capacity");
        //populates series with initial data
        for (int per = 0; per < 96; per++) {
            demandSeries.getData().add(new XYChart.Data<>(per, 0));
            baseCapacitySeries.getData().add(new XYChart.Data<>(per, 0));
            workZoneCapacitySeries.getData().add(new XYChart.Data<>(per, 0));
        }

        demandToCapacityChart.getData().add(baseCapacitySeries);
        demandToCapacityChart.getData().add(workZoneCapacitySeries);
        demandToCapacityChart.getData().add(demandSeries);


        Pane customLegend = createCustomDemandToCapacityLegend();
        BorderPane demandToCapacityChartWrapper = new BorderPane();
        demandToCapacityChartWrapper.setCenter(demandToCapacityChart);
        demandToCapacityChartWrapper.setBottom(customLegend);

        configureVCChartTooltips();

        return demandToCapacityChartWrapper;
    }

    private Node createQueueLengthChart() {
        final NumberAxis xAxis = new NumberAxis("Time of Day", 0, 96, 1);
        xAxis.setLabel("Time of Day");
        xAxis.setTickLabelFormatter(new PeriodFormatter(xAxis));
        xAxis.setMinorTickCount(0);
        xAxis.setTickUnit(4.0);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Estimated Queue Length (miles)");
        DataCursorAreaChart<Number, Number> qLenChart = new DataCursorAreaChart<>(xAxis, yAxis, 0, 0);
        qLenChart.getStyleClass().add("queue-length-chart");
        qLenChart.setTitle("24-Hour Estimated Queue Lengths");
        qLenChart.setCreateSymbols(false);
        qLenChart.setAnimated(true);
        qLenChart.setLegendVisible(false);
        //defines a series
        qLengthBaseSeries.setName("Base Conditions");
        qLengthWorkZoneSeries.setName("Work Zone");
        //populates series with initial data
        for (int per = 0; per < 96; per++) {
            qLengthBaseSeries.getData().add(new XYChart.Data<>(per, 0));
            qLengthWorkZoneSeries.getData().add(new XYChart.Data<>(per, 0));
        }

        qLenChart.getData().add(qLengthWorkZoneSeries);
        qLenChart.getData().add(qLengthBaseSeries);

        configureQueueLengthChartTooltips();

        Pane customLegend = createCustomQueueLengthLegend();
        BorderPane chartWrapper = new BorderPane();
        chartWrapper.setCenter(qLenChart);
        chartWrapper.setBottom(customLegend);
        return chartWrapper;
    }

    private Node createDCRatioBarChart() {
        final NumberAxis xAxis = new NumberAxis("Time of Day", 0, 96, 1);
        xAxis.setLabel("Time of Day");
        xAxis.setTickLabelFormatter(new PeriodFormatter(xAxis));
        xAxis.setMinorTickCount(0);
        xAxis.setTickUnit(4.0);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Estimated Queue Length (miles)");
        DataCursorLineChart<Number, Number> dcRatioChart = new DataCursorLineChart<>(xAxis, yAxis, 0, 0);
        dcRatioChart.getStyleClass().add("vc-ratio-chart");
        dcRatioChart.setTitle("24-Hour Demand-to-Capacity Ratio");
        dcRatioChart.setCreateSymbols(false);
        dcRatioChart.setAnimated(true);
        dcRatioChart.setLegendVisible(false);
        //defines a series
        dcRatioBaseSeries.setName("Base Conditions");
        dcRatioWorkZoneSeries.setName("Work Zone");
        //populates series with initial data
        for (int per = 0; per < 96; per++) {
            dcRatioBaseSeries.getData().add(new XYChart.Data<>(per, 0));
            dcRatioWorkZoneSeries.getData().add(new XYChart.Data<>(per, 0));
        }

        dcRatioChart.getData().add(dcRatioBaseSeries);
        dcRatioChart.getData().add(dcRatioWorkZoneSeries);

        configureDCRatioChartTooltips();

        Pane customLegend = createCustomDCRatioLegend();
        BorderPane chartWrapper = new BorderPane();
        chartWrapper.setCenter(dcRatioChart);
        chartWrapper.setBottom(customLegend);
        return chartWrapper;
    }

    private static final Object MOUSE_TRIGGER_LOCATION = "tooltip-last-location";

    private static final float TERRAIN_TYPE_LEVEL = 2.0f;
    private static final float TERRAIN_TYPE_ROLLING = 2.5f;
    private static final float TERRAIN_TYPE_MOUNTAIN = 3.0f;

    private static final float JAM_DENSITY =  190.0f;

    private enum WorkZoneType {
        NONE,
        SHOULDER,
        LANE_CLOSURE,
        CROSSOVER,
        CUSTOM
    }
}
