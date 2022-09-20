import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Config {

    private boolean load;
    private String fileNameLoad;
    private String formatLoad;

    private boolean save;
    private String fileNameSave;
    private String formatSave;

    private boolean log;
    private String fileNameLog;

    public Config() {
        loadConfigFile();
    }

    public boolean isLoad() {
        return load;
    }

    public String getFileNameLoad() {
        return fileNameLoad;
    }

    public String getFormatLoad() {
        return formatLoad;
    }

    public boolean isSave() {
        return save;
    }

    public String getFileNameSave() {
        return fileNameSave;
    }

    public String getFormatSave() {
        return formatSave;
    }

    public boolean isLog() {
        return log;
    }

    public String getFileNameLog() {
        return fileNameLog;
    }

    private void read(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("load");
        Element nodeChild = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeChild.getNodeType()) {
            load = Boolean.parseBoolean(nodeChild.getElementsByTagName("enabled").item(0).getTextContent());
            fileNameLoad = nodeChild.getElementsByTagName("fileName").item(0).getTextContent();
            formatLoad = nodeChild.getElementsByTagName("format").item(0).getTextContent();
        }

        nodeList = doc.getElementsByTagName("save");
        nodeChild = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeChild.getNodeType()) {
            save = Boolean.parseBoolean(nodeChild.getElementsByTagName("enabled").item(0).getTextContent());
            fileNameSave = nodeChild.getElementsByTagName("fileName").item(0).getTextContent();
            formatSave = nodeChild.getElementsByTagName("format").item(0).getTextContent();
        }

        nodeList = doc.getElementsByTagName("log");
        nodeChild = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeChild.getNodeType()) {
            log = Boolean.parseBoolean(nodeChild.getElementsByTagName("enabled").item(0).getTextContent());
            fileNameLog = nodeChild.getElementsByTagName("fileName").item(0).getTextContent();

        }


    }

    private void loadConfigFile() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));
            doc.normalize();
            read(doc);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

    }
}
