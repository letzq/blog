package com.xy.blog.framework.security.filter;

import com.xy.blog.framework.security.context.UserContext;
import com.xy.blog.framework.security.service.TokenService;
import com.xy.blog.framework.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT 认证过滤器。
 * 从请求头解析 Bearer token，校验通过后恢复当前请求的认证上下文。
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String authorizationHeader = request.getHeader(tokenService.getHeaderName());
        String token = tokenService.resolveToken(authorizationHeader);

        if (StringUtils.hasText(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtUtils.validateToken(token)) {
                    Long userId = jwtUtils.getUserId(token);
                    String userName = jwtUtils.getUserName(token);
                    if (tokenService.isTokenActive(userId, token)) {
                        UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        UserContext.set(userId, userName);
                    }
                }
            } catch (Exception exception) {
                SecurityContextHolder.clearContext();
                UserContext.clear();
            }
        }

        filterChain.doFilter(request, response);
    }
}