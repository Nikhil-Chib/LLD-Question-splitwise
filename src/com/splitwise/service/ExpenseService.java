package com.splitwise.service;

import com.splitwise.calculators.ExpenseCalculators;
import com.splitwise.formatters.ExpenseFormatter;
import com.splitwise.model.Expense;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseService {

    List<Expense> expenseList;
    ExpenseFormatter formatter;
    List<ExpenseCalculators> expenseCalculators;


    public ExpenseService(ExpenseFormatter expenseFormatter, List<ExpenseCalculators> expenseCalculators) {
        this.expenseList = new ArrayList<>();
        this.formatter = expenseFormatter;
        this.expenseCalculators = expenseCalculators;
    }

    public void calculateExpense(String[] input) throws Exception {
        Expense expense = this.formatter.formatExpense(input);

        Optional<ExpenseCalculators> o = expenseCalculators.stream().filter(expenseCalculator ->
                expense.getExpenseType().equals(expenseCalculator.getExpenseType())).findFirst();

        if(!o.isPresent()) {
            // throw Exception;
            throw new Exception("Invalid Expense Type");
        }

        ExpenseCalculators calculator = o.get();
        calculator.calculateExpense(expense);
    }
}
