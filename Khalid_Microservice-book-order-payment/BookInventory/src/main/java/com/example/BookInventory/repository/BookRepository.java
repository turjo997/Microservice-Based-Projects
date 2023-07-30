package com.example.BookInventory.repository;


import com.example.BookInventory.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    public BookEntity findByBookId(Long bookId);
    public BookEntity findByAuthorNameAndBookName(String authorName, String bookName);
    public List<BookEntity> findByAuthorName(String authorName);
    public void deleteById(Long bookId);

}
