package com.shubham.library.service.serviceinterface;

import com.shubham.library.dto.AddBookRequestDto;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> addBook(AddBookRequestDto addBookRequestDto);

    ResponseEntity<Object> showAllBooks();

    ResponseEntity<Object> findByIsbn(String isbn);

    ResponseEntity<Object> updateBook(AddBookRequestDto addBookRequestDto);

    ResponseEntity<Object> deleteByIsbn(String isbn);
}
