/*   package com.HabibDev.oderBook.config.testController;

import com.HabibDev.oderBook.config.FeignClientsConfig;
import com.HabibDev.oderBook.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class testController {

    @Autowired
    FeignClientsConfig feignClientsConfig;


    @GetMapping("/test")
    public ResponseEntity<BookModel> hello() {
        return feignClientsConfig.getBookById(1);
    }

}

 */

