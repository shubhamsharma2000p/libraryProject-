package com.shubham.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "book_details")
public class BookDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "isbn" , unique = true, nullable = false)
    private String isbn;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear ;


    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    private Timestamp updateTimestamp;


}
