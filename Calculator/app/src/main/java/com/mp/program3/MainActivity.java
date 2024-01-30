package com.mp.program3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mp.program3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private StringBuilder currNum;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calculator = new Calculator();
        currNum = new StringBuilder();
        setNumberBtnListeners();
        setClearBtnListener();
        setOperationBtnListeners();
        setEqualsBtnListener();
        setDecimalBtnListener();
        setNegateBtnListener();
    }


    private void setNumberBtnListeners() {
        int[] numBtnsIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        for (int btnId : numBtnsIds) {
            Button btn = findViewById(btnId);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleNumInput(btn.getText().toString());
                }
            });
        }
    }

    private void handleNumInput(String num) {
        currNum.append(num);
        binding.displayView.setText(currNum);
    }

    private void setDecimalBtnListener(){
        binding.decimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Only one decimal allowed
                if(currNum.toString().contains(".")){
                    return;
                }
                handleNumInput(binding.decimalBtn.getText().toString());
            }
        });
    }

    private void setClearBtnListener() {
        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.displayView.setText("");
                currNum.setLength(0);
                calculator.clear();
            }
        });
    }

    private void setOperationBtnListeners() {
        int[] operBtnsIds = {R.id.addBtn, R.id.subBtn, R.id.multBtn, R.id.divideBtn, R.id.powerBtn};

        for (int btnId : operBtnsIds) {
            Button btn = findViewById(btnId);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //When any operation is pressed, store the first number if it has not been
                    //stored yet which would be when it is not a number.
                    if (Double.isNaN(calculator.getFirstOperator())) {
                        calculator.setFirstOperator(Double.valueOf(currNum.toString()));
                        //Clear the number
                        currNum.setLength(0);
                    }else{
                        //Store the second number and calculate the result
                        calculator.setSecondOperator(Double.valueOf(currNum.toString()));
                        double result = calculator.calculateResult();
                        calculator.setFirstOperator(result);
                        binding.displayView.setText(String.valueOf(result));
                        currNum.setLength(0);
                    }
                    calculator.setOperation(btn.getText().toString());
                }
            });
        }
    }

    private void setEqualsBtnListener() {
        binding.equalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.setSecondOperator(Double.valueOf(currNum.toString()));
                double result = calculator.calculateResult();
                binding.displayView.setText(String.valueOf(result));
            }
        });
    }

    private void setNegateBtnListener(){
        binding.negateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currNum.toString().contains("-")){
                    //Take off the negative
                    currNum.replace(0, 1, "");

                }else{
                    currNum.insert(0, "-");
                }
                binding.displayView.setText(currNum);
            }
        });
    }
}