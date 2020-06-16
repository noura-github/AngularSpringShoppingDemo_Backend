package technou.com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import technou.com.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{
	
	Optional<Wishlist> findByCustomerId(int customerId);

}