package technou.com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.authority.AppUserDetails;
import technou.com.dao.CustomerRepository;
import technou.com.model.Customer;


@RestController
public class LoginController {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	@GetMapping("/login")
	public ResponseEntity<Customer> login() {
		
		AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<Customer> user = customerRepository.findByUsername(userDetails.getUsername());
		

		if (!user.isPresent()) {			
			user.orElseThrow(()->new UsernameNotFoundException(String.format("User % not found", userDetails.getUsername())));			
		}
		
		Customer customer = user.get();
		
		return ResponseEntity.ok().body(customer);
	}

	
	@GetMapping("logout-done")
	public String logoutPage() {

		return "logout-done";
	}
}
