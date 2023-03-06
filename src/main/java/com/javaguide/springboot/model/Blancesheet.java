package com.javaguide.springboot.model;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "balancesheet", uniqueConstraints = @UniqueConstraint(columnNames = "tid"))
public class Blancesheet {

	@Id
	@Column(name = "tid", nullable=false)
	private long tid;
	
	@Column(name = "phone", nullable=false)
	private String phone;
	
	@Column(name = "type", nullable=false)
	private String type; //cr/db
	
	@Column(name = "amt", nullable=false)
	private String amt;
	
	@Column(name = "bal", nullable=false)
	private String bal;
	
	@Column(name = "date", nullable=false)
	private Date date;
	
	@Column(name = "purpose", nullable=false)
	private String purpose;
	
	@Column(name = "spclfid", nullable=true)
	private String spclfid; //id/null
}
