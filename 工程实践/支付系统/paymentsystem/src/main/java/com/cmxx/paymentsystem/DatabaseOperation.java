package com.cmxx.paymentsystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.database.*;
import com.cmxx.domain.Account;
import com.cmxx.domain.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// 数据库操作类
@Service
public class DatabaseOperation {

    @Autowired
    private PaymentData paymentData;

    @Autowired
    private DrawingData drawingData;

    @Autowired
    private AccountData accountData;

    @Autowired
    private ReconciliationData reconciliationData;

    @Autowired
    private CheckData checkData;

    // 插入一条支付信息
    public void insertPayment(String responseJsonData) {
        //{"response_time": "2019-08-29 17:15:25", "request_id": "20190829171520123456", "response_id": "20190829171525654321", "saler_id": "saler2", "total_fee": "100", "reponse_code": "SUCCESS", "response_msg": "OK"}
        JSONObject jsonObject = JSON.parseObject(responseJsonData);
        paymentData.insert(jsonObject.getString("response_time"), jsonObject.getString("request_id"), jsonObject.getString("response_id"), jsonObject.getString("saler_id"), jsonObject.getDouble("total_fee"), jsonObject.getString("reponse_code"), jsonObject.getString("response_msg"));
    }

    // 插入一条提款信息
    public void insertDrawing(String responseJsonData) {
        //{"response_time": "2019-08-29 17:16:30", "request_time": "2019-08-29 17:16:21", "request_id": "20190829171621334455", "withdraw_money": "100", "saler_id": "saler1", "response_id": "20190829171630554433", "reponse_code": "FAIL", "response_msg": "账户余额不足，提款失败"}
        JSONObject jsonObject = JSON.parseObject(responseJsonData);
        drawingData.insert(jsonObject.getString("response_time"), jsonObject.getString("request_time"), jsonObject.getString("request_id"), jsonObject.getDouble("withdraw_money"), jsonObject.getString("saler_id"), jsonObject.getString("response_id"), jsonObject.getString("reponse_code"), jsonObject.getString("response_msg"));
    }

    // 插入一条用户信息
    public void insertAccount(String responseJsonData) {
        JSONObject jsonObject = JSON.parseObject(responseJsonData);
        accountData.insertAccount(jsonObject.getString("saler_id"), jsonObject.getDouble("total_fee"));
    }

    // 查找一条用户信息
    public boolean selectAccount(String saler_id) {
        Object i = accountData.selectAccount(saler_id);
        if (i == null) {
            System.out.println("查无此人");
            return false;
        } else {
            return true;
        }
    }

    // 用户表中获取balance信息
    public double getBalance(String saler_id) {
        Object obj = accountData.selectAccount(saler_id);
        BigDecimal balance = (BigDecimal) obj;
        return balance.doubleValue();
    }

    // 更新用户表一条信息
    public void updateAccount(String saler_id, double money) {
        double balance = getBalance(saler_id);
        double totalBalance = balance + money;
        accountData.updateAccount(totalBalance, saler_id);
    }

    // 查找支付表中所有信息
    public List<ResponseData> findAllFromPayment() {
        List<ResponseData> responseData = paymentData.selectPayment();
        return responseData;
    }

    // 支付对账表插入一条信息
    public void insertPayment_Reconciliation(String request_id, String response_id, double total_fee, String diff_type) {
        Object object = reconciliationData.selectPayment_Reconciliation(request_id);
        if (object == null) {
            reconciliationData.insertPayment_Reconciliation(request_id, response_id, total_fee, diff_type);
        } else {
            reconciliationData.updatePayment_Reconciliation(total_fee, diff_type, request_id);
        }
    }

    // 查找用户表中所有信息
    public List<Account> findAllFromAccount() {
        List<Account> accounts = accountData.selectAll();
        return accounts;
    }

    // 插入稽核表一条信息
    public void insertCheck(String saler_id, double balance, String check_result, double check_money) {
        Object object = checkData.selectCheck(saler_id);
        if (object == null) {
            checkData.insertCheck(saler_id, balance, check_result, check_money);
        } else {
            checkData.updateCheck(balance, check_result, check_money, saler_id);
        }
    }

    // 查找支付表中的所有行数
    public int selectNumFromPayment() {
        int totalNum = paymentData.selectNum();
        return totalNum;
    }

    // 查找提款表中的所有行数
    public int selectNumFromDrawing() {
        int totalNum = drawingData.selectNum();
        return totalNum;
    }
}
