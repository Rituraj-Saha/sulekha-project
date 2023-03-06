package com.javaguide.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "sessions", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
public class Seesions {

	public Seesions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Seesions(String phone, String token, String status) {
		super();
		this.phone = phone;
		this.token = token;
		this.status = status;
	}
	@Column(name = "phone", nullable=false)
	String phone;
	@Id
	@Column(name = "token", nullable=false)
	String token;
	@Column(name = "status", nullable=false)
	String status;
}
