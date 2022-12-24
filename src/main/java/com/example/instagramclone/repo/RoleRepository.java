package com.example.instagramclone.repo;

import com.example.instagramclone.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
