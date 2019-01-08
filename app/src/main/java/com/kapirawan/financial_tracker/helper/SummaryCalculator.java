package com.kapirawan.financial_tracker.helper;

import com.kapirawan.financial_tracker.roomdatabase.budget.Budget;
import com.kapirawan.financial_tracker.roomdatabase.expense.Expense;
import com.kapirawan.financial_tracker.roomdatabase.fund.Fund;
import com.kapirawan.financial_tracker.summary.Summary;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class SummaryCalculator {
    private List<Expense> expensesData = null;
    private List<Fund> fundsData = null;
    private List<Budget> budgetData = null;
    private List<Summary> summary = null;

    public SummaryCalculator(){

    }

    public List<Summary> updateExpense(List<Expense> expenses){
        summary = new ArrayList<>();
        expensesData = expenses;
        sortExpenses();
        sortBudgets();
        sumUpExpenses();
        sumUpBudgets();
        return summary;
    }

    private void sortExpenses(){
        //Sort the expense by ascending order of type/category.
        Collections.sort(expensesData, (expense1, expense2) -> {
            //compareTo returns -1 if string1 takes precedence over string2,
            //0 if they are equal, and 1 if string1 is succeeds string2.
            return expense1.type.compareTo(expense2.type);
        });
    }

    private void sortBudgets(){
        //Sort the expense by ascending order of type/category.
        Collections.sort(budgetData, (budget1, budget2) -> {
            //compareTo returns -1 if string1 takes precedence over string2,
            //0 if they are equal, and 1 if string1 is succeeds string2.
            return budget1.type.compareTo(budget2.type);
        });
    }

    private void sumUpExpenses(){
        if(expensesData.size() > 0){
            Summary summaryItem;
            String category = expensesData.get(0).type;
            float totalExpense = 0;
            for(Expense exp: expensesData){
                if(category == exp.type){
                    totalExpense += exp.amount;
                }else{
//                    summaryItem = new Summary(category, totalExpense,
//                            0, 0);
//                    summary.add(summaryItem);
                    category = exp.type;
                    totalExpense = 0;
                }
            }
//            summaryItem = new Summary(category, totalExpense,
//                    0, 0);
//            summary.add(summaryItem);
        }
    }

    private void sumUpBudgets(){
        if(budgetData.size() > 0){
            Summary summaryItem;
            String category = budgetData.get(0).type;
            float totalBudget = 0;

            for (int index = 0; index < budgetData.size(); index++){
                for(;category == budgetData.get(index).type
                        && index < budgetData.size();index++){
                    totalBudget += budgetData.get(index).amount;
                }
                boolean categoryFound = false;
                for(Summary summary: summary) {
//                    if (category == summary.type) {
//                        summary.totalBudget = totalBudget;
//                        categoryFound = true;
//                    }
                }
                if (!categoryFound){
//                    summaryItem = new Summary(category, 0, totalBudget,
//                            0);
//                    summary.add(summaryItem);
                }
                category = budgetData.get(index).type;
            }
        }
    }
}