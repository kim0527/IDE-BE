package api.v1.auth.token.jwt;


import api.v1.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${application.security.jwt.expiration}")
    private long accessExpiration;

    @Getter
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateAccessToken(User user, Date issuedAt) {
        return buildToken(user, issuedAt, accessExpiration);
    }

    public String generateRefreshToken(User user, Date issuedAt) {
        return buildToken(user, issuedAt, refreshExpiration);
    }
    private String buildToken(User user, Date issuedAt, long expiration) {
        return Jwts
                .builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))                        //payload
                .setSubject(user.getNickname())                       //payload
                .setIssuedAt(issuedAt)                                      //유효기간
                .setExpiration(new Date(issuedAt.getTime() + expiration))   //유효기간
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)          //sign (decode)
                .compact();
    }

    // jwt Header 설정
    // type : JWt
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 암호화
        return header;
    }

    // jwt claims 설정
    private Map<String,Object> createClaims(User user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("id",user.getId());
        claims.put("name",user.getName());
        return claims;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}