package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.batchDto.BatchDTO;
import com.bjit.finalproject.TMS.dto.batchDto.BatchTraineeDTO;
import com.bjit.finalproject.TMS.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch")
public class BatchController {
    private final BatchService batchService;
    @PostMapping("/create-batch")
    public ResponseEntity<Object> createBatch(@RequestBody BatchDTO batchDTO){
        return new ResponseEntity<>(batchService.createBatch(batchDTO), HttpStatus.OK);
    }
    @PostMapping("/assign-trainees-to-batch")
    public ResponseEntity<Object>assignTraineesToBatch(@RequestBody List<BatchTraineeDTO> batchTraineeDTO){
        return new ResponseEntity<>(batchService.assignTraineesToBatch(batchTraineeDTO), HttpStatus.OK);
    }
    @GetMapping("/trainees/{batchName}")
    public ResponseEntity<Object> getTraineesByBatch(@PathVariable String batchName){
        return new ResponseEntity<>(batchService.getTraineesByBatch(batchName), HttpStatus.OK);
    }
    @GetMapping("/get-batches")
    public ResponseEntity<Object> getBatches(){
        return new ResponseEntity<>(batchService.getBatches(), HttpStatus.OK);
    }
}
