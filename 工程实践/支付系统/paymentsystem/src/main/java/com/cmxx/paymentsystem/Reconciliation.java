package com.cmxx.paymentsystem;

import com.cmxx.domain.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 对账操作
@Service
public class Reconciliation {

    @Autowired
    private DatabaseOperation databaseOperation;

    public void paymentReconciliation(List<String> dataList) {
        List<ResponseData> responseData = databaseOperation.findAllFromPayment();
        if (dataList != null && !dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (i != 0) {//不读取第一行
                    String s = dataList.get(i);
                    judge(responseData, s);
                }
            }
        }
        lastJudge(responseData, dataList);
    }

    // 判断对账结果
    public void judge(List<ResponseData> responseData, String s) {
        Boolean isExist = false;
        String diff_type = "";
        String request_id = "";
        String response_id = "";
        double money = 0;
        double paymentsystem_money = 0;
        double upay_money = 0;
        String[] data = s.split(",");
        for (ResponseData r : responseData) {
            upay_money = Double.parseDouble(data[data.length - 1]);
            if (r.getRequest_id().equals(data[0])) {
                isExist = true;
                paymentsystem_money = r.getTotal_fee();
                if (paymentsystem_money == upay_money) {
                    diff_type = "F000";
                    money = paymentsystem_money;
                } else {
                    diff_type = "F115";
                    money = upay_money;
                }
                request_id = r.getRequest_id();
                response_id = r.getResponse_id();
            }
        }
        if (!isExist) {
            diff_type = "F114";
            money = upay_money;
            request_id = data[0];
            response_id = "暂无";
        }
        databaseOperation.insertPayment_Reconciliation(request_id, response_id, money, diff_type);
    }

    // 完成未比对数据的对账
    public void lastJudge(List<ResponseData> responseData, List<String> dataList) {
        String diff_type = "";
        double money = 0;
        String[] headData = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            if (i != 0) {//不读取第一行
                String s = dataList.get(i);
                String[] data = s.split(",");
                headData[i] = data[0];
            }
        }

        for (ResponseData r : responseData) {
            for (int i = 1; i < headData.length; i++) {
                if (r.getRequest_id().equals(headData[i])) {
                    break;
                }
                if (!r.getRequest_id().equals(headData[i]) && i == headData.length - 1) {
                    diff_type = "F113";
                    money = r.getTotal_fee();
                    databaseOperation.insertPayment_Reconciliation(r.getRequest_id(), r.getResponse_id(), money, diff_type);
                }
            }
        }
    }
}
