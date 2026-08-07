package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public JwtService(@Value("${jwt.secret}") String jwtSecretSting) {
        byte[] secretKeyByte = Decoders.BASE64.decode(jwtSecretSting);
        this.secretKey = Keys.hmacShaKeyFor(secretKeyByte);
    }

    public String tokenGenerator(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("role", usuario.getPapel().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}
