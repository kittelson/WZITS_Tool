package wzits_fx;

import GUI.Helper.IconHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class wzits_VCwiz extends Application {

    public class vcWizard extends VBox {
        GridPane inputsGrid = new GridPane();
        VBox root = new VBox();
    }

    @Override
    public void start(Stage primaryStage) {
        Label lblAADT = new Label("Bidirectional AADT");
        Label lblDirSplit = new Label("Directional Split");
        Label lblcompDir = new Label("Computed Directional");
//        Label lblDemandPro = new Label("Demand Profile");
//        Label lblPerTrucks = new Label("Percent Trucks");
//        Label lblTerrain = new Label("Terrain Type");
        Label lblLaneCap = new Label("Base Per Lane Capacity");
        Label lblNumLanes = new Label("Number of Lanes");
        Label lblSegCap = new Label("Total Segment Capacity");
        Label lblvehPerDay = new Label("veh/day");
        Label lbldirSplit = new Label(); //change to a binding that updates on slider changing
        Label lblTrucks = new Label("%");
        Label lblvehLnHr = new Label("veh/ln/hr");
        Label lblcompValue = new Label();

        lblAADT.getStyleClass().add("vc-label-styles");

        JFXButton btnPrev = new JFXButton();

        FontIcon prevIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_LEFT, Color.SEAGREEN, 20);
        btnPrev.setGraphic(prevIcon);
        FontIcon nextIcon = IconHelper.createIcon(FontAwesomeSolid.CHEVRON_RIGHT, Color.SEAGREEN, 20);
        btnPrev.setGraphic(nextIcon);

        JFXTextField txtAADT = new JFXTextField();
        JFXTextField txtLaneCap = new JFXTextField();
        JFXTextField txtPercTrucks = new JFXTextField();
        txtPercTrucks.setPromptText("Percent Trucks");
        txtPercTrucks.setLabelFloat(true);

        JFXSlider slidNumLanes = new JFXSlider();
        JFXSlider slidDirSplit = new JFXSlider();

        txtAADT.getStyleClass().add("jf-txtbox");
        txtLaneCap.getStyleClass().add("jf-txtbox");
        txtPercTrucks.getStyleClass().add("jf-txtbox");

        slidNumLanes.setShowTickMarks(true);
        slidDirSplit.setShowTickMarks(true);
        slidNumLanes.setShowTickLabels(true);
        slidDirSplit.setShowTickLabels(true);
        slidDirSplit.setMajorTickUnit(.5);
        slidNumLanes.setMajorTickUnit(1);
        slidDirSplit.setMax(1);
        slidNumLanes.setMax(6);
        slidNumLanes.getStyleClass().add("jfx-slider");

//        slidDirSplit.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(
//                    ObservableValue<? extends Number> observableValue,
//                    Number oldValue,
//                    Number newValue) {
//                lbldirSplit.textProperty().setValue(
//                        String.valueOf(newValue.doubleValue())
//                );
//            }
//        });
//        Double valueZero = Double.valueOf(lbldirSplit.getText());
//        Integer valueOne = Integer.valueOf(txtAADT.getText());
//        lblcompValue.textProperty().bind(
//                Bindings.createStringBinding(
//                        () -> String.format("%.2f", valueOne / valueZero)
//                )
//        );

        JFXComboBox<Label> cmbPeak = new JFXComboBox<>();
        cmbPeak.getItems().add(new Label("AM Peak"));
        cmbPeak.getItems().add(new Label("PM Peak"));
        cmbPeak.setMaxWidth(Integer.MAX_VALUE);
        cmbPeak.setPromptText("Demand Profile");
        cmbPeak.getStyleClass().add("jfx-combo-style");

        JFXComboBox<Label> cmbTerType = new JFXComboBox<>();
        cmbTerType.getItems().add(new Label("Level"));
        cmbTerType.getItems().add(new Label("Rocky"));
        cmbTerType.setMaxWidth(Integer.MAX_VALUE);
        cmbTerType.setPromptText("Terrain Type");
        cmbTerType.getStyleClass().add("jfx-combo-style");

        JFXButton btnUpdAnalysis = new JFXButton("Update Analysis");
        FontIcon update = IconHelper.createIcon(FontAwesomeSolid.RETWEET, Color.WHITE, 20);
        btnUpdAnalysis.setGraphic(update);
//        btnUpdAnalysis.
        btnUpdAnalysis.getStyleClass().add("jfx-button-style");

        GridPane inputGrids = new GridPane();
        BorderPane navPane = new BorderPane();
        BorderPane graphPane = new BorderPane();
        BorderPane aadtBP = new BorderPane();
        BorderPane dirSplitBP = new BorderPane();
        BorderPane trucksBP = new BorderPane();
        BorderPane laneCapBP = new BorderPane();

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time of Day");
        yAxis.setLabel("Demand (veh/hr)");

        aadtBP.setLeft(txtAADT);
        aadtBP.setRight(lblvehPerDay);

        dirSplitBP.setLeft(slidDirSplit);
        dirSplitBP.setRight(lbldirSplit);

        trucksBP.setLeft(txtPercTrucks);
        trucksBP.setRight(lblTrucks);

        laneCapBP.setLeft(txtLaneCap);
        laneCapBP.setRight(lblvehLnHr);

        navPane.setCenter(btnUpdAnalysis);
        navPane.setLeft(prevIcon);
        navPane.setRight(nextIcon);
        navPane.setPadding(new Insets(7,0,7,0));

        vcWizard vcWizard = new vcWizard();
        //columns, rows
        inputGrids.add(lblAADT, 0, 0);
        inputGrids.add(lblDirSplit, 0, 1);
        inputGrids.add(lblcompDir, 0, 2);
        inputGrids.add(aadtBP, 1, 0);
        inputGrids.add(dirSplitBP, 1, 1);
        inputGrids.add(lblcompValue,1,2);
//        inputGrids.add(lblDemandPro, 2, 0);
//        inputGrids.add(lblPerTrucks, 2, 1);
//        inputGrids.add(lblTerrain, 2, 2);
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
        inputGrids.setPadding(new Insets(15,0,10,0));

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


        vcWizard.getChildren().addAll(inputGrids, navPane, graphPane);
        //set node in stage as custom panel 'new vc wizard panel' to launch panel
        vcWizard.getStylesheets().add(getClass().getResource("/GUI/CSS/globalStyle.css").toExternalForm());
        primaryStage.setScene(new Scene(vcWizard, 1000, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
