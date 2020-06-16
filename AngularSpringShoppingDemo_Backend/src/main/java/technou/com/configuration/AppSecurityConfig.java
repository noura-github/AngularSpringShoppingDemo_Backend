package technou.com.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import technou.com.dao.CustomerRepository;
import technou.com.service.AppUserDetailsService;


@Configuration
@EnableWebSecurity //(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableJpaRepositories(basePackageClasses=CustomerRepository.class)
@ComponentScan(basePackages = "technou.com")
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	/**
	 * To tell the authentication manager what kind of authentication you want
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}


	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
			
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			
		provider.setPasswordEncoder(passwordEncoder);

		provider.setUserDetailsService(appUserDetailsService);
			
		return provider;
	}
		
	 @Override
     public void configure(WebSecurity web) throws Exception {
         web
                 .ignoring()
                 .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
     }
	

	 
	@Override //here to secure the access to the app
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.cors().configurationSource(request -> new CorsConfiguration()
				   .applyPermitDefaultValues());
		
		http.csrf().disable()
		.authorizeRequests()
		  .antMatchers("/cart/**", "/wishlist/**", "/customer/deleteCustomerById/**").hasAnyRole(AppUserRole.USER.name())
		  .antMatchers("/login", "/logout", "/customer/**", "/products/**", "/laptops/**", "/printers/**").permitAll()
		  .anyRequest().authenticated()
		 .and().httpBasic()
	       .and()
	        .logout()
	        .invalidateHttpSession(true)
			.clearAuthentication(true)
	        .logoutUrl("/logout")
	        .permitAll()
	        .logoutSuccessUrl("/logout-done");
		
	}
}
