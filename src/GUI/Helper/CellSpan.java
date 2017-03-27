/*
 * Adapted from: http://fxexperience.com/2012/10/cell-spanning-in-tableview/
 */
package GUI.Helper;

/**
 *
 * @author ltrask
 */
public class CellSpan {

    private final int rowSpan;
    private final int columnSpan;

    public CellSpan(int rowSpan, int columnSpan) {
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public int getColumnSpan() {
        return columnSpan;
    }
}
