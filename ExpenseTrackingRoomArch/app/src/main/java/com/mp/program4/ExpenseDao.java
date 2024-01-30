package com.mp.program4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM " + Expense.TABLE_NAME)
    LiveData<List<Expense>> selectAll();

    @Upsert
    void upsert(Expense expense);

    @Query("DELETE FROM " + Expense.TABLE_NAME + " WHERE " + Expense.COLUMN_ID + " = :id")
    void deleteById(long id);

    @Update
    void update(Expense expense);
}
