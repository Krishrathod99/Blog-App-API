package com.example.Blogging.Application.Controllers;

import com.example.Blogging.Application.Config.AppConstants;
import com.example.Blogging.Application.Payloads.ApiResponse;
import com.example.Blogging.Application.Payloads.PostDTO;
import com.example.Blogging.Application.Payloads.PostResponse;
import com.example.Blogging.Application.Services.FileService;
import com.example.Blogging.Application.Services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


//    create post and post will save in given userId's user and given categoryId's category
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    @PreAuthorize("hasRole('NORMAL')")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,@PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDTO post = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(post , HttpStatus.CREATED);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){

        PostDTO updatePost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatePost ,HttpStatus.OK);
    }


    @DeleteMapping("/post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully !!" , true);
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDTO , HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(      // this is for pagination and PostResponse is cls which contains all required field for pagination
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(      // pagination
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @PathVariable Integer categoryId){

        PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(     // pagination
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @PathVariable Integer userId){

        PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

//    Search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> findByTitleContaining(@PathVariable("keywords") String keywords){
        List<PostDTO> postDTOS = this.postService.findByTitleContaining(keywords);
        return new ResponseEntity<>(postDTOS , HttpStatus.OK);
    }

//    Post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        PostDTO postDTO = this.postService.getPostById(postId);   // If post doesn't exist with this id than it give proper error message

        String fileName = this.fileService.uploadImage(path, image);    // return the file name which was upload

        // From above filename, we need to update fileName into the database so we use postService

        postDTO.setImageName(fileName);    // all postDto field remain same bt image name is updated
        PostDTO updatePost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatePost , HttpStatus.OK);
    }

//    Method to serve files
// use other browser for run this method, write full path in url, you will get the image like http://localhost:8080/api/post/image/picture.jpg
    @GetMapping(value = "/post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(        // use other browser for run this method, write full path in url and you will get the image
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path , imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource , response.getOutputStream());

    }

}
