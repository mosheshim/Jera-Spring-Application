package moshe.shim.jera.security;

import lombok.val;
import moshe.shim.jera.services.impl.CustomDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTAuthentication jwtAuth;
    private final CustomDetailsService userDetailsService;

    public AuthenticationFilter(JWTAuthentication jwtAuth, CustomDetailsService customDetailsService) {
        this.jwtAuth = jwtAuth;
        this.userDetailsService = customDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            Optional<String> email = jwtAuth.validateJWT(request);

            if (email.isPresent()){
                val userDetails = userDetailsService.loadUserByUsername(email.get());

                val auth = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
                auth.setDetails(request);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
    }


}
