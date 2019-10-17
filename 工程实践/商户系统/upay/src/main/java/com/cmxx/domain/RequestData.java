package com.cmxx.domain;

public class RequestData {
    //{"request_time": "2019-08-29 17:15:20", "request_id": "20190829171520123456", "saler_id": "saler2", "total_fee": "100"}
    private String request_id;
    private String request_time;
    private String saler_id;
    private double total_fee;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
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
}
