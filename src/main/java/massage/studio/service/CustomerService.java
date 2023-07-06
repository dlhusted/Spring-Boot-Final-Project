package massage.studio.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import massage.studio.controller.model.CustomerData;
import massage.studio.dao.CustomerDao;
import massage.studio.entity.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = true)
	public List<CustomerData> retrieveAllCustomers(){
		List<Customer> customers = customerDao.findAll();
		List<CustomerData> response = new LinkedList<>();
		
		for(Customer customer : customers) {
			CustomerData psd = new CustomerData(customer);
			response.add(psd);
		}
		return response;
	}


}
