package com.project.travelExperts.config;

import com.project.travelExperts.data.enums.Role;
import com.project.travelExperts.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@Component
public class SecurityConfig {
    @Autowired
    private  UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private  AuthEntryPointJwt authEntryPointJwt;

//    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt authEntryPointJwt) {
//        this.userDetailsServiceImpl = userDetailsService;
//        this.authEntryPointJwt = authEntryPointJwt;
//    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/admin/**").permitAll()
                        .requestMatchers("/api/v1/auth/**","/api/v1/payment/**").permitAll()
                        .requestMatchers("/api/v1/auth/agent/**").permitAll()
                        .requestMatchers("api/v1/auth/customer/**").permitAll()
                        .requestMatchers(
                                "/api/v1/admin/createAgentManager",
                                "/api/v1/admin/customers",
                                "api/v1/admin/agents",
                                "api/v1/admin/agentManagers"
                        )
                        .hasAnyRole(Role.SUPER_ADMIN.name())
                        .requestMatchers("/api/v1/admin/createAgent",
                                "api/v1/admin/agentManagers-agents",
                                "/api/v1/admin//agent/deactivate/**",
                                "/api/v1/admin//agent/activate/**").hasAnyRole(Role.AGENT_MANAGER.name())
                        .requestMatchers("/api/v1/customer/**","/api/v1/review/**").hasAnyRole(Role.CUSTOMER.name())
                        .requestMatchers("/api/v1/agent/**").hasAnyRole(Role.AGENT.name())
                        .requestMatchers("/api/v1/customer/**","/api/v1/bookings/**").hasAnyRole(Role.CUSTOMER.name())
                        .requestMatchers("/api/v1/package/create",
                                "/api/v1/package/update",
                                "/api/v1/package/delete",
                                "/api/v1/product/create",
                                "/api/v1/product/update/**",
                                "/api/v1/product/delete/**",
                                "/api/v1/supplier/create",
                                "/api/v1/supplier/delete/**",
                                "/api/v1/supplier/update/**"
                                ).hasAnyRole(Role.AGENT.name(),Role.AGENT_MANAGER.name())
                        .requestMatchers("/api/v1/package/**","/api/v1/product/**","/api/v1/supplier/**").hasAnyRole(Role.AGENT.name(),Role.AGENT_MANAGER.name(),Role.SUPER_ADMIN.name(),Role.CUSTOMER.name())
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
