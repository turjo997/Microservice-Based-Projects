package com.bjit.tss.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoundCandidatesRequest {
    private String roundName;
    private String batchCode;

}
