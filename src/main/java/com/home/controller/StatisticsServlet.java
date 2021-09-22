package com.home.controller;

import com.home.model.StatisticsRequestChecker;
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

@WebServlet(name = "StatisticsServlet", value = "/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String statisticsReq = request.getParameter("statisticsReq");
        
        StatisticsRequestChecker checker = new StatisticsRequestChecker(statisticsReq);
        String[] statOp = null;
        String statistics = null;
        try {
            statOp = checker.check();
        } catch (Exception e) {
            statistics = "Your input is wrong, try again!";
        }
        if (statOp != null) {
            SimpleJdbcTemplate source = (SimpleJdbcTemplate) getServletContext().getAttribute("source");
            ResultsDao dao = new ResultsDao(source);
            try {
                statistics = dao.getStatistics(statOp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("statistics", statistics);
        RequestDispatcher view = request.getRequestDispatcher("result.jsp");
        view.forward(request, response);
    }
}
