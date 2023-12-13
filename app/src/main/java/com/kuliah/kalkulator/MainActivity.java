package com.kuliah.kalkulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
//import android.util.Log;

import com.google.android.material.button.MaterialButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView inputTv,operationTv;
    MaterialButton buttonC,buttonDivide,buttonMultiply,buttonAdd,buttonSubtract,buttonEqual;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    String operator = null;
    BigDecimal number1, number2, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTv = findViewById(R.id.inputTv);
        operationTv = findViewById(R.id.operationTv);

        assignId(buttonC,R.id.button_ac);
        assignId(buttonAdd,R.id.button_add);
        assignId(buttonSubtract,R.id.button_subtract);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonEqual,R.id.button_equal);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);

    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

@Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String inputText = inputTv.getText().toString();

        if (buttonText.equals("AC")) clearCalculator();
        else if (isOperator(buttonText)) handleOperatorClick(buttonText, inputText);
        else if (buttonText.equals("=")) calculateResult(inputText);
        else appendInputText(buttonText, inputText);
    }
    void clearCalculator() {
        inputTv.setText("0");
        operationTv.setText("0");
        operator = null;
        number1 = null;
        number2 = null;
        result = null;
    }
    void handleOperatorClick(String buttonText, String inputText) {
        if (operator == null) {
            number1 = new BigDecimal(inputText);
            operator = buttonText;
            operationTv.setText(number1 + operator);
            inputTv.setText("0");
        }
    }
    void calculateResult(String inputText) {
        if (operator != null) {
            number2 = new BigDecimal(inputText);
            operationTv.setText(operationTv.getText().toString() + number2 + "=");
            switch (operator) {
                case "+":
                    result = number1.add(number2);
                    break;
                case "-":
                    result = number1.subtract(number2);
                    break;
                case "x":
                    result = number1.multiply(number2);
                    break;
                case "/":
                    if (number2.compareTo(BigDecimal.ZERO) != 0) {
                        result = number1.divide(number2, 2, BigDecimal.ROUND_HALF_UP);
                    } else {
//                        inputTv.setText("Error");
                        showErrorDialog();
                        return;
                    }
                    break;
            }

            String resultStr = result.stripTrailingZeros().toPlainString();
            inputTv.setText(resultStr);
            operator = null;
        }
    }
    void appendInputText(String buttonText, String inputText) {
        if (inputText.equals("0")) inputTv.setText(buttonText);
        else {
            inputText += buttonText;
            inputTv.setText(inputText);
        }
    }
    boolean isOperator(String text) {
        return text.equals("+") || text.equals("-") || text.equals("x") || text.equals("/");
    }

    void showErrorDialog(){
        ConstraintLayout errorConstraintLayout = findViewById(R.id.errorConstraintLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.error_dialog, errorConstraintLayout);
        Button okButton = view.findViewById(R.id.okButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        okButton.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick (View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }
}

