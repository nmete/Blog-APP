package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@Schema(
        description = "CommentDto Model Information"
)
public class CommentDto {
    private long id;

    @Schema(
            description = "CommentDto name"
    )
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(
            description = "CommentDto email"
    )
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @Schema(
            description = "CommentDto body"
    )
    @NotEmpty
    @Size(min = 20, message = "comment should content minimum 10 character")
    private String body;
}
