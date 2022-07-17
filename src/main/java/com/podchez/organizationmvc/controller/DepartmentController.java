package com.podchez.organizationmvc.controller;

import com.podchez.organizationmvc.model.Department;
import com.podchez.organizationmvc.service.DepartmentService;
import com.podchez.organizationmvc.util.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentValidator departmentValidator) {
        this.departmentService = departmentService;
        this.departmentValidator = departmentValidator;
    }

    @GetMapping
    public String getAllPage(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/all";
    }

    @GetMapping("/new")
    public String getNewPage(@ModelAttribute("department") Department department) {
        return "departments/new";
    }

    @PostMapping
    public String save(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult) {
        departmentValidator.validate(department, bindingResult);

        if (bindingResult.hasErrors())
            return "departments/new";

        departmentService.save(department);
        return "redirect:/departments";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("department", departmentService.findById(id));
        return "departments/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("department") @Valid Department updatedDepartment,
                         BindingResult bindingResult) {
        departmentValidator.validate(updatedDepartment, bindingResult);

        if (bindingResult.hasErrors())
            return "departments/edit";

        departmentService.update(id, updatedDepartment);
        return "redirect:/departments";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
