package com.example.demo.controller;

import com.example.demo.entity.Batch;
import com.example.demo.model.BatchCreate;
import com.example.demo.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/create")
    ResponseEntity<Batch> createNewBatch(@RequestBody BatchCreate batch){
        return batchService.createBatch(batch);
    }

}