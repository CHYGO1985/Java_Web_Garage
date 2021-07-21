package mapper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pojo.Context;

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

    Map<String, String> contextWrapperMap;
    Map<String, Context> contextMap;

    public ContextWrapperMapper(Map<String, Context> contextMap) {
        this.contextWrapperMap = new HashMap<>();
        this.contextMap = contextMap;
        buildCxtWrapperMap();
    }

    private void buildCxtWrapperMap() {

        String cxtName = "";

        for (Map.Entry<String, Context> entry : contextMap.entrySet()) {

            cxtName = entry.getKey();
            // web.xml file path
            String filePath = entry.getValue().getDir();
            loadWrapper(filePath);
        }
    }

    private void loadWrapper(String directory) {

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
                Element servletNameEle = (Element) element.selectSingleNode("servlet-name");
                String servletMame = servletNameEle.getStringValue();

            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
