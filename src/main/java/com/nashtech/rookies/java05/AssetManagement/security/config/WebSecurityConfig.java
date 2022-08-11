package com.nashtech.rookies.java05.AssetManagement.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nashtech.rookies.java05.AssetManagement.security.UserDetailsServiceImpl;
import com.nashtech.rookies.java05.AssetManagement.security.jwt.JwtConfigure;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtConfigure jwtConfigure;
	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	public WebSecurityConfig(JwtConfigure jwtConfigure, UserDetailsServiceImpl userDetailsServiceImpl) {
		this.jwtConfigure = jwtConfigure;
		userDetailsService = userDetailsServiceImpl;
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// TODO
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable().authorizeRequests().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**").permitAll()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/user/register").hasAnyAuthority("ADMIN")
				.antMatchers("/api/user/check/**", "/api/user/disable/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/user/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/category/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/asset/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/report/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/staff/**").hasAnyAuthority("STAFF", "ADMIN")
				.antMatchers("/api/assignment/**").hasAnyAuthority("ADMIN")
				.antMatchers("/api/return/**").hasAnyAuthority("ADMIN", "STAFF")
				.anyRequest().authenticated()
				.and().apply(jwtConfigure);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

}
