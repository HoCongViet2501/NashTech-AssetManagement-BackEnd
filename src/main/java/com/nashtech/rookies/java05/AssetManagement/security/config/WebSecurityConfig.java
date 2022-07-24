package com.nashtech.rookies.java05.AssetManagement.security.config;

import com.nashtech.rookies.java05.AssetManagement.model.enums.UserRole;
import com.nashtech.rookies.java05.AssetManagement.security.jwt.JwtConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtConfigure jwtConfigure;
	
	@Autowired
	public WebSecurityConfig(JwtConfigure jwtConfigure) {
		this.jwtConfigure = jwtConfigure;
	}

	@Configuration
	public class CorsConfigure {
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
	}
	
	@Bean
	@Override
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
				.antMatchers("/api/auth/login","/api/auth/user/**").permitAll()
				.antMatchers("/api/user/register").permitAll()
				.anyRequest().authenticated()
				.and().apply(jwtConfigure);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(8);
	}
	
}
