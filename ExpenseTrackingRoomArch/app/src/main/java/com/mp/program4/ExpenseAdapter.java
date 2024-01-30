package com.mp.program4;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
    private static List<Expense> expenses = new ArrayList<>();
    private static onItemClickListener listener;

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View expenseView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(expenseView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense currExpense = this.expenses.get(position);
        holder.nameTV.setText(currExpense.getName());
        holder.categoryTV.setText(currExpense.getCategory());
        holder.dateTV.setText(currExpense.getDate());
        holder.amountTV.setText(String.valueOf(currExpense.getAmount()));
        holder.noteTV.setText(currExpense.getNote());
    }

    @Override
    public int getItemCount() {
        return this.expenses.size();
    }

    public void setExpenses(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    //For use in swiping to delete
    public Expense getExpenseAt(int position){
        return expenses.get(position);
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder{
        private TextView nameTV;
        private TextView categoryTV;
        private TextView dateTV;
        private TextView amountTV;
        private TextView noteTV;

       public ExpenseHolder(View expenseView){
           super(expenseView);
           nameTV = expenseView.findViewById(R.id.nameTextView);
           categoryTV = expenseView.findViewById(R.id.categoryTextView);
           dateTV = expenseView.findViewById(R.id.dateTextView);
           amountTV = expenseView.findViewById(R.id.amountTextView);
           noteTV = expenseView.findViewById(R.id.noteTextView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = getAbsoluteAdapterPosition();
                   //To make sure an item with an invalid position is not clicked
                   if(listener != null && pos != RecyclerView.NO_POSITION){
                       listener.onItemClick(expenses.get(pos));
                   }
               }
           });
       }
    }

    //For editing expenses
    public interface onItemClickListener{
        void onItemClick(Expense expense);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
