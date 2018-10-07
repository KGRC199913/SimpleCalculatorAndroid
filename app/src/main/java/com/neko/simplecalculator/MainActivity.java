package com.neko.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String equationStrKey = "ESK";
    private static String resultStrKey = "RSK";
    private static String lastInputCharKey = "LICK";
    private static String isLastInputBooleanKey = "ILIBK";

    private char lastInput;
    private boolean isLastInputOperator;
    private TextView equationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equationTextView = findViewById(R.id.equation_textView);
        if (savedInstanceState != null) {
            equationTextView.setText(savedInstanceState.getString(equationStrKey));
            ((TextView) findViewById(R.id.result_textView)).setText(savedInstanceState.getString(resultStrKey));
            lastInput = savedInstanceState.getChar(lastInputCharKey);
            isLastInputOperator = savedInstanceState.getBoolean(isLastInputBooleanKey);
        } else {
            lastInput = '0';
            isLastInputOperator = true;
        }
    }

    private void handleNumbericInput(int buttonID) {
        //TODO check if 0 is first number
        String sb = equationTextView.getText().toString();
        if (isLastInputOperator)
            sb += " ";
        sb += ((Button) findViewById(buttonID)).getText();
        equationTextView.setText(sb.toString());
        isLastInputOperator = false;
        lastInput = getResources().getString(buttonID).charAt(0);
    }

    public void OnNumbericButtonClicked(View view){
        handleNumbericInput(view.getId());
    }

    private void handleOperatorInput(int ButtonID) {
        StringBuilder sb = new StringBuilder(equationTextView.getText());
        if (isLastInputOperator) {
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
            }
            else
                return;
        }
        sb.append(" ").append(((Button) findViewById(ButtonID)).getText().toString());
        equationTextView.setText(sb.toString());
        isLastInputOperator = true;
        lastInput = getResources().getString(ButtonID).charAt(0);
    }

    public void OnOperatorButtonClicked(View view) {
        handleOperatorInput(view.getId());
    }

    public void OnButtonEqualClicked(View view){
        Calculator calculator = new Calculator(equationTextView.getText().toString());
        ((TextView) findViewById(R.id.result_textView)).setText(calculator.getResult());
    }

    public void OnResetClicked(View view) {
        equationTextView.setText("");
        ((TextView) findViewById(R.id.result_textView)).setText("");
        lastInput = '0';
        isLastInputOperator = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(equationStrKey, equationTextView.getText().toString());
        outState.putString(resultStrKey,
                ((TextView) findViewById(R.id.result_textView)).getText().toString());
        outState.putChar(lastInputCharKey, lastInput);
        outState.putBoolean(isLastInputBooleanKey, isLastInputOperator);
    }
}
