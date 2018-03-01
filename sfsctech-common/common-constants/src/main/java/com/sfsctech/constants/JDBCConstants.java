package com.sfsctech.constants;

import com.sfsctech.constants.inf.IEnum;

/**
 * Class JDBCConstants
 *
 * @author 张麒 2018-2-28.
 * @version Description:
 */
public class JDBCConstants {

    public enum Driver implements IEnum<String, DataSource> {

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
        public DataSource getContent() {
            return this.dataSource;
        }

        public static DataSource getDataSource(String driver) {
            return IEnum.findValue(values(), driver);
        }
    }

    public enum DataSource {

        /**
         * MySQL驱动<br>
         * MySQL(http://www.mysql.com)mysql-connector-java-2.0.14-bin.jar<br>
         * Class.forName( "org.gjt.mm.mysql.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:mysql://IP:Port/DatabaseName", sUsr, sPwd );<br>
         */
        MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%d/%s"),

        /**
         * PostgreSQL驱动<br>
         * PostgreSQL(http://www.de.postgresql.org)pgjdbc2.jar<br>
         * Class.forName("org.postgresql.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:postgresql://IP:Port/DatabaseName", sUsr, sPwd );
         */
        PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%s:%d/%s"),

        /**
         * Oracle驱动<br>
         * Oracle(http://www.oracle.com/ip/deploy/database/oracle9i/)classes12.zip<br>
         * Class.forName( "oracle.jdbc.driver.OracleDriver" );<br>
         * cn = DriverManager.getConnection("jdbc:oracle:thin:@//IP:Port/Orcl", sUsr, sPwd );
         */
        Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//%s:%d/%s"),

        /**
         * Sybase驱动<br>
         * Sybase(http://jtds.sourceforge.net)jconn2.jar<br>
         * Class.forName("com.sybase.jdbc2.jdbc.SybDriver" );<br>
         * cn = DriverManager.getConnection("jdbc:sybase:Tds:IP:Port", sUsr, sPwd );
         */
        Sybase("com.sybase.jdbc2.jdbc.SybDriver", "jdbc:sybase:Tds:%s:%d"),

        /**
         * SQLServerJTDS驱动<br>
         * Microsoft SQLServer(http://jtds.sourceforge.net)<br>
         * Class.forName("net.sourceforge.jtds.jdbc.Driver" );<br>
         * cn = DriverManager.getConnection("jdbc:jtds:sqlserver://IP:Port/DatabaseName", sUsr, sPwd );
         */
        SQLServerJTDS("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://%s:%d/%s"),

        /**
         * SQLServerJDBC驱动<br>
         * Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );<br>
         * Connection cn = DriverManager.getConnection("jdbc:sqlserver://IP:Port;DatabaseName=master", sUsr, sPwd );
         */
        SQLServerJDBC("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%d;DatabaseName=%s"),

        /**
         * DB2驱动<br>
         * Class.forName("com.ibm.db2.jdbc.net.DB2Driver");<br>
         * cn = DriverManager.getConnection("jdbc:db2://IP:Port/DatabaseName", sUsr, sPwd );
         */
        DB2("com.ibm.db2.jdbc.net.DB2Driver", "jdbc:db2://%s:%d/%s");

        private final String driver;
        private String url;

        DataSource(String driver, String url) {
            this.driver = driver;
            this.url = url;
        }

        public String getDriver() {
            return this.driver;
        }

        public String getUrl() {
            return this.url;
        }
    }
}
