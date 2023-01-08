package com.example.instagramclone;

import com.example.instagramclone.entity.Role;
import com.example.instagramclone.enums.RoleType;
import com.example.instagramclone.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstagramCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramCloneApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(RoleService roleService) {
        return args -> {
            Role role = new Role(null, RoleType.ROLE_USER.toString());
            roleService.save(role);
        };
    }
}
