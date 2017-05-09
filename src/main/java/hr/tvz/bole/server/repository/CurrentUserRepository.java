package hr.tvz.bole.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.bole.model.User;
import hr.tvz.bole.model.CurrentUser;

public interface CurrentUserRepository extends JpaRepository<User, Long> {

	public CurrentUser findByUsername(String username);

}
