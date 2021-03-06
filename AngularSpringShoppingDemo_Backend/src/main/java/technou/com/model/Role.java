package technou.com.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="role")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "roleId", unique = true, nullable = false)
	private int roleId;
		
	@Column(name = "customerId")
	private int customerId;

    @Column(name = "role")
	private String role;

    @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
    private Set<Customer> customers = new HashSet<Customer>();
}
