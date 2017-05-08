package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.model.User;

public interface UserRepository {

	public List<User> findAll();

	public User findOne(Integer id);

	public User findOneByUsername(String username);

	public Integer save(User user);

	public Integer update(User user);

	public Integer changePassword(Integer id, String password);

	public void delete(Integer id);

}
