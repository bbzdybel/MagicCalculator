package com.example.magiccalculator;

import java.util.Stack;

public class ProgramingNumbers {

    private final static char[] hexadecimal = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final static char[] charakters = {'.', '(', '^', ')', 'x', '+', '÷', '[', ']', ';', ','};

    public String decimalIntoBinary(String number) {
        String newNumber = "";
        Stack<Integer> stack = new Stack<Integer>();
        if (checkIfOnlyNumbers(number)) {
            int copyOfNumber = Integer.parseInt(number);
            if (copyOfNumber <= 2147483647) {
                while (copyOfNumber != 0) {
                    int modulo = copyOfNumber % 2;
                    stack.push(modulo);
                    copyOfNumber /= 2;
                }
                while (!(stack.isEmpty())) {
                    newNumber = newNumber + String.valueOf(stack.pop());
                }
            }
        }
        return newNumber;
    }

    private boolean checkIfOnlyNumbers(String number) {
        char[] letters = number.toCharArray();
        for (char l : letters) {
            for (char c : charakters) {
                if (c == l) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean only0Or1(String number) {
        char[] letters = number.toCharArray();
        for (char c : letters) {
            if (c != '1') {
                if (c != '0') {
                    return false;
                }
            } else if (c != '0') {
                if (c != '1') {
                    return false;
                }
            }
        }
        return true;
    }

    public String binaryToDecimal(String number) {
        int copyOfNumber = Integer.parseInt(number);
        int newNumber = 0;
        int index = 0;
        String result = "";
        if (only0Or1(number)) {
            while (copyOfNumber != 0) {
                int temp = copyOfNumber % 10;
                newNumber += temp * Math.pow(2, index);
                copyOfNumber = copyOfNumber / 10;
                index++;
            }
            result = String.valueOf(newNumber);
        }
        return result;
    }

    public String decimalToHexadecimal(String number) {
        String newNumber = "";
        int tempNumber = 0;
        if (only0Or1(number)) {
            number = binaryToDecimal(number);
        }
        if (checkIfOnlyNumbers(number)) {
            int copyOfNumber = Integer.parseInt(number);
            while (copyOfNumber != 0) {
                tempNumber = copyOfNumber % 16;
                newNumber = hexadecimal[tempNumber] + newNumber;
                copyOfNumber = copyOfNumber / 16;
            }
        }
        return newNumber;
    }

    public String decimalToU2(String number) {
        String newNumber = "";
        if (only0Or1(number)) {
            number = binaryToDecimal(number);
        } else if (number.contains("A") || number.contains("B") || number.contains("C") ||
                number.contains("D") || number.contains("E") || number.contains("F")) {
            number = hexToDec(number);
        }
        if (checkIfOnlyNumbers(number)) {
            int copyOfNumber = Integer.parseInt(number);
            if (copyOfNumber >= 0) {
                if (copyOfNumber <= 15) {
                    newNumber = makeU2FromPositive(copyOfNumber, 4);
                } else if (copyOfNumber <= 255 && copyOfNumber >= 16) {
                    newNumber = makeU2FromPositive(copyOfNumber, 8);
                } else if (copyOfNumber <= 4095 && copyOfNumber >= 256) {
                    newNumber = makeU2FromPositive(copyOfNumber, 12);
                } else if (copyOfNumber <= 65535 && copyOfNumber >= 4096) {
                    newNumber = makeU2FromPositive(copyOfNumber, 16);
                }
            } else {
                if (copyOfNumber >= -8) {
                    newNumber = makeU2(-8, copyOfNumber, 4);
                } else if (copyOfNumber >= -128 && copyOfNumber <= -8) {
                    newNumber = makeU2(-128, copyOfNumber, 8);
                } else if (copyOfNumber >= -2048 && copyOfNumber <= -128) {
                    newNumber = makeU2(-2048, copyOfNumber, 12);
                } else if (copyOfNumber >= -32768 && copyOfNumber <= -2048) {
                    newNumber = makeU2(-32768, copyOfNumber, 16);
                }
            }
        }
        return newNumber;
    }

    private String makeU2FromPositive(int copyOfNumber, int bitCounter) {
        String newNumber = "";
        newNumber = String.valueOf(copyOfNumber);
        newNumber = decimalIntoBinary(newNumber);
        if (newNumber.length() <= bitCounter - 1) {
            int n = bitCounter - newNumber.length();
            while (n != 0) {
                newNumber = "0" + newNumber;
                n--;
            }
        }
        return newNumber;
    }


    private String makeU2(int zakres, int liczba, int bitCounter) {
        String newNumber = "";
        liczba = Math.abs(zakres) + liczba;
        newNumber = String.valueOf(liczba);
        newNumber = decimalIntoBinary(newNumber);
        if (newNumber.length() <= bitCounter - 1) {
            int n = bitCounter - 1 - newNumber.length();
            while (n != 0) {
                newNumber = "0" + newNumber;
                n--;
            }
        }
        newNumber = "1" + newNumber;
        return newNumber;
    }

    public String addToBinaryNumbers(String firstNumber, String secondNumber, String calculation) {
        String newNumber = "";
        if (only0Or1(firstNumber) && only0Or1(secondNumber)) {
            int firNumber = Integer.parseInt(binaryToDecimal(firstNumber));
            int secNumber = Integer.parseInt(binaryToDecimal(secondNumber));
            if (calculation.equals("x")) {
                newNumber = String.valueOf(firNumber * secNumber);
                newNumber = decimalIntoBinary(newNumber);
            } else if (calculation.equals("+")) {
                newNumber = String.valueOf(firNumber + secNumber);
                newNumber = decimalIntoBinary(newNumber);
            } else if (calculation.equals("÷")) {
                newNumber = String.valueOf(firNumber / secNumber);
                newNumber = decimalIntoBinary(newNumber);
            } else if (calculation.equals("-")) {
                newNumber = String.valueOf(firNumber - secNumber);
                newNumber = decimalIntoBinary(newNumber);
            }
        }
        return newNumber;
    }

    public String hexToDec(String number) {
        String newNumber = "";
        int tempNumber = 0;
        if (checkIfOnlyNumbers(number)) {
            for (int i = 0; i < number.length(); i++) {
                char ch = number.charAt(i);
                int n = 0;
                for (char c : hexadecimal) {
                    if (c == ch) {
                        tempNumber = 16 * tempNumber + n;
                    }
                    n++;
                }
            }
            newNumber = String.valueOf(tempNumber);
            return newNumber;
        }
        return newNumber;
    }

    public String hexToBinary(String number) {
        String result = hexToDec(number);
        result = decimalIntoBinary(result);
        return result;
    }

    public String u2ToDecimal(String number) {
        String positiveOrNegativ = number.substring(0, 1);
        String result = "";
        int substract = 0;
        if (positiveOrNegativ.equals("1")) {
            number = number.substring(1);
            result = binaryToDecimal(number);
            substract = (int) Math.pow(2, number.length());
            int newNumber = Integer.parseInt(result);
            newNumber = newNumber - substract;
            result = String.valueOf(newNumber);
        } else if (positiveOrNegativ.equals("0")) {
            result = binaryToDecimal(number);
        }
        return result;
    }


}
