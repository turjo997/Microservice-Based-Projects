package com.sajal.inventory_service.service;

import com.sajal.inventory_service.dto.BookInfo;
import com.sajal.inventory_service.entity.BookEntity;
import com.sajal.inventory_service.repository.BookEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final BookEntityRepository bookRepository;
    public BookEntity createBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public BookEntity updateBook(BookEntity user) {
        return bookRepository.save(user);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Integer getBookCountById(Long id) {
        var book = bookRepository.findById(id);
        return book.isPresent()? book.get().getQuantity():0;

    }

    public Boolean updateBookCountById(Long bookId, Integer count) {
        var book = bookRepository.findById(bookId);
        if(book.isPresent()){
            book.get().setQuantity(count);
            bookRepository.save(book.get());
            return true;
        }
        return false;
    }

    public List<BookInfo> getBooksInfo(List<Long> ids) {
    return ids.stream().map(id->{
            var bookEntity = bookRepository.findById(id).get();
            return BookInfo.builder().id(bookEntity.getBookId()).name(bookEntity.getName()).authorName(bookEntity.getAuthorName()).price(bookEntity.getPrice()).build();
        }).collect(Collectors.toList());
    }
}
