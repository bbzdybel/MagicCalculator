package com.example.magiccalculator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class Number extends AppCompatActivity{

    private Button confirmButton;
    private final int PICK_CONTACT = 1;
    private EditText editTextPhone;
    public String number;
    private TextView textViewWrongNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number4);

        textViewWrongNumber = findViewById(R.id.textViewWrongNumber);
        editTextPhone = findViewById(R.id.editTextPhone);
        confirmButton = findViewById(R.id.button2);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }

        });


    }


    private void getNumberFromContacts() {
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContact, 1);
    }

    private void openMainActivity() {
        number = editTextPhone.getText().toString();
        if(checkNumber()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("number",number);
            startActivity(intent);
        }
    }


    public void setOpenContactsButton(View v){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            getNumberFromContacts();
        }else{
            requestPermision();
        }

    }

    private void requestPermision() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    PICK_CONTACT);
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri contactData = data.getData();
        Cursor c = getContentResolver().query(contactData, null, null, null, null);
        if (c.moveToFirst()) {
            int phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String num = c.getString(phoneIndex);
            checkIfNumberIsCorrect(num);
        }
    }

    private void checkIfNumberIsCorrect(String num) {
        if(num.length() < 9 ){
            editTextPhone.setText("");
            editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return;
        }
        if(num.contains("(") || num.contains(")") || num.contains("-")){
            num = num.replace("(","");
            num = num.replace(")","");
            num = num.replace("-"," ");
            if(num.contains("+")){
                num = num.substring(4);
            }
            splitNumber(num);
        }
        if(num.contains("+")) {
            num = num.substring(4);
            splitNumber(num);
        }
        if(num.contains(" ")){
            splitNumber(num);
        }
        if(num.length() == 9){
            editTextPhone.setText(num);
            editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
            number = num;
            checkNumber();
        }
    }

    private void splitNumber(String num){
        String[] tmpNumberTab = num.split(" ");
        number = tmpNumberTab[0] + tmpNumberTab[1] + tmpNumberTab[2];
        editTextPhone.setText(number);
        editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
        checkNumber();
    }

    private boolean checkNumber(){
        String numberToCheck;
        if(number.length()==9){
            numberToCheck = number.substring(0,2);
            if(numberToCheck.equals("50") || numberToCheck.equals("51") || numberToCheck.equals("53")
                    || numberToCheck.equals("57") || numberToCheck.equals("60") || numberToCheck.equals("66")
                    || numberToCheck.equals("69") || numberToCheck.equals("72") || numberToCheck.equals("73")
                    || numberToCheck.equals("78") || numberToCheck.equals("79") || numberToCheck.equals("88")){
                editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
                textViewWrongNumber.setText("");
                return true;
            }else{
                editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                textViewWrongNumber.setText("Podano nieprawidłowy numer telefonu");
                return false;
            }
        }else{
            editTextPhone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            textViewWrongNumber.setText("Podano nieprawidłowy numer telefonu");
            return false;
        }
    }


}