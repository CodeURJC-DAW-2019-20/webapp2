package com.practica.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class SecurityRestConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");

		// User
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/SignIn").permitAll();// set logged user
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/TenniShip/SignUp").permitAll();

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/Teams/**").permitAll();
		// Tournament
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/Tournaments/").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/RegisterMatch/Tournaments/**")
				.hasAnyRole("USER");
		http.authorizeRequests()
				.antMatchers(HttpMethod.PUT, "/api/TenniShip/RegisterMatch/Tournaments/{tournament}/Submission")
				.hasAnyRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/RegisterMatch/Tournaments")
				.hasAnyRole("USER");

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/Tournaments/**").permitAll();

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/TenniShip/ADMIN/Tournaments/{tournament}/EditMatches/{group}")
				.hasAnyRole("ADMIN");

		http.authorizeRequests()
				.antMatchers(HttpMethod.PUT,
						"/api/TenniShip/ADMIN/Tournaments/{tournament}/EditMatches/{group}/Submission")
				.hasAnyRole("ADMIN");

		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/TenniShip/ADMIN/Tournaments/{tournament}/Deleted")
				.hasAnyRole("ADMIN");

		http.authorizeRequests().anyRequest().authenticated();

		// Use HTTP basic authentication
		http.httpBasic();

		// Disable CSRF
		http.csrf().disable();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {
		});

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);

	}
}