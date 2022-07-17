package com.podchez.organizationmvc.service;

import com.podchez.organizationmvc.model.Employee;
import com.podchez.organizationmvc.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAllByFullName(String fullName) {
        return employeeRepository.findAllByFullNameIgnoreCaseContaining(fullName);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    public void update(Long id, Employee updatedEmployee) {
        if (!employeeRepository.existsById(id)) {
            return;
        }

        updatedEmployee.setId(id);
        updatedEmployee.setDepartment(employeeRepository.findById(id).get().getDepartment());

        employeeRepository.save(updatedEmployee);
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}