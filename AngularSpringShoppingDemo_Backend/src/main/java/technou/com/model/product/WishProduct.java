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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="wishProduct")
public class WishProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "wishProductId", unique = true, nullable = false)
	private int wishProductId;

	@Column(name = "productId", unique = true, nullable = false)
	private int productId;
		
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productId", nullable=false, insertable=false, updatable=false)
	private Product mainProduct;

}
