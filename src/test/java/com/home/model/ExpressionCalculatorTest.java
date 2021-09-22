package com.home.model;

import org.junit.Test;

import static com.home.model.dao.TestData.*;
import static org.junit.Assert.assertEquals;

public class ExpressionCalculatorTest {
    ExpressionCalculator calculator;

    @Test
    public void testCalculate1() throws Exception {
        calculator = new ExpressionCalculator(EXPRESSION1);
        assertEquals(POSTFIX1, calculator.fromInfixToPostfix());
        assertEquals(2401, calculator.calculatePostfix(), 0.01);
    }

    @Test
    public void testCalculate2() throws Exception {
        calculator = new ExpressionCalculator(EXPRESSION2);
        assertEquals(POSTFIX2, calculator.fromInfixToPostfix());
        assertEquals(127, calculator.calculatePostfix(), 0.01);
    }

    @Test(expected = Exception.class)
    public void wrongExpression() throws Exception {
        calculator = new ExpressionCalculator(WRONGEXPRESSION1);
        calculator.fromInfixToPostfix();
    }
}
