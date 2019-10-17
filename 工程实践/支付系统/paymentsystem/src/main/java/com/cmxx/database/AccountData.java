package com.cmxx.database;

import com.cmxx.domain.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

//保存balance的表
@Mapper
public interface AccountData {

    @Insert("INSERT INTO ACCOUNT(saler_id,balance) VALUES(#{saler_id},#{balance})")
    void insertAccount(@Param("saler_id") String saler_id, @Param("balance") double balance);

    @Select("SELECT BALANCE FROM ACCOUNT WHERE SALER_ID=#{saler_id}")
    Object selectAccount(@Param("saler_id") String saler_id);

    @Update("UPDATE ACCOUNT SET BALANCE=#{balance} WHERE SALER_ID=#{saler_id}")
    void updateAccount(@Param("balance") double balance, @Param("saler_id") String saler_id);

    @Results({
            @Result(property = "saler_id", column = "saler_id"),
            @Result(property = "balance", column = "balance")})
    @Select("SELECT * FROM ACCOUNT")
    List<Account> selectAll();
}
