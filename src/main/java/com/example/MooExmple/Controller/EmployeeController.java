package com.example.MooExmple.Controller;

import com.example.MooExmple.Models.Employee;
import com.example.MooExmple.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public String showAddEmployeeForm() {
        return "add_employee"; // Returns the "add_employee.html" template (Thymeleaf will handle the .html extension)
    }

    @PostMapping("/add")
    public String addEmployee(@RequestParam String ToDo,
                              @RequestParam String Progress,
                              @RequestParam String Done) {
        Employee newEmployee = new Employee();
        newEmployee.setToDo(ToDo);
        newEmployee.setProgress(Progress);
        newEmployee.setDone(Done);
        employeeService.saveEmployee(newEmployee);
        return "redirect:/"; // Redirect to the employee list after adding a new employee
    }

    // Other controller methods for listing, editing, and deleting employees
    @GetMapping("/")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee_list"; // Returns the "employee_list.html" template
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit_employee"; // Returns the "edit_employee.html" template
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id,
                               @RequestParam String ToDo,
                               @RequestParam String Progress,
                               @RequestParam String Done) {
        Employee employee = employeeService.getEmployeeById(id);
        employee.setToDo(ToDo);
        employee.setProgress(Progress);
        employee.setDone(Done);
        employeeService.saveEmployee(employee);
        return "redirect:/"; // Redirect to the employee list after editing the employee
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/"; // Redirect to the employee list after deleting the employee
    }
}
