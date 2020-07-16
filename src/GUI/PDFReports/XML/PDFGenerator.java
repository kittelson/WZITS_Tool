package GUI.PDFReports.XML;

import java.awt.Desktop;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

public class PDFGenerator {

    public void convertToPDF(String xmlFilepath, String xslFilepath, String pdfFilepath) throws Exception {
        // the XSL FO file
        File xsltFile = new File(xslFilepath);
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File(xmlFilepath).getAbsolutePath());
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;
        out = new java.io.FileOutputStream(pdfFilepath);

        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile.getAbsolutePath())); //.getAbsolutePath()

            // Resulting SAX events (the generated FO) must be piped through to
            // FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);
        } catch (TransformerException e) {
            e.printStackTrace();
        } finally {
            out.close();
            if (true) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        File f = new File(pdfFilepath);
                        Desktop.getDesktop().open(f);

                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "The report was created successfully, but the PDF reader could not be launched from the WZITS Tool.",
                            "Report Launch Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
