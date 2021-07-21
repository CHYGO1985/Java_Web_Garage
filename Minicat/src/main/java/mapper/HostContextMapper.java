package mapper;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import pojo.Context;
import pojo.Host;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * The class is for building host and content mapper.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class HostContextMapper {

    private Map<String, Host> hostMap;
    private Map<String, Context> hostContextMap;

    public HostContextMapper() {

        hostMap = new HashMap<>();
        hostContextMap = new HashMap<>();

        loadHost();
    }

    public void loadHost() {

        InputStream resAsStram = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();

        try {

            Document document = saxReader.read(resAsStram);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//Service");
            for (int idx = 0; idx < selectNodes.size(); idx ++) {

                Element element = selectNodes.get(idx);
                Element engineElement = (Element) element.selectSingleNode("Engine");
                Element hostElement = (Element) engineElement.selectSingleNode("Host");

                Attribute nameAttr = hostElement.attribute("name");
                String hostName = nameAttr.getValue();

                Attribute pathAttr = hostElement.attribute("appBase");
                String appDir = pathAttr.getValue();

                Host host = new Host(hostName, appDir);
                hostMap.put(hostName, host);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Context> getHostContextMap() {
        return hostContextMap;
    }

    public void setHostContextMap(Map<String, Context> hostContextMap) {
        this.hostContextMap = hostContextMap;
    }
}
