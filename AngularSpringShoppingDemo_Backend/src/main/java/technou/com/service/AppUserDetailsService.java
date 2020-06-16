package technou.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import technou.com.authority.AppUserDetails;
import technou.com.dao.CustomerRepository;
import technou.com.model.Customer;


@Service
public class AppUserDetailsService implements UserDetailsService{

	
	@Autowired 
	private CustomerRepository customerRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> userOp = customerRepository.findByUsername(username);

		userOp.orElseThrow(()->new UsernameNotFoundException(String.format("User % not found", username)));
		
		return userOp.map(AppUserDetails::new).get();
	}
}
