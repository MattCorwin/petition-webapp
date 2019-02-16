package com.mattcorwin.petitonwebapp.models;


import com.mattcorwin.petitonwebapp.models.data.EmployeeDao;
import com.mattcorwin.petitonwebapp.models.data.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserService {

    private EmployeeDao employeeDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(EmployeeDao employeeDao,
                       RoleDao roleDao,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeDao = employeeDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Employee findUserByUsername(String username) {
        return employeeDao.findByUsername(username);
    }

    public void saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setActive(1);
        Role userRole = roleDao.findByRole("USER");
        employee.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        employeeDao.save(employee);

    }

}

