package com.mvcemployee.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mvcemployee.app.models.entity.Developer;

public interface IDeveloperDao extends CrudRepository<Developer, Long> {

}
