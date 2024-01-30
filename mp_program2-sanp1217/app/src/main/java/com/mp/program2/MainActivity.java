package com.mp.program2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mp.program2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private ActivityMainBinding binding;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.radioGroup.setOnCheckedChangeListener(this);
        bill = new Bill(0, 0);

        binding.calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean isAmountNotEmpty = !binding.amountInput.getText().toString().isEmpty();
                boolean isTipNotEmpty = !binding.tipPercentInput.getText().toString().isEmpty();

                if(isAmountNotEmpty && isTipNotEmpty){
                    bill.setBillAmount(Double.parseDouble(binding.amountInput.getText().toString()));
                    bill.setTipPercent(Double.parseDouble(binding.tipPercentInput.getText().toString()));

                    //Format to two decimal places
                    String tip = String.format("%.2f", bill.getTip());
                    binding.tipText.setText(tip);
                    String totalBill = String.format("%.2f", bill.calculateTotalBill());
                    binding.totalBillText.setText(totalBill);
                }else{
                    Toast.makeText(getApplicationContext(), "Both input fields need to be filled.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCheckedChanged(RadioGroup group, int checkedId){
        if(group == binding.radioGroup){
            if(checkedId == R.id.roundBillRadio){
                bill.setRoundBillTrue();
            }else if(checkedId == R.id.roundTipRadio){
                bill.setRoundTipTrue();
            }else if(checkedId == R.id.noRoundingRadio){
                bill.setNoRounding();
            }
        }
    }
}