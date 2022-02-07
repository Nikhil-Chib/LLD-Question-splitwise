package com.splitwise.calculators;

import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseType;
import com.splitwise.service.BalanceService;

import java.util.List;

public class ExactExpenseCalculator implements ExpenseCalculators{
    BalanceService balanceService;

    public ExactExpenseCalculator(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public void calculateExpense(Expense expense) throws Exception{
        if(expense.getOwingAmt().isEmpty()
                || !isValidExpense(expense.getPaidAmount(), expense.getOwingAmt()))
            throw new Exception("Exact Amount not provided Correctly");

        if(expense.getOwingUsers().size() != expense.getOwingAmt().size()) {
            throw new Exception("Exact Amounts not provided Correctly");
        }

        for(int i=0; i<expense.getOwingUsers().size(); i++) {
            if(expense.getPayingUser().equalsIgnoreCase(expense.getOwingUsers().get(i))) continue;
            this.balanceService.updateBalance(expense.getPayingUser(), expense.getOwingUsers().get(i), expense.getOwingAmt().get(i));
        }
    }

    @Override
    public boolean isValidExpense(double total, List<Double> givenAmount) {
        double sum = 0.0;
        for(Double amount: givenAmount) {
            sum += amount;
        }

        return total == sum;
    }

    @Override
    public ExpenseType getExpenseType() {
        return ExpenseType.EXACT;
    }
}
