package massage.studio.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import massage.studio.entity.MassageStudio;
import massage.studio.entity.Therapist;

@Data
@NoArgsConstructor
public class TherapistData {
	private Long therapistId;
	private String therapistFirstName;
	private String therapistLastName;
	private String therapistPhone;
	private String therapistRank;
	MassageStudio massageStudio;
	
	public TherapistData(Therapist therapist) {
		therapistId = therapist.getTherapistId();
		therapistFirstName = therapist.getTherapistFirstName();
		therapistLastName = therapist.getTherapistLastName();
		therapistPhone = therapist.getTherapistPhone();
		therapistRank = therapist.getTherapistRank();
	}
	

}
