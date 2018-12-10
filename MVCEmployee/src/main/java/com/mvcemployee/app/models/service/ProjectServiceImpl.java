package com.mvcemployee.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvcemployee.app.models.dao.IProjectDao;
import com.mvcemployee.app.models.entity.Employee;
import com.mvcemployee.app.models.entity.Project;

@Service
public class ProjectServiceImpl implements IProjectService {
	
	@Autowired
	private IProjectDao projectDao;

	@Override
	public void saveProject(Project project) {
		projectDao.save(project);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Project> findAllProject() {
		return (List<Project>) projectDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Project findOne(Long id) {
		return projectDao.findById(id).orElse(null);
	}
	
}
