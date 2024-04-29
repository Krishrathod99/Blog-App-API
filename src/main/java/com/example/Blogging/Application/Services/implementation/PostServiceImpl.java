package com.example.Blogging.Application.Services.implementation;

import com.example.Blogging.Application.Entities.Category;
import com.example.Blogging.Application.Entities.Post;
import com.example.Blogging.Application.Entities.User;
import com.example.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.example.Blogging.Application.Payloads.PostDTO;
import com.example.Blogging.Application.Payloads.PostResponse;
import com.example.Blogging.Application.Repositories.CategoryRepository;
import com.example.Blogging.Application.Repositories.PostRepository;
import com.example.Blogging.Application.Repositories.UserRepository;
import com.example.Blogging.Application.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

//        find user and category from useRepository and categoryRepository for set them id into post
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());

        post.setUser(user);           // save user and category into the post table
        post.setCategory(category);

        Post savedPost = this.postRepository.save(post);

        return this.modelMapper.map(savedPost , PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAddedDate(postDTO.getAddedDate());
        post.setImageName(postDTO.getImageName());

        Post savedPost = this.postRepository.save(post);
        return this.modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

        this.postRepository.delete(post);
    }

    @Override
    public PostDTO getPostById(Integer postId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize, String sortBy, String sortDir) {

//        this is pagination and sorting

//        Sort sort = null;
//        if (sortDir.equalsIgnoreCase("asc")){
//            sort = Sort.by(sortBy).ascending();
//        } else {
//            sort = Sort.by(sortBy).descending();
//        }

//        or using ternary operator for dynamically sort post (user tell in api that in which order they want result post)
        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber , pageSize, sort);     // create pageable object , Create Sort class object
                        // which can sort the page Result in either ascending or descending dynamically as per given requirement

        Page<Post> pagePost = this.postRepository.findAll(p);   // give object to findAll method and sort results according given default field

        List<Post> postList = pagePost.getContent();          // through getContent() method, we can get list of posts

        List<PostDTO> postDTOS = postList.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).toList();

//         This is pagination and PostResponse is class which contains all required field for pagination like totalPage,isLastPage etc.
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override       // this method is for get all posts of given category
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));

        Pageable p = PageRequest.of(pageNumber , pageSize);

//        find all posts by given category , give all posts of given category
        Page<Post> postPage = this.postRepository.findByCategory(category, p);

        List<Post> postList = postPage.getContent();

//        convert all posts into postDTO
        List<PostDTO> postDTOS = postList.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override     // this method is for get all posts of given user
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Pageable p = PageRequest.of(pageNumber , pageSize);

        Page<Post> postPage = postRepository.findByUser(user, p);

        List<Post> postList = postPage.getContent();

        List<PostDTO> postDTOS = postList.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> findByTitleContaining(String keyword) {

        List<Post> posts = this.postRepository.findByTitleContaining(keyword);

        List<PostDTO> postDTOS = posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).toList();

        return postDTOS;
    }
}
