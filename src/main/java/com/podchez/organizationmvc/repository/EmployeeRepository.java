package com.podchez.organizationmvc.repository;

import com.podchez.organizationmvc.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByFullNameIgnoreCaseContaining(String fullName);
}
