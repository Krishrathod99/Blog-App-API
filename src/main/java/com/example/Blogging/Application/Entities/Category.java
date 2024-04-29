package com.example.Blogging.Application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // for auto Increment purpose , both are used ( AUTO & IDENTITY )
    private Integer categoryId;

    @Column(name = "title", length = 100, nullable = false)
    private String categoryTitle;

    @Column(name = "description")
    private String categoryDescription;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)   // OneToMany bcz one Category can contains many posts ,mapped by category field of Post table , cascade means if parent class is update then child class will automatically updated
    private List<Post> posts = new ArrayList<>();




}
