package com.mvcemployee.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvcemployee.app.models.dao.IDeveloperDao;
import com.mvcemployee.app.models.dao.IEmployeeDao;
import com.mvcemployee.app.models.dao.IPhoneDao;
import com.mvcemployee.app.models.entity.Developer;
import com.mvcemployee.app.models.entity.Employee;
import com.mvcemployee.app.models.entity.Phone;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;

	@Autowired
	private IPhoneDao phoneDao;
	
	@Autowired
	private IDeveloperDao developerDao;

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		employeeDao.save(employee);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllEmployee() {
		return (List<Employee>) employeeDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllOnlyEmployee() {
		List<Employee> employeesAndDevelopers = findAllEmployee();
		List<Employee> employees = new ArrayList<Employee>();
		for (int i = 0; i < employeesAndDevelopers.size(); i++) {
			if (!(employeesAndDevelopers.get(i) instanceof com.mvcemployee.app.models.entity.Developer)) {
				employees.add(employeesAndDevelopers.get(i));
			}
		}
		return employees;
	}
	
	
	
	@Override
	@Transactional(readOnly=true)
	public Employee findOne(Long id) {
		return employeeDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public void savePhone(Phone phone) {
		phoneDao.save(phone);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Phone> findAllPhone() {
		return (List<Phone>) phoneDao.findAll();
	}
	
	@Override
	@Transactional
	public void saveDeveloper(Developer developer) {
		developerDao.save(developer);
	}
	

	@Override
	@Transactional
	public void removeDeveloper(Developer developer) {
		developerDao.delete(developer);;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Developer findOneDeveloper(Long id) {
		return developerDao.findById(id).orElse(null);
	}
	

}
