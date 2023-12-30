package com.bonappetit.config.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bonappetit.model.DTO.UserJWTDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserJWTDto userDto){
        Date now = new Date();
        Date expire = new Date(now.getTime() + 3_600_000);
        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expire)
                .withClaim("id",userDto.getId())
                .withClaim("username",userDto.getUsername())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        UserJWTDto user = new UserJWTDto();
        user.setUsername(decodedJWT.getIssuer());
        user.setId(decodedJWT.getClaim("id").asLong());
        user.setEmail(decodedJWT.getClaim("email").asString());

        return new UsernamePasswordAuthenticationToken(user,null, Collections.emptyList());
    }
}
