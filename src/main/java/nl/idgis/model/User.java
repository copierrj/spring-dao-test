package nl.idgis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
    @GeneratedValue
    private Long id;

	@Column(unique = true, nullable = false)
	private String login;
	
	@Column
	private String name;
	
	public User() {
		
	}
	
	public User(String login, String name) {
		this.login = login;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
