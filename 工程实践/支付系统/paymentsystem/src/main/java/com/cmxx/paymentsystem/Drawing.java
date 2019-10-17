package com.cmxx.paymentsystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.domain.DrawingResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

// 提款操作
@Service
public class Drawing {
    @Autowired
    private DatabaseOperation databaseOperation;

    public Object myDrawing(String jsonData) {
        try {
            Random random = new Random();
            //random.nextInt(20);
            Thread.sleep(1000 * (random.nextInt(5) + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(jsonData);
        DrawingResponseData drawingResponseData = new DrawingResponseData();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String response_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String response_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + jsonObject.getString("saler_id");
        drawingResponseData.setResponse_time(response_time);
        drawingResponseData.setRequest_time(jsonObject.getString("request_time"));
        drawingResponseData.setRequest_id(jsonObject.getString("request_id"));
        drawingResponseData.setWithdraw_money(jsonObject.getDouble("withdraw_money"));
        drawingResponseData.setSaler_id(jsonObject.getString("saler_id"));
        drawingResponseData.setResponse_id(response_id);
        if (!databaseOperation.selectAccount(jsonObject.getString("saler_id"))) {
            drawingResponseData.setReponse_code("fail");
            drawingResponseData.setResponse_msg("查无此人");
        } else {
            double money = databaseOperation.getBalance(jsonObject.getString("saler_id"));
            if (jsonObject.getDouble("withdraw_money") > money) {
                drawingResponseData.setReponse_code("fail");
                drawingResponseData.setResponse_msg("余额不足");
            } else {
                drawingResponseData.setReponse_code("success");
                drawingResponseData.setResponse_msg("提款成功");
                databaseOperation.updateAccount(jsonObject.getString("saler_id"), -jsonObject.getDouble("withdraw_money"));
            }
        }

        String responseJsonData = JSON.toJSONString(drawingResponseData);
        databaseOperation.insertDrawing(responseJsonData);
        return responseJsonData;
    }
}
