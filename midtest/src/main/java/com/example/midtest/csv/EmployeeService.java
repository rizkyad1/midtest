package com.example.midtest.csv;

import com.example.midtest.Employee;
import com.example.midtest.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> listAll(){
        return employeeRepository.findAll(Sort.by("gender").ascending());
    }

}
