package com.cmxx.upay;

import org.springframework.web.client.RestTemplate;

// 向支付系统提出稽核请求
public class CheckRequest {
    public void httpRequest() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://127.0.0.1:8080/paymentsystem/check", Boolean.class);
    }
}
