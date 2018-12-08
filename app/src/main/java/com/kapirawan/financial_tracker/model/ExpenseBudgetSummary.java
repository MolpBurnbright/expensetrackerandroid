package com.kapirawan.financial_tracker.model;

import java.util.Date;

public class ExpenseBudgetSummary
{
    public String type;
    public double totalExpense;
    public double totalBudget;
    public double totalRemainingBudget;

    public ExpenseBudgetSummary(String type, double totalExpense, double totalBudget,
                                double totalRemainingBudget)
    {
        this.type = type;
        this.totalExpense = totalExpense;
        this.totalBudget = totalBudget;
        this.totalRemainingBudget = totalRemainingBudget;
    }
}
