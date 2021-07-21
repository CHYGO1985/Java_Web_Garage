package mapper;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import pojo.Context;
import pojo.Host;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 *
 * The class is for building host and content mapper.
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class HostContextMapper {

    // map: localhost : <localhost, webapps dir>
    private Map<String, Host> hostMap;
    // map: localhost : demo1 (name of context)
    private Map<String, Set<String>> hostContextMap;
    // map: demo1 : context obj
    private Map<String, Context> contextMap;

    public HostContextMapper() {

        hostMap = new HashMap<>();
        hostContextMap = new HashMap<>();
        contextMap = new HashMap<>();
        loadHost();
    }

    public Map<String, Context> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<String, Context> contextMap) {
        this.contextMap = contextMap;
    }

    public Map<String, Set<String>> getHostContextMap() {
        return hostContextMap;
    }

    public void setHostContextMap(Map<String, Set<String>> hostContextMap) {
        this.hostContextMap = hostContextMap;
    }

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public void setHostMap(Map<String, Host> hostMap) {
        this.hostMap = hostMap;
    }

    /**
     *
     * Laod host name and webapps dir from server.xml.
     *
     */
    private void loadHost() {

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

                buildHostCxtMap(hostName, appDir);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Build <host name : set of context name> (localhost: (demo1, demo2)) map
     * and <context name : context path> (webapps: .../webapps/demo1) map
     *
     * @param hostName
     * @param appDir
     *
     */
    private void buildHostCxtMap(String hostName, String appDir) {

        File webAppsDir = new File(appDir);

        File[] fileList = webAppsDir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                Set<String> cxtNameSet = hostContextMap.getOrDefault(hostName, new HashSet<>());
                cxtNameSet.add(file.getName());
                hostContextMap.put(hostName, cxtNameSet);
                Context context = new Context(file.getName(),
                        file.getAbsolutePath());
                contextMap.put(file.getName(), context);
            }
        }
    }
}
