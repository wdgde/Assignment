package com.cmxx.database;

import org.apache.ibatis.annotations.*;

// 保存对账信息的表
@Mapper
public interface ReconciliationData {

    @Insert("INSERT INTO payment_reconciliation(request_id,response_id,total_fee,diff_type) VALUES(#{request_id},#{response_id},#{total_fee},#{diff_type})")
    void insertPayment_Reconciliation(@Param("request_id") String request_id, @Param("response_id") String response_id, @Param("total_fee") double total_fee, @Param("diff_type") String diff_type);

    @Select("SELECT DIFF_TYPE FROM payment_reconciliation WHERE REQUEST_ID=#{request_id}")
    Object selectPayment_Reconciliation(@Param("request_id") String request_id);

    @Update("UPDATE payment_reconciliation SET total_fee=#{total_fee},diff_type=#{diff_type} WHERE REQUEST_ID=#{request_id}")
    void updatePayment_Reconciliation(@Param("total_fee") double total_fee, @Param("diff_type") String diff_type, @Param("request_id") String request_id);
}
