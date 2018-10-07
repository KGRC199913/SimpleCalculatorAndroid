package com.neko.simplecalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Calculator {
    private Stack<Double> doubleStack = new Stack<>();
    private String RPN;

    Calculator(String equation) {
        Stack<String> stack = new Stack<>();
        String[] infixData = equation.split(" +");

        StringBuilder RPNBuilder = new StringBuilder();

        for (String str : infixData) {
            if (android.text.TextUtils.isDigitsOnly(str)) {
                RPNBuilder.append(str).append(" ");
            } else {
                if (stack.empty())
                    stack.push(str);
                else {
                    while (!stack.empty()) {
                        if (OpPrio(stack.peek().charAt(0)) <= OpPrio(str.charAt(0))){
                            RPNBuilder.append(stack.pop()).append(" ");
                        } else
                            break;
                    }
                    stack.push(str);
                }
            }
        }

        while (!stack.empty()) {
            RPNBuilder.append(stack.pop()).append(" ");
        }

        RPN = RPNBuilder.toString();
    }

    private double calc(String op, double first, double second) {
        switch (op){
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                return first / second;
            default:
                return 0;
        }
    }

    private String calculate() {
        double result = 0;
        ArrayList<String> RPNData = new ArrayList<>(Arrays.asList(RPN.split(" +")));
        RPNData.remove("");

        if (RPNData.size() == 1) {
            return RPNData.get(0) + ".0";
        }

        for (String str : RPNData) {
            if (android.text.TextUtils.isDigitsOnly(str)){
                doubleStack.push(Double.parseDouble(str));
            } else {
                doubleStack.push(calc(str, doubleStack.pop(), doubleStack.pop()));
                result += doubleStack.peek();
            }
        }

        return Double.toString(result);
    }

    public String getResult() {
        String result = calculate();

        String[] parts = result.split("[.]+");
        if (parts[1].equals("0")) {
            return parts[0];
        }
        return result;
    }

    private int OpPrio(char op) {
        if (op == '+' || op == '-')
            return 3;
        if (op == '*' || op == '/')
            return 2;
        return 1;
    }
}
