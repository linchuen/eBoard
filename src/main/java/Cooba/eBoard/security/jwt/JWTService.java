package Cooba.eBoard.security.jwt;

import Cooba.eBoard.WebUser.WebUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final String KEY = "SpringBootCombineReactAsFrontendWithJWTForAccessControll";

    public String generateToken(AccessRequest accessRequest) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(accessRequest.getUsername(), accessRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        WebUser userDetails = (WebUser) authentication.getPrincipal();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);

        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("role", userDetails.getWebUserRole().name());
        claims.setIssuedAt(Calendar.getInstance().getTime());
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("Eboard project");

        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
