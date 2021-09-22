package com.home.model.dao;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public final static String EXPRESSION1 = "(-7*8+9)-(9/4.5))^2";
    public final static String EXPRESSION2 = "(18-3)+2^4*7";
    public final static String EXPRESSION3 = "14/7+(12-2)*3";
    public final static String EXPRESSION4 = "23+15-3^3";
    public final static String WRONGEXPRESSION1 = "(-7*8+9)-(9/4.5))^2=";

    public final static List<String> POSTFIX1 = new ArrayList<>(List.of("0", "7", "8", "*", "-", "9", "+", "9", "4.5", "/", "-", "2", "^"));
    public final static List<String> POSTFIX2 = new ArrayList<>(List.of("18", "3", "-", "2", "4", "^", "7", "*", "+"));

    public final static List<String> LISTOFNUMS1 = new ArrayList<>(List.of("7", "8", "9", "9", "4.5", "2"));
    public final static List<String> LISTOFNUMS2 = new ArrayList<>(List.of("18", "3", "2", "4", "7"));
    public final static List<String> LISTOFNUMS3 = new ArrayList<>(List.of("14", "7", "12", "2", "3"));
    public final static List<String> LISTOFNUMS4 = new ArrayList<>(List.of("23", "15", "3", "3"));

}
