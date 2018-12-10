package com.mvcemployee.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mvcemployee.app.models.entity.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Long> {

}
