package com.example.magiccalculator;

import java.math.BigDecimal;
import java.math.MathContext;

public class Matrix {
    private String word;
    private final static char [] charakters = {'(', '^' ,')','x','+','÷'};
    private BigDecimal [][] firstMatrix;
    private BigDecimal [][] secondMatrix;
    private BigDecimal [][] resultMatrix;
    private BigDecimal [] vector;
    private boolean addedFirstMatrix = true;
    private String operation;
    private boolean firstMatrixSize = true;
    private boolean secondMatrixSize = false;
    private BigDecimal[] result;


    public boolean addMatrix(String word){
        this.word = word;
        if(checkIfMatrixIsCorrect()){
            if(createNewMatrx()) {
                secondMatrixSize = true;
                return true;
            }else{
                setFlags();
                return false;
            }
        }
        setFlags();
        return false;
    }

    private boolean checkIfMatrixIsCorrect() {
        if(addedFirstMatrix){
            operation = word.substring(word.length()-1,word.length());
            word = word.substring(0,word.length()-1);
        }
        if(word.contains("[") && word.contains("]")){
            word = word.substring(1,word.length()-1);
            if(word.contains("[") || word.contains("]")) {
                return false;
            }
            if(checkIfOnlyNumbers()){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean createNewMatrx() {
        word = word.replace(" ","");
        try {
            if (word.contains(";")) {
                String[] row = word.split(";");
                for (int i = 0; i < row.length; i++) {
                    String[] column = row[i].split(",");
                    if (firstMatrixSize) {
                        firstMatrixSize = false;
                        firstMatrix = new BigDecimal[row.length][column.length];
                    }
                    if (secondMatrixSize) {
                        secondMatrixSize = false;
                        secondMatrix = new BigDecimal[row.length][column.length];
                    }
                    for (int j = 0; j < column.length; j++) {
                        if (addedFirstMatrix) {
                            firstMatrix[i][j] = new BigDecimal(column[j]);
                        } else {
                            secondMatrix[i][j] = new BigDecimal(column[j]);
                        }
                    }
                }
                addedFirstMatrix = false;
            } else {
                try {
                    String[] column = word.split(",");
                    vector = new BigDecimal[column.length];
                    for (int i = 0; i < column.length; i++) {
                        vector[i] = new BigDecimal(column[i]);
                    }
                }catch (Exception e){
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean checkIfOnlyNumbers(){
        char [] letters = word.toCharArray();
        for (char l : letters) {
            for (char c : charakters) {
                if(c == l){
                    return false;
                }
            }
        }
        return true;
    }

    public String countMatrix(){
        if(operation.equals("+")){
            String newMatrix = matrixAggregation();
            setFlags();
            return newMatrix;
        }else if(operation.equals("-")){
            String newMatrix = matrixSubstracking();
            setFlags();
            return newMatrix;
        }else if(operation.equals("x")){
            String newMatrix = matrixMultiply();
            setFlags();
            return newMatrix;
        }else if(operation.equals("÷")){
            String newMatrix = matrixGauss();
            setFlags();
            return newMatrix;
        }
        return "";
    }

    private void setFlags() {
        addedFirstMatrix = true;
        firstMatrixSize = true;
        secondMatrixSize = false;
    }

    private String matrixGauss() {
        String newWord ="";
        BigDecimal temp;
        result = new BigDecimal[vector.length];
        for (int i = 0; i < vector.length; i++) {
            int maxInRow = i;
            for (int j = maxInRow+1; j < vector.length; j++) {
                double first = firstMatrix[j][i].doubleValue();
                double second = firstMatrix[maxInRow][i].doubleValue();
                if(Math.abs(first) > Math.abs(second)){
                    maxInRow = j;
                }
            }
            for (int j = 0; j < firstMatrix[0].length; j++) {
                temp = firstMatrix[i][j];
                firstMatrix[i][j] = firstMatrix[maxInRow][j];
                firstMatrix[maxInRow][j] = temp;
            }
            temp = vector[i];
            vector[i] = vector[maxInRow];
            vector[maxInRow] = temp;

            for (int j = i+1; j < vector.length; j++) {
                MathContext mc = new MathContext(5);
                temp = firstMatrix[j][i].divide(firstMatrix[i][i],mc);
                vector[j] = vector[j].subtract(temp.multiply(vector[i]));
            }
        }
        for (int i = vector.length-1; i >= 0; i--) {
            temp = BigDecimal.valueOf(0);
            for (int j = i+1; j < vector.length; j++) {
                temp = temp.add( firstMatrix[i][j].multiply(result[j]));
            }
            MathContext mc = new MathContext(3);
            result[i] = (vector[i].subtract(temp)).divide(firstMatrix[i][i],mc);
        }
        return newWord;
    }

    private String matrixMultiply() {
        String newWord ="[";
        resultMatrix = new BigDecimal[firstMatrix.length][secondMatrix[0].length];
        if (secondMatrix.length==firstMatrix[0].length) {
            for (int i=0;i<resultMatrix.length;i++) {
                for (int j=0;j<resultMatrix[0].length;j++) {
                    resultMatrix[i][j]= BigDecimal.valueOf(0);
                    for (int x=0;x<firstMatrix[0].length;x++) {
                        resultMatrix[i][j]=resultMatrix[i][j].add(firstMatrix[i][x].multiply(secondMatrix[x][j]));
                    }
                    newWord = newWord + String.valueOf(resultMatrix[i][j]) + ",";
                }
                newWord = newWord.substring(0,newWord.length()-1) + ";";
            }
            newWord = newWord.substring(0,newWord.length()-1) + "]";
            return newWord;
        }
        return "";
    }

    private String matrixSubstracking() {
        String newWord ="[";
        resultMatrix = new BigDecimal[firstMatrix.length][secondMatrix[0].length];
        if(firstMatrix.length == secondMatrix.length && firstMatrix[0].length == secondMatrix[0].length) {
            for (int i = 0; i < firstMatrix.length; i++) {
                for (int j = 0; j < firstMatrix[0].length; j++) {
                    resultMatrix[i][j] = secondMatrix[i][j].subtract(firstMatrix[i][j]);
                    newWord = newWord + String.valueOf(resultMatrix[i][j]) + ",";
                }
                newWord = newWord.substring(0,newWord.length()-1) + ";";
            }
            newWord = newWord.substring(0,newWord.length()-1) + "]";
            return newWord;
        }
        return "";
    }

    private String matrixAggregation() {
        String newWord ="[";
        resultMatrix = new BigDecimal[firstMatrix.length][firstMatrix[0].length];
        if(firstMatrix.length == secondMatrix.length && firstMatrix[0].length == secondMatrix[0].length) {
            for (int i = 0; i < firstMatrix.length; i++) {
                for (int j = 0; j < firstMatrix[0].length; j++) {
                    resultMatrix[i][j] = secondMatrix[i][j].add(firstMatrix[i][j]);
                    newWord = newWord + String.valueOf(resultMatrix[i][j]) + ",";
                }
                newWord = newWord.substring(0,newWord.length()-1) + ";";
            }
            newWord = newWord.substring(0,newWord.length()-1) + "]";
            return newWord;
        }
        return "";
    }

    public String matrixDeterminant(String matrix){
        matrix = matrix + 'd';
        String determinant = "";
            if (addMatrix(matrix)) {
                double[][] newMatrixForDouble = new double[firstMatrix.length][firstMatrix[0].length];
                for (int i = 0; i < firstMatrix.length; i++) {
                    for (int j = 0; j < firstMatrix[0].length; j++) {
                        newMatrixForDouble[i][j] = firstMatrix[i][j].doubleValue();
                    }
                }
                if (firstMatrix.length == firstMatrix[0].length) {
                    double result = coutMatrixDeterminant(newMatrixForDouble);
                    determinant = String.valueOf(result);
                    setFlags();
                    return determinant;
                }
            }
        setFlags();
        return determinant;

    }

    private double coutMatrixDeterminant(double [][] matrix) {
        double result = 0;
        if(matrix.length == 1){
            result = firstMatrix[0][0].doubleValue();
            return result;
        }
        if(matrix.length == 2){
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return result;
        }

        for (int i = 0; i < matrix[0].length; i++) {
            double[][] minor = new double[matrix.length - 1][matrix[0].length - 1];
            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        minor[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        minor[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }
            result += matrix[0][i] * Math.pow (-1, Double.valueOf(i)) * coutMatrixDeterminant(minor);
        }
        return result;
    }


    public String transponedMatrix(String matrix){
        matrix = matrix + 't';
        String transponed = "[";
        try {
            if (addMatrix(matrix)) {
                resultMatrix = new BigDecimal[firstMatrix[0].length][firstMatrix.length];
                for (int i = 0; i < firstMatrix[0].length; i++) {
                    for (int j = 0; j < firstMatrix.length; j++) {
                        resultMatrix[i][j] = firstMatrix[j][i];
                        transponed = transponed + String.valueOf(resultMatrix[i][j]) + ",";
                    }
                    transponed = transponed.substring(0, transponed.length() - 1) + ";";
                }
                transponed = transponed.substring(0, transponed.length() - 1) + "]";
                setFlags();
                return transponed;
            }
        }catch(Exception e){
            setFlags();
            return "";
        }
        setFlags();
        return transponed;
    }


    public  BigDecimal [] getResult(){
        return result;
    }
    public String getOperation(){
        return operation;
    }

}
