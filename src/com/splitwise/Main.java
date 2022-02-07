package com.splitwise;

import com.splitwise.calculators.EqualExpenseCalculator;
import com.splitwise.calculators.ExactExpenseCalculator;
import com.splitwise.calculators.ExpenseCalculators;
import com.splitwise.calculators.PercentExpenseCalculator;
import com.splitwise.formatters.ExpenseFormatter;
import com.splitwise.model.User;
import com.splitwise.service.BalanceService;
import com.splitwise.service.ExpenseService;
import com.splitwise.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here


        User u1 = new User("u1", "u1", "", "");
        User u2 = new User("u2", "u2", "", "");
        User u3 = new User("u3", "u3", "", "");
        User u4 = new User("u4", "u4", "", "");

        UserService userService = new UserService();
        userService.addUsers(u1);
        userService.addUsers(u2);
        userService.addUsers(u3);
        userService.addUsers(u4);

        BalanceService balanceService = new BalanceService(userService);

        EqualExpenseCalculator equalExpenseCalculator = new EqualExpenseCalculator(balanceService);
        ExactExpenseCalculator exactExpenseCalculator = new ExactExpenseCalculator(balanceService);
        PercentExpenseCalculator percentExpenseCalculator = new PercentExpenseCalculator(balanceService);

        List<ExpenseCalculators> expenseCalculators = new ArrayList<>();
        expenseCalculators.add(equalExpenseCalculator);
        expenseCalculators.add(exactExpenseCalculator);
        expenseCalculators.add(percentExpenseCalculator);

        ExpenseFormatter expenseFormatter = new ExpenseFormatter();
        ExpenseService expenseService = new ExpenseService(expenseFormatter, expenseCalculators);

        List<String> commands = new ArrayList<>();
        commands.add("SHOW u1");
        commands.add("SHOW");
        commands.add("EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL");
        commands.add("SHOW u4");
        commands.add("SHOW u1");
        commands.add("EXPENSE u1 1250 2 u2 u3 EXACT 370 880");
        commands.add("SHOW");
        commands.add("EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20");
        commands.add("SHOW u1");
        commands.add("SHOW");



        for(String command: commands) {
            String[] splitInput = command.split(" ");
            if("SHOW".equalsIgnoreCase(splitInput[0])) {
                if(splitInput.length > 1) {
                    balanceService.showBalance(splitInput[1]);
                } else {
                    balanceService.showBalance();
                }
            } else if("EXPENSE".equalsIgnoreCase(splitInput[0])) {
                expenseService.calculateExpense(splitInput);
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}
