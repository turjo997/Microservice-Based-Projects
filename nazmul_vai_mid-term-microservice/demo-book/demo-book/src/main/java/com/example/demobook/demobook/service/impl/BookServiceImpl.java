package com.example.demobook.demobook.service.impl;

import com.example.demobook.demobook.entity.Book;
import com.example.demobook.demobook.model.BookModel;
import com.example.demobook.demobook.model.InventoryModel;
import com.example.demobook.demobook.model.InventoryResponse;
import com.example.demobook.demobook.repository.BookRepository;
import com.example.demobook.demobook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final String receivingUrl = "http://localhost:8079/book-inventory/update";
    private final String getUrl = "http://localhost:8079/book-inventory";
    @Override
    @Transactional
    public void createABook(BookModel bookModel) {
        InventoryModel inventory = InventoryModel.builder()
                                                .price(bookModel.getPrice())
                                                    .quantity(bookModel.getQuantity()).build();
        Book book = Book.builder()
                        .bookName(bookModel.getBookName())
                        .authorName(bookModel.getAuthorName())
                        .genre(bookModel.getGenre()).build();
        Integer bookId = bookRepository.save(book).getBookId();
        restTemplate.postForEntity(receivingUrl + "/"+bookId, inventory, Void.class);

    }

    @Override
    public List<BookModel> getAllBook() {
        //InventoryModel inventory = new InventoryModel();
        List<Book> allBooks = bookRepository.findAll();
        List<Integer> bookIds = new ArrayList<>();
        for(Book book:allBooks){
            Integer id = book.getBookId();
            bookIds.add(id);
        }

        String queryParams = bookIds.stream().map(id->id.toString()).collect(Collectors.joining(","));
//http://localhost:8079/book-inventory?id=5,6,7,8,89
        queryParams = "id="+queryParams;
        String requestUrl = UriComponentsBuilder.fromUriString(getUrl).query(queryParams)
                            .toUriString();


        ResponseEntity<List<InventoryModel>> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryModel>>() {}
        );
        List<InventoryModel> inventoryModels = response.getBody();
        System.out.println(inventoryModels);
        List<BookModel> books = new ArrayList<>();
//        for(Book book:allBooks){
//            BookModel bookModel = BookModel.builder()
//                                        .bookName(book.getBookName())
//                                        .authorName(book.getAuthorName())
//                                        .genre(book.getGenre())
//                                        .price()
//                                        .build();
//            books.add(bookModel);
//        }
        for (int i = 0; i < allBooks.size(); i++) {
            Book requiredBook = allBooks.get(i);
            Integer receiverObjectPrice = inventoryModels.get(i % inventoryModels.size()).getPrice();
            Integer receiverObjectQuantity = inventoryModels.get(i % inventoryModels.size()).getQuantity();
            BookModel bookModel = BookModel.builder()
                                        .bookName(requiredBook.getBookName())
                                        .authorName(requiredBook.getAuthorName())
                                        .genre(requiredBook.getGenre())
                                        .price(receiverObjectPrice)
                                        .quantity(receiverObjectQuantity).build();
            books.add(bookModel);
        }
        return books;
    }
}


//List<InventoryResponse> inventories = response.getBody();
//        List<BookModel> allBooksModel = new ArrayList<>();
//        for(InventoryResponse inv: inventories){
//            Integer id = inv.getId();
//            Optional<Book> book = bookRepository.findById(id);
//            Integer priceOfBook = inv.getPrice();
//            Integer quantityOfBook = inv.getQuantity();
//            if(book.isPresent()){
//                Book requiredBook = book.get();
//                BookModel responseBook = BookModel.builder()
//                                        .bookName(requiredBook.getBookName())
//                                        .authorName(requiredBook.getAuthorName())
//                                        .genre(requiredBook.getGenre())
//                                        .price(priceOfBook)
//                                        .quantity(quantityOfBook).build();
//                allBooksModel.add(responseBook);
//            }
//
//
//
//        }
//        return allBooksModel;