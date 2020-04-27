package GUI.PDFReports.XML;

import GUI.MainController;
import javafx.geometry.Pos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

public class XMLGenerator {

    private String template;
//    private Seed seed;
    private String xmlFilePath;
    private DocumentBuilderFactory documentBuilderFactory = null;
    private DocumentBuilder documentBuilder;
    private Document document;
    private Element rootElement;
    private TransformerFactory transformerFactory = null;
    private Transformer transformer = null;
    private DOMSource domSource = null;
    private StreamResult outputWriter;
    private BodyElement bodyElement;

    //This getter method when called for the first time instanciates the factory.
    //For all the subsequent calls, it returns the same method.
    DocumentBuilderFactory getDocumentBuilderFactory() {
        if (this.documentBuilderFactory == null) {
            this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
            return this.documentBuilderFactory;
        }
        return this.documentBuilderFactory;
    }

    DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        if (this.documentBuilderFactory == null) {
            getDocumentBuilderFactory();
        }
        if (documentBuilder == null) {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            return documentBuilder;
        }
        return documentBuilder;
    }

    Document getDocument() {
        if (documentBuilder == null) {
            getDocument();
        }
        if (document == null) {
            document = documentBuilder.newDocument();
            return document;
        }
        return document;
    }

    TransformerFactory getTransformerFactory() {
        if (transformerFactory == null) {
            transformerFactory = TransformerFactory.newInstance();
        }
        return transformerFactory;
    }

    public Element getRootElement() {
        return rootElement;
    }

    public XMLGenerator(String projectFileName, String xmlFilePath, String title1, String title2) throws ParserConfigurationException {
        this.xmlFilePath = xmlFilePath;
        outputWriter = new StreamResult(new File(xmlFilePath).getAbsolutePath());
//        this.seed = seed;
        getDocumentBuilderFactory();
        getDocumentBuilder();
        getDocument();
        createRootElement("pdf-data");
        HeaderElement header = new HeaderElement(getDocument(), title1);
        header.setSubtitle(title2);
        header.setProgramLogo(MainController.getResFolderLocation() + "/wzits_icon_64.png");
        header.setAgencyLogo(MainController.getResFolderLocation() + "/us_dot_logo.png");
        header.setFileName(projectFileName);
        header.generateHeader(getRootElement());
        bodyElement = new BodyElement(getDocument(), getRootElement());
        this.template = title1;
    }

    public void generateImage(String filepath, double width, double height, String header, String footer) {
        Element image = bodyElement.generateImage(filepath, width, height, header, footer);
        bodyElement.getBody().appendChild(image);
    }

    public void generateMap(String filepath, double width, double height, String header, String footer) {
        Element image = bodyElement.generateMap(filepath, width, height, header, footer);
        bodyElement.getBody().appendChild(image);
    }

    public void generateTable(String[][] data, String tableId, Boolean headerColumn, Boolean headerRow, Boolean generateCellId, String header, String footer, int tableWidth, float[] customColWidths, String[] customColAlignments) {
        Element table = bodyElement.generateTable(data, headerRow, headerColumn, generateCellId, false, header, footer, tableWidth, customColWidths, customColAlignments);
        table.setAttribute("id", tableId);
        bodyElement.getBody().appendChild(table);
    }

    public void generateTitle(String titleText, Pos titleJustification) {
        Element title = bodyElement.generateTitle(titleText, titleJustification);
        bodyElement.getBody().appendChild(title);
    }

    public void generateContour(String[][] data, String tableId, Boolean headerColumn, Boolean headerRow, Boolean generateCellId, String header, String footer) {
        bodyElement.setTableHeaderColumnSize(20);
        bodyElement.setTotalTableSize(210);
        Element table = bodyElement.generateTable(data, headerRow, headerColumn, generateCellId, true, header, footer, 100,null, null);
        table.setAttribute("id", tableId);
        bodyElement.getBody().appendChild(table);
    }

    public void createRootElement(String elementName) {
        rootElement = document.createElement(elementName);
        //TODO: Hardcoding the attributes right now. Can be passed in as arguments later
        rootElement.setAttribute("version", "0.0.1");
        getDocument().appendChild(rootElement);
    }

    public void generate() throws TransformerException {
        getTransformerFactory();
        getDOMSource();
        getTransformer().transform(getDOMSource(), getOutputWriter());
    }

    private StreamResult getOutputWriter() {
        return outputWriter;
    }

    private DOMSource getDOMSource() {
        if (domSource == null) {
            domSource = new DOMSource(document);
            return domSource;
        }
        return domSource;
    }

    private Transformer getTransformer() throws TransformerConfigurationException {
        if (transformer == null) {
            transformer = getTransformerFactory().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            return transformer;
        }
        return transformer;
    }

    public void addPageBreak() {
        bodyElement.getBody().appendChild(bodyElement.addPageBreak());
    }

    public void generateCommentBlock() {
        bodyElement.getBody().appendChild(bodyElement.generateCommentBlock());
    }
}
