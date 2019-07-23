package com.hopkinsdynamics.testcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private static final String TAG = "MainActivity";
    private final String TEXT_CONTENTS = "TextContents";


    // Variables to hold the operands and the type of calculations
    private Double operand1 = null;
  //  private Double operand2 = null;
    private String pendingOperation = "=";
    private final String OPERATION_CONTENTS = "OpContents";
    private final String OPERAND_CONTENTS="OperandContents";

    private static final String STATE_PENDING_OPERATION="PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiple);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);

        Button buttonPower = findViewById(R.id.buttonPower);
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonRoot = findViewById(R.id.buttonRoot);


        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
//                if(value.length() != 0){
//                    performOperation(value, op);
//                }
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }catch(NumberFormatException e){
                    newNumber.setText("");
                    e.printStackTrace();
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonClear.setOnClickListener(opListener);
        buttonRoot.setOnClickListener(opListener);
        buttonPower.setOnClickListener(opListener);


        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if(value.length()==0){
                    newNumber.setText("-");
                }else {
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue*= -1;
                        newNumber.setText(doubleValue.toString());
                    }catch(NumberFormatException e){
                        // newNumber was "-" or ".", so clear it
                        newNumber.setText("");
                    }
                }
            }
        });

        Log.d(TAG, "onCreate: out");
    }

    private void performOperation(Double value, String operation){
       // displayOperation.setText(operation);
        boolean clear=false;
        if ( null == operand1){
            operand1 = value;//Double.valueOf(value);
        }else {
           // operand2 = value;//Double.valueOf(value);
            if( pendingOperation.equals("=")){
                pendingOperation = operation;
            }
            switch(pendingOperation){
                case "=": operand1 = value;// operand2;
                    break;
                case "/":
                    if(/*operand2*/ value == 0){
                        operand1=0.0;
                    }else {
                        operand1/= value;//operand2;
                    }
                    break;
                case "*":
                    operand1*= value;
                    break;
                case "-":
                    operand1 -= value;//operand2;
                    break;
                case "+":
                    operand1+=value;//operand2;
                    break;
                case "CLR":
                    System.out.println(" clear is true");
                    operand1=0.0;
                    clear=true;

                    break;
                case "X^Y":
                   operand1= Math.pow(operand1,value);
                    break;
                case "ROOT":
                    operand1=Math.pow(operand1,(1.0/value));
                    break;
            }
        }
        if(clear){
            result.setText("");
            System.out.println(" clear is true");
            Log.d(TAG,"reached  clear bool block");
        }else {
            result.setText(operand1.toString());
        }
        newNumber.setText("");

    }
//
//    @Override
//    protected void onStart() {
//        Log.d(TAG, "onStart: in");
//        super.onStart();
//        Log.d(TAG, "onStart: out");
//
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");

        super.onRestoreInstanceState(savedInstanceState);

        pendingOperation =savedInstanceState.getString(STATE_PENDING_OPERATION);
        displayOperation.setText(pendingOperation);
        operand1=savedInstanceState.getDouble(STATE_OPERAND1);

        Log.d(TAG, "onRestoreInstanceState: out");
    }

//    @Override
//    protected void onResume() {
//        Log.d(TAG, "onResume: in");
//        super.onResume();
//        Log.d(TAG, "onResume: out");
//    }
//
//    @Override
//    protected void onPause() {
//        Log.d(TAG, "onPause: in");
//        super.onPause();
//        Log.d(TAG, "onPause: out");
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
       // Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1,operand1);
        }

        super.onSaveInstanceState(outState);
      //  Log.d(TAG, "onSaveInstanceState: out");
    }

//    @Override
//    protected void onStop() {
//        Log.d(TAG, "onStop: in");
//        super.onStop();
//        Log.d(TAG, "onStop: out");
//    }
//
//
//    @Override
//    protected void onRestart() {
//        Log.d(TAG, "onRestart: in");
//        super.onRestart();
//        Log.d(TAG, "onRestart: out");
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d(TAG, "onDestroy: in");
//        super.onDestroy();
//        Log.d(TAG, "onDestroy: out");
//    }

}
