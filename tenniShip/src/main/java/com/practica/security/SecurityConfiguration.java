package com.practica.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserRepositoryAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	// Public pages 
        http.authorizeRequests().antMatchers("/TenniShip").permitAll();
        http.authorizeRequests().antMatchers("/TenniShip/SignIn").permitAll();
        http.authorizeRequests().antMatchers("/TenniShip/SignUp").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/contactform/**","/css/**",
        		"/js/**","/img/**","/lib/**").permitAll();
        
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        // Private pages (all other pages)
        http.authorizeRequests().anyRequest().authenticated();
        //http.authorizeRequests().antMatchers("/home").hasAnyRole("USER");
        //http.authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/TenniShip/SignIn");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/TenniShip");
        http.formLogin().failureUrl("/loginerror");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/TenniShip/SignIn");
    
     // Disable CSRF at the moment
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        // Database authentication provider
        auth.authenticationProvider(authenticationProvider);
    }
}