package com.splitwise.calculators;

import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseType;
import com.splitwise.service.BalanceService;

import java.util.List;

public interface ExpenseCalculators {


    void calculateExpense(Expense expense) throws Exception;

    boolean isValidExpense(double total, List<Double> givenAmount) ;

    ExpenseType getExpenseType();
}
