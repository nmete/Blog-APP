package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Post Response Model Information"
)
public class PostResponse {
    @Schema(
            description = "Post Response content"
    )
    private List<PostDto> content;

    @Schema(
            description = "Post Response pageNo"
    )
    private int pageNo;

    @Schema(
            description = "Post Response pageSize"
    )
    private  int pageSize;


    @Schema(
            description = "Post Response totalElements"
    )
    private long totalElements;

    @Schema(
            description = "Post Response totalPages"
    )
    private int totalPages;

    @Schema(
            description = "Post Response last"
    )
    private boolean last;
}
