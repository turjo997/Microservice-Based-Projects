package com.sajal.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    Long id;
    String name;
    String authorName;
    Double price;
    Integer quantity;

}
