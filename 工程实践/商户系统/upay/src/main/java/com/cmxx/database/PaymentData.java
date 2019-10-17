package com.cmxx.database;

import com.cmxx.domain.RequestData;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 存放支付信息的表
@Mapper
public interface PaymentData {

    //{"request_time": "2019-08-29 17:15:20", "request_id": "20190829171520123456", "saler_id": "saler2", "total_fee": "100"}
    @Insert("INSERT INTO PAYMENT(request_time,request_id,saler_id,total_fee) VALUES(#{request_time},#{request_id},#{saler_id},#{total_fee})")
    void insert(@Param("request_time") String request_time, @Param("request_id") String request_id, @Param("saler_id") String saler_id, @Param("total_fee") double total_fee);

    @Results({
            @Result(property = "request_time", column = "request_time"),
            @Result(property = "request_id", column = "request_id"),
            @Result(property = "saler_id", column = "saler_id"),
            @Result(property = "total_fee", column = "total_fee")})
    @Select("SELECT * FROM PAYMENT")
    List<RequestData> selectAll();

    @Select("SELECT COUNT(*) FROM PAYMENT")
    int selectNum();
}
