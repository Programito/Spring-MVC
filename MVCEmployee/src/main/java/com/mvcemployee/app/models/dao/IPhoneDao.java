package com.mvcemployee.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mvcemployee.app.models.entity.Phone;

public interface IPhoneDao extends CrudRepository<Phone, Long> {
	
}
