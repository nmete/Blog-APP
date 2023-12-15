package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "JwtAuthResponse Model Information"
)
public class JwtAuthResponse {
    @Schema(
            description = "JwtAuthResponse accessToken"
    )
    private String accessToken;

    @Schema(
            description = "JwtAuthResponse Bearer"
    )
    private String tokenType = "Bearer";

}
