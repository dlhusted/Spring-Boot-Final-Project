package massage.studio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import massage.studio.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long>{

}
