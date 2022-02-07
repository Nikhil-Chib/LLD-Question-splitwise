package com.splitwise.formatters;

import com.splitwise.calculators.ExpenseCalculators;
import com.splitwise.model.Expense;
import com.splitwise.model.ExpenseType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExpenseFormatter {
    private static final Logger LOGGER = Logger.getLogger("ExpenseFormatter");

    public Expense formatExpense(String[] splitInput) {
        Expense formattedExpense = new Expense();
        try {

            formattedExpense.setPayingUser(splitInput[1]);

            formattedExpense.setPaidAmount(Double.parseDouble(splitInput[2]));

            int noOfUsers = Integer.parseInt(splitInput[3]);

            List<String> owingUsers = new ArrayList<>();

            int i = 4;
            while (i - 4 < noOfUsers) {
                owingUsers.add(splitInput[i]);
                i++;
            }
            formattedExpense.setOwingUsers(owingUsers);

            formattedExpense.setExpenseType(ExpenseType.valueOf(splitInput[i++]));

            int j = 0;
            List<Double> owingAmount = new ArrayList<>();
            while (ExpenseType.EQUAL != formattedExpense.getExpenseType() && j < noOfUsers) {
                owingAmount.add(Double.valueOf(splitInput[i + j]));
                j++;
            }

            formattedExpense.setOwingAmt(owingAmount);
        } catch(Exception e) {

            return null;
        }
        return formattedExpense;
    }
}
