package br.com.oboticariorevenda.oboticario_revenda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${remember.me}")
    private String remember_me;

    @Bean
    SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().permitAll()
        );
        http.rememberMe(remember -> remember
            .key(remember_me)
            .alwaysRemember(true)
            .tokenValiditySeconds(60 * 60 * 24 * 30)
            .userDetailsService(adminDetails())
        );
        http.formLogin(form -> form
            .loginPage("/secret-login")
            .loginProcessingUrl("/perform-login")
            .failureUrl("/secret-login?error=true")
            .defaultSuccessUrl("/admin/index")
            .permitAll()
        );
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        );
        return http.build();
    }

    @Bean
    UserDetailsService adminDetails() {
        UserDetails admin = User.withUsername(username)
        .password(password)
        .roles("ADMIN")
        .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
