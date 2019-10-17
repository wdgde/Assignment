package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.domain.DrawingRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

// 提款操作
@Service
public class Drawing {

    @Autowired
    private DatabaseOperation databaseOperation;

    @Async
    public void myDrawing(String userId, double drawing) {
        try {
            Random random = new Random();
            //random.nextInt(20);
            Thread.sleep(1000 * (random.nextInt(5) + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DrawingRequestData drawingRequestData = new DrawingRequestData();
        drawingRequestData.setSaler_id(userId);
        drawingRequestData.setWithdraw_money(drawing);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String request_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String request_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + userId;
        drawingRequestData.setRequest_time(request_time);
        drawingRequestData.setRequest_id(request_id);

        String requestJsonData = JSON.toJSONString(drawingRequestData);
        DrawingRequest drawingRequest = new DrawingRequest();
        String strReponse = drawingRequest.httpRequest(requestJsonData);

        // 提款返回的信息写入库
        JSONObject jsonObject = JSON.parseObject(strReponse);
        drawingRequestData.setReponse_code(jsonObject.getString("reponse_code"));
        String responseJsonData = JSON.toJSONString(drawingRequestData);

        databaseOperation.insertDrawing(responseJsonData);
    }
}
