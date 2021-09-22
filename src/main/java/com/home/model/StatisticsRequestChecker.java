package com.home.model;

public class StatisticsRequestChecker {

    private final String request;

    public StatisticsRequestChecker(String request) {
        this.request = request;
    }

    public String[] check() throws Exception {

        String[] operation;

        if (request.matches("COUNT[(](((0[1-9])|(1\\d)|(2\\d)|(3[01]))-((0[1-9])|(1[012]))-202[1-9])[)]")) {
            operation = new String[] {"1", request.substring(6, 16)};
        }
        else if (request.matches("OPERATION[(][+-/*^][)]")) {
            operation = new String[] {"2", request.substring(10, 11)};
        }
        else if (request.matches("ONDATE[(](((0[1-9])|(1\\d)|(2\\d)|(3[01]))-((0[1-9])|(1[012]))-202[1-9])[)]")) {
            operation = new String[] {"3", request.substring(7, 17)};
        }
        else if (request.matches("ONOPERATION[(][+-/*^][)]")) {
            operation = new String[] {"4", request.substring(12, 13)};
        }
        else if (request.matches("POPULAR[(][)]")) {
            operation = new String[] {"5", ""};
        }
        else { throw new Exception();}

        return operation;
    }
}
