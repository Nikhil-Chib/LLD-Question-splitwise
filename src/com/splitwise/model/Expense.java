package com.splitwise.model;

import java.util.List;


public class Expense {
    ExpenseType expenseType;
    String payingUser;
    Double paidAmount;
    List<String> owingUsers;
    List<Double> owingAmt;

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public String getPayingUser() {
        return payingUser;
    }

    public void setPayingUser(String payingUser) {
        this.payingUser = payingUser;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public List<String> getOwingUsers() {
        return owingUsers;
    }

    public void setOwingUsers(List<String> owingUsers) {
        this.owingUsers = owingUsers;
    }

    public List<Double> getOwingAmt() {
        return owingAmt;
    }

    public void setOwingAmt(List<Double> owingAmt) {
        this.owingAmt = owingAmt;
    }
}
