package com.splitwise.calculators;

import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseType;
import com.splitwise.service.BalanceService;

import java.util.List;

public class EqualExpenseCalculator implements ExpenseCalculators {

    BalanceService balanceService;

    public EqualExpenseCalculator(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public void calculateExpense(Expense expense) throws Exception{
        double amount = expense.getPaidAmount()/(expense.getOwingUsers().size());

        String payingUser = expense.getPayingUser();
        List<String> owingUsers = expense.getOwingUsers();

        for(String owingUser: owingUsers) {
            if(payingUser.equalsIgnoreCase(owingUser)) continue;

            this.balanceService.updateBalance(payingUser, owingUser, amount);
        }
    }

    @Override
    public boolean isValidExpense(double total, List<Double> givenAmount) {
        return true;
    }

    @Override
    public ExpenseType getExpenseType() {
        return ExpenseType.EQUAL;
    }
}
