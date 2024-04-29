package com.example.Blogging.Application.Services;

import com.example.Blogging.Application.Payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO , Integer postId, Integer userId);

    void deleteComment(Integer commentId);


}
