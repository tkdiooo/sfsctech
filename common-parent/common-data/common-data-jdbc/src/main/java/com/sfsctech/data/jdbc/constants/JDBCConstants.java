package com.sfsctech.data.jdbc.constants;

import com.sfsctech.core.base.enums.BaseEnum;

/**
 * Class JDBCConstants
 *
 * @author 张麒 2018-2-28.
 * @version Description:
 */
public class JDBCConstants {

    public enum Driver implements BaseEnum<String, DataSource> {

        /**
         * MySQL驱动
         */
        MySQL("MySQL", DataSource.MySQL),

        /**
         * PostgreSQL驱动
         */
        PostgreSQL("PostgreSQL", DataSource.PostgreSQL),

        /**
         * Oracle驱动
         */
        Oracle("Oracle", DataSource.Oracle),

        /**
         * Sybase驱动
         */
        Sybase("Sybase", DataSource.Sybase),

        /**
         * SQLServerJTDS驱动
         */
        SQLServerJTDS("SQLServerJTDS", DataSource.SQLServerJTDS),

        /**
         * SQLServerJDBC驱动
         */
        SQLServerJDBC("SQLServerJDBC", DataSource.SQLServerJDBC),

        /**
         * DB2驱动
         */
        DB2("DB2", DataSource.DB2);

        private final String driver;
        private final DataSource dataSource;


        Driver(String driver, DataSource dataSource) {
            this.driver = driver;
            this.dataSource = dataSource;
        }

        @Override
        public String getCode() {
            return this.driver;
        }

        @Override
        public DataSource getDescription() {
            return this.dataSource;
        }

        public static DataSource getDataSource(String driver) {
            return BaseEnum.findValue(values(), driver);
        }
    }

    public enum DataSource {

        /**
         * MySQL驱动<br>
         * MySQL(http://www.mysql.com)mysql-connector-java-2.0.14-bin.jar<br>
         * Class.forName( "org.gjt.mm.mysql.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:mysql://IP:Port/DatabaseName", sUsr, sPwd );<br>
         */
        MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%d/%s", "show databases", "show tables", "SELECT COLUMN_NAME AS name, IS_NULLABLE AS isNull, COLUMN_TYPE AS type, COLUMN_KEY AS 'key', COLUMN_COMMENT AS 'comment' FROM information_schema. COLUMNS WHERE table_schema = '%s' AND table_name = '%s'"),

        /**
         * PostgreSQL驱动<br>
         * PostgreSQL(http://www.de.postgresql.org)pgjdbc2.jar<br>
         * Class.forName("org.postgresql.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:postgresql://IP:Port/DatabaseName", sUsr, sPwd );
         */
        PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%s:%d/%s", "", "", ""),

        /**
         * Oracle驱动<br>
         * Oracle(http://www.oracle.com/ip/deploy/database/oracle9i/)classes12.zip<br>
         * Class.forName( "oracle.jdbc.driver.OracleDriver" );<br>
         * cn = DriverManager.getConnection("jdbc:oracle:thin:@//IP:Port/Orcl", sUsr, sPwd );
         */
        Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//%s:%d/%s", "", "", ""),

        /**
         * Sybase驱动<br>
         * Sybase(http://jtds.sourceforge.net)jconn2.jar<br>
         * Class.forName("com.sybase.jdbc2.jdbc.SybDriver" );<br>
         * cn = DriverManager.getConnection("jdbc:sybase:Tds:IP:Port", sUsr, sPwd );
         */
        Sybase("com.sybase.jdbc2.jdbc.SybDriver", "jdbc:sybase:Tds:%s:%d", "", "", ""),

        /**
         * SQLServerJTDS驱动<br>
         * Microsoft SQLServer(http://jtds.sourceforge.net)<br>
         * Class.forName("net.sourceforge.jtds.jdbc.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:jtds:sqlserver://IP:Port/DatabaseName", sUsr, sPwd );
         */
        SQLServerJTDS("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://%s:%d/%s", "", "", ""),

        /**
         * SQLServerJDBC驱动<br>
         * Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );<br>
         * Connection cn = DriverManager.getConnection("jdbc:sqlserver://IP:Port;DatabaseName=master", sUsr, sPwd );
         */
        SQLServerJDBC("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%d;DatabaseName=%s", "", "", ""),

        /**
         * DB2驱动<br>
         * Class.forName("com.ibm.db2.jdbc.net.DB2Driver");<br>
         * cn = DriverManager.getConnection("jdbc:db2://IP:Port/DatabaseName", sUsr, sPwd );
         */
        DB2("com.ibm.db2.jdbc.net.DB2Driver", "jdbc:db2://%s:%d/%s", "", "", "");

        private final String driver;
        private String url;
        private String showDatabases;
        private String showTables;
        private String descTable;

        DataSource(String driver, String url, String showDatabases, String showTables, String descTable) {
            this.driver = driver;
            this.url = url;
            this.showDatabases = showDatabases;
            this.showTables = showTables;
            this.descTable = descTable;
        }

        public String getDriver() {
            return this.driver;
        }

        public String getUrl() {
            return this.url;
        }

        public String showDatabases() {
            return showDatabases;
        }

        public String showTables() {
            return showTables;
        }

        public String descTable() {
            return descTable;
        }
    }
}
