package technou.com.dao;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import technou.com.model.product.Printer;


@ComponentScan(basePackages = "technou.com")
public interface PrinterRepository extends JpaRepository<Printer, Integer>{
	
	//Look for the printers in sale
    @Query("SELECT pr FROM Printer pr WHERE pr.sale > 0")
    List<Printer> findSalePrinter();

}
