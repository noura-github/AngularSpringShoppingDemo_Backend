package technou.com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import technou.com.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	Optional<Cart> findByCustomerId(int customerId);

}
