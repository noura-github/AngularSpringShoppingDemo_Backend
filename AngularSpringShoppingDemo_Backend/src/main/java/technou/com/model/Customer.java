package technou.com.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="customer")
public class Customer {
	
		@Id
	    @Column(name = "customerId", unique = true, nullable = false)
	    @GeneratedValue
	    private int customerId;
	    
	    @Column(name = "firstname")
	    @NotBlank(message = "Firstname is mandatory")
	    @Size(min = 3, message = "firstname must have at least characters")
		private String firstname;
	    
	    @Column(name = "lastname")
	    @NotBlank(message = "Lastname is mandatory")
	    @Size(min = 3, message = "lastname must have at least characters")
		private String lastname;
	    
		@Embedded //Not mandatory
		private Address address;
	    
	    @Column(name = "username", unique = true, nullable = false)
	    @NotBlank(message = "Username is mandatory")
		private String username;

	    @Column(name = "password")
	    @NotBlank(message = "Password is mandatory")
		private String password;
	    
	    @Column(name = "gender")
		private String gender;
		
	    @Column(name = "date_of_birth")
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "yyyy-mm-dd")
		private Calendar date_of_birth;

	    
	    @Column(name = "phone")
	    @NotBlank(message = "Phone is mandatory")
		private String phone;
	    
	    
	    @Column(name = "email")
	    @Email(message = "Email should be valid")
		private String email;
	    
	    @Column(name = "active")
		private int active = 1;
	    
	    @Transient
	    private boolean acceptTerms = false;
	    
	    @Transient
	    private String password_confirmation;
	    
	    
	    
	    @OneToOne(mappedBy = "customer", targetEntity=Cart.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
	    private Cart cart;
	    
	    @OneToOne(mappedBy = "customer", targetEntity=Wishlist.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
	    private Wishlist wishlist;
	    
	    
	    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JsonIgnore
	    @JoinTable(name="Customer_Role", 
	        joinColumns=@JoinColumn(name="UserId"),
	        inverseJoinColumns=@JoinColumn(name="RoleId"))  
	    private Set<Role> roles = new HashSet<Role>();
}
