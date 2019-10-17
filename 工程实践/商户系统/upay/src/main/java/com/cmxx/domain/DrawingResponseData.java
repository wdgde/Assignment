package com.cmxx.domain;

public class DrawingResponseData {
    private String response_time;
    private String request_time;
    private String request_id;
    private String response_id;
    private String saler_id;
    private double withdraw_money;
    private String reponse_code;
    private String response_msg;

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
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

    public double getWithdraw_money() {
        return withdraw_money;
    }

    public void setWithdraw_money(double withdraw_money) {
        this.withdraw_money = withdraw_money;
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
