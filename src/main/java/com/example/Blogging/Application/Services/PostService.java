package com.example.Blogging.Application.Services;

import com.example.Blogging.Application.Payloads.PostDTO;
import com.example.Blogging.Application.Payloads.PostResponse;

import java.util.List;

public interface PostService {

//    create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

//    update
    PostDTO updatePost(PostDTO postDTO , Integer postId);

//    Delete
    void deletePost(Integer postId);

//    get single post
    PostDTO getPostById(Integer postId);

//    get All posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

//    get all posts by category
    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

//    get all posts by user
    PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);

//    search post
    List<PostDTO> findByTitleContaining(String keyword);
}
