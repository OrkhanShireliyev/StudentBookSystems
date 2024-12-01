package com.example.springbootstudent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "author-book-graphs",
        attributeNodes = @NamedAttributeNode(value = "books",subgraph = "book-category"),
        subgraphs = @NamedSubgraph(name="book-category",attributeNodes = @NamedAttributeNode("category"))
)
public class Author{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @ManyToMany(mappedBy = "authors",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Book> books;

}
