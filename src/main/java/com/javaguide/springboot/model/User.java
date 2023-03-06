package com.javaguide.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
@Data
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "userName", nullable=false)
	private String userName;
	
	@Column(name = "phone", nullable=false,unique = true)
	private String phone;
	@Column(name = "role", nullable=false)
	private String role;
	@Column(name = "password", nullable=false)
	private String password;
	
	
}
