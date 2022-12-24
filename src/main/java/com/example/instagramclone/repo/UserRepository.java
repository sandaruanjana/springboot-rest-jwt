package com.example.instagramclone.repo;

import com.example.instagramclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
