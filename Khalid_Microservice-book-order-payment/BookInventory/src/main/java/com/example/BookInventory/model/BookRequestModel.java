package com.example.BookInventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestModel {

    private Long bookId;
    private String bookName;
    private String authorName;
    private Long price;
    private Long inStock;
}
