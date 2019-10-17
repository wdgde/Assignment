package com.cmxx.database;

import org.apache.ibatis.annotations.*;

// 保存稽核信息的表
@Mapper
public interface CheckData {

    @Insert("INSERT INTO CHECKMONEY(saler_id,balance,check_result,check_money) VALUES(#{saler_id},#{balance},#{check_result},#{check_money})")
    void insertCheck(@Param("saler_id") String saler_id, @Param("balance") double balance, @Param("check_result") String check_result, @Param("check_money") double check_money);

    //@Select("SELECT BALANCE FROM ACCOUNT WHERE SALER_ID=#{saler_id}")
    @Select("SELECT CHECK_RESULT FROM CHECKMONEY WHERE SALER_ID=#{saler_id}")
    Object selectCheck(@Param("saler_id") String saler_id);

    @Update("UPDATE CHECKMONEY SET BALANCE=#{balance},CHECK_RESULT=#{check_result},CHECK_MONEY=#{check_money} WHERE SALER_ID=#{saler_id}")
    void updateCheck(@Param("balance") double balance, @Param("check_result") String check_result, @Param("check_money") double check_money, @Param("saler_id") String saler_id);

}
