package com.cmxx.database;

import com.cmxx.domain.ResponseData;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 保存支付信息的表
@Mapper
public interface PaymentData {

    //{"response_time": "2019-08-29 17:15:25", "request_id": "20190829171520123456", "response_id": "20190829171525654321", "saler_id": "saler2", "total_fee": "100", "reponse_code": "SUCCESS", "response_msg": "OK"}
    @Insert("INSERT INTO PAYMENT(response_time,request_id,response_id,saler_id,total_fee,reponse_code,response_msg) VALUES(#{response_time},#{request_id},#{response_id},#{saler_id},#{total_fee},#{reponse_code},#{response_msg})")
    void insert(@Param("response_time") String response_time, @Param("request_id") String request_id, @Param("response_id") String response_id, @Param("saler_id") String saler_id, @Param("total_fee") double total_fee, @Param("reponse_code") String reponse_code, @Param("response_msg") String response_msg);

    @Results({
            @Result(property = "request_id", column = "request_id"),
            @Result(property = "response_time", column = "response_time"),
            @Result(property = "response_id", column = "response_id"),
            @Result(property = "saler_id", column = "saler_id"),
            @Result(property = "reponse_code", column = "reponse_code"),
            @Result(property = "response_msg", column = "response_msg"),
            @Result(property = "total_fee", column = "total_fee")})
    @Select("SELECT * FROM PAYMENT")
    List<ResponseData> selectPayment();

    @Select("SELECT COUNT(*) FROM PAYMENT")
    int selectNum();
}
