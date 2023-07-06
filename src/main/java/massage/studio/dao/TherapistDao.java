package massage.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import massage.studio.entity.Therapist;

public interface TherapistDao extends JpaRepository<Therapist, Long>{
	

}
