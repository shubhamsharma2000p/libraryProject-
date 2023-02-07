package com.shubham.library.repository;

import com.shubham.library.model.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
    BookDetails findByIsbn(String isbn);

    void deleteByIsbn(String isbn);
}
