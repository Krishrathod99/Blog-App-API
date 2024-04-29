package com.example.Blogging.Application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "Post_Id")
    private Post post;

    @ManyToOne
    private User user;

    private int uId;

}
