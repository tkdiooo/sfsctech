package com.sfsctech.common.data.jdbc.model;

/**
 * Class TableModel
 *
 * @author 张麒 2018-3-12.
 * @version Description:
 */
public class TableModel {

    private String name;
    private String isNull;
    private String type;
    private String key;
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
