package com.example.instagramclone.service.impl;

import com.example.instagramclone.dto.SignupDTO;
import com.example.instagramclone.dto.UserDTO;
import com.example.instagramclone.entity.Role;
import com.example.instagramclone.entity.User;
import com.example.instagramclone.enums.RoleType;
import com.example.instagramclone.repo.RoleRepository;
import com.example.instagramclone.repo.UserRepository;
import com.example.instagramclone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    public UserDTO save(SignupDTO signupDTO) throws Exception{
        log.info("Saving user {}", signupDTO.getUsername());
        signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        User user = mapper.map(signupDTO, User.class);
        Role role = roleRepository.findByName(String.valueOf(RoleType.ROLE_USER));
        user.setRoles(List.of(role));
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO addRoleToUser(String username, String roleName) {
        log.info("Assign role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            String message = String.format("User %s not found", username);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            log.debug("User {} found", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public UserDTO findByUsername(String username) {
        return mapper.map(userRepository.findByUsername(username), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return mapper.map(userRepository.findAll(), List.class);
    }
}
