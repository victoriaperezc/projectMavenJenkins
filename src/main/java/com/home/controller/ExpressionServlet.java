package com.home.controller;

import com.home.model.ExpressionCalculator;
import com.home.model.dao.ResultsDao;
import com.home.model.db.SimpleJdbcTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ExpressionServlet", value = "/ExpressionServlet")
public class ExpressionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String expression = request.getParameter("expression");

        ExpressionCalculator calc = new ExpressionCalculator(expression);
        List<String> postfix = null;
        double result = 0;
        String answer;
        try {
            postfix = calc.fromInfixToPostfix();
            result = calc.calculatePostfix();
            answer = String.valueOf(result);
        } catch (Exception e) {
            answer = "Your input is wrong, try again!";
        }
        if (answer.charAt(0) != 'Y') {
            SimpleJdbcTemplate source = (SimpleJdbcTemplate) getServletContext().getAttribute("source");
            ResultsDao dao = new ResultsDao(source);
            try {
                dao.saveResults(expression, result, postfix);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("answer", answer);
        RequestDispatcher view = request.getRequestDispatcher("result.jsp");
        view.forward(request, response);
    }
}
