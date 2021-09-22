package com.home.controller;

import com.home.model.db.DbInit;
import com.home.model.db.SimpleJdbcTemplate;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSource pool = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", "");
        SimpleJdbcTemplate source = new SimpleJdbcTemplate(pool);
        try {
            new DbInit(source).create();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("source", source);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing to do here
    }
}
