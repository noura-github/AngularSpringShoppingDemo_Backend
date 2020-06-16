package technou.com.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import technou.com.model.product.WishProduct;

public interface WishProductRepository extends JpaRepository<WishProduct, Integer>{

	Optional<WishProduct> findByProductId(int productId);
}