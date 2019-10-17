package com.cmxx.domain;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"response_time", "request_id", "response_id", "saler_id", "total_fee", "reponse_code", "response_msg"})
public class ResponseData {
    //{"response_time": "2019-08-29 17:15:25", "request_id": "20190829171520123456", "response_id": "20190829171525654321", "saler_id": "saler2", "total_fee": "100", "reponse_code": "SUCCESS", "response_msg": "OK"}
    private String response_time;
    private String request_id;
    private String response_id;
    private String saler_id;
    private double total_fee;
    private String reponse_code;
    private String response_msg;

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getSaler_id() {
        return saler_id;
    }

    public void setSaler_id(String saler_id) {
        this.saler_id = saler_id;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public String getReponse_code() {
        return reponse_code;
    }

    public void setReponse_code(String reponse_code) {
        this.reponse_code = reponse_code;
    }

    public String getResponse_msg() {
        return response_msg;
    }

    public void setResponse_msg(String response_msg) {
        this.response_msg = response_msg;
    }
}
