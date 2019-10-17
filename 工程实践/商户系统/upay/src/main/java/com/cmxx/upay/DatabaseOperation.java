package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.database.DrawingData;
import com.cmxx.database.PaymentData;
import com.cmxx.domain.DrawingRequestData;
import com.cmxx.domain.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 数据库操作类
@Service
public class DatabaseOperation {

    @Autowired
    private PaymentData paymentData;

    @Autowired
    private DrawingData drawingData;

    // 插入一条支付信息
    public void insertPayment(String requestJsonData) {
        JSONObject jsonObject = JSON.parseObject(requestJsonData);
        paymentData.insert(jsonObject.getString("request_time"), jsonObject.getString("request_id"), jsonObject.getString("saler_id"), jsonObject.getDouble("total_fee"));
    }

    //插入一条提款信息
    public void insertDrawing(String requestJsonData) {
        JSONObject jsonObject = JSON.parseObject(requestJsonData);
        drawingData.insert(jsonObject.getString("request_time"), jsonObject.getString("request_id"), jsonObject.getDouble("withdraw_money"), jsonObject.getString("saler_id"), jsonObject.getString("reponse_code"));
    }

    // 查找支付表里的所有数据
    public List<RequestData> findAllFromPayment() {
        List<RequestData> r = paymentData.selectAll();
        return r;
    }

    // 查找提款表里的所有数据
    public List<DrawingRequestData> findAllFromDrawing() {
        List<DrawingRequestData> r = drawingData.selectAll();
        return r;
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
