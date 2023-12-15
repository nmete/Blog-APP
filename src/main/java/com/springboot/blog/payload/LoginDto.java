package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "LoginDto Model Information"
)
public class LoginDto {
    @Schema(
            description = "Blog LoginDto userNamOrEmail"
    )
    private String userNamOrEmail;

    @Schema(
            description = "Blog LoginDto password"
    )
    private  String password;
}
