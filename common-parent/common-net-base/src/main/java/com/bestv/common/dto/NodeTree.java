//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bestv.common.dto;

public class NodeTree {
    private Node headNode = new Node();

    public NodeTree() {
    }

    public Node getNode(String path) {
        String[] paths = path.split("\\.");
        Node currentNode = this.headNode;

        for(int i = 0; i < paths.length; ++i) {
            currentNode = currentNode.getChildNode(paths[i]);
            if (currentNode == null) {
                return null;
            }
        }

        return currentNode;
    }

    public void addNode(Node node) {
        String[] paths = node.getNodeName().split("\\.");
        if (paths.length == 0) {
            throw new RuntimeException("非法的节点名!");
        } else {
            if (paths.length == 1) {
                this.headNode.addChildNode(node);
            } else {
                Node currentNode = this.headNode;

                for(int i = 0; i < paths.length; ++i) {
                    if (currentNode.getChildNode(paths[i]) == null) {
                        Node childNode = new Node();
                        childNode.setNodeName(paths[i]);
                        if (i == paths.length - 1) {
                            childNode.setValue(node.getValue());
                        }

                        currentNode.addChildNode(childNode);
                    }

                    currentNode = currentNode.getChildNode(paths[i]);
                }
            }

        }
    }

    public Node getHeadNode() {
        return this.headNode;
    }
}
