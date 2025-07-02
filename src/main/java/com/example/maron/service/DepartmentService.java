package com.example.maron.service;

import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.repository.DepartmentRepository;
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
    DepartmentRepository DepartmentRepository;

    /*
     * レコード全件取得処理
     */
    public List<DepartmentForm> findAllUser() throws ParseException {
        List<Department> results = DepartmentRepository.findAll();
        return setDepartmentForm(results);
    }

    private List<DepartmentForm> setDepartmentForm(List<Department> results) {
        List<DepartmentForm> departments = new ArrayList<>();
        for (Department value : results) {
            DepartmentForm department = new DepartmentForm();
            department.setId(value.getId());
            department.setName(value.getName());
            department.setCreatedDate(value.getCreatedDate());
            department.setUpdatedDate(value.getUpdatedDate());
            departments.add(department);
        }
        return departments;
    }
}