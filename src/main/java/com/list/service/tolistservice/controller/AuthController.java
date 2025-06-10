package com.list.service.tolistservice.controller;

import com.list.service.tolistservice.model.entity.User;
import com.list.service.tolistservice.model.enums.Role;
import com.list.service.tolistservice.repository.UserRepository;
import com.list.service.tolistservice.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "Методы регистрации и входа")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepo;

    private final PasswordEncoder encoder;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создаёт нового пользователя с ролью USER",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован")
    @ApiResponse(responseCode = "400", description = "Имя пользователя уже занято")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для регистрации", required = true)
                                    @RequestBody AuthRequest req) {

        if (userRepo.findByUsername(req.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username taken");

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        userRepo.save(u);
        return ResponseEntity.ok("Registered");
    }

    @Operation(
            summary = "Вход пользователя",
            description = "Аутентификация пользователя и получение JWT токена",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponse(responseCode = "200", description = "Успешная аутентификация, возвращает JWT токен")
    @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    @PostMapping("/login")
    public ResponseEntity<?> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для входа", required = true)
                                    @RequestBody AuthRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
            String token = jwtUtils.generateToken((UserDetails) auth.getPrincipal());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
}
