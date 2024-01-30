package com.mp.program4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Used in floating action button listener to add data to db
        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result != null && result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                String name = result.getData().getStringExtra(AddExpenseActivity.EXTRA_NAME);
                                String category = result.getData().getStringExtra(AddExpenseActivity.EXTRA_CATEGORY);
                                String date = result.getData().getStringExtra(AddExpenseActivity.EXTRA_DATE);
                                float amount = result.getData().getFloatExtra(AddExpenseActivity.EXTRA_AMOUNT, 0);
                                String note = result.getData().getStringExtra(AddExpenseActivity.EXTRA_NOTE);

                                Expense expense = new Expense(name, category, date, amount, note);
                                expenseViewModel.upsert(expense);

                                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        //set up recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ExpenseAdapter adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);


        FloatingActionButton addExpenseButton = findViewById(R.id.addExpenseBtn);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startForResult.launch(intent);
            }
        });


        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.setExpenses(expenses);
            }
        });

        //For swiping to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(adapter.getExpenseAt(viewHolder.getAbsoluteAdapterPosition()));
                Toast.makeText(MainActivity.this, "Expense Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        ActivityResultLauncher<Intent> startForResultEdit = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result != null && result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                long id = result.getData().getLongExtra(AddExpenseActivity.EXTRA_ID, -1);
                                if(id == -1){
                                    Toast.makeText(MainActivity.this, "Expense can't be updated", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                String name = result.getData().getStringExtra(AddExpenseActivity.EXTRA_NAME);
                                String category = result.getData().getStringExtra(AddExpenseActivity.EXTRA_CATEGORY);
                                String date = result.getData().getStringExtra(AddExpenseActivity.EXTRA_DATE);
                                float amount = result.getData().getFloatExtra(AddExpenseActivity.EXTRA_AMOUNT, 0);
                                String note = result.getData().getStringExtra(AddExpenseActivity.EXTRA_NOTE);

                                Expense expense = new Expense(name, category, date, amount, note);
                                expense.setId(id);
                                expenseViewModel.update(expense);
                                Toast.makeText(MainActivity.this, "Expense updated", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
        );

        //Updating an expense
        adapter.setOnItemClickListener(new ExpenseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                //Send data of expense clicked
                intent.putExtra(AddExpenseActivity.EXTRA_ID, expense.getId());
                intent.putExtra(AddExpenseActivity.EXTRA_NAME, expense.getName());
                intent.putExtra(AddExpenseActivity.EXTRA_CATEGORY, expense.getCategory());
                intent.putExtra(AddExpenseActivity.EXTRA_DATE, expense.getDate());
                intent.putExtra(AddExpenseActivity.EXTRA_AMOUNT, expense.getAmount());
                intent.putExtra(AddExpenseActivity.EXTRA_NOTE, expense.getNote());

                startForResultEdit.launch(intent);
            }
        });
    }
}