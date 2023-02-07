package com.shubham.library.controller;

import com.shubham.library.constant.APIConstants;
import com.shubham.library.constant.ApplicationConstants;
import com.shubham.library.constant.CodeConstant;
import com.shubham.library.constant.MessageConstant;
import com.shubham.library.dto.AddBookRequestDto;
import com.shubham.library.service.serviceinterface.BookService;
import com.shubham.library.utils.CommonResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class BookController {
    private static final Logger api_log = LoggerFactory.getLogger("API_Log");

    @Autowired
    BookService bookService;
    @Autowired
    CommonResponseEntity commonResponseEntity;

    String msg = "";
    LinkedHashMap<String, Object> data = null;

    @PostMapping(APIConstants.ADD_BOOK)
    public ResponseEntity<Object> addBook(@RequestHeader Map<String, String> requestHeaderMap, @Valid @RequestBody AddBookRequestDto addBookRequestDto) {


        final String isbn = requestHeaderMap.get("isbn");

        if (isbn == null || StringUtils.isEmpty(isbn) || !isbn.equals(addBookRequestDto.getIsbn()) || isbn.length() !=13)
         {
             msg = MessageConstant.INVALID_ISBN;
             data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
             api_log.error("Error in isbn : {} ", MessageConstant.ADD_UNSUCCESSFULL);
             return new ResponseEntity<Object>(data, HttpStatus.OK);
         }

        MDC.put("API",requestHeaderMap.get("isbn").concat(APIConstants.ADD_BOOK));

        api_log.info("Started");
        ResponseEntity<Object> response = bookService.addBook(addBookRequestDto);
        api_log.info("Completed");


        return response;
    }


    @GetMapping(APIConstants.SHOW_ALL_BOOKS)
    public ResponseEntity<Object> showAllBooks( ) {
        MDC.put("API",APIConstants.SHOW_ALL_BOOKS);
        api_log.info("Started");
        ResponseEntity<Object> response = bookService.showAllBooks();
        api_log.info("Completed");


        return response;
    }

    @GetMapping(APIConstants.FIND_BY_ISBN)
    public ResponseEntity<Object> findByIsbn(@RequestHeader Map<String, String> requestHeaderMap ) {

        final String isbn = requestHeaderMap.get("isbn");

        if (isbn == null || StringUtils.isEmpty(isbn) || isbn.length() !=13)
        {
            msg = MessageConstant.INVALID_ISBN;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
            api_log.error("Error in isbn : {} ", MessageConstant.FAILURE);
            return new ResponseEntity<Object>(data, HttpStatus.OK);
        }


        MDC.put("API",requestHeaderMap.get("isbn").concat(APIConstants.FIND_BY_ISBN));

        api_log.info("Started");
        ResponseEntity<Object> response = bookService.findByIsbn(requestHeaderMap.get("isbn"));
        api_log.info("Completed");


        return response;
    }

    @PutMapping(APIConstants.UPDATE_BOOK)
    public ResponseEntity<Object> updateBook(@RequestHeader Map<String, String> requestHeaderMap, @Valid @RequestBody AddBookRequestDto addBookRequestDto) {


        final String isbn = requestHeaderMap.get("isbn");

        if (isbn == null || StringUtils.isEmpty(isbn) || !isbn.equals(addBookRequestDto.getIsbn())|| isbn.length() !=13)
        {
            msg = MessageConstant.INVALID_ISBN;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
            api_log.error("Error in isbn : {} ", MessageConstant.INVALID_ISBN);
            return new ResponseEntity<Object>(data, HttpStatus.OK);
        }

        MDC.put("API",requestHeaderMap.get("isbn").concat(APIConstants.UPDATE_BOOK));

        api_log.info("Started");
        ResponseEntity<Object> response = bookService.updateBook(addBookRequestDto);
        api_log.info("Completed");


        return response;
    }

    @DeleteMapping(APIConstants.DELETE_BY_ISBN)
    public ResponseEntity<Object> deleteByIsbn(@RequestHeader Map<String, String> requestHeaderMap ) {

        final String isbn = requestHeaderMap.get("isbn");

        if (isbn == null || StringUtils.isEmpty(isbn)|| isbn.length() !=13 )
        {
            msg = MessageConstant.INVALID_ISBN;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
            api_log.error("Error in isbn : {} ", MessageConstant.FAILURE);
            return new ResponseEntity<Object>(data, HttpStatus.OK);
        }


        MDC.put("API",requestHeaderMap.get("isbn").concat(APIConstants.DELETE_BY_ISBN));

        api_log.info("Started");
        ResponseEntity<Object> response = bookService.deleteByIsbn(requestHeaderMap.get("isbn"));
        api_log.info("Completed");


        return response;
    }



}
