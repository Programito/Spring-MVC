package com.mvcemployee.app.models.service;

import java.util.List;

import com.mvcemployee.app.models.entity.Project;

public interface IProjectService {
	public void saveProject(Project project);
	public List<Project> findAllProject();
	public Project findOne(Long id);
}
