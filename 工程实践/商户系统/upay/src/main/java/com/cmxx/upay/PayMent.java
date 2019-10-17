package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.cmxx.domain.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

// 支付操作
@Service
public class PayMent {

    private Map<String, String> payMentMap = new HashMap<String, String>();

    @Autowired
    private DatabaseOperation databaseOperation;

    private int mapNum = 0;

    @Async
    public void myPayMent(String userId, double payMent) {
        try {
            Random random = new Random();
            //random.nextInt(20);
            Thread.sleep(1000 * (random.nextInt(5) + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestData requestData = new RequestData();
        requestData.setSaler_id(userId);
        requestData.setTotal_fee(payMent);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String request_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String request_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + userId;
        requestData.setRequest_time(request_time);
        requestData.setRequest_id(request_id);
        String requestJsonData = JSON.toJSONString(requestData);
        PayRequest payRequest = new PayRequest();
        String strReponse = payRequest.httpRequest(requestJsonData);
        //System.out.println("--------" + requestJsonData);
        payMentMap.put(request_id + mapNum, requestJsonData);
        mapNum++;
    }

    // 定时从缓存中读取数据
    @Scheduled(cron = "0/30 * * * * ?")
    public void timer() {
        if (!payMentMap.isEmpty()) {
            Iterator<String> hashmapIter = payMentMap.keySet().iterator();
            while (hashmapIter.hasNext()) {
                String keyName = hashmapIter.next();
                databaseOperation.insertPayment(payMentMap.get(keyName));
            }
            payMentMap.clear();
        }
        System.out.println("测试时间" + new Timestamp(System.currentTimeMillis()));
    }
}


