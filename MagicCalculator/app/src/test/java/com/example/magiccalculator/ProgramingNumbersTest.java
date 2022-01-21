package com.example.magiccalculator;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramingNumbersTest  {
    @Test
    public void changeDecimal_IntoBinary() {
        //given
        String expresion = "25";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.decimalIntoBinary(expresion);

        //then
        String expected = "11001";
        assertEquals(expected, result);
    }

    @Test
    public void changeBinary_intoDecimal() {
        //given
        String expresion = "111010";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.binaryToDecimal(expresion);

        //then
        String expected = "58";
        assertEquals(expected, result);
    }

    @Test
    public void changeBinary_intoDecimal_withWrogExpresion() {
        //given
        String expresion = "1151010";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.binaryToDecimal(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void changeDecimal_intoHexadecimal() {
        //given
        String expresion = "98";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.decimalToHexadecimal(expresion);

        //then
        String expected = "62";
        assertEquals(expected, result);
    }

    @Test
    public void changeDecimal_intoHexadecimal_withOperation() {
        //given
        String expresion = "98+";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.decimalToHexadecimal(expresion);

        //then
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void changeDecimal_intoU2() {
        //given
        String expresion = "-15";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.decimalToU2(expresion);

        //then
        String expected = "11110001";
        assertEquals(expected, result);
    }

    @Test
    public void changeHeksadecimal_intoU2() {
        //given
        String expresion = "A1";

        //when
        ProgramingNumbers programingNumbers = new ProgramingNumbers();
        String result = programingNumbers.decimalToU2(expresion);

        //then
        String expected = "10100001";
        assertEquals(expected, result);
    }


}