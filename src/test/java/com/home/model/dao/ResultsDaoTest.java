package com.home.model.dao;

import com.home.model.db.DbInit;
import com.home.model.db.SimpleJdbcTemplate;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static com.home.model.dao.TestData.*;

public class ResultsDaoTest {

    private static final DataSource pool = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", "");
    private static final SimpleJdbcTemplate source = new SimpleJdbcTemplate(pool);
    private final ResultsDao dao = new ResultsDao(source);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final LocalDate date = LocalDate.now();
    private final String currentDate = date.format(dateTimeFormatter);

    @BeforeClass
    public static void createTable() throws SQLException {
        new DbInit(source).create();
    }

    private int getResultsCount() throws SQLException {
        return source.statement(stmt -> {
            ResultSet resultSet = stmt.executeQuery("select count(*) from results ;");
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    @Before
    public void fillTable() throws SQLException {
        dao.saveResults(EXPRESSION1, 2401.0, LISTOFNUMS1);
        dao.saveResults(EXPRESSION2, 127.0, LISTOFNUMS2);
        dao.saveResults(EXPRESSION3, 32.0, LISTOFNUMS3);
        dao.saveResults(EXPRESSION4, 11.0, LISTOFNUMS4);
    }

    @After
    public void clearTable() throws SQLException {
        source.statement(stmt -> {
            stmt.executeUpdate("delete from results ;");
        });
    }

    @Test
    public void saveResults() throws SQLException {
        assertEquals(4, getResultsCount());
        dao.saveResults(EXPRESSION1, 2401.0, LISTOFNUMS1);
        assertEquals(5, getResultsCount());
    }

    @Test
    public void getStatisticsCount() throws SQLException {
        assertEquals("4", dao.getStatistics(new String[] {"1", currentDate}));
    }

    @Test
    public void getStatisticsOperation() throws SQLException {
        assertEquals("3", dao.getStatistics(new String[] {"2", "^"}));
    }

    @Test
    public void getStatisticsOndate() throws SQLException {
        assertEquals(EXPRESSION1 + "<br>" +
                EXPRESSION2 + "<br>" +
                EXPRESSION3 + "<br>" +
                EXPRESSION4 + "<br>",
                dao.getStatistics(new String[] {"3", currentDate}));
    }

    @Test
    public void getStatisticsOnoperation() throws SQLException {
        assertEquals(EXPRESSION1 + "<br>" +
                        EXPRESSION2 + "<br>" +
                        EXPRESSION4 + "<br>",
                dao.getStatistics(new String[] {"4", "^"}));
    }

    @Test
    public void getStatisticsPopular() throws SQLException {
        assertEquals("3.0", dao.getStatistics(new String[] {"5", ""}));
    }
}
