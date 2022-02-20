package moshe.shim.jera.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.val;
import moshe.shim.jera.exceptions.TokenException;
import moshe.shim.jera.exceptions.UserExistsException;
import moshe.shim.jera.services.impl.CustomDetailsService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final Environment environment;
    private final CustomDetailsService userDetailsService;

    public JWTAuthenticationFilter(Environment environment, CustomDetailsService customDetailsService) {
        this.environment = environment;
        this.userDetailsService = customDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            Optional<String> email = validateJWT(getJWTFromRequest(request));

            if (email.isPresent()){
                val userDetails = userDetailsService.loadUserByUsername(email.get());

                val auth = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                auth.setDetails(request);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
    }

    private Optional<String> validateJWT(String jwt){
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

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring("Bearer ".length());
        return null;
    }
}
