package massage.studio.service;

import java.util.LinkedList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import massage.studio.controller.model.MassageStudioData;
import massage.studio.controller.model.MassageStudioData.MassageStudioCustomer;
import massage.studio.controller.model.MassageStudioData.MassageStudioTherapist;
import massage.studio.dao.CustomerDao;
import massage.studio.dao.MassageStudioDao;
import massage.studio.dao.TherapistDao;
import massage.studio.entity.Customer;
import massage.studio.entity.MassageStudio;
import massage.studio.entity.Therapist;

@Service
public class MassageStudioService {
	
	@Autowired
	private MassageStudioDao massageStudioDao;
	
	@Autowired
	private TherapistDao therapistDao;
	
	@Autowired
	private CustomerDao customerDao;

	
	@Transactional(readOnly = false)
	public MassageStudioData saveMassageStudio(MassageStudioData massageStudioData) {
		Long massageStudioId = massageStudioData.getMassageStudioId();
		MassageStudio massageStudio;
		if(massageStudioId == null) {
			massageStudio = findOrCreateMassageStudio(massageStudioId);
		} else {
			massageStudio = findMassageStudioById(massageStudioId);
			if(massageStudio == null) {
				throw new NoSuchElementException("Massage Studio not found with ID=" + massageStudioId);
			}
		}
		copyMassageStudioFields(massageStudio, massageStudioData);
		MassageStudio savedMassageStudio = massageStudioDao.save(massageStudio);
		return new MassageStudioData(savedMassageStudio);
	}

	private void copyMassageStudioFields(MassageStudio massageStudio, MassageStudioData massageStudioData) {
		massageStudio.setMassageStudioName(massageStudioData.getMassageStudioName());
		massageStudio.setMassageStudioAddress(massageStudioData.getMassageStudioAddress());
		massageStudio.setMassageStudioCity(massageStudioData.getMassageStudioCity());
		massageStudio.setMassageStudioState(massageStudioData.getMassageStudioState());
		massageStudio.setMassageStudioZip(massageStudioData.getMassageStudioZip());
		massageStudio.setMassageStudioPhone(massageStudioData.getMassageStudioPhone());
		massageStudio.setMassageStudioId(massageStudioData.getMassageStudioId());
	}

	@Transactional(readOnly = true)
	private MassageStudio findMassageStudioById(Long massageStudioId) {
		return massageStudioDao.findById(massageStudioId).orElse(null);
	}

	private MassageStudio findOrCreateMassageStudio(Long massageStudioId) {
		MassageStudio massageStudio;
		if(Objects.isNull(massageStudioId)) {
			massageStudio = new MassageStudio();
		} else {
			massageStudio = findMassageStudioById(massageStudioId);
		}
		return massageStudio;
	}
	
	

	@Transactional(readOnly = false)
	public MassageStudioTherapist saveTherapist(Long massageStudioId, MassageStudioTherapist massageStudioTherapist) {
		MassageStudio massageStudio = findMassageStudioById(massageStudioId);
		Long therapistId = massageStudioTherapist.getTherapistId();
		Therapist therapist = findOrCreateTherapist(massageStudioId, therapistId);
		
		copyTherapistFields(therapist, massageStudioTherapist);
		
		therapist.setMassageStudio(massageStudio);
		massageStudio.getTherapists().add(therapist);
		
		Therapist dbTherapist = therapistDao.save(therapist);
		
		return new MassageStudioTherapist(dbTherapist);
	}

	private void copyTherapistFields(Therapist therapist, MassageStudioTherapist massageStudioTherapist) {
		therapist.setTherapistFirstName(massageStudioTherapist.getTherapistFirstName());
		therapist.setTherapistId(massageStudioTherapist.getTherapistId());
		therapist.setTherapistRank(massageStudioTherapist.getTherapistRank());
		therapist.setTherapistLastName(massageStudioTherapist.getTherapistLastName());
		therapist.setTherapistPhone(massageStudioTherapist.getTherapistPhone());
	}

	private Therapist findOrCreateTherapist(Long massageStudioId, Long therapistId) {
		if(Objects.isNull(therapistId)) {
			return new Therapist();
		} 
		return findTherapistById(massageStudioId, therapistId);
	}

	private Therapist findTherapistById(Long massageStudioId, Long therapistId) {
		 Therapist therapist = therapistDao.findById(therapistId).orElseThrow(
				 () -> new NoSuchElementException(
				 "Therapist with ID=" + therapistId + " was not found."));
		 
		 if(therapist.getMassageStudio().getMassageStudioId() != massageStudioId) {
			 throw new IllegalArgumentException("The therapist with ID=" + therapistId 
					 + " is not employed by the massage studio with ID=" + massageStudioId + ".");
		 }
		 return therapist;
	}

	public MassageStudioCustomer saveCustomer(Long massageStudioId, MassageStudioCustomer massageStudioCustomer) {
		MassageStudio massageStudio = findMassageStudioById(massageStudioId);
		Long customerId = massageStudioCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(massageStudioId, customerId);
		
		copyCustomerFields(customer, massageStudioCustomer);
		
		customer.getMassageStudios().add(massageStudio);
		massageStudio.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new MassageStudioCustomer(dbCustomer);
	}

	private void copyCustomerFields(Customer customer, MassageStudioCustomer massageStudioCustomer) {
		customer.setCustomerFirstName(massageStudioCustomer.getCustomerFirstName());
		customer.setCustomerId(massageStudioCustomer.getCustomerId());
		customer.setCustomerLastName(massageStudioCustomer.getCustomerLastName());
		customer.setCustomerEmail(massageStudioCustomer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long massageStudioId, Long customerId) {
		if(Objects.isNull(customerId)) {
			return new Customer();
		} 
		return findCustomerById(massageStudioId, customerId);
	}

	private Customer findCustomerById(Long massageStudioId, Long customerId) {
		 Customer customer = customerDao.findById(customerId).orElseThrow(
				 () -> new NoSuchElementException(
				 "Customer with ID=" + customerId + " was not found."));
		 
		 boolean found = false;
		 
		 for(MassageStudio massageStudio : customer.getMassageStudios()) {
			 if(massageStudio.getMassageStudioId() == massageStudioId) {
				 found = true;
				 break;
			 }
		 }
		 if(!found) {
			 throw new IllegalArgumentException("The customer with ID=" + customerId 
					 + " is not a member of the massage studio with ID=" + massageStudioId + ".");
		 }
		 return customer;
	}

	@Transactional(readOnly = true)
	public List<MassageStudioData> retrieveAllMassageStudios() {
		List<MassageStudio> massageStudios = massageStudioDao.findAll();
		
		List<MassageStudioData> response = new LinkedList<>();
		
		for(MassageStudio massageStudio : massageStudios) {
			MassageStudioData psd = new MassageStudioData(massageStudio);
			
			psd.getCustomers().clear();
			psd.getTherapists().clear();
			
			response.add(psd);
		}
		return response;
	}

	@Transactional(readOnly = true)
	public MassageStudioData retrieveMassageStudioById(Long massageStudioId) {
		return new MassageStudioData(findMassageStudioById(massageStudioId));
	}

	@Transactional(readOnly = false)
	public void deleteMassageStudioById(Long massageStudioId) {
		MassageStudio massageStudio = findMassageStudioById(massageStudioId);
		massageStudioDao.delete(massageStudio);
	}
	




}
