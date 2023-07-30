package com.HabibDev.BookShopApplication.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books")
public class BookEntity {
    @Id
    @GeneratedValue
    private Integer bookId;
    private String title;
    private String author;
    private String details;
    private Integer price;
    private Integer pageCount;
    private Integer bookQuantity;

}

