package com.example.instagramclone.dto;

import com.example.instagramclone.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private Collection<Role> roles = new ArrayList<>();
}
