package nl.idgis.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import nl.idgis.data.UserRepository;
import nl.idgis.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testIsEmpty() {
		Iterator<User> itr = userRepository.findAll().iterator();
		assertFalse("repository should be empty", itr.hasNext());
	}
	
	@Test
	@DirtiesContext
	public void testAddUser() {
		Iterator<User> itr = userRepository.findAll().iterator();
		assertFalse("repository should be empty", itr.hasNext());
		
		userRepository.save(new User("copierrj", "Reijer Copier"));
		
		itr = userRepository.findAll().iterator();
		assertTrue(itr.hasNext());
		
		User user = itr.next();
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("copierrj", user.getLogin());
		assertEquals("Reijer Copier", user.getName());
		
		assertFalse(itr.hasNext());
	}
}
