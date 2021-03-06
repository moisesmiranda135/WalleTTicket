package com.salesianos.triana.dam.walleTTicket.security;

import com.salesianos.triana.dam.walleTTicket.model.Category;
import com.salesianos.triana.dam.walleTTicket.security.jwt.JwtAccessDeniedHandler;
import com.salesianos.triana.dam.walleTTicket.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthorizationFilter filter;
    private final JwtAccessDeniedHandler accessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()

                //USER
                .antMatchers(HttpMethod.POST, "/auth/register/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/register/employee").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/register/user").anonymous()
                .antMatchers(HttpMethod.POST,"/auth/login").anonymous()
                .antMatchers(HttpMethod.GET,"/me").authenticated()

                .antMatchers(HttpMethod.GET, "/auth/all").hasAnyRole("ADMIN","EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/auth/all/employee").authenticated()
                .antMatchers(HttpMethod.GET, "/auth/all/admin").authenticated()

                .antMatchers(HttpMethod.PUT, "/user/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/employee/{id}").hasAnyRole("ADMIN","EMPLOYEE")
                .antMatchers(HttpMethod.PUT, "/admin/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/user/enabled/").authenticated()
                .antMatchers(HttpMethod.POST, "/user/disabled/").authenticated()

                .antMatchers(HttpMethod.DELETE, "/employee/{id}").hasRole("ADMIN")

                //CATEGORY

                .antMatchers(HttpMethod.POST,"/category/").authenticated()
                .antMatchers(HttpMethod.GET,"/category/").authenticated()
                .antMatchers(HttpMethod.GET,"/category/{id}").authenticated()
                .antMatchers(HttpMethod.PUT,"/category/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE,"/category/{id}").authenticated()

                //COMPANY

                .antMatchers(HttpMethod.POST,"/company/").authenticated()
                .antMatchers(HttpMethod.GET,"/company/").authenticated()
                .antMatchers(HttpMethod.GET,"/company/{id}").authenticated()
                .antMatchers(HttpMethod.PUT,"/company/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE,"/company/{id}").authenticated()

                //TICKET

                .antMatchers(HttpMethod.POST,"/ticket/*").authenticated()
                .antMatchers(HttpMethod.GET,"/ticket/*").authenticated()
                .antMatchers(HttpMethod.GET,"/ticket/all/user/favorite").authenticated()
                .antMatchers(HttpMethod.PUT,"/ticket/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE,"/ticket/{id}").authenticated()

                .anyRequest().anonymous();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
