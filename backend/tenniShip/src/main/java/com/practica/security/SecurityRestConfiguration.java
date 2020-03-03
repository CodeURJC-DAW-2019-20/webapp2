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
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/SignIn").permitAll();//set logged user
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/TenniShip/SignUp").permitAll();	
		
		//Tournament
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/RegisterMatch/Tournament/{tournament}").hasAnyRole("USER");	
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/TenniShip/RegisterMatch/Tournament/{tournament}/Submission").hasAnyRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/TenniShip/RegisterMatch/Tournament").hasAnyRole("USER");
        
        //Creator
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/TenniShip/Creator").hasAnyRole("USER","ADMIN");
        
        //http.authorizeRequests().antMatchers(HttpMethod.DELETE, "").hasAnyRole("ADMIN");

		// Use HTTP basic authentication
		http.httpBasic();

        // Disable CSRF
        http.csrf().disable();     
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }
}