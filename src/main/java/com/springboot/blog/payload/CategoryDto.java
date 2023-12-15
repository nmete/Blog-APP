package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "CategoryDto Model Information"
)
public class CategoryDto {
    private long id;
    @Schema(
            description = "CategoryDto name"
    )
    private String name;

    @Schema(
            description = "CategoryDto description"
    )
    private String description;
}
