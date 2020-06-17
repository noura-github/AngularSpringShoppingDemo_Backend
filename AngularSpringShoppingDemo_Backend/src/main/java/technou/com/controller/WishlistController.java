package technou.com.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.dao.ProductRepository;
import technou.com.dao.WishProductRepository;
import technou.com.dao.WishlistRepository;
import technou.com.model.Wishlist;
import technou.com.model.product.Product;
import technou.com.model.product.WishProduct;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
	
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private WishProductRepository wishProductRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(path = "/getWishlist/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<WishProduct>>getCart(@PathVariable(value="customerId") int customerId) throws Exception {
		
		Optional<Wishlist> wishlistopt = wishlistRepository.findByCustomerId(customerId);
		
		wishlistopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));
		
		Wishlist wishlist = wishlistopt.get();
		
		return ResponseEntity.ok().body(wishlist.getProductsInWishlist());
	}
	
	@PostMapping(path = "/addWishProduct/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<WishProduct>> addWishProduct(
			@PathVariable(value="customerId") int customerId,
			@Valid @RequestBody WishProduct wishProduct
			) throws Exception {
		

		Optional<Wishlist> wishlistopt = wishlistRepository.findByCustomerId(customerId);
		
		wishlistopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));
		
		Wishlist wishlist = wishlistopt.get();
		
		Optional<WishProduct> wishPrOpt = null;

		try {
			wishPrOpt = wishProductRepository.findByProductId(wishProduct.getProductId());
		}
		catch (Exception e) {
		   
		}
		
		if ((wishPrOpt==null)||((wishPrOpt!=null)&&!wishPrOpt.isPresent())) {

			Optional<Product> mainProduct = productRepository.findById(wishProduct.getProductId());
			
			if (mainProduct.isPresent()&&(wishProduct.getMainProduct()==null)) {
				
				wishProduct.setMainProduct(mainProduct.get());
			}
			
			@Valid
			WishProduct addedWishProduct = wishProductRepository.saveAndFlush(wishProduct);
			
			wishlist.getProductsInWishlist().add(addedWishProduct);
			
			wishlistRepository.saveAndFlush(wishlist);
		}
		
		return ResponseEntity.ok().body(wishlist.getProductsInWishlist());
	}
	
	
	@PostMapping(path = "/deleteWishProduct/{customerId}") 
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<WishProduct>> deleteCartProduct(
				@PathVariable(value="customerId") int customerId,
				@Valid @RequestBody WishProduct wishProduct
				) throws Exception {

				
			Optional<Wishlist> wishlistopt = wishlistRepository.findByCustomerId(customerId);
			
			wishlistopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));
			
			Wishlist wishlist = wishlistopt.get();

			Optional<WishProduct> wishPrOpt = wishProductRepository.findByProductId(wishProduct.getProductId());

			if (wishPrOpt.isPresent()) {
								
				WishProduct wishPrdb = wishPrOpt.get();
				
				wishlist.setProductsInWishlist(
						wishlist.getProductsInWishlist().stream()
						.filter(x->x.getProductId()!=wishPrdb.getProductId())
						.collect(Collectors.toCollection(ArrayList::new))
						);
				
				wishProductRepository.deleteById(wishPrdb.getWishProductId());

				wishlistRepository.saveAndFlush(wishlist);				
			} 		
			
			return ResponseEntity.ok().body(wishlist.getProductsInWishlist());
	}

}
