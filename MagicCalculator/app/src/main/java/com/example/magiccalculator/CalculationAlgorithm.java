package com.example.magiccalculator;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CalculationAlgorithm {

    public String yardAlgrithm(String word) {
        word = word.replace("x","*");
        Stack<Character> stack= new Stack<>();
        List<String> output = new LinkedList<>();
        String newNumber = "";
        String newWord = "";

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if(checkIfLetter(c) && c != '.'){
                output.add(newNumber);
                newNumber="";

                if(c == '('){
                    stack.push(c);
                }else if(c == ')'){
                    while(!stack.isEmpty() && stack.peek() != '('){
                        output.add(String.valueOf(stack.pop()));
                        stack.pop();
                    }
                }else{
                    while(!stack.isEmpty() && getPreference(c) <= getPreference(stack.peek())){
                        output.add(String.valueOf(stack.pop()));
                    }
                    stack.push(c);
                }

            }else{
                newNumber = newNumber+c;
            }

            if(i == word.length()-1){
                output.add(newNumber);
            }
        }
        while (!stack.isEmpty()){
            if(stack.peek() != '('){
                output.add(String.valueOf(stack.pop()));
            }
        }


        newWord = coutWord(output);
        return newWord;
    }

    private String coutWord(List<String> output) {
        Stack<String> stack= new Stack<>();
        stack.push(output.get(0));
        stack.push(output.get(1));
        BigDecimal result = null;
        for (int i = 2; i < output.size(); i++) {
            char c = output.get(i).charAt(0);
            if(checkIfLetter(c)){
                BigDecimal secondNumber = new BigDecimal(stack.pop());
                BigDecimal firstdNumber = new BigDecimal(stack.pop());
                if(c == '-'){
                    result = firstdNumber.subtract(secondNumber);
                }else if(c == '+'){
                    result = firstdNumber.add(secondNumber);
                }else if(c == '*'){
                    result = firstdNumber.multiply(secondNumber);
                }else if(c == '÷'){
                    MathContext mc = new MathContext(5);
                    result = firstdNumber.divide(secondNumber,mc);
                }else if(c == '^'){
                    int secNumber = secondNumber.intValue();
                    result = firstdNumber.pow(secNumber);
                }
                stack.push(String.valueOf(result));
            }else{
                stack.push(output.get(i));
            }
        }
        return stack.pop();
    }

    private int getPreference(char c) {
        if(c == '-' || c == '+'){
            return 1;
        }else if(c == '*' || c == '÷'){
            return 2;
        }else if(c == '^'){
            return 3;
        }else{
            return -1;
        }
    }

    private boolean checkIfLetter(char c) {
        if (Character.isLetterOrDigit(c)) {
            return false;
        }
        else {
            return true;
        }

    }
}
