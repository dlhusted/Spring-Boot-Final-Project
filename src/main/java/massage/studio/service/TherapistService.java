package massage.studio.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import massage.studio.controller.model.TherapistData;
import massage.studio.dao.TherapistDao;
import massage.studio.entity.Therapist;

@Service
public class TherapistService {
	
	@Autowired
	private TherapistDao therapistDao;

	@Transactional(readOnly = true)
	public List<TherapistData> retrieveAllTherapists() {
		List<Therapist> therapists = therapistDao.findAll();
		List<TherapistData> response = new LinkedList<>();
		
		for(Therapist therapist : therapists) {
			TherapistData psd = new TherapistData(therapist);
			response.add(psd);
		}
		return response;
	}

}
