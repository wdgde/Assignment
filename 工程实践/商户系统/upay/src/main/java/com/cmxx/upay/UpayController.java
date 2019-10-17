package com.cmxx.upay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmxx.domain.DrawingRequestData;
import com.cmxx.domain.RequestData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/upay")     // 通过这里配置使下面的映射都在/upay下，可去除
public class UpayController {

    @Autowired
    private PayMent payMent;

    @Autowired
    private Drawing drawing;

    @Autowired
    private CsvFile csvFile;

    @ApiOperation(value = "多线程支付请求", notes = "根据输入金额进行支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId1", value = "收款用户ID_1", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "payMent1", value = "支付金额_1", required = true, paramType = "path", dataType = "double"),
            @ApiImplicitParam(name = "userId2", value = "收款用户ID_2", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "payMent2", value = "支付金额_2", required = true, paramType = "path", dataType = "double"),
            @ApiImplicitParam(name = "userId3", value = "收款用户ID_3", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "payMent3", value = "支付金额_3", required = true, paramType = "path", dataType = "double")
    })
    @RequestMapping(value = {"/threadPayment/{userId1}/{payMent1}/{userId2}/{payMent2}/{userId3}/{payMent3}"}, method = RequestMethod.POST)
    public String threadPay(@PathVariable String userId1, @PathVariable double payMent1, @PathVariable String userId2, @PathVariable double payMent2, @PathVariable String userId3, @PathVariable double payMent3) {
        payMent.myPayMent(userId1, payMent1);
        payMent.myPayMent(userId3, payMent3);
        payMent.myPayMent(userId2, payMent2);
        return "success";
    }

    @Autowired
    private DatabaseOperation databaseOperation;

    @ApiOperation(value = "支付请求", notes = "根据输入金额进行支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "收款用户ID", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "payMent", value = "支付金额", required = true, paramType = "path", dataType = "double")
    })
    @RequestMapping(value = {"/payment/{userId}/{payMent}"}, method = RequestMethod.POST)
    public String payMoney(@PathVariable String userId, @PathVariable double payMent) {
        RequestData requestData = new RequestData();
        requestData.setSaler_id(userId);
        requestData.setTotal_fee(payMent);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String request_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String request_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + userId;
        requestData.setRequest_time(request_time);
        requestData.setRequest_id(request_id);
        String requestJsonData = JSON.toJSONString(requestData);
        PayRequest payRequest = new PayRequest();
        String strReponse = payRequest.httpRequest(requestJsonData);
        //DatabaseOperation databaseOperation = new DatabaseOperation();
        databaseOperation.insertPayment(requestJsonData);
        return strReponse;
    }

    @ApiOperation(value = "多线程提款请求", notes = "根据输入金额进行提款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId1", value = "提款用户ID_1", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "drawing1", value = "提款金额_1", required = true, paramType = "path", dataType = "double"),
            @ApiImplicitParam(name = "userId2", value = "提款用户ID_2", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "drawing2", value = "提款金额_2", required = true, paramType = "path", dataType = "double"),
            @ApiImplicitParam(name = "userId3", value = "提款用户ID_3", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "drawing3", value = "提款金额_3", required = true, paramType = "path", dataType = "double")
    })
    @RequestMapping(value = {"/threadDraw/{userId1}/{drawing1}/{userId2}/{drawing2}/{userId3}/{drawing3}"}, method = RequestMethod.POST)
    public String threadDraw(@PathVariable String userId1, @PathVariable double drawing1, @PathVariable String userId2, @PathVariable double drawing2, @PathVariable String userId3, @PathVariable double drawing3) {
        drawing.myDrawing(userId1, drawing1);
        drawing.myDrawing(userId2, drawing2);
        drawing.myDrawing(userId3, drawing3);
        return "success";
    }

    @ApiOperation(value = "提款请求", notes = "根据输入金额进行提款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "提款用户ID", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "drawing", value = "提款金额", required = true, paramType = "path", dataType = "double")
    })
    @RequestMapping(value = {"/drawing/{userId}/{drawing}"}, method = RequestMethod.POST)
    public String drawMoney(@PathVariable String userId, @PathVariable double drawing) {
        DrawingRequestData drawingRequestData = new DrawingRequestData();
        drawingRequestData.setSaler_id(userId);
        drawingRequestData.setWithdraw_money(drawing);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String request_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        String request_id = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp) + userId;
        drawingRequestData.setRequest_time(request_time);
        drawingRequestData.setRequest_id(request_id);
        String requestJsonData = JSON.toJSONString(drawingRequestData);
        DrawingRequest drawingRequest = new DrawingRequest();
        String strReponse = drawingRequest.httpRequest(requestJsonData);
        // 有问题
        JSONObject jsonObject = JSON.parseObject(strReponse);
        String isSuccess = jsonObject.getString("reponse_code");
        if (isSuccess.equals("success")) {
            drawingRequestData.setReponse_code("success");
        } else {
            drawingRequestData.setReponse_code("fail");
        }

        databaseOperation.insertDrawing(requestJsonData);
        return strReponse;
    }

    @ApiOperation(value = "对账请求", notes = "")
    @RequestMapping(value = {"/reconciliation"}, method = RequestMethod.GET)
    public List<RequestData> reconciliation() {
        List<RequestData> requestData = databaseOperation.findAllFromPayment();
        List<String> dataList = new ArrayList<String>();
        dataList.add("request_id,saler_id,request_time,total_fee");
        for (RequestData r : requestData) {
            String data = r.getRequest_id() + "," + r.getSaler_id() + "," + r.getRequest_time() + "," + r.getTotal_fee();
            dataList.add(data);
        }

        String paymentFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\payment.csv";
        boolean isSuccess = csvFile.exportCsv(new File(paymentFilePath), dataList);
        System.out.println("支付对账" + isSuccess);

        List<DrawingRequestData> drawingRequestData = databaseOperation.findAllFromDrawing();
        List<String> drawDataList = new ArrayList<String>();
        drawDataList.add("request_id,saler_id,request_time,withdraw_money,reponse_code");
        for (DrawingRequestData d : drawingRequestData) {
            String data = d.getRequest_id() + "," + d.getSaler_id() + "," + d.getRequest_time() + "," + d.getWithdraw_money() + "," + d.getReponse_code();
            drawDataList.add(data);
        }
        String drawingFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\drawing.csv";
        boolean drawisSuccess = csvFile.exportCsv(new File(drawingFilePath), drawDataList);
        System.out.println("提款对账" + drawisSuccess);

        ReconciliationRequest reconciliationRequest = new ReconciliationRequest();
        reconciliationRequest.paymentHttpRequest();
        reconciliationRequest.drawHttpRequest();
        return requestData;
    }

    @ApiOperation(value = "稽核请求", notes = "")
    @RequestMapping(value = {"/check"}, method = RequestMethod.GET)
    public String check() {
        CheckRequest checkRequest = new CheckRequest();
        checkRequest.httpRequest();
        return "success";
    }

    @ApiOperation(value = "多线程支付与提款（同一个账户）", notes = "")
    @RequestMapping(value = {"/threadPayAndDrawing"}, method = RequestMethod.GET)
    public String threadPayAndDrawing() {
        payMent.myPayMent("1", 20);
        drawing.myDrawing("1", 130);
        payMent.myPayMent("1", 10);
        drawing.myDrawing("1", 100);
        drawing.myDrawing("1", 2);
        payMent.myPayMent("1", 100);
        return "success";
    }
}
