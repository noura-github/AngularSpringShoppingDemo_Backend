package technou.com.dao;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import technou.com.model.Customer;

@ComponentScan(basePackages = "technou.com")
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
		
	Optional<Customer> findByUsername(String username);

}
