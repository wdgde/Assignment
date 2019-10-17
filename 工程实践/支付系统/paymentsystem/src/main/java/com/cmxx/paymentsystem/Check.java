package com.cmxx.paymentsystem;

import com.cmxx.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 稽核操作
@Service
public class Check {
    @Autowired
    private DatabaseOperation databaseOperation;

    public void checkMoney(List<String> paymentDataList, List<String> drawDataList) {
        List<Account> accounts = databaseOperation.findAllFromAccount();
        for (Account a : accounts) {
            String saler_id = a.getSaler_id();
            double balance = a.getBalance();
            String check_result = "";
            double check_money = 0;
            double paymentMoney = findPaymentMoney(paymentDataList, saler_id);
            double drawMoney = findDrawMoney(drawDataList, saler_id);
            double money = paymentMoney - drawMoney;
            check_money = money;
            if (money == a.getBalance()) {
                check_result = "success";
                databaseOperation.insertCheck(saler_id, balance, check_result, check_money);
            } else {
                check_result = "fail";
                databaseOperation.insertCheck(saler_id, balance, check_result, check_money);
            }
        }
    }

    // 找到支付的信息
    public double findPaymentMoney(List<String> paymentDataList, String saler_id) {
        double money = 0;
        for (int i = 0; i < paymentDataList.size(); i++) {
            if (i != 0) {
                String s = paymentDataList.get(i);
                String[] data = s.split(",");
                if (data[1].equals(saler_id)) {
                    money += Double.parseDouble(data[data.length - 1]);
                }
            }
        }
        return money;
    }

    // 找到提款的信息
    public double findDrawMoney(List<String> drawDataList, String saler_id) {
        double money = 0;
        for (int i = 0; i < drawDataList.size(); i++) {
            if (i != 0) {
                String s = drawDataList.get(i);
                String[] data = s.split(",");
                if (data[1].equals(saler_id)) {
                    if (data[data.length - 1].equals("success")) {
                        money += Double.parseDouble(data[data.length - 2]);
                    }
                }
            }
        }
        return money;
    }
}
