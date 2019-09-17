package com.mytutorial.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private BigDecimal balance;

    public Account(String id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", name=" + name + ", balance=" + balance + "]";
    }

}
