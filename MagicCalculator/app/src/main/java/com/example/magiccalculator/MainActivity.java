package com.example.magiccalculator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;



public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText numbers;
    private TextView history;

    private Button sqrtButton;
    private Button sinButton;
    private Button cosButton;
    private Button tgButton;
    private Button ctgButton;

    private Button squareBrackets;
    private Button comma;
    private Button semicolon;
    private Button transposed;
    private Button detA;
    private Button hexLetters;

    private Button parenthesesButton;
    private Button exponentButton;
    private Button plusMinusButton;
    private Button dotButton;

    private Button decimalButton;
    private Button hexadecimalButton;
    private Button binaryButton;
    private Button u2Button;

    private ImageButton closeButton;
    private TextView resultTestView;


    private CheckBox programistyCheckBox;
    double firstNumber;
    int numberOfOperations;
    String telNum = null;

    private String firstBinNumber = "";
    private String calculation = "";
    private final static char [] hexadecimal = {'A','B','C','D','E','F'};
    private boolean hexadecimalFlag = false;
    private boolean u2flag = false;

    ProgramingNumbers programingNumbers;
    CalculationAlgorithm calculationAlgorithm;
    Matrix matrix;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = findViewById(R.id.textView3);
        numbers.setShowSoftInputOnFocus(false);

        sqrtButton = findViewById(R.id.squareButton);
        sinButton = findViewById(R.id.sinButton);
        cosButton = findViewById(R.id.cosButton);
        tgButton = findViewById(R.id.tgButton);
        ctgButton = findViewById(R.id.ctgButton);

        squareBrackets = findViewById(R.id.squareBrackets);
        comma = findViewById(R.id.comma);
        semicolon = findViewById(R.id.semicolon);
        transposed = findViewById(R.id.transposed);
        detA = findViewById(R.id.detA);
        hexLetters = findViewById(R.id.hexLetters);

        parenthesesButton = findViewById(R.id.parenthesesButton);
        exponentButton = findViewById(R.id.exponentButton);
        plusMinusButton = findViewById(R.id.plusMinusButton);
        dotButton = findViewById(R.id.dotButton);


        decimalButton = findViewById(R.id.decimalButton);
        hexadecimalButton = findViewById(R.id.hexadecimalButton);
        binaryButton = findViewById(R.id.binaryButton);
        u2Button = findViewById(R.id.u2Button);

        programistyCheckBox = (CheckBox) findViewById(R.id.programisty);

        decimalButton.setEnabled(false);
        hexadecimalButton.setEnabled(false);
        binaryButton.setEnabled(false);
        u2Button.setEnabled(false);
        hexLetters.setEnabled(false);

        history = findViewById(R.id.textView);
        matrix = new Matrix();

        closeButton = findViewById(R.id.closeButton);
        resultTestView = findViewById(R.id.resultTextView);
        closeButton.setVisibility(View.INVISIBLE);
        resultTestView.setVisibility(View.INVISIBLE);

        programistyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged (CompoundButton buttonView,boolean isChecked)
            {
                int orientation = getResources().getConfiguration().orientation;
                if (isChecked) {
                    if (orientation == 2) {
                        setButonsEnabledIfTrue();
                    }
                } else {
                    if (orientation == 2) {
                        setButtonsEnabledIfFalse();
                    }
                }

            }
        });



    }

    private void setButtonsEnabledIfFalse() {
        sqrtButton.setEnabled(true);
        sinButton.setEnabled(true);
        cosButton.setEnabled(true);
        tgButton.setEnabled(true);
        ctgButton.setEnabled(true);

        squareBrackets.setEnabled(true);
        comma.setEnabled(true);
        semicolon.setEnabled(true);
        transposed.setEnabled(true);
        detA.setEnabled(true);


        parenthesesButton.setEnabled(true);
        exponentButton.setEnabled(true);
        plusMinusButton.setEnabled(true);
        dotButton.setEnabled(true);

        decimalButton.setEnabled(false);
        hexadecimalButton.setEnabled(false);
        binaryButton.setEnabled(false);
        u2Button.setEnabled(false);
        hexLetters.setEnabled(false);
    }

    private void setButonsEnabledIfTrue() {
        sqrtButton.setEnabled(false);
        sinButton.setEnabled(false);
        cosButton.setEnabled(false);
        tgButton.setEnabled(false);
        ctgButton.setEnabled(false);


        squareBrackets.setEnabled(false);
        comma.setEnabled(false);
        semicolon.setEnabled(false);
        transposed.setEnabled(false);
        detA.setEnabled(false);

        parenthesesButton.setEnabled(false);
        exponentButton.setEnabled(false);
        plusMinusButton.setEnabled(false);
        dotButton.setEnabled(false);

        decimalButton.setEnabled(true);
        decimalButton.setEnabled(true);
        hexadecimalButton.setEnabled(true);
        binaryButton.setEnabled(true);
        u2Button.setEnabled(true);
        hexLetters.setEnabled(true);

    }

    private void getTelNumber() {
        Bundle bundle=getIntent().getExtras();
        if(bundle != null) {
            telNum = bundle.getString("number");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void update(String addNumber){
        String oldNumber = numbers.getText().toString();
        numbers.setText(String.format("%s%s",oldNumber,addNumber));
    }

    public void binarButton(View view){
        String number = numbers.getText().toString();
        programingNumbers = new ProgramingNumbers();
        if(hexadecimalFlag){
            String newNumber = programingNumbers.hexToBinary(number);
            show("Hexadecimal -> Binary",number, newNumber);
            numbers.setText(newNumber);
            hexadecimalFlag = false;
        } else{
            String newNumber = programingNumbers.decimalIntoBinary(number);
            show("Decimal -> Binary",number, newNumber);
            numbers.setText(newNumber);
        }
    }

    public void decimButton(View view){
        decimalButton.setTextColor(Color.BLACK);
        String number = numbers.getText().toString();
        programingNumbers = new ProgramingNumbers();
        if(u2flag){
            String newNumber = programingNumbers.u2ToDecimal(number);
            show("U2 -> Decimal",number, newNumber);
            numbers.setText(newNumber);
            u2flag=false;
        }else if(programingNumbers.only0Or1(number) && !u2flag){
            String newNumber = programingNumbers.binaryToDecimal(number);
            show("Binary -> Decimal",number, newNumber);
            numbers.setText(newNumber);
        }else if(hexadecimalFlag || checkIfheksadecimal()){
            String newNumber = programingNumbers.hexToDec(number);
            show("Hexadecimal -> Decimal",number, newNumber);
            numbers.setText(newNumber);
            hexadecimalFlag = false;
        }

    }

    private void show(String operation,String oldNumber, String newNumber) {
        closeButton.setVisibility(View.VISIBLE);
        resultTestView.setVisibility(View.VISIBLE);
        resultTestView.setText(operation + "\n" + oldNumber + "->" + newNumber);
    }

    private boolean checkIfheksadecimal() {
        String newString = numbers.getText().toString();
        for (int i = 0; i < newString.length(); i++) {
            char c = newString.charAt(i);
            for(char h : hexadecimal){
                if(c == h){
                    return true;
                }
            }
        }
        return false;
    }


    public void hexButton(View view){
        String number = numbers.getText().toString();
        programingNumbers = new ProgramingNumbers();
        String newNumber = programingNumbers.decimalToHexadecimal(number);
        show("Decimal -> Hexadecimal",number, newNumber);
        if(programingNumbers.only0Or1(number) && !u2flag){
            show("Binary -> Hexadecimal",number, newNumber);
        }else if(u2flag){
            numbers.setText("");
        }
        numbers.setText(newNumber);
    }

    public void u2Button(View view){
        u2flag = true;
        String number = numbers.getText().toString();
        programingNumbers = new ProgramingNumbers();
        String newNumber = programingNumbers.decimalToU2(number);
        if(programingNumbers.only0Or1(number) && !u2flag){
            show("Binary -> U2",number, newNumber);
        }else if(hexadecimalFlag || checkIfheksadecimal()){
            show("Hexadecimal -> U2",number, newNumber);
            numbers.setText(newNumber);
            hexadecimalFlag = false;
        }else{
            show("Decimal -> U2",number, newNumber);
        }
        numbers.setText(newNumber);
    }


    public void zeroButton(View view){
        update("0");
    }
    public void oneButton(View view){
        update("1");
    }
    public void twoButton(View view){
        update("2");
    }
    public void threeButton(View view){
        update("3");
    }
    public void fourButton(View view){
        update("4");
    }
    public void fiveButton(View view){
        update("5");
    }
    public void sixButton(View view){
        update("6");
    }
    public void svenButton(View view){
        update("7");
    }
    public void eightButton(View view){
        update("8");
    }
    public void nineButton(View view){
        update("9");
    }

    public void exponentButton(View view){
        update("^");
    }

    public void parnthesesButton(View view){
        String word = numbers.getText().toString();
        if(word.contains("(")){
            update(")");
        }else {
            update("(");
        }
    }

    public void devideButton(View view){
        update("÷");
        addFirstBinNumber();
        addFirstMatrix();
        numberOfOperations++;
    }
    public void multiplyButton(View view){
        update("x");
        addFirstBinNumber();
        addFirstMatrix();
        numberOfOperations++;
    }
    public void addButton(View view){
        update("+");
        addFirstBinNumber();
        addFirstMatrix();
        numberOfOperations++;
    }
    public void substractButton(View view){
        update("-");
        addFirstBinNumber();
        addFirstMatrix();
        numberOfOperations++;
    }
    public void plusMinusButton(View view){
        if(Double.parseDouble(numbers.getText().toString())>0) {
            numbers.setText("-" + numbers.getText().toString());
        }else{
            String newNumber = numbers.getText().toString();
            newNumber = newNumber.substring(1,newNumber.length());
            numbers.setText(newNumber);
        }
    }
    public void pointButton(View view){
        String word = numbers.getText().toString();
        int n = word.length();
        if(word.equals("")){
            numbers.setText("0.");
        }else if(word.lastIndexOf(".",n)==-1){
            update(".");
        }

    }

    public void equalsButton(View view){
        if(programistyCheckBox.isChecked()){
            if(firstBinNumber != ""){
                programingNumbers = new ProgramingNumbers();
                numbers.setText(programingNumbers.addToBinaryNumbers(firstBinNumber,numbers.getText().toString(),calculation));
                firstBinNumber = "";
            }
        }else if(checkIfMatrix()){
            countMatrix();
        } else {
            clear();
            if(trySplit()) {
                calculationAlgorithm = new CalculationAlgorithm();
                String newNumber = numbers.getText().toString();
                newNumber = newNumber.replace("(", "");
                newNumber = newNumber.replace(")", "");
                newNumber = calculationAlgorithm.yardAlgrithm(newNumber);
                numbers.setText(newNumber);
            }
        }
    }

    private void countMatrix() {
        addFirstMatrix();
        String newWord = matrix.countMatrix();
        if(matrix.getOperation().equals("÷")){
            closeButton.setVisibility(View.VISIBLE);
            resultTestView.setVisibility(View.VISIBLE);
            BigDecimal[] result = matrix.getResult();
            resultTestView.setText("Wyniki: \n");
            for (int i = 0; i < result.length; i++) {
                String temp = resultTestView.getText().toString();
                resultTestView.setText(temp + result[i] + "\n");
            }
        }
        numbers.setText(newWord);
    }

    public void closeResultPanel(View view){
        closeButton.setVisibility(View.INVISIBLE);
        resultTestView.setVisibility(View.INVISIBLE);
    }


    private boolean checkIfMatrix() {
        String matrix = numbers.getText().toString();
        if(matrix.contains("]")){
            squareBrackets.setTextColor(Color.BLACK);
            return true;
        }
        return false;
    }

    private void addFirstMatrix(){
        if(checkIfMatrix()){
            boolean matrixFlag = matrix.addMatrix(numbers.getText().toString());
            if(matrixFlag){
                squareBrackets.setTextColor(Color.GREEN);
                numbers.setText("");
            }else{
                squareBrackets.setTextColor(Color.RED);
                numbers.setText("");
            }
        }
    }

    private void addFirstBinNumber(){
        String word = numbers.getText().toString();
        if(programistyCheckBox.isChecked()) {
            if(word.contains("1") || word.contains("0")) {
                if (firstBinNumber == "") {
                    firstBinNumber = numbers.getText().toString();
                    int lenght = firstBinNumber.length();
                    calculation = firstBinNumber.substring(lenght - 1, lenght);
                    firstBinNumber = firstBinNumber.substring(0, lenght - 1);
                    numbers.setText("");
                }
            }
        }
    }


    private boolean trySplit() {
        if (numberOfOperations >= 4) {
            getTelNumber();
            numbers.setText(telNum);
            history.setText("");
            numberOfOperations=0;
            return false;
        }
        numberOfOperations=0;
        return true;

    }

    public void backspaceButton(View view){
        String word = numbers.getText().toString();
        int input = word.length();
        if (input > 0){
            numbers.setText(word.substring(0, input-1));
        }
    }
    private void clear(){
        history.setText(numbers.getText().toString());
    }


    public void clearButton(View view){
        numbers.setText("");
    }

    public void squareBrackets(View view){
        String word = numbers.getText().toString();
        if(word.contains("[")){
            update("]");
        }else if(word.length()<=1){
            update("[");
        }
    }
    public void comma(View view){
        update(",");
    }
    public void semicolon(View view){
        update(";");
    }
    public void transposed(View view){
        String newWord = matrix.transponedMatrix(numbers.getText().toString());
        numbers.setText(newWord);
    }
    public void detA(View view){
        String newWord = matrix.matrixDeterminant(numbers.getText().toString());
        closeButton.setVisibility(View.VISIBLE);
        resultTestView.setVisibility(View.VISIBLE);
        resultTestView.setText("det(A) = " + newWord );
        numbers.setText(newWord);
    }
    public void hexLetters(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu);

        popupMenu.show();

    }
    public void sqrtButton(View view){
        countTrigonometry("sqrt");

    }
    private boolean checkToFindMathSymbol(String word){
        if(word.contains("^") || word.contains("(") || word.contains(")") ||
                word.contains("÷") || word.contains("x") || word.contains("+") ||
                word.contains("-")){
            return true;
        }else{
            return false;
        }
    }

    public void sinButton(View view){
        countTrigonometry("sin");
    }

    private void countTrigonometry(String trigonometricFunction) {
        String word = numbers.getText().toString();
        int input = word.length();
        double trigonometricValue=0;
        if(checkToFindMathSymbol(word)){
            switch (trigonometricFunction) {
                case "sin":
                    sinButton.setTextColor(Color.RED);
                    break;
                case "cos":
                    cosButton.setTextColor(Color.RED);
                    break;
                case "tg":
                    tgButton.setTextColor(Color.RED);
                    break;
                case "ctg":
                    ctgButton.setTextColor(Color.RED);
                case "sqrt":
                    sqrtButton.setTextColor(Color.RED);
                    break;
            }
        }else if(input>0) {
            switch (trigonometricFunction) {
                case "sin":
                    trigonometricValue = Math.sin(Double.parseDouble(word));
                    break;
                case "cos":
                    trigonometricValue = Math.cos(Double.parseDouble(word));
                    break;
                case "tg":
                    trigonometricValue = Math.tan(Double.parseDouble(word));
                    break;
                case "ctg":
                    trigonometricValue = Math.tan(Double.parseDouble(word));
                    trigonometricValue = 1/trigonometricValue;
                    break;
                case "sqrt":
                    trigonometricValue = Math.sqrt(Double.parseDouble(word));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + trigonometricFunction);
            }
            String newWord = String.valueOf(trigonometricValue);
            numbers.setText(newWord);
            sinButton.setTextColor(Color.BLACK);
            cosButton.setTextColor(Color.BLACK);
            tgButton.setTextColor(Color.BLACK);
            ctgButton.setTextColor(Color.BLACK);
            sqrtButton.setTextColor(Color.BLACK);
        }
    }

    public void cosButton(View view){
        countTrigonometry("cos");
    }
    public void tgButton(View view){
        countTrigonometry("tg");
    }
    public void ctgButton(View view){
        countTrigonometry("ctg");
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        hexadecimalFlag = true;
        int id = item.getItemId();
        switch (id){
            case R.id.A:
                update("A");
                return true;
            case R.id.B:
                update("B");
                return true;
            case R.id.C:
                update("C");
                return true;
            case R.id.D:
                update("D");
                return true;
            case R.id.E:
                update("E");
                return true;
            case R.id.F:
                update("F");
                return true;
        }
        return false;
    }
}