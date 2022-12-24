package com.example.instagramclone.service;

import com.example.instagramclone.dto.SignupDTO;
import com.example.instagramclone.dto.UserDTO;

import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface UserService {
    UserDTO save(SignupDTO user) throws Exception;
    UserDTO addRoleToUser(String username, String roleName);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
}
