package com.cmxx.upay;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

// 向支付系统提出对账请求
public class ReconciliationRequest {
    public void paymentHttpRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String paymentFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\payment.csv";
        FileSystemResource resource = new FileSystemResource(new File(paymentFilePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("deviceId", "支付账单");
        param.add("file", resource);
        // POST方法
        restTemplate.postForObject("http://127.0.0.1:8080/paymentsystem/reconciliation", param, Boolean.class);
    }

    public void drawHttpRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String drawingFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\drawing.csv";
        FileSystemResource resource = new FileSystemResource(new File(drawingFilePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("deviceId", "提款账单");
        param.add("file", resource);
        // POST方法
        restTemplate.postForObject("http://127.0.0.1:8080/paymentsystem/reconciliation", param, Boolean.class);
    }
}
