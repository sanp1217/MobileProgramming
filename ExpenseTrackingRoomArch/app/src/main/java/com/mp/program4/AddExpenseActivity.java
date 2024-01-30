package com.mp.program4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

//Used for adding and inserting
public class AddExpenseActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "package com.mp.program4.EXTRA_ID";

    public static final String EXTRA_NAME = "package com.mp.program4.EXTRA_NAME";

    public static final String EXTRA_CATEGORY = "package com.mp.program4.EXTRA_CATEGORY";

    public static final String EXTRA_DATE = "package com.mp.program4.EXTRA_DATE";

    public static final String EXTRA_AMOUNT = "package com.mp.program4.EXTRA_AMOUNT";

    public static final String EXTRA_NOTE = "package com.mp.program4.EXTRA_NOTE";


    private EditText nameEditText;
    private EditText categoryEditText;
    private EditText dateEditText;
    private EditText amountEditText;
    private EditText noteEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        nameEditText = findViewById(R.id.nameEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        dateEditText = findViewById(R.id.dateEditText);
        amountEditText = findViewById(R.id.amountEditText);
        noteEditText = findViewById(R.id.noteEditText);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        //Id is only sent when user wants to update, sets title accordingly
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Expense");
            nameEditText.setText(intent.getStringExtra(EXTRA_NAME));
            categoryEditText.setText(intent.getStringExtra(EXTRA_CATEGORY));
            dateEditText.setText(intent.getStringExtra(EXTRA_DATE));
            amountEditText.setText(String.valueOf(intent.getFloatExtra(EXTRA_AMOUNT, 0)));
            noteEditText.setText(intent.getStringExtra(EXTRA_NOTE));
        }else{
            setTitle("Add Expense");
        }
    }

    private void saveExpense(){
        String name = nameEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String amountText = amountEditText.getText().toString();
        String note = noteEditText.getText().toString();

        if(name.trim().isEmpty() || category.trim().isEmpty() || date.trim().isEmpty() || amountText.trim().isEmpty()){
            Toast.makeText(this, "Only note can be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //Was getting an empty string error when declaring at
        //the top of the function so its only declared when I
        //know that it won't be empty.
        float amount = Float.valueOf(amountText);

        //to send data back to main activity
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_NOTE, note);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.saveExpense){
            saveExpense();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}