package com.mvcemployee.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.*;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;


@Entity
@Table
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Developer extends Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String title;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "developer")
	private Employee employee;
	

	public Developer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	private static final long serialVersionUID = 1L;

}
