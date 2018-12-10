package com.mvcemployee.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
public class Phone implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Phone_Number")
	private String phoneNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employeeId")
	private Employee employee;
	
	public Phone() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	

	@Override
	public String toString() {
		return "Phone [id=" + id + ", phoneNumber=" + phoneNumber + "]";
	}



	private static final long serialVersionUID = 1L;
}
