package com.podchez.organizationmvc.controller;

import com.podchez.organizationmvc.model.Employee;
import com.podchez.organizationmvc.service.DepartmentService;
import com.podchez.organizationmvc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/departments/{departmentId}/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String getAllPage(@PathVariable("departmentId") Long departmentId, Model model) {
        model.addAttribute("employees", departmentService.findEmployeesByDepartmentId(departmentId));

        return "employees/all";
    }

    @GetMapping("/new")
    public String getNewPage(@PathVariable("departmentId") Long departmentId,
                             @ModelAttribute("employee") Employee employee) {
        return "employees/new";
    }

    @PostMapping
    public String save(@PathVariable("departmentId") Long departmentId,
                       @ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "employees/new";

        employee.setDepartment(departmentService.findById(departmentId));
        employeeService.save(employee);
        return "redirect:/departments/" + departmentId + "/employees";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("departmentId") Long departmentId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("todo", employeeService.findById(id));
        return "employees/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("departmentId") Long departmentId, @PathVariable("id") Long id,
                         @ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "employees/edit";

        employee.setDepartment(departmentService.findById(departmentId));
        employeeService.update(id, employee);
        return "redirect:/departments/" + departmentId + "/employees";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("departmentId") Long departmentId, @PathVariable("id") Long id) {
        employeeService.delete(id);
        return "redirect:/departments/" + departmentId + "/employees";
    }

    @GetMapping("/search")
    public String getSearchPage(@PathVariable("departmentId") Long departmentId) {
        return "employees/search";
    }

    @PostMapping("/search")
    public String search(@PathVariable("departmentId") Long departmentId,
                         @RequestParam("fullName") String fullName, Model model) {
        model.addAttribute("employees", employeeService.findAllByFullName(fullName).stream()
                .filter(employee -> employee.getDepartment().getId().equals(departmentId))
                .collect(Collectors.toList()));
        return "employees/search";
    }
}
