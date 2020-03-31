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
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/validator/user").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/signin").permitAll();// set logged user
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/tenniship/signup").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniShip/logout").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/tenniship/teams/{teamID}/image").hasAnyRole("USER");
		
		//Team
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/teams").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/teams/**").permitAll();
		
		// Tournament
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/tournaments").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/tournaments/{tournamentId}/image").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/tournaments/teams/matches")
		.hasAnyRole("USER");
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.PUT, "/api/tenniship/tournaments/{tournament}/matches")
		.hasAnyRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/tournaments/{tournament}/matches")
				.hasAnyRole("USER");
		

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/tenniship/tournaments/**").permitAll();

		//ADMIN
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/tenniship/admin/tournaments/{tournament}/matches/{group}")
				.hasAnyRole("ADMIN");

		http.authorizeRequests()
				.antMatchers(HttpMethod.PUT,
						"/api/tenniship/admin/tournaments/{tournament}/matches/{group}")
				.hasAnyRole("ADMIN");

		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/tenniship/admin/tournaments/{tournament}")
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