package com.home.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsRequestCheckerTest {

    StatisticsRequestChecker checker;

    @Test
    public void count() throws Exception {
        checker = new StatisticsRequestChecker("COUNT(21-02-2021)");
        assertArrayEquals( new String[] {"1", "21-02-2021"} , checker.check());
    }

    @Test
    public void operation() throws Exception {
        checker = new StatisticsRequestChecker("OPERATION(+)");
        assertArrayEquals( new String[] {"2", "+"} , checker.check());
    }

    @Test
    public void ondate() throws Exception {
        checker = new StatisticsRequestChecker("ONDATE(22-02-2021)");
        assertArrayEquals( new String[] {"3", "22-02-2021"} , checker.check());
    }

    @Test
    public void onoperation() throws Exception {
        checker = new StatisticsRequestChecker("ONOPERATION(/)");
        assertArrayEquals( new String[] {"4", "/"} , checker.check());
    }

    @Test
    public void popular() throws Exception {
        checker = new StatisticsRequestChecker("POPULAR()");
        assertArrayEquals( new String[] {"5", ""} , checker.check());
    }

    @Test(expected = Exception.class)
    public void wrongRequest1() throws Exception {
        checker = new StatisticsRequestChecker("POPULAR(+)");
        checker.check();
    }

    @Test(expected = Exception.class)
    public void wrongRequest2() throws Exception {
        checker = new StatisticsRequestChecker("ONDATE(22-14-2021)");
        checker.check();
    }
}
