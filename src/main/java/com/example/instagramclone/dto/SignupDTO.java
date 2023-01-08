package com.example.instagramclone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
}
