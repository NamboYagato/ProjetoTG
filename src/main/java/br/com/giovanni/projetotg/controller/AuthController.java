package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.LoginDtoRequest;
import br.com.giovanni.projetotg.dto.LoginDtoResponse;
import br.com.giovanni.projetotg.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody LoginDtoRequest loginDtoRequest) {
        String token;
        try {
            token = authService.authUser(loginDtoRequest.email(), loginDtoRequest.password());
        } catch (BadCredentialsException | EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginDtoResponse(null, e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new LoginDtoResponse(token, null));
    }
}
