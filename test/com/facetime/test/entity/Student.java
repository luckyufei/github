package com.facetime.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.facetime.orm.common.BusinessObject;

@Entity
public class Student implements BusinessObject {

	private static final long serialVersionUID = 5638782704337231526L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(updatable = true)
	private String username;
	@Column(updatable = true)
	private String password;

	public Student() {
		super();
	}

	public Student(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Student [username=" + username + ", password=" + password + "]";
	}
}
