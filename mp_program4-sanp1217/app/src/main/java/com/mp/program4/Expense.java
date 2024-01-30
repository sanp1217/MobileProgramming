package com.mp.program4;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Expense.TABLE_NAME)
public class Expense {
    public static final String TABLE_NAME = "expenses";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_NOTE = "note";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    @ColumnInfo(name = COLUMN_CATEGORY)
    public String category;

    @ColumnInfo(name = COLUMN_DATE)
    public String date;

    @ColumnInfo(name = COLUMN_AMOUNT)
    public float amount;

    @ColumnInfo(name = COLUMN_NOTE)
    public String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Ignore
    public Expense(){
    }

    public Expense(String name, String category, String date, float amount, String note){
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }
}
