package com.home.model.dao;

import com.home.model.db.SimpleJdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultsDao {

    private final SimpleJdbcTemplate source;

    public ResultsDao(SimpleJdbcTemplate source) {
        this.source = source;
    }

    public void saveResults(String expression, Double result, List<String> postfix) throws SQLException {

        postfix.removeIf(s -> !Character.isDigit(s.charAt(0)));
        String numbers = String.join("\n", postfix);

        String sql = "insert into results (expression, result, numbers, date)\n" +
                "  values (?, ?, ?, ?);" ;

        source.preparedStatement(sql, prepStmt -> {
            prepStmt.setString(1, expression);
            prepStmt.setDouble(2, result);
            prepStmt.setString(3, numbers);
            prepStmt.setDate(4, Date.valueOf(LocalDate.now()));
            prepStmt.executeUpdate();
        });
    }

    public String getStatistics(String[] type) throws SQLException {

        char operation = type[0].charAt(0);
        switch (operation) {
            case '1':
                return count(type[1]);
            case '2':
                return operation(type[1]);
            case '3':
                return ondate(type[1]);
            case '4':
                return onoperation(type[1]);
            case '5':
                return popular();
            default:
                return "Error";
        }
    }

    private String count(String date) throws SQLException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDate = LocalDate.parse(date, dateTimeFormatter);

        String sql = "select count(*) from results \n" +
                "  where date = ? ;" ;

        return source.preparedStatement(sql, prepStmt -> {
            prepStmt.setDate(1, Date.valueOf(formattedDate));
            ResultSet resultSet = prepStmt.executeQuery();
            resultSet.next();
            return String.valueOf(resultSet.getInt(1));
        });
    }

    private String operation(String op) throws SQLException {

        String sql = "select count(*) from results \n" +
                "  where expression like ? ;" ;

        return source.preparedStatement(sql, prepStmt -> {
            prepStmt.setString(1, "%" + op + "%");
            ResultSet resultSet = prepStmt.executeQuery();
            resultSet.next();
            return String.valueOf(resultSet.getInt(1));
        });
    }

    private String ondate(String date) throws SQLException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDate = LocalDate.parse(date, dateTimeFormatter);

        String sql = "select expression from results \n" +
                "  where date = ? ;" ;

        return source.preparedStatement(sql, prepStmt -> {
            prepStmt.setDate(1, Date.valueOf(formattedDate));
            ResultSet resultSet = prepStmt.executeQuery();
            StringBuilder list = new StringBuilder();
            while (resultSet.next()) {
                list.append(resultSet.getString(1));
                list.append("<br>");
            }
            return list.toString();
        });
    }

    private String onoperation(String op) throws SQLException {

        String sql = "select expression from results \n" +
                "  where expression like ? ;" ;

        return source.preparedStatement(sql, prepStmt -> {
            prepStmt.setString(1, "%" + op + "%");
            ResultSet resultSet = prepStmt.executeQuery();
            StringBuilder list = new StringBuilder();
            while (resultSet.next()) {
                list.append(resultSet.getString(1));
                list.append("<br>");
            }
            return list.toString();
        });
    }

    private String popular() throws SQLException {

        return source.statement(stmt -> {
            ResultSet resultSet = stmt.executeQuery("select numbers from results ;");
            StringBuilder stringOfNums = new StringBuilder();
            while (resultSet.next()) {
                stringOfNums.append(resultSet.getString(1));
                stringOfNums.append("\n");
            }
            List<Double> numbers = stringOfNums.toString().lines().map(Double::parseDouble).collect(Collectors.toList());
            int max = 0;
            double result = 0;
            Map<Double, Integer> map = new HashMap<>();
            for (Double num: numbers) {
                if (map.containsKey(num)) {
                    map.put(num, map.get(num) + 1);
                } else {
                    map.put(num, 1);
                }
                if (map.get(num) > max) {
                    max = map.get(num);
                    result = num;
                }
            }
            return String.valueOf(result);
        });
    }

}
