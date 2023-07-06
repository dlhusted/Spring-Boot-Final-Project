                                            package massage.studio.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MassageStudio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long massageStudioId;
	
	private String massageStudioName;
	private String massageStudioAddress;
	private String massageStudioCity;
	private String massageStudioState;
	private String massageStudioZip;
	private String massageStudioPhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "massage_studio_customer", joinColumns = @JoinColumn(name = "massage_studio_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "massageStudio", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Therapist> therapists = new HashSet<>();
	
}
