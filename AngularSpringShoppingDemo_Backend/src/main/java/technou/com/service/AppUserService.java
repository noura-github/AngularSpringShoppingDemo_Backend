package technou.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import technou.com.dao.CustomerRepository;
import technou.com.dao.RoleRepository;
import technou.com.model.Customer;

@Service
public class AppUserService implements UserService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Customer save(Customer user) {
		
		//Encrypt the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAcceptTerms(true);

	    user.getRoles().forEach(role->{
	    	roleRepository.save(role);
	    });
	    
	    return customerRepository.save(user);
	}
	
	@Override
	public boolean checkPassword(int userId, String password) {

		Optional<Customer> userdbOpt = customerRepository.findById(userId);
		
		if (userdbOpt.isPresent()) {
			
			Customer userdb = userdbOpt.get();
			
			return passwordEncoder.matches(password, userdb.getPassword());
			
		}
		
		return false;
	}
	
	@Override
	public boolean updatePassword(int userId, String password) {

		Optional<Customer> userdbOpt = customerRepository.findById(userId);
		
		if (userdbOpt.isPresent()) {
			
			Customer userdb = userdbOpt.get();
			
			userdb.setPassword(passwordEncoder.encode(password));
			userdb.setPassword_confirmation(passwordEncoder.encode(password));
		
			userdb.setAcceptTerms(true);

			userdb = customerRepository.save(userdb);
			
			return (userdb!=null);
		}
		
		return false;
	}


	@Override
	public boolean deleteByUsername(String username) {

		Optional<Customer> user = customerRepository.findByUsername(username);
		
		if (user.isPresent()) {
			
			customerRepository.delete(user.get());
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean deleteById(int userId) {

		Optional<Customer> user = customerRepository.findById(userId);
		
		if (user.isPresent()) {
			
			customerRepository.deleteById(user.get().getCustomerId());
			
			return true;
		}
		
		return false;
	}
}
