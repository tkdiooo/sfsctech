package com.sfsctech.database.model;

import com.sfsctech.constants.JDBCConstants;

/**
 * Class DBConfigModel
 *
 * @author 张麒 2018-2-28.
 * @version Description:
 */
public class DBConfigModel {

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
        return String.format(getDataSource().getUrl(), this.serverip, this.port, this.database);
    }
}
