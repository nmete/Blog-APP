package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private long id;

    @NotEmpty
    @Schema(
            description = "Blog post title"
    )
    @Size(min = 2, message = "post title should have at least 2 character")
    private String title;

    @NotEmpty
    @Schema(
            description = "Blog post description"
    )
    @Size(min = 10 , message = "post title should have at least 10 character")
    private String description;

    @NotEmpty
    @Schema(
            description = "Blog post content"
    )
    private String content;

    @Schema(
            description = "Blog post comments"
    )
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog post categoryId"
    )
    private long categoryId;
}
