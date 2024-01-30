package com.mp.program4;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpenses;

    public ExpenseRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        expenseDao = db.ExpenseDao();
        allExpenses = expenseDao.selectAll();
    }

    public void upsert(Expense expense){
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               expenseDao.upsert(expense);
           }
       });
       thread.start();
    }

    public void delete(Expense expense){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                expenseDao.deleteById(expense.getId());
            }
        });
        thread.start();
    }

    public void update(Expense expense){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                expenseDao.update(expense);
            }
        });
        thread.start();
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }
}
