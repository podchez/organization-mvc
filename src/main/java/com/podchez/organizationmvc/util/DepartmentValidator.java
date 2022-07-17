package com.podchez.organizationmvc.util;

import com.podchez.organizationmvc.model.Department;
import com.podchez.organizationmvc.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentValidator(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;

        if (departmentRepository.existsByName(department.getName())) {
            errors.rejectValue("name", "", "Отдел с таким названием уже существует");
        }
    }
}
