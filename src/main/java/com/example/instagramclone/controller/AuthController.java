package com.example.instagramclone.controller;

import com.example.instagramclone.dto.LoginDTO;
import com.example.instagramclone.dto.SignupDTO;
import com.example.instagramclone.dto.TokenDTO;
import com.example.instagramclone.dto.UserDTO;
import com.example.instagramclone.entity.Role;
import com.example.instagramclone.entity.User;
import com.example.instagramclone.repo.UserRepository;
import com.example.instagramclone.service.UserService;
import com.example.instagramclone.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDTO signupDTO) {
        try {
            UserDTO user = userService.save(signupDTO);
            return ResponseEntity.created(null).body(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) throws BadJOSEException, ParseException, JOSEException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));


        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = JwtUtil.createAccessToken(authentication.getName(), String.valueOf(request.getRequestURL()), roles);
        String refreshToken = JwtUtil.createRefreshToken(authentication.getName());
        long expirationTime = JwtUtil.getExpirationTime(accessToken).getTime() / 1000;

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccess_token(accessToken);
        tokenDTO.setRefresh_token(refreshToken);
        tokenDTO.setExpires_in(expirationTime);
        tokenDTO.setToken_type("Bearer");

        return ResponseEntity.ok().body(tokenDTO);

    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestParam(value = "refreshToken") String refreshToken, HttpServletRequest request) throws BadJOSEException, ParseException, JOSEException {
        UsernamePasswordAuthenticationToken authenticationToken = JwtUtil.parseToken(refreshToken);
        String username = authenticationToken.getName();
        User user = userRepository.findByUsername(username);
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        String accessToken = JwtUtil.createAccessToken(username, request.getServletPath(), roles);
        String newRefreshToken = JwtUtil.createRefreshToken(username);
        long expirationTime = JwtUtil.getExpirationTime(accessToken).getTime() / 1000;

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccess_token(accessToken);
        tokenDTO.setRefresh_token(newRefreshToken);
        tokenDTO.setExpires_in(expirationTime);
        tokenDTO.setToken_type("Bearer");

        return ResponseEntity.ok().body(tokenDTO);
    }

    // Springboot 3 JWT Authentication and Authorization with Spring Security and MySQL Example


}
