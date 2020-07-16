package GUI.PDFReports.XML;

import javafx.geometry.Pos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import javax.swing.SwingConstants;

public class BodyElement extends ElementGenerator {

    private Document document;
    private Element body;

    public Element getBody() {
        return body;
    }

    public BodyElement(Document doc, Element parent) {
        super(doc, parent);
        body = generateElement("body");
        parent.appendChild(body);
//        body.appendChild(generateFacilityGraphic());
    }

    public Element generateImage(String filepath, double width, double height, String header, String footer) {
        String wrap = urlWrapper(filepath);
        Element imageElement = generateElement("image");
        imageElement.appendChild(generateAndPopulateStringElement("header", header));
        imageElement.appendChild(generateAndPopulateStringElement("data", wrap));

        Element attributes = generateElement("attributes");
        attributes.appendChild(generateAndPopulateStringElement("content-height", "scale-to-fit"));
        attributes.appendChild(generateAndPopulateStringElement("height", String.valueOf(height) + "in"));
//        attributes.appendChild(generateAndPopulateStringElement("height", String.valueOf(height) + "px"));
        attributes.appendChild(generateAndPopulateStringElement("scaling", "non-uniform"));
        attributes.appendChild(generateAndPopulateStringElement("content-width", String.valueOf(width) + "in"));
//        attributes.appendChild(generateAndPopulateStringElement("content-width", String.valueOf(width) + "px"));

        imageElement.appendChild(attributes);
        imageElement.appendChild(generateAndPopulateStringElement("footer", footer));
        return imageElement;
    }

    public Element generateImagePixels(String filepath, double width, double height, String header, String footer) {
        String wrap = urlWrapper(filepath);
        Element imageElement = generateElement("image");
        imageElement.appendChild(generateAndPopulateStringElement("header", header));
        imageElement.appendChild(generateAndPopulateStringElement("data", wrap));

        Element attributes = generateElement("attributes");
//        attributes.appendChild(generateAndPopulateStringElement("content-height", "scale-to-fit"));
        attributes.appendChild(generateAndPopulateStringElement("height", String.valueOf(height) + "px"));
//        attributes.appendChild(generateAndPopulateStringElement("scaling", "non-uniform"));
        attributes.appendChild(generateAndPopulateStringElement("content-width", String.valueOf(width) + "px"));

        imageElement.appendChild(attributes);
        imageElement.appendChild(generateAndPopulateStringElement("footer", footer));
        return imageElement;
    }

    public Element generateMap(String filepath, double width, double height, String header, String footer) {
        String wrap = urlWrapper(filepath);
        Element imageElement = generateElement("map");
        imageElement.appendChild(generateAndPopulateStringElement("header", header));
        imageElement.appendChild(generateAndPopulateStringElement("data", wrap));

        Element attributes = generateElement("attributes");
        attributes.appendChild(generateAndPopulateStringElement("content-height", "scale-to-fit"));
        attributes.appendChild(generateAndPopulateStringElement("height", String.valueOf(height) + "in"));
        attributes.appendChild(generateAndPopulateStringElement("scaling", "non-uniform"));
        attributes.appendChild(generateAndPopulateStringElement("content-width", String.valueOf(width) + "in"));

        imageElement.appendChild(attributes);
        imageElement.appendChild(generateAndPopulateStringElement("footer", footer));
        return imageElement;
    }

    private Element generateFacilityGraphic() {
//        TODO: As per our discussion, the facility graphics has to be an external graphics file either in jpg, png or
//        TODO: svg and the url to that file is currently hardcoded, has to be changed later on.
        String header = "Facility Image";
        String url = "/Users/alay/Documents/NCSU/ITRE/trial-app/src/main/java/resources/facility.png";
        String wrap = urlWrapper(url);
        Element imageElement = generateElement("image");
        imageElement.appendChild(generateAndPopulateStringElement("header", header));
        imageElement.appendChild(generateAndPopulateStringElement("data", wrap));
        imageElement.appendChild(generateAndPopulateStringElement("footer", ""));
        return imageElement;
    }

    public Element addPageBreak() {
        return generateElement("page-break");
    }

    public Element generateCommentBlock() {
        return generateElement("commentBlock");
    }

    public Element generateTitle(String titleText, Pos titleJustification) {
        Element titleElement;
        switch (titleJustification) {
            default:
            case CENTER:
            case TOP_CENTER:
            case BOTTOM_CENTER:
                titleElement = generateElement("titleCenter");
                break;
            case CENTER_RIGHT:
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
                titleElement = generateElement("titleRight");
                break;
            case CENTER_LEFT:
            case TOP_LEFT:
            case BOTTOM_LEFT:
                titleElement = generateElement("titleLeft");
                break;
        }
        titleElement.appendChild(generateAndPopulateStringElement("titleText", titleText));
        return titleElement;
    }
}
