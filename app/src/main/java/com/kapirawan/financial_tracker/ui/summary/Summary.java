package com.kapirawan.financial_tracker.ui.summary;

import com.kapirawan.financial_tracker.roomdatabase.sum.Sum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Summary
{
    public String name;
    public double totalExpense;
    public double totalBudget;
    public double totalRemaining;

    public Summary(String name, double totalExpense, double totalBudget, double totalRemaining)
    {
        this.name = name;
        this.totalExpense = totalExpense;
        this.totalBudget = totalBudget;
        this.totalRemaining = totalRemaining;
    }

    public static List<Summary> getSummaries(List<Sum> sumExpenses, List<Sum> sumBudgets){
        ArrayList<Summary> summaries = new ArrayList<>();
        double totalExpense = 0, totalBudget = 0;
        for (Sum sum : sumExpenses) {
            Sum sumBudget = findItem(sumBudgets, sum.name);
            if(sumBudget != null){
                summaries.add(new Summary(sum.name, sum.amount, sumBudget.amount,
                        sumBudget.amount - sum.amount));
                totalExpense += sum.amount;
                totalBudget += sumBudget.amount;
                sumBudgets.remove(sumBudget);
            }else{
                summaries.add(new Summary(sum.name, sum.amount, 0,
                        0 - sum.amount));
                totalExpense += sum.amount;
            }
        }
        for (Sum sum: sumBudgets){
            summaries.add(new Summary(sum.name, 0, sum.amount, sum.amount));
            totalBudget += sum.amount;
        }
        summaries.add(new Summary("Total", totalExpense, totalBudget,
                totalBudget - totalExpense));
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
