package massage.studio.entity;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Therapist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long therapistId;
	
	private String therapistFirstName;
	private String therapistLastName;
	private String therapistPhone;
	private String therapistRank;

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "massage_studio_id")
	MassageStudio massageStudio;


//	public Long getMassageStudioId() {
//		return massageStudio.getMassageStudioId();
//	}
	
//	public Long getMassageStudioId(MassageStudio massageStudio) {
//		return massageStudio.getMassageStudioId();
//}
	
}
