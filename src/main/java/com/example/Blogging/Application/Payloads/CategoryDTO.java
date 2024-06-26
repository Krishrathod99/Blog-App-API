package com.example.Blogging.Application.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer CategoryId;
    @NotBlank
    @Size(min = 4 , message = "Minimum size of Category title is 4")
    private String categoryTitle;
    @NotBlank
    @Size(min = 10 , message = "Minimum size of Category description is 10")
    private String categoryDescription;

}
