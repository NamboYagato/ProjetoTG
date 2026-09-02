package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.LoginDtoRequest;
import br.com.giovanni.projetotg.dto.LoginDtoResponse;
import br.com.giovanni.projetotg.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@RequestBody LoginDtoRequest loginDtoRequest) {
        String token = authService.authUser(loginDtoRequest.email(), loginDtoRequest.senha());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginDtoResponse(token, null));
    }
}
