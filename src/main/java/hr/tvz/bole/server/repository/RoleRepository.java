package hr.tvz.bole.server.repository;

import java.util.List;

import hr.tvz.bole.model.UserRole;

public interface RoleRepository {

	public List<UserRole> findAll();

	public UserRole findOne(Integer userId, String role);

	public List<UserRole> findAllByUser(Integer id);

	public void save(UserRole userRole);

	public void delete(Integer id);

	public void deleteAllByUserId(Integer userId);
}
