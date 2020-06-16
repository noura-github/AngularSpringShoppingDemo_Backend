package technou.com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import technou.com.model.product.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{

	Optional<CartProduct> findByProductId(int productId);
}
