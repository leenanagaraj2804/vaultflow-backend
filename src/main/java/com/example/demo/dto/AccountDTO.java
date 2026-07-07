package com.example.demo.dto;

public class AccountDTO {

    private Long id;
    private String accountName;
    private String accountType;
    private Double balance;
    private Double interestRate;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String accountName, String accountType, Double balance, Double interestRate) {
        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
