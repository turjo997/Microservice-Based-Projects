package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.batchDto.BatchDTO;
import com.bjit.finalproject.TMS.dto.batchDto.BatchTraineeDTO;
import com.bjit.finalproject.TMS.dto.batchDto.BatchTraineeDetailsDTO;
import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.BatchTrainees;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BatchService {
    Batch createBatch(BatchDTO batchDTO);
    List<Batch> getBatches();
    List<BatchTrainees> assignTraineesToBatch(List<BatchTraineeDTO> batchTraineeDTO);
    List<BatchTraineeDetailsDTO> getTraineesByBatch(String batchName);
//    List<Batch> getBatchesByName(String batchName);
}
