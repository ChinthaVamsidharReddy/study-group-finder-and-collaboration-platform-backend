package com.infy.project.config;

import com.infy.project.security.JwtFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ✅ CORS support
            .cors(Customizer.withDefaults())

            // ✅ Disable CSRF (since using JWT)
            .csrf(csrf -> csrf.disable())

            // ✅ Authorization Rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/groups/*/sessions").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/groups/*/sessions").authenticated()
                .requestMatchers("/api/sessions/**").authenticated()
                .anyRequest().permitAll()
            )

            // ✅ Add JWT filter before standard username/password auth
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
            "http://localhost:3000",
            "https://study-group-finder-and-collaboratio-roan.vercel.app"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        // ✅ PRINT CORS ORIGINS TO SERVER LOG
        System.out.println("🚀 CORS Allowed Origins:");
        config.getAllowedOriginPatterns().forEach(origin ->
            System.out.println("   → " + origin)
        );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
        public FilterRegistrationBean<CorsDebugFilter> corsDebugFilter() {
            FilterRegistrationBean<CorsDebugFilter> bean = new FilterRegistrationBean<>();
            bean.setFilter(new CorsDebugFilter());
            bean.addUrlPatterns("/*");
            return bean;
        }

        class CorsDebugFilter implements jakarta.servlet.Filter {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest req,
                                jakarta.servlet.ServletResponse res,
                                jakarta.servlet.FilterChain chain)
                    throws java.io.IOException, jakarta.servlet.ServletException {

                var request = (jakarta.servlet.http.HttpServletRequest) req;

                System.out.println("🌍 CORS Request From Origin: " + request.getHeader("Origin"));
                System.out.println("➡  Path: " + request.getRequestURI());
                System.out.println("-------------------------");

                chain.doFilter(req, res);
            }
        }


}
