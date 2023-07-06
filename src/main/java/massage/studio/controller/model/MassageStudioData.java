package massage.studio.controller.model;

import java.util.HashSet;


import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import massage.studio.entity.Customer;
import massage.studio.entity.MassageStudio;
import massage.studio.entity.Therapist;


@Data
@NoArgsConstructor
public class MassageStudioData {
	private Long massageStudioId;
	private String massageStudioName;
	private String massageStudioAddress;
	private String massageStudioCity;
	private String massageStudioState;
	private String massageStudioZip;
	private String massageStudioPhone;
	private Set<MassageStudioCustomer> customers = new HashSet<>();
	private Set<MassageStudioTherapist> therapists = new HashSet<>();
	
	
	public MassageStudioData(MassageStudio massageStudio) {
		massageStudioId = massageStudio.getMassageStudioId();
		massageStudioName = massageStudio.getMassageStudioName();
		massageStudioAddress = massageStudio.getMassageStudioAddress();
		massageStudioCity = massageStudio.getMassageStudioCity();
		massageStudioState = massageStudio.getMassageStudioState();
		massageStudioZip = massageStudio.getMassageStudioZip();
		massageStudioPhone = massageStudio.getMassageStudioPhone();
		
		for(Customer customer : massageStudio.getCustomers()) {
			customers.add(new MassageStudioCustomer(customer));
		}
		
		for(Therapist therapist : massageStudio.getTherapists()) {
			therapists.add(new MassageStudioTherapist(therapist));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class MassageStudioCustomer{
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public MassageStudioCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class MassageStudioTherapist{
		private Long therapistId;
		private String therapistFirstName;
		private String therapistLastName;
		private String therapistPhone;
		private String therapistRank;
		
		public MassageStudioTherapist(Therapist therapist) {
			therapistId = therapist.getTherapistId();
			therapistFirstName = therapist.getTherapistFirstName();
			therapistLastName = therapist.getTherapistLastName();
			therapistPhone = therapist.getTherapistPhone();
			therapistRank = therapist.getTherapistRank();
		}
		
	}

}

