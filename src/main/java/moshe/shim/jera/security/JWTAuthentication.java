package moshe.shim.jera.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.val;
import moshe.shim.jera.exceptions.TokenException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTAuthentication {

    private final Environment environment;

    public JWTAuthentication(Environment environment) {
        this.environment = environment;
    }


    public String buildJWT(String email) {
        val expires = environment.getProperty("shim.mosh.jwt.expires", Long.class);
        val secret = environment.getProperty("shim.mosh.jwt.secret");
        if (expires == null || secret == null)
            throw new NullPointerException("Secret JWT key or JWT Expires setting is not in properties");

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + expires) )
                .withSubject(email)
                .sign(Algorithm.HMAC512(secret));
    }

    public Optional<String> validateJWT(HttpServletRequest request) {
        return validateJWT(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

        public Optional<String> validateJWT(String bearerToken){
        val jwt = getJWTFromRequest(bearerToken);
        val secret = environment.getProperty("shim.mosh.jwt.secret");
        if(StringUtils.hasText(secret) && StringUtils.hasText(jwt))
            try {
                String email = JWT.require(Algorithm.HMAC512(secret))
                        .build()
                        .verify(jwt)
                        .getSubject();

                return StringUtils.hasText(email) ? Optional.of(email) : Optional.empty();
            }catch (TokenExpiredException e){
                throw new TokenException(jwt, "Expired");
            }catch (JWTVerificationException e){
                throw new TokenException(jwt, "Invalid");
            }
        return Optional.empty();
    }

    private String getJWTFromRequest(String bearerToken){
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring("Bearer ".length());
        return null;
    }
}
