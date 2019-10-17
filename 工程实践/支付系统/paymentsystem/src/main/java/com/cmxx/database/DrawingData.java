package com.cmxx.database;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// 保存提款信息的表
@Mapper
public interface DrawingData {
    //{"response_time": "2019-08-29 17:16:30", "request_time": "2019-08-29 17:16:21", "request_id": "20190829171621334455", "withdraw_money": "100", "saler_id": "saler1", "response_id": "20190829171630554433", "reponse_code": "FAIL", "response_msg": "账户余额不足，提款失败"}

    @Insert("INSERT INTO DRAWING(response_time,request_time,request_id,withdraw_money,saler_id,response_id,reponse_code,response_msg) VALUES(#{response_time},#{request_time},#{request_id},#{withdraw_money},#{saler_id},#{response_id},#{reponse_code},#{response_msg})")
    void insert(@Param("response_time") String response_time, @Param("request_time") String request_time, @Param("request_id") String request_id, @Param("withdraw_money") double withdraw_money, @Param("saler_id") String saler_id, @Param("response_id") String response_id, @Param("reponse_code") String reponse_code, @Param("response_msg") String response_msg);

    @Select("SELECT COUNT(*) FROM DRAWING")
    int selectNum();
}
