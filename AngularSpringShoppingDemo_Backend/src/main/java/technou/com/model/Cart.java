package technou.com.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import technou.com.model.product.CartProduct;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "cartId", unique = true, nullable = false)
	private int cartId;
		
	@Column(name = "customerId", nullable = false)
	private int customerId;
	
    //Shopping cart of the customer
    @ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="CART_PRODUCT", joinColumns=@JoinColumn(name="CART_ID"))
	@GenericGenerator(name = "increment-gen", strategy = "increment")
	@CollectionId(columns = { @Column(name="PRODUCT_ID") }, generator = "increment-gen", type = @Type(type="int"))
	private Collection<CartProduct> productsInCart = new ArrayList<>();
    

    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "customerId", nullable=false, insertable=false, updatable=false)
    private Customer customer;

}
