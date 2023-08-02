package com.bjit.tss.service.impl;

import com.bjit.tss.entity.ExamineeInfo;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.ExamineeRepository;
import com.bjit.tss.enums.Role;
import com.bjit.tss.service.CandidateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final ExamineeRepository examineeRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allCandidates() {
        List<ExamineeInfo> examineeInfos = examineeRepository.findByRole(Role.CANDIDATE);
        ListResponse<?> listResponse = ListResponse.builder()
                .listResponse(examineeInfos)
                .dataLength( examineeInfos.size())
                .build();
        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }
}