package com.nashtech.rookies.java05.AssetManagement.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.nashtech.rookies.java05.AssetManagement.exception.JwtAuthenticationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
	@Autowired
	private final JwtProvider jwtProvider;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);

		try {
			if (token != null && jwtProvider.validateToken(token)) {
				UsernamePasswordAuthenticationToken authentication = jwtProvider.getAuthentication(token);
				if (authentication != null) {
					authentication.setDetails(
							new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
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
