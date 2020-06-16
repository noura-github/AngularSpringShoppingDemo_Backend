package technou.com.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name="cartProduct")
public class CartProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cartProductId", unique = true, nullable = false)
	private int cartProductId;

	@Column(name = "productId", unique = true, nullable = false)
	private int productId;
		
	
	@Column(name = "qty", nullable = false)
	@NotNull(message = "qty is mandatory")
	@Min(value = 0, message = "The value of qty must be positive")
	private int qty;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productId", nullable=false, insertable=false, updatable=false)
	private Product mainProduct;

}
