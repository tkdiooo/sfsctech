package com.sfsctech.database.jdbc;

import com.sfsctech.database.model.DBConfigModel;
import com.sfsctech.database.model.TableModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * Class JDBCService
 *
 * @author 张麒 2018-3-12.
 * @version Description:
 */
public class JdbcService {

    public static List<String> showDatabases(DBConfigModel config) {
        return JdbcPool.getJdbcTemplate(config).queryForList(config.getDataSource().showDatabases(), String.class);
    }

    public static List<String> showTables(DBConfigModel config) {
        return JdbcPool.getJdbcTemplate(config).queryForList(config.getDataSource().showTables(), String.class);
    }

    public static List<TableModel> descTable(DBConfigModel config, String tableName) {
        return JdbcPool.getJdbcTemplate(config).query(config.descTable(tableName), new BeanPropertyRowMapper<>(TableModel.class));
    }
}
