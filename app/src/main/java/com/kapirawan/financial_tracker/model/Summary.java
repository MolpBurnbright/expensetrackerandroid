package com.kapirawan.financial_tracker.model;

import android.util.Log;

import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Summary
{
    public String name;
    public double totalExpense;
    public double totalBudget;

    public Summary(String name, double totalExpense, double totalBudget)
    {
        this.name = name;
        this.totalExpense = totalExpense;
        this.totalBudget = totalBudget;
    }

    public static List<Summary> getSummaries(List<Sum> sumExpenses, List<Sum> sumBudgets){
        ArrayList<Summary> summaries = new ArrayList<>();
        for (Sum sum : sumExpenses) {
            Sum sumBudget = findItem(sumBudgets, sum.name);
            if(sumBudget != null){
                summaries.add(new Summary(sum.name, sum.amount, sumBudget.amount));
                sumBudgets.remove(sumBudget);
            }else{
                summaries.add(new Summary(sum.name, sum.amount, 0));
            }
        }
        for (Sum sum: sumBudgets){
            Sum sumExpense = findItem(sumExpenses, sum.name);
            if(sumExpense == null)
                summaries.add(new Summary(sum.name, 0, sum.amount));
        }
        return summaries;
    }

    private static Sum findItem(List<Sum> sums, String name){
        Iterator<Sum> iterator = sums.iterator();
        while(iterator.hasNext()){
            Sum sum = iterator.next();
            if(sum.name.equals(name)){
                return sum;
            }
        }
        return null;
    }
}
