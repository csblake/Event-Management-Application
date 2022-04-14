/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByUsername(String username);
	User findOneByUsernameAndPassword(String username, String password);
	
	boolean existsByUsername(String username);
}
