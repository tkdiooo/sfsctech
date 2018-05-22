package com.sfsctech.support.tools.xml.model;

import java.util.List;
import java.util.Map;

/**
 * Class XmlModel
 *
 * @author 张麒 2016/4/13.
 * @version Description:
 */
public class XmlModel {

    private String label;

    private Map<String, String> element;

    private String content;

    private List<XmlModel> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getElement() {
        return element;
    }

    public void setElement(Map<String, String> element) {
        this.element = element;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<XmlModel> getChildren() {
        return children;
    }

    public void setChildren(List<XmlModel> children) {
        this.children = children;
    }

}
