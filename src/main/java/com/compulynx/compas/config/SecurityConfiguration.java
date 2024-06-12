package com.compulynx.compas.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//C:\Users\Ceasar\.m2\repository\com\google\guava\guava\18.0
import com.google.common.collect.ImmutableList;


@Configuration
@EnableWebSecurity
// @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final String CSRF_HEADER_NAME = "XSRF-TOKEN";

//  private final UserService userDetailsService;
//  private final PasswordEncoder passwordEncoder;
//
//  public SecurityConfiguration(UserService userDetailsService, PasswordEncoder passwordEncoder) {
//    this.userDetailsService = userDetailsService;
//    this.passwordEncoder = passwordEncoder;
//  }

//  protected void configure(HttpSecurity http) throws Exception {
//    http
//            .csrf()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////            .and()
////            .authorizeRequests()
////            .antMatchers("/iprint/rest/v1/sysusers/auth").permitAll()
////            .antMatchers("/iprint/rest/v1/getConfigs").permitAll()
////            .antMatchers("/iprint/rest/v1/getPassExpiry").permitAll()
////            .antMatchers("/iprint/rest/v1/getPasswordConfigs").permitAll()
////            .antMatchers("/iprint/rest/v1/dashboard/stats").permitAll()
////            .antMatchers("/iprint/rest/v1/sysusers/**").permitAll()
////            .antMatchers("/iprint/rest/v1/menulist/**").permitAll()
//
//
////            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////            .antMatchers(HttpMethod.GET, "/*.js","/*.woff","/*.ttf", "/*.woff2", "/*.png", "/*.css" ).permitAll()
////            .antMatchers(HttpMethod.GET, "/assets/**").permitAll()
////            .antMatchers(HttpMethod.POST, "/iprint/rest/v1/testAuthenticate").permitAll()
////            .antMatchers(HttpMethod.POST, "/iprint/rest/v1/sysusers/auth").permitAll()
//////            .anyRequest()
////            .authenticated()
////            .and()
////            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
////            // We filter the api/login requests
////            .addFilterBefore(new JWTLoginFilter("/iprint/rest/v1/sysusers/auth", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
////            // And filter other requests to check the presence of JWT in header
////            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//  }

    private CsrfTokenRepository csrfTokenRepository() {
      CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
      repository.setHeaderName(CSRF_HEADER_NAME);
      return repository;
    }



//  //  Added code for authentication
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();

  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
	  /*start comment while on local*/
//  httpSecurity
//  .csrf()
//  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//	.and()
//    .authorizeRequests()
  /*end*/
	     /*start comment while pushing to UAT*/
	httpSecurity.cors().and().csrf().disable()
	.authorizeRequests()
	/*	END*/

	  .antMatchers("/iprint/rest/v1/sysusers/auth").permitAll()
	  .antMatchers("/iprint/rest/v1/getConfigs").permitAll()
	  .antMatchers("/iprint/rest/v1/getPassExpiry").permitAll()
	  .antMatchers("/iprint/rest/v1/getPasswordConfigs").permitAll()
	  .antMatchers("/iprint/rest/v1/dashboard/stats").permitAll()
	  .antMatchers("/iprint/rest/v1/sysusers/**").permitAll()
	  .antMatchers("/iprint/rest/v1/menulist/**").permitAll()
	
	
	  .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	  .antMatchers(HttpMethod.GET, "/*.js","/*.woff","/*.ttf", "/*.woff2", "/*.png", "/*.css" ).permitAll()
	  .antMatchers(HttpMethod.GET, "/assets/**").permitAll()
	  .antMatchers(HttpMethod.GET, "/iprint/rest/v1/getConfigs").permitAll()
	  .antMatchers(HttpMethod.GET, "/eximbank/iprint/rest/v1/getConfigs").permitAll()
	
	  .antMatchers("/**").permitAll()
    
      .anyRequest().authenticated()
      .and().exceptionHandling()
      .and().sessionManagement()
      .and().addFilter(getAuthenticationFilter())
      .addFilter(new AuthorizationFilter(authenticationManager()))
      .sessionManagement()
     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  public AuthenticationFilter getAuthenticationFilter() throws Exception {
    final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/eximbank/iprint/rest/v1/sysusers/auth");
    System.out.println("In this weirdo function." + filter);
    return filter;
  }
  
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
   /*uncomment on local  */// configuration.setAllowedOrigins(ImmutableList.of("http://localhost:4200"));
   /*uncomment while pushing to UAT*/   configuration.setAllowedOrigins(Arrays.asList("*"));
      configuration.setAllowedMethods(Arrays.asList("POST","OPTIONS"));
      configuration.setAllowCredentials(true);
     //configuration.setAllowedHeaders(Arrays.asList("*"));
     configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
  }
 

}
