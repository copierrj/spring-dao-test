package nl.idgis.data;

import org.springframework.data.repository.CrudRepository;

import nl.idgis.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}
