package com.example.Blogging.Application.Payloads;

import com.example.Blogging.Application.Entities.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Integer postId;
    @NotBlank
    @Size(min = 10 , message = "Post title size must be minimum of 10 characters")
    private String title;
    @NotBlank
    @Size(min = 10 , message = "Post title size must be minimum of 10 characters")
    private String content;
    @NotNull
    private String imageName;
    @NotEmpty
    private Date addedDate;
    private CategoryDTO category;      // CategoryDTO and userDTO bcz it's not contain imageName,date field etc so now we're not
//                                        get the recursion error which we was occur with Category and User class bcz they contains
//                                        contains image, date field and that's why infinite loop was occur
    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();      // when we fetch the post then we automatically get the comments
}
