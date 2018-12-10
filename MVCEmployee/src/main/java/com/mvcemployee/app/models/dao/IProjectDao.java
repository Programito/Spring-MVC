package com.mvcemployee.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mvcemployee.app.models.entity.Project;

public interface IProjectDao extends CrudRepository<Project, Long> {
	
}
