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
import technou.com.model.product.WishProduct;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="wishlist")
public class Wishlist {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "wishlistId", unique = true, nullable = false)
	private int wishlistId;
		
	@Column(name = "customerId", nullable = false)
	private int customerId;
	
    //Wishlist of the customer
    @ElementCollection
	@JoinTable(name="WISHLIST_PRODUCT", joinColumns=@JoinColumn(name="WISHLIST_ID"))
	@GenericGenerator(name = "increment-gen", strategy = "increment")
	@CollectionId(columns = { @Column(name="PRODUCT_ID") }, generator = "increment-gen", type = @Type(type="int"))
	private Collection<WishProduct> productsInWishlist = new ArrayList<>();

    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "customerId", nullable=false, insertable=false, updatable=false)
    private Customer customer;

}
