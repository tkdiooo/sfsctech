//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Node {
    private String nodeName;
    private Object value;
    private Node fatherNode;
    private boolean isArray = false;
    private boolean needFormat = true;
    private Map<String, Node> childNodes = new LinkedHashMap();

    public Node() {
    }

    public Node getChildNode(String nodeName) {
        return (Node)this.childNodes.get(nodeName);
    }

    public Set<Entry<String, Node>> getChildNodes() {
        return this.childNodes.entrySet();
    }

    public void addChildNode(Node childNode) {
        if (this.childNodes.get(childNode.getNodeName()) != null) {
            throw new RuntimeException("已存在的子节点.");
        } else {
            childNode.setFatherNode(this);
            this.childNodes.put(childNode.getNodeName(), childNode);
        }
    }

    public String getPath() {
        Node currentNode = this;

        StringBuilder stringBuilder;
        for(stringBuilder = new StringBuilder(); currentNode.fatherNode != null; currentNode = currentNode.fatherNode) {
            stringBuilder.insert(0, currentNode.getNodeName()).insert(0, ".");
        }

        String _path = stringBuilder.toString();
        return _path.substring(1, _path.length());
    }

    public boolean isChildNodesExists() {
        return this.childNodes != null && this.childNodes.size() > 0;
    }

    public boolean isNeedFormat() {
        return this.needFormat;
    }

    public void setNeedFormat(boolean needFormat) {
        this.needFormat = needFormat;
    }

    public boolean isNodeArray() {
        return this.isArray;
    }

    public void setNodeArray(boolean isNodeArray) {
        this.isArray = isNodeArray;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getFatherNode() {
        return this.fatherNode;
    }

    void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }

    public String toString() {
        return "Node{nodeName='" + this.nodeName + '\'' + ", value=" + this.value + '}';
    }
}
