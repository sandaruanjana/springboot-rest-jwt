package com.example.instagramclone.service.impl;

import com.example.instagramclone.entity.Role;
import com.example.instagramclone.repo.RoleRepository;
import com.example.instagramclone.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
