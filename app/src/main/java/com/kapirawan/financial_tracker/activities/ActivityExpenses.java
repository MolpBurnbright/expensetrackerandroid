package com.kapirawan.financial_tracker.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.adapter.ExpenseAdapter;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.viewmodel.ViewModelExpense;

public class ActivityExpenses extends AppCompatActivity {

    private ViewModelExpense viewModelExpense = null;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the account passed on the intent
        account = (Account) getIntent().getSerializableExtra("Account");
        setContentView(R.layout.activity_expenses);
        onCreateSetToolbar();
        //onCreateSetSnackbar();
        onCreateSetupExpenseRecyclerView();
    }

    private void onCreateSetToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
    }

    private void onCreateSetSnackbar(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void onCreateSetupExpenseRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_expenses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ExpenseAdapter adapter = new ExpenseAdapter(this, new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                //To DO: Add feature to display menu
            }
        });
        recyclerView.setAdapter(adapter);
        //Setup the ViewModel for Expenses RecyclerView
        viewModelExpense = ViewModelProviders.of(this).get(ViewModelExpense.class);
/*        viewModelExpense.init(account);
        viewModelExpense.getExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {
                adapter.setExpenses(expenses);
            }
        });
*/
    }
}