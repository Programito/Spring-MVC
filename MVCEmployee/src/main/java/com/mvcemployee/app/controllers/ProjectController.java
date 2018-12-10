package com.mvcemployee.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mvcemployee.app.models.entity.Employee;
import com.mvcemployee.app.models.entity.Phone;
import com.mvcemployee.app.models.entity.Project;
import com.mvcemployee.app.models.service.IEmployeeService;
import com.mvcemployee.app.models.service.IProjectService;

@Controller
public class ProjectController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IEmployeeService employeeService;
	

	@RequestMapping(value = { "/home2" }, method = RequestMethod.GET)
	public String home(Model model) {
		
		Project project= new Project();
		project.setName("fisica");
		
		Employee employee = new Employee();
		employee.setName("hola");
		List<Project> projects=new ArrayList<Project>();
		projects.add(project);
		
		projectService.saveProject(project);
		employee.setProjects(projects);
		employeeService.saveEmployee(employee);

		return "home";
	}
	
	@RequestMapping(value = { "/viewprojects" }, method = RequestMethod.GET)
	public ModelAndView viewEmployees(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("project/viewprojects");
		List<Project> projects = projectService.findAllProject();
		mav.addObject("projects", projects);
		return mav;
	}
	
	@RequestMapping(value = "addproject", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("project/addproject");
		mav.addObject("newProject", new Project());
		return mav;
	}
	
	@RequestMapping(value = "addproject", method = RequestMethod.POST)
	public String registerEmployee(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newProject") Project newProject, RedirectAttributes flash, Model model) {

		if (newProject != null && newProject.getName() != null && newProject.getName() != "") {
			projectService.saveProject(newProject);
		}

		return "redirect:/viewprojects";
	}
	
}
