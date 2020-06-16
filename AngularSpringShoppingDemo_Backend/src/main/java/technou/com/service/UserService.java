package technou.com.service;

import technou.com.model.Customer;

public interface UserService {

	 Customer save(Customer user);
	
	 boolean deleteByUsername(String username);
	 
	 boolean updatePassword(int userId, String password);
	 
	 boolean deleteById(int userId);
	 
	 boolean checkPassword(int userId, String passord);
	 
}
