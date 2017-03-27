/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Helper;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;

/**
 *
 * @author ltrask
 */
public class CellSpanTableView<S> extends TableView<S> {

    public CellSpanTableView() {
        super();
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    public CellSpanTableView(ObservableList<S> items) {
        super(items);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    // --- Span Model
    private ObjectProperty<SpanModel> spanModel
            = new SimpleObjectProperty<SpanModel>(this, "spanModel");

    public final ObjectProperty<SpanModel> spanModelProperty() {
        return spanModel;
    }

    public final void setSpanModel(SpanModel value) {
        spanModelProperty().set(value);
    }

    public final SpanModel getSpanModel() {
        return spanModel.get();
    }

    /**
     * *************************************************************************
     *                                                                         *
     * Stylesheet Handling * *
     * ************************************************************************
     */
    private static final String DEFAULT_STYLE_CLASS = "cell-span-table-view";

    private boolean isFirstRun = true;

    @Override
    protected void layoutChildren() {
        // ugly hack to enable adding the cell-span.css file to the scenegraph
        // without requiring user intervention
        if (isFirstRun) {
            Scene scene = getScene();
            if (scene != null) {
                ObservableList<String> stylesheets = scene.getStylesheets();
                String cssPath = CellSpanTableView.class.getResource("/GUI/CSS/cell-span.css").toExternalForm();
                if (!stylesheets.contains(cssPath)) {
                    stylesheets.add(cssPath);
                    isFirstRun = false;
                }
            }
        }

        super.layoutChildren();
    }
}
