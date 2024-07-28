package br.com.pathplanner.path_planner.auth;

import br.com.pathplanner.path_planner.auth.login.LoginRequestDto;
import br.com.pathplanner.path_planner.auth.login.LoginResponseDto;
import br.com.pathplanner.path_planner.auth.register.RegisterRequestDto;
import br.com.pathplanner.path_planner.auth.register.RegisterResponseDto;
import br.com.pathplanner.path_planner.auth.security.TokenService;
import br.com.pathplanner.path_planner.exceptions.UserAlreadyExistsException;
import br.com.pathplanner.path_planner.exceptions.UserNotFoundException;
import br.com.pathplanner.path_planner.modules.trip.TripCreateResponse;
import br.com.pathplanner.path_planner.modules.user.User;
import br.com.pathplanner.path_planner.modules.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Rota de autenticação e autorização")
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenService tService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Essa função é responsável por logar um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LoginResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou dados são inválidos"),
            @ApiResponse(responseCode = "400", description = "Requisição não processada"),

    })
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro de usuário", description = "Essa função é responsável por cadastrar um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = RegisterResponseDto.class))
            }),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado"),
    })
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDto body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User rawUser = new User();
        rawUser.setPassword(passwordEncoder.encode(body.password()));
        rawUser.setEmail(body.email());
        rawUser.setFullName(body.fullName());
        this.repository.save(rawUser);

        // Geração do Token
        String token = this.tService.generateToken(rawUser);

        return ResponseEntity.ok(new RegisterResponseDto(token, rawUser.getFullName()));
    }
}
