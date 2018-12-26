package com.kapirawan.financial_tracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;

public class ActivityAccountOptions extends AppCompatActivity {
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_options);
        account = (Account) getIntent().getSerializableExtra("Account");
        ((TextView) findViewById(R.id.textView_account_name)).setText(account.name);
    }

    public void expenseOptionSelected(View v){
        Log.i("ActivityAccountOptions", "Expenses was selected");
        Intent intent = new Intent(getApplicationContext(), ActivityExpenses.class);
        intent.putExtra("Account", account);
        startActivity(intent);
    }
}