package hr.tvz.bole.server.repository;

import java.util.List;

import hr.tvz.bole.model.User;

public interface UserRepository {

	public List<User> findAll();

	public User findOne(Integer id);

	public User findOneByUsername(String username);

	public void save(User user);
	
	//XXX - updates only fields: name, surname, email
	public void update(User user);

	public void changePassword(Integer id, String password);

	public void delete(Integer id);

}
