package com.jobportal.api_gateway.filter;

import com.jobportal.api_gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            log.info("Request path : {}", path);

            if(isPathPublic(path)) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid authorization for path: {}", path);
                return unauthorizedResponse(exchange);
            }

            String token = authHeader.substring(7);

            if(!jwtUtil.isTokenValid(token)) {
                log.warn("Invalid Jwt token for path: {}", path);
                return unauthorizedResponse(exchange);
            }

            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("X-User-Email", email)
                            .header("X-User-Role", role)
                            .build())
                    .build();

            log.info("Jwt Valid. User={} role={}", email, role);

            return chain.filter(modifiedExchange);
        };
    }

    public Boolean isPathPublic(String path) {
        return path.contains("/api/users/register") ||
                path.contains("/api/users/login") ||
                path.contains("/actuator") ||
                (path.contains("/api/jobs") &&
                 !path.contains("/apply") &&
                 !path.contains("/applications"));
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {

    }
}
