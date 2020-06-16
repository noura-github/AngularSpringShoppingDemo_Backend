package technou.com.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.dao.LaptopRepository;
import technou.com.model.product.Laptop;

@RestController
@RequestMapping("/laptops")
public class LaptopController {

	
	@Autowired
	private LaptopRepository laptopRepository;
	
		
	@GetMapping("")
	public ResponseEntity<Collection<Laptop>> getLaptops() {
		
		List<Laptop> products = laptopRepository.findAll();
		
		return ResponseEntity.ok().body(products==null?new ArrayList<Laptop>():products);
	}
	
	@GetMapping("/sale")
	public ResponseEntity<Collection<Laptop>> getSaleLaptops() {
		
		List<Laptop> products = laptopRepository.findSalePrinter();
		
		List<Laptop> saleproducts = products==null?new ArrayList<Laptop>():products;

		return ResponseEntity.ok().body(saleproducts);
	}
}
