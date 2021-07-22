package mapper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.Context;
import service.HttpServlet;
import service.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * The class is to build <context name : set of wrapper name> map
 *
 * @author jingjiejiang
 * @history Jul 21, 2021
 *
 */
public class ContextWrapperMapper {

    Map<String, Context> contextMap;
    // map: </demo1/lagou : servlet>
    Map<String, HttpServlet> urlServletMap;

    public ContextWrapperMapper(Map<String, Context> contextMap) {
        this.contextMap = contextMap;
        this.urlServletMap = new HashMap<>();
        buildCxtWrapperMap();
    }

    public Map<String, HttpServlet> getUrlServletMap() {
        return urlServletMap;
    }

    public void setUrlServletMap(Map<String, HttpServlet> urlServletMap) {
        this.urlServletMap = urlServletMap;
    }

    private void buildCxtWrapperMap() {

        String cxtName = "";

        for (Map.Entry<String, Context> entry : contextMap.entrySet()) {

            cxtName = entry.getKey();
            // web.xml file path
            String filePath = entry.getValue().getDir();
            loadWrapper(cxtName, filePath);
        }
    }

    private void loadWrapper(String cxtName, String directory) {

        String seperator = System.getProperty("file.separator");

        InputStream resAsStream = null;

        try {
            resAsStream = new FileInputStream(new File(directory + seperator + "web.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resAsStream);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int idx = 0; idx < selectNodes.size(); idx ++) {

                Element element = selectNodes.get(idx);
                // <servlet-name>lagou</servlet-name>
                Element servletNameEle = (Element) element.selectSingleNode("servlet-name");
                String servletMame = servletNameEle.getStringValue();

                // <servlet-class>webapps.demo1.server.LagouServlet</servlet-class>
                Element servletClassEle = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassEle.getStringValue();

                // according to servlet-name to find url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode(
                        "/web-app/servlet-mapping[servlet-name='" + servletMame + "']");

                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                urlServletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
                int test = 0;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
