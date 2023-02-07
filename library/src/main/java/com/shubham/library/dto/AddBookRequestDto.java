package com.shubham.library.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AddBookRequestDto {
    @NotNull(message = "isbn can not be null")
    @NotEmpty(message = "isbn can not be empty")
    @NotBlank(message = "isbn can not be blank")
    @Size(min = 13, max = 13 , message = "enter 13 digit isbn")
    private String isbn;
    @NotNull(message = "name can not be null")
    @NotEmpty(message = "name can not be empty")
    @NotBlank(message = "name can not be blank")
    @Pattern(regexp ="^[a-zA-Z][A-Za-z ]*$",message = "Invalid name")
    private String name;
    @Min(value=1000, message="year should be of 4 digits")
    @Max(value=9999, message="year should be of 4 digits")
    private Integer publicationYear;

    @Min(value=1, message="amount should be grater than 0")
    private Integer amount;
    private String description;
}
