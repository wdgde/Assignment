package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.cmxx.domain.DrawingResponseData;
import org.springframework.web.client.RestTemplate;

// 向支付系统提出提款请求
public class DrawingRequest {
    public String httpRequest(String jsonData) {
        RestTemplate restTemplate = new RestTemplate();
        DrawingResponseData drawingResponseData = restTemplate.getForObject("http://127.0.0.1:8080/paymentsystem/drawing/" + jsonData, DrawingResponseData.class, jsonData);
        String responseJsonData = JSON.toJSONString(drawingResponseData);
        return responseJsonData;
    }
}
