package massage.studio.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import massage.studio.controller.model.CustomerData;
import massage.studio.controller.model.MassageStudioData;
import massage.studio.controller.model.MassageStudioData.MassageStudioCustomer;
import massage.studio.controller.model.MassageStudioData.MassageStudioTherapist;
import massage.studio.controller.model.TherapistData;
import massage.studio.service.CustomerService;
import massage.studio.service.MassageStudioService;
import massage.studio.service.TherapistService;

@RestController
@RequestMapping("/massage_studio")
@Slf4j
public class MassageStudioController {
	
	@Autowired
	private MassageStudioService massageStudioService;
	
	@Autowired 
	private TherapistService therapistService;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/massage_studio")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MassageStudioData insertMassageStudio(@RequestBody MassageStudioData massageStudioData) {
		log.info("Creating MassageStudio {}", massageStudioData);
		return massageStudioService.saveMassageStudio(massageStudioData);
	}
	
	@PutMapping("/massage_studio/{massageStudioId}")
	public MassageStudioData updateMassageStudioData(@PathVariable Long massageStudioId, 
			@RequestBody MassageStudioData massageStudioData) {
		massageStudioData.setMassageStudioId(massageStudioId);
		log.info("creating massage studio { } for ID=" + massageStudioId);
		return massageStudioService.saveMassageStudio(massageStudioData);
	}
	
	@PostMapping("{massageStudioId}/therapist")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MassageStudioTherapist insertTherapist(@PathVariable Long massageStudioId, @RequestBody MassageStudioTherapist massageStudioTherapist) {
		log.info("Adding therapist { } to massageStudio with ID=", massageStudioTherapist, massageStudioId);
		return massageStudioService.saveTherapist(massageStudioId, massageStudioTherapist);
	}
	
	@PostMapping("{massageStudioId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MassageStudioCustomer insertCustomer(@PathVariable Long massageStudioId, @RequestBody MassageStudioCustomer massageStudioCustomer) {
		log.info("Adding customer { } to massageStudio with ID=", massageStudioCustomer, massageStudioId);
		return massageStudioService.saveCustomer(massageStudioId, massageStudioCustomer);
	}
	
	@GetMapping("/massage_studio")
	public List<MassageStudioData> retrieveAllMassageStudios(){
		log.info("Retrieve all massage studios.");
		return massageStudioService.retrieveAllMassageStudios();
	}
	
	@GetMapping("{massageStudioId}")
	public MassageStudioData retrieveMassageStudioById(@PathVariable Long massageStudioId) {
		log.info("Retreiving massage studio with ID={ }", massageStudioId);
		return massageStudioService.retrieveMassageStudioById(massageStudioId);
	}
	
	@DeleteMapping("/{massageStudioId}")
	public Map<String, String> deleteMassageStudioById(@PathVariable Long massageStudioId){
		log.info("Deleting massage studio with ID={ }", massageStudioId);
		massageStudioService.deleteMassageStudioById(massageStudioId);
		return Map.of("message", "Massage Studio with ID=" + massageStudioId + " deleted.");
	}
	
	@GetMapping("/therapist")
	public List<TherapistData> retrieveAllTherapists(){
		log.info("Retrieve all therapists.");
		return therapistService.retrieveAllTherapists();
	}
	
	@GetMapping("/customer")
	public List<CustomerData> retrieveAllCustomers(){
		log.info("Retrieve all customers.");
		return customerService.retrieveAllCustomers();
	}
	
	//retrieve all therapists associated with specific massageStudioId 
	@GetMapping("{massageStudioId}/therapist")
	public Set<MassageStudioTherapist> retrieveTherapistsbyMassageStudioId(@PathVariable long massageStudioId){
		log.info("Retrieve all therapists at Massage Studio with ID=" + massageStudioId);
		return massageStudioService.retrieveMassageStudioById(massageStudioId).getTherapists();
	}
	
	//retrieve all customers associated with specific massageStudioId 
	@GetMapping("{massageStudioId}/customer")
	public Set<MassageStudioCustomer> retrieveCustomersbyMassageStudioId(@PathVariable long massageStudioId){
		log.info("Retrieve all customers at Massage Studio with ID=" + massageStudioId);
		return massageStudioService.retrieveMassageStudioById(massageStudioId).getCustomers();
	}

}
