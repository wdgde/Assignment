package com.mytutorial.controller;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mytutorial.model.Account;

@RestController
@RequestMapping("/v1/forecasting")
public class AccountController {

    //produces={MediaType.APPLICATION_XML_VALUE}
    @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces={MediaType.APPLICATION_XML_VALUE})
    public Account[] getAccounts() {
        Account[] accounts = new Account[] { new Account("123", "John R", BigDecimal.valueOf(235.00)),
                new Account("345", "Peter J", BigDecimal.valueOf(2505.60)) };

        return accounts;
    }

}
