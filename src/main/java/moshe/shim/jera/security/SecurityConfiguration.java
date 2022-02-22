package moshe.shim.jera.security;

import moshe.shim.jera.services.impl.CustomDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomDetailsService customDetailsService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            CustomDetailsService customDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.customDetailsService = customDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/1/coffee/**").permitAll()
                .antMatchers("/api/1/coffee/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/1/product-series/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/1/product-series/**").hasRole("ADMIN")

                .antMatchers("/api/1/auth/**").permitAll()

                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic().authenticationEntryPoint(new AuthEntryPoint())
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
