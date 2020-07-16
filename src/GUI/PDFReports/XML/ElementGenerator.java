package GUI.PDFReports.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementGenerator {

    private Document document;
    private float totalTableSize;
    private float tableHeaderColumnSize;

    public ElementGenerator(Document doc, Element parent) {
        document = doc;
    }

    public String urlWrapper(String url) {
        return "url('" + url + "')";
    }

    public Element generateElement(String tag) {
        Element element = document.createElement(tag);
        return element;
    }

    public void populateElementValue(Element e, String value) {
        e.appendChild(document.createTextNode(value));
        return;
    }

    public Element generateAndPopulateStringElement(String tag, String value) {
        Element e = document.createElement(tag);
        e.appendChild(document.createTextNode(value));
        return e;
    }

    public String getId(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("[\\s]", "_");
        return s;
    }

    private void generateRow(String data, Element rowElement, Boolean ColumnHeader, Boolean generateCellIds) {
        generateRow(data, rowElement, ColumnHeader, generateCellIds, null);
    }

    private void generateRow(String data, Element rowElement, Boolean ColumnHeader, Boolean generateCellIds, String alignment) {
        Element cellElement;
        if (ColumnHeader) {
            cellElement = generateElement("columnHeader");
        } else {
            cellElement = generateElement("cell");
        }

        Element dataElement = generateAndPopulateStringElement("data", data != null ? data : "??");
        cellElement.appendChild(dataElement);
        if (generateCellIds) {
            cellElement.setAttribute("id", getId(data));
        }
        cellElement.appendChild(generateAndPopulateStringElement("text-align", alignment != null ? alignment : "left"));
        rowElement.appendChild(cellElement);
    }

    private void generateCounterRowHeader(String data, Element rowElement, int cols) {
        Element cellElement = generateElement("cell");
        Element colSpanElement;
        Element dataElement = generateAndPopulateStringElement("data", data);
        colSpanElement = generateAndPopulateStringElement("colSpan", String.valueOf(cols));
        cellElement.appendChild(colSpanElement);
        cellElement.appendChild(dataElement);
        rowElement.appendChild(cellElement);
    }

    public Element generateAndPopulateRowSizeElement(String value) {
        Element rowSize = generateElement("rowSize");
        Element cell = generateElement("cell");
        Element data = generateAndPopulateStringElement("data", value);
        cell.appendChild(data);
        rowSize.appendChild(data);
        return rowSize;
    }

    public void setTotalTableSize(float size) {
        totalTableSize = size;
    }

    public void setTableHeaderColumnSize(float size) {
        this.tableHeaderColumnSize = size;
    }

    public Element generateTable(String[][] data, Boolean headerRow, Boolean headerColumn, Boolean generateCellId, Boolean contour, String header, String footer, int tableWidth, float[] customColWidths, String[] customColAlignments) {
        //TODO: counter value yet to be implemented
        Element tableElement;
        Element headerElement, dataElement, footerElement;
        headerElement = generateAndPopulateStringElement("header", header);
        footerElement = generateAndPopulateStringElement("footer", footer);
        dataElement = generateElement("data");
        Element rowElement = null;
        if (contour) {
            int i = 0;
            float rowSize = this.totalTableSize / data.length;
            if (headerRow) {
                rowSize = this.totalTableSize / (data.length - 1);
                i = 1;
            }
//            System.out.println(data.length + String.valueOf(totalTableSize) + String.valueOf(rowSize));
            tableElement = generateElement("contour");
            Element rowSizeElement = generateAndPopulateRowSizeElement(String.valueOf(rowSize));
            dataElement.appendChild(rowSizeElement);
            Element columnSizeElement = generateElement("columnSize");
            Element size = generateAndPopulateStringElement("size", String.valueOf(tableHeaderColumnSize));
            columnSizeElement.appendChild(size);
            float dataLength = (100 - tableHeaderColumnSize) / (data[0].length - 1);
            for (; i < data[0].length; i++) {
                if (headerColumn) {
                    size = generateAndPopulateStringElement("size", String.valueOf(dataLength));
                    columnSizeElement.appendChild(size);
                }
            }
            dataElement.appendChild(columnSizeElement);

        } else if (customColWidths != null) {
            tableElement = generateElement("customTable");
            Element rowSizeElement = generateAndPopulateRowSizeElement(String.valueOf(4.0f));
            dataElement.appendChild(rowSizeElement);
            Element columnSizeElement = generateElement("columnSize");
            //Element size = generateAndPopulateStringElement("size", String.valueOf(tableHeaderColumnSize));
            Element size;
            for (int i = 0; i < data[0].length; i++) {
                size = generateAndPopulateStringElement("size", String.valueOf(customColWidths[i]));
                columnSizeElement.appendChild(size);
            }
            dataElement.appendChild(columnSizeElement);
        } else {
            tableElement = generateElement("table");
        }

        int i = 0;
        if (headerRow) {
//            Add the 0th row as header row
            rowElement = generateElement("rowHeader");
            if (contour) {
                if (data[0].length > 20 && data[0].length < 70) {
                    System.out.println(data[0].length);
                    int numberOfColumns = (data[0].length - 1) / 5;
                    int displacement = (data[0].length - 1) % 5;
                    generateRow(data[i][0], rowElement, false, generateCellId);
                    generateCounterRowHeader(data[i][1] + "-" + data[i][5 + displacement], rowElement,
                            5 + displacement);
                    int j = 1;
                    for (; j < numberOfColumns; j++) {
                        generateCounterRowHeader(data[i][(j * 5) + displacement + 1] + "-"
                                + data[i][((j + 1) * 5 + displacement)], rowElement, 5);
                    }
                } else if (data[0].length >= 70) {
                    int numberOfColumns = (data[0].length - 1) / 10;
                    int displacement = (data[0].length - 1) % 10;
                    generateRow(data[i][0], rowElement, false, generateCellId);
                    generateCounterRowHeader(data[i][1] + "-" + data[i][10 + displacement], rowElement,
                            10 + displacement);
                    int j = 1;
                    for (; j < numberOfColumns; j++) {
                        generateCounterRowHeader(data[i][(j * 10) + displacement + 1] + "-"
                                + data[i][((j + 1) * 10 + displacement)], rowElement, 10);
                    }

                } else {
                    for (int j = 0; j < data[0].length; j++) {
                        generateRow(data[i][j], rowElement, false, generateCellId);
                    }
                    dataElement.appendChild(rowElement);
                }
                dataElement.appendChild(rowElement);
            } else {
                for (int j = 0; j < data[0].length; j++) {
                    generateRow(data[i][j], rowElement, false, generateCellId, customColAlignments != null ? customColAlignments[j] : "center");
                }
                dataElement.appendChild(rowElement);
            }
            i = 1;
        }

        for (; i < data.length; i++) {
            rowElement = generateElement("row");
            int j = 0;
            if (headerColumn) {
                generateRow(data[i][j], rowElement, true, generateCellId, customColAlignments != null ? customColAlignments[j] : null);
                j++; // Incrementing column index
            }

            for (; j < data[i].length; j++) {
                if (data[i][j] == null) {
                    System.out.println("here");
                }
                generateRow(data[i][j], rowElement, false, generateCellId, customColAlignments != null ? customColAlignments[j] : null);
            }
            dataElement.appendChild(rowElement);
        }

        Element tableWidthElement = generateElement("tableWidth");
        Element width = generateAndPopulateStringElement("width", String.valueOf(tableWidth) + "%");
        width.setAttribute("id", "tableWidth");
        tableWidthElement.appendChild(width);
        tableElement.appendChild(width);
        tableElement.appendChild(headerElement);
        tableElement.appendChild(dataElement);
        tableElement.appendChild(footerElement);
        return tableElement;
    }
}
