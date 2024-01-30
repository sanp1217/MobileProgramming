package com.mp.program4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;
    public static final String DATABASE_NAME = "Program4DB.db";

    public abstract ExpenseDao ExpenseDao();

    public static AppDatabase getInstance(final Context context){
        if(db == null){
            db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }
        return db;
    }
}
