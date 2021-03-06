package com.asiczen.api.attendancemgmt.security;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.asiczen.api.attendancemgmt.security.jwt.AuthEntryPointJwt;
import com.asiczen.api.attendancemgmt.security.jwt.AuthTokenFilter;
import com.asiczen.api.attendancemgmt.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.cors().and().csrf().disable()
	// .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	// .authorizeRequests().antMatchers("/api/auth/signin").permitAll()
	// .antMatchers("/api/test/**").permitAll()
	// .antMatchers("/api/password/**").permitAll()
	// .antMatchers("/api/auth/signup").permitAll()
	// .anyRequest().authenticated();
	//
	// http.addFilterBefore(authenticationJwtTokenFilter(),
	// UsernamePasswordAuthenticationFilter.class);
	// }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/auth/signin").permitAll().antMatchers("/api/test/**").permitAll()
				.antMatchers("/api/password/**").permitAll().antMatchers("/api/auth/signup").permitAll()
				.antMatchers("/api/org/validate").permitAll().antMatchers("/api/emp/validate").permitAll()
				.antMatchers("/api/file/upload").permitAll().antMatchers("/api/file/download").permitAll()
				.antMatchers("/api/file/delete").permitAll().antMatchers("/api/file/empinouttime").permitAll()
				.antMatchers("/api/file/getlock").permitAll().antMatchers("/api/file/savelock").permitAll().anyRequest()
				.authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
