/*
 * Adapted from: http://fxexperience.com/2012/10/cell-spanning-in-tableview/
 */
package GUI.Helper;

/**
 *
 * @author ltrask
 */
public interface SpanModel {

    // cell spanning is only run when isCellSpanEnabled returns true
    public boolean isCellSpanEnabled();

    // Returns the CellSPan for the given row/column index.
    public CellSpan getCellSpanAt(int rowIndex, int columnIndex);
}
