package com.example.Blogging.Application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "Posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "Post_title" , length = 100, nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addedDate;

    @ManyToOne     // ManyToOne relation bcz many posts can belong to the one category
    @JoinColumn(name = "Category_id")
    private Category category;     // which category contain this xyz post

    @ManyToOne    // Many posts can be post by one user
    private User user;        // which user upload this xyz post

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)     // throw mapped property, foreign key will only be created in comment table
    private Set<Comment> comments = new HashSet<>();

}
