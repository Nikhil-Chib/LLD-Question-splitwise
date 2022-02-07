package com.splitwise.service;

import com.splitwise.model.Balance;
import com.splitwise.model.User;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public class BalanceService {
    Map<String, List<Balance>> balances;
    UserService userService;

    public BalanceService(UserService userService) {
        this.balances = new HashMap<>();
        this.userService = userService;
    }

    public void updateBalance(String userIdPaid, String userIdOwes, Double amount) throws Exception{
        User payingUser = userService.getUserByUserId(userIdPaid);
        User owingUser = userService.getUserByUserId(userIdOwes);

        if(payingUser == null || owingUser == null) {
            throw new Exception("User Not found.");
        }

        List<Balance> owingUserBalances = this.getBalance(userIdOwes);
        Optional<Balance> o = owingUserBalances.stream().filter(owingUserBalance ->
                userIdPaid.equalsIgnoreCase(owingUserBalance.getUser().getUserId())).findFirst();

        if(!o.isPresent()) {
            addBalance(this.getBalance(userIdPaid), new Balance(owingUser, amount));
            return;
        }

        Balance payUserBalanceInOwingUser = o.get();
        Double updatedBalance = payUserBalanceInOwingUser.getBalance() - amount;

        if(updatedBalance <= 0) {
            removeBalance(owingUserBalances, userIdPaid, userIdOwes);
        }

        if(Math.abs(updatedBalance) > 0) {
            addBalance(this.getBalance(userIdPaid), new Balance(owingUser, Math.abs(updatedBalance)));
        }

    }

    private List<Balance> getBalance(String userId) {
        if(!balances.containsKey(userId)) {
            this.balances.put(userId, new ArrayList<>());
        }

        return this.balances.get(userId);
    }

    private void removeBalance(List<Balance> owingUserBalances, String userIdPaid, String userIdOwes) {
        List<Balance> updatedList = owingUserBalances.stream().filter(owingUserBalance ->
                !userIdPaid.equalsIgnoreCase(owingUserBalance.getUser().getUserId())).collect(Collectors.toList());

        this.balances.put(userIdOwes, updatedList);
    }

    private void addBalance(List<Balance> balances, Balance balance) {
        Optional<Balance> o = balances.stream().filter(balance1 -> balance.getUser().getUserId().equalsIgnoreCase(balance1.getUser().getUserId())).findFirst();
        if(!o.isPresent()) {
            balances.add(balance);
            return;
        }

        Balance existingBalance = o.get();
        existingBalance.setBalance(existingBalance.getBalance()+balance.getBalance());
    }

    public void showBalance(String userId) {
        List<String> balances = this.getBalance(userId).stream().map(balance -> String.format("%s owes %s : %s",
                balance.getUser().getUserId(), userId, balance.getBalance())).collect(Collectors.toList());

        for(String user: this.balances.keySet()) {
            if(userId.equalsIgnoreCase(user)) {
                continue;
            }

            List<Balance> otherUserBalances =  this.getBalance(user).stream().filter(balance ->
                    userId.equalsIgnoreCase(balance.getUser().getUserId())).collect(Collectors.toList());

            for(Balance balance : otherUserBalances) {
                balances.add(String.format("%s owes %s : %s", userId, user, balance.getBalance()));
            }
        }

        if(balances.isEmpty()) System.out.println("No Balances");
        else System.out.println(balances);
    }

    public void showBalance() {
        List<String> balances = new ArrayList<>();

        for(String userId : this.balances.keySet()) {
            List<String> output = this.getBalance(userId).stream().map(balance -> String.format("%s owes %s : %s",
                    balance.getUser().getUserId(), userId, balance.getBalance())).collect(Collectors.toList());

            if(!output.isEmpty()) {
                balances.addAll(output);
            }

        }

        if(balances.isEmpty()) System.out.println("No Balances");
        else System.out.println(balances);
    }
}
