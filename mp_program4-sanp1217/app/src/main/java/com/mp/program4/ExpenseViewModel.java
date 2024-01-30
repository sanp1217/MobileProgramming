package com.mp.program4;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<List<Expense>> allExpenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpenses();
    }

    public void upsert(Expense expense){
        repository.upsert(expense);
    }

    public void delete(Expense expense){
        repository.delete(expense);
    }

    public void update(Expense expense){
        repository.update(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }
}
