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

import technou.com.dao.CartProductRepository;
import technou.com.dao.CartRepository;
import technou.com.dao.ProductRepository;
import technou.com.model.Cart;
import technou.com.model.product.CartProduct;
import technou.com.model.product.Product;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartProductRepository cartProductRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(path = "/getCart/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<CartProduct>>getCart(@PathVariable(value="customerId") int customerId) throws Exception {

		Optional<Cart> cartopt = cartRepository.findByCustomerId(customerId);
		
		cartopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));
		
		Cart cart = cartopt.get();
		
		return ResponseEntity.ok().body(cart.getProductsInCart());
	}
	
	@PostMapping(path = "/addCartProduct/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<CartProduct>> addCartProduct(@PathVariable(value="customerId") int customerId,
			@Valid @RequestBody CartProduct cartProduct
			) throws Exception {
	

		Optional<Cart> cartopt = cartRepository.findByCustomerId(customerId);
		
		cartopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));

		Cart cart = cartopt.get();
		
		Optional<CartProduct> cPdbOpt = null;

		try {
			cPdbOpt = cartProductRepository.findByProductId(cartProduct.getProductId());
		}
		catch (Exception e) {
		   
		}
		
		if ((cPdbOpt!=null)&&cPdbOpt.isPresent()) {
			
			CartProduct cPdb = cPdbOpt.get();

			cPdb.setQty(cPdb.getQty()+1);
			
			cartProductRepository.saveAndFlush(cPdb);
			
			cart.setProductsInCart(
					cart.getProductsInCart().stream()
					.filter(x->x.getProductId()!=cPdb.getProductId())
					.collect(Collectors.toCollection(ArrayList::new))
					);
			cart.getProductsInCart().add(cPdb);

			cartRepository.saveAndFlush(cart);
			
		} else {

			Optional<Product> mainProduct = productRepository.findById(cartProduct.getProductId());
			
			if (mainProduct.isPresent()&&(cartProduct.getMainProduct()==null)) {
				
				cartProduct.setMainProduct(mainProduct.get());
			}
			
			@Valid
			CartProduct addedCartProduct = cartProductRepository.saveAndFlush(cartProduct);
			
			cart.getProductsInCart().add(addedCartProduct);
			
			cartRepository.saveAndFlush(cart);
		}
		return ResponseEntity.ok().body(cart.getProductsInCart());
	}
	
	
	@PostMapping(path = "/reduceCartProductQty/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<CartProduct>> reduceCartProductQty(@PathVariable(value="customerId") int customerId,
				@Valid @RequestBody CartProduct cartProduct
				) throws Exception {

			
			Optional<Cart> cartopt = cartRepository.findByCustomerId(customerId);
			
			cartopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));
			
			Cart cart = cartopt.get();

			Optional<CartProduct> cPdbOpt = cartProductRepository.findByProductId(cartProduct.getProductId());
			
			if (cPdbOpt.isPresent()) {
				
				CartProduct cPdb = cPdbOpt.get();
				
				cPdb.setQty(cPdb.getQty()-1);
				
				if (cPdb.getQty()<=0) {
					
					cart.setProductsInCart(
							cart.getProductsInCart().stream()
							.filter(x->x.getProductId()!=cPdb.getProductId())
							.collect(Collectors.toCollection(ArrayList::new))
							);
					
					cartProductRepository.deleteById(cPdb.getCartProductId());
				}
				
				cartRepository.saveAndFlush(cart);	
			} 

			return ResponseEntity.ok().body(cart.getProductsInCart());
	}
	
	@PostMapping(path = "/deleteCartProduct/{customerId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Collection<CartProduct>> deleteCartProduct(
				@PathVariable(value="customerId") int customerId,
				@Valid @RequestBody CartProduct cartProduct
				) throws Exception {
		
			
			Optional<Cart> cartopt = cartRepository.findByCustomerId(customerId);
			
			cartopt.orElseThrow(()->new Exception(String.format("Cart not found % for the customer", customerId)));

			Cart cart = cartopt.get();

			Optional<CartProduct> cPdbOpt = cartProductRepository.findByProductId(cartProduct.getProductId());
			
			if (cPdbOpt.isPresent()) {

				CartProduct cPdb = cPdbOpt.get();
				
				cPdb.setQty(0);
				
				cart.setProductsInCart(
						cart.getProductsInCart().stream()
						.filter(x->x.getCartProductId()!=cPdb.getCartProductId())
						.collect(Collectors.toCollection(ArrayList::new))
						);
				
				cartProductRepository.deleteById(cartProduct.getCartProductId());

				cartRepository.saveAndFlush(cart);				
			} 		
			
			return ResponseEntity.ok().body(cart.getProductsInCart());
	}
}
