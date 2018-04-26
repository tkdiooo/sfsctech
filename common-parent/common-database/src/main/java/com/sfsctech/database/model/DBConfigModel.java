package com.sfsctech.database.model;

import com.sfsctech.common.util.StringUtil;
import com.sfsctech.constants.JDBCConstants;
import com.sfsctech.constants.LabelConstants;

import java.io.Serializable;

/**
 * Class DBConfigModel
 *
 * @author 张麒 2018-2-28.
 * @version Description:
 */
public class DBConfigModel implements Serializable {

    private static final long serialVersionUID = 105523604446308585L;

    private String driver;
    private String serverip;
    private Integer port;
    private String database;
    private String username;
    private String password;

    public DBConfigModel(String driver, String serverip, Integer port, String database, String username, String password) {
        this.driver = driver;
        this.serverip = serverip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JDBCConstants.DataSource getDataSource() {
        return JDBCConstants.Driver.getDataSource(this.driver);
    }

    public String getUrl() {
        if (StringUtil.isNotBlank(this.database)) {
            return String.format(getDataSource().getUrl(), this.serverip, this.port, this.database);
        } else {
            return String.format(StringUtil.substringBeforeLast(getDataSource().getUrl(), LabelConstants.FORWARD_SLASH), this.serverip, this.port);
        }
    }

    public String descTable(String tableName) {
        return String.format(getDataSource().descTable(), this.database, tableName);
    }
}
