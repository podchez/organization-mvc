package com.podchez.organizationmvc.service;

import com.podchez.organizationmvc.model.Department;
import com.podchez.organizationmvc.model.Employee;
import com.podchez.organizationmvc.repository.DepartmentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<Employee> findEmployeesByDepartmentId(Long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (!department.isPresent()) { // isEmpty() in java 11
            return Collections.emptyList();
        }

        Hibernate.initialize(department.get().getEmployees());

        return department.get().getEmployees();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department findByName(String name) {
        return departmentRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public void update(Long id, Department updatedDepartment) {
        updatedDepartment.setId(id);
        departmentRepository.save(updatedDepartment);
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
