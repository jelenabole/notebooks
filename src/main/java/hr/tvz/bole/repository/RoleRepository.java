package hr.tvz.bole.repository;

import java.util.List;

import hr.tvz.bole.exceptions.RoleExistsForUser;
import hr.tvz.bole.model.UserRole;

public interface RoleRepository {

	public List<UserRole> findAll();

	public List<UserRole> findAllByUsername(String username);

	public UserRole findOne(String username, String role);

	public Integer save(UserRole userRole) throws RoleExistsForUser;

	Integer update(UserRole userRole);

	void delete(Integer id);

	public void deleteAllByUsername(String username);

	public void deleteRoleByUsername(String username, String role);

}
