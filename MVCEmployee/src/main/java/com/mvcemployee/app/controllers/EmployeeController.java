package com.mvcemployee.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvcemployee.app.models.entity.Address;
import com.mvcemployee.app.models.entity.Developer;
import com.mvcemployee.app.models.entity.Employee;
import com.mvcemployee.app.models.entity.Phone;
import com.mvcemployee.app.models.entity.Project;
import com.mvcemployee.app.models.service.IEmployeeService;
import com.mvcemployee.app.models.service.IProjectService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;
import org.springframework.context.MessageSource;
import java.util.Locale;

@Controller
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private MessageSource messageSource;


	@RequestMapping(value = { "/", "/*" }, method = RequestMethod.GET)
	public ModelAndView viewEmployees(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewemployees");
		List<Employee> employees = employeeService.findAllOnlyEmployee();
		mav.addObject("employees", employees);
		return mav;
	}

	@RequestMapping(value = "addemployee", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("addemployee");
		mav.addObject("newEmployee", new Employee());
		return mav;
	}

	@RequestMapping(value = "addemployee", method = RequestMethod.POST)
	public ModelAndView registerEmployee(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newEmployee") Employee newEmployee, RedirectAttributes flash, Model model, Locale locale) {

		if (newEmployee != null && newEmployee.getName() != null && newEmployee.getName() != "") {
			employeeService.saveEmployee(newEmployee);

			ModelAndView mav = new ModelAndView("viewemployees");
			List<Employee> employees = employeeService.findAllOnlyEmployee();
			mav.addObject("employees", employees);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("addemployee");
			mav.addObject("employeName", messageSource.getMessage("text.error.employee.name", null, locale));
			return mav;
		}

	}

	@RequestMapping(value = "viewemployee/{id}", method = RequestMethod.GET)
	public ModelAndView viewEmployee(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav;

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("Employee con id " + id + "no existe");
			mav = new ModelAndView("viewemployees");
		} else {
			mav = new ModelAndView("viewemployee");
			mav.addObject("employee", employee);
			mav.addObject("phones", employee.getPhones());
			mav.addObject("projects", employee.getProjects());
			mav.addObject("address", employee.getAddress());
			if(employee.getDeveloper()!=null) {
				mav.addObject("developer", employee.getDeveloper().getTitle());
			}
		}
		return mav;
	}


	@RequestMapping(value = "addphone/{id}", method = RequestMethod.GET)
	public ModelAndView addphone(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav;

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("Employee con id " + id + "no existe");
			mav = new ModelAndView("viewemployees");
		} else {
			mav = new ModelAndView("addphone");
			mav.addObject("newPhone", new Phone());
			mav.addObject("id", id);
		}
		return mav;
	}

	@RequestMapping(value = "/addphone/{id}", method = RequestMethod.POST)
	public String registerProduct(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newPhone") Phone newPhone, RedirectAttributes flash, Model model) {

		Employee employee = employeeService.findOne(id);
		Phone phone = new Phone();
		if (employee == null) {
			System.out.println("employee con id" + id + "es nulo");
		} else {
			phone.setEmployee(employee);
			phone.setPhoneNumber(newPhone.getPhoneNumber());
			employeeService.savePhone(phone);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "addproject/{id}", method = RequestMethod.GET)
	public ModelAndView addProject(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes flash, Model model) {
		ModelAndView mav;
		if (id == null) {
			mav = new ModelAndView("viewemployees");
			List<Employee> employees = employeeService.findAllOnlyEmployee();
			mav.addObject("employees", employees);
			System.out.println("Lista de proyectos vacia");
			return mav;
		}
		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("Employee con id " + id + "no existe");
			mav = new ModelAndView("viewemployees");
			return mav;
		} else {
			List<Project> projects = projectService.findAllProject();
			if (projects == null || projects.size() == 0) {
				mav = new ModelAndView("redirect:/");
				List<Employee> employees = employeeService.findAllOnlyEmployee();
				mav.addObject("employees", employees);
				System.out.println("Lista de proyectos vacia");

				return mav;
			} else {
				mav = new ModelAndView("addproject");
				List<Project> projectActive = employee.getProjects();
				if (projectActive != null) {
					for (int i = 0; i < projectActive.size(); i++) {
						if (projects.contains(projectActive.get(i))) {
							projects.remove(projectActive.get(i));
						}
					}
				}

				if (projects.size() == 0) {
					mav = new ModelAndView("viewemployees");
					List<Employee> employees = employeeService.findAllOnlyEmployee();
					mav.addObject("employees", employees);
					System.out.println("Lista de proyectos vacia");
					return mav;
				}
				mav.addObject("projects", projects);
				mav.addObject("id", id);
				Project project = new Project();
				project.setName("vacio");
				mav.addObject("newProject", project);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/addproject/{id}", method = RequestMethod.POST)
	public String registerProject(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newProject") Project newProject, RedirectAttributes flash, Model model) {

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("employee con id" + id + "es nulo");
			return "redirect:/";
		}
		List<Project> projects = employee.getProjects();

		if (projects == null) {
			projects = new ArrayList<Project>();
		}
		if (newProject.getId() == null) {
			System.out.println("new project con id" + id + "es nulo");
			return "redirect:/";
		}
		Project project = projectService.findOne(newProject.getId());
		if (project == null) {
			System.out.println("No exise el project " + newProject.getId());
			return "redirect:/";
		}
		if (!projects.contains(project)) {
			projects.add(project);
			employee.setProjects(projects);
			employeeService.saveEmployee(employee);
		}

		return "redirect:/";
	}

	@RequestMapping(value = "addaddress/{id}", method = RequestMethod.GET)
	public ModelAndView addAdress(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav;

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("Employee con id " + id + "no existe");
			mav = new ModelAndView("viewemployees");
		} else {
			mav = new ModelAndView("addaddress");
			mav.addObject("newAddress", new Address());
			mav.addObject("id", id);
		}
		return mav;
	}

	@RequestMapping(value = "addaddress/{id}", method = RequestMethod.POST)
	public String registerProduct(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newAddress") Address newAddress, RedirectAttributes flash, Model model) {

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("employee con id" + id + "es nulo");
		} else {
			employee.setAddress(newAddress);
			employeeService.saveEmployee(employee);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "developer/{id}", method = RequestMethod.GET)
	public ModelAndView developer(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav;

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("Employee con id " + id + "no existe");
			mav = new ModelAndView("viewemployees");
		} else {
			mav = new ModelAndView("developer");
			if(employee.getDeveloper()!=null) {
				mav.addObject("developer", employee.getDeveloper());
			}
			else {
				mav.addObject("developer", new Developer());
				
			}
			mav.addObject("id", id);
			
		}
		return mav;
	}
	
	@RequestMapping(value = "developer/{id}", method = RequestMethod.POST)
	public String registerDeveloper(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("developer") Developer newDeveloper, RedirectAttributes flash, Model model) {

		Employee employee = employeeService.findOne(id);
		if (employee == null) {
			System.out.println("employee con id" + id + "es nulo");
		} else {
			if(employee.getDeveloper()!=null) {
				employeeService.removeDeveloper(employee.getDeveloper());
			}
			
			Developer developer=new Developer();
			developer.setTitle(newDeveloper.getTitle());
			developer.setName(employee.getName());
			employeeService.saveDeveloper(developer);
			
			employee.setDeveloper(developer);
			employeeService.saveEmployee(employee);
			
		}
		return "redirect:/";
	}
}
