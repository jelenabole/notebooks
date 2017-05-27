package hr.tvz.bole.server.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.bole.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public long count();

	public List<User> findAll();

	public User findById(Integer id);

	public User findByUsername(String username);

	@SuppressWarnings("unchecked")
	public User save(User user);

	@Modifying
	@Transactional
	@Query("UPDATE User SET enabled=true WHERE id=:id")
	public void enableUser(@Param("id") Integer id);

	@Modifying
	@Transactional
	@Query("UPDATE User SET enabled=false WHERE id=:id")
	public void disableUser(@Param("id") Integer id);

	@Modifying
	@Transactional
	@Query("UPDATE User SET password=:password WHERE id=:id")
	public void changePassword(@Param("id") Integer id, @Param("password") String password);

	@Transactional
	public Long deleteById(Integer id);

}
