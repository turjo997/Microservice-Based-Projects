package com.sajal.user_service.service;

import com.sajal.user_service.config.InventoryClientConfig;
import com.sajal.user_service.dto.BookDto;
import com.sajal.user_service.dto.BookInfo;
import com.sajal.user_service.dto.UserDto;
import com.sajal.user_service.entity.PurchasedBook;
import com.sajal.user_service.entity.UserEntity;
import com.sajal.user_service.repository.PurchasedBookRepository;
import com.sajal.user_service.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userRepository;
    private final PurchasedBookRepository purchasedBookRepository;
    private final InventoryClientConfig inventoryClientConfig;
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> getUsers() {
        return  userRepository.findAll();
    }

    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        List<PurchasedBook> myBooks = purchasedBookRepository.findAllByUserId(id);
        List<BookDto> purchasedBooks = new ArrayList<>();
        if(!myBooks.isEmpty())
        {
        List<Long> bookIds = myBooks.stream().map(PurchasedBook::getBookId).toList();
        List<BookInfo> info = inventoryClientConfig.getBookInfo(bookIds);

        myBooks.forEach(
                book->purchasedBooks.add(
                        BookDto.builder()
                                .id(book.getId())
                                .name(info.stream().filter(i->i.getId().equals(book.getBookId())).findAny().get().getName())
                                .authorName(info.stream().filter(i->i.getId().equals(book.getBookId())).findAny().get().getAuthorName())
                                .price(Double.valueOf(info.stream().filter(i->i.getId().equals(book.getBookId())).findAny().get().getPrice()))
                                .quantity(book.getQuantity())
                                .build())
        );
        }
        return UserDto.builder()
                .id(user.getId())
                .purchasedBooks(purchasedBooks)
                .name(user.getName())
                .build();
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean checkUserExist(Long id) {
       return userRepository.findById(id).isPresent();
    }

    public Boolean addBookToUser(Long userId, Long bookId, Integer quantity) {
        try {
            //TODO: have some bug here
            var purchasedBook = purchasedBookRepository.findPurchasedBooksByUserIdAndBookId(userId,bookId);
            if(purchasedBook.isEmpty()){
                purchasedBookRepository.save(PurchasedBook.builder()
                        .userId(userId)
                        .bookId(bookId)
                        .quantity(quantity)
                        .build());
                return true;
            }
            purchasedBook.get().setQuantity(purchasedBook.get().getQuantity()+quantity);
            purchasedBookRepository.save(purchasedBook.get());
            return true;
        }
        catch (Exception e){
            return false;
        }

    }
}
