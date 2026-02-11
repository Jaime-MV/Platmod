package com.prototipo.platmod.service;
import com.prototipo.platmod.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    // ⚠️ IMPORTANTE: Usa una clave secreta larga y segura (esto es solo un ejemplo)
    private static final String SECRET_KEY = "mi_clave_secreta_super_segura_y_larga_para_firmar_tokens";

    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo()) // El "usuario" del token
                .claim("rol", usuario.getRol())  // Guardamos el rol dentro del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
