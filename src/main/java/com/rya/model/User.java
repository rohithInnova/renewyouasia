package com.rya.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER")
public class User {
	
	public User() {
		
	}

	public User(int id, String firstName, String lastName, String userName, String password, int roleId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.roleId = roleId;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
	
	@Column(name = "first_name")
    private String firstName;
    
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "user_name")
    private String userName;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "role_id")
    private int roleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", roleId=" + roleId + "]";
	}
    
}