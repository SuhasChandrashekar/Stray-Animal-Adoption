package com.suhas.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="employeeuseraccount_table")

public class EmployeeUserAccount {
	public EmployeeUserAccount() {
		
	}
	
	@Column(name="password")
	private String password;
	
	@Transient
	private Employee employee;
	
	@Id
	@GeneratedValue(generator="generator")
	@GenericGenerator(name="generator" ,strategy="assigned")
	@Column(name="employeeid", unique=true, nullable=false)
	private long employeeid;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}
	
	
}
