package com.mp.helloworldplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private nameViewModel nameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.nameViewModel = new ViewModelProvider(this).get(com.mp.helloworldplus.nameViewModel.class);

        if(savedInstanceState == null){
            NameInputFragment myFragment = new NameInputFragment();

            //Add fragment to container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nameFragContainer, myFragment)
                    .commit();

            nameViewModel.getName().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String newName) {
                    Log.v("Name from activity", newName);
                }
            });
        }
    }
}