package com.home.model.db;

import java.io.IOException;
import java.sql.SQLException;

public class DbInit {

    final SimpleJdbcTemplate source;

    public DbInit(SimpleJdbcTemplate source) {
        this.source = source;
    }

    public void create() throws SQLException {
        String sql = "create table results (\n" +
                "  id IDENTITY primary key,\n" +
                "  expression VARCHAR(100) not null,\n" +
                "  result DOUBLE not null,\n" +
                "  numbers VARCHAR(100) not null,\n" +
                "  date DATE not null \n" +
                ");";
        source.statement(stmt -> {
            stmt.execute(sql);
        });
    }

}
