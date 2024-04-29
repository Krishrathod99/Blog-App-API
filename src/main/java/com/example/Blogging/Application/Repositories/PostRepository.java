package com.example.Blogging.Application.Repositories;

import com.example.Blogging.Application.Entities.Category;
import com.example.Blogging.Application.Entities.Post;
import com.example.Blogging.Application.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post , Integer> {

    Page<Post> findByUser(User user , Pageable p);     // to get or fetch xyz user's all posts
    Page<Post> findByCategory(Category category, Pageable p);      // to get xyz category's all posts

//    @Query("select p from Post where p.title like : key")
    List<Post> findByTitleContaining(String title);

}
