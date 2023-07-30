package com.sajal.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookInfo {
    private Long id;
    private String name;
    private String authorName;
    private Integer price;
}
