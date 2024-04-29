package com.example.Blogging.Application.Payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private int id;

    private String content;

    private int userId;

}
