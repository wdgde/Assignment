package com.cmxx.paymentsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

// 接收商户系统请求并返回相关信息
@RestController
@RequestMapping("/paymentsystem")
public class UpayController {

    @Autowired
    private DatabaseOperation databaseOperation;

    @Autowired
    private PayMent payMent;

    @Autowired
    private Drawing drawing;

    @Autowired
    private Reconciliation reconciliation;

    @Autowired
    private Check check;

    // 支付请求
    //@Async
    @RequestMapping(value = "/payment/{jsonData}", method = RequestMethod.GET)
    public Object pay(@PathVariable String jsonData) {
        Object responseJsonData = payMent.myPayMent(jsonData);
        //System.out.println("---------" + responseJsonData);
        return responseJsonData;
    }

    // 提款请求
    //@Async
    @RequestMapping(value = "/drawing/{jsonData}", method = RequestMethod.GET)
    public Object drawMoney(@PathVariable String jsonData) {
        Object responseJsonData = drawing.myDrawing(jsonData);
        return responseJsonData;
    }

    // 对账请求
    @RequestMapping(value = "/reconciliation", method = RequestMethod.POST)
    public void reconciliation(@PathVariable MultipartFile file, String deviceId) throws IOException {
        //file.getInputStream();
        CsvFile csvFile = new CsvFile();
        csvFile.acceptFile(file);
        System.out.println("文件接收：success------" + deviceId);
        String paymentFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\";
        List<String> dataList = csvFile.importCsv(new File(paymentFilePath + file.getOriginalFilename()));
        if (deviceId.equals("支付账单")) {
            reconciliation.paymentReconciliation(dataList);
        }
    }

    // 稽核请求
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public void check() {
        CsvFile csvFile = new CsvFile();
        String paymentFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\payment.csv";
        List<String> paymentDataList = csvFile.importCsv(new File(paymentFilePath));
        String drawingFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\cmxx\\file\\drawing.csv";
        List<String> drawDataList = csvFile.importCsv(new File(drawingFilePath));
        check.checkMoney(paymentDataList, drawDataList);
    }
}
