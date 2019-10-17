package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.cmxx.domain.ResponseData;
import org.springframework.web.client.RestTemplate;

// 向支付系统提出支付请求
public class PayRequest {
    public String httpRequest(String jsonData) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseData responseData = restTemplate.getForObject("http://127.0.0.1:8080/paymentsystem/payment/" + jsonData, ResponseData.class, jsonData);
        String responseJsonData = JSON.toJSONString(responseData);
        return responseJsonData;
    }
}
