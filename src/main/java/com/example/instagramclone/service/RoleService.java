package com.example.instagramclone.service;

import com.example.instagramclone.entity.Role;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface RoleService {
    Role save(Role role);
    Role findByName(String name);
}
