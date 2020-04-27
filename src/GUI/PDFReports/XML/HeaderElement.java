package GUI.PDFReports.XML;

import GUI.MainController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderElement {

    private final Document document;
//    private final Element header;
    private final Element titleElement;
    private Element subtitleElement = null;
    private Element freevalLogoElement = null;
    private Element ncdotLogoElement = null;
    private Element seedFileNameElement;



    //public HeaderElement(Document doc, Element parent, Seed seed, String title1, String title2) {
    public HeaderElement(Document doc, String title) {
        //String freevalLogoFilePath = PDFReportHelper2.getResFolderLocation() + "freeval-logo256.png";
        //String ncdotLogoFilePath = PDFReportHelper2.getResFolderLocation() + "NCDOT_logo256.png";

        //this.seed = seed;
        document = doc;
        this.titleElement = generateTitle1(title);
        //seedFileNameElement = document.createElement("seedFileName");
        //String seedFileName = seed.getValueString(CEConst.IDS_SEED_FILE_NAME);
        //if (seedFileName == null) {
        //    seedFileName = "[Unsaved Project File]";
        //}
        //seedFileNameElement.appendChild(document.createTextNode(seedFileName));
        //dateTimeElement = generateDateTime();
//        header = generateHeader();
//        parent.appendChild(header);
    }

    public void setFileName(String fileName) {
        seedFileNameElement = document.createElement("seedFileName");
        seedFileNameElement.appendChild(document.createTextNode(fileName));
    }

    public void setProgramLogo(String pathToLogo) {
        freevalLogoElement = generateLogo(pathToLogo, "freevalLogo");
    }

    public void setAgencyLogo(String pathToLogo) {
        ncdotLogoElement = generateLogo(pathToLogo, "ncdotLogo");
    }

    public void setSubtitle(String subtitle) {
        subtitleElement = document.createElement("title2");
        subtitleElement.appendChild(document.createTextNode(subtitle));
    }

    private Element generateTitle1(String template) {
        Element projectElement = document.createElement("title1");
        projectElement.appendChild(document.createTextNode(template));
        return projectElement;
    }

    private Element generateVersion() {
        Element versionElement = document.createElement("programVersion");
        versionElement.appendChild(document.createTextNode("WZITS Tool " + MainController.VERSION));
        return versionElement;
    }

    private Element generateDateTime() {
        Element dataTimeElement = document.createElement("date-time");
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        System.out.println(dateFormat.format(new Date()));
        dataTimeElement.appendChild(document.createTextNode(dateFormat.format(new Date())));
        return dataTimeElement;
    }

    private Element generateLogo(String logoFilePath, String tag) {
        String urlWrap = "url('" + logoFilePath + "')";
        Element logoElement = document.createElement(tag);
        logoElement.appendChild(document.createTextNode(urlWrap));
        return logoElement;
    }

    public Element generateHeader(Element parent) {
        Element header = document.createElement("header");
        header.appendChild(titleElement);
        //header.appendChild(dateTimeElement);
        header.appendChild(generateVersion());
        if (freevalLogoElement != null) {
            header.appendChild(freevalLogoElement);
        }
        if (ncdotLogoElement != null) {
            header.appendChild(ncdotLogoElement);
        }
        if (seedFileNameElement != null) {
            header.appendChild(seedFileNameElement);
        }
        if (subtitleElement != null) {
            header.appendChild(subtitleElement);
        }
        parent.appendChild(header);
        return header;
    }
}
