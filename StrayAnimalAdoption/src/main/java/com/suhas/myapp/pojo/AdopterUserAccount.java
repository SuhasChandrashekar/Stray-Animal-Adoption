package com.suhas.myapp.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="adopteruseraccount_table")
@PrimaryKeyJoinColumn(name="email")

public class AdopterUserAccount {
	public AdopterUserAccount() {
		
	}
	
	
	@Column(name="password")
	private String password;
	
	@Transient
	private Adopter adopter;
	
	@Id
	@GeneratedValue(generator="generator")
	@GenericGenerator(name="generator" ,strategy="assigned")
	@Column(name="adopteremail", unique=true, nullable=false)
	private String adopterEmail;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Adopter getAdopter() {
		return adopter;
	}
	public void setAdopter(Adopter adopter) {
		this.adopter = adopter;
	}
	public String getAdopterEmail() {
		return adopterEmail;
	}
	public void setAdopterEmail(String adopterEmail) {
		this.adopterEmail = adopterEmail;
	}
	
	
}