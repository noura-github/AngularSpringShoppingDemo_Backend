package technou.com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.dao.ProductRepository;
import technou.com.model.product.Product;


@RestController
@RequestMapping("/products")
public class ProductController {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping("")
	public ResponseEntity<List<Product>> getProducts() {
		
		List<Product> products = productRepository.findAll();

		return  ResponseEntity.ok().body(products);
	}
	
	@GetMapping("/searchProducts/{searchItem}")
	public ResponseEntity<List<Product>> searchProducts(@PathVariable("searchItem") String searchItem) {
		
		List<Product> products = productRepository.findBySearchitem(searchItem);
	
		return  ResponseEntity.ok().body(products);
	}
	
	@GetMapping("/getProductById/{productNumber}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer productNumber) throws Exception {
		
		System.out.println("## getProductById ##");
		
		Optional<Product> product = productRepository.findById(productNumber);

		product.orElseThrow(()->new Exception(String.format("Product with id % not found", productNumber)));
		
		return ResponseEntity.ok().body(product.get());
	}

}
