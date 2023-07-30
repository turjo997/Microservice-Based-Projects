package com.example.demobook.demobook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookModel {
    private String bookName;
    private String authorName;
    private String genre;
    private Integer price;
    private Integer quantity;
}
