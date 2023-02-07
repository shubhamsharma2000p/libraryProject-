package com.shubham.library.dto;

import com.shubham.library.model.BookDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ShowAllBooksResponseDto {
    Integer totalBooks;
    List<AddBookRequestDto> listOfBooks ;

}
