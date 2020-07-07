package com.suhas.myapp.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="employee_table")

public class Employee {
	public Employee() {
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employeeid", unique= true, nullable=false)
	private long employeeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="role")
	private String role;
	
	@Transient
	private EmployeeUserAccount account;
	
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public EmployeeUserAccount getAccount() {
		return account;
	}
	public void setAccount(EmployeeUserAccount account) {
		this.account = account;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
