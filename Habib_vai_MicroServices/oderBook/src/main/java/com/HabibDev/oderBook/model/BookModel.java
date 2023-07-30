package com.HabibDev.oderBook.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    private Integer bookId;
    private String title;
    private String author;
    private String details;
    private Integer price;
    private Integer pageCount;
    private Integer bookQuantity;

    // Add more properties as needed

}

