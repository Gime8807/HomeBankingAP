package com.mindhub.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@EnableWebSecurity
@Configuration
public class WebAuthorization  {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        http.authorizeRequests()
                .antMatchers("/web/index.html", "/web/js/**","/web/css/**","/web/img/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login","/api/logout","/api/clients").permitAll()
                .antMatchers("/h2-console/**","/rest/**", "/api/clients").hasAuthority("ADMIN")
                .antMatchers("/api/**","/api/clients/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/clients/current", "/web/**","/api/clients/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/clients/current/accounts/**","/clients/current/cards").hasAuthority("CLIENT")
                .anyRequest().denyAll();


        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
