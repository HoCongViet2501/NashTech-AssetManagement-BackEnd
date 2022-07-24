package com.nashtech.rookies.java05.AssetManagement.security.jwt;

import com.nashtech.rookies.java05.AssetManagement.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
	private final JwtProvider jwtProvider;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);
		try {
			if (token != null && jwtProvider.validateToken(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (JwtAuthenticationException e) {
			SecurityContextHolder.clearContext();
			((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
			throw new JwtAuthenticationException("JWT.token.is.expired.or.invalid");
		}
		filterChain.doFilter(servletRequest,servletResponse);
	}
}
