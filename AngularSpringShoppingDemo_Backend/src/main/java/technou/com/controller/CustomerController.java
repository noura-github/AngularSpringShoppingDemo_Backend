package technou.com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.dao.CustomerRepository;
import technou.com.model.Customer;
import technou.com.service.AppUserService;


@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Autowired
	private AppUserService appUserService;
	
	
	@GetMapping("/getCustomer/{customerId}")
	public Customer getCustomer(@PathVariable(value="customerId") int customerId) throws Exception {

		Optional<Customer> customer = customerRepository.findById(customerId);
		
		customer.orElseThrow(()->new Exception(String.format("User with id % not found", customerId)));

		return customer.get();
	}
	
	@GetMapping("/checkCustomerByName/{username}")
	public ResponseEntity<Boolean> checkCustomerByName(@PathVariable String username) throws Exception {

		Optional<Customer> cust = customerRepository.findByUsername(username);
		
		if (cust.isPresent()) return ResponseEntity.ok().body(true);
		
		return ResponseEntity.ok().body(false);
	}
	
	@GetMapping("/checkCustomerPassword/{customerId}/{password}")
	public ResponseEntity<String> checkCustomerPassword(@PathVariable int customerId, @PathVariable String password) throws Exception {
		
		boolean valid = appUserService.checkPassword(customerId, password);
		
		if (valid) return ResponseEntity.ok().body("true");
		
		return ResponseEntity.ok().body("false");
	}
	
	@GetMapping("/updateCustomerPassword/{customerId}/{password}")
	public ResponseEntity<String> updateCustomerPassword(@PathVariable int customerId, @PathVariable String password) throws Exception {

		boolean success = appUserService.updatePassword(customerId, password);
		
		if (success) return ResponseEntity.ok().body("true");
		
		return ResponseEntity.ok().body("false");
	}
	
	@PostMapping("/registerCustomer")
	public ResponseEntity<Customer>  addCustomer(@RequestBody Customer customer) {
		
		Customer savedCustomer = appUserService.save(customer);
		
		return ResponseEntity.ok().body(savedCustomer);
	}
	
	@PostMapping("/deleteCustomerById/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") Integer customerId) {

		boolean success = appUserService.deleteById(customerId);
		
		if (success) return ResponseEntity.ok().body("true");
		
		return ResponseEntity.ok().body("false");
	}
	
}
