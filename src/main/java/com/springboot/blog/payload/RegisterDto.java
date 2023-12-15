package com.springboot.blog.payload;

import com.springboot.blog.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "RegisterDto Model Information"
)
public class RegisterDto {
    @Schema(
            description = "RegisterDto name"
    )
    private String name;

    @Schema(
            description = "RegisterDto userName"
    )
    private  String userName;

    @Schema(
            description = "RegisterDto email"
    )
    private  String email;

    @Schema(
            description = "RegisterDto password"
    )
    private String password;

    @Schema(
            description = "RegisterDto password"
    )
    private Set<Role> roles;

}
