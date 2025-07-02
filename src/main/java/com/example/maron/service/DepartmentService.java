package com.example.maron.service;

import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    UserRepository userRepository;

    /*
     * レコード全件取得処理
     */
    public List<DepartmentForm> findAllUser() throws ParseException {
        List<Department> results = userRepository.findAll();
        List<DepartmentForm> Departments = setDepartmentForm(results);
        return Departments;
    }

    private List<DepartmentForm> setDepartmentForm(List<Department> results) {
        List<DepartmentForm> departments = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            DepartmentForm department = new DepartmentForm();
            Department result = results.get(i);
            department.setId(result.getId());
            department.setName(result.getName());
            department.setCreatedDate(result.getCreatedDate());
            department.setUpdatedDate(result.getUpdatedDate());
            departments.add(department);
        }
        return departments;
    }
}