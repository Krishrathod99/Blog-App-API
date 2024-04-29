package com.example.Blogging.Application.Repositories;

import com.example.Blogging.Application.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
