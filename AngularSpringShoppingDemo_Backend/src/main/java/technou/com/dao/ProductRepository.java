package technou.com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import technou.com.model.product.Product;


@ComponentScan(basePackages = "technou.com")
public interface ProductRepository extends JpaRepository<Product, Integer>{
		
	Optional<Product> findByProductId(int productId);

    @Query("SELECT pr FROM Product pr WHERE UPPER(pr.productName) LIKE CONCAT('%',UPPER(:searchitem),'%')"
    		+ "OR UPPER(pr.productDescription) LIKE CONCAT('%',UPPER(:searchitem),'%')"

    		)
    List<Product> findBySearchitem(String searchitem);

}
