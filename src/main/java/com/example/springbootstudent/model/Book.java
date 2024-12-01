package com.example.springbootstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "book-authors-graphs",
                attributeNodes = @NamedAttributeNode(value = "authors")
        )
        ,
        @NamedEntityGraph(
                name = "book-category-graphs",
                attributeNodes = @NamedAttributeNode(value = "category")
        )
        ,
        @NamedEntityGraph(
                name = "book-authors-category-graphs",
                attributeNodes = {
                        @NamedAttributeNode(value = "authors"),
                        @NamedAttributeNode(value = "category")
                }
        )
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String year;
    private double price;
    private int count;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    @JsonIgnore
    private Category category;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name ="author_id"))
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Author> authors;
}
