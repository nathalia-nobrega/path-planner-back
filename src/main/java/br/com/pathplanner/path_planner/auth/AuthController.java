package br.com.pathplanner.path_planner.auth;

import br.com.pathplanner.path_planner.auth.login.LoginRequestDto;
import br.com.pathplanner.path_planner.auth.login.LoginResponseDto;
import br.com.pathplanner.path_planner.auth.register.RegisterRequestDto;
import br.com.pathplanner.path_planner.auth.register.RegisterResponseDto;
import br.com.pathplanner.path_planner.auth.security.TokenService;
import br.com.pathplanner.path_planner.exceptions.UserNotFoundException;
import br.com.pathplanner.path_planner.modules.user.User;
import br.com.pathplanner.path_planner.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenService tService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isEmpty()) {
            User rawUser = new User();
            rawUser.setPassword(passwordEncoder.encode(body.password()));
            rawUser.setEmail(body.email());
            rawUser.setFullName(body.fullName());
            this.repository.save(rawUser);

            // Geração do Token
            String token = this.tService.generateToken(rawUser);

            return ResponseEntity.ok(new RegisterResponseDto(token, rawUser.getFullName()));
        }
        return ResponseEntity.badRequest().build();
    }
}
