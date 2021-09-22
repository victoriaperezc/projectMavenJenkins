package com.home.model;

import java.util.*;

public class ExpressionCalculator {
    private final Deque<String> stackOps = new ArrayDeque<>();
    private final List<String> postfix = new ArrayList<>();
    private final String infix;

    public ExpressionCalculator (String infix) {
        this.infix = infix;
    }

    public List<String> fromInfixToPostfix() throws Exception {

        StringBuilder temp = new StringBuilder();
        char ch;

        if (infix.matches(".*[^\\d.+*/^()-].*")) {
            throw new Exception("Wrong Input");
        }

        for (int i = 0; i < infix.length(); i++) {
            ch = infix.charAt(i);
            if (!Character.isDigit(ch) && ch != '.' && temp.length() > 0) {
                postfix.add(temp.toString());
                temp.delete(0, temp.length());
            }
            if (ch == '-' && ( i == 0 || infix.charAt(i-1) == '(')) {
                postfix.add("0");
            }
            switch (ch) {
                case '+':
                case '-':
                    putOperator(ch, 1);
                    break;
                case '*':
                case '/':
                    putOperator(ch, 2);
                    break;
                case '^':
                    putOperator(ch, 3);
                    break;
                case '(':
                    stackOps.push(String.valueOf(ch));
                    break;
                case ')':
                    putParen();
                    break;
                default:
                    temp.append(ch);
            }
        }
        if (temp.length() > 0) {
            postfix.add(temp.toString());
        }
        while (stackOps.size() > 0) {
            postfix.add(stackOps.pop());
        }
        return new ArrayList<>(postfix);
    }

    private void putOperator(char opThis, int priorThis) {

        char opTop;

        while (stackOps.size() > 0) {
            opTop = stackOps.pop().charAt(0);
            if (opTop == '(') {
                stackOps.push(String.valueOf(opTop));
                break;
            } else {
                int priorTop = 1;

                switch (opTop) {
                    case '+':
                    case '-':
                        priorTop = 1;
                        break;
                    case '*':
                    case '/':
                        priorTop = 2;
                        break;
                    case '^':
                        priorTop = 3;
                        break;
                    default:
                }
                if (priorTop < priorThis) {
                    stackOps.push(String.valueOf(opTop));
                    break;
                } else {
                    postfix.add(String.valueOf(opTop));
                }
            }
        }
        stackOps.push(String.valueOf(opThis));
    }

    private void putParen() {

        while (stackOps.size() > 0) {
            char ch = stackOps.pop().charAt(0);
            if (ch == '(')
                break;
            else
                postfix.add(String.valueOf(ch));
        }
    }

    public double calculatePostfix() throws NoSuchElementException {

        Deque<Double> stackNums = new ArrayDeque<>();
        double a, b, r = 0;

        for (String element: postfix) {
            if (!Character.isDigit(element.charAt(0))) {
                b = stackNums.pop();
                a = stackNums.pop();
                switch (element.charAt(0)) {
                    case '+':
                        r = a + b;
                        break;
                    case '-':
                        r = a - b;
                        break;
                    case '/':
                        r = a / b;
                        break;
                    case '*':
                        r = a * b;
                        break;
                    case '^':
                        r = Math.pow(a, b);
                        break;
                    default:
                }
            } else {
                r = Double.parseDouble(element);
            }
            stackNums.push(r);
        }
        return stackNums.pop();
    }
}
