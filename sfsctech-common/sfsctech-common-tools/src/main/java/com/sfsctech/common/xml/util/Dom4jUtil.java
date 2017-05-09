package com.sfsctech.common.xml.util;

import com.sfsctech.common.util.FileUtil;
import com.sfsctech.common.xml.model.XmlModel;
import com.sfsctech.common.tool.Assert;
import com.sfsctech.common.util.ClassUtil;
import com.sfsctech.common.util.StringUtil;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Class Dom4jUtil
 *
 * @author 张麒 2016/4/13.
 * @version Description:
 */
public class Dom4jUtil {

    public static Document build(File file) throws SAXException, DocumentException {
        SAXReader builder = new SAXReader();
        // 不解析xml中的DOCTYPE
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return builder.read(file);
    }

    public static Document build(InputStream is) throws SAXException, DocumentException {
        SAXReader builder = new SAXReader();
        // 不解析xml中的DOCTYPE
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return builder.read(is);
    }

    /**
     * 创建xml文件
     *
     * @param path  文件保存地址
     * @param model Xml Model
     */
    public static void createXml(String path, XmlModel model) throws IOException {
        Assert.notNull(model);
        Assert.isNotBlank(model.getLabel(), "xml label is null");
        Document document = DocumentHelper.createDocument();
        Element element = document.addElement(model.getLabel());
        addElement(element, model);
        FileOutputStream fos = null;
        XMLWriter xmlWriter = null;
        try {
            fos = new FileOutputStream(path);
            xmlWriter = new XMLWriter(fos);
            xmlWriter.write(document);
            xmlWriter.flush();
        } finally {
            close(xmlWriter, fos);
            close(document, element);
        }

    }

    /**
     * 解析XML
     *
     * @param path Xml Address
     * @return XmlModel
     */
    public static XmlModel readXML(String path) throws DocumentException {
        Assert.isNotBlank(path, "文件路径为空");
        SAXReader saxReader = new SAXReader();
        Document document = null;
        Element root = null;
        XmlModel model = new XmlModel();
        try {
            document = saxReader.read(new File(path));
            root = document.getRootElement();
            readElement(root, model);
        } finally {
            close(document, root);
        }
        return model;
    }

    /**
     * 解析XML，转换为Class对象
     *
     * @param xml Xml Content
     * @param cls 要转换的对象类
     * @param <T> 范型
     * @return Class Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T readXML(String xml, Class<T> cls) throws Exception {
        Assert.isNotBlank(xml, "xml为空");
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        List<Element> nodes = root.elements();
        Field[] fields = cls.getDeclaredFields();
        try {
            T t = cls.newInstance();
            for (Element node : nodes) {
                for (Field field : fields) {
                    if (field.getName().equals(node.getName())) {
                        field.setAccessible(true);
                        field.set(t, ClassUtil.convertType(node.getText(), field.getType()));
                    }
                }
            }
            return t;
        } finally {
            close(document, root);
        }
    }

    private static void addElement(Element element, XmlModel model) {
        if (StringUtil.isNotBlank(model.getContent()))
            element.setText(model.getContent());
        else if (null != model.getElement()) {
            for (String key : model.getElement().keySet()) {
                element.addAttribute(key, model.getElement().get(key));
            }
        }
        if (null != model.getChildren())
            for (XmlModel children : model.getChildren()) {
                Assert.isNotBlank(children.getLabel(), "xml label is null");
                addElement(element.addElement(children.getLabel()), children);
            }
    }

    @SuppressWarnings("unchecked")
    private static void readElement(Element element, XmlModel model) {
        // 解析标签
        model.setLabel(element.getName());
        // 解析属性
        if (element.attributeCount() > 0) {
            model.setElement(new HashMap<>());
            for (int i = 0; i < element.attributeCount(); i++) {
                Attribute attr = element.attribute(i);
                model.getElement().put(attr.getName(), attr.getText());
            }
        }
        // 解析内容
        if (StringUtil.isNotBlank(element.getText())) {
            model.setContent(element.getText());
        }
        // 解析子节点
        else {
            model.setChildren(new ArrayList<>());
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element node = (Element) iterator.next();
                XmlModel childXml = new XmlModel();
                readElement(node, childXml);
                model.getChildren().add(childXml);
            }
        }

    }

    private static void close(XMLWriter xmlWriter, FileOutputStream fos) throws IOException {
        if (null != xmlWriter) xmlWriter.close();
        if (null != fos) fos.close();
    }

    private static void close(Document document, Element root) {
        if (root != null) root.clearContent();
        if (document != null) document.clearContent();
    }

    /**
     * xml文档写入
     *
     * @param document xml文档
     * @param filePath 文件路径
     * @return File
     */
    public static File writeDom4jToFile(Document document, String filePath) throws IOException {
        Assert.isNotBlank(filePath, "文件路径为空");
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        FileOutputStream fos = null;
        XMLWriter xmlWriter = null;
        try {
            fos = new FileOutputStream(filePath);
            xmlWriter = new XMLWriter(fos, format);
            xmlWriter.write(document);
            xmlWriter.flush();
        } finally {
            if (null != xmlWriter)
                xmlWriter.close();
            FileUtil.close(fos);
        }
        return new File(filePath);
    }
}
