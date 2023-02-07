package com.shubham.library.service.serviceimpl;

import com.shubham.library.constant.ApplicationConstants;
import com.shubham.library.constant.CodeConstant;
import com.shubham.library.constant.MessageConstant;
import com.shubham.library.dto.AddBookRequestDto;
import com.shubham.library.dto.ShowAllBooksResponseDto;
import com.shubham.library.model.BookDetails;
import com.shubham.library.repository.BookDetailsRepository;
import com.shubham.library.service.serviceinterface.BookService;
import com.shubham.library.utils.CommonResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger api_log = LoggerFactory.getLogger("API_Log");

    @Autowired
    BookDetailsRepository bookDetailsRepository;


    @Autowired
    CommonResponseEntity commonResponseEntity;

    String msg = "";
    LinkedHashMap<String, Object> data = null;
    @Override
    public ResponseEntity<Object> addBook(AddBookRequestDto addBookRequestDto) {



        try {

            BookDetails bookDetailsResult = bookDetailsRepository.findByIsbn(addBookRequestDto.getIsbn());
            if(bookDetailsResult!=null)
            {
                msg =MessageConstant.ISBN_ALREADY_PRESENT;
                data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.ISBN_ALREADY_PRESENT_CODE);
                api_log.error("Error in saving book : {} ", MessageConstant.ISBN_ALREADY_PRESENT);
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }

            BookDetails bookDetails = new BookDetails();
            bookDetails.setIsbn(addBookRequestDto.getIsbn());
            bookDetails.setName(addBookRequestDto.getName());
            bookDetails.setPublicationYear(addBookRequestDto.getPublicationYear());
            bookDetails.setAmount(addBookRequestDto.getAmount());
            bookDetails.setDescription(addBookRequestDto.getDescription());
            bookDetails.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            bookDetails.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
           BookDetails bookDetails1 = bookDetailsRepository.saveAndFlush(bookDetails);
            api_log.info("book saved {}", bookDetails);
           if(bookDetails1==null)
           {
               msg =MessageConstant.ADD_UNSUCCESSFULL;
               data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
               api_log.error("Error in saving book : {} ", MessageConstant.ADD_UNSUCCESSFULL);
               return new ResponseEntity<Object>(data, HttpStatus.OK);
           }

            msg = MessageConstant.ADD_SUCCESSFULL;
            data = commonResponseEntity.ResponseEntityWithMap(
                    ApplicationConstants.SUCCESS, msg, CodeConstant.OK);
        }
        catch (Exception e)
        {
            msg =MessageConstant.SYSTEM_ERROR_MSG;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.SYSTEM_ERROR_STATUS, msg, CodeConstant.SYSTEM_ERROR_CODE);
            api_log.error("Error in saving book : {} ", e.getMessage());
            return new ResponseEntity<Object>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(data, HttpStatus.OK);




    }

    @Override
    public ResponseEntity<Object> showAllBooks() {
        try {
            ShowAllBooksResponseDto showAllBooksResponseDto = new ShowAllBooksResponseDto();
            List<BookDetails> list = bookDetailsRepository.findAll();
            if(list.size()==0)
            {
                msg = MessageConstant.NO_DATA_FOUND;
                data = commonResponseEntity.ResponseEntityWithMap(
                        ApplicationConstants.SUCCESS, msg, CodeConstant.NO_DATA_FOUND);
                api_log.error("No books found ");
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }

            showAllBooksResponseDto.setTotalBooks(list.size());
            List<AddBookRequestDto> booksList = new ArrayList<>();
            for(int i=0;i<list.size();i++)
            {
                AddBookRequestDto addBookRequestDto = new AddBookRequestDto();
                addBookRequestDto.setIsbn(list.get(i).getIsbn());
                addBookRequestDto.setName(list.get(i).getName());
                addBookRequestDto.setPublicationYear(list.get(i).getPublicationYear());
                addBookRequestDto.setDescription(list.get(i).getDescription());
                addBookRequestDto.setAmount(list.get(i).getAmount());
                booksList.add(addBookRequestDto);

            }


            showAllBooksResponseDto.setListOfBooks(booksList);
            api_log.info("books fetched {}", showAllBooksResponseDto);
            msg = MessageConstant.DETAILS_FETCHED;
            data = commonResponseEntity.ResponseEntityWithMapAndData(
                    ApplicationConstants.SUCCESS, msg, CodeConstant.OK,showAllBooksResponseDto);
        }
        catch (Exception e)
        {
            msg =MessageConstant.SYSTEM_ERROR_MSG;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.SYSTEM_ERROR_STATUS, msg, CodeConstant.SYSTEM_ERROR_CODE);
            api_log.error("Error in fetching book : {} ", e.getMessage());
            return new ResponseEntity<Object>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findByIsbn(String isbn) {
        try {
            BookDetails bookDetails = bookDetailsRepository.findByIsbn(isbn);

            if(bookDetails==null)
            {
                msg =MessageConstant.NO_DATA_FOUND;
                data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.NO_DATA_FOUND);
                api_log.error("no book found : {} ", MessageConstant.NO_DATA_FOUND);
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }

            AddBookRequestDto addBookRequestDto = new AddBookRequestDto();
            addBookRequestDto.setIsbn(bookDetails.getIsbn());
            addBookRequestDto.setName(bookDetails.getName());
            addBookRequestDto.setPublicationYear(bookDetails.getPublicationYear());
            addBookRequestDto.setAmount(bookDetails.getAmount());
            addBookRequestDto.setDescription(bookDetails.getDescription());

            api_log.info("books fetched {}", addBookRequestDto);
            msg = MessageConstant.DETAILS_FETCHED;
            data = commonResponseEntity.ResponseEntityWithMapAndData(
                    ApplicationConstants.SUCCESS, msg, CodeConstant.OK,addBookRequestDto);

        }
        catch (Exception e)
        {
            msg =MessageConstant.SYSTEM_ERROR_MSG;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.SYSTEM_ERROR_STATUS, msg, CodeConstant.SYSTEM_ERROR_CODE);
            api_log.error("Error in fetching book : {} ", e.getMessage());
            return new ResponseEntity<Object>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }



        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateBook(AddBookRequestDto addBookRequestDto) {
        try {
            BookDetails bookDetails = bookDetailsRepository.findByIsbn(addBookRequestDto.getIsbn());
            if (bookDetails == null) {
                msg = MessageConstant.NO_DATA_FOUND;
                data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.NO_DATA_FOUND);
                api_log.error("no book found : {} ", MessageConstant.NO_DATA_FOUND);
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }

            bookDetails.setName(addBookRequestDto.getName());
            bookDetails.setPublicationYear(addBookRequestDto.getPublicationYear());
            bookDetails.setAmount(addBookRequestDto.getAmount());
            bookDetails.setDescription(addBookRequestDto.getDescription());
            bookDetails.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));

            BookDetails bookDetails1 = bookDetailsRepository.saveAndFlush(bookDetails);
            api_log.info("book updated {}", bookDetails);
            if (bookDetails1 == null) {
                msg = MessageConstant.UPDATE_UNSUCCESSFULL;
                data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.FAILURE);
                api_log.error("Error in updating book : {} ", MessageConstant.UPDATE_UNSUCCESSFULL);
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }

            msg = MessageConstant.UPDATE_SUCCESSFULL;
            data = commonResponseEntity.ResponseEntityWithMap(
                    ApplicationConstants.SUCCESS, msg, CodeConstant.OK );

        }
        catch(Exception e)
        {
            msg =MessageConstant.SYSTEM_ERROR_MSG;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.SYSTEM_ERROR_STATUS, msg, CodeConstant.SYSTEM_ERROR_CODE);
            api_log.error("Error in fetching book : {} ", e.getMessage());
            return new ResponseEntity<Object>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }



        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteByIsbn(String isbn) {
        try{
            BookDetails bookDetails = bookDetailsRepository.findByIsbn(isbn);
            if (bookDetails == null) {
                msg = MessageConstant.NO_DATA_FOUND;
                data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.FAILURE, msg, CodeConstant.NO_DATA_FOUND);
                api_log.error("no book found : {} ", MessageConstant.NO_DATA_FOUND);
                return new ResponseEntity<Object>(data, HttpStatus.OK);
            }
            bookDetailsRepository.deleteByIsbn(isbn);
            api_log.info("book deleted {}", bookDetails);
            msg = MessageConstant.DELETE_SUCCESSFULL;
            data = commonResponseEntity.ResponseEntityWithMap(
                    ApplicationConstants.SUCCESS, msg, CodeConstant.OK );

        }
        catch(Exception e)
        {
            msg =MessageConstant.SYSTEM_ERROR_MSG;
            data = commonResponseEntity.ResponseEntityWithMap(ApplicationConstants.SYSTEM_ERROR_STATUS, msg, CodeConstant.SYSTEM_ERROR_CODE);
            api_log.error("Error in deleting book : {} ", e.getMessage());
            return new ResponseEntity<Object>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }
}
