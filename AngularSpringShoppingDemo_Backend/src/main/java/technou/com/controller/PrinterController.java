package technou.com.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import technou.com.dao.PrinterRepository;
import technou.com.model.product.Printer;


@RestController
@RequestMapping("/printers")
public class PrinterController {


	@Autowired
	private PrinterRepository printerRepository;
	
	
	@GetMapping("")
	public ResponseEntity<Collection<Printer>> getPrinters() {
		
		System.out.println("## getPrinters ##");
		
		List<Printer> products = printerRepository.findAll();
		
		return ResponseEntity.ok().body(products==null?new ArrayList<Printer>():products);
	}
	
	@GetMapping("/sale")
	public ResponseEntity<Collection<Printer>> getSalePrinters() {
		
		System.out.println("## getSalePrinters ##");
		
		List<Printer> products = printerRepository.findSalePrinter();
		
		List<Printer> saleproducts = products==null?new ArrayList<Printer>():products;
		
		return ResponseEntity.ok().body(saleproducts);
	}
}
