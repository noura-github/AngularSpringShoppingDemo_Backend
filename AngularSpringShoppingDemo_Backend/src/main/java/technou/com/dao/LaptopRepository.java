package technou.com.dao;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import technou.com.model.product.Laptop;


@ComponentScan(basePackages = "technou.com")
public interface LaptopRepository extends JpaRepository<Laptop, Integer>{
	
	//Look for the laptops in sale
    @Query("SELECT lp FROM Laptop lp WHERE lp.sale > 0")
    List<Laptop> findSalePrinter();

}
