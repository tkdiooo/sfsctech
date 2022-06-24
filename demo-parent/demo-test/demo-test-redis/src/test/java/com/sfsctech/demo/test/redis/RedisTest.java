package com.sfsctech.demo.test.redis;

import com.sfsctech.core.base.constants.CacheConstants;
import com.sfsctech.core.cache.factory.CacheFactory;
import com.sfsctech.core.cache.redis.RedisProxy;
import com.sfsctech.demo.test.redis.service.RedisService;
import com.sfsctech.support.common.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class RedisTest
 *
 * @author 张麒 2018-8-13.
 * @version Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Runner.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class RedisTest {

    @Autowired
    private CacheFactory<RedisProxy<String, Object>> factory;

    @Autowired
    private RedisService service;

    final static String DB_URL = "jdbc:oracle:thin:@172.16.146.11:1521/orcl";
    final static String DB_USER = "sfsc";
    final static String DB_PASSWORD = "SFSC";


    @Test
    public void testRedis() throws InterruptedException, SQLException {
//        Properties info = new Properties();
//        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
//        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
//        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
//
//
//        OracleDataSource ods = new OracleDataSource();
//        ods.setURL(DB_URL);
//        ods.setConnectionProperties(info);
//
//
//        OracleConnection connection = (OracleConnection) ods.getConnection();
//        // Get the JDBC driver name and version
//        DatabaseMetaData dbmd = connection.getMetaData();
//        System.out.println("Driver Name: " + dbmd.getDriverName());
//        System.out.println("Driver Version: " + dbmd.getDriverVersion());
//        // Print some connection properties
//        System.out.println("Default Row Prefetch Value is: " +
//                connection.getDefaultRowPrefetch());
//        System.out.println("Database Username is: " + connection.getUserName());
//        System.out.println();
//        // Perform a database operation
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from sfsc.fs_humbas");
//        while (resultSet.next())
//                        System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " ");

        // With AutoCloseable, the connection is closed automatically.
//        try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
//            // Get the JDBC driver name and version
//            DatabaseMetaData dbmd = connection.getMetaData();
//            System.out.println("Driver Name: " + dbmd.getDriverName());
//            System.out.println("Driver Version: " + dbmd.getDriverVersion());
//            // Print some connection properties
//            System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
//            System.out.println("Database Username is: " + connection.getUserName());
//            System.out.println();
//            // Perform a database operation
//            try (Statement statement = connection.createStatement()) {
//                try (ResultSet resultSet = statement.executeQuery("select * from sfsc.fs_humbas")) {
//                    System.out.println("first_name" + "  " + "last_name");
//                    System.out.println("---------------------");
//                    while (resultSet.next())
//                        System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " ");
//                }
//            }
//        }
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            keys.add(UUIDUtil.base58Uuid());
        }

        factory.getExtraCacheClient("test").putTimeOut("abc", "test11", CacheConstants.MilliSecond.Minutes30.getContent());

        keys.forEach(key -> {
            factory.getCacheClient().putTimeOut(key, "abc:" + key, CacheConstants.MilliSecond.Minutes30.getContent());
            System.out.println(factory.get(key).toString());
            System.out.println(factory.getCacheClient().ttl(key));
        });

        keys.forEach(key -> System.out.println(factory.get(key).toString()));
        String key = UUIDUtil.base58Uuid();
        factory.getCacheClient().putTimeOut(key, "abc:" + key, CacheConstants.MilliSecond.Minutes30.getContent());
        System.out.println(factory.get(key).toString());
        System.out.println(factory.getCacheClient().ttl("ukc6twTVirQaCp9l2X5yokjK##%!##@$%|$#$%(^)$}$*{^*+%"));


    }
}
