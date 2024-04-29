package com.example.Blogging.Application.Controllers;

import com.example.Blogging.Application.Payloads.ApiResponse;
import com.example.Blogging.Application.Payloads.CommentDTO;
import com.example.Blogging.Application.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comments")
    @PreAuthorize("hasRole('NORMAL')")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO , @PathVariable Integer postId,
                                                    @PathVariable Integer userId){
        CommentDTO createdCommentDTO = this.commentService.createComment(commentDTO, postId, userId);
        return new ResponseEntity<>(createdCommentDTO , HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully !!" , true) , HttpStatus.OK);
    }


}
