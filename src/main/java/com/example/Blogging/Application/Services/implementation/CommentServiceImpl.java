package com.example.Blogging.Application.Services.implementation;

import com.example.Blogging.Application.Entities.Comment;
import com.example.Blogging.Application.Entities.Post;
import com.example.Blogging.Application.Entities.User;
import com.example.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.example.Blogging.Application.Payloads.CommentDTO;
import com.example.Blogging.Application.Repositories.CommentRepository;
import com.example.Blogging.Application.Repositories.PostRepository;
import com.example.Blogging.Application.Repositories.UserRepository;
import com.example.Blogging.Application.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);

        comment.setPost(post);
        comment.setUser(user);
        comment.setUId(userId);

        Comment savedComment = this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment , CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        this.commentRepository.delete(comment);


    }
}
