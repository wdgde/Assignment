package com.cmxx.database;

import com.cmxx.domain.DrawingRequestData;
import org.apache.ibatis.annotations.*;

import java.util.List;


// 存放提款信息的表
@Mapper
public interface DrawingData {
    //{"request_time": "2019-08-29 17:16:21", "request_id": "20190829171621334455", "withdraw_money": "100", "saler_id": "saler1"}
    @Insert("INSERT INTO DRAWING(request_time,request_id,withdraw_money,saler_id,reponse_code) VALUES(#{request_time},#{request_id},#{withdraw_money},#{saler_id},#{reponse_code})")
    void insert(@Param("request_time") String request_time, @Param("request_id") String request_id, @Param("withdraw_money") double withdraw_money, @Param("saler_id") String saler_id, @Param("reponse_code") String reponse_code);

    @Results({
            @Result(property = "request_time", column = "request_time"),
            @Result(property = "request_id", column = "request_id"),
            @Result(property = "saler_id", column = "saler_id"),
            @Result(property = "withdraw_money", column = "withdraw_money"),
            @Result(property = "reponse_code", column = "reponse_code")})
    @Select("SELECT * FROM DRAWING")
    List<DrawingRequestData> selectAll();

    @Select("SELECT COUNT(*) FROM DRAWING")
    int selectNum();
}
