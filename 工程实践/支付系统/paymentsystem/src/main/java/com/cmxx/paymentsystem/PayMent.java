package com.cmxx.paymentsystem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.domain.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Object myPayMent(String jsonData) {
        try {
            Random random = new Random();
            //random.nextInt(20);
            Thread.sleep(1000 * (random.nextInt(5) + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(jsonData);
        ResponseData responseData = new ResponseData();
        //{"response_time": "2019-08-29 17:15:25", "request_id": "20190829171520123456", "response_id": "20190829171525654321", "saler_id": "saler2", "total_fee": "100", "reponse_code": "SUCCESS", "response_msg": "OK"}
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String response_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String response_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + jsonObject.getString("saler_id");
        responseData.setResponse_time(response_time);
        responseData.setRequest_id(jsonObject.getString("request_id"));
        responseData.setResponse_id(response_id);
        responseData.setSaler_id(jsonObject.getString("saler_id"));
        responseData.setTotal_fee(jsonObject.getDouble("total_fee"));
        responseData.setReponse_code("success");
        responseData.setResponse_msg("ok");
        String responseJsonData = JSON.toJSONString(responseData);
        payMentMap.put(response_id + mapNum, responseJsonData);
        mapNum++;
        //databaseOperation.insertPayment(responseJsonData);
        return responseJsonData;
    }

    // 定时从缓存中读取
    @Scheduled(cron = "0/30 * * * * ?")
    public void timer() {
        if (!payMentMap.isEmpty()) {
            Iterator<String> hashmapIter = payMentMap.keySet().iterator();
            while (hashmapIter.hasNext()) {
                String keyName = hashmapIter.next();
                JSONObject jsonObject = JSON.parseObject(payMentMap.get(keyName));
                databaseOperation.insertPayment(payMentMap.get(keyName));
                if (databaseOperation.selectAccount(jsonObject.getString("saler_id"))) {
                    databaseOperation.updateAccount(jsonObject.getString("saler_id"), jsonObject.getDouble("total_fee"));
                } else {
                    databaseOperation.insertAccount(payMentMap.get(keyName));
                }
            }
            payMentMap.clear();
        }
        System.out.println("测试时间" + new Timestamp(System.currentTimeMillis()));
    }
}
