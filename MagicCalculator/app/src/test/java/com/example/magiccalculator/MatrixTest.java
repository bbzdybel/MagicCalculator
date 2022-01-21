package com.example.magiccalculator;

import junit.framework.TestCase;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MatrixTest  {
    @Test
    public void giveCorrectMatrixDeterminant_whenRecivedMatrix() {
        //given
        String expresion = "[2,3,1;4,5,2;3,2,1]";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.matrixDeterminant(expresion);

        //then
        String expected = "1.0";
        assertEquals(expected, result);
    }

    @Test
    public void giveEmptyResult_whenRecivedWrongMatrix() {
        //given
        String expresion = "[2,3,1;4,5,2;3,2,1]+";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.matrixDeterminant(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void giveEmptyResult_whenRecivedMissedSpelledMatrix() {
        //given
        String expresion = "[2,3,1;;4,5,2;3,2,1]";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.matrixDeterminant(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void giveEmptyResult_whenRecivedEmptyMatrix() {
        //given
        String expresion = "";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.matrixDeterminant(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void giveCorrectDeterminant_whenRecivedNotSquareMatrix() {
        //given
        String expresion = "[2,3;4,5;6,7";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.matrixDeterminant(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void giveCorrectTransponedMatrix_whenRecivedCorrectString() {
        //given
        String expresion = "[2,3,1;4,5,2;3,2,1]";

        //when
        Matrix matrix = new Matrix();
        String result = matrix.transponedMatrix(expresion);

        //then
        String expected = "[2,4,3;3,5,2;1,2,1]";
        assertEquals(expected, result);
    }

    @Test
    public void giveCorrectValue_whenAddedTwoMatrix() {
        //given
        String expresion = "[1,1,1;1,1,1]+";
        String secondExpresion = "[1,1,1;1,1,1]";

        //when
        Matrix matrix = new Matrix();
        boolean shouldBeTrue = matrix.addMatrix(expresion);
        boolean shouldBeTrueSecondTime = matrix.addMatrix(secondExpresion);
        String result = matrix.countMatrix();

        //then
        String expected = "[2,2,2;2,2,2]";
        assertEquals(expected, result);
    }

    @Test
    public void giveCorrectValue_whenMultiplyTwoMatrix() {
        //given
        String expresion = "[1,2,3;3,4,5;6,7,3]÷";
        String secondExpresion = "[3,4,5]";

        //when
        Matrix matrix = new Matrix();
        boolean shouldBeTrue = matrix.addMatrix(expresion);
        boolean shouldBeTrueSecondTime = matrix.addMatrix(secondExpresion);
        String result = matrix.countMatrix();
        BigDecimal[] results = matrix.getResult();
        double[] doubleResults = new double[results.length];
        for (int i = 0; i < doubleResults.length; i++) {
            doubleResults[i]= results[i].doubleValue();
        }

        //then
        double[] expected = {0.848, -0.215, 0.472};
        assertArrayEquals(expected,doubleResults,0);
    }
}