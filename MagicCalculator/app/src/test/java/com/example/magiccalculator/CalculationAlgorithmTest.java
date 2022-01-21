package com.example.magiccalculator;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculationAlgorithmTest {

    @Test
    public void orderOfExecution_isCorrect() {
        //given
        String expresion = "2+2*2";

        //when
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        String stringResult = calculationAlgorithm.yardAlgrithm(expresion);

        //then
        double expected = 6.0;
        double result = Double.parseDouble(stringResult);
        assertEquals(expected, result,0);
    }

    @Test
    public void numbersWithComma_areCorrect() {
        //given
        String expresion = "2.5+2*2.4";

        //when
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        String stringResult = calculationAlgorithm.yardAlgrithm(expresion);

        //then
        double expected = 7.3;
        double result = Double.parseDouble(stringResult);
        assertEquals(expected, result,0);
    }

    @Test
    public void action_isCorrect() {
        //given
        String expresion = "3+4^4-5";

        //when
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        String stringResult = calculationAlgorithm.yardAlgrithm(expresion);

        //then
        double expected = 254.0;
        double result = Double.parseDouble(stringResult);
        assertEquals(expected, result,0);
    }

    @Test
    public void actionWithMultiplicationAndExponentation_isCorrect() {
        //given
        String expresion = "3*4^4-5";

        //when
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        String stringResult = calculationAlgorithm.yardAlgrithm(expresion);

        //then
        double expected = 763.0;
        double result = Double.parseDouble(stringResult);
        assertEquals(expected, result,0);
    }

}