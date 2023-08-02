package com.bjit.tss;

import com.bjit.tss.model.request.DataStorageRequest;
import com.bjit.tss.service.DataStorageService;
import com.bjit.tss.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TssApplication implements CommandLineRunner {

    private final RegisterService registerService;
    private final DataStorageService dataStorageService;
    public static void main(String[] args) {
        SpringApplication.run(TssApplication.class, args);
    }

    @Value("${spring.admin.email}")
    private String adminEmail;

    @Value("${spring.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        registerService.adminRegistration(adminEmail, adminPassword);

        DataStorageRequest data1 = DataStorageRequest.builder()
                .dataKey("TotalMarkWritten")
                .dataValue("30")
                .build();
        DataStorageRequest data2 = DataStorageRequest.builder()
                .dataKey("PassingMarkWritten")
                .dataValue("15")
                .build();
        DataStorageRequest data3 = DataStorageRequest.builder()
                .dataKey("WrittenQuestionNumber")
                .dataValue("5")
                .build();
        DataStorageRequest data4 = DataStorageRequest.builder()
                .dataKey("TotalMarkAptitude")
                .dataValue("30")
                .build();
        DataStorageRequest data5 = DataStorageRequest.builder()
                .dataKey("AptitudeQuestionNumber")
                .dataValue("5")
                .build();
        DataStorageRequest data6 = DataStorageRequest.builder()
                .dataKey("PassingMarkAptitude")
                .dataValue("15")
                .build();
        DataStorageRequest data7 = DataStorageRequest.builder()
                .dataKey("TotalMarkHrViva")
                .dataValue("30")
                .build();
        DataStorageRequest data8 = DataStorageRequest.builder()
                .dataKey("HrVivaQuestionNumber")
                .dataValue("3")
                .build();

        DataStorageRequest data9 = DataStorageRequest.builder()
                .dataKey("PassingMarkHrViva")
                .dataValue("15")
                .build();
        DataStorageRequest data10 = DataStorageRequest.builder()
                .dataKey("TotalMarkTechnical")
                .dataValue("30")
                .build();
        DataStorageRequest data11 = DataStorageRequest.builder()
                .dataKey("TechnicalQuestionNumber")
                .dataValue("3")
                .build();
        DataStorageRequest data12 = DataStorageRequest.builder()
                .dataKey("PassingMarkTechnical")
                .dataValue("15")
                .build();

        List<DataStorageRequest> listRequest= new ArrayList<>();
        listRequest.add(data1);
        listRequest.add(data2);
        listRequest.add(data3);
        listRequest.add(data4);
        listRequest.add(data5);
        listRequest.add(data6);
        listRequest.add(data7);
        listRequest.add(data8);
        listRequest.add(data9);
        listRequest.add(data10);
        listRequest.add(data11);
        listRequest.add(data12);

        dataStorageService.setDataStorageInit(listRequest);


    }
}