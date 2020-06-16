package technou.com.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "productId", unique = true, nullable = false)
	private int productId;
		
	@Column(name = "productName", nullable = false)
	@NotBlank(message = "Product name is mandatory")
	private String productName;

	@Column(name = "productDescription")
	private String productDescription;
	
	
	@Column(name = "imageUrl")
	@Lob
	private String imageUrl;
	
	
	@Column(name = "quantityInStock", nullable = false)
	@NotNull(message = "QuantityInStock is mandatory")
	@Min(value = 0, message = "The value must be positive")
	private int quantityInStock;
	

	@Column(name = "buyPrice", nullable = false)
	@NotNull(message = "Buy price is mandatory")
	@Min(value = 0L, message = "The value must be positive")
	private double buyPrice;
	
	@Column(name = "sale", nullable = false)
	@NotNull(message = "Sale is mandatory")
	@Min(value = 0, message = "The value of sale must be positive")
	@Max(value = 99, message = "The max value of sale should not greater than 99")
	private int sale;

}
